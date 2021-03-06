package com.example.cryptotracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.cryptotracker.Adapter.RVadapter;
import com.example.cryptotracker.Interface.ILoadMore;
import com.example.cryptotracker.Model.CurrencyModal;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

class Data{
    ArrayList<CurrencyModal> data;
}
public class MainActivity extends AppCompatActivity {

    private ArrayList<CurrencyModal> currencyModalArrayList = new ArrayList<>();
    private RVadapter adapter;
    private RecyclerView recyclerView;
    private Button notifButton;

    Request request;
    RelativeLayout relativeLayout;
    OkHttpClient client;

    private EditText searchBar;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.RVcurrencies);
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                loadFirst30coins(1);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setupAdapter();
        //notification
        notifButton = (Button) findViewById(R.id.notifButton);
        notifButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("hi");
            }
        });
    }

    private void setupAdapter(){
         adapter = new RVadapter(recyclerView, currencyModalArrayList,MainActivity.this);
         recyclerView.setAdapter(adapter);
         //25min
         adapter.setIloadMore(new ILoadMore() {
             @Override
             public void onLoadMore() {
                 if(currencyModalArrayList.size()<=1000){
//                     loadNext30coins(currencyModalArrayList.size());
                 }
                 else{
                     Toast.makeText(MainActivity.this, "Max items is 1000", Toast.LENGTH_SHORT).show();
                 }
             }
         });
    }

    private void loadFirst30coins(int index){
        client = new OkHttpClient();
        request = new Request.Builder().url(
                String.format("https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?start=%d&limit=30", index))
                .header("X-CMC_PRO_API_KEY", "c87b3b35-e0c3-412d-8c34-a641e9983e9c").build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String body = response.body().string();
                Gson gson = new Gson();
                Data data1 = gson.fromJson(body, Data.class);
                final ArrayList<CurrencyModal> newCurrencyModalArrayList = data1.data;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        currencyModalArrayList.addAll(newCurrencyModalArrayList);
                        adapter.setLoaded();
                        adapter.updateData(newCurrencyModalArrayList);
                    }
                });
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}