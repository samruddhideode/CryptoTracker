package com.example.cryptotracker.Activites;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cryptotracker.Adapter.AlertAdapter;
import com.example.cryptotracker.DBHandler;
import com.example.cryptotracker.Model.AlertModal;
import com.example.cryptotracker.R;

import java.util.ArrayList;

public class AllAlerts extends AppCompatActivity {
    DBHandler myDB;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager alertLayoutManager;
    RecyclerView.Adapter alertAdapter;
    ArrayList<AlertModal> alerts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_alerts);
        myDB = new DBHandler(AllAlerts.this);

        alerts = getAllAlerts();
        System.out.println("ALL ALERTS: "+alerts);
        recyclerView = (RecyclerView) findViewById(R.id.alertRecyclerView);

        alertLayoutManager = new LinearLayoutManager(this);
        alertAdapter = new AlertAdapter(alerts);
        recyclerView.setLayoutManager(alertLayoutManager);
        recyclerView.setAdapter(alertAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }



    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            String coin_name = alerts.get(position).getCoin_name();
            deleteAlert(coin_name);
        }
    };
    public ArrayList getAllAlerts(){
        Cursor res = myDB.showAll();
        if(res.getCount()==0){
            Toast.makeText(AllAlerts.this,"No entries found", Toast.LENGTH_SHORT).show();
        }

        ArrayList<AlertModal> alerts = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            AlertModal alertModal = new AlertModal();
            alertModal.setCoin_name(res.getString(1));
            alertModal.setCoin_limit(res.getDouble(2));
            alerts.add(alertModal);
        }
        return (alerts);
    }

    public void deleteAlert(String coin_name){
        boolean res = myDB.deleteData(coin_name);
        if (res){
            Toast.makeText(AllAlerts.this,"Alert Deleted Successfully", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(AllAlerts.this,"", Toast.LENGTH_SHORT).show();
        }
    }
}
