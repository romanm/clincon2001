package org.clincon2001.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.clincon2001.synchronization.PatientenSynchronization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SynchronizationController {
	private final Log logger = LogFactory.getLog(getClass());
	@Autowired private PatientenSynchronization patientenSynchronization;
	@RequestMapping(value = "/pasy", produces ="text/html")
	public String updatePatient(Model uiModel) {
		logger.info("-------BEGIN---------------");
		patientenSynchronization.updatePatienten();
		logger.info("-------END---------------");
		return "redirect:/";
	}
}
