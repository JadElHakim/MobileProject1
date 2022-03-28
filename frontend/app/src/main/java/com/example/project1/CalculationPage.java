package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CalculationPage extends AppCompatActivity {

    EditText usd_val,lbp_val;
    double usd_to_convert,lbp_to_convert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation_page);

        usd_val=findViewById(R.id.usd_val);
        lbp_val=findViewById(R.id.lbp_val);

    }

    public void onCalculate(View view){

        try {
            //getting the value stored inside the usd_val
            usd_to_convert = Double.parseDouble(usd_val.getText().toString());


        }
        catch(Exception e){

        }
        try {
            //getting the value stored inside the lbp_val
            lbp_to_convert = Double.parseDouble(lbp_val.getText().toString());
        }
        catch(Exception e){

        }

        //no values were added show toast and continue
        if ((usd_to_convert==0.0) && (lbp_to_convert==0.0)) {
            Toast toast=Toast.makeText(getApplicationContext(),"Enter A Value",Toast.LENGTH_LONG);
            toast.show();
        }
        else
            //converting from lbp to usd
        if(usd_to_convert==0.0&&lbp_to_convert!=0.0) {

        }
        else
            //converting from usd to lbp
        if(usd_to_convert!=0.0&&lbp_to_convert==0.0){

        }
            //value has been converted reset to place a value in one of the two options
        if(usd_to_convert!=0.0&&lbp_to_convert!=0.0){
            Toast toast=Toast.makeText(getApplicationContext(),"Please Press Erase",Toast.LENGTH_LONG);
            toast.show();
        }



    }

    public void onErase(View v) {

        usd_val.setText("");
        lbp_val.setText("");


    }

}

