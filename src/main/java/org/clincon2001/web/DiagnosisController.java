package org.clincon2001.web;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.clincon2001.domain.Ccdiagnosis;
import org.clincon2001.domain.Diagnosis;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

@RequestMapping("/diagnoses")
@Controller
@RooWebScaffold(path = "diagnoses", formBackingObject = Diagnosis.class)
public class DiagnosisController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Diagnosis diagnosis, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, diagnosis);
            return "diagnoses/create";
        }
        uiModel.asMap().clear();
        diagnosis.persist();
        return "redirect:/diagnoses/" + encodeUrlPathSegment(diagnosis.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Diagnosis());
        return "diagnoses/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("diagnosis", Diagnosis.findDiagnosis(id));
        uiModel.addAttribute("itemId", id);
        return "diagnoses/show";
    }

	private final Log logger = LogFactory.getLog(getClass());
	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		logger.info("-------BEGIN---------------");
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("diagnoses", Diagnosis.findDiagnosisEntries(firstResult, sizeNo));
            float nrOfPages = (float) Diagnosis.countDiagnoses() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("diagnoses", Diagnosis.findAllDiagnoses());
        }
        return "diagnoses/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Diagnosis diagnosis, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, diagnosis);
            return "diagnoses/update";
        }
        uiModel.asMap().clear();
        diagnosis.merge();
        return "redirect:/diagnoses/" + encodeUrlPathSegment(diagnosis.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, Diagnosis.findDiagnosis(id));
        return "diagnoses/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Diagnosis diagnosis = Diagnosis.findDiagnosis(id);
        diagnosis.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/diagnoses";
    }

	void populateEditForm(Model uiModel, Diagnosis diagnosis) {
        uiModel.addAttribute("diagnosis", diagnosis);
        uiModel.addAttribute("ccdiagnoses", Ccdiagnosis.findAllCcdiagnoses());
    }

	String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
}
