package com.sdcompany.javong.controller;

import com.sdcompany.javong.dto.SignInForm;
import com.sdcompany.javong.dto.SignUpForm;
import com.sdcompany.javong.entity.SignIn;
import com.sdcompany.javong.repository.SignInRepository;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


@Controller
@Slf4j
public class SignInController {

    @Autowired
    private SignInRepository siRepos;

    private String makeHiUrl(String username, Long userid) {
        return String.format("/hi?username=%s&userid=%d", username, userid);
    }

    @PostMapping("/signin")
    public String TrySignIn(@NotNull SignInForm sf, Model model) {
        log.info( sf.toString() );

        SignIn si = sf.toEntity();
        log.info( si.toString() );

        List<SignIn> signIn = siRepos.findByEmail( si.getEmail() );
        for(SignIn sis : signIn){
            if ( sis.getEmail().equals(si.getEmail()) )
            {
                // read data
                // model.addAttribute("userid", sis.getId());
                String url = String.format("/todos?userid=%d", sis.getId());

                return url;
                // return "/todos";
            }
        }
        return "/signup";
    }


    @PostMapping("/trysignup")
    public String TrySignUp(@NotNull SignUpForm sf, Model model) {
        log.info( sf.toString() );

        SignIn si = sf.toEntity();
        log.info( si.toString() );

        SignIn savedSi = null;
        try {
            savedSi = siRepos.save(si);
        } catch (Exception e) {
            String strMsg = String.format("%s는 이미 존재하는 이메일입니다.", si.getEmail());
            model.addAttribute("errorMsg", strMsg);
            return "/error_msg";
        }

        log.info( savedSi.toString() );

        model.addAttribute("username", savedSi.getNickname());
        model.addAttribute("userid", savedSi.getId());

        return "hello";

        // return makeHiUrl(savedSi.getNickname(), savedSi.getId());
    }

}
