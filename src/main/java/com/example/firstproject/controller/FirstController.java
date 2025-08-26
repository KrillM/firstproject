package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

    @GetMapping("/hi")
    public String niceToMeetU(Model model){
        model.addAttribute("username", "Krille");
        return "greetings";
    }

    @GetMapping("/bye")
    public String bye2(Model model){
        model.addAttribute("friend", "Krille");
        return "bye";
    }
}
