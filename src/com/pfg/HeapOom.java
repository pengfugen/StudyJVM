package com.pfg;

import java.util.ArrayList;
import java.util.List;

public class HeapOom {

	// case 1
	static class OOMObject {
		
	}
	

	// case 2
	private int stackLenght = 1;
	public void stackLeak() {
		stackLenght++;
		stackLeak();
	}
	
	// case 4
	public Object instance = null;
	private static final int _1MB = 1024*1024;
	private byte[] bigSize = new byte[2 * _1MB];
	public static void testGC() {
		HeapOom objA = new HeapOom();
		HeapOom objB = new HeapOom();
		objA.instance = objB;
		objB.instance = objA;
		
		objA = null;
		objB = null;
		System.gc();
	}
	
	
	public static void main(String[] args) {
		// case 1
		/*List<OOMObject> list = new ArrayList<OOMObject>();
		while(true) {
			list.add(new OOMObject());
		}*/

		// case 2
		/*HeapOom oom = new HeapOom();
		try {
			oom.stackLeak();
		} catch (Throwable e) {
			System.out.println("stack length:"+oom.stackLenght);
			throw e;
		}*/
		
		// case 3
		/*String str1 = new StringBuilder("¼ÆËã»ú").append("Èí¼þ").toString();
		System.out.println(str1.intern() == str1);
		
		String str2 = new StringBuilder("ja").append("va").toString();
		System.out.println(str2.intern() == str2);
		Class class1 = HeapOom.class;
		System.out.println(class1);
		
		String abc = "abcdef";
		String def = "abcdef";
		System.out.println(abc == def);*/
		
		// case 4
		testGC();
	}

}
