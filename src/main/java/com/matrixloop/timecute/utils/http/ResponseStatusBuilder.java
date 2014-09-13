package com.matrixloop.timecute.utils.http;

/**
  * 类名称:   ResponseStatusBuilder
  * 类描述:   [一句话描述该类的功能]
  * 创建人:   ChenYong  
  * 创建时间:  2014年9月12日 下午12:11:17
 */


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * @author ChenYong
 *
 */
public class ResponseStatusBuilder {
	
	private static final StringBuffer json_error = new StringBuffer("{\"desc\":\"build json failed\",\"status\":1007}");
	private static String json_status = "status";
	private static String json_desc = "desc";
	private static String json_data = "data";
	private static String json_page = "page";
	private static String json_total = "total";
	
	public static StringBuffer json(int code, String desc) {
		JSONObject resp = new JSONObject();
		try {
			resp.put(json_status, code);
			resp.put(json_desc, desc);
		} catch (JSONException e) {
			return json_error;
		}
		return new StringBuffer(resp.toString());
	}
	public static StringBuffer json(ResponseCode responseCode) {
		return json(responseCode.getCode(), responseCode.getDesc());
	}
	
	public static StringBuffer json(ResponseCode responseCode,String desc) {
		return json(responseCode.getCode(), responseCode.getDesc()+desc);
	}
	
	public static StringBuffer json(int code, String desc,JSONObject job) {
		JSONObject resp = new JSONObject();
		try {
			resp.put(json_status, code);
			resp.put(json_desc, desc);
			resp.put(json_data, job);
		} catch (JSONException e) {
			return json_error;
		}
		return new StringBuffer(resp.toString());
	}
	
	public static StringBuffer json(ResponseCode responseCode,JSONObject job) {
		return json(responseCode.getCode(), responseCode.getDesc(),job);
	}
	
	public static StringBuffer json(int code, String desc,JSONArray jar) {
		JSONObject resp = new JSONObject();
		try {
			resp.put(json_status, code);
			resp.put(json_desc, desc);
			resp.put(json_data, jar);
		} catch (JSONException e) {
			return json_error;
		}
		return new StringBuffer(resp.toString());
	}
	
	public static StringBuffer json(int code, String desc, int page, int total, JSONArray jar) {
		JSONObject resp = new JSONObject();
		try {
			resp.put(json_status, code);
			resp.put(json_desc, desc);
			resp.put(json_page, page);
			resp.put(json_total, total);
			resp.put(json_data, jar);
		} catch (JSONException e) {
			return json_error;
		}
		return new StringBuffer(resp.toString());
	}
	
	public static StringBuffer json(ResponseCode responseCode, int page, int total, JSONArray jar) {
		JSONObject resp = new JSONObject();
		try {
			resp.put(json_status, responseCode.getCode());
			resp.put(json_desc, responseCode.getDesc());
			resp.put(json_page, page);
			resp.put(json_total, total);
			resp.put(json_data, jar);
		} catch (JSONException e) {
			return json_error;
		}
		return new StringBuffer(resp.toString());
	}
	
}
