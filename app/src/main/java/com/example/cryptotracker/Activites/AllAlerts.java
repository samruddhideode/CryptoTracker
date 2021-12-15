package com.example.cryptotracker.Activites;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cryptotracker.Adapter.AlertAdapter;
import com.example.cryptotracker.DBHandler;
import com.example.cryptotracker.R;

import java.util.ArrayList;

public class AllAlerts extends AppCompatActivity {
    DBHandler myDB;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager alertLayoutManager;
    RecyclerView.Adapter alertAdapter;
    ArrayList<ArrayList<String>> alerts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_alerts);
        myDB = new DBHandler(AllAlerts.this);

        alerts = getAllAlerts();
        recyclerView = (RecyclerView) findViewById(R.id.alertRecyclerView);

        alertLayoutManager = new LinearLayoutManager(this);
        alertAdapter = new AlertAdapter(alerts);
        recyclerView.setLayoutManager(alertLayoutManager);
        recyclerView.setAdapter(alertAdapter);

    }

    public ArrayList getAllAlerts(){
        Cursor res = myDB.showAll();
        if(res.getCount()==0){
            Toast.makeText(AllAlerts.this,"No entries found", Toast.LENGTH_SHORT).show();
        }

        ArrayList<ArrayList<String>> alerts = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            ArrayList<String> arr = new ArrayList<>();
            arr.add(res.getString(1));
            arr.add(res.getString(2));
            arr.add(res.getString(3));
            alerts.add(arr);
        }

        System.out.println("All alerts: "+alerts);
        return (alerts);
    }
}
