package com.jackcc.db;

import com.jackcc.util.TypeConversion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.jackcc.db.LibFunctionSelfSim.getSim;
import static com.jackcc.util.HashConvert.byte2str;
import static com.jackcc.util.SimilarityOperation.intersection;
import static com.jackcc.util.StrandStatistics.*;


public class TargetSimProcess {

    private JdbcDao db;
    private Connection conn;

    public TargetSimProcess () {
        this.db	= new JdbcDao();
        this.conn = this.db.getConnection();
    }

//    public Double getTargetSelfSim(ArrayList<String> target) throws SQLException {
//        Integer count = target.size();
//        Integer max = 0 ;
//        Integer tmp = count / 1000;
//        if (tmp>0){
//            max = tmp;
//        }else {
//            max = 1;
//        }
//        Integer index = 0;
//        ArrayList<BigInteger> targetProbList = new ArrayList<>();
//        while (max>0) {
//            String sql = "SELECT probability_reverse FROM strand_statistic WHERE strand = \'" + target.get(0) +"\'";
//            for(int j = 1+index; j < count &&j < index+999; j++){
//                String item = target.get(j);
//                sql = sql +" OR strand = \'" + item +"\'";
//
//            }
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            ResultSet result = pstmt.executeQuery();
//
//            while (result.next()){
//                // need to optimization!!! BigInt to INT to BigInt
//                Integer integer = Integer.valueOf(result.getInt("probability_reverse"));
//                BigInteger probabilityItem = new BigInteger(String.valueOf(integer));
//                targetProbList.add(probabilityItem);
//            }
//            max--;
//        }
//        double sim = getSim(targetProbList);
//        return sim;
//    }

    public Double getTargetSelfSim(ArrayList<String> target) throws SQLException, IOException, ClassNotFoundException {

        HashMap<String, BigInteger> targetProbMap = getProbabilityReverseOfLibStrand(getCountOfStrand(target), getSizeOfLib());

        TypeConversion typeConversion = new TypeConversion();
        ArrayList<BigInteger> targetProbList = typeConversion.hashMapValue2list(targetProbMap);
        double targetSelfSim = getSim(targetProbList);

        return targetSelfSim;
    }



    public static Double getRelativelySim(Double targetSelfSim, Double querySelfSim, Double intersectionSim){
        return intersectionSim/Math.sqrt(targetSelfSim*querySelfSim);
    }

    public static ArrayList<Double> getRelativelySim(Double targetSelfSim, ArrayList<Double> querySelfSim, ArrayList<Double> intersectionSim){
        ArrayList<Double> simList = new ArrayList<>();
        double sim = 0;
        Integer size = querySelfSim.size();
        if(querySelfSim.size() == intersectionSim.size()){
            for(int i = 0; i < size; i++){
                sim = intersectionSim.get(i)/Math.sqrt(querySelfSim.get(i)*targetSelfSim);
                simList.add(sim);            }
        }
        else {
            return null;
        }
        return simList;
    }

    public void calcSim(String func_name,ArrayList<String> targetHash) throws SQLException, IOException, ClassNotFoundException {
        //      Init db connection for function strands
        JdbcDao db = new JdbcDao();
        Connection conn = db.getConnection();
        LibFunctionSelfSim libFunctionSelfSim = new LibFunctionSelfSim();

        String sql = "SELECT id, func_name,strands FROM function_strands";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet result = pstmt.executeQuery();
        ArrayList<ArrayList<byte[]>> libStrands = new ArrayList<>();
        Double targetSim= this.getTargetSelfSim(targetHash);

        while (result.next()) {
            ObjectInputStream in = new ObjectInputStream(result.getBinaryStream("strands"));
            ArrayList<byte[]> strands = (ArrayList<byte[]>) in.readObject();
            in.close();
            ArrayList<String> query = byte2str(strands);
            ArrayList<String> intersect = intersection(query,targetHash);

            if (intersect.size()>0){
                Double intersectSim = this.getTargetSelfSim(intersect);
                Double querySim = libFunctionSelfSim.getFunctionSelfSim(result.getInt("id"));
                Double sim =  getRelativelySim(targetSim,querySim,intersectSim);

                if (sim > 0.5) {
                    System.out.println( func_name + "  >>  " +result.getString("func_name") + " >> " +sim);
                }
            }

        }

    }

}
