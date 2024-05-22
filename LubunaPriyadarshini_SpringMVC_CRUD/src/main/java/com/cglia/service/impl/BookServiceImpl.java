package com.cglia.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglia.dao.BookDAO;
import com.cglia.dao.impl.BookDAOImpl;
import com.cglia.model.Book;
import com.cglia.service.BookService;
@Service
public class BookServiceImpl implements BookService {
	@Autowired
	private BookDAO dao;
	public Integer saveBook(Book book) {
		//BookDAO dao = new BookDAOImpl();
		Integer id = dao.save(book);
		return id;
	}

	@Override
	public Book getBookById(Integer id) {
		///EmployeeDAO dao=new EmployeeDAOImpl();
				Book book=dao.getById(id);
				return book;
		
	}
	@Override
	public int updateBook(Book book) {
		//EmployeeDAO dao=new EmployeeDAOImpl();
				int count=dao.update(book);
				return count;
	}


	@Override
	public int deleteBookById(Integer id) {
		//EmployeeDAO dao=new EmployeeDAOImpl();
				int count=dao.deleteById(id);
				return count;

	}

	@Override
	public List<Book> getAllBook() {
		//EmployeeDAO dao=new EmployeeDAOImpl();
		List<Book> bookList=dao.getAllBook();
		return bookList;
		
	}

	
}
