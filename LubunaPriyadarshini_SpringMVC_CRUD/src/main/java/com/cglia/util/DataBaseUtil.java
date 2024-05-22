package com.cglia.util;

import java.sql.Connection;

import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;

public class DataBaseUtil {
	private static BasicDataSource dataSource;

	static {
		dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/bookdb");
		dataSource.setUsername("root");
		dataSource.setPassword("Lubuna@123");
	}

	public static Connection getConnection() throws SQLException {
		Connection connection = dataSource.getConnection();

		if (connection != null && !connection.isClosed()) {
			System.out.println("Connection established successfully!");

		} else {
			System.out.println("Failed to establish connection.");
		}
		return connection;
	}
}
