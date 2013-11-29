package ca.sheridan.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
	
	public DataAccess() {
		connect();
	}
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
	 * This method closes all prepared statements and tears down the connection to the database.
	 * @return boolean true if the connection tear-down is successful, otherwise false.
	 */
	public boolean disconnect() {
		try {
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
	
	public ArrayList<Resource> searchResources(String searchTerm) {

		ArrayList<Resource> searchResults = new ArrayList<Resource>();
		ResultSet rs;
		Resource result;
		Author author;
		ArrayList<Author> authors;
		Publisher publisher;
		Address address;
		boolean continueFlag = false;
		String query = "SELECT r.*, a.*, p.* "
				+ "FROM resources r "
				+ "JOIN authors_resources ar "
				+ "ON(r.isbn=ar.isbn) "
				+ "JOIN authors a "
				+ "ON(ar.aid=a.aid) "
				+ "JOIN publishers p "
				+ "ON (r.pubid=p.pubid)";
				/*+ "WHERE r.isbn=?"
				+ " OR r.title LIKE (%?%)"
				+ " OR a.fname LIKE (%?%)"
				+ " OR a.lname LIKE (%?%)"
				+ " OR p.pubname LIKE (%?%)"
				*/
				;
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			//ps.setLong(1, DataConverter.isbn(searchTerm));
			//ps.setString(2, searchTerm);
			//ps.setString(3, searchTerm);
			//ps.setString(4, searchTerm);
			rs = ps.executeQuery();
			while (rs.next()) {
				result = new Resource();
				publisher = new Publisher();
				author = new Author();
				authors = new ArrayList<Author>();
				address = new Address();
				result.setRid(rs.getInt("r.rid"));
				result.setIsbn(rs.getLong("r.isbn"));
				author.setAid(rs.getInt("a.aid"));
				author.setFname(rs.getString("a.fname"));
				author.setLname(rs.getString("a.lname"));
				if (inResults(searchResults, result, author)) {
					continue;
				}
				authors.add(author);
				result.setAuthors(authors);
				result.setTitle(rs.getString("r.title"));
				result.setPubdate(rs.getDate("r.pubdate"));
				result.setPrice(rs.getDouble("r.price"));
				result.setRtype(rs.getString("rtype"));
				address.setStreet(rs.getString("p.street"));
				address.setCity(rs.getString("p.city"));
				address.setProvince(rs.getString("p.province"));
				address.setPostCode(rs.getString("p.postcode"));
				publisher.setAddress(address);
				publisher.setName(rs.getString("p.pubname"));
				result.setPublisher(publisher);
				result.setLoanDate(rs.getDate("r.ldate"));
				result.setReturnDate(rs.getDate("r.rdate"));
				result.setDueDate(rs.getDate("r.ddate"));
				result.setHoldDate(rs.getDate("r.hdate"));
				searchResults.ensureCapacity(searchResults.size() + 1);
				searchResults.add(result);
			}
		}
		catch (SQLException e){
			e.printStackTrace();
			return null;
		}
		return searchResults;
	}
	
	private boolean inResults (ArrayList<Resource> searchResults, Resource result, Author author) {
		for (int i = 0; i < searchResults.size(); i++) {
			if (result.getIsbn() == searchResults.get(i).getIsbn()) {
				searchResults.get(i).getAuthors().ensureCapacity(searchResults.get(i).getAuthors().size() + 1);
				searchResults.get(i).getAuthors().add(author);
				return true;
			}
		}
		return false;
	}
	
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
