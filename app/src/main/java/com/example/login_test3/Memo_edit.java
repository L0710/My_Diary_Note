package com.example.login_test3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Memo_edit extends BasicActivity {

    EditText memo_edit_Text; // 메모 내용을 담을 변수.
    String memo_str;

    long now = System.currentTimeMillis(); // 현재 시간 가져오기.
    String getTime; //가져온 현재 시간을 String에 넣을 변수.

    Button memo_complete_btn;
    Button memo_back_btn2;

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_edit);

        //Date 생성하기
        final Date date = new Date(now);
        //가져오고 싶은 형식으로 가져오기
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        getTime = sdf.format(date);

        memo_complete_btn = (Button) findViewById(R.id.memo_complete_btn);
        memo_back_btn2 = (Button) findViewById(R.id.memo_back_btn2);

        // 작성완료 버튼을 눌렀을때.
        memo_complete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                memo_edit_Text = (EditText) findViewById(R.id.memo_edit_Text);
                memo_str = memo_edit_Text.getText().toString();

                Intent intent = new Intent(Memo_edit.this, Memo_edit_complete.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags((Intent.FLAG_ACTIVITY_NO_HISTORY));

                //---------------------------------------------

                //memo_str, getTime

                SharedPreferences memo_sh = getSharedPreferences("memo", MODE_PRIVATE);
                SharedPreferences memo_count_sh = getSharedPreferences("memo_count", MODE_PRIVATE);

                SharedPreferences.Editor editor = memo_sh.edit();
                SharedPreferences.Editor editor1 = memo_count_sh.edit();
                editor1.putInt("key1", 0);

                int keycount = memo_count_sh.getInt("key1",0);

                String str = keycount + "#" + memo_str + "#" + getTime;

                if (str != null) {

                    //숫자 가져오깅
                    count = memo_count_sh.getInt("key1", 0);

                    //키값 저장하기
                    editor.putString("key" + count, str);
                    editor.apply();

                    //숫자 증가
                    count++;

                    //숫자 저장
                    editor1.putInt("key1", count);
                    editor1.apply();
                }

                //인텐트로 정보 넘기기
                startActivity(intent);

            }
        });

        //돌아가기 버튼을 눌렀을때.
        memo_back_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Memo_edit.this, Memo_home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });




    }


}
