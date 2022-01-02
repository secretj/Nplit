package com.nplit.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nplit.exception.FailedSendMailException;
import com.nplit.exception.ResponseBodyException;

/**
 * 컨트롤러 컴포넌트 전체 오류를 처리하는 클래스
 * 
 * @author Park
 *
 */
@ControllerAdvice
public class NplitControllerAdvice {

	@ExceptionHandler(Exception.class)
	public String custom(Exception e) {
		System.out.println(e.getMessage());
		return "/success/404";
	}

	@ExceptionHandler(ResponseBodyException.class)
	@ResponseBody
	public String custom1(ResponseBodyException e) {
		return e.getMessage();
	}
}