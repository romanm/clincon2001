package org.clincon2001.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.clincon2001.domain.Clincon;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

	@RequestMapping(value = "/list", produces = "text/html")
	public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("clincons", Clincon.findClinconEntries(firstResult, sizeNo));
			float nrOfPages = (float) Clincon.countClincons() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
		} else {
			uiModel.addAttribute("clincons", Clincon.findAllClincons());
		}
		return "tumorboard/list";
	}

}
