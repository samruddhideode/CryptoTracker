package com.example.cryptotracker.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cryptotracker.R;

import org.w3c.dom.Text;

public class AlertViewHolder<textView> extends RecyclerView.ViewHolder{
    public TextView price, name, limit;
    public AlertViewHolder(@NonNull View itemView) {
        super(itemView);
        price = (TextView) itemView.findViewById(R.id.price);
        name = (TextView) itemView.findViewById(R.id.name);
        limit = (TextView) itemView.findViewById(R.id.limit);
    }
}
