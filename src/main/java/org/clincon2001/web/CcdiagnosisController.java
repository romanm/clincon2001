package org.clincon2001.web;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.clincon2001.domain.Ccdiagnosis;
import org.clincon2001.domain.Clincon;
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

@RequestMapping("/ccdiagnoses")
@Controller
@RooWebScaffold(path = "ccdiagnoses", formBackingObject = Ccdiagnosis.class)
public class CcdiagnosisController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Ccdiagnosis ccdiagnosis, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, ccdiagnosis);
            return "ccdiagnoses/create";
        }
        uiModel.asMap().clear();
        ccdiagnosis.persist();
        return "redirect:/ccdiagnoses/" + encodeUrlPathSegment(ccdiagnosis.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Ccdiagnosis());
        return "ccdiagnoses/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("ccdiagnosis", Ccdiagnosis.findCcdiagnosis(id));
        uiModel.addAttribute("itemId", id);
        return "ccdiagnoses/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("ccdiagnoses", Ccdiagnosis.findCcdiagnosisEntries(firstResult, sizeNo));
            float nrOfPages = (float) Ccdiagnosis.countCcdiagnoses() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("ccdiagnoses", Ccdiagnosis.findAllCcdiagnoses());
        }
        return "ccdiagnoses/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Ccdiagnosis ccdiagnosis, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, ccdiagnosis);
            return "ccdiagnoses/update";
        }
        uiModel.asMap().clear();
        ccdiagnosis.merge();
        return "redirect:/ccdiagnoses/" + encodeUrlPathSegment(ccdiagnosis.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, Ccdiagnosis.findCcdiagnosis(id));
        return "ccdiagnoses/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Ccdiagnosis ccdiagnosis = Ccdiagnosis.findCcdiagnosis(id);
        ccdiagnosis.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/ccdiagnoses";
    }

	void populateEditForm(Model uiModel, Ccdiagnosis ccdiagnosis) {
        uiModel.addAttribute("ccdiagnosis", ccdiagnosis);
        uiModel.addAttribute("clincons", Clincon.findAllClincons());
        uiModel.addAttribute("diagnoses", Diagnosis.findAllDiagnoses());
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
