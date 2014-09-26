/**
 * 类名称:   UserInfoDao
 * 类描述:   [UserInfoDao 数据库处理层]
 * 创建人:   ChenYong  
 * 创建时间:  2014年9月26日 下午2:46:35
 */
package com.matrixloop.timecute.business.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.matrixloop.timecute.app.db.mysql.MySqlDBIns;
import com.matrixloop.timecute.app.db.mysql.MySqlDBIns.Operation;
import com.matrixloop.timecute.business.user.bean.UserInfoBean;

import com.matrixloop.timecute.utils.log.*;
/**
 * @author ChenYong
 * 
 */
public enum UserInfoDao {

	INSTANCE;

	private static final String dbNamePrefix = "timecute";
	private static final String tableName = "user_info";

	public UserInfoBean get(long id) throws SQLException {
		Connection conn = MySqlDBIns.INSTANCE.getDBConnection(Operation.Read);
		if (conn == null) {
			throw new SQLException("Can't connect to database for " + dbNamePrefix);
		}

		UserInfoBean bean = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT * FROM `" + dbNamePrefix + "`.`" + tableName + "` WHERE `uid`=?";
			ps = conn.prepareStatement(sql);
			ps.setLong(1, id);

			SLogger.debug(ps);
			rs = ps.executeQuery();

			if (rs.next()) {
				bean = new UserInfoBean();
				bean.setUid(rs.getLong("uid"));
				bean.setNickname(rs.getString("nickname"));
				bean.setDesc(rs.getString("desc"));
				bean.setAvatar(rs.getString("avatar"));
				bean.setSex(rs.getInt("sex"));
				bean.setEmail(rs.getString("email"));
				bean.setBirth(rs.getString("birth"));
				bean.setPhone(rs.getString("phone"));
				bean.setAddress(rs.getString("address"));
				bean.setBackground(rs.getString("background"));
				bean.setExtraInfo(rs.getString("extra_info"));
			}
			MySqlDBIns.INSTANCE.release(conn, ps, rs);
		} finally {
			MySqlDBIns.INSTANCE.release(conn, ps, rs);
		}
		return bean;
	}
	
	public boolean quickInsert(long uid,String nickname) throws SQLException{
		Connection conn = MySqlDBIns.INSTANCE.getDBConnection(Operation.Write);
		if (conn == null) {
			throw new SQLException("Can't connect to database for " + dbNamePrefix);
		}

		boolean rst = false;
		PreparedStatement ps = null;
		try {
			String sql = "INSERT INTO `" + dbNamePrefix + "`.`" + tableName + "` " + 
					"(`uid`,`nickname`)"
					+ " VALUES (?,?);";

			ps = conn.prepareStatement(sql);
			ps.setLong(1, uid);
			ps.setString(2, nickname);

			SLogger.debug(ps);

			rst = ps.executeUpdate() > 0;
			MySqlDBIns.INSTANCE.release(conn, ps, null);
		} finally {
			MySqlDBIns.INSTANCE.release(conn, ps, null);
		}
		return rst;
	}
	
	public boolean insert(UserInfoBean bean) throws SQLException {

		Connection conn = MySqlDBIns.INSTANCE.getDBConnection(Operation.Write);
		if (conn == null) {
			throw new SQLException("Can't connect to database for " + dbNamePrefix);
		}

		boolean rst = false;
		PreparedStatement ps = null;
		try {
			String sql = "INSERT INTO `" + dbNamePrefix + "`.`" + tableName + "` " + 
					"(`uid`,`nickname`,`desc`,`avatar`,`sex`,`email`,`birth`,`phone`,`address`,`background`,`extra_info`)"
					+ " VALUES (?,?,?,?,?,?,?,?,?,?);";

			ps = conn.prepareStatement(sql);
			ps.setLong(1, bean.getUid());
			ps.setString(2, bean.getNickname());
			ps.setString(3, bean.getDesc());
			ps.setString(4, bean.getAvatar());
			ps.setInt(5, bean.getSex());
			ps.setString(6, bean.getEmail());
			ps.setString(7, bean.getBirth());
			ps.setString(8, bean.getPhone());
			ps.setString(9, bean.getAddress());
			ps.setString(10, bean.getBackground());
			ps.setString(11, bean.getExtraInfo());

			SLogger.debug(ps);

			rst = ps.executeUpdate() > 0;
			MySqlDBIns.INSTANCE.release(conn, ps, null);
		} finally {
			MySqlDBIns.INSTANCE.release(conn, ps, null);
		}
		return rst;
	}

	public boolean update(UserInfoBean bean) throws SQLException {
		Connection conn = MySqlDBIns.INSTANCE.getDBConnection(Operation.Write);
		if (conn == null) {
			throw new SQLException("Can't connect to database for " + dbNamePrefix);
		}

		boolean rst = false;
		PreparedStatement ps = null;
		try {
			String sql = "UPDATE `" + dbNamePrefix + "`.`" + tableName + "` " + "SET `nickname`=?,`desc`=?,"
					+ "`avatar`=?,`sex`=?,`email`=?,`birth`=?,`phone`=?,`address`=?,`background`=?,`extra_info`=? WHERE `uid`=?;";

			ps = conn.prepareStatement(sql);
			
			ps.setString(1, bean.getNickname());
			ps.setString(2, bean.getDesc());
			ps.setString(3, bean.getAvatar());
			ps.setInt(4, bean.getSex());
			ps.setString(5, bean.getEmail());
			ps.setString(6, bean.getBirth());
			ps.setString(7, bean.getPhone());
			ps.setString(8, bean.getAddress());
			ps.setString(9, bean.getBackground());
			ps.setString(10, bean.getExtraInfo());
			ps.setLong(11, bean.getUid());

			SLogger.debug(ps);

			rst = ps.executeUpdate() > 0;
			MySqlDBIns.INSTANCE.release(conn, ps, null);
		} finally {
			MySqlDBIns.INSTANCE.release(conn, ps, null);
		}
		return rst;
	}
	
	
	public long getMaxUid() throws SQLException {
		Connection conn = MySqlDBIns.INSTANCE.getDBConnection(Operation.Read);
		if (conn == null) {
			throw new SQLException("Can't connect to database for " + dbNamePrefix);
		}

		long maxUid = 1L;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT max(`uid`) as `max_uid` FROM `" + dbNamePrefix + "`.`" + tableName +";";
			ps = conn.prepareStatement(sql);

			SLogger.debug(ps);
			rs = ps.executeQuery();

			if (rs.next()) {
				maxUid = rs.getLong("max_uid");
			}
			MySqlDBIns.INSTANCE.release(conn, ps, rs);
		} finally {
			MySqlDBIns.INSTANCE.release(conn, ps, rs);
		}
		return maxUid;
	}

}