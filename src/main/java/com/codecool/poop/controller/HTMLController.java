package com.codecool.poop.controller;


import com.codecool.poop.model.Skills;
import com.codecool.poop.model.User;
import com.codecool.poop.service.SessionService;
import com.codecool.poop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;


@Controller
public class HTMLController {

    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @GetMapping(value = "/login")
    public String loginPage() {
        return "login";
    }


    @GetMapping(value = "/registration")
    public String registrationPage() {
        return "registration";
    }

    @GetMapping(value = "/")
    public String indexPage(Model model) {
        if (sessionService.getCurrentUser() == null) {
            return "redirect:/login";
        }
        model.addAttribute("user_name", sessionService.getCurrentUser().getUsername());
        return "index";
    }

    @GetMapping(value = "/user-profile")
    public String userProfilePage(Model model) {
        if (sessionService.getCurrentUser() == null) {
            return "redirect:/login";
        }
        User user = sessionService.getCurrentUser();
        Map<Skills, Integer> skills = user.getExperiences();
        model.addAttribute("user_name", user.getUsername());
        model.addAttribute("skills", skills);
        return "user_profile";
    }

    @GetMapping(value = "/logout")
    public String logout() {
        sessionService.setCurrentUser(null);
        return "redirect:/login";
    }

    @GetMapping(value = "/white-room")
    public String whiteRoom(Model model) {
        if (sessionService.getCurrentUser() == null) {
            return "redirect:/login";
        }
        User user = sessionService.getCurrentUser();
        model.addAttribute("user_name", user.getUsername());
        return "rooms/white_room";
    }

    @GetMapping(value = "/green-room")
    public String greenRoom(Model model) {
        if (sessionService.getCurrentUser() == null) {
            return "redirect:/login";
        }
        User user = sessionService.getCurrentUser();
        model.addAttribute("user_name", user.getUsername());
        return "rooms/green_room";
    }

    @GetMapping(value = "/blue-room")
    public String blueRoom(Model model) {
        if (sessionService.getCurrentUser() == null) {
            return "redirect:/login";
        }
        User user = sessionService.getCurrentUser();
        model.addAttribute("user_name", user.getUsername());
        return "rooms/blue_room";
    }

    @GetMapping(value = "/red-room")
    public String redRoom(Model model) {
        if (sessionService.getCurrentUser() == null) {
            return "redirect:/login";
        }
        User user = sessionService.getCurrentUser();
        model.addAttribute("user_name", user.getUsername());
        return "rooms/red_room";
    }


}
