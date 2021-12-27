package com.nplit.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nplit.exception.FailedSendMailException;
import com.nplit.exception.ResponseBodyException;
/**
 * ��Ʈ�ѷ� ������Ʈ ��ü ������ ó���ϴ� Ŭ����
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