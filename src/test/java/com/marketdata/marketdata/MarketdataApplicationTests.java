package com.marketdata.marketdata;

import com.marketdata.marketdata.dto.MarketData;
import com.marketdata.marketdata.handler.MarketDataProcessor;
import com.marketdata.marketdata.util.CacheManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class MarketdataApplicationTests {

	@Autowired
	MarketDataProcessor marketDataProcessor;


	@Test
	public void testOnMessage(){
		int symbol = 1;
		double bid = 1;
		double ask = bid * 1.05;
		double last = bid * 1.1;
		Date date = new Date();
		MarketData marketData = new MarketData(symbol, bid, ask, last, date);
		marketDataProcessor.onMessage(marketData);
	}



	@Test
	void contextLoads() {
	}

}
