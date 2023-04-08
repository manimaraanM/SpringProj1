package com.promineotech.jeep.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.promineotech.jeep.entity.Jeep;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DefaultJeepService implements JeepSalesService {

	@Override
	public List<Jeep> fetchJeepDetails(String model, String trim) {
		// TODO Auto-generated method stub
		log.debug("Fetch Jeep Details from Service Layer with model{} and trim{}",model,trim);
		return null;
	}

}
