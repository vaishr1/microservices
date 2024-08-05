package com.vr.microservices.currency_exchange_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;

@RestController
public class CircuitBreakerController {
	
	private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);
	
	@GetMapping("/sample-api")
////	@Retry(name = "default", fallbackMethod = "hardCodedResponse")
//	@CircuitBreaker(name = "default", fallbackMethod = "hardCodedResponse")
//	@RateLimiter(name="default")	// 10s => 1000 req to same api
	@Bulkhead(name="sample-api")
	public String sampleApi(){
		logger.info("Sample API response");
		
//		ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/some-dummy-url", String.class);
//	
//		return forEntity.getBody();
		
		return "sample-api-response";
	}
	public String hardCodedResponse(Exception ex) {
		return "fallback response";
	}
}
