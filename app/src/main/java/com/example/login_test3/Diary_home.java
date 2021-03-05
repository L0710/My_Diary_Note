package com.example.login_test3;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;


public class Diary_home extends BasicActivity {
    private static final String TAG = "MainActivity";

    Button diary_home_backbtn; // 일기 홈에서 돌아가기 버튼 변수.
    Button diary_home_editbtn; // 일기 홈에서 일기작성 버튼 변수.

    RecyclerView mRecyclerView =null ;
    DiaryAdapter mAdapter =null ;
    ArrayList<Diarydata> mList = new ArrayList<Diarydata>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_home);

        // 돌아가기 버튼 클릭.
        diary_home_backbtn = (Button) findViewById(R.id.diary_home_backbtn);
        diary_home_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Diary_home.this, Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

         //일기작성 버튼 클릭.
        diary_home_editbtn = (Button) findViewById(R.id.diary_home_editbtn);
        diary_home_editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Diary_home.this, Diary_edit.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


    }





    //리사이클러뷰 추가하는 메소드
    public void addItem(String title, String time, int keynumber, Uri muri) { //Uri
        Diarydata item = new Diarydata();

        item.setDiarytext(title);
        item.setTime(time);
        item.setKeynumber(keynumber);
        item.setIconmuri(muri);

        //mList.add(item);
        mList.add(0, item);
    }



    @Override
    protected void onResume() {
        super.onResume();

        mList.clear();

        //쉐어드 파일 지정
        final SharedPreferences diary = getSharedPreferences("diary", MODE_PRIVATE);
        //숫자용 쉐어드 파일
        SharedPreferences diary_count = getSharedPreferences("diary_count", MODE_PRIVATE);

        //숫자를 불러올때
        final SharedPreferences.Editor editor1 = diary_count.edit();

        //값을 저장할 경우
        final SharedPreferences.Editor editor = diary.edit();

        final int counttext = diary_count.getInt("key1", 0);

        if (editor != null) {

            for (int i = 0; i <= counttext; i++) {

                String naam = diary.getString("key" + i, " ");

                String[] array = naam.split("#");

                if (array.length == 4) {
                    //String uri = array[0];
                    int key = Integer.parseInt(array[0]);
                    String text = array[1];
                    String time = array[2];
                    String uri = array[3];
                    //String으로 받은 uri 주소를 Uri로 변환하여 아이템에 추가 하면 될까??
                    //위에 되긴 되는데 어플을 껏다 키면 이미지가 사라져 있음...
                    //신기한건 같은 사라진 이미지와 같은 이미지를 새로 등록할 경우
                    //기존 없어졌던 이미지가 다시 생김.
                    //uri2 = Uri.parse(uri);
                    Uri uri1 = Uri.parse(uri);

                    // 아이템 추가.
                    addItem(text, time, key, uri1);

                }
            }
        }



        //리사이클러뷰 아이디 지정
        mRecyclerView = findViewById(R.id.diary_recyclerview);
        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        mAdapter = new DiaryAdapter(this, mList);
        mRecyclerView.setAdapter(mAdapter);

        // 리사이클러뷰에 LinearLayoutManager 지정. (vertical)
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        /// ... 코드 계속.

        //uri값이 null이 아닐때 이미지를 출력

        mAdapter.notifyDataSetChanged();

        DiaryAdapter adapter = new DiaryAdapter(mList);
    }





}
