package com.example.cryptotracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import java.util.Locale;

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
    private Button trendButton;
    private EditText search_bar;

    Request request;
    RelativeLayout relativeLayout;
    OkHttpClient client;

    private EditText searchBar;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trendButton = findViewById(R.id.trendsButton);
        trendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent explicit = new Intent(MainActivity.this, Visualization.class);
                startActivity(explicit);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.RVcurrencies);
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                loadFirst30coins(1);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setupAdapter();

        EditText search_bar = findViewById(R.id.search_bar);
        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void filter(String text){
        ArrayList<CurrencyModal> filteredList = new ArrayList<>();
        for (CurrencyModal item: currencyModalArrayList){
            if (item.getCurrencyName().toLowerCase().contains(text.toLowerCase()) ||
                    item.getCurrencySymbol().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
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