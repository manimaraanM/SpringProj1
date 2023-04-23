package com.promineotech.jeep.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Controller;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.promineotech.jeep.entity.Color;
import com.promineotech.jeep.entity.Customer;
import com.promineotech.jeep.entity.Engine;
import com.promineotech.jeep.entity.Jeep;
import com.promineotech.jeep.entity.JeepModel;
import com.promineotech.jeep.entity.Option;
import com.promineotech.jeep.entity.OptionType;
import com.promineotech.jeep.entity.Order;
import com.promineotech.jeep.entity.OrderRequest;
import com.promineotech.jeep.entity.Tire;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
@Controller
@Slf4j
public class DefaultJeepOrderDao implements JeepOrderDao {
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	@Override
	public Optional<Order> createOrderDaoCall(OrderRequest orderrequest) {
		// TODO Auto-generated method stub
		log.info("DAO call - {}",orderrequest);
		return null;
	}

	@Override
	public Optional<Customer> fetchCustomer(String customer) {
		// TODO Auto-generated method stub
		String query="SELECT * FROM"
				+" customers"
				+" where customer_id=:customer_id";
		
		Map<String,Object> params=new HashMap<>();
		
		params.put("customer_id",customer);
		

		return Optional.ofNullable(
				jdbcTemplate.query(query, params, new CustomerExtractor()));
			
	}
	 class CustomerExtractor implements ResultSetExtractor<Customer> {
		 @Override
			public Customer extractData(ResultSet rs) throws SQLException, DataAccessException {
				// TODO Auto-generated method stub
			 rs.next();
			 
				return Customer.builder()
						.customerId(rs.getString("customer_id"))
						.customerPK(rs.getLong("customer_pk"))
						.firstName(rs.getString("first_name"))
						.lastName(rs.getString("last_name"))
						.phone(rs.getString("phone"))
						.build();
			}}
	@Override
	public Optional<Color> fetchColor(String color) {
		// TODO Auto-generated method stub
		String query=" Select * from colors "
				+" where color_id=:color_id";
		Map<String,Object> param=new HashMap<>();
		param.put("color_id",color);
		
		return Optional.ofNullable(jdbcTemplate.query(query, param,new ColorExtractor()));
	}
	
	class ColorExtractor implements ResultSetExtractor<Color>{

		@Override
		public Color extractData(ResultSet rs) throws SQLException, DataAccessException {
			// TODO Auto-generated method stub
			rs.next();
			return Color.builder()
					.colorId(rs.getString("color_id"))
					.colorPK(rs.getLong("color_pk"))
					.color(rs.getString("color"))
					.price(rs.getBigDecimal("price"))
					.isExterior(rs.getBoolean("is_exterior"))
					.build();
		}
		
	}

	@Override
	public Optional<Engine> fetchEngine(String engine) {
		// TODO Auto-generated method stub
		String query="Select * from engines "
				+" where engine_id=:engine_id";
		Map<String,Object> params =new HashMap<>();
		params.put("engine_id", engine);
		return Optional.ofNullable(jdbcTemplate.query(query,params,new EngineExtractor()));
	}
	
	class EngineExtractor implements ResultSetExtractor<Engine>{

		@Override
		public Engine extractData(ResultSet rs) throws SQLException, DataAccessException {
			// TODO Auto-generated method stub
			rs.next();
			return Engine.builder()
					.engineId(rs.getString("engine_id"))
					.enginePK(rs.getLong("engine_pk"))
					.description(rs.getString("description"))
					.hasStartStop(rs.getBoolean("has_start_stop"))
					.mpgCity(rs.getFloat("mpg_city"))
					.mpgHwy(rs.getFloat("mpg_hwy"))
					.name(rs.getString("name"))
					.price(rs.getBigDecimal("price"))
					.sizeInLiters(rs.getFloat("size_in_liters"))
					.build();
		}
		
		
	}

	@Override
	public Optional<Tire> fetchTire(String tire) {
		// TODO Auto-generated method stub
		String query="Select * from tires "
				+" where tire_id=:tire_id";
		Map<String,Object> params =new HashMap<>();
		params.put("tire_id", tire);
		return Optional.ofNullable(jdbcTemplate.query(query,params,new TireExtractor()));
	}

	class TireExtractor implements ResultSetExtractor<Tire>{

		@Override
		public Tire extractData(ResultSet rs) throws SQLException, DataAccessException {
			// TODO Auto-generated method stub
			rs.next();
			return Tire.builder()
					.tireId(rs.getString("tire_id"))
					.manufacturer(rs.getString("manufacturer"))
					.tirePK(rs.getLong("tire_pk"))
					.tireSize(rs.getString("tire_size"))
					.price(rs.getBigDecimal("price"))
					.warrantyMiles(rs.getInt("warranty_miles"))
					.build();
		}
		
	}
	
	@Override
	public Optional<Jeep> fetchJeepDetails(JeepModel model, String trim, int doors) {
		// TODO Auto-generated method stub
		String query="Select * from models "
				+" where model_id=:model_id"
				+" and trim_level=:trim_level"
				+" and num_doors=:num_doors";

		
		Map<String,Object> params =new HashMap<>();
		params.put("model_id", model.toString());
		params.put("trim_level", trim);
		params.put("num_doors", doors);
		return Optional.ofNullable(jdbcTemplate.query(query,params,new JeepDetailsExtractor()));
		
	}

	class JeepDetailsExtractor implements ResultSetExtractor<Jeep>{

		@Override
		public Jeep extractData(ResultSet rs) throws SQLException, DataAccessException {
			// TODO Auto-generated method stub
			rs.next();
					return Jeep.builder()
							.base_price(rs.getBigDecimal("base_price"))
							.model_id(JeepModel.valueOf(rs.getString("model_id")))
							.model_pk(rs.getLong("model_pk"))
							.num_doors(rs.getInt("num_doors"))
							.trim_level(rs.getString("trim_level"))
							.wheel_size(rs.getInt("wheel_size"))
							.build();
		}
		
	}
	
	@Override
	public List<Option> fetchOptions(List<String> options) {
		// TODO Auto-generated method stub
		if(options.isEmpty()) {
			return new LinkedList<>();
			
		}
		String query="Select * from options"
				+" where option_id in (";
		Map<String,Object> params = new HashMap<>();
		
		for(int index=0;index<options.size();index++) {
			String key="option_"+index;
			query += ":" +key +", ";
			params.put(key,options.get(index));
		}
		
		query = query.substring(0,query.length()-2);
		query +=")";
		
		 return jdbcTemplate.query(query, params, new RowMapper<Option>() {

			@Override
			public Option mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				
				return Option.builder()
						.name(rs.getString("name"))
						.manufacturer(rs.getString("manufacturer"))
						.optionId(rs.getString("option_id"))
						.optionPK(rs.getLong("option_pk"))
						.price(rs.getBigDecimal("price"))
						.category(OptionType.valueOf(rs.getString("category")))
						.build();
			}});
	}
	
	
	
	@Override
	public Order saveOrder(Customer customer,Jeep jeep, Color color, Engine engine, Tire tire, BigDecimal price,List<Option> option) {
		// TODO Auto-generated method stub
		SqlParams params = generateInsertSqlorders(customer, jeep, color, engine, tire, price);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(params.sql, params.source, keyHolder);

		Long orderPK = keyHolder.getKey().longValue();
		saveOptions(option, orderPK);

	// @formatter:off
    return Order.builder()
        .orderPK(orderPK)
        .customer(customer)
        .model(jeep)
        .color(color)
        .engine(engine)
        .tire(tire)
        .options(option)
        .price(price)
        .build();
    // @formatter:on
		
		
	}
	
	private void saveOptions(List<Option> option, Long orderPK) {
		// TODO Auto-generated method stub
		for (Option options : option) {
			SqlParams params = generateInsertSqloptions(options, orderPK);
			jdbcTemplate.update(params.sql, params.source);
		}
		
	}

	private SqlParams generateInsertSqloptions(Option option, Long orderPK) {
		// TODO Auto-generated method stub
		SqlParams params = new SqlParams();
		// @formatter:off
	    params.sql = ""
	        + "INSERT INTO order_options ("
	        + "option_fk, order_fk"
	        + ") VALUES ("
	        + ":option_fk, :order_fk"
	        + ")";
	    // @formatter:on

			params.source.addValue("option_fk", option.getOptionPK());
			params.source.addValue("order_fk", orderPK);

			return params;
	}

	private SqlParams generateInsertSqlorders(Customer customer, Jeep jeep, Color color, Engine engine, Tire tire,
			BigDecimal price) {
		// TODO Auto-generated method stub
		// @formatter:off
	    String sql = ""
	        + "INSERT INTO orders ("
	        + "customer_fk, color_fk, engine_fk, tire_fk, model_fk, price"
	        + ") VALUES ("
	        + ":customer_fk, :color_fk, :engine_fk, :tire_fk, :model_fk, :price"
	        + ")";
	    // @formatter:on

			SqlParams params = new SqlParams();

			params.sql = sql;
			params.source.addValue("customer_fk", customer.getCustomerPK());
			params.source.addValue("color_fk", color.getColorPK());
			params.source.addValue("engine_fk", engine.getEnginePK());
			params.source.addValue("tire_fk", tire.getTirePK());
			params.source.addValue("model_fk", jeep.getmodel_pk());
			params.source.addValue("price", price);

			return params;
		
	}
	class SqlParams{
		String sql;
		MapSqlParameterSource source=new MapSqlParameterSource();
	}
	
	}
