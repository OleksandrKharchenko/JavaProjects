package zad1;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MyServlet
 */
@WebServlet("/MyServletTPO")
public class MyServlet extends HttpServlet {
	private String userName = "root";
	private String password = "root";
	private String connectionURL = "jdbc:mysql://localhost:3307/bookstpo?verifyServerCertificate=false&useSSL=true";
	private String completeHTML = "";
	private String sortedByString = "";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("resource")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try { // fix for driver
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			String html = "<form style = \"text-align:center; \" action = \"MyServletTPO\">\r\n"
					+ "<select name = \"by\">\r\n" + "  <option>Find by Name</option>\r\n"
					+ "  <option>Find by Author</option>\r\n" + "</select>\r\n"
					+ "<input type=\"text\" name =\"name\">\r\n" + "<input type=\"submit\" value=\"Find\">\r\n"
					+ "<button name =\"all\">Show all books</button>\r\n" + "\r\n" + "</form>";

			PrintWriter pw = response.getWriter();
			pw.println(html);

			if (request.getParameter("name") != null) {
				if (request.getParameter("by").equalsIgnoreCase("Find by Name")) {
					this.sortedByString = "by Name";
					String parametr = request.getParameter("name");
					String books = getBooksByName(parametr);
					pw = response.getWriter();
					pw.println(books);

				}
				if (request.getParameter("by").equalsIgnoreCase("Find by Author")) {
					this.sortedByString = "by Author";
					String parametr = request.getParameter("name");
					String books = getBooksByAuthor(parametr);
					pw = response.getWriter();
					pw.println(books);
				}
				// pw.close();
			} else {
				String books = getAllBooks();
				pw = response.getWriter();
				pw.println(books);
			}

			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String getBooksByName(String name) throws Exception {
		Connection myCon = DriverManager.getConnection(connectionURL, userName, password);
		Statement myStatement = myCon.createStatement();
		ResultSet resultSet = myStatement
				.executeQuery("select * from books where Name like" + "'%" + name + "%'" + ";");
		StringBuilder sb = new StringBuilder();
		while (resultSet.next()) {
			sb.append(
					"<h4 style = \"margin-bottom: -15px; margin-left: 10px; text-align:left; font-weight: normal; \" >"
							+ resultSet.getString("idBooks") + ". " + "<b>" + resultSet.getString("Name") + "</b>" + "   by "
							+ resultSet.getString("Author") + " " + resultSet.getString("NumberOfPages").toString()
							+ "p " + "</h4>");
		}
		return sb.toString();
	}

	public String getBooksByAuthor(String author) throws Exception {
		Connection myCon = DriverManager.getConnection(connectionURL, userName, password);
		Statement myStatement = myCon.createStatement();
		ResultSet resultSet = myStatement
				.executeQuery("select * from books where Author like " + "'%" + author + "%'" + ";");
		StringBuilder sb = new StringBuilder();
		while (resultSet.next()) {
			sb.append(
					"<h4 style = \"margin-bottom: -15px; margin-left: 10px; text-align:left; font-weight: normal; \" >"
							+ resultSet.getString("idBooks") + ". " + "<b>" + resultSet.getString("Name") + "</b>" + "   by "
							+ resultSet.getString("Author") + " " + resultSet.getString("NumberOfPages").toString()
							+ "p " + "</h4>");
		}
		return sb.toString();
	}

	public String getAllBooks() throws Exception {
		Connection myCon = DriverManager.getConnection(connectionURL, userName, password);
		Statement myStatement = myCon.createStatement();
		ResultSet resultSet = myStatement.executeQuery("select * from books");
		StringBuilder sb = new StringBuilder();
		while (resultSet.next()) {
			sb.append(
					"<h4 style = \"margin-bottom: -15px; margin-left: 10px; text-align:left; font-weight: normal; \" >"
							+ resultSet.getString("idBooks") + ". " + "<b>" + resultSet.getString("Name") + "</b>" + "   by "
							+ resultSet.getString("Author") + " " + resultSet.getString("NumberOfPages").toString()
							+ "p " + "</h4>");
		}
		return sb.toString();
	}

}
