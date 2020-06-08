package view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import connection.DAOAcces;

/**
 * Servlet implementation class Connexion
 */
@WebServlet("/Connexion")
public class Connexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Connexion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();
		
		//Ouvre la connexion
		DAOAcces dao = new DAOAcces("com.mysql.jdbc.Driver", "tableurnotes", "root", "");
		//Formulaire de connexion
			out.println("<html><head></head><body>"+
						"<div align=center><form name = \"FormConnexion\" action=\"ControleurConnexion\" method=GET>"
						+ "<h1>Connexion</h1><br>"
						+ "<br>");
			
			out.println("Nom: <input type=\"text\" name=\"nameCo\"> </input> <br><br>");
			out.println("Mot de passe: <input type=\"password\" name=\"mdpCo\"> </input> <br><br>");

			
			out.print("<input type=\"submit\" value = \"Valider\" /></input>");   
			out.print("</form></div></body></html>");
			
	        //Ferme la connexion
			dao.closeConnection();
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
