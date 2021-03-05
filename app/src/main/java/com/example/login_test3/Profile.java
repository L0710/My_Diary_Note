package com.example.login_test3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;



public class Profile extends BasicActivity {

    private static final String TAG = "Profile";

    Button logout_btn; //로그아웃 버튼 변수 설정.
    ImageButton profile_back_btn; //뒤로가기 이미지 버튼 변수 설정.
    Button profile_write_btn; // 프로필 작성 버튼 변수 설정.
    Button delete_btn; // 회원탈퇴 버튼 변수 설정.
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser firebaseUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        startLoading();


        // 로그아웃 버튼 클릭 이벤트
        logout_btn = (Button) findViewById(R.id.logout_btn);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(Profile.this, Login.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //finish();
                ActivityCompat.finishAffinity(Profile.this);
                startActivity(intent);
            }
        });

        // 뒤로가기 버튼 클릭 이벤트
        profile_back_btn = (ImageButton) findViewById(R.id.profile_back_btn);
        profile_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        //프로필 작성 버튼 클릭 이벤트
        profile_write_btn = (Button) findViewById(R.id.profile_write_btn);
        profile_write_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//너도 문제 있는거 같음..
                Intent intent = new Intent(Profile.this, Profile_edit.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                //intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        //회원탈퇴 버튼 클릭 이벤트
        delete_btn = (Button) findViewById(R.id.delete_btn);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteDialog(); //회원탈퇴 다이얼로그 생성
                //Intent intent = new Intent(Profile.this, Login.class);
                //ActivityCompat.finishAffinity(Profile.this);
                //startActivity(intent);

            }
        });


        final ImageView profileImageView = findViewById(R.id.imageView4);
        final TextView nick = findViewById(R.id.textView15);
        final TextView message = findViewById(R.id.textView14);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users").document(firebaseUser.getUid());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        if (document.exists()) {
                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                            if(document.getData().get("photoUri") != null){
                                Glide.with(Profile.this).load(document.getData().get("photoUri")).centerCrop().override(500).into(profileImageView);
                            }

                            nick.setText(document.getData().get("nick").toString());
                            message.setText(document.getData().get("message").toString());
                        } else {
                            Log.d(TAG, "No such document");
                            //myStartActivity(MemberInitActivity.class);
                        }
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

    }

    //회원탈퇴 다이얼로그 함수.
    private void deleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
        builder.setTitle("회원 탈퇴");
        builder.setMessage("회원탈퇴시, 기존에 작성한 모든 자료가 삭제됩니다.\n계속해서 탈퇴하시겠습니까?");

        builder.setNegativeButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //예 눌렀을때의 이벤트 처리
                revokeAccess();
                Intent intent = new Intent(Profile.this, Login.class);
                ActivityCompat.finishAffinity(Profile.this);
                startActivity(intent);
            }
        });

        builder.setPositiveButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //아니오 눌렀을때의 이벤트 처리
            }
        });
        builder.show();
    }


    //파이어베이스 회원탈퇴 함수.
    private void revokeAccess() {
        mAuth.getCurrentUser().delete();
    }

    public void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                final RelativeLayout lodaerLayout = findViewById(R.id.loaderLyaout);
                lodaerLayout.setVisibility(View.GONE);
            }
        }, 2000);
    }

}
