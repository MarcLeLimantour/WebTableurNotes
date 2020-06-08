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
 * Servlet implementation class CreationClasse
 */
@WebServlet("/CreationClasse")
public class CreationClasse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreationClasse() {
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
		
		//Formulaire d'ajout de classe
			out.println("<html><head></head><body>"+
						"<div align=center><form name = \"ajouterClasse\" action=\"ControleurAjtClasse\" method=GET>"
						+ "<h1>Ajouter une classe</h1><br>"
						+ "<br>");
			
			out.println("Nom de la classe: <input type=\"text\" name=\"nmCL\"> </input> <br><br>");
			
			out.print("<input type=\"submit\" value = \"Valider\" /></input></div>");   
			out.print("</form></body></html>");
			
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
