package com.sdcompany.javong.entity;

import jakarta.persistence.*;
import lombok.*;


@Data
@Entity     // Database와 연동 ( 이 객체를 Entity로 인식함 )
@AllArgsConstructor
@ToString
//@NamedQuery(name="SignIn.findByTheEmail", query = "from SIGN_IN si where si.email = ?1")
public class SignIn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column
    private String nickname;

    @Column
    private String password;

    public SignIn() {
        this.id = null;
        this.email = "";
        this.password = "";
    }
}
