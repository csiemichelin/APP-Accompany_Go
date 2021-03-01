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
import android.widget.TextView;
import android.widget.Toast;

import com.manjurulhoque.mynearbyplaces.activity.PlaceOnMapActivity;

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

public class mission_start extends AppCompatActivity {
int state = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_start);
        new PostDataAsyncTask1().execute();

        ImageButton bt_complete = findViewById(R.id.bt_complete);

        bt_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mission_start.this,my_Point.class);
                startActivity(intent);
                finish();
            }
        });

    }


    public class PostDataAsyncTask1 extends AsyncTask<String, String, String> {
        Bundle bundle = getIntent().getExtras();
        final String phone_number = bundle.getString("phone_number");
        final String name = bundle.getString("name");
        final String SD_name = bundle.getString("SD_name");
        final String SD_phone_number = bundle.getString("SD_phone_number");
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
                        HttpPost httppost = new HttpPost("http://10.51.50.16/com.php");// ip , cmd 中 configh

                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("state", String.valueOf(state)));
                        params.add(new BasicNameValuePair("name", String.valueOf(name)));
                        params.add(new BasicNameValuePair("SD_name", String.valueOf(SD_name)));
                        params.add(new BasicNameValuePair("SD_phone_number", String.valueOf(SD_phone_number)));
                        params.add(new BasicNameValuePair("phone_number", String.valueOf(phone_number)));

                        httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));


                        HttpResponse response = httpclient.execute(httppost);


                        Toast.makeText(getApplicationContext(), "任務開始 :D ", Toast.LENGTH_SHORT).show();

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
