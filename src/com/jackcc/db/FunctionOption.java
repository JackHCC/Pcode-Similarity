package com.jackcc.db;

import com.jackcc.util.HashConvert;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;

public class FunctionOption {

	private JdbcDao db;
	private Connection conn;

	public FunctionOption () {
		this.db	= new JdbcDao();
		this.conn = this.db.getConnection();
	}

	public void add(String funcName,ArrayList<byte[]> funcStrands)
			throws SQLException, IOException, NoSuchAlgorithmException {
		String sql = "INSERT INTO function_strands ('func_name', 'strands', 'hash') VALUES (?, ?, ?)";

		PreparedStatement pstmt = conn.prepareStatement(sql);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(funcStrands);
		byte[] bytes = baos.toByteArray();

		String funcHash = HashConvert.calcHash(HashConvert.byte2str(funcStrands));

		// set parameters
		pstmt.setString(1,funcName);
		pstmt.setBytes(2, bytes);

		pstmt.setString(3,funcHash);

		pstmt.executeUpdate();
	}

	public ArrayList<byte[]> getStrands(int funcID)
			throws SQLException, IOException, NoSuchAlgorithmException, ClassNotFoundException {
		String sql = "SELECT strands FROM function_strands WHERE id=?";
		ResultSet result = null;

		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setInt(1, funcID);
		result = pstmt.executeQuery();

		ObjectInputStream in  = new ObjectInputStream(result.getBinaryStream("strands"));

		ArrayList<byte[]> strands = (ArrayList<byte[]>)in.readObject();
		in.close();

		return strands;
	}

	public ArrayList<byte []> getStrands(String funcName)
			throws SQLException, IOException, NoSuchAlgorithmException, ClassNotFoundException {
		String sql = "SELECT strands FROM function_strands WHERE func_name=?";
		ResultSet result = null;

		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, funcName);
		result = pstmt.executeQuery();

		ObjectInputStream in  = new ObjectInputStream(result.getBinaryStream("strands"));

		ArrayList<byte[]> strands = (ArrayList<byte[]>)in.readObject();
		in.close();
		return strands;
	}


}
