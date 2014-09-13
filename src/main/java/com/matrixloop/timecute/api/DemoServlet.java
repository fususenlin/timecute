package com.matrixloop.timecute.api;



import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;

import com.matrixloop.timecute.business.demo.DemoBean;
import com.matrixloop.timecute.business.demo.DemoService;
import com.matrixloop.timecute.common.Param;
import com.matrixloop.timecute.utils.X;
import com.matrixloop.timecute.utils.http.ResponseBuilder;
import com.matrixloop.timecute.utils.http.ResponseCode;
import com.matrixloop.timecute.utils.http.ResponseStatusBuilder;
import com.matrixloop.timecute.utils.log.*;

@WebServlet("/demo")
public class DemoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DemoServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding(X.UTF8);
		response.setCharacterEncoding(X.UTF8);

		StringBuffer result = null;	
		try{
			String action = request.getParameter(Param.Common.ACTION);// 处理方法
			if("get".equals(action)){
				String idStr = request.getParameter(Param.Demo.ID);
				if(!StringUtils.isNumeric(idStr)){
					result = ResponseStatusBuilder.json(ResponseCode.PARAMAS_ERROR, Param.Demo.ID);
				}else{
					long id = Long.parseLong(idStr);
					DemoBean demo = DemoService.INSTANCE.get(id);
					if(null != demo){
						result = ResponseStatusBuilder.json(ResponseCode.NORMAL_RETURNED,demo.toJson());
					}else{
						result = ResponseStatusBuilder.json(ResponseCode.DATA_NOT_EXIST);
					}
				}
			}else{
				result = ResponseStatusBuilder.json(ResponseCode.PARAMAS_ERROR, Param.Common.ACTION);
			}
		}catch (SQLException e){
			result = ResponseStatusBuilder.json(ResponseCode.DATABASE_ERROR);
			SLogger.error(e,e);
		}catch (JSONException e){
			result = ResponseStatusBuilder.json(ResponseCode.JSONFORMAT_ERROR);
			SLogger.error(e,e);
		}catch (Exception e){
			result = ResponseStatusBuilder.json(ResponseCode.SERVER_ERROR);
			SLogger.error(e,e);
		}
		ResponseBuilder.sendZipedResponse(response, result);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
