package com.sdcompany.javong.controller;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URL;

@Controller
@Slf4j
public class JavongController {

    @GetMapping("/")
    public String StartPage() {
        log.info( "test" );

        return "index.html";
    }

    @RequestMapping("/hi")
    public String hello(@RequestParam("username")String username, Long userid, @NotNull Model model)
    {
        // model.addAttribute("username", "맨날코딩");
        model.addAttribute("username", username);
        model.addAttribute("userid", userid);

//        URL res = getClass().getClassLoader().getResource("icons8.png");
//        System.out.println( res );

        return "hello";
    }

    @GetMapping("/signup")
    public String SignUp(Model model) {
        return "/signup";
    }


}
