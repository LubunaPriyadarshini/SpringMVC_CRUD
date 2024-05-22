package com.cglia.dao;

import java.util.List;

import com.cglia.model.Book;

public interface BookDAO {
	public Integer save(Book book);
	public Book getById(Integer id);
	public int update(Book book);
	public int deleteById(Integer id);
	public List<Book> getAllBook();
}
