package com.example.project1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
import java.util.HashMap;
import java.util.Map;


public class CalculationPage extends AppCompatActivity {

    String current_rate_string, res;
    EditText usd_val, lbp_val;
    double usd_to_convert, lbp_to_convert, current_rate;
    String url = "http://192.168.1.117/project1/test.php";
    String post_url = "http://192.168.1.117/project1/post_data.php";
    DownloadTask task;
    RequestQueue requestQueue;
    Button calculate_button;
    int currency = 0;


    public class DownloadTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection http;

            try {
                url = new URL(urls[0]);
                http = (HttpURLConnection) url.openConnection();

                InputStream in = http.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            return result;
        }

        protected void onPostExecute(String s) {
            current_rate_string = s;
            current_rate = Double.parseDouble(current_rate_string.replace(",", ""));
            Log.i("result", "" + current_rate);
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation_page);
        task = new DownloadTask();
        task.execute(url);
        usd_val = findViewById(R.id.usd_val);
        lbp_val = findViewById(R.id.lbp_val);
        calculate_button = (Button) findViewById(R.id.calculate_button);
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        calculate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currency = 0;
                try {
                    //getting the value stored inside the usd_val
                    usd_to_convert = Double.parseDouble(usd_val.getText().toString());

                } catch (Exception e) {

                }
                try {
                    //getting the value stored inside the lbp_val
                    lbp_to_convert = Double.parseDouble(lbp_val.getText().toString());
                } catch (Exception e) {

                }
                //no values were added show toast and continue
                if ((usd_to_convert == 0.0) && (lbp_to_convert == 0.0)) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Enter A Value", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                } else
                    //converting from lbp to usd
                    if (usd_to_convert == 0.0 && lbp_to_convert != 0.0) {
                        currency = 1;
                        usd_val.setText("" + lbp_to_convert / current_rate);
                        lbp_to_convert = 0;
                    } else
                        //converting from usd to lbp
                        if (usd_to_convert != 0.0 && lbp_to_convert == 0.0) {
                            currency = 2;
                            lbp_val.setText("" + usd_to_convert * current_rate);
                            usd_to_convert = 0;

                        }
                //value has been converted reset to place a value in one of the two options
                if (usd_to_convert != 0.0 && lbp_to_convert != 0.0) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please Press Erase", Toast.LENGTH_LONG);
                    toast.show();
                    usd_to_convert = 0;
                    lbp_to_convert = 0;
                    return;

                }


                if (currency != 0) {

                    StringRequest request = new StringRequest(Request.Method.POST, post_url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();
                            if (currency == 1) {
                                parameters.put("currency", "LBP");
                                parameters.put("amount", lbp_val.getText().toString());
                                parameters.put("rate", current_rate_string);
                            } else if (currency == 2) {
                                parameters.put("currency", "USD");
                                parameters.put("amount", usd_val.getText().toString());
                                parameters.put("rate", current_rate_string);
                            }
                            return parameters;
                        }
                    };

                }
            }
        });
    }

    public void onErase(View v) {

        usd_val.setText("");
        lbp_val.setText("");


    }

//        class BackendExecution extends AsyncTask<String, Void, String> {
//            @Override
//            protected String doInBackground(String... params) {
//                Log.i("here","i am here");
//                String currencyHolder = params[0] ;
//                String AmountHolder = params[1] ;
//                String rate = current_rate_string;
//                URL url;
//                HttpURLConnection http;
//                String result= "";
//
//                    try{
//
//                        url = new URL(login_url);
//                        http = (HttpURLConnection) url.openConnection();
//                        http.setRequestMethod("POST");
//                        http.setDoOutput(true);
//                        http.setDoInput(true);
//                        http.setChunkedStreamingMode(0);
//                        OutputStream outputStream = http.getOutputStream();
//                        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
//                        BufferedWriter writer = new BufferedWriter(outputStreamWriter);
//
//                       writer.write("amount="+AmountHolder+"&"+"currency="+currencyHolder+"&"+"rate="+rate);
//                       // writer.write("amount=2000&currency=USD");
//                        writer.flush();
//                        InputStream in = http.getInputStream();
//                        InputStreamReader reader = new InputStreamReader(in);
//                        int data = reader.read();
//
//                        while( data != -1){
//                            char current = (char) data;
//                            result += current;
//                            data = reader.read();
//                        }
//                        return result;
//                    }catch(Exception e){
//                        e.printStackTrace();
//                        return null;
//                    }
//
//                }
//                protected void onPostExecute(String s){
//                    res=s;
//                    if(usd_to_convert!=0.0&&lbp_to_convert==0.0){
//                        lbp_val.setText(res);
//                    }
//                    if(usd_to_convert==0.0&&lbp_to_convert!=0.0){
//                        usd_val.setText(res);
//                    }
//                    Log.d("result",""+res);
//                    Toast. makeText(getApplicationContext(),res,Toast. LENGTH_SHORT).show();
//                }
//        }

}



