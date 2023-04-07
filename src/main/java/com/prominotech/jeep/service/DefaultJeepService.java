package com.prominotech.jeep.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.promineotech.jeep.entity.Jeep;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DefaultJeepService implements JeepService {

	@Override
	public List<Jeep> fetchJeepDetails(String model, String trim) {
		// TODO Auto-generated method stub
		log.debug("Fetch Jeep Details from Service Layer");
		return null;
	}

}
