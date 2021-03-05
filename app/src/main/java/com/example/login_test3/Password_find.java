package com.example.login_test3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Password_find extends BasicActivity {

    private FirebaseAuth mAuth;
    String TAG = "Password_find";

    EditText find_email_text; // 비빌번호찾기 이메일 입력 텍스트 변수 설정.
    Button find_send_email_btn; // 비빌번호착기 보내기 버튼 변수 설정.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_find);
        // Initialize Firebase Auth 초기화
        mAuth = FirebaseAuth.getInstance();

        find_send_email_btn = (Button) findViewById(R.id.find_send_email_btn);
        find_email_text = (EditText) findViewById(R.id.find_email_text);


        // 보내기 버튼 클릭시 이벤트.
        find_send_email_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                send();

            }
        });

    }


    private void send(){

        find_email_text = (EditText) findViewById(R.id.find_email_text);
        String findemail = find_email_text.getText().toString();



        if(findemail.length() > 0 ){

            mAuth.sendPasswordResetEmail(findemail)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                startToast("이메일을 보냈습니다. \n이메일을 확인해 주세요.");
                            }
                        }
                    });
        } else {
            startToast("이메일을 입력해 주세요.");
        }

    }


    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


}
