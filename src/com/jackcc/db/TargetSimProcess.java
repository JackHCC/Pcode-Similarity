package com.jackcc.db;

<<<<<<< Updated upstream
import java.io.IOException;
=======
>>>>>>> Stashed changes
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.jackcc.db.LibFunctionSelfSim.getSim;
<<<<<<< Updated upstream
import static com.jackcc.util.HashConvert.byte2str;
=======
>>>>>>> Stashed changes

public class TargetSimProcess {

    private JdbcDao db;
    private Connection conn;

    public TargetSimProcess () {
        this.db	= new JdbcDao();
        this.conn = this.db.getConnection();
    }

    public Double getTargetSelfSim(ArrayList<String> target) throws SQLException {
        Integer count = target.size();
        Integer max = 0 ;
        Integer tmp = count / 1000;
        if (tmp>0){
            max = tmp;
        }else {
            max = 1;
        }
        Integer index = 0;
        ArrayList<BigInteger> targetProbList = new ArrayList<>();
        while (max>0) {
            String sql = "SELECT probability_reverse FROM strand_statistic WHERE strand = \'" + target.get(0) +"\'";
            for(int j = 1+index; j < count &&j < index+999; j++){
                String item = target.get(j);
                sql = sql +" OR strand = \'" + item +"\'";

            }
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet result = pstmt.executeQuery();

            while (result.next()){
                // need to optimization!!! BigInt to INT to BigInt
                Integer integer = Integer.valueOf(result.getInt("probability_reverse"));
                BigInteger probabilityItem = new BigInteger(String.valueOf(integer));
                targetProbList.add(probabilityItem);
            }
<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
            max--;
        }
        double sim = getSim(targetProbList);

        return sim;
    }

    public static Double getRelativelySim(Double targetSelfSim, Double querySelfSim, Double intersectionSim){
        return intersectionSim/Math.sqrt(targetSelfSim*querySelfSim);
    }
<<<<<<< Updated upstream

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

=======
>>>>>>> Stashed changes
}
