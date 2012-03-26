package org.clincon2001.util;

import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CronTest {
	private final Log logger = LogFactory.getLog(getClass());
	private String test;

	public String checkTest() {
		String string = "This it - "+getTest()+" :: "+Calendar.getInstance().getTime();
		logger.info(string);
		return string;
	}
	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}
}
