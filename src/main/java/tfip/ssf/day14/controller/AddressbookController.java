package tfip.ssf.day14.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import tfip.ssf.day14.model.Contact;
import tfip.ssf.day14.services.AddressbookService;

@Controller
public class AddressbookController {
    @Autowired
    private AddressbookService addrbkSvc;

    @GetMapping(value="/")
    public String showContactForm(Model model){
        model.addAttribute("contact",new Contact());
        return "contactform";
    }

    @PostMapping(path="/contact")
    public String saveContact(@Valid Contact contact, BindingResult result, Model model, HttpServletResponse response){
        if (result.hasErrors()){
            return "contactform";
        }
        addrbkSvc.saveContact(contact);
        model.addAttribute("contact", contact);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return "contact";
    }

}
