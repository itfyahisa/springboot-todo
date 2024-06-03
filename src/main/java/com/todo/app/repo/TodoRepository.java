package com.todo.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todo.app.model.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long>{
	//データ操作インターフェース実装
}
