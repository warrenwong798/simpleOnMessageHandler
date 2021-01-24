package com.marketdata.marketdata.handler;

import com.marketdata.marketdata.MarketdataApplication;
import com.marketdata.marketdata.constant.MarketDataConstant;
import com.marketdata.marketdata.dto.MarketData;
import com.marketdata.marketdata.util.CacheManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MarketDataProcessor {

    private static final Logger logger = LogManager.getLogger(MarketDataProcessor.class);

    @Autowired
    private CacheManager cacheManager;

    // Receive incoming market data

    public void onMessage(MarketData data) {

        Date lastPublishTime = cacheManager.getSymbolUpdateTime(data.getSymbol());
        if (!validateSymbolFreq(lastPublishTime, data.getUpdateTime())){
            logger.error("Exceed Symbol update frequency. " + data.toString());
            return;
        }

        double timeInterval = (data.getUpdateTime().getTime() - cacheManager.getLastTime().getTime()) / 1000.0;
        if (!validateIntervalLimit(timeInterval)){
            logger.error("Exceed Interval Limit. " + data.toString());
            return;
        }

        handlePublish(data, timeInterval);

    }

    private boolean validateSymbolFreq(Date lastPublishTime, Date newTime){
        if (lastPublishTime != null){
            double seconds = (newTime.getTime() - lastPublishTime.getTime()) / 1000.0;
            if (seconds < MarketDataConstant.symbolUpdateLimit){
                return false;
            }
        }
        return true;
    }

    private boolean validateIntervalLimit(double timeInterval){
        if (timeInterval < MarketDataConstant.intervalLimit){
            cacheManager.incrementTimeIntervalSum();
            if (cacheManager.getTimeIntervalSum() > MarketDataConstant.updatePerInterval){
                return false;
            }
        }
        return true;
    }

    private void handlePublish(MarketData data, double timeInterval){
        cacheManager.updateSymbolUpdateTime(data.getSymbol(), data.getUpdateTime());
        if (timeInterval > MarketDataConstant.intervalLimit){
            cacheManager.resetInterval(data.getUpdateTime());
        }
        publishAggregatedMarketData(data);
    }



// do something



// Publish aggregated and throttled market data

    public void publishAggregatedMarketData(MarketData data) {

        // Do Nothing, assume implemented.
        logger.info(data.toString() + " published.");
    }

    public void printStartLog(){
        logger.info("Market Processor started.");
    }

}
