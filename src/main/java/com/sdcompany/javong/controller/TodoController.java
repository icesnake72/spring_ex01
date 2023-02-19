package com.sdcompany.javong.controller;

import com.sdcompany.javong.dto.SignInForm;
import com.sdcompany.javong.dto.TodoForm;
import com.sdcompany.javong.entity.SignIn;
import com.sdcompany.javong.entity.TodoList;
import com.sdcompany.javong.repository.SignInRepository;
import com.sdcompany.javong.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@Controller
@Slf4j
public class TodoController {

    @Autowired
    private TodoRepository todoRepos;
    @Autowired
    private SignInRepository signInRepository;

    @PostMapping("/addtodo")
    public String AddTodo(@NotNull TodoForm todoForm, @NotNull Model model) {
        TodoList todo = todoForm.toEntity();
        TodoList savedTodo = todoRepos.save(todo);
        log.info( savedTodo.toString() );

        // userid 로 SignIn Entity에서 User정보를 불러온다
        Optional<SignIn> siList = signInRepository.findById(todo.getUserid());
        if ( !siList.isPresent() )
            throw (new IllegalArgumentException());

        log.info( siList.toString() );
        SignIn si = siList.get();
        model.addAttribute("username", si.getNickname());
        model.addAttribute("userid", si.getId());

        List<TodoList> todos = todoRepos.findByUserid(String.format("%d", si.getId()));
        if ( todos.isEmpty() ) {

        }
        else {

        }

        return "/hello";
    }

}
