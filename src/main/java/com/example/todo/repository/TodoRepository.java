package com.example.todo.repository;

import com.example.todo.model.Todo;
import java.util.ArrayList;

public interface TodoRepository{
    
    ArrayList<Todo> getTodos();
    Todo getTodoById(int todoId);
    Todo addTodo(Todo todo);

    Todo updateTodo(int todoId, Todo todo);

    void deleteTodo(int todoId);
}