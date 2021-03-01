package com.manjurulhoque.mynearbyplaces.activity;

import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.manjurulhoque.mynearbyplaces.DirectionsJSONParser;
import com.manjurulhoque.mynearbyplaces.GlobalClass;
import com.manjurulhoque.mynearbyplaces.MapActivity;
import com.manjurulhoque.mynearbyplaces.R;
import com.manjurulhoque.mynearbyplaces.mission_start;
import com.manjurulhoque.mynearbyplaces.models.Location;
import com.manjurulhoque.mynearbyplaces.models.Results;
import com.manjurulhoque.mynearbyplaces.old_mission_start;
import com.manjurulhoque.mynearbyplaces.student_issue;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlaceOnMapActivity extends FragmentActivity implements OnMapReadyCallback {

    // variable
    String[] state_array = new String[10];
    private Results results;
    private LatLng pos;
    private Location location1, location2;
    private Marker marker;
    private double lat, lng;
    private GoogleMap googleMap;
    private String type;
    private String postname;
    private String posthelp;
    private String postphone_number;
    private double old_lat=0;
    private double old_lng=0;
    private int flag =0;
    private  String  result2="null";
    String state = null;
    JSONArray mJsonArray1 = null;
    int   count1 = 0;
    String  Print1  ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_on_map);

        LocationManager locationManager_old;
        int count = 1;

        //添加的
        final GlobalClass globalClass = (GlobalClass) getApplicationContext();
        final Button bt_help = findViewById(R.id.bt_help);
        final Button bt_match = findViewById(R.id.bt_match);


        bt_match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(mutiThread1); // 返回php值 and updata button and textview data
                thread.start();


                Thread t = new Thread(runnable1);
                t.start();

                if("1".equals(state_array[0])) {
                    Toast.makeText(getApplicationContext(), "任務開始了", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "請等待~~孫子還沒看到你", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bt_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                postname= globalClass.getName();
                posthelp=globalClass.getIssue();
                postphone_number=globalClass.getPhone_number();
                old_lat = globalClass.getDlatitude();
                old_lng = globalClass.getDlongitude();
                new PostDataAsyncTask().execute();
            }
        });
        //添加的

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            results = (Results) bundle.getSerializable("result");
            lat = bundle.getDouble("lat");
            lng = bundle.getDouble("lng");
            type = bundle.getString("type");
            location2 = results.getGeometry().getLocation();
            Toast.makeText(this, String.valueOf(lat), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Got Nothing!!", Toast.LENGTH_SHORT).show();
            return;
        }
    }





    private  Runnable mutiThread1 = new Runnable(){
        public void run()
        {                                           //post to php and get the database
            try {
                URL url = new URL("http://10.51.50.16/old_com.php");//注意:不是127.0.0.1,cmd 中ipconfig的ip   XD...
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
                    result2 = box; // 把存放用字串放到全域變數

                }



            } catch(Exception e) {

                result2 = e.toString(); // 如果出事，回傳錯誤訊息
            }
            runOnUiThread(new Runnable() {
                public void run() {
                    try {                                              //~~~~~~  jsonarray  ~~~~~
                        mJsonArray1 = new JSONArray(result2);
                        count1 = mJsonArray1.length();
                        state_array = new String[count1];



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONObject movieObject1 = null;
                    for(int i=0; i<count1; i++) {
                        try {
                            movieObject1 = mJsonArray1.getJSONObject(i);

                            state = movieObject1.getString("state");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        state_array[i]=state;
                    }

                }
            });
        }
    };

    private Runnable runnable1 = new Runnable() {
        @Override
        public void run() {       // runOnUiThread  updata ui
            try {

                    Thread.sleep(2000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                          if(("1".equals(state_array[0])) ){
                                Intent intent = new Intent(PlaceOnMapActivity.this, old_mission_start.class);
                                startActivity(intent);
                            }


                        }
                    });

            } catch (InterruptedException e) {
            }

        }

    };




    public class PostDataAsyncTask extends AsyncTask<String, String, String> {

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
                        HttpPost httppost = new HttpPost("http://10.51.50.16/demo.php");// ip , cmd 中 configh

                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("lat", String.valueOf(old_lat)));
                        params.add(new BasicNameValuePair("lng", String.valueOf(old_lng)));
                        params.add(new BasicNameValuePair("name", postname));
                        params.add(new BasicNameValuePair("help", posthelp));
                        params.add(new BasicNameValuePair("phone_number",postphone_number));

                        httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                        Log.d("test","OK 200");

                        HttpResponse response = httpclient.execute(httppost);

                        Log.d("test","OK 200");
                        Toast.makeText(getApplicationContext(), "你已發出請求了 :D ", Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("test","404");
                    }
                    Looper.loop();
                }
            }).start();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (type.equals("distance")) {
            showDistance();
        } else {
            showOnMap();
        }
    }

    private void showOnMap() {
        pos = new LatLng(Double.valueOf(location2.getLat()), Double.valueOf(location2.getLng()));
        //Toast.makeText(this, String.valueOf(pos), Toast.LENGTH_SHORT).show();
        //marker.remove();
        this.googleMap.addMarker(new MarkerOptions().position(pos)
                .title(results.getName())
                .snippet(results.getVicinity())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                .alpha(1f));

        this.googleMap.getUiSettings().setCompassEnabled(true);
        this.googleMap.getUiSettings().setZoomControlsEnabled(true);
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(pos)); // move the camera to the position
        this.googleMap.animateCamera(CameraUpdateFactory.zoomTo(16.5f));
    }

    private void showDistance() {
        final GlobalClass globalClass = (GlobalClass) getApplicationContext();
        LatLng destinationPosition = new LatLng(Double.valueOf(location2.getLat()), Double.valueOf(location2.getLng()));

        globalClass.setDlatitude(Double.valueOf(location2.getLat()));
        globalClass.setDlongitude(Double.valueOf(location2.getLng()));
        Double dlan = globalClass.getDlatitude();
        Double dlong = globalClass.getDlongitude();
        Log.d("HKT", "dlan" + dlan);
        Log.d("HKT", "dlong" + dlong);

        LatLng currentPosition = new LatLng(lat, lng); // user location

        // for destination
        googleMap.addMarker(new MarkerOptions().position(destinationPosition)
                .title(results.getName())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                .snippet(results.getVicinity())
                .alpha(1f))
                .showInfoWindow();

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(destinationPosition));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(destinationPosition, 13.0f));
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        // for current
        googleMap.addMarker(new MarkerOptions().position(currentPosition)
                .title("目前位置")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                .alpha(1f))
                .showInfoWindow();

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentPosition));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 13.0f));
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        String url = getDirectionsUrl(currentPosition, destinationPosition);

        FetchUrl fetchUrl = new FetchUrl();

        fetchUrl.execute(url);
        //move map camera
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentPosition));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;


        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&key=" + this.getResources().getString(R.string.google_api_key);

        // Output format
        String output = "json";

        // Building the url to the web service
        Log.d("finalUrl", "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters);

        return "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            Log.d("downloadUrl", data.toString());
            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Fetches data from url passed
    private class FetchUrl extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                Log.d("ParserTask", jsonData[0].toString());
                DirectionsJSONParser parser = new DirectionsJSONParser();
                Log.d("ParserTask", parser.toString());

                // Starts parsing data
                routes = parser.parse(jObject);
                Log.d("ParserTask", "Executing routes");
                Log.d("ParserTask", routes.toString());

            } catch (Exception e) {
                Log.d("ParserTask", e.toString());
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.RED);

                Log.d("onPostExecute", "onPostExecute lineoptions decoded");
            }

            // Drawing polyline in the Google Map for the i-th route
            if (lineOptions != null) {
                googleMap.addPolyline(lineOptions);
            } else {
                Log.d("onPostExecute", "without Polylines drawn");
            }
        }
    }
}
