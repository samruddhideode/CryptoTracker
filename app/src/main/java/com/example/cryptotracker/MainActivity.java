package com.example.cryptotracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.cryptotracker.Adapter.RVadapter;
import com.example.cryptotracker.Interface.ILoadMore;
import com.example.cryptotracker.Model.CurrencyModal;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

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

    Handler handler = new Handler();
    Runnable runnable;
    int delay = 60000;

    Request request;
    RelativeLayout relativeLayout;
    OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Explicit intent
        //Takes you to sister activity- Visualisation
        trendButton = findViewById(R.id.trendsButton);
        trendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent explicit = new Intent(MainActivity.this, Visualization.class);
                startActivity(explicit);
            }
        });

        //Loads the first 30 coins into the recycler view
        recyclerView = (RecyclerView) findViewById(R.id.RVcurrencies);
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                loadFirst30coins(1);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setupAdapter();

        //search functionality
        search_bar = findViewById(R.id.search_bar);
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

    @Override
    //refer activity lifecycle https://developer.android.com/guide/components/activities/activity-lifecycle
    protected void onResume() {
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                handler.postDelayed(runnable, delay);
                System.out.println("Every 10 secs");
                onReloadPressed();
            }
        }, delay);
        super.onResume();
    }

    @Override
    //stop handler when activity not visible (shifted to visualisation activity) super.onPause();
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    //filters the Recycler View
    //After text changed in search bar, creates a new ArrayList containing only matching Coin Names or Symbols
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

    //Code for functioning of the Reload button in the Super Menu
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.super_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_reload:
                onReloadPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onReloadPressed(){
    //reload code here
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);//Start the same Activity
        finish(); //finish Activity.
    }


    //Calls the CoinMArketCapAPI and adds data to the CurrencyModalArrayList
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