package com.example.login_test3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Diary_edit extends BasicActivity {

    private static final String TAG = "다이어리작성 액티비티";
    private static final int PICK_IMAGE = 0;
    private FirebaseUser user;
    private ArrayList<String> pathList = new ArrayList<>();
    private LinearLayout parent;
    private RelativeLayout buttonsBackgroundLayout;
    private ImageView selectedImageVIew;
    private EditText selectedEditText;
    private int pathCount, successCount;

    EditText diary_edit_settext; //일기 작성 텍스트 변수
    String diary_textstr;

    ImageView diary_edit_setimage; //일기 작성 이미지 변수
    String uristr;

    Button diary_set_imagebtn; // 일기 작성 이미지 등록 버튼 변수
    Button diary_edit_savebtn; // 일기 작성 저장버튼 변수
    Button diary_edit_backbtn; // 일기 작성 돌아가기 버튼 변수


    long now = System.currentTimeMillis(); // 현재 시간 가져오기.
    String getTime; //가져온 현재 시간을 String에 넣을 변수.

    int count = 0;

    //이미지 변수
    private static final int REQUEST_CODE = 0;
    private int PICK_IMAGE_REQUEST = 1;
    private int CAMERA_REQUEST_CODE = 2;
    Uri uri = null;
    //private String uri;

    private String profilePath;

    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_edit);


        diary_edit_setimage = (ImageView) findViewById(R.id.diary_edit_setimage);

        //Date 생성하기
        final Date date = new Date(now);
        //가져오고 싶은 형식으로 가져오기
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        getTime = sdf.format(date);


        // 돌아가기 버튼 클릭.
        diary_edit_backbtn = (Button) findViewById(R.id.diary_edit_backbtn);
        diary_edit_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Diary_edit.this, Diary_home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


        // 저장 버튼 클릭.//쉐어드 사용하여 저장할때 쓰는 코드들.

        diary_edit_savebtn = (Button) findViewById(R.id.diary_edit_savebtn);
        diary_edit_savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                diary_edit_settext = (EditText) findViewById(R.id.diary_edit_settext);
                diary_textstr = diary_edit_settext.getText().toString();

                //uristr = uri.toString();

                Intent intent = new Intent(Diary_edit.this, Diary_edit_complete.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                //-------------------------------------------------

                SharedPreferences diary = getSharedPreferences("diary", MODE_PRIVATE);
                SharedPreferences diary_count = getSharedPreferences("diary_count", MODE_PRIVATE);

                SharedPreferences.Editor editor = diary.edit();
                SharedPreferences.Editor editor1 = diary_count.edit();

                editor1.putInt("key1", 0);

                int keycount = diary_count.getInt("key1", 0);

                String str = keycount + "#" + diary_textstr + "#" + getTime + "#" + profilePath;

                if (str != null) {

                    //숫자 가져오기
                    count = diary_count.getInt("key1", 0);

                    //키값 저장하기(+입력한 내용 저장)
                    editor.putString("key" + count, str);
                    editor.apply();

                    //숫자 증가
                    count++;

                    //숫자 저장
                    editor1.putInt("key1", count);
                    editor1.apply();


                }

                startActivity(intent);

            }

        });




        // 이미지 등록 버튼 클릭.
        diary_set_imagebtn = (Button) findViewById(R.id.diary_set_imagebtn);
        diary_set_imagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  Intent intent = new Intent();
                // Show only images, no videos or anything else
              //  intent.setType("image/*");
              //  intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                // Always show the chooser (if there are multiple options available)
              //  startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

                myStartActivity(GalleryActivity.class);
            }
        });



        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //startActivityForResult(intent, 0);
                //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            //    startActivityForResult(intent, CAMERA_REQUEST_CODE);
                myStartActivity(CameraActivity.class);
            }
        });



    }


/*
    //이미지 메소드, 쉐어드 이용시 사용.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {


                    uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                diary_edit_setimage = (ImageView) findViewById(R.id.diary_edit_setimage);
                diary_edit_setimage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

 */






    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0: {
                if (resultCode == Activity.RESULT_OK) {
                    profilePath = data.getStringExtra("profilePath");
                    Log.e("로그","들어옴?: "+profilePath);
                    Glide.with(this).load(profilePath).centerCrop().override(507).into(diary_edit_setimage);
                }
                break;
            }
        }
    }





    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivityForResult(intent, 0);
    }




}
