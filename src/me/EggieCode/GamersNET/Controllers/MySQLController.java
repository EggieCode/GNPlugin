package me.EggieCode.GamersNET.Controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import org.bukkit.Bukkit;

public abstract class MySQLController {
	private String hostname = "";
	private String portnmbr = "";
	private String username = "";
	private String password = "";
	private String database = "";
	protected boolean connected;
	protected Connection connection;
	protected String url = "";
	/**
	 * 
	 * @param Hostname of the MySQL Server
	 * @param Port number of the MySQL Server (Empty for standard port 3306)
	 * @param Database name
	 * @param Username to login the MySQL Server
	 * @param Password to login the MySQL Server
	 */
	protected MySQLController(String hostname, String portnmbr, String database, String username, String password) {
		this.hostname = hostname;
		this.portnmbr = portnmbr;
		this.database = database;
		this.username = username;
		this.password = password;
		if(portnmbr == null || portnmbr == "") {
			this.portnmbr = "3306";
			
		}
		if(password == null || password == "") {
			this.password = "";
			
		}
	}

	/**
	 * open database connection
	 *  
	 *  */
	protected Connection open() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		url = "jdbc:mysql://" + this.hostname + ":" + this.portnmbr + "/" + this.database + "?autoReconnect=true&failOverReadOnly=false&maxReconnects=3";
		this.connection = DriverManager.getConnection(url, this.username, this.password);
		return this.connection;
	}

	/**
	 * close database connection
	 * */
	protected void close() throws Exception{
		if (connection != null)
			connection.close();
	}

	/**
	 * returns the active connection
	 * 
	 * @return Connection
	 * 
	 * */
	
	protected Connection getConnection() {
		return this.connection;
	}

	/**
	 * checks if the connection is still active
	 * 
	 * @return true if still active
	 * */
	protected boolean checkConnection() {
		if (connection != null)
			return true;
		return false;
	}

	/**
	 * Query the database
	 * 
	 * @param query the database query
	 * @return ResultSet of the query
	 * 
	 * @throws SQLException
	 * */
	protected ResultSet query(String query) throws SQLException {
		Statement statement = null;
		ResultSet result = null;
		try {

			Bukkit.getLogger().log(Level.INFO, "Select query: '"+query+ "'");
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			return result;
		} catch (SQLException e) {
			if (e.getMessage().equals("Can not issue data manipulation statements with executeQuery().")) {
				try {
					statement.executeUpdate(query);
				} catch (SQLException ex) {
					if (e.getMessage().startsWith("You have an error in your SQL syntax;")) {
						String temp = (e.getMessage().split(";")[0].substring(0, 36) + e.getMessage().split(";")[1].substring(91));
						temp = temp.substring(0, temp.lastIndexOf("'"));
						throw new SQLException(temp);
					} else {
						ex.printStackTrace();
					}
				}
			} else if (e.getMessage().startsWith("You have an error in your SQL syntax;")) {
				String temp = (e.getMessage().split(";")[0].substring(0, 36) + e.getMessage().split(";")[1].substring(91));
				temp = temp.substring(0, temp.lastIndexOf("'"));
				throw new SQLException(temp);
			} else {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Empties a table
	 * 
	 * @param table the table to empty
	 * @return true if data-removal was successful.
	 * 
	 * */
	protected boolean clearTable(String table) {
		Statement statement = null;
		String query = null;
		try {
			statement = this.connection.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM " + table);
			if (result == null)
				return false;
			query = "DELETE FROM " + table;
			statement.executeUpdate(query);
				return true;
		} catch (SQLException e) {
				return false;
		}
	}
	
	/**
	 * Insert data into a table
	 *  
	 * @param table the table to insert data
	 * @param column a String[] of the columns to insert to
	 * @param value a String[] of the values to insert into the column (value[0] goes in column[0])
	 * 
	 * @return true if insertion was successful.
	 * */
	protected boolean insert(String table, String[] column, String[] value) {
		Statement statement = null;
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		for (String s : column) {
			sb1.append(s + ",");
		}
		for (String s : value) {
			sb2.append(""+s + ",");
		}
		String columns = sb1.toString().substring(0, sb1.toString().length() - 1);
		String values = sb2.toString().substring(0, sb2.toString().length() - 1);
		try {
			statement = this.connection.createStatement();
			String query = "INSERT INTO " + table + "(" + columns + ") VALUES (" + values + ")";
			Bukkit.getLogger().log(Level.INFO, "Insert query: '"+query+ "'");
			statement.execute(query);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Delete a table
	 * 
	 * @param table the table to delete
	 * @return true if deletion was successful.
	 * */
	protected boolean deleteTable(String table) {
		Statement statement = null;
		try {
			if (table.equals("") || table == null) {
				return true;
			}
			statement = connection.createStatement();
			statement.executeUpdate("DROP TABLE " + table);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
