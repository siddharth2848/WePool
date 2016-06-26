package com.pathania.wepool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    Intent intent;
    double lat, lng;
    private GoogleMap mMap;
    JSONArray jArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        try {
            intent = getIntent();
            String jsonArray = intent.getStringExtra("jsonArray");
            jArr = new JSONArray(jsonArray);

        } catch (Exception e) {
            Toast.makeText(MapsActivity.this, "mlat" + lat + "mlng" + lng, Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Place and move the camera

        for(int count = 0 ; count < jArr.length() ; count++){
            JSONObject obj = null;
            try {
                obj = jArr.getJSONObject(count);
                lat = Double.parseDouble(obj.getString("latitude"));
                lng = Double.parseDouble(obj.getString("longitude"));
                LatLng car = new LatLng(lat, lng);
                mMap.addMarker(new MarkerOptions().position(car));
                //mMap.setZoom(15);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(car, 15));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }
}
