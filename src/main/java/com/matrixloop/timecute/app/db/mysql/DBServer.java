package com.matrixloop.timecute.app.db.mysql;


public enum DBServer {
	
	my_server_s("my_server_store"),
	my_server_m("my_server_store")
	;
	
	String dbName;
	
	public String getDatabaseName() {
		return this.dbName;
	}
	
	DBServer(String databaseName) {
		this.dbName = databaseName;
	}
}
