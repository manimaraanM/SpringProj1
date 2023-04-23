package com.promineotech.jeep.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.promineotech.jeep.entity.Color;
import com.promineotech.jeep.entity.Customer;
import com.promineotech.jeep.entity.Engine;
import com.promineotech.jeep.entity.Jeep;
import com.promineotech.jeep.entity.JeepModel;
import com.promineotech.jeep.entity.Option;
import com.promineotech.jeep.entity.Order;
import com.promineotech.jeep.entity.OrderRequest;
import com.promineotech.jeep.entity.Tire;

public interface JeepOrderDao {

	Optional <Order> createOrderDaoCall(OrderRequest orderrequest);

	Optional <Customer> fetchCustomer(String customer);

	Optional <Color> fetchColor(String color);

	Optional <Engine> fetchEngine(String engine);

	Optional <Tire> fetchTire(String tire);

	Optional <Jeep> fetchJeepDetails(JeepModel model, String trim, int doors);

	Order saveOrder(Customer customer,Jeep jeep, Color color, Engine engine, Tire tire, BigDecimal price, List<Option> option);

	List<Option> fetchOptions(List<String> options);

}
