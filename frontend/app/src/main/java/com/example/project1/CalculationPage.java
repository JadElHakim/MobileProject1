package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

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
        BackendExecution be = new BackendExecution();

        try {
            //getting the value stored inside the usd_val
            usd_to_convert = Double.parseDouble(usd_val.getText().toString());
            be.execute(String.valueOf(usd_to_convert),"USD");

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

        class BackendExecution extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                String currencyHolder = params[0] ;
                String AmountHolder = params[1] ;
                String login_url = "http://localhost/send_data.php";
                URL url;
                HttpURLConnection http;

                try{
                    url = new URL(login_url);
                    http = (HttpURLConnection) url.openConnection();
                    http.setRequestMethod("POST");
                    http.setDoInput(true);
                    http.setDoOutput(true);
                    OutputStream ot = http.getOutputStream();
                    BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(ot, "UTF-8"));
                    String data = URLEncoder.encode("currency", "UTF-8")+"="+URLEncoder.encode(currencyHolder, "UTF-8")+
                            "&"+URLEncoder.encode("amount", "UTF-8")+"="+URLEncoder.encode(AmountHolder,"UTF-8");
                    bf.write(data);
                    bf.flush();
                    bf.close();
                    ot.close();
                    InputStream is = http.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is,"iso-8859-1"));
                    String result= "";
                    String line = "";
                    while((line = br.readLine()) != null) {
                        result += line;
                    }
                    br.close();
                    is.close();
                    http.disconnect();
                    return result;
                }
                catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {

                super.onPostExecute(result);

                Toast.makeText(CalculationPage.this, "Conversion Complete", Toast.LENGTH_LONG).show();

            }
        }
    }



