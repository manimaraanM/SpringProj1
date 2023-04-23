package com.promineotech.jeep.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.http.*;

import com.promineotech.jeep.entity.*;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class CreateOrderTest {
	

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
		private TestRestTemplate testTemplate;
	
	@Test
	 void CreateOrderReturn201() {	
		log.info("testing Create order");
	
		
		  String body= createOrderBody();
		  String url=String.format("http://localhost:%s/orders/", port);
		  HttpHeaders header = new HttpHeaders();
		  header.setContentType(MediaType.APPLICATION_JSON);
		  
		  HttpEntity<String> bodyEntity= new HttpEntity<>(body,header);
		  
	
		  
		 log.info("Create Order before response {}",bodyEntity);
		 
		  ResponseEntity<Order> response=testTemplate.exchange(url, HttpMethod.POST, bodyEntity, Order.class);
		  
			 log.info("Create Order after response-{} ",response.getStatusCode());
		  assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
			 log.info("Assert");
			 
			 assertThat(response.getBody()).isNotNull();
			 
			 Order order=response.getBody();
		
			 
			 assertThat(order.getCustomer().getCustomerId()).isEqualTo("MORISON_LINA");
			 assertThat(order.getModel().getModel_id()).isEqualTo(JeepModel.WRANGLER);
			 assertThat(order.getModel().getTrim_level()).isEqualTo("Sport Altitude");
			 assertThat(order.getModel().getNum_doors()).isEqualTo(4);
			 assertThat(order.getColor().getColorId()).isEqualTo("EXT_NACHO");
			 assertThat(order.getEngine().getEngineId()).isEqualTo("2_0_TURBO");
			 assertThat(order.getTire().getTireId()).isEqualTo("35_TOYO");
			 assertThat(order.getOptions()).hasSize(6);

			 
			    
				}
	
	}
	
	

	private String createOrderBody() {
		// TODO Auto-generated method stub
		//@formatter:off
	       return "{\n"
	            + "   \"customer\":\"MORISON_LINA\",\n"
	            + "   \"model\":\"WRANGLER\",\n"
	            + "   \"trim\":\"Sport Altitude\",\n"
	            + "   \"doors\":4,\n"
	            + "   \"color\":\"EXT_NACHO\",\n"
	            + "   \"engine\":\"2_0_TURBO\",\n"
	            + "   \"tire\":\"35_TOYO\",\n"
	            + "   \"options\":[\n"
	            + "     \"DOOR_QUAD_4\",\n"
	            + "    \"EXT_AEV_LIFT\",\n"
	            + "    \"EXT_WARN_WINCH\",\n"
	            + "    \"EXT_WARN_BUMPER_FRONT\",\n"
	            + "    \"EXT_WARN_BUMPER_REAR\",\n"
	            + "    \"EXT_ARB_COMPRESSOR\"\n"
	            + "   ]\n"
	            + "}";
	       //@formatter:on
		
	}
	
	@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT )
	@ActiveProfiles("test")
	@Sql(scripts= {"classpath:flyway/migration/V1.0__Jeep_Schema.sql",
			"classpath:flyway/migration/V1.1__Jeep_Data.sql"},
		config= @SqlConfig(encoding="utf-8")
			)
	@Nested
	class MockingTest{

		@LocalServerPort
		private int port;
		@Autowired
		private TestRestTemplate testTemplate;
	
	@Test
	 void CreateOrderReturn201() {	
		


		log.info("testing Create order");
	}
	
}
	}


