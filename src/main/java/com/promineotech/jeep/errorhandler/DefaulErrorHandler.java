package com.promineotech.jeep.errorhandler;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestControllerAdvice
public class DefaulErrorHandler {


private enum Errorlevel{
	INFO,DEBUG
}

	@ExceptionHandler(NoSuchElementException.class)
	@ResponseStatus(code=HttpStatus.NOT_FOUND)
	public Map<String,Object> handleNoSuchElementException(NoSuchElementException e,WebRequest webRequest) {
		log.info("testing handleNoSuchElementException exception");
		
		
		return CalculateError(e,HttpStatus.NOT_FOUND,webRequest,Errorlevel.INFO);
		
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(code=HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String,Object> handleException(Exception e,WebRequest webRequest){
		
		return CalculateError(e,HttpStatus.INTERNAL_SERVER_ERROR,webRequest,Errorlevel.DEBUG);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(code=HttpStatus.BAD_REQUEST)
	public Map<String,Object> handleConstraintViolationException(ConstraintViolationException e,WebRequest webRequest) {
		log.info("testing handleConstraintViolationException exception");
		
		
		return CalculateError(e,HttpStatus.BAD_REQUEST,webRequest,Errorlevel.INFO);
		
	}
		
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(code=HttpStatus.BAD_REQUEST)
	public Map<String,Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e,WebRequest webRequest) {
		log.info("testing MethodArgumentTypeMismatchException exception");
		
		
		return CalculateError(e,HttpStatus.BAD_REQUEST,webRequest,Errorlevel.INFO);
		
	}
	
	private Map<String, Object> CalculateError(Exception e, HttpStatus status, WebRequest webRequest,Errorlevel level) {
		// TODO Auto-generated method stub
		Map<String,Object> error=new HashMap<>();
		String time=ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME);
		
		if(webRequest instanceof ServletWebRequest) {
			error.put("uri", ((ServletWebRequest)webRequest).getRequest().getRequestURI());
		}
		error.put("message",e.toString() );
		error.put("status", status.value());
		
		error.put("timestramp",time);
		error.put("reason", status.getReasonPhrase());
		ErrorLogger(level,e);
		return error;
	}
	
	private void ErrorLogger(Errorlevel level,Exception e) {
		if(level == Errorlevel.INFO) {
			log.error("Exception is {}",e.toString());
		}
		else {
			log.error("Exception is : ",e);
		}
	}
}
