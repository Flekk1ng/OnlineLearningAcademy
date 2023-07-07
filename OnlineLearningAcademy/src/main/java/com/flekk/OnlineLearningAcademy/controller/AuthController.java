package com.flekk.OnlineLearningAcademy.controller;

import com.flekk.OnlineLearningAcademy.Dto.view.RegisterUserDto;
import com.flekk.OnlineLearningAcademy.Dto.UserDto;
import com.flekk.OnlineLearningAcademy.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;


@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registration(Model model){
        RegisterUserDto registerUserDto = new RegisterUserDto();
        model.addAttribute("user", registerUserDto);
        return "register";
    }

    @PostMapping("/register")
    public String registration(@Valid @ModelAttribute("user") RegisterUserDto registerUserDto,
                               BindingResult result,
                               Model model){
        Optional<UserDto> existingUser = userService.findUserByEmail(registerUserDto.getEmail());

        if(existingUser.isPresent()) {
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()) {
            model.addAttribute("user", registerUserDto);
            return "/register";
        }

        userService.createUser(registerUserDto);
        return "redirect:/auth/login?success";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/auth/login?logout";
    }

}
