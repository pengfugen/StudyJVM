package com.pfg;

import java.util.Arrays;

public class Stack {
	
	private Object[] elements;
	private int size = 0;
	private static final int DEFAULT_INITIAL_CAPACITY = 16;
	
	public Stack() {
		elements = new Object[DEFAULT_INITIAL_CAPACITY];
	}
	
	public void push(Object e) {
		ensureCapacity();
		elements[size++] = e;
	}
	
	public Object pop() throws EmptyStackException {
		if(size == 0) {
			throw new EmptyStackException();
		}
		Object result = elements[--size];
		elements[size] = null;
		return result;
	}
	
	private void ensureCapacity() {
		if(elements.length == size) {
			elements = Arrays.copyOf(elements, 2*size + 1);
		}
	}
	
	class EmptyStackException extends Exception {
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Stack stack = new Stack();
		for(int i = 0; i < 2000000; i++) {
			stack.push(new Object());
		}
		
		for(int i = 0; i < 1000000; i++) {
			try {
				Object result = stack.pop();
				System.out.println(result == null);
			} catch (EmptyStackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("end===============");
	}

}
