package com.example.blogplatform.controller;

import com.example.blogplatform.model.User;
import com.example.blogplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/profile")
public class ProfileEditController {

    @Autowired
    private UserService userService;

    @GetMapping("/edit")
    public String showEditForm(@AuthenticationPrincipal UserDetails currentUser, Model model) {
        User user = userService.findByUsername(currentUser.getUsername())
                .orElseThrow(() -> new RuntimeException("المستخدم غير موجود"));

        model.addAttribute("user", user);
        return "profile-edit";
    }

    @PostMapping("/edit")
    public String updateProfile(@AuthenticationPrincipal UserDetails currentUser,
            @Valid User updatedUser,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            return "profile-edit";
        }

        User existingUser = userService.findByUsername(currentUser.getUsername())
                .orElseThrow(() -> new RuntimeException("المستخدم غير موجود"));

        // تحديث الحقول المسموح بها
        existingUser.setFullName(updatedUser.getFullName());
        existingUser.setBio(updatedUser.getBio());

        // ✅ استخدام الدالة الجديدة
        userService.updateUser(existingUser);

        return "redirect:/profile/" + currentUser.getUsername() + "?updated";
    }
}