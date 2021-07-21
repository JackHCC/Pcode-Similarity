package com.jackcc.db;

import com.jackcc.util.HashConvert;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class LibFunctionSelfSim {
    private JdbcDao db;
    private Connection conn;

    public LibFunctionSelfSim () {
        this.db	= new JdbcDao();
        this.conn = this.db.getConnection();
    }


    // ToDo:
    public void add(HashMap<String, Double> selfSimMap)
            throws SQLException, IOException, NoSuchAlgorithmException {
        String sql = "INSERT INTO function_selfSim ('id', 'hash', 'selfSim') VALUES (?, ?, ?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // set parameters
//        pstmt.setString(1,funcName);
//        pstmt.setBytes(2, bytes);
//        pstmt.setString(3,funcHash);
        pstmt.executeUpdate();
    }


    public HashMap<String, Double> getSelfSimMap() throws SQLException, IOException, ClassNotFoundException {
        HashMap<String, Double> selfSimMap = new HashMap<>();
        FunctionOption funcOp = new FunctionOption();
        ArrayList<ArrayList<byte []>> libStrands = funcOp.getLibStrands();

        String sql = "SELECT hash FROM function_strands";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet result = pstmt.executeQuery();

//        while (result.next()){
//            ObjectInputStream in  = new ObjectInputStream(result.getBinaryStream("strands"));
//            ArrayList<byte[]> strands = (ArrayList<byte[]>)in.readObject();
//            in.close();
//            libStrands.addAll(strands);
//        }

        return selfSimMap;
    }

}
