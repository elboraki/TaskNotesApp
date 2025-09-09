package com.labgeek.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private static final String URL="jdbc:mysql://localhost:3306/tasks_notes";
    private static final String USER = "admin";
    private static final String PASSWORD = "admin";

    private static DatabaseConnection instance;
    private Connection connection;


    private DatabaseConnection() throws SQLException{
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
    		this.connection=DriverManager.getConnection(URL,USER,PASSWORD);
            System.out.println("✅ Database connected successfully!");

    	}catch(ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found!", e);
    	}
    }

    public static DatabaseConnection getInstance() throws SQLException{
    	if(instance==null || instance.getConnection().isClosed()) {
    		instance=new DatabaseConnection();
    	}
		return instance;

    }

 // Getter for connection
    public Connection getConnection() {
        return connection;
    }
}
