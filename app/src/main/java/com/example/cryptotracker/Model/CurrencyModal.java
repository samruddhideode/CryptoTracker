package com.example.cryptotracker.Model;

import static java.sql.Types.NULL;

import java.io.Serializable;
import java.text.DecimalFormat;

public class CurrencyModal implements Serializable {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private String name;
    private String symbol;
    private double low_limit = NULL;
    public Quote quote;

    public Quote getQuote() {
        return quote;
    }

    public class Quote implements Serializable{
        public currency getUSD() {
            return USD;
        }

        public currency USD;
        public class currency implements Serializable{
            double price;
            double percent_change_1h;
            double percent_change_24h;
            double percent_change_7d;

            public currency() {
                this.price = price;
                this.percent_change_1h = percent_change_1h;
                this.percent_change_24h = percent_change_24h;
                this.percent_change_7d = percent_change_7d;
            }

            //getter and setter
            public double getPrice() {
                return Double.parseDouble(df.format(price));
            }

            public void setPrice(double currencyRate) { this.price = currencyRate; }

            public double getChange1h() {
                return Double.parseDouble(df.format(percent_change_1h));
            }

            public void setChange1h(double change1h) {
                this.percent_change_1h = change1h;
            }

            public double getChange24h() {
                return Double.parseDouble(df.format(percent_change_24h));
            }

            public void setChange24h(double change24h) {
                this.percent_change_24h = change24h;
            }

            public double getChange7d() {
                return Double.parseDouble(df.format(percent_change_7d));
            }

            public void setChange7d(double change7d) {
                this.percent_change_7d = change7d;
            }
        }
    }


    public CurrencyModal(String currencyName, String currencySymbol, double currencyRate, double percent_change_1h, double percent_change_24h, double percent_change_7d)
    {
        this.name = currencyName;
        this.symbol = currencySymbol;
    }

    public double getLowerLimit() {
        return low_limit; }

    public void setLowerLimit(double lower_limit) {
        this.low_limit = lower_limit;
    }

    public String getCurrencyName() {
        return name;
    }

    public void setCurrencyName(String currencyName) {
        this.name = currencyName;
    }

    public String getCurrencySymbol() {
        return symbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.symbol = currencySymbol;
    }

    public double getLow_limit() {
        return low_limit; }

    public void setLow_limit(double low_limit) { this.low_limit = low_limit; }

}
