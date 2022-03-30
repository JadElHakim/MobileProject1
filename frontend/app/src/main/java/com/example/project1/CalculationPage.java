package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
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

    String current_rate_string,res;
    EditText usd_val,lbp_val;
    double usd_to_convert,lbp_to_convert,current_rate;
    String url="http://192.168.1.105:8080/project1/test.php";
    String login_url = "http://192.168.1.105:8080/project1/post_data.php";
    DownloadTask task;
    BackendExecution be;

    public class DownloadTask extends AsyncTask<String, Void, String>{

        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection http;

            try{
                url = new URL(urls[0]);
                http = (HttpURLConnection) url.openConnection();

                InputStream in = http.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while( data != -1){
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
            }catch(Exception e){
                e.printStackTrace();
                return null;
            }

            return result;
        }
        protected void onPostExecute(String s){
            current_rate_string=s;
            current_rate = Double.parseDouble(current_rate_string.replace(",",""));
            Log.i("result",""+current_rate);
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation_page);
        task=new DownloadTask();
        task.execute(url);



        usd_val=findViewById(R.id.usd_val);
        lbp_val=findViewById(R.id.lbp_val);



    }

    public void onCalculate(View view){
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
            be = new BackendExecution();
            be.execute("LBP",Double.toString(lbp_to_convert));
            usd_val.setText(res);
            lbp_to_convert = 0;
        }
        else
            //converting from usd to lbp
        if(usd_to_convert!=0.0&&lbp_to_convert==0.0){
            be = new BackendExecution();
            be.execute("USD",Double.toString(usd_to_convert));
            lbp_val.setText(res);
            usd_to_convert = 0;

        }
            //value has been converted reset to place a value in one of the two options
        if(usd_to_convert!=0.0&&lbp_to_convert!=0.0){
            Toast toast=Toast.makeText(getApplicationContext(),"Please Press Erase",Toast.LENGTH_LONG);
            toast.show();
            usd_to_convert = 0;
            lbp_to_convert=0;
        }



    }

    public void onErase(View v) {

        usd_val.setText("");
        lbp_val.setText("");


    }

        class BackendExecution extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                Log.d("here","i am here");
                String currencyHolder = params[0] ;
                String AmountHolder = params[1] ;
                String rate = current_rate_string;
                URL url;
                HttpURLConnection http;
                String result= "";

                    try{

                        url = new URL(login_url);
                        http = (HttpURLConnection) url.openConnection();
                        http.setRequestMethod("POST");
                        http.setDoOutput(true);
                        http.setDoInput(true);
                        http.setChunkedStreamingMode(0);
                        OutputStream outputStream = http.getOutputStream();
                        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                        BufferedWriter writer = new BufferedWriter(outputStreamWriter);

                       writer.write("amount="+AmountHolder+"&"+"currency="+currencyHolder+"&"+"rate="+rate);
                       // writer.write("amount=2000&currency=USD");
                        writer.flush();
                        InputStream in = http.getInputStream();
                        InputStreamReader reader = new InputStreamReader(in);
                        int data = reader.read();

                        while( data != -1){
                            char current = (char) data;
                            result += current;
                            data = reader.read();
                        }
                        return result;
                    }catch(Exception e){
                        e.printStackTrace();
                        return null;
                    }

                }
                protected void onPostExecute(String s){
                    res=s;
                    if(usd_to_convert!=0.0&&lbp_to_convert==0.0){
                        lbp_val.setText(res);
                    }
                    if(usd_to_convert==0.0&&lbp_to_convert!=0.0){
                        usd_val.setText(res);
                    }
                    Log.d("result",""+res);
                    Toast. makeText(getApplicationContext(),res,Toast. LENGTH_SHORT).show();
                }
        }
    }



