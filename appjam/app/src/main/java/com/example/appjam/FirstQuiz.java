package com.example.appjam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class FirstQuiz extends AppCompatActivity {

    TextView title;
    TextView answer;
    member m[];

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_quiz);
        initializeView();

    }

    public void initializeView(){
        title = findViewById(R.id.first_title);
        answer = findViewById(R.id.first_answer);
        Intent intent = getIntent();
        Thread thread = new thread();
        thread.start();

    }

    public void onClick(View view) {
        try {
            title.setText(m[count].q);
            answer.setText("");
        } catch (Exception e){
            count=0;
            title.setText(m[count].q);
            answer.setText("");
        }
    }

    public void onClick1(View view) {
        answer.setText(m[count].a);
        count += 1;
    }

    public class thread extends Thread {
        public synchronized void run(){
            String urlStr = "https://seongyongshin.github.io/hahaha/index.html";

            URL url = null;
            try {
                url = new URL(urlStr);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            URLConnection connection = null;
            try {
                connection = url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            connection.setDoOutput(true);

            // 타입 설정
            connection.setRequestProperty("CONTENT-TYPE", "text/xml");

            //openStream() : URL페이지 정보를 읽어온다.
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                String quiz = "";
                String msg = null;
                int i = 0;
                while((msg = in.readLine()) != null){
                    quiz+= msg;
                    i+=1;
                }
                String[] abc;
                abc = quiz.split("<body>")[1].split("<br>");
                m =  new member[i];
                for(int k=0; k<i;k++){
                    try {
                        m[k] = new member();
                    m[k].q = abc[k].split("@")[0];
                    m[k].a = abc[k].split("@")[1];
                    Log.d("aaaaaa",m[k].q);
                    }catch (Exception e){
                        e.printStackTrace();
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

}
class member {
    public String q, a;
}