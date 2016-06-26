package com.pathania.wepool;


import android.content.Intent;
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

import cz.msebera.android.httpclient.Header;

public class GiveCar extends AppCompatActivity implements View.OnClickListener{

    Button button;
    Double longitude,latitude;
    EditText editText,editText1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_car);
        button = (Button) findViewById(R.id.getLocation);
        editText = (EditText) findViewById(R.id.dest);
        editText1 = (EditText) findViewById(R.id.source);
        button.setOnClickListener(this);
        Intent intent = new Intent(this, GPSTracker.class);
        startActivityForResult(intent,1);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            Bundle extras = data.getExtras();
            longitude = extras.getDouble("Longitude");
            latitude = extras.getDouble("Latitude");

            //Toast.makeText(getApplicationContext() , "lat"+latitude+" long "+longitude,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(this,"onclick",Toast.LENGTH_LONG).show();
        String dest = editText.getText().toString();
        String src = editText1.getText().toString();
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            /*
            * Bind parameters here
            * */
            RequestParams params = new RequestParams();
            try {
                params.put("source", src);
                params.put("destination", dest);
                params.put("longitude", longitude);
                params.put("latitude", latitude);

            } catch (Exception e) {
                Toast.makeText(GiveCar.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            client.post("http://wepool.roms4all.com/post_drive.php", params, new TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String res) {
                            Log.e("success" , res);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                            // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                            Toast.makeText(GiveCar.this, "" + res, Toast.LENGTH_SHORT).show();
                            Log.e("fail",res);
                        }
                    }
            );
        } catch (Exception e) {
            Log.e("catch" , e.toString());
            Toast.makeText(GiveCar.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}