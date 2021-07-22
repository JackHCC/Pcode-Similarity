package com.jackcc;

import com.jackcc.db.LibFunctionSelfSim;
import com.jackcc.db.TargetSimProcess;
import com.jackcc.db.*;
import com.jackcc.util.StrandsGenerator;

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

		long startTime = System.currentTimeMillis();   //start_time

		TargetSimProcess targetSimProcess = new TargetSimProcess();
		StrandsGenerator strandsGenerator =new StrandsGenerator();

		ArrayList<String> pathList = strandsGenerator.getFile("test");
		ArrayList<String> nameList = strandsGenerator.getFuncName("test");
//		for (int i =0;i<pathList.size();i++){
//
////			Init target strands
//			String target_name = nameList.get(i);
//			ArrayList<String> target = convert2Strand(pathList.get(i));
//			ArrayList<byte[]> strandByteHash = calcStrandHash(target);
//
//			// Construct the target hash
//			ArrayList<String> targetHash = byte2str(strandByteHash);
//
////			calc sim
//			targetSimProcess.calcSim(target_name,targetHash);
//		}

		ArrayList<String> target = convert2Strand("res/memcpy");
			ArrayList<byte[]> strandByteHash = calcStrandHash(target);

			// Construct the target hash
			ArrayList<String> targetHash = byte2str(strandByteHash);

//			calc sim
			targetSimProcess.calcSim("puts",targetHash);
		long endTime = System.currentTimeMillis(); //end time
		System.out.println("run time： " + (endTime - startTime) + "ms");

	}

}

