package com.jackcc.db;

public class TargetFunctionSim {

	private final Object conn;
	private final JdbcDao db;

	public TargetFunctionSim () {
		this.db	= new JdbcDao();
		this.conn = this.db.getConnection();
	}

	
}
