package com.sdcompany.javong.repository;

import com.sdcompany.javong.entity.SignIn;
import com.sdcompany.javong.entity.TodoList;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface TodoRepository extends ListCrudRepository<TodoList, Long> {
    public List<TodoList> findByUserid(String userid);
}
