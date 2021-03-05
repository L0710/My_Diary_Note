package com.example.login_test3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class News_home extends BasicActivity {

    StringBuilder searchResult;
    BufferedReader br;
    //String[] title, link, description, bloggername, postdate;
    String[] title, originallink, link, description, pubDate;
    SNSViewAdaptor mMyAdapter;

    private ListView mListView;
    int itemCount;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_home);


        searchNaver("실시간");

        mListView = (ListView) findViewById(R.id.listViewSNS);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String blog_url = mMyAdapter.getItem(position).getNewslink();
                Intent intent = new Intent(getApplicationContext(), InternetWebView.class);
                intent.putExtra("blog_url", blog_url);
                startActivity(intent);

            }
        });



    }


    public void searchNaver(final String searchObject) {
        final String clientId = "ok4tNl9ssQ_aJg47Y3Rp";//애플리케이션 클라이언트 아이디값";
        final String clientSecret = "OfO9g4a38J";//애플리케이션 클라이언트 시크릿값";
        final int display = 100; // 보여지는 검색결과의 수

        // 네트워크 연결은 Thread 생성 필요
        new Thread() {

            @Override
            public void run() {
                try {
                    String text = URLEncoder.encode(searchObject, "UTF-8");
                    String apiURL = "https://openapi.naver.com/v1/search/news.json?query=" + text + "&display=" + display + "&"; // json 결과
                                     //https://openapi.naver.com/v1/search/news.xml?query=
                                     //https://openapi.naver.com/v1/search/news.json?query=
                                     //https://openapi.naver.com/v1/search/blog?query=
                    URL url = new URL(apiURL);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setRequestProperty("X-Naver-Client-Id", clientId);
                    con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
                    con.connect();

                    int responseCode = con.getResponseCode();


                    if(responseCode==200) { // 정상 호출
                        br = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    } else {  // 에러 발생
                        br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                    }

                    searchResult = new StringBuilder();
                    String inputLine;
                    while ((inputLine = br.readLine()) != null) {
                        searchResult.append(inputLine + "\n");

                    }
                    br.close();
                    con.disconnect();

                    String data = searchResult.toString();

                    String[] array = data.split("\"");
                    title = new String[display];
                    originallink = new String[display];
                    link = new String[display];
                    description = new String[display];
                    pubDate = new String[display];
                    //bloggername = new String[display];
                    //postdate = new String[display];

                    itemCount = 0;
                    for (int i = 0; i < array.length; i++) {
                        if (array[i].equals("title"))
                            title[itemCount] = array[i + 2];
                        if (array[i].equals("link"))
                            link[itemCount] = array[i + 2];
                        if (array[i].equals("description"))
                            description[itemCount] = array[i + 2];
                        if (array[i].equals("originallink"))
                            originallink[itemCount] = array[i + 2];
                            //bloggername[itemCount] = array[i + 2];
                        if (array[i].equals("pubDate")) {
                            pubDate[itemCount] = array[i + 2];
                            //postdate[itemCount] = array[i + 2];
                            itemCount++;
                        }
                    }

                    Log.e("아~아~ 테스트, 테스트", "title잘나오니: " + title[0] + link[0] + description[0] + originallink[0] + pubDate[0]);

                    // 결과를 성공적으로 불러오면, UiThread에서 listView에 데이터를 추가
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listViewDataAdd();
                        }
                    });
                } catch (Exception e) {
                    Log.e("아~아~ 테스트, 테스트", "error : " + e);
                }

            }
        }.start();

    }


    public void listViewDataAdd() {
        mMyAdapter = new SNSViewAdaptor();
        for (int i = 0; i < itemCount; i++) {

            mMyAdapter.addItem(Html.fromHtml(title[i]).toString(),
                    Html.fromHtml(description[i]).toString(),
                    Html.fromHtml(pubDate[i]).toString(),
                    //Html.fromHtml(bloggername[i]).toString(),
                    //Html.fromHtml(postdate[i]).toString(),
                    Html.fromHtml(link[i]).toString());

        }

        // set adapter on listView
        mListView.setAdapter(mMyAdapter);

    }





}
