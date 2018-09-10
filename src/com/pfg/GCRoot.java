package com.pfg;

/*
 * ����ΪGC Root���������֣�
 * 1. �����ջ�ֲ��������õĶ���
 * 2. �������ྲ̬�������õĶ���
 * 3. �������������õĶ���
 * 4. native�����Ķ���
 */
public class GCRoot {
	
	private static final int _1MB = 1024*1024;
	private byte[] memory;
	
	GCRoot(int size) {
		memory = new byte[size * _1MB];
	}
	
	// GC Root�ĵ�һ�����
	private static void method01() {
		GCRoot root1 = new GCRoot(80);
		System.gc();
	}
	
	// GC Root�ĵڶ������
	static GCRoot root2;
	
	// GC Root�ĵ��������
	private final static GCRoot root3 = new GCRoot(80);
	
	public static void main(String[] args) {
		
		/* 
		 // GC Root�ĵ�һ�����
		 method01();
		 System.gc();
		 */
		
		/*
	     // GC Root�ĵڶ������
		 GCRoot root = new GCRoot(40);
		 root = null;
		 System.gc();
		
		 root.root2 = new GCRoot(80);
		*/
		
		// GC Root�ĵ��������
		GCRoot root = new GCRoot(40);
		root= null;
		System.gc();
		
	}

}
