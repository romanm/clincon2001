package org.clincon2001.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class ListClicoQuartzJob extends QuartzJobBean {
	private final Log logger = LogFactory.getLog(getClass());
	private CronTest cronTest;
	public CronTest getCronTest() {
		return cronTest;
	}
	public void setCronTest(CronTest cronTest) {
		this.cronTest = cronTest;
	}
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		logger.info("Listing images in image database, scheduled by Quartz");
	}

}
