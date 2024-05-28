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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import vn.fs.entities.voucher;
import vn.fs.entities.User;
import vn.fs.repository.ProductRepository;
import vn.fs.repository.UserRepository;
import vn.fs.repository.VoucherRepository;

/**
 * @author DongTHD
 *
 */
@Controller
@RequestMapping("/admin")
public class VoucherController{
	
	@Autowired
	VoucherRepository voucherRepository;

	@Autowired
	UserRepository userRepository;

	@ModelAttribute(value = "user")
	public User user(Model model, Principal principal, User user) {

		if (principal != null) {
			model.addAttribute("user", new User());
			user = userRepository.findByEmail(principal.getName());
			model.addAttribute("user", user);
		}

		return user;
	}

	// show list voucher - table list
	@ModelAttribute("voucher")
	public List<voucher> showVoucher(Model model) {
		List<voucher> voucher = voucherRepository.findAll();
		model.addAttribute("voucher", voucher);

		return voucher;
	}

	@GetMapping(value = "/voucher")
	public String voucher(Model model, Principal principal) {
		voucher voucher = new voucher();
		model.addAttribute("voucher", voucher);

		return "admin/voucher";
	}

	// add voucher
	@PostMapping(value = "/addVoucher")
	public String addVoucher(@Validated @ModelAttribute("voucher") voucher voucher, ModelMap model,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("error", "failure");

			return "admin/voucher";
		}
		voucher.setCreateDate();
		voucherRepository.save(voucher);
		model.addAttribute("message", "successful!");

		return "redirect:/admin/voucher";
	}

	// get Edit voucher
	@GetMapping(value = "/editVoucher/{id}")
	public String editVoucher(@PathVariable("id") Long id, ModelMap model) {
		voucher voucher = voucherRepository.findById(id).orElse(null);

		model.addAttribute("voucher", voucher);

		return "admin/editvoucher";
	}

	// delete voucher
//	@GetMapping("/delete/{id}")
//	public String delVoucher(@PathVariable("id") Long id, Model model) {
//		voucherRepository.deleteById(id);
//
//		model.addAttribute("message", "Delete successful!");
//
//		return "redirect:/admin/voucher";
//	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
}
