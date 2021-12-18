package com.nplit.exception;

/**
 * 로그인이 실패 했을 때 익셉션.
 * @author Park
 *
 */
public class LoginFailedException extends ResponseBodyException {
   private static final long serialVersionUID = 1L;

   public LoginFailedException(String message, Throwable cause) {
      super(message, 200, cause);
   }
}