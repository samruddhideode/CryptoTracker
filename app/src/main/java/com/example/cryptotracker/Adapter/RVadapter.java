package com.example.cryptotracker.Adapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cryptotracker.CurrencyViewHolder;
import com.example.cryptotracker.Interface.ILoadMore;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.*;
import java.lang.*;
import androidx.annotation.NonNull;

import com.example.cryptotracker.Model.CurrencyModal;
import com.example.cryptotracker.R;

import java.util.ArrayList;

//set data from json to each item of the recycler view
public class RVadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

//    RecyclerView recyclerView;
    ArrayList<CurrencyModal> currencyModalArrayList;
    Activity activity;
    ILoadMore iloadMore;
    boolean isLoading;

    int visibleThreshold = 5, lastVisibleItem, totalItemCount;

    public void setIloadMore(ILoadMore iloadMore) {
        this.iloadMore = iloadMore;
    }

    public RVadapter(RecyclerView recyclerView, ArrayList<CurrencyModal> currencyModalArrayList, Activity activity) {
        this.currencyModalArrayList = currencyModalArrayList;
        this.activity = activity;
        //System.out.println("hi:"+currencyModalArrayList); // null

        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if(!isLoading && totalItemCount <= (lastVisibleItem+ visibleThreshold)){
                    if(iloadMore!=null)
                        iloadMore.onLoadMore();
                    isLoading = true;
                }
            }
        });


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(
                R.layout.currencyitem, parent,false
        );
        return new CurrencyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CurrencyModal currencyModal = currencyModalArrayList.get(position);

        CurrencyViewHolder holderItem = (CurrencyViewHolder) holder;

        holderItem.currencyName.setText(currencyModal.getCurrencyName());
        holderItem.currencySymbol.setText(currencyModal.getCurrencySymbol());
        holderItem.currencyRate.setText("$ "+String.valueOf(currencyModal.getQuote().getUSD().getPrice()));
        holderItem.oneHour.setText(String.valueOf(currencyModal.getQuote().getUSD().getChange1h())+" %");
        holderItem.twentyfourHours.setText(String.valueOf(currencyModal.getQuote().getUSD().getChange24h())+" %");
        holderItem.sevenDays.setText(String.valueOf(currencyModal.getQuote().getUSD().getChange7d())+" %");

        holderItem.oneHour.setTextColor(String.valueOf(currencyModal.getQuote().getUSD().getChange1h()).contains("-")?
                Color.parseColor("#FF0000"):Color.parseColor("#32CD32"));
        holderItem.twentyfourHours.setTextColor(String.valueOf(currencyModal.getQuote().getUSD().getChange24h()).contains("-")?
                Color.parseColor("#FF0000"):Color.parseColor("#32CD32"));
        holderItem.sevenDays.setTextColor(String.valueOf(currencyModal.getQuote().getUSD().getChange7d()).contains("-")?
                Color.parseColor("#FF0000"):Color.parseColor("#32CD32"));
    }

    @Override
    public int getItemCount() {
        return currencyModalArrayList.size();
    }

    public void setLoaded(){isLoading=true;}

    public void updateData(ArrayList<CurrencyModal> currencyModals)
    {
        this.currencyModalArrayList = currencyModals;
        notifyDataSetChanged();
    }

    public void filterList(ArrayList<CurrencyModal> filteredList){
        this.currencyModalArrayList = filteredList;
        notifyDataSetChanged();
    }

}
