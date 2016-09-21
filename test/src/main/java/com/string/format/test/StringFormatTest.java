package com.string.format.test;

public class StringFormatTest {
	public static void main(String[] args) {

		System.out.println(String.format("%1$-2c\t%2$-3s\t%3$-30s\t%4$#,12.3f\t%5$#,12.3f", 'v', "中文", "Name0",
				10000.11d, -2.567d));
		System.out.println(String.format("%1$-2c\t%2$-3s\t%3$-30s\t%4$#,12.3f\t%5$#,12.3f", 'f', "T01", "Name01",
				100.11d, -2000.567d));
		System.out.println(
				String.format("%1$-2c\t%2$-3s\t%3$-30s\t%4$#,12.3f\t%5$#,12.3f", 'a', "T2", "Name012", 0d, -1d));
	}
}
