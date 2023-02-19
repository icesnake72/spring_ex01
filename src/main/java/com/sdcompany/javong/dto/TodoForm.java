package com.sdcompany.javong.dto;


import com.sdcompany.javong.entity.SignIn;
import com.sdcompany.javong.entity.TodoList;
import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class TodoForm {
    private String todo;
    private String userid;

    public TodoList toEntity() {

        return new TodoList(null, Long.parseLong(userid), todo, false);
    }

}
