package com.github.nhathuyld.shop365.tests;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Test;

public class thu {
	
	@Test
	public void thu() throws InterruptedException, ParseException {
		//System.out.println(getCurrentDate());
		System.out.println(getEndDate());
	}
	public String getCurrentDate() throws InterruptedException {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		return (dateFormat.format(cal.getTime()));
	}
	public String getEndDate() throws InterruptedException, ParseException {
		String dt = getCurrentDate();  // Start date
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(dt));
		c.add(Calendar.DATE, 1);  // number of days to add
		dt = sdf.format(c.getTime()); 
		return dt;
	}
}
