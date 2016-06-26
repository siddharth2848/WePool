package com.pathania.wepool;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class FindCar extends AppCompatActivity implements View.OnClickListener {

    String source, destination;
    Button submit_btn;
    EditText src, dest;
    double srcLat, srcLng;
    double lat, lng;
    JSONArray jArr;
    private boolean res = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_car);

        src = (EditText) findViewById(R.id.source);
        dest = (EditText) findViewById(R.id.dest);
        submit_btn = (Button) findViewById(R.id.submit);
        submit_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        source = src.getText().toString();
        ArrayList<Double> latlng = getLocationFromAddress(FindCar.this,source);
        srcLat = latlng.get(0);
        srcLng = latlng.get(1);


        try {
            /*
            * Asynchttpclient libary
            * */
            AsyncHttpClient client = new AsyncHttpClient();
            /*
            * Bind Parameters
            * */
            RequestParams params = new RequestParams();
            try {
                params.put("source", source);
                params.put("longitude", srcLng);
                params.put("latitude", srcLat);


            } catch (Exception e) {
                Toast.makeText(FindCar.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            client.post("http://wepool.roms4all.com/get_ride.php", params, new TextHttpResponseHandler() {

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            Log.e("success" , responseString);
                            decodeJson(responseString);
                        }

                    }
            );
        } catch (Exception e) {
            Toast.makeText(FindCar.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        //Toast.makeText(FindCar.this, lat + " , " + lng, Toast.LENGTH_SHORT).show();

    }


    private void decodeJson(String result) {
        try {
            jArr = new JSONArray(result);
            /*for (int count = 0; count < jArr.length(); count++) {
                JSONObject obj = jArr.getJSONObject(count);

                lat = Double.parseDouble(obj.getString("latitude"));
                lng = Double.parseDouble(obj.getString("longitude"));
            }*/
            Toast.makeText(FindCar.this, "count: " + jArr.length(), Toast.LENGTH_SHORT).show();

            res = true;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (res) {
            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            intent.putExtra("jsonArray",jArr.toString());
            startActivity(intent);
        }
    }

    public ArrayList<Double> getLocationFromAddress(Context context, String strAddress) {

        ArrayList<Double> latlng = new ArrayList<>();

        Geocoder coder = new Geocoder(context);
        List<Address> address;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                Toast.makeText(FindCar.this,"after if", Toast.LENGTH_SHORT).show();

                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            latlng.add(location.getLatitude());
            latlng.add(location.getLongitude());

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return latlng;
    }

}
