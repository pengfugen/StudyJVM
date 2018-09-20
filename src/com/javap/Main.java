package com.javap;

public class Main {

	public static void main(String[] args) {
		JavapTask task = new JavapTask();
		//注意arg应该为xxx.class
		task.run(args[0]);
	}
	
}
