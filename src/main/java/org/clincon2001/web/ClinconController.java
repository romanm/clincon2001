package org.clincon2001.web;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.clincon2001.domain.Ccdate;
import org.clincon2001.domain.Ccdiagnosis;
import org.clincon2001.domain.Clincon;
import org.clincon2001.domain.Participant;
import org.clincon2001.domain.Patient;
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

@RequestMapping("/clincons")
@Controller
@RooWebScaffold(path = "clincons", formBackingObject = Clincon.class)
public class ClinconController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Clincon clincon, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, clincon);
            return "clincons/create";
        }
        uiModel.asMap().clear();
        clincon.persist();
        return "redirect:/clincons/" + encodeUrlPathSegment(clincon.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Clincon());
        return "clincons/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("clincon", Clincon.findClincon(id));
        uiModel.addAttribute("itemId", id);
        return "clincons/show";
    }

	@RequestMapping(produces = "text/html")
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
        return "clincons/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Clincon clincon, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, clincon);
            return "clincons/update";
        }
        uiModel.asMap().clear();
        clincon.merge();
        return "redirect:/clincons/" + encodeUrlPathSegment(clincon.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, Clincon.findClincon(id));
        return "clincons/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Clincon clincon = Clincon.findClincon(id);
        clincon.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/clincons";
    }

	void populateEditForm(Model uiModel, Clincon clincon) {
        uiModel.addAttribute("clincon", clincon);
        uiModel.addAttribute("ccdates", Ccdate.findAllCcdates());
        uiModel.addAttribute("ccdiagnoses", Ccdiagnosis.findAllCcdiagnoses());
        uiModel.addAttribute("participants", Participant.findAllParticipants());
        uiModel.addAttribute("patients", Patient.findAllPatients());
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
