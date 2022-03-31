//-----------------------------------------------------------------------------------------------------------------------//
//Authors: Abou Chedid Alexander, El Hakim Jad
//Project Description: currency converter from USD-->LBP or LBP-->USD using live rates fetched from an api
//Instructions: instructions on how to run the code can be found either commented or inside the ReadMe file of the Project
//Project Link https://github.com/JadElHakim/MobileProject1
//-------------------------------------------------------------------------------------------------------------------//
package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onEnter(View v){
        //redirect to Currency Converting page
        Intent intent = new Intent(this, CalculationPage.class);
        startActivity(intent);
    }
}