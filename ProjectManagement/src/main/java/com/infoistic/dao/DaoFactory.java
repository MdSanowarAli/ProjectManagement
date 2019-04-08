package com.infoistic.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DaoFactory {
	private static String connectionUrl;
	private static String userName;
	private static String password;

	private static Connection defaultConn;

	public static Connection getDefaultConn() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		try {
			defaultConn.prepareStatement("SELECT 1").execute();
		} catch (Exception e) {
			defaultConn = DriverManager.getConnection(connectionUrl, userName, password);
			System.out.println("Success Re-connection..........@@@@@@");
		}
		return defaultConn;
	}
	static {
		Properties props = new Properties();
		try {
			//connectionUrl = "jdbc:mysql://192.168.75.11/projectManagement";
			connectionUrl = "jdbc:mysql://localhost:3306/projectmanagement";
			userName = "root";
			password = "0123";
			defaultConn = DriverManager.getConnection(connectionUrl, userName, password);
		} catch (SQLException e) {
			System.out.println("########## SQL DaoFactory ststic: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("########## EX DaoFactory ststic: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static Connection getNewConnection() {
		try {
			return DriverManager.getConnection(connectionUrl, userName, password);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("### New Connection could not be made.");
			return null;
		}
	}
}
