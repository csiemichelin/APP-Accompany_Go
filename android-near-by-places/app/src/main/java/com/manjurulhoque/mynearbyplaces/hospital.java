package com.manjurulhoque.mynearbyplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
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

public class hospital extends AppCompatActivity {

    String name = null;
    String address = null;
    String phone = null;

    int   count = 0;
    JSONArray mJsonArray = null;
    String[] Name_array = new String[500];
    String[] Address_array = new String[500];
    String[] Phone_array = new String[500];
    String[] flag=new String[0];
    //Button Butt[] = new Button[100]; ///~~~動態出button
    TextView Txt1[] = new TextView[1000];///~~~動態出TXT
    TextView Txt2[] = new TextView[1000];
    TextView Txt3[] = new TextView[1000];
    TextView Txt4[] = new TextView[1000];
    Button button,button1,mission_but1,my_mission;
    String result;
    String  Print  ="";
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);

        Intent intent = getIntent();

        //getSupportActionBar().hide();


        //Button my_mission = findViewById(R.id.my_misssion);
        LinearLayout ll = (LinearLayout)findViewById(R.id.viewObj);
        //LinearLayout l2 = (LinearLayout)findViewById(R.id.viewObj);
        //LinearLayout l3 = (LinearLayout)findViewById(R.id.viewObj);

        for(int i = 1 ; i<=500; i++) {

            //Butt[i]=new Button(this);
            Txt1[i]=new TextView(this);
            Txt2[i]=new TextView(this);
            Txt3[i]=new TextView(this);
            Txt4[i]=new TextView(this);


            //Butt[i].setId(i);
            Txt1[i].setId(i);
            Txt2[i].setId(i);
            Txt3[i].setId(i);
            Txt4[i].setId(i);

            //Butt[i].setText("");
            Txt1[i].setText("");
            Txt2[i].setText("");
            Txt3[i].setText("");
            Txt4[i].setText("");

            //Butt[i].setTextSize(20);
            Txt1[i].setTextSize(20);
            Txt2[i].setTextSize(20);
            Txt3[i].setTextSize(20);
            Txt4[i].setTextSize(5);


            Txt1[i].setBackgroundColor(android.graphics.Color.parseColor("#FFFFF4E3"));
            Txt2[i].setBackgroundColor(android.graphics.Color.parseColor("#FFFFF4E3"));
            Txt3[i].setBackgroundColor(android.graphics.Color.parseColor("#FFFFF4E3"));
            Txt4[i].setBackgroundColor(android.graphics.Color.parseColor("#8C8557"));
            //Butt[i].setBackgroundColor(android.graphics. Color.parseColor("#E9EAFF"));


            TextPaint tp1 = Txt1[i] .getPaint();
            TextPaint tp2 = Txt2[i] .getPaint();
            TextPaint tp3 = Txt3[i] .getPaint();
            TextPaint tp4 = Txt3[i] .getPaint();
            // TextPaint bp = Butt[i] .getPaint();
            tp1.setFakeBoldText(true);
            tp2.setFakeBoldText(true);
            tp3.setFakeBoldText(true);
            tp4.setFakeBoldText(true);
            //bp.setFakeBoldText(true);

            //ll.addView(Butt[i]);
            ll.addView(Txt1[i]);
            ll.addView(Txt2[i]);
            ll.addView(Txt3[i]);
            ll.addView(Txt4[i]);

            //Toast.makeText(getApplicationContext(),result, Toast.LENGTH_SHORT).show();
            //Butt[i].setClickable(true);
        }

        Thread thread = new Thread(mutiThread); // 返回php值 and updata button and textview data
        thread.start();
        Thread t = new Thread(runnable);
        t.start();

        /*button1.setOnClickListener(new View.OnClickListener() {              //~~~~~~~~~~~~~~~~~~~~~~~~~ go back botton
            @Override
            public void onClick(View view) {    // go to Main2Activity
                Intent intent=new Intent(student_issue.this,StudentActivity.class);
                startActivity(intent);
            }
        });*/

        /*button.setOnClickListener(new View.OnClickListener() {                    //~~~~~~~~~~~~~~~~~~~~~~~get the php data
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(mutiThread); // 返回php值 and updata button and textview data
                thread.start();
                Thread t = new Thread(runnable);
                t.start();

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

                            for (int k = 1; k <Name_array.length; k++  ) {
                                Txt1[k].setText("名字:   "+Name_array[k]);
                                Txt2[k].setText("地址:   "+Address_array[k]);
                                Txt3[k].setText("電話:   "+Phone_array[k]);

                            }
                            //Toast.makeText(getApplicationContext(),result, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } catch (InterruptedException e) {
            }
        }

    };

    private  Runnable mutiThread = new Runnable(){
        public void run()
        {                                           //post to php and get the database
            try {
                URL url = new URL("http://10.51.50.16/hospital.php");//注意:不是127.0.0.1,cmd 中ipconfig的ip   XD...
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
                    System.out.println(result);

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
                        Address_array = new String[count];
                        Phone_array = new String[count];

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONObject movieObject = null;
                    for(int i=1; i<count; i++) {
                        try {
                            movieObject = mJsonArray.getJSONObject(i);
                            name = movieObject.getString("name");
                            address = movieObject.getString("address");
                            phone = movieObject.getString("phone");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Name_array[i]=name;
                        Address_array[i]=address;
                        Phone_array[i]=phone;

                    }

                    /*for (int j = 0; j < Name_array.length; j++) {
                        Print +="名字  :    " + Name_array[j] + "            " + " 地址  :   " + Address_array[j] +"            " +"\n"+
                                "電話  :    " + Phone_array[j] + "            " + "\n"+"\n";
                    }*/

                    //textView.setText(Print); // 更改顯示文字

                    Print=""; //init
                    System.out.print(name);

                }
            });
        }
    };
}
