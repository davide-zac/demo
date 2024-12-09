package com.project.prova.demo.controller;
import org.springframework.web.bind.annotation.*;


@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Benvenuto alla Home Page pubblica!";
    }
}
