package com.jackcc.db;

import com.jackcc.util.TypeConversion;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.jackcc.util.HashConvert.byte2str;

public class LibFunctionSelfSim {
    public JdbcDao db;
    public Connection conn;

    public LibFunctionSelfSim () {
        this.db	= new JdbcDao();
        this.conn = this.db.getConnection();
    }



    public void add(HashMap<String, Double> selfSimMap)
            throws SQLException, IOException, NoSuchAlgorithmException {

        TypeConversion<String, Double> typeConversion = new TypeConversion<>();
        ArrayList<String> functionHashList = typeConversion.hashMapKey2list(selfSimMap);
        ArrayList<Double> selfSimList = typeConversion.hashMapValue2list(selfSimMap);

        String sql = "INSERT INTO function_selfSim ('hash', 'selfSim') VALUES (?, ?)";

        Integer mapSize = functionHashList.size();
        System.out.println(mapSize);


        for (int i = 1; i < mapSize; i++) {
            sql = sql + ",(?, ?)";
        }
        PreparedStatement pstmt = conn.prepareStatement(sql);

        for (int i = 0; i < mapSize; i++) {
            // set parameters
            pstmt.setString(1 + 2 * i, functionHashList.get(i));
            pstmt.setDouble(2 + 2 * i, selfSimList.get(i));
        }
        pstmt.executeUpdate();
    }




    public HashMap<Integer, Double> getSelfSimMap() throws SQLException, IOException, ClassNotFoundException {

        String sql = "SELECT id FROM function_strands";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet result = pstmt.executeQuery();

        TypeConversion<Integer, Double> typeConversion = new TypeConversion();

        ArrayList<Integer> functionHashList = new ArrayList<>();
        while (result.next()){
            functionHashList.add(result.getInt("id"));
        }
        System.out.println("functionHashList:"+functionHashList.size());

        ArrayList<Double> selfSimList = calculateSelfSim();

        HashMap<Integer, Double> selfSimMap = typeConversion.list2hashMap(functionHashList, selfSimList);

        return selfSimMap;
    }

    public ArrayList<Double> calculateSelfSim() throws SQLException, IOException, ClassNotFoundException {
        FunctionOption funcOp = new FunctionOption();
        ArrayList<ArrayList<byte []>> libStrands = funcOp.getLibStrands();

        ArrayList<Double> selfSimList = new ArrayList<>();
        for(int i = 0; i < libStrands.size(); i++){
            ArrayList<String> functionStrands = byte2str(libStrands.get(i));

            Integer count = functionStrands.size();
            Integer max = 0 ;
            Integer tmp = count / 1000;
            if (tmp>0){
                max = tmp;
            }else {
                max = 1;
            }
            Integer index = 0;
            ArrayList<BigInteger> functionProbList = new ArrayList<>();
            while (max>0) {
                String sql = "SELECT probability_reverse FROM strand_statistic WHERE strand = \'" + functionStrands.get(0) +"\'";

                for(int j = 1+index; j < count &&j < index+999; j++){
                    String item = functionStrands.get(j);
                    sql = sql +" OR strand = \'" + item +"\'";

                }
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet result = pstmt.executeQuery();

                while (result.next()){

                    // need to optimization!!!
                    Integer integer = Integer.valueOf(result.getInt("probability_reverse"));
                    BigInteger probabilityItem = new BigInteger(String.valueOf(integer));
                    functionProbList.add(probabilityItem);
                }

                max--;
            }
            double sim = getSim(functionProbList);

            selfSimList.add(sim);

        }

        return selfSimList;
    }

    public static Double getLog(BigInteger bigInteger){
        return Math.log(bigInteger.doubleValue());
    }

    public static Double getSim(ArrayList<BigInteger> functionProbList){
        double sim = 0;
        for(BigInteger item: functionProbList){
            sim = sim + getLog(item);
        }
        return sim;
    }

    public Double getSelfSim(Integer funcId) throws SQLException, IOException, ClassNotFoundException {
        Double selfSim = null;
        String sql = "SELECT selfSim FROM functionID_selfSim WHERE id = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, funcId);

        ResultSet result = pstmt.executeQuery();

        while (result.next()){
            selfSim = result.getDouble("selfSim");
        }

        return selfSim;
    }




}

