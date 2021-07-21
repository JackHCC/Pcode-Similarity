package com.jackcc.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class TypeConversion<T, V> {

	public ArrayList<T> hashMapKey2list(HashMap<T, V> hashMap) {
		ArrayList<T> keyList = new ArrayList<>();
		Iterator it = hashMap.keySet().iterator();
		while (it.hasNext()) {
			T key = (T) it.next();
			keyList.add(key);
		}
		return keyList;
	}

	public ArrayList<V> hashMapValue2list(HashMap<T, V> hashMap) {
		ArrayList<V> valueList = new ArrayList<>();
		Iterator it = hashMap.values().iterator();
		while (it.hasNext()) {
			V value = (V) it.next();
			valueList.add(value);
		}
		return valueList;
	}
	public HashMap<T, V> list2hashMap(ArrayList<T> list1, ArrayList<V> list2) {
		HashMap<T, V> hashMap;
		if (list1.size() != list2.size()) {
			return null;
		} else {
			hashMap = new HashMap<>();
			for (int i = 0; i < list1.size(); i++) {
				hashMap.put(list1.get(i), list2.get(i));
			}
		}
		return hashMap;
	}

	public HashMap<T, V> list2hashMap(ArrayList<T> list1, ArrayList<V> list2) {
		HashMap<T, V> hashMap;
		if (list1.size() != list2.size()) {
			return null;
		} else {
			hashMap = new HashMap<>();
			for (int i = 0; i < list1.size(); i++) {
				hashMap.put(list1.get(i), list2.get(i));
			}
		}
		return hashMap;
	}

}
