package com.matrixloop.timecute.business.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.matrixloop.timecute.app.db.mysql.MySqlDBIns;
import com.matrixloop.timecute.app.db.mysql.MySqlDBIns.Operation;
import com.matrixloop.timecute.utils.log.SLogger;

public enum UserPwdDao {
	
	INSTANCE;

	private static final String dbNamePrefix = "timecute";
	private static final String tableName = "user_pwd";

	public boolean get(long uid,String securityPwd) throws SQLException {
		Connection conn = MySqlDBIns.INSTANCE.getDBConnection(Operation.Read);
		if (conn == null) {
			throw new SQLException("Can't connect to database for " + dbNamePrefix);
		}

		boolean rst = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT count(*) as `count` FROM `" + dbNamePrefix + "`.`" + tableName + "` WHERE `uid`= ? AND `password`=?";
			ps = conn.prepareStatement(sql);
			ps.setLong(1, uid);
			ps.setString(2, securityPwd);

			SLogger.debug(ps);
			rs = ps.executeQuery();

			if (rs.next()) {
				rst = rs.getInt("count") == 1 ? true : false;
			}
			MySqlDBIns.INSTANCE.release(conn, ps, rs);
		} finally {
			MySqlDBIns.INSTANCE.release(conn, ps, rs);
		}
		return rst;
	}

	public boolean updatePwd(long uid,String newSecurityPwd) throws SQLException {

		Connection conn = MySqlDBIns.INSTANCE.getDBConnection(Operation.Write);
		if (conn == null) {
			throw new SQLException("Can't connect to database for " + dbNamePrefix);
		}

		boolean rst = false;
		PreparedStatement ps = null;
		try {
			String sql = "UPDATE `" + dbNamePrefix + "`.`" + tableName + "` " + "SET `password`=? WHERE `uid`=?;";

			ps = conn.prepareStatement(sql);
			ps.setString(1, newSecurityPwd);
			ps.setLong(2, uid);

			SLogger.debug(ps);

			rst = ps.executeUpdate() > 0;
			MySqlDBIns.INSTANCE.release(conn, ps, null);
		} finally {
			MySqlDBIns.INSTANCE.release(conn, ps, null);
		}
		return rst;
	}
	
	public boolean insert(long uid,String securityPwd) throws SQLException {
		Connection conn = MySqlDBIns.INSTANCE.getDBConnection(Operation.Write);
		if (conn == null) {
			throw new SQLException("Can't connect to database for " + dbNamePrefix);
		}

		boolean rst = false;
		PreparedStatement ps = null;
		try {
			String sql = "INSERT INTO `" + dbNamePrefix + "`.`" + tableName + "` " + "(`uid`,`password`) VALUES (?,?);";

			ps = conn.prepareStatement(sql);
			ps.setLong(1,uid);
			ps.setString(2, securityPwd);
			SLogger.debug(ps);

			rst = ps.executeUpdate() > 0;
			MySqlDBIns.INSTANCE.release(conn, ps, null);
		} finally {
			MySqlDBIns.INSTANCE.release(conn, ps, null);
		}
		return rst;
	}
}
