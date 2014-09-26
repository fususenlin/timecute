package com.matrixloop.timecute.business.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.matrixloop.timecute.app.db.mysql.MySqlDBIns;
import com.matrixloop.timecute.app.db.mysql.MySqlDBIns.Operation;
import com.matrixloop.timecute.business.user.bean.UserAccountBean;
import com.matrixloop.timecute.utils.X;
import com.matrixloop.timecute.utils.log.SLogger;

public enum UserAccountDao {

	INSTANCE;

	private static final String dbNamePrefix = "timecute";
	private static final String tableName = "user_account";

	public UserAccountBean get(String username) throws SQLException {
		Connection conn = MySqlDBIns.INSTANCE.getDBConnection(Operation.Read);
		if (conn == null) {
			throw new SQLException("Can't connect to database for " + dbNamePrefix);
		}

		UserAccountBean bean = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT * FROM `" + dbNamePrefix + "`.`" + tableName + "` WHERE `username`= ? AND `status`=1";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);

			SLogger.debug(ps);
			rs = ps.executeQuery();

			if (rs.next()) {
				bean = new UserAccountBean();
				bean.setUid(rs.getLong("uid"));
				bean.setUsername(rs.getString("username"));
				bean.setLastLoginTime(rs.getLong("last_login_time"));
			}
			MySqlDBIns.INSTANCE.release(conn, ps, rs);
		} finally {
			MySqlDBIns.INSTANCE.release(conn, ps, rs);
		}
		return bean;
	}

	public boolean insert(UserAccountBean bean) throws SQLException {

		Connection conn = MySqlDBIns.INSTANCE.getDBConnection(Operation.Write);
		if (conn == null) {
			throw new SQLException("Can't connect to database for " + dbNamePrefix);
		}

		boolean rst = false;
		PreparedStatement ps = null;
		try {
			String sql = "INSERT INTO `" + dbNamePrefix + "`.`" + tableName + "` " + 
					"(`uid`,`username`,`create_time`,`last_login_time`,`status`)"
					+ " VALUES (?,?,?,?,?);";

			ps = conn.prepareStatement(sql);
			ps.setLong(1, bean.getUid());
			ps.setString(2, bean.getUsername());
			ps.setLong(3, System.currentTimeMillis());
			ps.setLong(4, System.currentTimeMillis());
			ps.setInt(5, X.STATUS.NORMAL);

			SLogger.debug(ps);

			rst = ps.executeUpdate() > 0;
			MySqlDBIns.INSTANCE.release(conn, ps, null);
		} finally {
			MySqlDBIns.INSTANCE.release(conn, ps, null);
		}
		return rst;
	}
	
	public boolean delete(long uid) throws SQLException{
		return updateStatus(uid,X.STATUS.DELETE);
	}
	public boolean recovery(long uid) throws SQLException{
		return updateStatus(uid,X.STATUS.NORMAL);
	}
	
	private boolean updateStatus(long uid,int status) throws SQLException {
		Connection conn = MySqlDBIns.INSTANCE.getDBConnection(Operation.Write);
		if (conn == null) {
			throw new SQLException("Can't connect to database for " + dbNamePrefix);
		}

		boolean rst = false;
		PreparedStatement ps = null;
		try {
			String sql = "UPDATE `" + dbNamePrefix + "`.`" + tableName + "` " + "SET `status`=? WHERE `uid`=?;";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, status);
			ps.setLong(2, uid);
			SLogger.debug(ps);

			rst = ps.executeUpdate() > 0;
			MySqlDBIns.INSTANCE.release(conn, ps, null);
		} finally {
			MySqlDBIns.INSTANCE.release(conn, ps, null);
		}
		return rst;
	}
	
	public boolean updateLastLoginTime(long uid) throws SQLException {
		Connection conn = MySqlDBIns.INSTANCE.getDBConnection(Operation.Write);
		if (conn == null) {
			throw new SQLException("Can't connect to database for " + dbNamePrefix);
		}

		boolean rst = false;
		PreparedStatement ps = null;
		try {
			String sql = "UPDATE `" + dbNamePrefix + "`.`" + tableName + "` " + "SET `last_login_time`=? WHERE `uid`=?;";

			ps = conn.prepareStatement(sql);
			ps.setLong(1, System.currentTimeMillis());
			ps.setLong(2, uid);
			SLogger.debug(ps);

			rst = ps.executeUpdate() > 0;
			MySqlDBIns.INSTANCE.release(conn, ps, null);
		} finally {
			MySqlDBIns.INSTANCE.release(conn, ps, null);
		}
		return rst;
	}
}
