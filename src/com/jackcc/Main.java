package com.jackcc;

import com.jackcc.db.FunctionOption;
import com.jackcc.db.LibFunctionSelfSim;
import com.jackcc.db.StrandOperation;
import com.jackcc.db.TargetSimProcess;
import com.jackcc.util.Similarity;
import com.jackcc.util.StrandsGenerator;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.jackcc.util.HashConvert.*;
import static com.jackcc.util.StrandStatistics.*;
import static com.jackcc.util.StrandsGenerator.*;

public class Main {

	public static void main(String[] args)
			throws IOException, NoSuchAlgorithmException, SQLException, ClassNotFoundException {

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

//        ArrayList<ArrayList<String>> p = PGenerator();
		/**
		 * q read from P by loop
		 * */
//        Similarity sim = new Similarity(strandByteHash, pHash);
//        for (int i =0; i<sim.lib.size(); i++) {
//            sim.query =sim.lib.get(i);
//            // Using to test
//            sim.sizeOfLib = sim.getLibSize(sim.lib);
//            sim.strandNumMap = sim.getStrandNum(sim.target, sim.lib);
//
//            sim.probabilityReverseMap = sim.getStrandProbabilityReverse(sim.strandNumMap, sim.sizeOfLib);
//
//            sim.listIntersection = sim.intersection(byte2str(sim.target), byte2str(sim.query));
//
//            sim.simScore = sim.getSim(sim.listIntersection, sim.probabilityReverseMap);
//            sim.relativelySimScore = sim.getRelativelySim(byte2str(sim.target), sim.query, sim.listIntersection, sim.probabilityReverseMap);
//
//
//            /**
//             * Print
//             * */
//            System.out.println("--------------------------------------------------------------------------------");
//            System.out.println(sim.sizeOfLib);
//
//            System.out.println(sim.strandNumMap);
//            System.out.println(sim.probabilityReverseMap);
//
//            System.out.println(sim.target);
//            System.out.println(sim.query);
//            System.out.println(sim.listIntersection);
//
//            System.out.println(sim.simScore);
//            System.out.println(sim.relativelySimScore);
//        }

	}
}

