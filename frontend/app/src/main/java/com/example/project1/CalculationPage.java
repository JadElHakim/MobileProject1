//-----------------------------------------------------------------------------------------------------------------------//
//Authors: Abou Chedid Alexander, El Hakim Jad
//Project Description: currency converter from USD-->LBP or LBP-->USD using live rates fetched from an api
//Instructions: instructions on how to run the code can be found either commented or inside the ReadMe file of the Project
//Project Link https://github.com/JadElHakim/MobileProject1
//Code referenced: https://stackoverflow.com/questions/2323617/android-httppost-how-to-get-the-result
//snippet used by Ali Khaki, Android Application Developer.
//-------------------------------------------------------------------------------------------------------------------//
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

import com.android.volley.RequestQueue;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class CalculationPage extends AppCompatActivity {
//------------------------------------------------------------------------//
//----------------------initializing variables---------------------------//

    //string value for rate that will be fetched
    String current_rate_string;
    //storing inputs of the 2 EditTexts
    EditText usd_val, lbp_val;
    //values to be stored as doubles then sent to the post api for calculation
    double usd_to_convert, lbp_to_convert, current_rate;
    //url for fetching the current rate
    String url = "http://192.168.1.105:8080/project1/test.php";
    //url for posting conversion result
    String post_url = "http://192.168.1.105:8080/project1/post_data.php";
    //fetching rate
    DownloadTask task;
    //posting conversion
    RequestQueue requestQueue;
    //convert button
    Button calculate_button;
    //the currency is initially set to 0
    //--> 1 indicates its a LBP conversion
    //--> 2 indicates its a USD conversion
    int currency = 0;
//----------------------------------------------------------------------//

    private void sendPostRequest() {

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(post_url);
                //instantialize BasicNamePairs to null
                //-->they will be assigned depending on the currency to be converted
                //basic name pair maps the strings to a key
                BasicNameValuePair currency_pair = null;
                BasicNameValuePair amount_pair = null;
                BasicNameValuePair rate_pair = null;
                if (currency == 1) {
                    //conversion to LBP
                    currency_pair = new BasicNameValuePair("currency", "LBP");
                    amount_pair = new BasicNameValuePair("amount", lbp_val.getText().toString());
                    rate_pair = new BasicNameValuePair("rate", current_rate_string);
                } else if (currency == 2) {
                    //conversion to USD
                    currency_pair = new BasicNameValuePair("currency", "USD");
                    amount_pair = new BasicNameValuePair("amount", usd_val.getText().toString());
                    rate_pair = new BasicNameValuePair("rate", current_rate_string);
                }
                //adding the content that we want to pass with the POST request to as name-value pairs
                List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
                nameValuePairList.add(currency_pair);
                nameValuePairList.add(amount_pair);
                nameValuePairList.add(rate_pair);

                try {
                    UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList);
                    httpPost.setEntity(urlEncodedFormEntity);

                    try {
                        HttpResponse httpResponse = httpClient.execute(httpPost);

                        InputStream inputStream = httpResponse.getEntity().getContent();

                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                        StringBuilder stringBuilder = new StringBuilder();

                        String bufferedStrChunk = null;

                        while ((bufferedStrChunk = bufferedReader.readLine()) != null) {

                            stringBuilder.append(bufferedStrChunk);

                        }
                        return stringBuilder.toString();

                    } catch (ClientProtocolException cpe) {

                    } catch (IOException ioe) {
                    }

                } catch (Exception e) {

                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                //initialize jsonObject to null
                //-->set depending on currency to be converted
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(result);
                } catch (Exception e) {
                    Log.i("error", e.toString());
                }
                String fetched_value = null;
                try {
                    fetched_value = jsonObject.getString("result");
                } catch (Exception e) {
                }
                if (currency == 1) {
                    //converting to lbp
                    Log.i("to lbp", fetched_value + " " + currency);
                    usd_to_convert = Double.parseDouble(fetched_value);
                    usd_val.setText("" + usd_to_convert);

                } else if (currency == 2) {
                    //converting to usd
                    Log.i("to usd", fetched_value + " " + currency);
                    lbp_to_convert = Double.parseDouble(fetched_value);
                    lbp_val.setText("" + lbp_to_convert);

                }

            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute();
    }


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

        //on click listener because i read online that it is better than referring to an onclick function
        calculate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //initially currency=0 neither USD nor LBP
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
                        lbp_to_convert = 0;
                        usd_to_convert = 0;


                    } else
                        //converting from usd to lbp
                        if (usd_to_convert != 0.0 && lbp_to_convert == 0.0) {
                            currency = 2;
                            //reset values
                            lbp_to_convert = 0;
                            usd_to_convert = 0;

                        }
                //value has been converted reset to place a value in one of the two options
                if (usd_to_convert != 0.0 && lbp_to_convert != 0.0) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please Press Erase", Toast.LENGTH_LONG);
                    toast.show();
                    //reset values
                    lbp_to_convert = 0;
                    usd_to_convert = 0;

                    return;

                }
                if (currency != 0) {
                    //user wants to convert
                    sendPostRequest();
                }
            }
        });
    }

    public void onErase(View v) {
        //reset texts
        usd_val.setText("");
        lbp_val.setText("");
    }
}