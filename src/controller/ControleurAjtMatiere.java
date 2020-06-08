package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import connection.DAOAcces;

/**
 * Servlet implementation class ControleurAjtMatiere
 */
@WebServlet("/ControleurAjtMatiere")
public class ControleurAjtMatiere extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControleurAjtMatiere() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter();
		
		DAOAcces dao = new DAOAcces("com.mysql.jdbc.Driver", "tableurnotes", "root", "");
		try {
			
			//Prépare la requête d'insertion de matière
			Statement creationMatiere = dao.getConn().createStatement();
            String nomMat = request.getParameter("nmMat");
            String sqlMat = "INSERT INTO matiere (`nomMatiere`) VALUES ('"+nomMat+"')";            
            
			//Si le champs du formulaire de création de classe n'est pas vide, ajoute une matière dans la BDD
            
            if(nomMat.equals(""))
            {
               creationMatiere.executeUpdate(sqlMat);
               request.getRequestDispatcher("/Accueil").forward(request, response);
            }
            else 
            {
            	request.getRequestDispatcher("/CreationMatiere").forward(request, response);
            }

		 }
	    catch(SQLException e) {
			System.out.println("Probleme SQL !!");
			e.printStackTrace();
		}
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
