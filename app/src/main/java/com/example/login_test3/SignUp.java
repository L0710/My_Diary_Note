package com.example.login_test3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends BasicActivity {

    private static final String TAG = "SignUpActivity";
    private FirebaseAuth mAuth;

    Button signup_complete_btn; //회원가입 버튼 변수 설정.
    EditText signup_email_edittext; //이메일 텍스트 변수 설정.
    EditText signup_password_edittext; //패스워드 텍스트 변수 설정.
    EditText signup_password_check; // 패스워드 체크 텍스트 변수 설정.
    ImageButton signup_back_btn; // 뒤로가기 이미지 버튼 변수 설정.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Auth 초기화
        mAuth = FirebaseAuth.getInstance();

        signup_complete_btn = (Button) findViewById(R.id.signup_complete_btn);
        signup_complete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();

            }
        });

        signup_back_btn = (ImageButton) findViewById(R.id.signup_back_btn);
        signup_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, Login.class);
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

    //실제 회원가입 로직을 수행하는 놈.

    private void signUp(){
        // 이메일을 설정 후 String email에 입력한 값을 넣고 String으로 변환.
        signup_email_edittext = (EditText) findViewById(R.id.signup_email_edittext);
        String email = signup_email_edittext.getText().toString();

        // 패스워드를 설정 후 String password에 입력한 값을 넣고 String으로 변환.
        signup_password_edittext = (EditText) findViewById(R.id.signup_password_edittext);
        String password = signup_password_edittext.getText().toString();

        //패스워드 체크를 설정 후 String passwordcheck에 입력한 값을 넣고 String으로 변환.
        signup_password_check = (EditText) findViewById(R.id.signup_password_check);
        String passwordcheck = signup_password_check.getText().toString();

        if(email.length() > 0 && password.length() > 0 && passwordcheck.length() > 0){


            if(password.equals(passwordcheck)){

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //Toast.makeText(SignUp.this, "회원가입이 성공했습니다.", Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    startToast("회원가입에 성공했습니다.");

                                    Intent intent = new Intent(SignUp.this, Login.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);


                                    //성공했을떄 UI 로직
                                } else {
                                    if(task.getException() != null){
                                        //실패했을때 UI 로직
                                        //startToast(task.getException().toString());
                                        startToast("아이디가 이메일 형식인지 \n중복된 이메일이 있는지 \n비밀번호가 6자리 이상인지 확인해주세요. ");
                                    }
                                    // If sign in fails, display a message to the user.
                                    //Log.w(TAG, "createUserWithEmail:failure", task.getException());

                                }

                                // ...
                            }
                        });

            }else {
                Toast.makeText(SignUp.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            }
        } else {
            startToast("이메일 또는 비밀번호를 입력해 주세요.");
        }



    }

    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


}
