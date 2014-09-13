package com.matrixloop.timecute.utils.http;

/**
  * 类名称:   ResponseCode
  * 类描述:   [返回的状态码]
  * 创建人:   ChenYong  
  * 创建时间:  2014年9月12日 下午12:03:40
 */

/**
 * @author ChenYong
 *
 */
public enum ResponseCode {
	
	NORMAL_RETURNED(1000, "OK"), 
	
	//ERROR
	REQUEST_TYPE_ERROR(1001, "Request Type Error"), 
	PARAMAS_ERROR(1002, "Parameters Error"), 
	JSONFORMAT_ERROR(1003, "Json Format Error"), 
	DATABASE_ERROR(1004, "Database Error"),
	SERVER_ERROR(1005, "Server Error"), 
	UNKNOWN_ERROR(1006, "Unknow Error"), 
	
	//
	DATA_NOT_EXIST(2001, "data not exist"),
	FORBIDDEN(2002, "Access Forbidden"),
	NOT_LOGIN(2003, "NOT Login");
	
	private int code;
	private String desc;
	
	ResponseCode(int code, String desc){
		this.code = code;
		this.desc = desc;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
