package com.example.login_test3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class Memo_home extends BasicActivity {

    Button memo_edit_btn; // 메모작성하기 버튼 변수
    Button memo_back_btn; // 메모 홈에서 홈으로 돌아가기 버튼 변수


    RecyclerView mRecyclerView = null;
    MemoAdapter mAdapter = null;
    ArrayList<Memodata> mList = new ArrayList<Memodata>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_home);

        memo_edit_btn = (Button) findViewById(R.id.memo_edit_btn);
        memo_back_btn = (Button) findViewById(R.id.memo_back_btn);

        // 메모작성하기 버튼을 눌렀을때.
        memo_edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Memo_home.this, Memo_edit.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                //intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        // 돌아가기 버튼을 눌렀을때.
        memo_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Memo_home.this, Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    //리사이클러뷰 추가하는 메소드
    public void addItem(String title, String time, int keynumber) {
        Memodata item = new Memodata();

        item.setMemoStr(title);
        item.setTimeStr(time);
        item.setKeynumber(keynumber);

        //mList.add(item);
        mList.add(0, item);
    }


    @Override
    protected void onResume() {
        super.onResume();

        mList.clear();

        //쉐어드 파일 지정
        final SharedPreferences memo_sh = getSharedPreferences("memo", MODE_PRIVATE);
        //숫자용 쉐어드 파일
        SharedPreferences memo_count_sh = getSharedPreferences("memo_count", MODE_PRIVATE);


        //숫자를 불러올때
        final SharedPreferences.Editor editor1 = memo_count_sh.edit();
        //값을 저장할 경우
        final SharedPreferences.Editor editor = memo_sh.edit();

        final int counttext = memo_count_sh.getInt("key1", 0);


        if (editor != null) {

            for (int i = 0; i <= counttext; i++) {

                String naam = memo_sh.getString("key" + i, " ");

                String[] array = naam.split("#");

                if (array.length == 3) {
                    /*String uri = array[0];*/
                    int key = Integer.parseInt(array[0]);
                    String text = array[1];
                    String date = array[2];

                    // 아이템 추가.
                    addItem(text, date, key);


                }

            }

        }


        //리사이클러뷰 아이디 지정
        mRecyclerView = findViewById(R.id.memo_recyclerview);
        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        mAdapter = new MemoAdapter(this, mList);
        mRecyclerView.setAdapter(mAdapter);

        // 리사이클러뷰에 LinearLayoutManager 지정. (vertical)
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        /// ... 코드 계속.

        //uri값이 null이 아닐때 이미지를 출력

        mAdapter.notifyDataSetChanged();

        MemoAdapter adapter = new MemoAdapter(mList);
    }

}
