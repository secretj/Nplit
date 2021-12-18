package com.nplit.exception;

public class ResponseBodyException extends Exception {
 public int errorCode;
   
   public ResponseBodyException(String message, int errorCode, Throwable cause) {
      super(message, cause);
      this.errorCode = errorCode;
   }
}