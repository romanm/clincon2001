package org.clincon2001.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.clincon2001.synchronization.AppUtils;
import org.clincon2001.synchronization.PatientenSynchronization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SynchronizationController {
	private final Log logger = LogFactory.getLog(getClass());
	@Autowired private PatientenSynchronization patientenSynchronization;
	@Autowired @Qualifier("appUtils")		private		AppUtils		appUtils;
//	@Autowired @Qualifier("aput")		private		Map		aput;
	
	@RequestMapping(value = "/tuborsyn", produces ="text/html")
	public String tuborsyn(Model uiModel) {
		logger.info("-------BEGIN---------------");
		patientenSynchronization.updatePatientenOws1tubo(appUtils);
		logger.info("-------END---------------");
		return "redirect:/";
	}
	@RequestMapping(value = "/pasy", produces ="text/html")
	public String updatePatient(Model uiModel) {
		logger.info("-------BEGIN---------------");
		logger.debug(appUtils);
		logger.debug(appUtils.getUrldb2());
		logger.debug(appUtils.getContextdb2());
		patientenSynchronization.updatePatientenJohannisplatz(appUtils);
		logger.info("-------END---------------");
		return "redirect:/";
	}
}


