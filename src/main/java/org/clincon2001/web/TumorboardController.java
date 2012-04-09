package org.clincon2001.web;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.clincon2001.domain.Ccdate;
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
	@RequestMapping(value = "/td-{yyyy}-{mm}-{dd}", produces = "text/html")
	public String date(
			@PathVariable("yyyy") Integer yyyy, 
			@PathVariable("mm") Integer mm, 
			@PathVariable("dd") Integer dd, 
			Model uiModel) {
		Date tumorboarddate = convert2date(yyyy, mm, dd);
		logger.debug(tumorboarddate);
		uiModel.addAttribute("tumorboarddate", tumorboarddate);
		return "tumorboard/tumorboarddate";
	}
	private Date convert2date(Integer yyyy, Integer mm, Integer dd) {
		Calendar instance = Calendar.getInstance();
		instance.set(Calendar.YEAR, yyyy);
		instance.set(Calendar.MONTH, mm-1);
		instance.set(Calendar.DAY_OF_MONTH, dd);
		return instance.getTime();
	}
	@RequestMapping(value = "/id-{id}", produces = "text/html")
	public String show(@PathVariable("id") Long id, Model uiModel) {
		logger.info("-------BEGIN---------------"+id);
		Clincon findClincon = Clincon.findClincon(id);
		uiModel.addAttribute("clincon", findClincon);
		uiModel.addAttribute("itemId", id);
		logger.info("END");
		return "tumorboard/show";
	}

	@RequestMapping(value = "/list", produces = "text/html")
	public String list(
			@RequestParam(value = "page", required = false) Integer page, 
			@RequestParam(value = "size", required = false) Integer size, 
			Model uiModel
			) {
		logger.debug("size="+size);
		logger.debug("page="+page);
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("clincons", Clincon.findClinconEntries(firstResult, sizeNo));
			float nrOfPages = (float) Clincon.countClincons() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
		} else {
			uiModel.addAttribute("clincons", Clincon.findAllClincons());
		}
		List<Ccdate> findAllCcdates = Ccdate.findAllCcdates();
		logger.debug("findAllCcdates.size()="+findAllCcdates.size());
		uiModel.addAttribute("ccdates", findAllCcdates);
		return "tumorboard/list";
	}

}
