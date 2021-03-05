package com.example.login_test3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.bumptech.glide.Glide;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class Diary_edit_box extends BasicActivity {

    //이미지 변수
    private static final int REQUEST_CODE = 0;
    private int PICK_IMAGE_REQUEST = 1;
    Uri uri0;
    private String profilePath;
    //private String profilePath;

    long now = System.currentTimeMillis(); // 현재 시간 가져오기.
    String getTime; //가져온 현재 시간을 String에 넣을 변수.

    ImageView diary_box_image;
    EditText diary_box_settext;
    Button diary_box_set_imagebtn;
    Button diary_box_savebtn;
    Button diary_box_backbtn;
    TextView textView;
    Button button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_edit_box);

        //Date 생성하기
        final Date date = new Date(now);
        //가져오고 싶은 형식으로 가져오기
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        getTime = sdf.format(date);

        diary_box_image = (ImageView) findViewById(R.id.diary_see_image);
        diary_box_set_imagebtn = (Button) findViewById(R.id.diary_box_set_imagebtn);
        diary_box_savebtn = (Button) findViewById(R.id.diary_box_savebtn);
        diary_box_backbtn = (Button) findViewById(R.id.diary_see_backbtn);
        diary_box_settext = (EditText) findViewById(R.id.diary_box_settext);
        button4 = (Button) findViewById(R.id.button4);

        textView = (TextView) findViewById(R.id.textView);

        Intent intent = getIntent();
        final int key = intent.getIntExtra("key",0);
        Log.e("Main2Activity","phone : " + key);

        //String strID = editTextID.getText().toString();
        String strEnglish = diary_box_settext.getText().toString();
        //String strKorean = editTextKorean.getText().toString();

        //쉐어드 연결
        SharedPreferences pref = getSharedPreferences("diary", MODE_PRIVATE);
        SharedPreferences pref1 = getSharedPreferences("diary_count", MODE_PRIVATE);

        SharedPreferences.Editor editor = pref.edit();
        SharedPreferences.Editor editor1 = pref1.edit();


        //쉐어드 정보를 저장할 변수
        String deletedata = null;


        int count = 0;
        count = pref1.getInt("key1", 0);
        for (int i = 0; count > i; i++) {
            deletedata = pref.getString("key" + i, " ");
            if (!deletedata.equals(" ")) {
                String[] array = deletedata.split("#");

                if (array.length == 4) {
                    String key1 = array[0];
                    int keynumbercheck = Integer.parseInt(key1);
                    String title = array[1];
                    String date1 = array[2];
                    String uri = array[3];

                    //가져온 키값과 담긴 키값이 일치한다면
                    if (key == keynumbercheck) {

                        diary_box_settext.setText(title);
                        diary_box_image.setImageURI(Uri.parse(uri));

                        //editor.remove("key" + i);
                        //editor.commit();

                    }

                }

            }

        }


        diary_box_savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String strID = editTextID.getText().toString();
                String strEnglish = diary_box_settext.getText().toString();
                //String strKorean = editTextKorean.getText().toString();

                //쉐어드 연결
                SharedPreferences pref = getSharedPreferences("diary", MODE_PRIVATE);
                SharedPreferences pref1 = getSharedPreferences("diary_count", MODE_PRIVATE);

                SharedPreferences.Editor editor = pref.edit();
                SharedPreferences.Editor editor1 = pref1.edit();


                //쉐어드 정보를 저장할 변수
                String deletedata = null;


                int count = 0;
                count = pref1.getInt("key1", 0);
                for (int i = 0; count > i; i++) {
                    deletedata = pref.getString("key" + i, " ");
                    if (!deletedata.equals(" ")) {
                        String[] array = deletedata.split("#");

                        if (array.length == 4) {
                            String key1 = array[0];
                            int keynumbercheck = Integer.parseInt(key1);
                            String title = array[1];
                            String date1 = array[2];
                            String uri = array[3];

                            //가져온 키값과 담긴 키값이 일치한다면
                            if (key == keynumbercheck) {

                                editor.putString("key"+i, key + "#" + strEnglish + "#" + getTime + "#" +profilePath);

                                //editor.remove("key" + i);
                                editor.commit();

                                Intent intent1 = new Intent(Diary_edit_box.this, Diary_home.class);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent1);

                            }

                        }

                    }

                }

            }
        });

        // 이미지 등록 버튼 클릭.

        diary_box_set_imagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Intent intent = new Intent();
                // Show only images, no videos or anything else
              //  intent.setType("image/*");
              //  intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                // Always show the chooser (if there are multiple options available)
               // startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                myStartActivity(GalleryActivity.class);
            }
        });

        // 사진찍기 버튼 클릭 이벤트.

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myStartActivity(CameraActivity.class);
            }
        });

        // 뒤로가기 버튼 클릭 이벤트.
        diary_box_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Diary_edit_box.this, Diary_home.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0: {
                if (resultCode == RESULT_OK) {
                    profilePath = data.getStringExtra("profilePath");
                    Log.e("로그","들어옴?: "+profilePath);
                    Glide.with(this).load(profilePath).centerCrop().override(508).into(diary_box_image);
                }
                break;
            }
        }
    }





/*
    //이미지 메소드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            uri0 = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri0);
                // Log.d(TAG, String.valueOf(bitmap));

                diary_box_image = (ImageView) findViewById(R.id.diary_see_image);
                diary_box_image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

 */





    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivityForResult(intent, 0);
    }

}
