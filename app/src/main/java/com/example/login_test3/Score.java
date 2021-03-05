package com.example.login_test3;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Score extends BasicActivity {

    TextView sub_result ;
    Button sub_retry;
    Button back_home;
    SharedPreferences spf = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);//주소로 알고있는 xml을 눈에 보이는 view로 바꿔줌 ->InfLate

        spf = getSharedPreferences("spfScore",MODE_PRIVATE); // 키값이 또 있으면 덮어쓰겠다

        sub_result = findViewById(R.id.sub_result);
        sub_retry = findViewById(R.id.sub_retry);
        back_home = findViewById(R.id.back_home);

        ActionBar actionBar = getSupportActionBar(); // getSupportActionBar()를 통해 엑션바를 받아오기.
        actionBar.hide(); // hide()를 통해서 액션바 감추기.

        int score = getIntent().getIntExtra("score", -1);
        sub_result.setText(String.valueOf(score));

        if(spf.getInt("spfscore",0) < score){ //내점수가 저번 점수보다 크면
            spf.edit().putInt("spfscore",score).commit(); //반영의 commit(). 현재상태 저장
            sub_result.setText("신기록달성\n"+score);
        }

        sub_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Score.this, Game.class);
                finish();
                startActivity(intent);
            }
        });

        back_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Score.this, Home.class);
                ActivityCompat.finishAffinity(Score.this);
                startActivity(intent);
                //finish();
            }
        });

    }
}
