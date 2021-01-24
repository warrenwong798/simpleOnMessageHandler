package com.marketdata.marketdata;

import com.marketdata.marketdata.dto.MarketData;
import com.marketdata.marketdata.handler.MarketDataProcessor;
import com.marketdata.marketdata.util.CacheManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Random;

@Component
public class DummyFeed {

    private static final Logger logger = LogManager.getLogger(DummyFeed.class);

    @Autowired
    private MarketDataProcessor marketDataProcessor;
    @Autowired
    private CacheManager cacheManager;

    private final Random rand = new Random();

    public DummyFeed(){

    }

    private MarketData generateRandomData(){
        int symbol = rand.nextInt(1000);
        double bid = rand.nextDouble() * 100;
        double ask = bid * 1.05;
        double last = bid * 1.1;
        Date date = new Date();
        return new MarketData(symbol, bid, ask, last, date);
    }



    @PostConstruct
    private void run() throws InterruptedException {
        marketDataProcessor.printStartLog();

        logger.info("Normal random dummy data~~~~~~");
        randomFeed(10);
        Thread.sleep(3000);
        logger.info("Data with same symbol~~~~~~");
        sameSymbolFeed(10, 1001);
        Thread.sleep(3000);
        logger.info("High frequency data~~~~~~");
        randomFeed(150);
    }

    public void randomFeed(int times){
        for (int i = 0; i < times; i ++){
            marketDataProcessor.onMessage(generateRandomData());
        }
    }

    public void sameSymbolFeed(int times, int symbol){
        for (int i = 0; i < times; i ++){
            MarketData marketData = generateRandomData();
            marketData.setSymbol(symbol);
            marketDataProcessor.onMessage(marketData);
        }
    }


}
