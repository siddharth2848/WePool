package com.pathania.wepool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button find_car, give_car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        find_car = (Button) findViewById(R.id.find_car);
        give_car = (Button) findViewById(R.id.give_car);

        find_car.setOnClickListener(this);
        give_car.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.find_car:
                startActivity(new Intent(getApplicationContext(), FindCar.class));
                //finish();
                break;
            case R.id.give_car:
                startActivity(new Intent(getApplicationContext(), GiveCar.class));
                //finish();
                break;
        }
    }
}
