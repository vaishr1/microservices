package com.vr.microservices.currency_exchange_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {
	
	Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);
	
	@Autowired
	private CurrencyExchangeRepository repository;
	
	@Autowired
	private Environment environment;
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange retrieveExchangeValue(
			@PathVariable String from,
			@PathVariable String to) {
		
		logger.info("retrieveExchangeValue called with {} to {}", from, to);
		/*
		 INFO [currency-exchange,,] 41872 --- [currency-exchange] [nio-8000-exec-1] [                                                 ]
		  o.s.web.servlet.DispatcherServlet        : Completed initialization in 598 ms
2024-07-30T19:11:13.686-07:00  INFO [currency-exchange,4b7eaf0bf3239cccb2429456f9dedd77,7b8000b22ffacd6e] 41872 
--- [currency-exchange] [nio-8000-exec-1] [4b7eaf0bf3239cccb2429456f9dedd77-7b8000b22ffacd6e] c.v.m.c.CurrencyExchangeController       
: retrieveExchangeValue called with AUD to INR

micrometer asigns this id to the microservice for this specific request 
let's say request goes from CurrencyExchange to CurrencyConversion, this id will be retained.
passed on to the currencyConversionService.
This if will be used to trace the request
		 * */
		
//		CurrencyExchange currencyExchange = new CurrencyExchange(1000l, from, to, BigDecimal.valueOf(50));
		
		CurrencyExchange currencyExchange = repository.findByFromAndTo(from, to);
		
		if(currencyExchange == null){
			throw new RuntimeException("Unable to Find data for " + from + " to " + to);
		}
		String port = environment.getProperty("local.server.port");
		currencyExchange.setEnvironment(port);
		return currencyExchange;
	}

}
