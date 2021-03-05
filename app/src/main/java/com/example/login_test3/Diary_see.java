package com.example.login_test3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Diary_see extends BasicActivity {

    ImageView diary_see_image2;
    TextView diary_see_text;
    Button diary_see_backbtn;
    //private String uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_see);

        diary_see_image2 = (ImageView) findViewById(R.id.diary_see_image2);
        diary_see_text = (TextView) findViewById(R.id.diary_see_text);

        Intent intent = getIntent();
        final int key = intent.getIntExtra("key",0);
        Log.e("Main2Activity","phone : " + key);

        //String strID = editTextID.getText().toString();
        //String strEnglish = diary_box_settext.getText().toString();
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
                    Log.e("asdf","들어왔니???????????"+uri);
                    //가져온 키값과 담긴 키값이 일치한다면
                    if (key == keynumbercheck) {

                        diary_see_text.setText(title);
                        //diary_see_image2.setImageURI(Uri.parse(uri));
                        Glide.with(this).load(uri).centerCrop().override(509).into(diary_see_image2);
                        Log.e("asdf","들어왔니?"+diary_see_image2);
                        //editor.remove("key" + i);
                        //editor.commit();

                    }

                }

            }

        }

        // 뒤로가기 버튼 클릭시 이벤트.
        diary_see_backbtn = findViewById(R.id.diary_see_backbtn);
        diary_see_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Diary_see.this, Diary_home.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
            }
        });



    }
}
