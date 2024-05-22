package com.cglia.dao.impl;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cglia.dao.BookDAO;
import com.cglia.model.Book;
import com.cglia.util.DataBaseUtil;

@Repository
public class BookDAOImpl implements BookDAO {
	@Override
	public Integer save(Book book) {
		final String query = "INSERT INTO book (name, author, price) values (?,?,?)";
		Integer id = 0;
		try (Connection con = DataBaseUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
			ps.setString(1, book.getName());
			ps.setString(2, book.getAuthor());
			ps.setDouble(3, book.getPrice());

			int count = ps.executeUpdate();
			if (count != 0) {
				try (ResultSet rs = ps.getGeneratedKeys()) {
					if (rs.next()) {
						id = rs.getInt(1);
						System.out.println("Book saved with id=" + id);
					}
					
				}
			} else {
				System.out.println("Book save failed");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public Book getById(Integer id) {
		final String query = "SELECT * FROM book WHERE id = ?";
		Book book = null;
		try (Connection con = DataBaseUtil.getConnection(); PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					book = new Book();
					book.setId(rs.getInt("id"));
					book.setName(rs.getString("name"));
					book.setAuthor(rs.getString("author"));
					book.setPrice(rs.getDouble("price"));

				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return book;
	}

	public int update(Book book) {
		final String query = "UPDATE book SET name= ?,author=?,price=? WHERE id=?";
		int count = 0;
		try (Connection con = DataBaseUtil.getConnection(); PreparedStatement stmt = con.prepareStatement(query);) {
			//stmt.setInt(1, book.getId());
			stmt.setString(1, book.getName());
			stmt.setString(2, book.getAuthor());
			stmt.setDouble(3, book.getPrice());
			stmt.setInt(4, book.getId());
			count = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public int deleteById(Integer id) {
		final String query = "DELETE FROM book WHERE id=?";
		int count = 0;
		try (Connection con = DataBaseUtil.getConnection(); PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setInt(1, id);
			count = stmt.executeUpdate();
			if (count != 0) {
				System.out.println("Employee with ID:" + id + " is deleted.");
			} else {
				System.out.println("Deletion failed.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public List<Book> getAllBook() {
		final String query = "SELECT * FROM book";
		List<Book> bookList = new ArrayList<>();
		try (Connection con = DataBaseUtil.getConnection(); Statement stmt = con.createStatement();) {
			boolean areAnyRecords = stmt.execute(query);
			if (areAnyRecords) {
				try (ResultSet rs = stmt.getResultSet()) {
					if (rs != null) {
						while (rs.next()) {
							Book book = new Book();
							book.setId(rs.getInt("id"));
							book.setName(rs.getString(2));
							book.setAuthor(rs.getString(3));
							book.setPrice(rs.getDouble(4));
							bookList.add(book);
						}
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bookList;
	}

}
