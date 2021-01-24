package com.marketdata.marketdata;

import com.marketdata.marketdata.handler.MarketDataProcessor;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarketdataApplication {

	private static final Logger logger = LogManager.getLogger(MarketdataApplication.class);

	@Autowired
	private MarketDataProcessor marketDataProcessor;

	public synchronized static void main(String[] args) throws InterruptedException {
		SpringApplication.run(MarketdataApplication.class, args);
		// hold the application on for additional feed input
		MarketdataApplication.class.wait();
	}

}
