package com.promineotech.jeep.entity;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Jeep {
	private Long model_pk;
	private JeepModel model_id;
	private String trim_level;
	private int num_doors;
	private int wheel_size;
	private BigDecimal base_price;

}
