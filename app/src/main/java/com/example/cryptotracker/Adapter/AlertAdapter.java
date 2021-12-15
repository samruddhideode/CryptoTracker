package com.example.cryptotracker.Adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cryptotracker.Model.CurrencyModal;
import com.example.cryptotracker.ViewHolder.AlertViewHolder;
import com.example.cryptotracker.ViewHolder.CurrencyViewHolder;

import java.util.ArrayList;

public class AlertAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<ArrayList<String>> alerts;
    public AlertAdapter(ArrayList<ArrayList<String>> alerts) {
        this.alerts = alerts;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AlertModal alertModal = alerts.get(position);

        AlertViewHolder holderItem = (AlertViewHolder) holder;

        holderItem.name.setText(AlertModal.getCurrencyName());
        holderItem.limit.setText(AlertModal.getCurrencyLimit());
        holderItem.price.setText(AlertModal.getCurrencyPrice());
    }

    @Override
    public int getItemCount() {
        return alerts.size();
    }

}
