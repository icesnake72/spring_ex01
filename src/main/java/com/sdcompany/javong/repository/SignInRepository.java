package com.sdcompany.javong.repository;

import com.sdcompany.javong.entity.SignIn;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface SignInRepository extends ListCrudRepository<SignIn, Long> {

//    @Query("select si from SIGN_IN si where si.email = :email")
    public List<SignIn> findByEmail(String email);
}
