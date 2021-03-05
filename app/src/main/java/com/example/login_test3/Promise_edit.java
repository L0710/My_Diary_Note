package com.example.login_test3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Promise_edit extends BasicActivity {

    EditText box_set_edtext2; // 텍스트 입력 변수
    Button box_set_btn2;  // 완료 버튼 변수
    Button box_cancel2; //취소 버튼 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promise_edit);

        box_set_edtext2 = (EditText) findViewById(R.id.box_set_edtext2);
        box_set_btn2 = (Button) findViewById(R.id.box_set_btn2);
        box_cancel2 = (Button) findViewById(R.id.box_cancel2);

        SharedPreferences promise = getSharedPreferences("promise_info", MODE_PRIVATE);
        String text = promise.getString("set_promise","");
        box_set_edtext2.setText(text);

        //완료버튼을 누르면
        box_set_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 사용자가 입력한 내용을 가져와서
                String str_set_edtext = box_set_edtext2 .getText().toString();

                //쉐어드를 생성하고
                SharedPreferences promise = getSharedPreferences("promise_info", MODE_PRIVATE);
                SharedPreferences.Editor editor = promise.edit();

                //editor.remove("set_promise" + str_set_edtext);
                //editor.commit();

                //쉐어드에 저장한다.
                editor.putString("set_promise", str_set_edtext);
                editor.apply();

                Intent intent = new Intent(Promise_edit.this, Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        //취소버튼을 누르면
        box_cancel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Promise_edit.this, Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

    }
}
