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
	private byte[] bigSize = new byte[80 * _1MB];
	
	// ����ΪGC Root�ĵ�һ�����
	private static void method01() {
		GCRoot root = new GCRoot();
		System.gc();
	}
	
	public static void main(String[] args) {
		method01();
		System.gc();
	}

}
