package vn.fs.controller.admin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import vn.fs.entities.Product;
import vn.fs.entities.User;
import vn.fs.repository.UserRepository;

/**
 * @author DongTHD
 *
 */
@Controller
public class UserController{

	@Autowired
	UserRepository userRepository;

	@GetMapping(value = "/admin/users")
	public String customer(Model model, Principal principal) {
		
		User user = userRepository.findByEmail(principal.getName());
		model.addAttribute("user", user);
		
		List<User> users = userRepository.findAll();
		model.addAttribute("users", users);
		
		return "/admin/users";
	}
	
	@GetMapping(value = "/admin/editUser/{userId}")
	public String editCategory(@PathVariable("userId") Long id, ModelMap model) {
		User user = userRepository.findById(id).orElse(null);
		
		model.addAttribute("user", user);

		return "admin/editUser";
	}
	
	@PostMapping(value = "/admin/adduser")
	public String addProduct(@ModelAttribute("user") User user, ModelMap model, HttpServletRequest httpServletRequest) {
		User u = userRepository.save(user);
		if (null != u) {
			model.addAttribute("message", "Update success");
			model.addAttribute("user", user);
		} else {
			model.addAttribute("message", "Update failure");
			model.addAttribute("user", user);
		}
		return "redirect:/admin/users";
	}
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
}
