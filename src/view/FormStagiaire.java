package view;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FormStagiaire
 */
@WebServlet("/FormStagiaire")
public class FormStagiaire extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FormStagiaire() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		
			out.println("<h1 align=center>Formulaire ajout d'un stagiaire : </h1></br>"
					+ "	<div align=center><form action='AjtS' method=GET>"
					+ "		Nom : <input type='text' name='newN' value=''></input></br>"
					+ "		Prenom : <input align=center type='text' name='newP' value=''></input></br>"
					+ "<input type=submit name='newSt' value='Ajout Stagiaire'></input>");
			out.println("</form>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
