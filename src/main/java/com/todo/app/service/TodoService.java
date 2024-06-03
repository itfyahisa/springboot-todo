package com.todo.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.todo.app.model.Todo;
import com.todo.app.repo.TodoRepository;

//業務処理実装
@Service
public class TodoService {

	@Autowired
	private TodoRepository todoRepository;
	
	//getAllTodo findAllはiterable型を返すため、forEachですべてとってくる。
	@Cacheable("getTodos") //キャッシュ有効
	public List<Todo> getAllTodos(){
		List<Todo> todos = new ArrayList<>();
		todoRepository.findAll().forEach(todos::add);
		return todos;
	}
	
	//getTodo findBy はOptional型を返す(nullチェックできる)
	@Cacheable(value = "getTodo", key = "#p0") //キャッシュ有効 p0=itemId
	public Optional<Todo> getTodo(Long todoId){
		return todoRepository.findById(todoId);
	}
	
	//addTodo
	@CacheEvict(value="getTodos", allEntries = true) //指定した文字列で管理されているキャッシュ全体を削除する。
	public void addTodo(Todo todo) {
		todoRepository.save(todo);
	}
	
	//updateTodo idがあったらsave	
	@Caching(evict = {
			@CacheEvict(value = "getTodo", key = "#p0"),
			@CacheEvict(value = "getTodos", allEntries = true)
	})
	public void updateTodo(Long todoId, Todo todo) {
		if(todoRepository.findById(todoId).get() != null) {
//		if(todoRepository.findById(todoId).isPresent()) {	
			todoRepository.save(todo);
		}
	}
	
	//deleteTodo
	@Caching(evict = {
			@CacheEvict(value = "getTodo", key = "#p0"),
			@CacheEvict(value = "getTodos", allEntries = true)
	})
	public void deleteTodo(Long todoId) {
		todoRepository.deleteById(todoId);
	}
	
}
