package com.promineotech.jeep.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.doThrow;

import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.jdbc.JdbcTestUtils;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import com.promineotech.jeep.constants.Defaultconstants;
import com.promineotech.jeep.entity.*;
import com.promineotech.jeep.service.JeepSalesService;

import lombok.extern.slf4j.Slf4j;

@Slf4j




public class FetchJeepTest {
	
	
	
	@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT )
	@ActiveProfiles("test")
	@Sql(scripts= {"classpath:flyway/migration/V1.0__Jeep_Schema.sql",
			"classpath:flyway/migration/V1.1__Jeep_Data.sql"},
		config= @SqlConfig(encoding="utf-8")
			)
	@Nested
	class RegularTest{
		
		@LocalServerPort
		private int port;	
		@Autowired
		private TestRestTemplate restTemplate;
		@Autowired
		private JdbcTemplate jdbctemplate;

		@Disabled
		@Test
		void dummytest() {
	      int tablerows=JdbcTestUtils.countRowsInTable(jdbctemplate, "customers");
	      log.info("rows in table : {}",tablerows);
			
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
			expected.forEach(e->System.out.println(e));
			assertThat(response.getBody()).isEqualTo(expected);
			
		}

		@Test
		void testThatErrorMessageReturnedWhenMissingModelAndTrimAreSupplied() {
			JeepModel model=JeepModel.WRANGLER;
			String trim="sporting";
		System.out.println("testing 1");

			String uri=String.format("http://localhost:%s/jeep?model=%s&trim=%s", port,model,trim);
			
			System.out.println(uri);

		ResponseEntity<Map<String,Object>> response=restTemplate.exchange(uri, HttpMethod.GET, null, 
								new ParameterizedTypeReference<>() {});
			
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
			
			Map<String,Object> error=response.getBody();
			assertErrors(error,HttpStatus.NOT_FOUND);
			
		}
		

		@ParameterizedTest
		@MethodSource("com.promineotech.jeep.controller.FetchJeepTest#supplyparamsfortestThatErrorMessageReturnedWhenInValidModelAndTrimAreSupplied")
		void testThatErrorMessageReturnedWhenInValidModelAndTrimAreSupplied(String model,String trim,String reason) {
			
		System.out.println("testing 1");

			String uri=String.format("http://localhost:%s/jeep?model=%s&trim=%s", port,model,trim);
			
			System.out.println(uri);

		ResponseEntity<Map<String,Object>> response=restTemplate.exchange(uri, HttpMethod.GET, null, 
								new ParameterizedTypeReference<>() {});
			
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
			
			Map<String,Object> error=response.getBody();
			assertErrors(error,HttpStatus.BAD_REQUEST);
			
		
			
			//Actual list is equal to expected result
			//List<Jeep> actual=Jeeplist();
			//List<Jeep> expected=buildExpected();
			//expected.forEach(e->System.out.println(e));
			//assertThat(response.getBody()).isEqualTo(expected);
			
		}

	}
	
	static Stream<Arguments> supplyparamsfortestThatErrorMessageReturnedWhenInValidModelAndTrimAreSupplied() {
		return Stream.of(
				arguments("WRANGLER","w2341(&","Invalid model"),
				arguments("WRANGLER","D".repeat(Defaultconstants.TRIM_MAX_LENGTH +1),"Model Length too big"),
				arguments("wrong","Sport","Invalid trim")
				);
		
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
	
		Collections.sort(j);
		return j;
	}

	
	@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT )
	@ActiveProfiles("test")
	@Sql(scripts= {"classpath:flyway/migration/V1.0__Jeep_Schema.sql",
			"classpath:flyway/migration/V1.1__Jeep_Data.sql"},
		config= @SqlConfig(encoding="utf-8")
			)
	@Nested
	class PollutedTest{
		@LocalServerPort
		private int port;	
		@Autowired
		private TestRestTemplate restTemplate;
		@Autowired
		private JdbcTemplate jdbctemplate;
		
		@MockBean
		private JeepSalesService jeepsalesservice;
		
		@Disabled
		@Test
		void dummytest() {
	      int tablerows=JdbcTestUtils.countRowsInTable(jdbctemplate, "customers");
	      log.info("rows in table : {}",tablerows);
			
		}
		
		@Test
		void testThatErrorMessageReturnedWhenMissingModelAndTrimAreSupplied() {
			JeepModel model=JeepModel.WRANGLER;
			String trim="MMMMMMMMMMM";
		System.out.println("testing 1");

			String uri=String.format("http://localhost:%s/jeep?model=%s&trim=%s", port,model,trim);
			
			System.out.println(uri);
		
			
			doThrow(new RuntimeException("MMM Mocking Failure"))
			.when(jeepsalesservice)
			.fetchJeepDetails(model,trim);

		ResponseEntity<Map<String,Object>> response=restTemplate.exchange(uri, HttpMethod.GET, null, 
								new ParameterizedTypeReference<>() {});
			
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
			
			Map<String,Object> error=response.getBody();
			assertErrors(error,HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
	}
	
	
	private void assertErrors(Map<String, Object> error,HttpStatus status) {
		assertThat(error).containsKey("message")
		.containsEntry("status", status.value())
		.containsEntry("uri","/jeep")
		.containsKey("timestramp")
		.containsEntry("reason", status.getReasonPhrase());
	}
	
	
	
	
	

}
