package com.matrixloop.timecute.app.idcontrol.service;

import java.sql.SQLException;

import com.matrixloop.timecute.app.idcontrol.IDType;
import com.matrixloop.timecute.app.idcontrol.cacher.IDCacher;
import com.matrixloop.timecute.business.user.dao.UserInfoDao;

public enum IDService {
	
	INSTANCE;
	
	public long get(IDType type) throws SQLException{
		if(!IDCacher.INSTANCE.exists(type)){
			if(type == IDType.UID){
				long maxUid = UserInfoDao.INSTANCE.getMaxUid();
				if(maxUid > 0){
					IDCacher.INSTANCE.put(maxUid, type);
				}
			}
		}
		return IDCacher.INSTANCE.incrAndGet(type);
	}
	
	
}
