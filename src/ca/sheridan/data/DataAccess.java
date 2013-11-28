package ca.sheridan.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class provides the main interface between the web application and the database.
 * @author Eric Milcic
 */
public class DataAccess {
	private String driverName = "com.mysql.jdbc.Driver";
	private String conURL = "jdbc:mysql://localhost/project";
	private String user = "root";
	private String pass = "assignment";
	Connection conn = null;
	private PreparedStatement ps;
	private PreparedStatement pa;
	private String select = "SELECT * FROM ProductCatalogue WHERE code=?";
	private String add = "INSERT INTO ProductCatalogue VALUES(?, ?, ?, ?)";
	
	/**
	 * This method establishes a connection to the database.
	 * @return boolean true if the connection is successfully established, otherwise false.
	 */
	public boolean connect() {
		try {
			Class.forName(driverName).newInstance();
			conn = DriverManager.getConnection(conURL, user, pass);			
		}
		
		catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * This method prepares all the prepared statements. It should be called only once
	 * for each instance of the class, after a connection is made.
	 * @return boolean true if all statements are prepared successfully, otherwise false.
	 */
	public boolean prepareStatements() {
		try {
			ps = conn.prepareStatement(select);
			pa = conn.prepareStatement(add);
		} 
		catch (SQLException | NullPointerException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * This method closes all prepared statements and tears down the connection to the database.
	 * @return boolean true if the connection tear-down is successful, otherwise false.
	 */
	public boolean disconnect() {
		try {
			ps.close();
			pa.close();
			conn.close();
		} 
		catch (SQLException | NullPointerException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Returns an Item object that matches the input code. Since code is the primary key in the table,
	 * there will be at most one result (or none if no match is found). An item with a price of -1.0 is
	 * returned if there is no match, in order to distinguish the result from a failed SQL query.
	 * @param code code is the bar code to search for in the database.
	 * @return Item if there is a match, an Item with price -1.0 if no match, and null in case of error.
	 */
	
	
	/*
	public Item getItem(String code) {
		Item item = null;
		try {
		ps.setString(1, code);
		ResultSet res = ps.executeQuery();
			// Since "code" is the primary key, we only need to grab the first matching item
			if (res.next()) {
				item = new Item(res.getString("code"), res.getString("name"), 
						res.getDouble("price"), res.getBoolean("taxable"));
			}
			else {
				item = new Item ("n/a", "n/a", -1.0, true);
			}
		} catch (SQLException | NullPointerException e) {
			return null;
		}
		return item;
	}
	
	/**
	 * Inserts a row into the ProductCatalogue table by setting the prepared statement rows to the values
	 * in the Item parameter, and executing the SQL statements.
	 * @param item object whose fields are to be inserted into the database.
	 * @return boolean if the Item is added successfully, otherwise false.
	 */
	
	/*
	public boolean addItem(Item item) {
		try {
			pa.setString(1, item.getCode());
			pa.setString(2, item.getName());
			pa.setDouble(3, item.getPrice());
			pa.setBoolean(4, item.isTaxable());
			pa.executeUpdate();
		} catch (SQLException | NullPointerException e) {
			return false;
		}
		return true;
	}
	*/
}
