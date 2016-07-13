package com.mouse.event.test;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;

public class MouseEventTest extends Thread {
	public void run() {
		int n = 10;
		for (int i = 0; i < n; n++) // horrible infinite loop
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} // this will slow the capture rate to 0.1 seconds
			PointerInfo a = MouseInfo.getPointerInfo();
			Point p = new Point(0, 0);
			a = MouseInfo.getPointerInfo();
			p = a.getLocation();
			int x = (int) p.getX(); // getX and getY return doubles so typecast
			int y = (int) p.getY();
			System.out.println("" + x + "   " + y); // to see it grabing
													// locations not needed
		}
	}
	
	public static void main(String[] args) throws Exception {
		Thread thread = new MouseEventTest();
		thread.start();
	}
	
}