package com.jackcc.db;

import com.jackcc.util.TypeConversion;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class StrandOperation {

    private JdbcDao db;
    private Connection conn;

    public StrandOperation () {
        this.db	= new JdbcDao();
        this.conn = this.db.getConnection();
    }

    // add strand statistics to table
    public void add(HashMap countStrandMap, HashMap probabilityReverseMap)
            throws SQLException {

        TypeConversion<String, Integer> typeInt = new TypeConversion<>();
        TypeConversion<String, BigInteger> typeBigint = new TypeConversion<>();
        ArrayList<String> strandList = typeInt.hashMapKey2list(countStrandMap);
        ArrayList<Integer> countList = typeInt.hashMapValue2list(countStrandMap);
        ArrayList<BigInteger> probabilityReverseList = typeBigint.hashMapValue2list(probabilityReverseMap);

        String sql = "INSERT INTO strand_statistic ('strand', 'count', 'probability_reverse') VALUES (?, ?, ?)";

        for(int i=1; i < strandList.size(); i++){
            sql = sql + ",(?, ?, ?)";
        }
        PreparedStatement pstmt = conn.prepareStatement(sql);

        for(int i=0; i < strandList.size(); i++){
            // set parameters
            pstmt.setString(1+3*i, strandList.get(i));
            pstmt.setInt(2+3*i, countList.get(i));
            pstmt.setString(3+3*i, String.valueOf(probabilityReverseList.get(i)));
        }
        pstmt.executeUpdate();
    }
}
