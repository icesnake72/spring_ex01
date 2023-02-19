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
    public String TrySignIn(@NotNull SignInForm sf) {
        log.info( sf.toString() );

        SignIn si = sf.toEntity();
        log.info( si.toString() );

        List<SignIn> signIn = siRepos.findByEmail( si.getEmail() );
  //      log.info( signIn.getEmail() + signIn.getPassword() );
        for(SignIn sis : signIn){
            if ( sis.getEmail().equals(si.getEmail()))
                return makeHiUrl(sis.getNickname(), sis.getId());
        }



//        List<SignIn> sis = siRepos.findAll();


//        if ( sis instanceof Collection )
//            log.info( "Iterable is Collection!!");
//
//        for(SignIn sie : sis) {
//            log.info( sie.getEmail() );
//            log.info( si.getEmail() );
//            if ( sie.getEmail().equals(si.getEmail()) )
//                return makeHiUrl(sie.getEmail());
//        }


//        Iterator<SignIn> iter = sis.iterator();
//
//        while(iter.hasNext())
//        {
//            SignIn sie = iter.next();
//            log.info( sie.getEmail() );
//            log.info( si.getEmail() );
//            if ( sie.getEmail().equals(si.getEmail()) )
//                return makeHiUrl(sie.getEmail());
//        }

//        for(SignIn sie : sis) {
//            if ( sie.getEmail()==si.getEmail() )
//                return makeHiUrl(sie.getEmail());
//        }

        return "/signup";
    }

    @RequestMapping("/error_page")
    public String ErrorMessage(@RequestParam("errorMsg")String strError, @NotNull Model model) {
        model.addAttribute("errorMsg", strError);
        return "/error_msg";
    }




    @PostMapping("/trysignup")
    public String TrySignUp(@NotNull SignUpForm sf) {
        log.info( sf.toString() );

        SignIn si = sf.toEntity();
        log.info( si.toString() );

        SignIn savedSi = null;
        try {
            savedSi = siRepos.save(si);
        } catch (Exception e) {
            return "/error_page?errorMsg=이미 존재하는 이메일 입니다.";
        }

        log.info( savedSi.toString() );

        return makeHiUrl(savedSi.getNickname(), savedSi.getId());
    }

}
