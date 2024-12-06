package com.project.prova.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('TEST')")
    public String testDashboard() {
        log.info("Accesso al dashboard test");
        return "Benvenuto nella dashboard dei Test Users!";
    }
}

