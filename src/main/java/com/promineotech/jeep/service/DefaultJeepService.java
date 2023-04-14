package com.promineotech.jeep.service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.promineotech.jeep.dao.JeepSalesDao;
import com.promineotech.jeep.entity.Jeep;
import com.promineotech.jeep.entity.JeepModel;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DefaultJeepService implements JeepSalesService {
@Autowired
private JeepSalesDao jeepSalesDao;

	@Transactional(readOnly=true)
	@Override
	public List<Jeep> fetchJeepDetails(JeepModel model, String trim) {
		// TODO Auto-generated method stub
		log.debug("Fetch Jeep Details from Service Layer with model{} and trim{}",model,trim);
		List<Jeep> jeeps=jeepSalesDao.fetchJeeps(model,trim);
		if(jeeps.isEmpty()) {
			String msg=String.format("Provided model %s and trim %s are invalid", model,trim);
			throw new NoSuchElementException(msg);
			
		}
	  Collections.sort(jeeps);
	  return jeeps;
	}

}
