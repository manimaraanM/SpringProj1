package com.promineotech.jeep.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.promineotech.jeep.entity.Jeep;
import com.promineotech.jeep.entity.JeepModel;
import com.promineotech.jeep.service.JeepSalesService;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class DefaultJeepSalesController implements JeepSalesController {
	@Autowired
private JeepSalesService jeepService;
	 @Override
	public List<Jeep> fetchJeep(JeepModel model, String trim) {
		 log.debug("fetching jeep details with model={},trim={}",model,trim);
		
		 System.out.println("fetch Jeep from Controller");
		return jeepService.fetchJeepDetails(model,trim);
		//return null;
	}

}
