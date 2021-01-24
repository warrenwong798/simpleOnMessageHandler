package com.marketdata.marketdata.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MarketData {

    private int symbol;
    private double bid;
    private double ask;
    private double last;
    private Date updateTime;

    public MarketData(int symbol, double bid, double ask, double last, Date updateTime){
        this.symbol = symbol;
        this.bid = bid;
        this.ask = ask;
        this.last = last;
        this.updateTime = updateTime;
    }

    public String toString(){
        return "Symbol: " + symbol + " Bid: " + bid + " Ask: " + ask + " Last: " + last + " Update Time: " + updateTime;
    }


}
