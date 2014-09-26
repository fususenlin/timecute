package com.matrixloop.timecute.api;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;

import com.matrixloop.timecute.app.idcontrol.UID;
import com.matrixloop.timecute.business.user.bean.UserAccountBean;
import com.matrixloop.timecute.business.user.bean.UserInfoBean;
import com.matrixloop.timecute.business.user.bean.UserPwdBean;
import com.matrixloop.timecute.business.user.service.UserService;
import com.matrixloop.timecute.common.Param;
import com.matrixloop.timecute.utils.MD5;
import com.matrixloop.timecute.utils.X;
import com.matrixloop.timecute.utils.http.ResponseBuilder;
import com.matrixloop.timecute.utils.http.ResponseCode;
import com.matrixloop.timecute.utils.http.ResponseStatusBuilder;
import com.matrixloop.timecute.utils.log.SLogger;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/user")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
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
			if("userInfo".equals(action)){
				HttpSession session = ((HttpServletRequest) request).getSession();
				if(null == session.getAttribute(Param.User.UID)){
					result = ResponseStatusBuilder.json(ResponseCode.NOT_LOGIN);
				}else{
					long uid = (Long) session.getAttribute(Param.User.UID);
					UserInfoBean userInfo = UserService.INSTANCE.getUserInfo(uid);
					if(null == userInfo){
						result = ResponseStatusBuilder.json(ResponseCode.DATA_NOT_EXIST);
					}else{						
						result = ResponseStatusBuilder.json(ResponseCode.NORMAL_RETURNED,userInfo.toJson());
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
		request.setCharacterEncoding(X.UTF8);
		response.setCharacterEncoding(X.UTF8);

		StringBuffer result = null;	
		try{
			String action = request.getParameter(Param.Common.ACTION);// 处理方法
			if("login".equals(action)){
				String username = StringUtils.trim(request.getParameter("username"));
				String password = StringUtils.trim(request.getParameter("password"));
				String remember = request.getParameter("remember");// 用于设置cookie过期时间
				UserAccountBean account = null;
				if(StringUtils.isBlank(username)){
					result = ResponseStatusBuilder.json(ResponseCode.PARAMAS_ERROR, Param.User.USERNAME +" is blank");
				}else if(StringUtils.isBlank(password)){
					result = ResponseStatusBuilder.json(ResponseCode.PARAMAS_ERROR, Param.User.PASSWORD +" is blank");
				}else if(null == (account = UserService.INSTANCE.getUserAccountBy(username))){
					result = ResponseStatusBuilder.json(ResponseCode.PARAMAS_ERROR, username +" not exist");
				}else if(!UserService.INSTANCE.checkPwd(account.getUid(), password)){
					result = ResponseStatusBuilder.json(ResponseCode.PARAMAS_ERROR, Param.User.PASSWORD +" error");
				}else{
					UserService.INSTANCE.updateLastLoginTime(account.getUid());
					//cokie
					Map<String, String> cookieMap = new HashMap<String, String>();
					cookieMap.put(Param.User.USERNAME, URLEncoder.encode(username, "UTF-8"));
					Set<String> keys = cookieMap.keySet();
					for (String k : keys) {
						Cookie cookie = new Cookie(k, cookieMap.get(k));
						cookie.setHttpOnly(true);
						if (remember.equals("true")) {
							cookie.setMaxAge(X.Http.cookieExpireTime);
						}
						cookie.setPath("/");
						response.addCookie(cookie);
						cookie = null;
					}
					cookieMap = null;
					
					//session
					HttpSession session = ((HttpServletRequest) request).getSession();
					session.setAttribute(Param.User.UID, account.getUid());
					
					result = ResponseStatusBuilder.json(ResponseCode.NORMAL_RETURNED);
				}
			}else if("register".equals(action)){
				String username = StringUtils.trim(request.getParameter(Param.User.USERNAME));
				String password = StringUtils.trim(request.getParameter(Param.User.PASSWORD));
				if(StringUtils.isBlank(username)){
					result = ResponseStatusBuilder.json(ResponseCode.PARAMAS_ERROR, Param.User.USERNAME +" is blank");
				}else if(StringUtils.isBlank(password)){
					result = ResponseStatusBuilder.json(ResponseCode.PARAMAS_ERROR, Param.User.PASSWORD +" is blank");
				}else if(null != UserService.INSTANCE.getUserAccountBy(username)){
					result = ResponseStatusBuilder.json(ResponseCode.PARAMAS_ERROR, "username:" +username+" has been used");
				}else{
					long uid = UID.get();
					UserAccountBean account = new UserAccountBean();
					account.setUid(uid);
					account.setUsername(username);
					
					UserPwdBean userPwd = new UserPwdBean();
					userPwd.setPassword(MD5.encode2String(password));
					userPwd.setUid(uid);
					
					if(!UserService.INSTANCE.quickRegister(account, userPwd)){
						result = ResponseStatusBuilder.json(ResponseCode.DATABASE_ERROR);
					}else{
						result = ResponseStatusBuilder.json(ResponseCode.NORMAL_RETURNED);
					}
				}
			} else if("update_pwd".equals(action)){
				String password = StringUtils.trim(request.getParameter(Param.User.PASSWORD));
				String newPassword = StringUtils.trim(request.getParameter("newPassword"));
				HttpSession session = ((HttpServletRequest) request).getSession();
				
				if(StringUtils.isBlank(password)){
					result = ResponseStatusBuilder.json(ResponseCode.PARAMAS_ERROR, Param.User.PASSWORD +" is blank");
				}else if(StringUtils.isBlank(newPassword)){
					result = ResponseStatusBuilder.json(ResponseCode.PARAMAS_ERROR, "new password is blank");
				}else if(null == session.getAttribute(Param.User.UID)){
					result = ResponseStatusBuilder.json(ResponseCode.NOT_LOGIN);
				}else{
					long uid = (Long) session.getAttribute(Param.User.UID);
					if(!UserService.INSTANCE.checkPwd(uid, MD5.encode2String(password))){
						result = ResponseStatusBuilder.json(ResponseCode.PARAMAS_ERROR,"password error");
					}else if(!UserService.INSTANCE.updatePassword(uid, MD5.encode2String(newPassword))){
						result = ResponseStatusBuilder.json(ResponseCode.DATABASE_ERROR);
					}else{
						result = ResponseStatusBuilder.json(ResponseCode.NORMAL_RETURNED);
					}
				}	
			}else if("logout".equals(action)){
				HttpSession session = ((HttpServletRequest) request).getSession();
				session.removeAttribute(Param.User.UID);
				if(null != session.getAttribute(Param.User.UID)){
					result = ResponseStatusBuilder.json(ResponseCode.SERVER_ERROR);
				}else{
					result = ResponseStatusBuilder.json(ResponseCode.NORMAL_RETURNED);
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

}
