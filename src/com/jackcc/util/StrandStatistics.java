package com.jackcc.util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

import static com.jackcc.util.HashConvert.byte2str;

public class StrandStatistics {

	// get all strands count
	public static BigInteger getSizeOfLib(ArrayList<byte[]> libStrands) {
		BigInteger libSize = new BigInteger(String.valueOf(libStrands.size()));
		return libSize;
	}

	// get every strand count
	public static HashMap<String, Integer> getCountOfLibStrand(ArrayList<byte[]> libStrands) {
		if (libStrands == null || libStrands.size() == 0)
			return null;
		ArrayList<String> lib = byte2str(libStrands);
		HashMap<String, Integer> countStrandMap = new HashMap<>();
		for (String temp : lib) {
			Integer count = countStrandMap.get(temp);
			countStrandMap.put(temp, (count == null) ? 1 : count + 1);
		}
		return countStrandMap;
	}

	// get every strand probability reverse
	public static HashMap<String, BigInteger> getProbabilityReverseOfLibStrand(
			HashMap countStrandMap, BigInteger libSize) {
		HashMap<String, BigInteger> probabilityReverseMap = new HashMap();
		countStrandMap.forEach((k, v) -> {
			/** Approximate Value to BigInteger*/
			BigInteger value = new BigInteger(String.valueOf(v));
			BigInteger probabilityReverse = libSize.divide(value);
			probabilityReverseMap.put((String) k, probabilityReverse);
		});

		return probabilityReverseMap;
	}

}
