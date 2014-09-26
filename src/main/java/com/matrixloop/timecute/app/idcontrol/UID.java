package com.matrixloop.timecute.app.idcontrol;

import java.sql.SQLException;

import com.matrixloop.timecute.app.idcontrol.service.IDService;

public class UID {
	
	private UID(){}
	
	public static long get() throws SQLException{
		return IDService.INSTANCE.get(IDType.UID);
	}
}
