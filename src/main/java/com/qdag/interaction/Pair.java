package com.qdag.interaction;

import java.io.Serializable;
import java.nio.ByteBuffer;

@SuppressWarnings("rawtypes")
public class Pair<T,U> implements Comparable,Serializable {
	private T t;
	U u;
	public static void main(String[] args) {
		long t = System.currentTimeMillis();
		ByteBuffer buffer = ByteBuffer.allocateDirect(500 * 1024);
		byte[] data = new byte[20];
		for(int i = 0 ;i< 500 * 1024;i++) {
			data[i % 20] =  (byte)i;
			if((i+1) % 20  == 0) 
				buffer.put(data);
		}
		System.out.println("Time for putting a data array into byte buffer directly is:"+(System.currentTimeMillis() - t));
	}
	/*
	 * Constructors	
	 */
	public Pair() {
		super();
	}

	public Pair(T t, U u) {
		super();
		this.setT(t);
		this.u = u;
	}
	/*
	 * Setters and Getters
	 */
	
	public T getT() {
		return t;
	}
	public void setT(T t) {
		this.t = t;
	}
	public U getU() {
		return u;
	}
	public void setU(U u) {
		this.u = u;
	}

	@Override
	public String toString() {
		return "Pair [t=" + t + ", u=" + u + "]";
	}

	@SuppressWarnings("unchecked")
	@Override
	public int compareTo(Object arg0) {
		
		return ((Comparable) t).compareTo(((Pair) arg0).t);
	}
	
	
}
