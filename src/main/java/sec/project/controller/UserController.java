package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.User;
import org.springframework.ui.Model;
import sec.project.repository.UserRepository;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.ArrayList;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public String loadLoginForm() {
    return "login";
  }

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public String submitLoginForm(@RequestParam String username, @RequestParam String password, Model model, RedirectAttributes redirectAttributes) {
    User user = userRepository.findByUsername(username);
    if (user != null) {

      List<User> users = userRepository.findAll();
      for (int i = 0; i < users.size(); i++) {
        System.out.println("PASSWORD: " + users.get(i).getPassword());
      }

      model.addAttribute("user", user);
      return "form";

    } else {
      redirectAttributes.addFlashAttribute("error", "Invalid credentials");
      return "redirect:/login";
    }
    
  }

  @RequestMapping(value = "/register", method = RequestMethod.GET)
  public String loadRegistrationForm() {
    return "register";
  }

  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public String submitRegistrationForm(@RequestParam String username, @RequestParam String password, Model model, RedirectAttributes redirectAttributes) {
    if (userRepository.findByUsername(username) == null) {
      User newUser = new User(username, password, "user");
      userRepository.save(newUser);
      model.addAttribute("user", newUser);
      return "login";
    } else {
      redirectAttributes.addFlashAttribute("error", "Username is already taken");
      return "redirect:/register";
    }
  }
}