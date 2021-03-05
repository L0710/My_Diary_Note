package com.example.login_test3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Login extends BasicActivity {

    private FirebaseAuth mAuth;
    String TAG = "MainActivity";

    Button email_start_btn; //이메일로 시작하기 버튼 변수 설정.
    Button login_btn; // 로그인 버튼 변수 설정.
    EditText Login_email_text; // 이메일 작성 텍스트 변수 설정.
    EditText Login_password_text;  // 비밀번호 작성 텍스트 변수 설정.
    TextView login_signup_text; // 회원가입 하시겠습니까? 텍스트 변수 설정.
    TextView password_find; // 비밀번호 찾기 텍스트 변수 설정.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth 초기화
        mAuth = FirebaseAuth.getInstance();

        email_start_btn = findViewById(R.id.email_start_btn); // 이메일로 시작하기 설정.
        login_btn = findViewById(R.id.login_btn); // 로그인 버튼 설정.
        Login_email_text = findViewById(R.id.Login_email_text); //이메일 작성 텍스트 설정.
        Login_password_text = findViewById(R.id.Login_password_text); //비밀번호 작성 텍스트 설정.
        login_signup_text = findViewById(R.id.login_signup_text); //회원가입 하시겠습니까? 텍스트 설정.
        password_find = findViewById(R.id.password_find);


        // 이메일로 시작하기 버튼 클릭시 이벤트.
        email_start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email_start_btn.setVisibility(View.GONE);
                login_btn.setVisibility(View.VISIBLE);
                Login_email_text.setVisibility(View.VISIBLE);
                Login_password_text.setVisibility(View.VISIBLE);
                login_signup_text.setVisibility(View.VISIBLE);
                password_find.setVisibility(View.VISIBLE);

            }
        });

        // 회원가입 하시겠습니까? 텍스트 클릭시 이벤트.
        login_signup_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        // 로그인 버튼 클릭시 이벤트.
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login();

            }
        });

        // 비밀번호 찾기 텍스트 클릭시 이벤트.
        password_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Password_find.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void login(){

        Login_email_text = findViewById(R.id.Login_email_text); //이메일 작성 텍스트 설정.
        String email = Login_email_text.getText().toString();

        Login_password_text = findViewById(R.id.Login_password_text); //비밀번호 작성 텍스트 설정.
        String password = Login_password_text.getText().toString();

        if(email.length() > 0 && password.length() > 0){

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            startToast("로그인에 성공했습니다.");

                            Intent intent = new Intent(Login.this, Home.class);
                            //intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            finish();
                            startActivity(intent);


                            //성공했을떄 UI 로직
                        } else {
                            if(task.getException() != null){
                                //startToast(task.getException().toString());
                                startToast("로그인에 실패하였습니다. \n이메일과 패스워드를 다시 확인해주세요.");
                            }
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            //실패했을때 UI 로직
                        }

                        // ...
                    }
                });
        } else {
            startToast("이메일 또는 비밀번호를 입력해 주세요.");
        }

    }


    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
