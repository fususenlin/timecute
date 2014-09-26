package com.matrixloop.timecute.business.user.service;

import java.sql.SQLException;

import com.matrixloop.timecute.business.user.bean.UserAccountBean;
import com.matrixloop.timecute.business.user.bean.UserInfoBean;
import com.matrixloop.timecute.business.user.bean.UserPwdBean;
import com.matrixloop.timecute.business.user.dao.UserAccountDao;
import com.matrixloop.timecute.business.user.dao.UserInfoDao;
import com.matrixloop.timecute.business.user.dao.UserPwdDao;
import com.matrixloop.timecute.utils.MD5;

public enum UserService {
	
	INSTANCE;
	
	public boolean checkPwd(long uid,String password) throws SQLException{
		return UserPwdDao.INSTANCE.get(uid, MD5.encode2String(password));
	}
	
	public UserAccountBean getUserAccountBy(String username) throws SQLException{
		return UserAccountDao.INSTANCE.get(username);
	}
	
	public boolean updateLastLoginTime(long uid) throws SQLException{
		return UserAccountDao.INSTANCE.updateLastLoginTime(uid);
	}
	
	public boolean quickRegister(UserAccountBean account,UserPwdBean userPwd) throws SQLException{
		return UserAccountDao.INSTANCE.insert(account) && UserPwdDao.INSTANCE.insert(userPwd.getUid(), userPwd.getPassword())
				&& UserInfoDao.INSTANCE.quickInsert(account.getUid(), account.getUsername());
	}
	
	public UserInfoBean getUserInfo(long uid) throws SQLException{
		return UserInfoDao.INSTANCE.get(uid);
	}
	
	public boolean updatePassword(long uid ,String newPassword) throws SQLException{
		return UserPwdDao.INSTANCE.updatePwd(uid, MD5.encode2String(newPassword));
	}
}
