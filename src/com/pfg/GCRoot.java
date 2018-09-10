package com.pfg;

/*
 * 可作为GC Root对象有四种：
 * 1. 虚拟机栈局部变量引用的对象
 * 2. 方法区类静态属性引用的对象
 * 3. 方法区常量引用的对象
 * 4. native创建的对象
 */
public class GCRoot {
	
	private static final int _1MB = 1024*1024;
	private byte[] bigSize = new byte[80 * _1MB];
	
	// 可作为GC Root的第一种情况
	private static void method01() {
		GCRoot root = new GCRoot();
		System.gc();
	}
	
	public static void main(String[] args) {
		method01();
		System.gc();
	}

}
