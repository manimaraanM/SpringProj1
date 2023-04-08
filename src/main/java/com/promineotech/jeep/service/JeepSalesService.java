package com.promineotech.jeep.service;

import java.util.List;


import com.promineotech.jeep.entity.Jeep;

public interface JeepSalesService {

	 List<Jeep> fetchJeepDetails(String model, String trim) ;

}
