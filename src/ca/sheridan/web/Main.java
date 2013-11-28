package ca.sheridan.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ca.sheridan.data.DataAccess;

/**
 * Servlet implementation class Main
 */
@WebServlet("/Main")
public class Main extends HttpServlet {
	private DataAccess da;
	String errMessage = "";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Main() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("message", "");
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		ServletConfig config = getServletConfig();
		// This section is to be replaced by authentication code
		session.setAttribute("user", 11111);
		session.setAttribute("isAdmin", true);
		session.setAttribute("isLibrarian", true);
		session.setAttribute("isClient", true);
		//

		boolean showAdminOptions = false;
		boolean showLibrarianOptions = false;
		boolean showClientOptions = false;
		if (session.getAttribute("user") != null) {
			if ((Boolean)session.getAttribute("isAdmin")) {
				showAdminOptions = true;
			}
			if ((Boolean)session.getAttribute("isLibrarian")) {
				showLibrarianOptions = true;
			}
			if ((Boolean)session.getAttribute("isClient")) {
				showClientOptions = true;
			}
		}
		else {
			response.sendRedirect("/index.jsp");
		}
		session.setAttribute("showAdminOptions", showAdminOptions);
		session.setAttribute("showLibrarianOptions", showLibrarianOptions);
		session.setAttribute("showClientOptions", showClientOptions);
		RequestDispatcher rd = request.getRequestDispatcher("/MainDisplay.jsp");
		rd.forward(request, response);
	}

	@Override
	public void init() throws ServletException {
		//super.init(config);
		da = new DataAccess();
		if (!da.connect()) {
			errMessage += "DB: Can't connect to database.<br />";
			return;
		}
		else if (!da.prepareStatements()) {
			errMessage += "DB: Can't create SQL statements.<br />";
		}
	}

	@Override
	public void destroy() {
		da.disconnect();
		super.destroy();
	}
}
