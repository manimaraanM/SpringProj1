package com.promineotech.jeep.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.promineotech.jeep.dao.JeepSalesDao;
import com.promineotech.jeep.entity.Jeep;
import com.promineotech.jeep.entity.JeepModel;

public class DefaultJeepServices implements JeepSalesService {
@Autowired
private JeepSalesDao jeepdao;
	
	@Override
	public List<Jeep> fetchJeepDetails(JeepModel model, String trim) {
		// TODO Auto-generated method stub
		return null;
	}

}
