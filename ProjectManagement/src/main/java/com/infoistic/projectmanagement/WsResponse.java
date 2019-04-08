package com.infoistic.projectmanagement;


public class WsResponse {
	private String status;
	private String message;
	private Object data;
	private String errorCode;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	private WsResponse(String status, String message, Object data, String errorCode) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
		this.errorCode = errorCode;
	}

	public static WsResponse createSuccessResponse(String message, Object data){
		return new WsResponse("SUCCESS",message,data,null);
	}
	public static WsResponse createErrorResponse(String errorCode, String message, Object data){
		return new WsResponse("ERROR",message,data,errorCode);
	}
}
