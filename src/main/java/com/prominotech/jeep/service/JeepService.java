package com.prominotech.jeep.service;

import java.util.List;


import com.promineotech.jeep.entity.Jeep;

public interface JeepService {

	 List<Jeep> fetchJeepDetails(String model, String trim) ;
}
