package com.example.login_test3;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Startloding extends BasicActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startloding);

        startLoading();

        Intent intent = getIntent(); // 인텐트로 넘어온 값을 받아올 수 있게 함.

        ActionBar actionBar = getSupportActionBar(); // getSupportActionBar()를 통해 엑션바를 받아오기.
        actionBar.hide(); // hide()를 통해서 액션바 감추기.

    }


    public void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Startloding.this, Home.class);
                finish();
                startActivity(intent);
            }
        }, 2000);
    }

}
