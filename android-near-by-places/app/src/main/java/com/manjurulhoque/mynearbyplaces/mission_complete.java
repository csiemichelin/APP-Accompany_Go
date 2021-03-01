package com.manjurulhoque.mynearbyplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.util.ArrayList;
import java.util.List;

public class mission_complete extends AppCompatActivity {

int  point = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_complete);
        ImageButton gg1=findViewById(R.id.GG1_text);
        ImageButton gg2=findViewById(R.id.GG2_text);
        ImageButton gg3=findViewById(R.id.GG3_text);
        ImageButton gg5=findViewById(R.id.GG5_text);
        ImageButton gg6=findViewById(R.id.GG6_text);

        gg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                point = 100;
            }

        });


        gg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                point =200;
            }
        });


        gg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                point =500;
            }
        });

        gg5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                point +=0;
                new PostDataAsyncTask2().execute();
                Intent intent=new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(mission_complete.this,select_issue.class);
                startActivity(intent);
            }
        });

        gg6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                point +=300;
                new PostDataAsyncTask2().execute();
                Intent intent=new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(mission_complete.this,select_issue.class);
                startActivity(intent);
            }
        });



    }


    public class PostDataAsyncTask2 extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            // do stuff before posting data
            new Thread(new Runnable() {

                @Override
                public void run() {
                    Looper.prepare();
                    // TODO Auto-generated method stub

                    try {

                        HttpClient httpclient = new DefaultHttpClient();
                        HttpPost httppost = new HttpPost("http://10.51.50.16/point.php");// ip , cmd 中 configh

                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("point", String.valueOf(point)));


                        httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                        Log.d("test","OK 200");

                        HttpResponse response = httpclient.execute(httppost);

                        Log.d("test","OK 200");
                        Toast.makeText(getApplicationContext(), "完成 :D ", Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("test","404");
                    }
                    Looper.loop();
                }
            }).start();
        }
    }

}
