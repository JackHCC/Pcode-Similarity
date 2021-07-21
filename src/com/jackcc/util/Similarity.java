package com.jackcc.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.jackcc.util.HashConvert.byte2str;

public class Similarity {

	public ArrayList<byte[]> target;
	public ArrayList<byte[]> query;
	public ArrayList<String> listIntersection;
	public ArrayList<ArrayList<byte[]>> lib;
	public BigInteger sizeOfLib;
	public HashMap strandNumMap;
	public HashMap probabilityReverseMap;
	public Double simScore;
	public Double relativelySimScore;

	public Similarity(ArrayList<byte[]> t, ArrayList<ArrayList<byte[]>> lib) {
		this.target = t;
		this.lib = lib;
		this.getLibSize(this.lib);
		this.getStrandNum(this.target, this.lib);
		this.getStrandProbabilityReverse(this.strandNumMap, this.sizeOfLib);
	}

	public Similarity(ArrayList<HashMap<String, ArrayList<byte[]>>> lib) {

	}

	public ArrayList<String> intersection(ArrayList<String> queryHash,
			ArrayList<String> targetHash) {
		ArrayList<String> result = new ArrayList<>();
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

	public BigInteger getLibSize(ArrayList<ArrayList<byte[]>> lib) {
		sizeOfLib = new BigInteger("0");

		for (int i = 0; i < lib.size(); i++) {
			ArrayList<byte[]> arrayListItem = lib.get(i);
			sizeOfLib = sizeOfLib.add(BigInteger.valueOf(arrayListItem.size()));
		}
		return sizeOfLib;
	}

	public HashMap getStrandNum(ArrayList<byte[]> target, ArrayList<ArrayList<byte[]>> lib) {
		ArrayList<String> tString = byte2str(target);

		strandNumMap = new HashMap();
		for (int i = 0; i < target.size(); i++) {
			BigInteger countStrand = new BigInteger("0");
			/** Change Hash to String */
//            byte[] itemOft = t.get(i);
			String itemOft = tString.get(i);

			for (int j = 0; j < lib.size(); j++) {
				for (int k = 0; k < lib.get(j).size(); k++) {
					/** Change Hash to String */
//                    byte[] libStrandsItem = P.get(j).get(k);
					String libStrandsItem = byte2str(lib.get(j).get(k));
					if (libStrandsItem.equals(itemOft)) {
						countStrand = countStrand.add(BigInteger.valueOf(Long.parseLong("1")));

					}
				}
			}
			if (countStrand.equals(BigInteger.valueOf(Long.parseLong("0")))) {
				countStrand = countStrand.add(BigInteger.valueOf(Long.parseLong("1")));
			}
			strandNumMap.put(itemOft, countStrand);
		}
		return strandNumMap;
	}

	public HashMap getStrandProbabilityReverse(HashMap strandNumMap, BigInteger sizeOfLib) {
		probabilityReverseMap = new HashMap();
		strandNumMap.forEach((k, v) -> {
			/** Approximate Value to BigInteger*/
			BigInteger probabilityReverse = sizeOfLib.divide((BigInteger) v);
			probabilityReverseMap.put(k, probabilityReverse);
		});
		return probabilityReverseMap;
	}

	public Double getSim(ArrayList<String> arrayIntersection, HashMap strandProbabilityReverse) {
		BigInteger sim = new BigInteger("0");
		simScore = sim.doubleValue();
		for (int i = 0; i < arrayIntersection.size(); i++) {
			for (Object k : strandProbabilityReverse.keySet()) {
				if (k.equals(arrayIntersection.get(i))) {
					BigInteger probabilityReverse = (BigInteger) strandProbabilityReverse.get(k);
//                    sim = sim.add(probabilityReverse);
					simScore = simScore + (Math.log(probabilityReverse.doubleValue()));
				}
			}
		}
		return simScore;
	}

	public Double getRelativelySim(ArrayList<String> arrayT, ArrayList<byte[]> arrayQ,
			ArrayList<String> arrayIntersection, HashMap strandProbabilityReverse) {
		HashMap qStrandProbabilityReverse =
				getStrandProbabilityReverse(getStrandNum(arrayQ, lib), sizeOfLib);
		relativelySimScore = getSim(arrayIntersection, strandProbabilityReverse) / Math.sqrt(
				getSim(arrayT, strandProbabilityReverse) * getSim(byte2str(arrayQ),
						qStrandProbabilityReverse));
		return relativelySimScore;
	}

}
