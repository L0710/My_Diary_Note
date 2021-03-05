package com.example.login_test3;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Game extends BasicActivity {

    TextView time ;
    TextView count;
    Button start;
    Button back_home2;

    ImageView[] img_array = new ImageView[9];
    int[] imageID = {R.id.chicken1, R.id.chicken2, R.id.chicken3, R.id.chicken4, R.id.chicken5, R.id.chicken6, R.id.chicken7, R.id.chicken8, R.id.chicken9};

    final String TAG_ON = "on"; //태그용
    final String TAG_OFF = "off";
    int score = 0;

    //Thread thread = null;

    BackgroundTask task;
    int value;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        time=findViewById(R.id.time);
        count=findViewById(R.id.count);
        start=findViewById(R.id.start);
        back_home2=findViewById(R.id.back_home2);
        progressBar = findViewById(R.id.progressBar);

        ActionBar actionBar = getSupportActionBar(); // getSupportActionBar()를 통해 엑션바를 받아오기.
        actionBar.hide(); // hide()를 통해서 액션바 감추기.

        for(int i = 0; i<img_array.length; i++){
            img_array[i] = (ImageView)findViewById(imageID[i]);
            img_array[i].setImageResource(R.drawable.fried_chicken);
            img_array[i].setTag(TAG_OFF);

            img_array[i].setOnClickListener(new View.OnClickListener() { //두더지이미지에 온클릭리스너
                @Override
                public void onClick(View v) {
                    if(((ImageView)v).getTag().toString().equals(TAG_ON)){
                        Toast.makeText(getApplicationContext(), "O", Toast.LENGTH_LONG).show();
                        count.setText(String.valueOf(score++));
                        ((ImageView) v).setImageResource(R.drawable.fried_chicken);
                        v.setTag(TAG_OFF);
                    }else{
                        Toast.makeText(getApplicationContext(), "X", Toast.LENGTH_LONG).show();
                        if(score<=0){
                            score=0;
                            count.setText(String.valueOf(score));
                        }else{
                            count.setText(String.valueOf(score--));
                        }
                        ((ImageView) v).setImageResource(R.drawable.fried_chicken2);
                        v.setTag(TAG_ON);
                    }
                }
            });
        }


        time.setText("20초");
        count.setText("0마리");

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                start.setVisibility(View.GONE);
                count.setVisibility(View.VISIBLE);
                back_home2.setVisibility(View.GONE);

                new Thread(new timeCheck()).start();

                for(int i = 0; i<img_array.length; i++){
                    new Thread(new DThread(i)).start();
                }

                task = new BackgroundTask();
                task.execute();

            }
        });

        back_home2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Game.this, Game_title.class);
                //finish();
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


    }



    Handler onHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            img_array[msg.arg1].setImageResource(R.drawable.fried_chicken2);
            img_array[msg.arg1].setTag(TAG_ON); //올라오면 ON태그 달아줌
        }
    };

    Handler offHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            img_array[msg.arg1].setImageResource(R.drawable.fried_chicken);
            img_array[msg.arg1].setTag(TAG_OFF); //내려오면 OFF태그 달아줌

        }
    };

    public class DThread implements Runnable{ //두더지를 올라갔다 내려갔다 해줌
        int index = 0; //두더지 번호

        DThread(int index){
            this.index=index;
        }

        @Override
        public void run() {
            while(true){
                try {
                    Message msg1 = new Message();
                    int offtime = new Random().nextInt(10000) + 500 ;
                    Thread.sleep(offtime); //두더지가 내려가있는 시간

                    msg1.arg1 = index;
                    onHandler.sendMessage(msg1);

                    int ontime = new Random().nextInt(2000)+500;
                    Thread.sleep(ontime); //두더지가 올라가있는 시간
                    Message msg2 = new Message();
                    msg2.arg1= index;
                    offHandler.sendMessage(msg2);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            time.setText(msg.arg1 + "초");
        }
    };

    public class timeCheck implements Runnable {
        final int MAXTIME = 20;

        @Override
        public void run() {
            for (int i = MAXTIME; i >= 0; i--) {
                Message msg = new Message();
                msg.arg1 = i;
                handler.sendMessage(msg);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Intent intent = new Intent(Game.this, Score.class);
            intent.putExtra("score", score);
            //intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }


    public class BackgroundTask extends AsyncTask<Integer, Integer, Integer> {
        protected void onPreExcute() {
            value = 0;
            progressBar.setProgress(value);
        }

        @Override
        protected Integer doInBackground(Integer... integers) {

            while(isCancelled() == false){
                value++;
                if(value>= 100){
                    break;
                } else{
                    publishProgress(value);
                }

                try {
                    Thread.sleep(200);
                } catch(InterruptedException ex){}
            }

            return value;
        }

        protected void onProgressUpdate(Integer ... values){
            progressBar.setProgress(values[0].intValue());
        }

        protected void onPostExecute(Integer result){
            progressBar.setProgress(0);
        }

        protected void onCancelled() {
            progressBar.setProgress(0);
        }

    }
}
