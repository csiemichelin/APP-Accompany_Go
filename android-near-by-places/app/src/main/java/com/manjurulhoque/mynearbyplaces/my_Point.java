package com.manjurulhoque.mynearbyplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.manjurulhoque.mynearbyplaces.activity.PlaceOnMapActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class my_Point extends AppCompatActivity {
    private String result3="null";
    String point = null;
    JSONArray mJsonArray2 = null;
    int   count2 = 0;
    String[] point_array = new String[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__point);

        Thread thread = new Thread(mutiThread2); // 返回php值 and updata button and textview data
        thread.start();

        Thread t = new Thread(runnable2);
        t.start();

    }


    private Runnable runnable2 = new Runnable() {
        @Override
        public void run() {       // runOnUiThread  updata ui
            final TextView View_point =findViewById(R.id.point_2);
            final ImageView image_set = findViewById(R.id.image_set);

            try {
                Thread.sleep(500);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if ( Integer.parseInt(point) <= 1000){
                            View_point.setText("階級一: 公益種子");
                            image_set.setImageResource(R.drawable.tree1);
                        }
                        if (1000 < Integer.parseInt(point) && Integer.parseInt(point) <= 2000){
                            View_point.setText("階級二: 溫馨小樹苗");
                            image_set.setImageResource(R.drawable.tree1_2);
                        }
                        if (2000 < Integer.parseInt(point) && Integer.parseInt(point) <= 3000){
                            View_point.setText("階級三: 暖心小樹");
                            image_set.setImageResource(R.drawable.tree2);
                        }
                        if (3000 < Integer.parseInt(point)){
                            View_point.setText("階級MAX: 熱心大樹");
                            image_set.setImageResource(R.drawable.tree3);
                        }
                    }
                });

            } catch (InterruptedException e) {
            }

        }

    };






    private  Runnable mutiThread2 = new Runnable(){
        public void run()
        {                                           //post to php and get the database
            try {
                URL url = new URL("http://10.51.50.16/my_point.php");//注意:不是127.0.0.1,cmd 中ipconfig的ip   XD...
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
                    result3 = box; // 把存放用字串放到全域變數

                }



            } catch(Exception e) {

                result3 = e.toString(); // 如果出事，回傳錯誤訊息
            }
            runOnUiThread(new Runnable() {
                public void run() {
                    try {                                              //~~~~~~  jsonarray  ~~~~~
                        mJsonArray2 = new JSONArray(result3);
                        count2 = mJsonArray2.length();
                        point_array = new String[count2];



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONObject movieObject1 = null;
                    for(int i=0; i<count2; i++) {
                        try {
                            movieObject1 = mJsonArray2.getJSONObject(i);

                            point = movieObject1.getString("point");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        point_array[i]=point;
                    }

                }
            });
        }
    };
}
