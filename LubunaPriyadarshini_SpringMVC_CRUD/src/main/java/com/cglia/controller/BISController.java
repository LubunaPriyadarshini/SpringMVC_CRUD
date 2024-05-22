package com.cglia.controller;

import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cglia.model.Book;
import com.cglia.service.BookService;

@Controller(value="bisController")
public class BISController {
	@Autowired
	private BookService service;
	
	@GetMapping({ "/home", "/" })
	public String showHome() {
		return "Home";
	}

	@GetMapping("/add")
	public String addBook() {
		return "AddBook";
	}

	@RequestMapping(path = "/save", method = RequestMethod.POST)
	public String saveBook(@ModelAttribute Book book, HttpServletRequest request, RedirectAttributes attrs) {
		System.out.println(book);
		//BookService service = new BookServiceImpl();
		Integer id = service.saveBook(book);
		// request.setAttribute("id", id);
		attrs.addFlashAttribute("id", id);
		if (id > 0) {
			attrs.addFlashAttribute("successmsg", "Book saved successfully with ID:" + id);
			// request.setAttribute("successmsg", "Book saved successfully with ID:" + id);
		} else {
			attrs.addFlashAttribute("failuremsg", "System problem. Please contact Help desk.");
			// request.setAttribute("failuremsg", "System problem. Please contact Help
			// desk.");
		}

		return "redirect:/getAll";
	}

	@RequestMapping(path = "/getAll", method = RequestMethod.GET)
	public String getAllBook(@ModelAttribute Book book,HttpServletRequest request) {
		//BookService service = new BookServiceImpl();
		List<Book> bookList = service.getAllBook();
		request.setAttribute("bookList", bookList);
		System.out.println(bookList);
		return "ShowAllBook";
	}

	@GetMapping("/getBook")
	public String getBookById(@RequestParam("id") Integer id, HttpServletRequest request) {
		//BookService service = new BookServiceImpl();
		Book book = service.getBookById(id);
		request.setAttribute("book", book);
		return "UpdateBook";
	}

	@PostMapping("/update")
	public String updateBookById(@ModelAttribute Book book, RedirectAttributes attrs) {
		//BookService service = new BookServiceImpl();
		int count = service.updateBook(book);
		attrs.addFlashAttribute("updatecount", count);
		if (count > 0) {
			attrs.addFlashAttribute("updated", "Employee with ID: " + book.getId() + "is updated successfully.");
		} else {
			attrs.addFlashAttribute("notupdated", "an error occurred. Please try again...");
		}
		return "redirect:/getAll";
	}

	@PostMapping("/delete")
	public String deleteBook(HttpServletRequest request, RedirectAttributes attrs) {
		Integer id = null;
		int count = 0;
		if (Objects.nonNull(request.getParameter("idtodelete"))) {
			id = Integer.parseInt(request.getParameter("idtodelete"));
		}

		//BookService service = new BookServiceImpl();
		if (Objects.nonNull(id)) {
			count = service.deleteBookById(id);
			attrs.addFlashAttribute("deletecount", count);
		}

		if (count > 0) {
			attrs.addFlashAttribute("deleted", "Employee with ID: " + id + " is deleted successfully.");
		} else {
			attrs.addFlashAttribute("notdeleted", "An error occurred. Please try again...");
		}
		return "redirect:/getAll";
	}

}
