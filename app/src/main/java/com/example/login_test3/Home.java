package com.example.login_test3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends BasicActivity {

    private AdView mAdView; //안드로이드 에드몹 광고

    Button promise_set_btn; // 홈에서 [다짐/명언] 텍스트 삽입 버튼 변수.
    ImageButton home_news_btn; // 홈에서 뉴스 이미지 버튼 변수.
    TextView promise_set_text; // 홈에서 [다짐/명엄] 텍스트 변수.
    ImageButton home_memo_btn; // 홈에서 메모 이미지 버튼 변수.
    ImageButton home_alarm_but; // 홈에서 알람 이미지 버튼 변수.
    ImageButton home_diary_btn; // 홈에서 일기 이미지 버튼 변수.
    ImageButton home_profile_btn; // 홈에서 프로필 이미지 버튼 변수.
    ImageButton home_game_btn; // 홈에서 게임 이미지 버튼 변수.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //안드로이드 에드몹 광고

        MobileAds.initialize(this, getString(R.string.admob_app_id));
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.e("에드몹","실행됨??");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.e("에드몹","에러냐??");
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });


        //파이어베이스
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            Intent intent = new Intent(Home.this, Login.class);
            //finish();
            ActivityCompat.finishAffinity(Home.this);
            startActivity(intent);
        }


        promise_set_btn = (Button) findViewById(R.id.promise_set_btn);
        promise_set_text = (TextView) findViewById(R.id.promise_set_text);
        home_memo_btn = (ImageButton) findViewById(R.id.home_memo_btn);
        home_news_btn = (ImageButton) findViewById(R.id.home_news_btn);
        home_alarm_but = (ImageButton) findViewById(R.id.home_alarm_but);
        home_diary_btn = (ImageButton) findViewById(R.id.home_diary_btn);
        home_profile_btn = (ImageButton) findViewById(R.id.home_profile_btn);
        home_game_btn = (ImageButton) findViewById(R.id.home_game_btn);



        //오늘의 다짐 수정 버튼 클릭.
        promise_set_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Promise_edit.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        // 메모이미지 버튼 클릭.
        home_memo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Memo_home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        // 뉴스 이미지 버튼 클릭.
        home_news_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://news.naver.com/"));
                //startActivity(intent);

                Intent intent = new Intent(Home.this, News_home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        // 알람 이미지 버튼 클릭
        home_alarm_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Alarm_home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        // 일기 이미지 버튼 클릭.
        home_diary_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Diary_home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        // 프로필 이미지 버튼 클릭.
        home_profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Profile.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //finish();
                startActivity(intent);
            }
        });

        // 게임 이미지 버튼 클릭.
        home_game_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Game_title.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //finish();
                startActivity(intent);
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        //저장된 값을 불러오기 위해 같은 네임파일을 찾음.
        SharedPreferences promise = getSharedPreferences("promise_info",MODE_PRIVATE);
        //promise_info라는 key에 저장된 값이 있는지 확인. 아무값도 들어있지 않으면 ""를 반환
        String text = promise.getString("set_promise","");
        promise_set_text.setText(text);

        home_profile_btn = (ImageButton) findViewById(R.id.home_profile_btn);


    }



}
