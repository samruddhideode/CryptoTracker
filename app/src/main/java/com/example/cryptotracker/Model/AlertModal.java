package com.example.cryptotracker.Model;

import java.util.ArrayList;

public class AlertModal {
    private String coin_name;
    private Double coin_limit;

    public String getCoin_name() {
        return coin_name;
    }

    public void setCoin_name(String coin_name) {
        this.coin_name = coin_name;
    }

    public Double getCoin_limit() {
        return coin_limit;
    }

    public void setCoin_limit(Double coin_limit) {
        this.coin_limit = coin_limit;
    }
}