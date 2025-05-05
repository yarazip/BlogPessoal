package com.blogpessoal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontendController {
    
    @GetMapping({
        "/",
        "/login",
        "/cadastro",
        "/postagens",
        "/temas",
        "/perfil",
        "/{path:^(?!api$|assets$|static$)[^\\.]*$}/**"
    })
    public String forwardToAngular() {
        return "forward:/index.html";
    }
}
