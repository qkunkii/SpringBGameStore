package com.qkunkii.qkn.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String HomeBlog(Model model) {
        model.addAttribute("title", "Home");
        return "home";
    }
    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "About me");
        model.addAttribute("text", "About me");
        return "home";
    }

}