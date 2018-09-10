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
	private byte[] memory;
	
	GCRoot(int size) {
		memory = new byte[size * _1MB];
	}
	
	// GC Root的第一种情况
	private static void method01() {
		GCRoot root1 = new GCRoot(80);
		System.gc();
	}
	
	// GC Root的第二种情况
	static GCRoot root2;
	
	// GC Root的第三种情况
	private final static GCRoot root3 = new GCRoot(80);
	
	public static void main(String[] args) {
		
		/* 
		 // GC Root的第一种情况
		 method01();
		 System.gc();
		 */
		
		/*
	     // GC Root的第二种情况
		 GCRoot root = new GCRoot(40);
		 root = null;
		 System.gc();
		
		 root.root2 = new GCRoot(80);
		*/
		
		// GC Root的第三种情况
		GCRoot root = new GCRoot(40);
		root= null;
		System.gc();
		
	}

}
