package com.example.cryptotracker;

import java.util.*;
import java.text.*;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.*;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cryptotracker.Model.CurrencyModal;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Visualization extends AppCompatActivity {
    private CandleStickChart candleStickChart;
    private Button apply;
    private RadioGroup coins;
    private RadioGroup time;
    private RadioButton coin_selected;
    private RadioButton time_selected;
    Request request;
    OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualization);

        candleStickChart = findViewById(R.id.candleStick);
        coins = findViewById(R.id.coins);
        time = findViewById(R.id.time);
        apply = findViewById(R.id.apply);

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radio_coin_id = coins.getCheckedRadioButtonId();
                coin_selected = findViewById(radio_coin_id);
                int radio_time_id = time.getCheckedRadioButtonId();
                time_selected = findViewById(radio_time_id);

                String coin = coin_selected.getText().toString();
                String time = time_selected.getText().toString();
                System.out.println("COIN TIME "+coin+time);
                showCandleStickChart(coin,time);
            }
        });

    }
    private void showCandleStickChart(String coin, String time){
        final ArrayList<ArrayList<Long>> dataSet = new ArrayList<>();
        client = new OkHttpClient();
        String url = "https://api.gemini.com/v2/candles/"+ coin + "/"+ time;
        request = new Request.Builder().url(
                String.format(url)).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String jsonString = response.body().string(); //array of arrays
                try {
                    //getting json array of array response in dataSet [time, open, high, low, close, volume]
                    JSONArray itemArray = new JSONArray(jsonString);
                    for (int i = 0; i < itemArray.length(); i++) {
                        ArrayList<Long> eachCoin = new ArrayList<>();
                        for(int j=0; j<itemArray.getJSONArray(i).length(); j++){
                            eachCoin.add(itemArray.getJSONArray(i).getLong(j));
                        }
                        dataSet.add(eachCoin);
                    }

                    //styling chart layout
                    YAxis yAxis = candleStickChart.getAxisLeft();
                    YAxis rightAxis = candleStickChart.getAxisRight();
                    yAxis.setDrawGridLines(false);
                    rightAxis.setDrawGridLines(false);
                    candleStickChart.requestDisallowInterceptTouchEvent(true);

                    XAxis xAxis = candleStickChart.getXAxis();

                    xAxis.setDrawGridLines(false);// disable x axis grid lines
                    xAxis.setDrawLabels(false);
                    rightAxis.setTextColor(Color.BLACK);
                    yAxis.setDrawLabels(false);
                    xAxis.setGranularity(1f);
                    xAxis.setGranularityEnabled(true);
                    xAxis.setAvoidFirstLastClipping(true);

                    Legend l = candleStickChart.getLegend();
                    l.setEnabled(true);
                    candleStickChart.setHighlightPerDragEnabled(true);
                    candleStickChart.setDrawBorders(true);
                    candleStickChart.setBorderColor(getResources().getColor(R.color.purple_700));

                    //setting data
                    ArrayList<String> xvalue = new ArrayList<String>();
                    ArrayList<CandleEntry> yvalue = new ArrayList<CandleEntry>();
                    for (int i=0; i<dataSet.size(); i++) {
                        //code to convert unix timestamp to day date time
                        long unix_seconds = dataSet.get(i).get(0);
                        Date date = new Date(unix_seconds);
                        String[] splitted = date.toString().split("\\s+");//split by space  Wed Dec 09 09:30:00 GMT+05:30 2020

                        //dates on x axis
                        xvalue.add(splitted[2] + splitted[1] + splitted[5]);
                        //data on y axis
                        yvalue.add(new CandleEntry(i, dataSet.get(i).get(2), dataSet.get(i).get(3), dataSet.get(i).get(1), dataSet.get(i).get(4)));
                    }

                    //styling data layout
                        CandleDataSet set1 = new CandleDataSet(yvalue,"Dataset1");
                        set1.setColor(Color.rgb(80, 80, 80));
                        set1.setShadowColor(getResources().getColor(R.color.purple_200));
                        set1.setShadowWidth(0.8f);
                        set1.setDecreasingColor(getResources().getColor(R.color.purple_500));
                        set1.setDecreasingPaintStyle(Paint.Style.FILL);
                        set1.setIncreasingColor(getResources().getColor(R.color.purple_700));
                        set1.setIncreasingPaintStyle(Paint.Style.FILL);
                        set1.setNeutralColor(Color.LTGRAY);
                        set1.setDrawValues(false);

                        //creating a data object with the dataset
                        CandleData data = new CandleData(set1);

                        //set data
                        candleStickChart.setData(data);
                        candleStickChart.invalidate();

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
