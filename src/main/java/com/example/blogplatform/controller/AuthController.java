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

/**
 * وحدة التحكم المسؤولة عن عمليات المصادقة (التسجيل وتسجيل الدخول).
 * تدير طلبات تسجيل المستخدمين الجديد وعرض صفحة تسجيل الدخول.
 */
@Controller
public class AuthController {

    @Autowired
    private UserService userService; // خدمة المستخدم للتعامل مع منطق الأعمال المتعلق بالمستخدمين

    /**
     * عرض نموذج التسجيل.
     * 
     * @param model نموذج لإضافة البيانات إلى الصفحة
     * @return اسم صفحة التسجيل (register.html)
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // إضافة كائن UserRegistrationDto لنقل البيانات بين العرض ووحدة التحكم
        model.addAttribute("user", new UserRegistrationDto());
        return "register";
    }

    /**
     * معالجة طلب تسجيل مستخدم جديد.
     * يقوم بالتحقق من صحة البيانات المدخلة، والتحقق من عدم تكرار اسم المستخدم أو
     * البريد الإلكتروني،
     * ثم حفظ المستخدم الجديد في قاعدة البيانات.
     * 
     * @param registrationDto كائن يحمل بيانات التسجيل من النموذج
     * @param result          يحتوي على نتائج التحقق من صحة البيانات (Bean
     *                        Validation)
     * @param model           نموذج لإضافة البيانات إلى الصفحة في حالة وجود أخطاء
     * @return إعادة توجيه إلى صفحة تسجيل الدخول إذا نجح التسجيل، أو العودة إلى صفحة
     *         التسجيل إذا فشل
     */
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") UserRegistrationDto registrationDto,
            BindingResult result,
            Model model) {

        // التحقق من وجود اسم المستخدم مسبقاً في قاعدة البيانات
        if (userService.existsByUsername(registrationDto.getUsername())) {
            result.rejectValue("username", "error.user", "اسم المستخدم موجود بالفعل"); // إضافة خطأ مخصص
        }

        // التحقق من وجود البريد الإلكتروني مسبقاً في قاعدة البيانات
        if (userService.existsByEmail(registrationDto.getEmail())) {
            result.rejectValue("email", "error.user", "البريد الإلكتروني مسجل بالفعل");
        }

        // إذا كانت هناك أخطاء في التحقق (سواء من Bean Validation أو من التحقق المخصص)،
        // نعود لصفحة التسجيل
        if (result.hasErrors()) {
            return "register";
        }

        // حفظ المستخدم الجديد في قاعدة البيانات
        userService.save(registrationDto);

        // إعادة التوجيه إلى صفحة تسجيل الدخول مع رسالة نجاح (يمكن إضافة flash
        // attribute)
        return "redirect:/login?success";
    }

    /**
     * عرض صفحة تسجيل الدخول.
     * Spring Security يتعامل مع منطق تسجيل الدخول تلقائياً،
     * هذه الدالة فقط تعرض الصفحة.
     * 
     * @return اسم صفحة تسجيل الدخول (login.html)
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}