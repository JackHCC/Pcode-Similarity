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

	public static void PGenerator()
			throws IOException, NoSuchAlgorithmException, SQLException {
		FunctionOption functionOption = new FunctionOption();

		ArrayList<String> pathList = getFile("res");
		ArrayList<String> nameList = getFuncName("res");

		for (int i = 0; i < pathList.size(); i++) {

			ArrayList<byte[]> strandsHash = calcStrandHash(convert2Strand(pathList.get(i)));
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
