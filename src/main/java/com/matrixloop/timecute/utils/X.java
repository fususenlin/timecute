package com.matrixloop.timecute.utils;

/**
  * 类名称:   X
  * 类描述:   [通用常量，避免魔法变量]
  * 创建人:   ChenYong  
  * 创建时间:  2014年9月12日 上午11:57:18
 */


/**
 * @author ChenYong
 *
 */
public interface X {
	
	String UTF8 = "utf-8";
	
	
	interface STATUS {
		int NORMAL = 1;
		int DELETE = 0;
	}
	
	interface Http {
		String GET = "GET";
		String POST = "POST";
		String CTP_GZIP = "gzip";
		String CTP_TEXT_HTML = "text/html";
		String CTP_TEXT_XML = "text/xml";
		String CTP_TEXT_JSON = "application/json";
		String HD_Referer = "Referer";
		String CONTENT_ENCODING = "Content-Encoding";
		String USER_AGENT = "user-agent";
		
		int cookieExpireTime = 7 * 24 * 60 * 60;
	}
}
