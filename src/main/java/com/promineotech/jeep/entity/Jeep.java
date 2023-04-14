package com.promineotech.jeep.entity;

import java.math.BigDecimal;
import java.util.Comparator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Jeep implements Comparable<Jeep> {
	private Long model_pk;
	private JeepModel model_id;
	private String trim_level;
	private int num_doors;
	private int wheel_size;
	private BigDecimal base_price;

	@JsonIgnore
	public Long getmodel_pk() {
		return model_pk;
		
	}

	@Override
	public int compareTo(Jeep o) {
		// TODO Auto-generated method stub
		return Comparator.comparing(Jeep::getModel_id)
				.thenComparing(Jeep::getNum_doors)
				.thenComparing(Jeep::getWheel_size)
				.compare(this,o);
	
	}
	
	
	
}

