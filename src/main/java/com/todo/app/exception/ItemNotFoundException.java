package com.todo.app.exception;

public class ItemNotFoundException extends RuntimeException {

		private static final long serialVersionUID = 1L;
		
		public ItemNotFoundException(Long todoId) {
			super("商品コード：" + todoId + "は見つかりません");
		}

}
