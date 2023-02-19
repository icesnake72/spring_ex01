package com.sdcompany.javong.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@ToString
public class TodoList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long userid;

    @Column
    private String todo;

    @Column
    private boolean done;

    public TodoList() {
        id = null;
        userid = null;
        todo = "";
        done = false;
    }
}
