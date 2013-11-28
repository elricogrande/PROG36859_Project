package ca.sheridan.data;

import java.sql.Date;

public class Resource {
	private int rid;
	private long isbn;
	private String title;
	private Date pubdate;
	private Publisher publisher;
	private double price;
	private String rtype;
	private Author[] authors;
	private Date loanDate;
	private Date returnDate;
	private Date dueDate;
	private Date holdDate;
}
