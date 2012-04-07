package org.clincon2001.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.clincon2001.domain.Clincon;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/tumorboard")
@Controller
public class TumorboardController {
	private final Log logger = LogFactory.getLog(getClass());

	@RequestMapping(produces = "text/html")
	public String home(Model uiModel) {
		logger.info("-------BEGIN---------------");
		return "tumorboard/home";
	}
	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Long id, Model uiModel) {
		logger.info("-------BEGIN---------------"+id);
		Clincon findClincon = Clincon.findClincon(id);
		uiModel.addAttribute("clincon", findClincon);
		uiModel.addAttribute("itemId", id);
		logger.info("END");
		return "tumorboard/show";
	}
}
