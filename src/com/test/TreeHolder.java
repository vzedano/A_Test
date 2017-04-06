package com.test;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class TreeHolder {
	
	public Map<String, MySimpleObject> ourTree = Collections.synchronizedSortedMap(new TreeMap<String, MySimpleObject>());
	
	
	
}