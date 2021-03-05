package com.example.login_test3;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class Profile_edit extends BasicActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String profilePath;
    private ImageView profileImageVIew;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        mAuth = FirebaseAuth.getInstance();

        profileImageVIew = findViewById(R.id.profile_edit_image);

        findViewById(R.id.profile_edit_complete_btn).setOnClickListener(onClickListener);
        findViewById(R.id.profile_edit_gallery_btn).setOnClickListener(onClickListener);
        findViewById(R.id.profile_edit_back_btn).setOnClickListener(onClickListener);
        findViewById(R.id.camera_btn).setOnClickListener(onClickListener);


        final ImageView profileImageView1 = findViewById(R.id.profile_edit_image);
        final TextView nick = findViewById(R.id.profile_edit_nick);
        final TextView message = findViewById(R.id.profile_edit_message);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        //DocumentReference documentReference = firebaseFirestore.collection("users").document(firebaseUser.getUid());
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users").document(firebaseUser.getUid());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        if (document.exists()) {
                            //Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                            if(document.getData().get("photoUri") != null){
                                Glide.with(Profile_edit.this).load(document.getData().get("photoUri")).centerCrop().override(500).into(profileImageView1);
                            }

                            nick.setText(document.getData().get("nick").toString());
                            message.setText(document.getData().get("message").toString());
                        } else {
                            //Log.d(TAG, "No such document");
                            //myStartActivity(MemberInitActivity.class);
                        }
                    }
                } else {
                    //Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0: {
                if (resultCode == Activity.RESULT_OK) {
                    profilePath = data.getStringExtra("profilePath");
                    Log.e("뭐야??","어떻게 된거지?"+profilePath);
                    Glide.with(Profile_edit.this).load(profilePath).centerCrop().override(506).into(profileImageVIew);
                    //Picasso.with(context).load(profilePath).resize(400, 0).into(profileImageVIew);
                    //Glide.with(this).load(profilePath).centerCrop().override(500).into(profileImageVIew);
                    //profileImageVIew.setImageURI(Uri.parse(profilePath));

                }
                break;
            }
        }
    }





    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.profile_edit_complete_btn:
                    profileUpdate();
                    break;

                case R.id.profile_edit_gallery_btn:
                    myStartActivity(GalleryActivity.class);
                    break;

                case R.id.profile_edit_back_btn://이거 지금 문제 있음....
                    Intent intent = new Intent(Profile_edit.this, Profile.class);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);
                    break;

                case R.id.camera_btn:
                    myStartActivity(CameraActivity.class);
                    //Intent intent1 = new Intent(Profile_edit.this, CameraActivity.class);
                    //intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //startActivity(intent1);
                    break;
            }
        }
    };

    private void profileUpdate() {
        final String nick = ((EditText)findViewById(R.id.profile_edit_nick)).getText().toString();
        final String message = ((EditText)findViewById(R.id.profile_edit_message)).getText().toString();

        if(nick.length() > 0 && message.length() > 0) {

            final RelativeLayout lodaerLayout = findViewById(R.id.loaderLyaout);
            lodaerLayout.setVisibility(View.VISIBLE);

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            user = FirebaseAuth.getInstance().getCurrentUser();
            final StorageReference mountainImagesRef = storageRef.child("users/" + user.getUid() + "/profileImage.jpg");

            if(profilePath == null){
                Profile_info profile_info = new Profile_info(nick, message);
                uploader(profile_info);
            }else{
                try {
                    InputStream stream = new FileInputStream(new File(profilePath));
                    UploadTask uploadTask = mountainImagesRef.putStream(stream);
                    uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                lodaerLayout.setVisibility(View.GONE); //로딩 사라짐
                                throw task.getException();
                            }
                            return mountainImagesRef.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                lodaerLayout.setVisibility(View.GONE); //로딩 사라짐
                                Uri downloadUri = task.getResult();

                                Profile_info profile_info = new Profile_info(nick, message, downloadUri.toString());
                                uploader(profile_info);
                            } else {
                                lodaerLayout.setVisibility(View.GONE); //로딩 사라짐
                                startToast("회원정보를 보내는데 실패하였습니다.");
                            }
                        }
                    });
                } catch (FileNotFoundException e) {
                    Log.e("로그", "에러: " + e.toString());
                }
            }
        } else {
            startToast("회원정보를 입력해주세요.");
        }
    }

    private void uploader(Profile_info profile_info){

        final RelativeLayout lodaerLayout = findViewById(R.id.loaderLyaout);
        lodaerLayout.setVisibility(View.VISIBLE);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(user.getUid()).set(profile_info)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        lodaerLayout.setVisibility(View.GONE); //로딩 사라짐
                        startToast("회원정보 등록을 성공하였습니다.");
                        Intent intent = new Intent(Profile_edit.this, Profile.class);
                        //intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        lodaerLayout.setVisibility(View.GONE); //로딩 사라짐
                        startToast("회원정보 등록에 실패하였습니다.");
                        Log.e("에러임?", "Error writing document", e);
                    }
                });
    }

    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivityForResult(intent, 0);
    }



}
