package com.manjurulhoque.mynearbyplaces;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.manjurulhoque.mynearbyplaces.directionhelpers.FetchURL;
import com.manjurulhoque.mynearbyplaces.directionhelpers.TaskLoadedCallback;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback, View.OnClickListener, LocationListener {
    GoogleMap mMap;
    MarkerOptions place1, place2;
    Button btnGetDirection;
    Button mission_start;
    Polyline currentPolyline;
    //location
    private  float longitude =123123132;
    private  float Latitude = 1231231312 ;
    private  float dlongitude = (float) 120.842534;
    private  float dLatitude = (float) 24.398190;

    private LocationManager locationManager;
    private int count = 1;
    private static final String TAG = "";

    public void Locat(float lo, float le) {
        longitude = lo;
        Latitude = le;

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        final Button call_but = findViewById(R.id.call_old_but);
        Bundle bundle = getIntent().getExtras();
        String lat = bundle.getString("lat");
        String lng = bundle.getString("lng");
        final String SD_phone_number = bundle.getString("SD_phone_number");
        final String SD_name = bundle.getString("SD_name");
        final String name = bundle.getString("name");
        final String phone_number = bundle.getString("phone_number");
        dLatitude = Float.parseFloat(lat);
        dlongitude = Float.parseFloat(lng);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);


        btnGetDirection = findViewById(R.id.btnGetDirection);
        mission_start = findViewById(R.id.btn_arrive);


        call_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // call_but.setText(phone_number);
                makeCall(phone_number);
            }
        });


        mission_start.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MapActivity.this,mission_start.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", String.valueOf(name)); //old
                bundle.putString("phone_number", String.valueOf(phone_number));
                bundle.putString("SD_name", String.valueOf(SD_name)); //young
                bundle.putString("SD_phone_number", String.valueOf(SD_phone_number));
                intent.putExtras(bundle);

                startActivity(intent);
                finish();
            }
        }));

        btnGetDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = getUrl(place1.getPosition(), place2.getPosition(),"driving");
                new FetchURL(MapActivity.this).execute(url, "driving");
            }
        });

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapNearBy);
        mapFragment.getMapAsync(this);

        //27.658143,85.3199503
        //27.667491,85.3208583

        place2 = new MarkerOptions().position(new LatLng(dLatitude, dlongitude)).title("目的地");
    }
    protected void makeCall(String phone_number) {
        Log.i("Make call", "");

        Intent phoneIntent = new Intent(Intent.ACTION_CALL);
        phoneIntent.setData(Uri.parse("tel:"+phone_number));

        try {
            startActivity(phoneIntent);
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MapActivity.this,
                    "Call faild, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        place2 = new MarkerOptions().position(new LatLng(dLatitude, dlongitude)).title("目的地");
        Log.d("mylog", "Added Markers");

        mMap.addMarker(place2);
        mMap.setMyLocationEnabled(true);
    }
    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=AIzaSyCAeZ8zmZraoTaxsm9cT8AUOviUavqhTlA"/* + getString(R.string.google_maps_key)*/;
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);

    }


    @Override
    public void onClick(View view) {


        Intent intent = new Intent();
        intent.setClass(MapActivity.this,StudentActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        Log.i(TAG,"onLocationChanged -" + location.toString());
        longitude= (float) location.getLongitude();
        Latitude= (float) location.getLatitude();
        place1 = new MarkerOptions().position(new LatLng(Latitude,longitude)).title("Location 1");
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.i(TAG,"onStatusChanged");
    }

    @Override
    public void onProviderEnabled(String s) {
        Log.i(TAG,"onProviderEnabled");
    }

    @Override
    public void onProviderDisabled(String s) {
        Log.i(TAG,"onProviderDisabled");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
            Intent intent = new Intent(MapActivity.this, StudentActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
