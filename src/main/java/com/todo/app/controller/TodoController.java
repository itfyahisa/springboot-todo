package com.todo.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.todo.app.exception.ItemNotFoundException;
import com.todo.app.model.Todo;
import com.todo.app.service.TodoService;

//コントローラ実装
@RestController
public class TodoController {

	@Autowired
	private TodoService todoService;
	
	//getAllTodo
	@GetMapping("/todos")
	public List<Todo> getAllTodos(){
		return todoService.getAllTodos();
	}
	
	//getTodo
	@GetMapping("/todos/{todoId}")
	public Todo getTodo(@PathVariable("todoId") Long todoId) {
		return todoService.getTodo(todoId).orElseThrow(() -> new ItemNotFoundException(todoId));
	}
	
	//PostTodo
	@PostMapping("/todos")
	public void addItem(@RequestBody Todo todo) {
		todoService.addTodo(todo);
	}
	
	//PutTodo
	@PutMapping("/todos/{todoId}")
	public void updateTodo(@PathVariable("todoId") Long todoId, @RequestBody Todo todo) {
		if(todoService.getTodo(todoId).isPresent()) {
			todoService.updateTodo(todoId, todo);
		}else {
			throw new ItemNotFoundException(todoId);
		}
	}
	
	//DelelteTodo
	@DeleteMapping("/todos/{todoId}")
	public void deleteTodo(@PathVariable("todoId") Long todoId) {
		if(todoService.getTodo(todoId).isPresent()) {
			todoService.deleteTodo(todoId);
		}else {
			throw new ItemNotFoundException(todoId);
		}	
	}
	
	
}
