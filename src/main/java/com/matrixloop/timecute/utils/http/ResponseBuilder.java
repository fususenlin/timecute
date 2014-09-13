package com.matrixloop.timecute.utils.http;

/**
  * 类名称:   ResponseBuilder
  * 类描述:   [一句话描述该类的功能]
  * 创建人:   ChenYong  
  * 创建时间:  2014年9月12日 下午12:11:05
 */


import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.matrixloop.timecute.utils.X;



/**
 * @author ChenYong
 *
 */
public class ResponseBuilder {
	
	public static void sendZipedResponse(final HttpServletResponse response, final StringBuffer result) throws IOException{
		
		//使用Gzip压缩输出
		response.setHeader(X.Http.CONTENT_ENCODING, X.Http.CTP_GZIP);
		ServletOutputStream out = response.getOutputStream() ;
		GZIPOutputStream zipOut = new GZIPOutputStream(out) ;
		PrintWriter pw = new PrintWriter(zipOut);
		if(null == result){
			pw.print(new StringBuffer(ResponseStatusBuilder.json(ResponseCode.UNKNOWN_ERROR)));
		}else{
			pw.print(result);
		}
		
		pw.flush();
		
		pw.close();
		pw = null;
		zipOut.close() ;
		zipOut = null ;
		out.close() ;
		out = null ;
	}
	
	public static void sendResponse(final HttpServletResponse response, final StringBuffer result) throws IOException{
		response.setCharacterEncoding(X.UTF8);
		OutputStream out = response.getOutputStream() ;
		if(null == result){
			out.write(ResponseStatusBuilder.json(ResponseCode.UNKNOWN_ERROR).toString().getBytes("UTF-8"));
		}else{
			out.write(result.toString().getBytes("UTF-8"));
		}
		out.close() ;
		out = null ;
	}
}
