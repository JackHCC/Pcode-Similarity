package com.jackcc;

import com.jackcc.db.LibFunctionSelfSim;
import com.jackcc.db.TargetSimProcess;
import com.jackcc.db.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.jackcc.db.TargetSimProcess.getRelativelySim;
import static com.jackcc.util.HashConvert.*;
import static com.jackcc.util.SimilarityOperation.intersection;
import static com.jackcc.util.StrandsGenerator.*;

public class Main {

	public static void main(String[] args)
			throws IOException, NoSuchAlgorithmException, SQLException, ClassNotFoundException {
		long startTime=System.currentTimeMillis();   //start_time

//         Init target strands
		ArrayList<String> target = convert2Strand("res/puts");
		TargetSimProcess targetSimProcess = new TargetSimProcess();
       // Construct the target hash
		ArrayList<byte[]> strandByteHash = calcStrandHash(target);
		ArrayList<String> targetHash = byte2str(strandByteHash);

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
				if (sim > 0.5) {
					System.out.println(result.getString("func_name") + "  " +sim);
				}
			}

		}
		long endTime = System.currentTimeMillis(); //end time
		System.out.println("run time： "+(endTime-startTime)+"ms");
	}

}

