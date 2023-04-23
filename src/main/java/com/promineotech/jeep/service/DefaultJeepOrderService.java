package com.promineotech.jeep.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promineotech.jeep.dao.JeepOrderDao;
import com.promineotech.jeep.entity.Color;
import com.promineotech.jeep.entity.Customer;
import com.promineotech.jeep.entity.Engine;
import com.promineotech.jeep.entity.Jeep;
import com.promineotech.jeep.entity.Option;
import com.promineotech.jeep.entity.Order;
import com.promineotech.jeep.entity.OrderRequest;
import com.promineotech.jeep.entity.Tire;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DefaultJeepOrderService implements JeepOrderService {

	@Autowired
	JeepOrderDao createOrderDao;
	
	
	
	@Override
	public Order createOrderServiceCall(OrderRequest orderrequest) {
		// TODO Auto-generated method stub
		log.info("Service Layer - {}",orderrequest);
		Customer customer=getCustomer(orderrequest);
		
		
		Jeep jeep=getModel(orderrequest);
			
		Color color = getColor(orderrequest);
		
		Engine engine = getEngine(orderrequest);
		
		Tire tire=getTire(orderrequest);
		
		List<Option> option=createOrderDao.fetchOptions(orderrequest.getOptions());
		
		BigDecimal price= jeep.getBase_price().add(color.getPrice()).add(engine.getPrice()).add(tire.getPrice());
		
		for(Option options:option) {
			price = price.add(options.getPrice());
		}
		
		
		log.info("MMM Customer details - {} ",customer);
		log.info("MMM Color details - {} ",color);
		log.info("MMM Engine details-{}",engine);
		log.info("MMM Tire details-{}",tire);
		
		log.info("MMM Jeep details -{}",jeep);
		log.info("MMM Option details - {}",option);
		
		log.info("MMM price details -{}",price);
		
		Order order=createOrderDao.saveOrder(customer,jeep,color,engine,tire,price,option);
		/**
		 * 
		 * "model":"GLADIATOR",
   "trim":"Overland",
   "doors":4,
   "color":"EXT_STING_GRAY",
   "engine":"3_0_DIESEL",
   "tire":"265_MICHELIN",
   "options":[
      "EXT_MOPAR_HEAD_LED"
   ]
		
		 * 
		 * 
		 */
		
		log.info("Customer details - {}",customer);
		log.info("Order: {}",order);
		return order;
	}



	private Jeep getModel(OrderRequest orderrequest) {
		return createOrderDao.fetchJeepDetails(orderrequest.getModel(),orderrequest.getTrim(),orderrequest.getDoors())
				.orElseThrow(()-> new NoSuchElementException("Model with id =" + orderrequest.getModel()+"is missing"));
	}



	private Tire getTire(OrderRequest orderrequest) {
		return createOrderDao.fetchTire(orderrequest.getTire())
				.orElseThrow(()-> new NoSuchElementException("Tire with id =" + orderrequest.getTire()+"is missing"));
	}



	private Engine getEngine(OrderRequest orderrequest) {
		return createOrderDao.fetchEngine(orderrequest.getEngine())
				.orElseThrow(()-> new NoSuchElementException("Engine with id =" + orderrequest.getEngine()+"is missing"));
	}



	private Color getColor(OrderRequest orderrequest) {
		return createOrderDao.fetchColor(orderrequest.getColor())
				.orElseThrow(()-> new NoSuchElementException("Color with id =" + orderrequest.getColor()+"is missing"));
	}



	private Customer getCustomer(OrderRequest orderrequest) {
		return createOrderDao.fetchCustomer(orderrequest.getCustomer())
				.orElseThrow(()-> new NoSuchElementException("Customer with id =" + orderrequest.getCustomer()+"is missing"));
	}

}
