package ca.sheridan.web.client;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ca.sheridan.data.DataAccess;
import ca.sheridan.data.Resource;

/**
 * Servlet implementation class Search
 */
@WebServlet("/Search")
public class Search extends HttpServlet {
	DataAccess da;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String searchTerm = request.getParameter("searchTerm");
		if (searchTerm == null) {
			searchTerm = "";
		}
		System.out.println(searchTerm);
		RequestDispatcher rd = request.getRequestDispatcher("/SearchDisplay.jsp");
		if (!(Boolean)session.getAttribute("isClient")) {
			response.sendRedirect("/Unauthorized");
		}
		da = new DataAccess();
		if (searchTerm != null) {
			ArrayList<Resource> searchResults = da.searchResources(searchTerm);
			request.setAttribute("searchResults", searchResults);
			rd.forward(request, response);
		}
	}
}
