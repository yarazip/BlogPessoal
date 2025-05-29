package com.blogpessoal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontendController {
    
    @GetMapping({"/", "/login", "/error-page"})
    public String forwardRoot() {
        return "forward:/index.html";
    }

    @GetMapping("/{path:[a-zA-Z-]+}")
    public String forwardPaths() {
        return "forward:/index.html";
    }
    
    @GetMapping("/{path:[a-zA-Z-]+}/{subpath:.*}")
    public String forwardSubPaths() {
        return "forward:/index.html";
    }
}
