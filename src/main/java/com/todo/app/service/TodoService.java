package com.todo.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.app.model.Todo;
import com.todo.app.repo.TodoRepository;

//業務処理実装
@Service
public class TodoService {

	@Autowired
	private TodoRepository todoRepository;
	
	//getAllTodo findAllはiterable型を返すため、forEachですべてとってくる。
	public List<Todo> getAllTodos(){
		List<Todo> todos = new ArrayList<>();
		todoRepository.findAll().forEach(todos::add);
		return todos;
	}
	
	//getTodo findBy はOptional型を返す(nullチェックできる)
	public Optional<Todo> getTodo(Long todoId){
		return todoRepository.findById(todoId);
	}
	
	//addTodo
	public void addTodo(Todo todo) {
		todoRepository.save(todo);
	}
	
	//updateTodo idがあったらsave	
	public void updateTodo(Long todoId, Todo todo) {
//		if(todoRepository.findById(todoId).get() != null) {
		if(todoRepository.findById(todoId).isPresent()) {	
			todoRepository.save(todo);
		}
	}
	
	//deleteTodo
	public void deleteTodo(Long todoId) {
		todoRepository.deleteById(todoId);
	}
	
}
