package com.example.cryptotracker;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

public class CurrencyViewHolder extends RecyclerView.ViewHolder {
    public TextView currencyName,currencySymbol, currencyRate, oneHour, twentyfourHours, sevenDays;
    public long lower_limit;
    public Button setAlert;
    public CurrencyViewHolder(@NonNull View itemView) {
        super(itemView);
        currencyName = itemView.findViewById(R.id.currencyName);
        currencyRate = itemView.findViewById(R.id.currencyRate);
        currencySymbol = itemView.findViewById(R.id.currencySymbol);
        oneHour = itemView.findViewById(R.id.oneHour);
        twentyfourHours = itemView.findViewById(R.id.twentyfourHours);
        sevenDays = itemView.findViewById(R.id.sevenDays);
        setAlert = itemView.findViewById(R.id.setAlert);
    }

}
