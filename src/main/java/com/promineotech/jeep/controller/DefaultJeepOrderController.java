package com.promineotech.jeep.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.promineotech.jeep.entity.Order;
import com.promineotech.jeep.entity.OrderRequest;
import com.promineotech.jeep.service.JeepOrderService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class DefaultJeepOrderController implements JeepOrderController {

	@Autowired
	private JeepOrderService createOrderService;

	@Override
	public Order CreateOrder(OrderRequest orderrequest) {
		// TODO Auto-generated method stub
		log.info("Create Order - Controller interface -{}",orderrequest);
		return createOrderService.createOrderServiceCall(orderrequest);
	}

}
