package com.example.glitsapp10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.Style;


public class MainActivity extends AppCompatActivity {

    private final int PERMISSION_READ_STATE = 0;
    private boolean callMap = false;
    private MapView mapView;
    private Bundle sis;
    private MapboxMap map;

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {

        sis = savedInstanceState;
        super.onCreate(sis);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_READ_STATE);
        }
        else {
            makeMap(sis);
        }

    }

    protected void makeMap(Bundle savedInstanceState){
        Mapbox.getInstance(this, getString(R.string.access_token));
        setContentView(R.layout.activity_main);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this::onMapReady);
    }

    public void onMapReady(@NonNull MapboxMap mapboxMap){
        map = mapboxMap;


        java.lang.String uri = "mapbox://styles/thodoris2000/ckwapqiiu5sau14mvu7736nci";
        Style.Builder builder =  new Style.Builder().fromUri(uri);
        mapboxMap.setStyle(builder.getUri(), new Style.OnStyleLoaded(){
            @Override public void onStyleLoaded(@NonNull Style style) {
                String worked = "this project has ruined my life, but at least I can now see where it's being ruined";
                Toast.makeText(getApplicationContext(), worked, Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_READ_STATE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callMap = true;
                    makeMap(sis);
                }
                else {
                    this.finish();
                }
                return;
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(callMap){mapView.onStart();}
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(callMap){mapView.onResume();}
    }
    

    @Override
    protected void onPause() {
        super.onPause();
        if(callMap){mapView.onPause();}
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(callMap){mapView.onStop();}
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(callMap){mapView.onSaveInstanceState(outState);}
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if(callMap){mapView.onLowMemory();}
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(callMap){mapView.onDestroy();}
    }
}