package com.jackcc.db;

import com.jackcc.util.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;

import static com.jackcc.util.HashConvert.byte2str;
import static com.jackcc.util.HashConvert.calcHash;

public class FunctionOption {

	public JdbcDao db;
	public Connection conn;

	public FunctionOption() {
		this.db = new JdbcDao();
		this.conn = this.db.getConnection();
	}

	public void add(String funcName, ArrayList<byte[]> funcStrands)
			throws SQLException, IOException, NoSuchAlgorithmException {
		String sql =
				"INSERT INTO function_strands ('func_name', 'strands', 'hash') VALUES (?, ?, ?)";

		PreparedStatement pstmt = conn.prepareStatement(sql);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(funcStrands);
		byte[] bytes = baos.toByteArray();

		String funcHash = calcHash(HashConvert.byte2str(funcStrands));

		// set parameters
		pstmt.setString(1, funcName);
		pstmt.setBytes(2, bytes);
		pstmt.setString(3, funcHash);
		pstmt.executeUpdate();
	}

	public ArrayList<byte[]> getStrands(int funcID)
			throws SQLException, IOException, ClassNotFoundException {
		String sql = "SELECT strands FROM function_strands WHERE id=?";
		ResultSet result = null;

		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setInt(1, funcID);
		result = pstmt.executeQuery();

		ObjectInputStream in = new ObjectInputStream(result.getBinaryStream("strands"));

		ArrayList<byte[]> strands = (ArrayList<byte[]>) in.readObject();
		in.close();

		return strands;
	}

	public ArrayList<byte[]> getStrands(String funcName)
			throws SQLException, IOException, ClassNotFoundException {
		String sql = "SELECT strands FROM function_strands WHERE func_name=?";
		ResultSet result = null;

		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, funcName);
		result = pstmt.executeQuery();

		ObjectInputStream in = new ObjectInputStream(result.getBinaryStream("strands"));

		ArrayList<byte[]> strands = (ArrayList<byte[]>) in.readObject();
		in.close();
		return strands;
	}

	public String getFuncName(int funcID)
			throws SQLException, IOException, NoSuchAlgorithmException, ClassNotFoundException {
		String sql = "SELECT func_name FROM function_strands WHERE id=?";
		ResultSet result = null;
		String funcName = new String();
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setInt(1, funcID);
		result = pstmt.executeQuery();

		while (result.next()) {
			funcName = result.getString("func_name");
		}

		return funcName;
	}

	public String getFuncName(ArrayList<byte[]> funcStrands)
			throws SQLException, IOException, NoSuchAlgorithmException, ClassNotFoundException {
		String sql = "SELECT func_name FROM function_strands WHERE hash=?";
		ResultSet result = null;
		String funcName = new String();
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, calcHash(byte2str(funcStrands)));
		result = pstmt.executeQuery();

		while (result.next()) {
			funcName = result.getString("func_name");
		}

		return funcName;
	}

	public void close() throws SQLException {
		this.conn.close();

	}

	/**
	 * From Lib get all strands Set
	 * @return All strands Set
	 * */
	public ArrayList<ArrayList<byte[]>> getLibStrands()
			throws SQLException, IOException, ClassNotFoundException {
		String sql = "SELECT strands FROM function_strands";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet result = pstmt.executeQuery();
		ArrayList<ArrayList<byte[]>> libStrands = new ArrayList<>();
		while (result.next()) {
			ObjectInputStream in = new ObjectInputStream(result.getBinaryStream("strands"));
			ArrayList<byte[]> strands = (ArrayList<byte[]>) in.readObject();
			in.close();
			libStrands.add(strands);
		}
		return libStrands;
	}

	public ArrayList<byte[]> getLibStrandsArray()
			throws SQLException, IOException, ClassNotFoundException {
		String sql = "SELECT strands FROM function_strands";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet result = pstmt.executeQuery();
		ArrayList<byte[]> libStrands = new ArrayList<>();
		while (result.next()) {
			ObjectInputStream in = new ObjectInputStream(result.getBinaryStream("strands"));
			ArrayList<byte[]> strands = (ArrayList<byte[]>) in.readObject();
			in.close();
			libStrands.addAll(strands);
		}

		return libStrands;
	}

}
