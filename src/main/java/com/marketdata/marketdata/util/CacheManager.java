package com.marketdata.marketdata.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Getter
@Setter
@Transactional
public class CacheManager {

    private int timeIntervalSum = 0;
    private Date lastTime = new Date();
    private Map<Integer, Date> symbolUpdateTimeMap = new HashMap<>();

    public Date getSymbolUpdateTime(int symbol){
        return symbolUpdateTimeMap.get(symbol);
    }

    public void updateSymbolUpdateTime(int symbol, Date timestamp){
        symbolUpdateTimeMap.put(symbol, timestamp);
    }

    public void resetInterval(Date lastTime){
        this.timeIntervalSum = 0;
        this.lastTime = lastTime;
    }

    public void incrementTimeIntervalSum(){
        timeIntervalSum++;
    }



}
