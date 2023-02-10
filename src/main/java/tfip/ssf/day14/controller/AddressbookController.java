package tfip.ssf.day14.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import tfip.ssf.day14.model.Contact;
import tfip.ssf.day14.services.AddressbookService;

@Controller
public class AddressbookController {
    @Autowired
    private AddressbookService addrbkSvc;

    @GetMapping(path="/")
    public String showContactForm(Model model){ //pass an empty Contact object (only have random generated ID) to contactform.html
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
        response.setStatus(HttpServletResponse.SC_CREATED); //To send out Status code of 201
        return "contact";
    }

    @GetMapping("/contact") //http://localhost:8080/contact?startIndex=0    
    public String getAllContact(Model model, @RequestParam(name="startIndex") Integer startIndex){
        List<Contact> result = addrbkSvc.getAllContact(startIndex);
        model.addAttribute("contacts",result);
        return "list";
    }

    @GetMapping(path="/contact/{contactId}")
    public String getContactDetails(Model model, @PathVariable(value="contactId") String contactId){
        Contact ctc = addrbkSvc.findContactById(contactId);
        model.addAttribute("contact",ctc);
        return "contact";
    }
}
