package com.example.cryptotracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cryptotracker.Model.CurrencyModal;

import java.util.ArrayList;

class Alert{
    private String currencyName;
    private double lowerLimit;
}
public class SetAlert extends AppCompatActivity {
    private Button setAlert;
    private EditText getLL;
    ArrayList<Alert> alerts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_alert);

        Intent exp =  getIntent();

        CurrencyModal currencyModal = (CurrencyModal) exp.getSerializableExtra("currency_modal");
        System.out.println(currencyModal.getCurrencyName());

        setAlert = findViewById(R.id.go);
        getLL = findViewById(R.id.getLowerLimit);

        setAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double lower_limit = Double.parseDouble(getLL.getText().toString());
                currencyModal.setLowerLimit(lower_limit);
                Context context = getApplicationContext();
                CharSequence text = "You will be alerted when "+ currencyModal.getCurrencySymbol()+" price drops lower than "+ Double.toString(lower_limit);
                int duration = Toast.LENGTH_SHORT;
                Toast.makeText(context, text, duration).show();
                getLL.setText("");
            }
        });
    }
}
