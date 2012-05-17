package org.clincon2001.util;

import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.clincon2001.synchronization.AppUtils;
import org.clincon2001.synchronization.PatientenSynchronization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class CronTest {
	private final Log logger = LogFactory.getLog(getClass());
	@Autowired private PatientenSynchronization patientenSynchronization;
	@Autowired @Qualifier("appUtils")		private		AppUtils		appUtils;
	private String test;

	public String checkTest() {
		String string = "This it - "+getTest()+" :: "+Calendar.getInstance().getTime();
		logger.info(string);
		patientenSynchronization.updatePatientenJohannisplatz(appUtils);
//		int lastCopydPatiantId = patientenSynchronization.lastCopydPatiantId("praxisluisa");
//		logger.info("lastCopydPatiantId="+lastCopydPatiantId);
		return string;
	}
	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}
}
