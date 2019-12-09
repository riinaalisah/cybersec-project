package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Signup;
import org.springframework.ui.Model;
import sec.project.repository.SignupRepository;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.ArrayList;

@Controller
public class SignupController {

    @Autowired
    private SignupRepository signupRepository;

    @Autowired
    private EntityManager entityManager;

    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm() {
        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm(@RequestParam String name, @RequestParam String address, Model model) {
        signupRepository.save(new Signup(name, address));
        List<Object[]> results = entityManager.createQuery("Select name, address from Signup where name = '" + name + "'").getResultList();
        List<Signup> signups = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            Object[] o = results.get(i);
            Signup su = new Signup();
            su.setName((String) o[0]);
            su.setAddress((String) o[1]);
            signups.add(su);
        }
        model.addAttribute("list", signups);
        return "done";
    }

}
