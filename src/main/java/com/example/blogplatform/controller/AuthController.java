package com.example.blogplatform.controller;

import com.example.blogplatform.dto.UserRegistrationDto;
import com.example.blogplatform.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") UserRegistrationDto registrationDto,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {

        // التحقق من تطابق كلمة المرور
        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error.user", "كلمة المرور وتأكيدها غير متطابقتين");
        }

        // التحقق من وجود اسم المستخدم
        if (userService.existsByUsername(registrationDto.getUsername())) {
            result.rejectValue("username", "error.user", "اسم المستخدم موجود بالفعل");
        }

        // التحقق من وجود البريد الإلكتروني
        if (userService.existsByEmail(registrationDto.getEmail())) {
            result.rejectValue("email", "error.user", "البريد الإلكتروني مسجل بالفعل");
        }

        if (result.hasErrors()) {
            return "register";
        }

        try {
            userService.save(registrationDto);
            redirectAttributes.addFlashAttribute("successMessage", "تم التسجيل بنجاح! يمكنك الآن تسجيل الدخول.");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "حدث خطأ أثناء التسجيل: " + e.getMessage());
            return "register";
        }
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}