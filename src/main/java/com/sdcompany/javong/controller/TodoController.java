package com.sdcompany.javong.controller;

import com.sdcompany.javong.dto.TodoForm;
import com.sdcompany.javong.entity.SignIn;
import com.sdcompany.javong.entity.TodoList;
import com.sdcompany.javong.repository.SignInRepository;
import com.sdcompany.javong.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        return ToHello(savedTodo.getUserid(), model);
    }

    @RequestMapping(value = "/todos")
    public String ReadTodos(@RequestParam Long userid, @NotNull Model model) {

        // Long lUserid = Long.parseLong(userid);

        return ToHello(userid, model);
    }

    @PostMapping("/todolist")
    public String TodoList(@RequestParam Long userid, Model model) {

        return ToHello(userid, model);
    }

    // @RequestMapping(value="/updatetodo")
//    public String UpdateTodo(@RequestParam Long id, @RequestParam Long userid, @RequestParam Boolean done, Model model) {
//
//        Optional<TodoList> todoList = todoRepos.findById(id);
//        if (!todoList.isPresent())
//            throw (new IllegalArgumentException());
//        TodoList todo = todoList.get();
//        todo.setDone(done);
//        try {
//            TodoList savedTodo = todoRepos.save(todo);
//        }
//        catch (IllegalArgumentException iexc){
//            String errMsg = String.format("todo 업데이트 중 IllegalArgumentException 발생 : %s", iexc.getMessage());
//            model.addAttribute("errorMsg", errMsg);
//            return "/error_msg";
//        }
//        catch (OptimisticLockingFailureException ofexc) {
//            String errMsg = String.format("todo 업데이트 중 OptimisticLockingFailureException 발생 : %s", ofexc.getMessage());
//            model.addAttribute("errorMsg", errMsg);
//            return "/error_msg";
//        }
//
//        return ToHello(userid, model);
//        // return String.format("/todos?userid=%d", userid);
//    }

    @PostMapping("/updatetodo")
    public String UpdateTodo(@RequestParam Long id, @RequestParam Long userid, @RequestParam Boolean done, Model model) {

        Optional<TodoList> todoList = todoRepos.findById(id);
        if (!todoList.isPresent())
            throw (new IllegalArgumentException());
        TodoList todo = todoList.get();
        todo.setDone(done);
        try {
            TodoList savedTodo = todoRepos.save(todo);
        }
        catch (IllegalArgumentException iexc){
            String errMsg = String.format("todo 업데이트 중 IllegalArgumentException 발생 : %s", iexc.getMessage());
            model.addAttribute("errorMsg", errMsg);
            return "/error_msg";
        }
        catch (OptimisticLockingFailureException ofexc) {
            String errMsg = String.format("todo 업데이트 중 OptimisticLockingFailureException 발생 : %s", ofexc.getMessage());
            model.addAttribute("errorMsg", errMsg);
            return "/error_msg";
        }

        return ToHello(userid, model);
    }


    public String ToHello(Long userid, Model model) {
        // userid 로 SignIn Entity에서 User정보를 불러온다
        Optional<SignIn> siList = signInRepository.findById(userid);
        if ( !siList.isPresent() )
            throw (new IllegalArgumentException());

        log.info( siList.toString() );
        SignIn si = siList.get();
        model.addAttribute("username", si.getNickname());
        model.addAttribute("userid", si.getId());

        List<TodoList> todos = todoRepos.findByUserid(String.format("%d", si.getId()));
        if ( todos.isEmpty() ) {
            log.info( "todos is empty!!!" );
        }
        else {
//            log.info( todos.toString() );
            ArrayList<TodoList> arrTodos = new ArrayList<>();
            for(TodoList myTodo : todos) {
                arrTodos.add(myTodo);
            }
            model.addAttribute("todos", arrTodos);
            log.info( arrTodos.toString() );
        }

        return "/hello";
    }
}
