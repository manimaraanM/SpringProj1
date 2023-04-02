package com.promineotech.jeep.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.promineotech.jeep.entity.Jeep;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;




@RequestMapping("/jeep")
@OpenAPIDefinition(info=@Info(title="Jeep Sales Service"),
servers=@Server(url="http://localhost:8080",description="local host server"))

public interface JeepSalesController {
	
@Operation(
		summary="Service to check Jeep",
		description="Service to check Jeep Details",
		responses= {
				@ApiResponse(responseCode="200",
						description="Response Ok",
						content= @Content(mediaType="application/json"
						)),
				@ApiResponse(responseCode="400",
								description="Service in Different type ",
								content= @Content(mediaType="application/json")),
				@ApiResponse(responseCode="500",
								description="Service unavaiable",
								content= @Content(mediaType="application/json"))
		},
		parameters={
				@Parameter(name="model",
						allowEmptyValue=false,
						required=false,
						description="model name i.e Wrangler"),
				@Parameter(name="trim",
				allowEmptyValue=false,
				required=false,
				description="trim name i.e Sport")
		}

		)
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	List<Jeep> fetchJeep(@RequestParam String model,@RequestParam String trim);
}
