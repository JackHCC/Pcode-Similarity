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

}
