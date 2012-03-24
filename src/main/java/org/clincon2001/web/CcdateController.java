package org.clincon2001.web;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.clincon2001.domain.Ccdate;
import org.clincon2001.domain.Cctype;
import org.clincon2001.domain.Clincon;
import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
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

@RequestMapping("/ccdates")
@Controller
@RooWebScaffold(path = "ccdates", formBackingObject = Ccdate.class)
public class CcdateController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Ccdate ccdate, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, ccdate);
            return "ccdates/create";
        }
        uiModel.asMap().clear();
        ccdate.persist();
        return "redirect:/ccdates/" + encodeUrlPathSegment(ccdate.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Ccdate());
        return "ccdates/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("ccdate", Ccdate.findCcdate(id));
        uiModel.addAttribute("itemId", id);
        return "ccdates/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("ccdates", Ccdate.findCcdateEntries(firstResult, sizeNo));
            float nrOfPages = (float) Ccdate.countCcdates() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("ccdates", Ccdate.findAllCcdates());
        }
        addDateTimeFormatPatterns(uiModel);
        return "ccdates/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Ccdate ccdate, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, ccdate);
            return "ccdates/update";
        }
        uiModel.asMap().clear();
        ccdate.merge();
        return "redirect:/ccdates/" + encodeUrlPathSegment(ccdate.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, Ccdate.findCcdate(id));
        return "ccdates/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Ccdate ccdate = Ccdate.findCcdate(id);
        ccdate.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/ccdates";
    }

	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("ccdate_clincondatetime_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

	void populateEditForm(Model uiModel, Ccdate ccdate) {
        uiModel.addAttribute("ccdate", ccdate);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("cctypes", Cctype.findAllCctypes());
        uiModel.addAttribute("clincons", Clincon.findAllClincons());
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
