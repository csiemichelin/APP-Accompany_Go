package com.manjurulhoque.mynearbyplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class student_issue extends AppCompatActivity {

    String name = null;
    String help = null;
    String lat = null;
    String lng = null;
    String phone_number = null;


    int   count = 0;
    JSONArray mJsonArray = null;
    String[] Name_array = new String[10];
    String[] Help_array = new String[10];
    String[] phone_number_array = new String[10];
    String[] lat_array = new String[10];
    String[] lng_array = new String[10];
    String[] flag=new String[0];
    Button Butt[] = new Button[100]; ///~~~動態出button
    TextView Txt[] = new TextView[100]; ///~~~動態出TXT
    TextView Txt_number[] = new TextView[100]; ///~~~動態出TXT
    Button button,button1,mission_but1,my_mission;
    String result;
    String  Print  ="";
    String SD_name=null;
    String SD_phone_number=null;
    @SuppressLint("ResourceType")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_issue);

        Button button = findViewById(R.id.button);
        //Button my_mission = findViewById(R.id.my_misssion);
        Intent intent = getIntent();

        String sd_name = intent.getStringExtra("sd_Name");
        String sd_phone_number = intent.getStringExtra("sd_phone_number");//phone_number
        SD_name=sd_name;
        SD_phone_number=sd_phone_number;
        Toast.makeText(student_issue.this, "我是"+SD_phone_number, Toast.LENGTH_SHORT).show();
        LinearLayout ll = (LinearLayout)findViewById(R.id.viewObj);

        for(int i = 0 ; i<=50; i++) {

            Butt[i]=new Button(this);
            Txt[i]=new TextView(this);
            Txt_number[i]=new TextView(this);



            Butt[i].setId(i);
            Txt[i].setId(i);
            Txt_number[i].setId(i);

            Butt[i].setText("");
            Txt[i].setText("");
            Txt_number[i].setText("");

            Butt[i].setTextSize(20);
            Txt[i].setTextSize(20);
            Txt_number[i].setTextSize(20);

            Txt[i].setBackgroundColor(android.graphics.Color.parseColor("#7784F3"));
            Txt_number[i].setBackgroundColor(android.graphics.Color.parseColor("#7784F3"));
            Butt[i].setBackgroundColor(android.graphics. Color.parseColor("#E9EAFF"));


            TextPaint tp = Txt[i] .getPaint();
            TextPaint gp = Txt_number[i] .getPaint();
            TextPaint bp = Butt[i] .getPaint();
            tp.setFakeBoldText(true);
            bp.setFakeBoldText(true);
            gp.setFakeBoldText(true);

            ll.addView(Butt[i]);
            ll.addView(Txt[i]);
            ll.addView(Txt_number[i]);
            Butt[i].setClickable(true);
        }


        button.setOnClickListener(new View.OnClickListener() {                    //~~~~~~~~~~~~~~~~~~~~~~~get the php data
            @Override
            public void onClick(View view) {

                Thread thread = new Thread(mutiThread); // 返回php值 and updata button and textview data
                thread.start();

                Thread t = new Thread(runnable);
                t.start();

            }
        });

        /*my_mission.setOnClickListener(new View.OnClickListener() {  //go to ~~~
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(student_issue.this,my_mission_list.class);
                startActivity(intent);
            }
        });*/
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {       // runOnUiThread  updata ui
            try {
                while (true) {
                    Thread.sleep(500);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            for (int k = 0; k <lat_array.length; k++  ) {

                                Butt[k].setTag(k);
                                Butt[k].setOnClickListener(new Button.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        int k = (Integer) v.getTag();
                                        Intent intent = new Intent();
                                        intent.setClass(student_issue.this, MapActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("lat", String.valueOf(lat_array[k]));
                                        bundle.putString("lng", String.valueOf(lng_array[k]));
                                        bundle.putString("name", String.valueOf(Name_array[k]));
                                        bundle.putString("phone_number", String.valueOf(phone_number_array[k]));
                                        bundle.putString("SD_name", String.valueOf(SD_name));
                                        bundle.putString("SD_phone_number", String.valueOf(SD_phone_number));
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                        student_issue.this.finish();
                                        Toast.makeText(student_issue.this, "已接受願望~~~", Toast.LENGTH_SHORT).show();
                                    }

                                });

                                Butt[k].setText("老人:   "+Name_array[k]);
                                Txt[k].setText("願望:   "+Help_array[k]);
                                Txt_number[k].setText("電話:   "+phone_number_array[k]);
                            }
                        }
                    });
                }
            } catch (InterruptedException e) {
            }
            Toast.makeText(student_issue.this, "已刷新,點擊追蹤願望", Toast.LENGTH_SHORT).show();
        }

    };

    private  Runnable mutiThread = new Runnable(){
        public void run()
        {                                           //post to php and get the database
            try {
                URL url = new URL("http://10.51.50.16/print_the_data.php");//注意:不是127.0.0.1,cmd 中ipconfig的ip   XD...
                // 開始宣告 HTTP 連線需要的物件，這邊通常都是一綑的
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                // 建立 Google 比較挺的 HttpURLConnection 物件
                connection.setRequestMethod("POST");
                // 設定連線方式為 POST
                connection.setDoOutput(true); // 允許輸出
                connection.setDoInput(true); // 允許讀入
                connection.setUseCaches(false); // 不使用快取
                connection.connect(); // 開始連線

                int responseCode =
                        connection.getResponseCode();
                // 建立取得回應的物件
                if(responseCode ==
                        HttpURLConnection.HTTP_OK){
                    // 如果 HTTP 回傳狀態是 OK ，而不是 Error
                    InputStream inputStream =
                            connection.getInputStream();
                    // 取得輸入串流
                    BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                    // 讀取輸入串流的資料
                    String box = ""; // 宣告存放用字串
                    String line = null; // 宣告讀取用的字串
                    while((line = bufReader.readLine()) != null) {
                        box += line + "\n";
                        // 每當讀取出一列，就加到存放字串後面
                    }
                    inputStream.close(); // 關閉輸入串流
                    result = box; // 把存放用字串放到全域變數

                }
                // 讀取輸入串流並存到字串的部分
                // 取得資料後想用不同的格式
                // 例如 Json 等等，都是在這一段做處理

            } catch(Exception e) {

                result = e.toString(); // 如果出事，回傳錯誤訊息
            }

            // 當這個執行緒完全跑完後執行

            runOnUiThread(new Runnable() {
                public void run() {
                    try {                                              //~~~~~~  jsonarray  ~~~~~
                        mJsonArray = new JSONArray(result);
                        count = mJsonArray.length();
                        Name_array = new String[count];
                        Help_array = new String[count];
                        lat_array = new String[count];
                        lng_array = new String[count];
                        phone_number_array = new String[count];

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONObject movieObject = null;
                    for(int i=0; i<count; i++) {
                        try {
                            movieObject = mJsonArray.getJSONObject(i);
                            name = movieObject.getString("name");
                            help = movieObject.getString("help");
                            lat = movieObject.getString("lat");
                            lng = movieObject.getString("lng");
                            phone_number = movieObject.getString("phone_number");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Name_array[i]=name;
                        Help_array[i]=help;
                        lat_array[i]=lat;
                        lng_array[i]=lng;
                        phone_number_array[i]=phone_number;
                    }

                    for (int j = 0; j < Name_array.length; j++) {
                        Print +="老人  :    " + Name_array[j] + "            " + " 請求  :   " + Help_array[j] +"            " +"\n"+
                                "lat  :    " + lat_array[j] + "            " + " lng  :   " + lng_array[j]   +  "\n"+"\n";
                    }

                    //textView.setText(Print); // 更改顯示文字

                    Print=""; //init

                }
            });
        }
    };
}
