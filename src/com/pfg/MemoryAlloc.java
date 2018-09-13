package com.pfg;

public class MemoryAlloc {
	private static final int _1MB = 1024*1024;
	
	byte [] mallocation1 = new byte[_1MB / 4];
	byte [] mallocation2 = new byte[_1MB / 4];
	byte [] mallocation3 = new byte[4* _1MB];
	byte [] mallocation4 = new byte[3* _1MB];
	
	
	public static void testAllocation() {
		byte [] allocation1, allocation2, allocation3, allocation4;
		allocation1 = new byte[_1MB / 4];
		allocation2 = new byte[_1MB / 4];
		allocation3 = new byte[3* _1MB];
		allocation3 = null;
		allocation3 = new byte[4* _1MB]; 
	}
	
	public static void testTenuringThreshold() {
		byte [] allocation1, allocation2, allocation3;
		allocation1 = new byte[_1MB / 4];
		allocation2 = new byte[3 * _1MB];
		allocation3 = new byte[3 * _1MB];
		allocation3 = null;
		allocation3 = new byte[3 * _1MB]; 
	}
	
	public static void testTenuringThreshold2() {
		byte [] allocation1, allocation2, allocation3, allocation4;
		allocation1 = new byte[_1MB / 4];
		allocation2 = new byte[_1MB / 4];
		allocation3 = new byte[3 * _1MB];
		allocation4 = new byte[3 * _1MB]; 
		allocation4 = null;
		allocation4 = new byte[3* _1MB];
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//testAllocation();
		testTenuringThreshold2();
		//MemoryAlloc alloc = new MemoryAlloc();
		//testAllocation();
	}

}
