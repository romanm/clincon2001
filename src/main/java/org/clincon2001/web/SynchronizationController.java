package org.clincon2001.web;

import org.clincon2001.synchronization.PatientenSynchronization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SynchronizationController {

	@Autowired PatientenSynchronization PatientenSynchronization;
	@RequestMapping(value = "/proxytest", produces = "text/html")
    public String updatePatient(Model uiModel) {
        return "/";
    }
}
