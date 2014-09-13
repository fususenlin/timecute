package com.matrixloop.timecute.app.db.mysql;

/**
  * 类名称:   MySqlDBIns
  * 类描述:   [一句话描述该类的功能]
  * 创建人:   ChenYong  
  * 创建时间:  2014年9月12日 下午3:37:40
 */


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.matrixloop.timecute.app.config.AppConfig;
import com.matrixloop.timecute.utils.log.*;

/**
 * @author ChenYong
 *
 */
public enum MySqlDBIns {
	INSTANCE;
	public static enum Operation {
		Read, Write;
	}

	public static enum ServerType {
		Master, Slave;
	}
	
	private static final ConcurrentHashMap<String, DataSource> dataSourceMap = new ConcurrentHashMap<String, DataSource>();
	
	private DataSource getDatasource(String serverName) {
		DataSource ds = dataSourceMap.get(serverName);
		if (ds == null) {
			Context ctx = null;
			try {
				ctx = new InitialContext();
				ds = (DataSource) ctx.lookup("java:comp/env/" + serverName);
				DataSource tmpDs = dataSourceMap.putIfAbsent(serverName, ds);
				if(null != tmpDs){
					ds = tmpDs;
				}
			} catch (NamingException e) {
				SLogger.error("InitialContext.lookup() Got NamingException at " + new Date(), e);
			} catch(Exception e){
				SLogger.error("put DataSource error at " + new Date(), e);
			}finally {
				if (ctx != null) {
					try {
						ctx.close();
					} catch (NamingException e) {
						SLogger.error("InitialContext.close() Got NamingException at " + new Date());
					}
				}
			}
		}
		return ds;
	}
	
	
	private static final int PORT = 3306;
	protected static String DB_USER;
	protected static String DB_PASSWORD;

	static {
		Properties p = new Properties();
		try {
			p.load(MySqlDBIns.class.getResourceAsStream(AppConfig.SystemConfig.SYSTEM_CONFIG_PATH));
			DB_USER = p.getProperty("mysql_username");
			DB_PASSWORD = p.getProperty("mysql_password");
		} catch (IOException e) {
			SLogger.error(e, e);
			throw new RuntimeException("can't find system configuration file , system starting failed !", e);
		} catch (Exception e) {
			SLogger.error(e, e);
			throw new RuntimeException("initial system failed !", e);
		} finally {
			p = null;
		}
	}
	
	public Connection getDBConnection(Operation operation) {
		DBServer server = null;
		if (Operation.Read == operation) {
			server = DBServer.my_server_s;
		} else {
			server = DBServer.my_server_m;
		}

		long start = System.currentTimeMillis();
		Connection conn = null;
		try {
			DataSource ds = getDatasource(server.name());
			if (ds != null) {
				conn = ds.getConnection();
			}
			if (conn == null) {
			    ds = getDatasource(server.name());
                if (ds != null) {
                    conn = ds.getConnection();
                }
				SLogger.warn("get sql db connection , return null");
				if (conn == null) {
					conn = getConnectionDirectly(server.name(), server.dbName);
					SLogger.warn("get sql db connection , return null");
					if (conn == null) {
						SLogger.error("get sql db connection , return null");
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SLogger.debug("SuishenMySqlDBIns.getSystemServerConnection(" + (System.currentTimeMillis() - start) + "): Operation=" + operation + " ; conn=" + conn);
		return conn;
	}
	
	/**
	 * 直接链接数据库
	 * **/
	protected Connection getConnectionDirectly(String host, String databaseName) {
		Connection conn = null;
		try {

			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://" + host + ":" + PORT + "/" + databaseName + "?user=" + DB_USER + "&password=" + DB_PASSWORD;
			conn = DriverManager.getConnection(url);
		} catch (Exception e) {
			SLogger.error(e, e);
		}
		return conn;
	}
	
	public boolean release(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.clearWarnings();
				conn.close();
			}
		} catch (SQLException e) {
			SLogger.error(e, e);
		} finally {
			conn = null;
		}
		return true;
	}

	public void release(Connection conn, PreparedStatement ps, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				SLogger.error(e, e);
			}
			rs = null;
		}
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				SLogger.error(e, e);
			}
			ps = null;
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				SLogger.error(e, e);
			}
			conn = null;
		}
	}
}
