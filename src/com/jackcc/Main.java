package com.jackcc;

<<<<<<< Updated upstream
import com.jackcc.db.FunctionOption;
import com.jackcc.db.LibFunctionSelfSim;
import com.jackcc.db.StrandOperation;
import com.jackcc.db.TargetSimProcess;
=======
import com.jackcc.db.*;
>>>>>>> Stashed changes
import com.jackcc.util.Similarity;
import com.jackcc.util.StrandsGenerator;
import com.jackcc.util.TypeConversion;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.jackcc.db.TargetSimProcess.getRelativelySim;
import static com.jackcc.util.HashConvert.*;
import static com.jackcc.util.SimilarityOperation.calculateSelfSim;
import static com.jackcc.util.SimilarityOperation.intersection;
import static com.jackcc.util.StrandStatistics.*;
import static com.jackcc.util.StrandsGenerator.*;

public class Main {

	public static void main(String[] args)
			throws IOException, NoSuchAlgorithmException, SQLException, ClassNotFoundException {

<<<<<<< Updated upstream
		//         Init target strands
		ArrayList<String> target = convert2Strand("res/puts");

		// Construct the target hash
		ArrayList<byte[]> strandByteHash = calcStrandHash(target);
		ArrayList<String> strandHash = byte2str(strandByteHash);

		TargetSimProcess targetSimProcess = new TargetSimProcess();
		Double targetSelSim = targetSimProcess.getTargetSelfSim(strandHash);
		System.out.println(targetSelSim);

//      Init db connection for function strands
//		FunctionOption funcOp = new FunctionOption();
//
//		ArrayList<byte[]> libStrands = funcOp.getLibStrandsArray();
//
//		StrandOperation strandOp = new StrandOperation();
//		HashMap<String, Integer> strandCountMap = getCountOfLibStrand(libStrands);
//		BigInteger sizeOfLib = getSizeOfLib(libStrands);
//
//		strandOp.add(strandCountMap, getProbabilityReverseOfLibStrand(strandCountMap, sizeOfLib));
//		System.out.println(funcOp.getLibStrandsArray());
//		System.out.println(getSizeOfLib(funcOp.getLibStrandsArray()));
//		System.out.println(getCountOfLibStrand(funcOp.getLibStrandsArray()));


		LibFunctionSelfSim libFunctionSelfSim = new LibFunctionSelfSim();
		HashMap<Integer, Double> selfSimMap = libFunctionSelfSim.getSelfSimMap();
//		libFunctionSelfSim.add(selfSimMap);
		System.out.println(selfSimMap);
=======
//         Init target strands
		ArrayList<String> target = convert2Strand("res/setbuf");
		TargetSimProcess targetSimProcess = new TargetSimProcess();
       // Construct the target hash
		ArrayList<byte[]> strandByteHash = calcStrandHash(target);
		ArrayList<String> targetHash = byte2str(strandByteHash);

//		ArrayList<String> query = convert2Strand("res/setbuf");
//		// Construct the target hash
//		ArrayList<byte[]> queryByteHash = calcStrandHash(query);
//		ArrayList<String> queryHash = byte2str(queryByteHash);
//		System.out.println(targetHash);
//		System.out.println(queryHash);
//
//		ArrayList<String> intersect = intersection(queryHash,targetHash);
//		System.out.println(intersect);
////
//
//      Init db connection for function strands
		JdbcDao db = new JdbcDao();
		Connection conn = db.getConnection();
		LibFunctionSelfSim libFunctionSelfSim = new LibFunctionSelfSim();

		String sql = "SELECT id, func_name,strands FROM function_strands";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet result = pstmt.executeQuery();
		ArrayList<ArrayList<byte[]>> libStrands = new ArrayList<>();
		Double targetSim= targetSimProcess.getTargetSelfSim(targetHash);

		while (result.next()) {
			ObjectInputStream in = new ObjectInputStream(result.getBinaryStream("strands"));
			ArrayList<byte[]> strands = (ArrayList<byte[]>) in.readObject();
			in.close();
			ArrayList<String> query = byte2str(strands);
			ArrayList<String> intersect = intersection(query,targetHash);

			if (intersect.size()>0){
				Double intersectSim = targetSimProcess.getTargetSelfSim(intersect);
				Double querySim = libFunctionSelfSim.getSelfSim(result.getInt("id"));
				Double sim =  getRelativelySim(targetSim,querySim,intersectSim);
>>>>>>> Stashed changes

				if (sim > 0.9) {

					System.out.println(result.getString("func_name") + "  " +sim);
				}
			}

		}

	}
}

