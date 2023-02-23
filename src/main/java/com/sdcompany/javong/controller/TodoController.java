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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    //@Autowired
    //private RedirectAttributes redirect;



    @PostMapping("/addtodo")
    public String AddTodo(@NotNull TodoForm todoForm, @NotNull Model model, @NotNull RedirectAttributes attributes) {
        TodoList todo = todoForm.toEntity();
        TodoList savedTodo = todoRepos.save(todo);
        log.info( savedTodo.toString() );
        log.info( attributes.toString() );

        attributes.addAttribute("userid", savedTodo.getUserid());
        // attributes.addFlashAttribute("model", model);

        return String.format("redirect:/todolist");

        // return String.format("redirect:/todos?userid=%d", savedTodo.getUserid());
        // return ToHello(savedTodo.getUserid(), model);
    }

    @RequestMapping(value = "/todos")
    public String ReadTodos(@RequestParam Long userid, @NotNull Model model) {

        // Long lUserid = Long.parseLong(userid);

        return ToHello(userid, model);
    }

    @GetMapping("/todolist")
    public String TodoList(@RequestParam Long userid, @NotNull Model model) {
    //public String TodoList(@NotNull RedirectAttributes attributes) {
        log.info( String.format("@@@ TodoController.TodoList(userid=%d, model=%s", userid, model.toString()) );

        return ToHello(userid, model);
    }


    @DeleteMapping("/deltodo")
    public String DeleteTodo(@RequestParam Long id, Model model) {
        Optional<TodoList> todo = todoRepos.findById(id);
        if ( !todo.isPresent() )
        {
            String msg = String.format("해당 todo 데이터를 찾을 수 없습니다");
            model.addAttribute("error_msg", msg);
            return "/error_msg";
        }

        Long userid = todo.get().getUserid();
        todoRepos.deleteById(id);

        model.addAttribute("userid", userid);
        //redirect.addFlashAttribute("model", model);

        return String.format("redirect:/todolist");
        // return ToHello(userid, model);
    }



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
