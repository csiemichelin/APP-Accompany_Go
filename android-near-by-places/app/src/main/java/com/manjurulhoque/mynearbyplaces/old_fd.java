package com.manjurulhoque.mynearbyplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextPaint;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class old_fd extends AppCompatActivity {

    String name = null;
    String sd_fd_name = null;
    String sd_fd_phone_num = null;
    int count_fd = 0;
    JSONArray mJsonArray_fd = null;
    String[] sd_fd_name_array = new String[10];
    String[] sd_fd_phone_num_array = new String[10];
    String result_fd;
    String getResponseString = "";
    Button Butt[] = new Button[100]; ///~~~動態出button
    TextView Txt[] = new TextView[100]; ///~~~動態出TXT


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_fd);


        Intent intent = getIntent();
        String Name = intent.getStringExtra("Name");
        name = Name;


        LinearLayout ll = (LinearLayout) findViewById(R.id.viewObj);
        for (int i = 0; i <= 50; i++) {

            Butt[i] = new Button(this);
            Txt[i] = new TextView(this);


            Butt[i].setId(i);
            Txt[i].setId(i);


            Butt[i].setText("");
            Txt[i].setText("");


            Butt[i].setTextSize(20);
            Txt[i].setTextSize(20);


            Txt[i].setBackgroundColor(android.graphics.Color.parseColor("#8C8557"));

            Butt[i].setBackgroundColor(android.graphics.Color.parseColor("#FFFFF4E3"));


            TextPaint tp = Txt[i].getPaint();
            TextPaint bp = Butt[i].getPaint();
            tp.setFakeBoldText(true);
            bp.setFakeBoldText(true);

            ll.addView(Butt[i]);
            ll.addView(Txt[i]);

            Butt[i].setClickable(true);

        }

        new PostDataAsyncTask22().execute();
        Thread t = new Thread(runnable_fd);
        t.start();
    }

    private Runnable runnable_fd = new Runnable() {

        @Override
        public void run() {       // runOnUiThread  updata ui
            try {
                while (true) {
                    Thread.sleep(500);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            if(sd_fd_name_array[0]==null){
                                Butt[0].setText("你根本沒有朋友....");
                            }

                            else {
                                for (int k = 0; k < sd_fd_name_array.length; k++) {

                                    Butt[k].setTag(k);


                                    Butt[k].setText("孫子:   " + sd_fd_name_array[k]);
                                    Txt[k].setText("電話:   " + sd_fd_phone_num_array[k]);

                                }

                            }
                        }
                    });
                }
            } catch (InterruptedException e) {
            }

        }

    };






    public class PostDataAsyncTask22 extends AsyncTask<String, String, String> {

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

                        String postURL = "http://10.51.50.16/old_fd.php";

                        HttpPost post = new HttpPost(postURL);

                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("name", name));

                        UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, "UTF-8");
                        post.setEntity(ent);

                        HttpClient client = new DefaultHttpClient();
                        HttpResponse responsePOST = client.execute(post);

                        BufferedReader reader = new BufferedReader(new InputStreamReader(responsePOST.getEntity().getContent()), 2048);

                        if (responsePOST != null) {
                            StringBuilder sb = new StringBuilder();
                            String line;
                            while ((line = reader.readLine()) != null) {
                                System.out.println(" line : " + line);
                                sb.append(line);
                            }

                            getResponseString = sb.toString();
                            //   Toast.makeText(getApplicationContext(), getResponseString , Toast.LENGTH_SHORT).show();


                            try {                                              //~~~~~~  jsonarray  ~~~~~
                                mJsonArray_fd = new JSONArray(getResponseString);
                                count_fd = mJsonArray_fd.length();
                                sd_fd_name_array = new String[count_fd];
                                sd_fd_phone_num_array = new String[count_fd];


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            JSONObject movieObject = null;
                            for(int i=0; i<count_fd; i++) {
                                try {
                                    movieObject = mJsonArray_fd.getJSONObject(i);
                                    sd_fd_name = movieObject.getString("SD_name");
                                    sd_fd_phone_num = movieObject.getString("SD_num");

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(getApplicationContext(), sd_fd_phone_num , Toast.LENGTH_SHORT).show();
                                sd_fd_name_array[i]=sd_fd_name;
                                sd_fd_phone_num_array[i]=sd_fd_phone_num;
                            }


                        }


                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                    Looper.loop();
                }
            }).start();




        }
    }


}
