package com.cglia.service;

import java.util.List;

import com.cglia.model.Book;

public interface BookService {
	public Integer saveBook(Book book);

	public Book getBookById(Integer id);

	public int updateBook(Book book);

	public int deleteBookById(Integer id);

	public List<Book> getAllBook();
}
