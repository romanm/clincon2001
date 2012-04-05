package org.clincon2001.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
}
