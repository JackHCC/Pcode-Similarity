package com.jackcc.util;

import com.jackcc.db.FunctionOption;
import com.jackcc.db.LibFunctionSelfSim;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SimilarityOperation {


	public static ArrayList<String> intersection(ArrayList<String> queryHash,
			ArrayList<String> targetHash) {
		ArrayList<String> result = new ArrayList<>();
		ArrayList<String> tmp = new ArrayList<>();

		if (targetHash.size()>queryHash.size()) {
			tmp =queryHash;
			queryHash = targetHash;
			targetHash = tmp;
		}

		BloomFileter bloomFileter = new BloomFileter(queryHash.size());
		bloomFileter.addList(queryHash);

		for (int i = 0; i < targetHash.size(); i++) {
			String q = targetHash.get(i);
			if (bloomFileter.check(q)) {
				result.add(q);
			}
		}
		return result;
	}

	public static ArrayList<Double> calculateSelfSim(ArrayList<String> functionStrands) throws SQLException, IOException, ClassNotFoundException {
		LibFunctionSelfSim libFunctionSelfSim = new LibFunctionSelfSim();
		FunctionOption funcOp = new FunctionOption();
		ArrayList<ArrayList<byte []>> libStrands = funcOp.getLibStrands();

		ArrayList<Double> selfSimList = new ArrayList<>();

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
			PreparedStatement pstmt = funcOp.conn.prepareStatement(sql);
			ResultSet result = pstmt.executeQuery();

			while (result.next()){

				// need to optimization!!!
				Integer integer = Integer.valueOf(result.getInt("probability_reverse"));
				BigInteger probabilityItem = new BigInteger(String.valueOf(integer));
				functionProbList.add(probabilityItem);
			}

			max--;
		}
		double sim = libFunctionSelfSim.getSim(functionProbList);

		selfSimList.add(sim);



		return selfSimList;
	}

}
