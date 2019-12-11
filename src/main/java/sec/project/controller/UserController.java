package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.config.CustomUserDetailsService;
import org.springframework.ui.Model;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

  @Autowired
  private CustomUserDetailsService userDetailsService;

  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public String loadLoginForm() {
    return "login";
  }

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public String submitLoginForm(@RequestParam String username, @RequestParam String password, Model model, RedirectAttributes redirectAttributes) {
    try {
      UserDetails user = userDetailsService.loadUserByUsername(username);
      if (user.getPassword().equals(password)) {
        model.addAttribute("username", username);
        return "form";
      }
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("error", "Invalid credentials");
      return "redirect:/login";
    }

    return "redirect:/login";
  }

  @RequestMapping(value = "/register", method = RequestMethod.GET)
  public String loadRegistrationForm() {
    return "register";
  }

  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public String submitRegistrationForm(@RequestParam String username, @RequestParam String password, Model model, RedirectAttributes redirectAttributes) {
    try {
      UserDetails user = userDetailsService.loadUserByUsername(username);
      redirectAttributes.addFlashAttribute("error", "Username is already taken");
      return "redirect:/register";
    } catch (Exception e) {
      userDetailsService.saveNewUser(username, password);
      return "login";
    }
  }
}