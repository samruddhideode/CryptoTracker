package com.example.cryptotracker.ViewHolder;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cryptotracker.R;

public class CurrencyViewHolder extends RecyclerView.ViewHolder {
    public TextView currencyName,currencySymbol, currencyRate, oneHour, twentyfourHours, sevenDays;
    public ImageButton setAlert;
    public CurrencyViewHolder(@NonNull View itemView) {
        super(itemView);
        currencyName = itemView.findViewById(R.id.currencyName);
        currencyRate = itemView.findViewById(R.id.currencyRate);
        currencySymbol = itemView.findViewById(R.id.currencySymbol);
        oneHour = itemView.findViewById(R.id.oneHour);
        twentyfourHours = itemView.findViewById(R.id.twentyfourHours);
        sevenDays = itemView.findViewById(R.id.sevenDays);
        setAlert = (ImageButton) itemView.findViewById(R.id.setAlert);
    }

}
