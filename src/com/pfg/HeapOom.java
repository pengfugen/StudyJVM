package com.pfg;

import java.util.ArrayList;
import java.util.List;

// VM argument:-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:PermSize=10M -XX:MaxPermSize=10M 
// -XX:+PrintGCDetails -XX:SurvivorRatio=8 -Xss128k -XX:+HeapDumpOnOutOfMemoryError
// jdk 1.8
public class HeapOom {

	// case 1: OutOfMemoryError
	static class OOMObject {
		
	}

	// case 2: StackOverflowError
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
		
		// case 3: 方法区
		/*String str1 = new StringBuilder("计算机").append("软件").toString();
		System.out.println(str1.intern() == str1);
		
		String str2 = new StringBuilder("ja").append("va").toString();
		System.out.println(str2.intern() == str2);
		Class class1 = HeapOom.class;
		System.out.println(class1);
		
		String abc = "abcdef";
		String def = "abcdef";
		System.out.println(abc == def);*/
		
		// case 4: 验证对象存在相互引用时也会被GC回收，说明不是用引用计数方法来判定
		testGC();
	}

}
