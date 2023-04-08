package com.promineotech.jeep.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import com.promineotech.jeep.entity.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT )
@ActiveProfiles("test")
@Sql(scripts= {"classpath:flyway/migration/V1.0__Jeep_Schema.sql",
		"classpath:flyway/migration/V1.1__Jeep_Data.sql"},
	config= @SqlConfig(encoding="utf-8")
		)

public class FetchJeepTest {
	@LocalServerPort
	private int port;	
	@Autowired
	private TestRestTemplate restTemplate;

	@Disabled
	@Test
	void dummytest() {
		
	}
	
	
	@Test
	void testThatJeepsAreReturnedWhenAValidModelAndTrimAreSupplied() {
		JeepModel model=JeepModel.WRANGLER;
		String trim="Sport";
	System.out.println("testing 1");

		String uri=String.format("http://localhost:%s/jeep?model=%s&trim=%s", port,model,trim);
		
		System.out.println(uri);

	ResponseEntity<List<Jeep>> response=restTemplate.exchange(uri, HttpMethod.GET, null, 
							new ParameterizedTypeReference<>() {});
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		//Actual list is equal to expected result
		//List<Jeep> actual=Jeeplist();
		List<Jeep> expected=buildExpected();
		log.info("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
		expected.forEach(e->System.out.println(e));
		assertThat(response.getBody()).isEqualTo(expected);
		
	}

	private List<Jeep> buildExpected() {
		// TODO Auto-generated method stub
		List<Jeep> j=new LinkedList<>();
		Jeep j1=Jeep.builder().model_id(JeepModel.WRANGLER)
				.trim_level("Sport")
				.num_doors(2)
				.wheel_size(17)
				.base_price(new BigDecimal("28475.00"))
				.build();
		Jeep j2=Jeep.builder().model_id(JeepModel.WRANGLER)
				.trim_level("Sport")
				.num_doors(4)
				.wheel_size(17)
				.base_price(new BigDecimal("31975.00"))
				.build();
		j.add(j2);
		j.add(j1);
		
		return j;
	}

	

}
