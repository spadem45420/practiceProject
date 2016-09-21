package com.date.compare.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateCompare {

	public static void main(String[] args) throws ParseException {
		Date date1 = new Date();
		Date date2 = new Date();
		System.out.println(DateCompare.formatDate(date1).equals(DateCompare.formatDate(date2)));
		System.out.println(DateCompare.formatDate(date1).equals(DateCompare.formatDate(date2)));
	}

	private static Date formatDate(Date date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = sdf.format(date);
		return sdf.parse(dateString);
	}

}
