package com.jackcc.util;

import com.jackcc.db.FunctionOption;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.jackcc.util.HashConvert.calcStrandHash;

/**
 * Generate the Strand for simulation
 * */
public class StrandsGenerator {

	/**
	 * Random generate strands to test
	 *
	 * @return*/
	public static ArrayList getStrands() {
		ArrayList<String> strands = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			String str = "add a" + Integer.toString(i) + ", a" + Integer.toString(i + 1);
			strands.add(str);
		}
		return strands;
	}

	public static ArrayList getStrands(int seed1, int seed2) {
		ArrayList<String> strands = new ArrayList<>();
		for (int i = seed1; i < seed2; i++) {
			String str = "add a" + Integer.toString(i) + ", a" + Integer.toString(i + 1);
			strands.add(str);
		}
		return strands;
	}

	public static ArrayList getSameStrands() {
		ArrayList<String> strands = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			String str = "add a1, a2";
			strands.add(str);
		}
		return strands;
	}

	/**
	 * Convert to strand from path
	 *
	 *
	 * @param path
	 * @return*/
	public static ArrayList<String> convert2Strand(String path) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(path));
		String pyData;
		String tmp;
		ArrayList<String> funcStrands = new ArrayList<>();
		while ((pyData = in.readLine()) != null) {
			tmp = pyData.replace("[", "");
			tmp = tmp.replace("]", "");
			tmp = normalize(tmp);
			funcStrands.add(tmp);
		}
		return funcStrands;
	}

	/**
	 * Construct Lib P(Hash): include query procedures
	 **/
	public static ArrayList<ArrayList<byte[]>> PGenerator(int seed)
			throws UnsupportedEncodingException, NoSuchAlgorithmException {
		ArrayList<ArrayList<byte[]>> P = new ArrayList<>();
		for (int i = 0; i < seed; i++) {
			P.add(calcStrandHash(getStrands(i, i + 11)));

		}
		return P;
	}

	public static void PGenerator()
			throws IOException, NoSuchAlgorithmException, SQLException {
		FunctionOption functionOption = new FunctionOption();

		ArrayList<String> pathList = getFile("res");
		ArrayList<String> nameList = getFuncName("res");

		for (int i = 0; i<pathList.size(); i++) {

			ArrayList<byte []> strandsHash = calcStrandHash(convert2Strand(pathList.get(i)));
			functionOption.add(nameList.get(i), strandsHash);
		}
	}

	private static ArrayList getFile(String path) {
		ArrayList<String> pathList = new ArrayList<>();
		// get file list where the path has
		File file = new File(path);
		// get the folder list
		File[] array = file.listFiles();

		for (int i = 0; i < array.length; i++) {
			if (array[i].isFile()) {
				pathList.add(array[i].getPath());
			}
			else if (array[i].isDirectory()) {
				getFile(array[i].getPath());
			}
		}

		return pathList;
	}

	private static ArrayList getFuncName(String path) {
		ArrayList<String> nameList = new ArrayList<>();
		// get file list where the path has
		File file = new File(path);
		// get the folder list
		File[] array = file.listFiles();

		for (int i = 0; i < array.length; i++) {
			if (array[i].isFile()) {
				nameList.add(array[i].getName());
			}
			else if (array[i].isDirectory()) {
				getFuncName(array[i].getPath());
			}
		}

		return nameList;
	}

	public static String normalize(String strand) {
		String tmp = strand;
		String regex = "(?<=\\(?(\\,\\s))[a-zA-Z0-9]+(?=\\,\\s[0-9]*\\))";
		Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);

		Matcher matcher = pattern.matcher(strand);

		while (matcher.find()) {
			tmp = tmp.replace(matcher.group(0), "offset");
		}
		return tmp;
	}

}
