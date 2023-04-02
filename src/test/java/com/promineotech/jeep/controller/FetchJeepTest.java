package com.promineotech.jeep.controller;

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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import com.promineotech.jeep.entity.JeepModel;
import com.promineotech.jeep.entity.*;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT )
@ActiveProfiles("test")

public class FetchJeepTest {
	@LocalServerPort
	private int port;	
	@Autowired
	private TestRestTemplate restTemplate;

	void testThatJeepsAreReturnedWhenAValidModelAndTrimAreSupplied() {
		JeepModel model=JeepModel.WRANGLER;
		String trim="Sport";
	

		String uri=String.format("http://localhost:%s/jeep?model=%s&trim=%s", port,model,trim);
		
		ResponseEntity<List<Jeep>> response=restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		
	}

	

}
