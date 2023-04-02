package com.promineotech.jeep.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.promineotech.jeep.entity.Jeep;

@RestController
public class DefaultJeepSalesController implements JeepSalesController {

	 @Override
	public List<Jeep> fetchJeep(String model, String trim) {
		 System.out.println("fetch Jeep");
		return null;
	}

}
