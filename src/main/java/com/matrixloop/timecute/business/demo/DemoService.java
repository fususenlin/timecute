package com.matrixloop.timecute.business.demo;

import java.sql.SQLException;

public enum DemoService {
	
	INSTANCE;
	
	public DemoBean get(long id) throws SQLException{
		/*
		if (id <= 0) {
			return null;
		}
		DemoBean demo = DemoCache.INSTANCE.get(id);
		if (demo == null) {
			demo = DemoDao.INSTANCE.get(id);
			if (demo != null) {
				DemoCache.INSTANCE.put(id, demo);
			}
		}
		return demo;*/
		return DemoDao.INSTANCE.get(id);
	}
	
}
