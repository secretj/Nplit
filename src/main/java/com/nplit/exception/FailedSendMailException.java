package com.nplit.exception;

/**
 * 이메일 전송이 실패 했을 때 익셉션.
 * 
 * @author Park
 *
 */
public class FailedSendMailException extends ResponseBodyException {
	private static final long serialVersionUID = 1L;

	public FailedSendMailException(String message, Throwable cause) {
		super(message, 100, cause);
	}
}