package com.matrixloop.timecute.business.demo;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.matrixloop.timecute.app.db.mysql.MySqlDBIns;

import com.matrixloop.timecute.utils.log.*;


public enum DemoDao {
	
	INSTANCE;
	
	private static final String dbNamePrefix = "timecute";
	private static final String tableName = "demo";
	
	public DemoBean  get(long id) throws SQLException {
		Connection conn = MySqlDBIns.INSTANCE.getDBConnection(MySqlDBIns.Operation.Read);
		if (conn == null) {
			throw new SQLException("Can't connect to database for timecute");
		}
		DemoBean bean = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			String sql = "SELECT * from "+dbNamePrefix+"."+tableName+" WHERE `id`=?;";
			ps=conn.prepareStatement(sql);
			ps.setLong(1, id);
			
			SLogger.debug(ps);
			rs = ps.executeQuery();
			if(rs.next()){
				bean = new DemoBean();
				bean.setId(rs.getLong("id"));
				bean.setName(rs.getString("name"));
			}
			MySqlDBIns.INSTANCE.release(conn, ps, rs);
		}finally{
			MySqlDBIns.INSTANCE.release(conn, ps, rs);
		}
		return bean;
	}
	
	
}
