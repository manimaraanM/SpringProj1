package com.promineotech.jeep.dao;

import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Controller;

import com.promineotech.jeep.entity.Jeep;
import com.promineotech.jeep.entity.JeepModel;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class DefaultJeepSalesDao implements JeepSalesDao {
@Autowired
private NamedParameterJdbcTemplate jdbcTemplate;
	@Override
	public List<Jeep> fetchJeeps(JeepModel model, String trim) {
		// TODO Auto-generated method stub
		log.info("Dao implementation layer called for model: {} ,trim : {}",model,trim);
		String query=""
				+" Select * from models"
				+" where model_id= :model_id"
				+" and trim_level= :trim_level";
		
		Map<String,Object> queryadd = new HashMap<>();
		queryadd.put("model_id",model.toString());
		queryadd.put("trim_level",trim);
		
		return jdbcTemplate.query(query, queryadd, 
				new RowMapper<>() {
					@Override
					public Jeep mapRow(ResultSet rs, int rowNum) throws SQLException {
						// TODO Auto-generated method stub
						return Jeep.builder()
								.base_price(new BigDecimal(rs.getString("base_price")))
								.model_id(JeepModel.valueOf(rs.getString("model_id")))
								.model_pk(rs.getLong("model_pk"))
								.num_doors(rs.getInt("num_doors"))
								.trim_level(rs.getString("trim_level"))
								.wheel_size(rs.getInt("wheel_size"))
								.build();
					}}
		
				);
		
		
	}

}
