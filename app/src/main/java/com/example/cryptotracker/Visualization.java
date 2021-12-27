package com.example.cryptotracker;


import android.content.Intent;
import android.os.*;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cryptotracker.Model.CurrencyModal;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Visualization extends AppCompatActivity {
    private LineChart lineChart;
    private Button homeExpButton;
    Request request;
    OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualization);

        lineChart = findViewById(R.id.lineChart);
        homeExpButton = (Button) findViewById(R.id.homeButton);

        showLineChart();

        homeExpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent explicit_home = new Intent(Visualization.this, MainActivity.class);
                startActivity(explicit_home);
            }
        });

    }

    private void showLineChart(){
        ArrayList<Entry> toReturn = new ArrayList<>();
        final ArrayList<ArrayList<Long>> dataSet = new ArrayList<>();
        client = new OkHttpClient();
        request = new Request.Builder().url(
                String.format("https://api.gemini.com/v2/candles/btcusd/1day")).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String jsonString = response.body().string(); //array of arrays
                try {
                    //getting json array of array response in dataSet [time, open, high, low, close, volume]
                    JSONArray itemArray = new JSONArray(jsonString);
                    //System.out.println("item array: "+itemArray);
                    for (int i = 0; i < itemArray.length(); i++) {
                        //System.out.println("I: "+itemArray.getJSONArray(i));
                        ArrayList<Long> eachCoin = new ArrayList<>();
                        for(int j=0; j<itemArray.getJSONArray(i).length(); j++){
                            eachCoin.add(itemArray.getJSONArray(i).getLong(j));
                        }
                        dataSet.add(eachCoin);
                    }
                    for (int arr =0; arr<dataSet.size(); arr++){
                        toReturn.add(new Entry(arr,dataSet.get(arr).get(2)));
                    }
                    //display graph
                    LineDataSet lineDataSet = new LineDataSet(toReturn, "data set");
                    System.out.println("HERE" + lineDataSet);
                    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
                    iLineDataSets.add(lineDataSet);

                    LineData lineData = new LineData(iLineDataSets);
                    lineChart.setData(lineData);
                    lineChart.invalidate();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Toast.makeText(Visualization.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
