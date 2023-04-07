package com.promineotech.jeep.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.promineotech.jeep.entity.Jeep;
import com.prominotech.jeep.service.JeepService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class DefaultJeepSalesController implements JeepSalesController {
	//@Autowired
private JeepService jeepService;
	 @Override
	public List<Jeep> fetchJeep(String model, String trim) {
		 log.debug("fetching jeep details with trim={},model={}",trim,model);
		 log.info("test");
		 System.out.println("fetch Jeep");
		return jeepService.fetchJeepDetails(model,trim);
		//return null;
	}

}
