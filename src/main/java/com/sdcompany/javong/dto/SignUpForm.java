package com.sdcompany.javong.dto;

import com.sdcompany.javong.entity.SignIn;
import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class SignUpForm {
    private String email;
    private String nickname;
    private String password;
    private String confPassword;

    public SignIn toEntity() {

        return new SignIn(null, email, nickname, password);
    }
}
