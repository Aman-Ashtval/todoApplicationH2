package com.example.todo.service;

import java.util.*;
import com.example.todo.model.*;
import com.example.todo.repository.TodoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TodoH2Service implements TodoRepository{
    
    @Autowired
    private JdbcTemplate db;

    @Override 
    public ArrayList<Todo> getTodos(){
        List<Todo> todoList = db.query("SELECT * FROM TODOLIST", new TodoRowMapper());
        return new ArrayList<>(todoList);
    }

    @Override 
    public Todo getTodoById(int todoId){
        try{
            Todo existTodo = db.queryForObject("SELECT * FROM TODOLIST WHERE id = ?", new TodoRowMapper(), todoId);
            return existTodo;
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Todo addTodo(Todo todo){
        db.update("INSERT INTO TODOLIST (todo, status, priority) VALUES(?, ?, ?)", todo.getTodo(), todo.getStatus(), todo.getPriority());
        Todo updatedTodo = db.queryForObject("SELECT * FROM TODOLIST WHERE todo=? and status", new TodoRowMapper(), todo.getTodo(), todo.getStatus());
        return updatedTodo;
    }

    @Override 
    public Todo updateTodo(int todoId, Todo todo){
        if(todo.getTodo() != null){
            db.update("UPDATE TODOLIST SET todo = ? WHERE id = ?", todo.getTodo(), todoId);
        }
        if(todo.getStatus() != null){
            db.update("UPDATE TODOLIST SET status = ? WHERE id = ?", todo.getStatus(), todoId);
        }    
        if(todo.getPriority() != null){
            db.update("UPDATE TODOLIST SET priority = ? WHERE id = ?", todo.getPriority(), todoId);
        }

        return getTodoById(todoId);
    }

    @Override 
    public void deleteTodo(int todoId){
        db.update("DELETE FROM TODOLIST WHERE id = ?", todoId);
    }

}