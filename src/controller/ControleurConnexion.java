package controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import connection.DAOAcces;

/**
 * Servlet implementation class ControleurConnexion
 */
@WebServlet("/ControleurConnexion")
public class ControleurConnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControleurConnexion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String nameCheck = (String)request.getParameter("nameCo");	
		String mdpCheck = (String)request.getParameter("mdpCo");

		
		
		if(nameCheck.equals("") || mdpCheck.equals(""))
		{
			getServletContext().getRequestDispatcher("/Connexion").forward(request, response);
			System.out.println("Veuillez compléter tout les champs svp !");

		}
		else
		{
			//Ouvre la connexion
			DAOAcces dao = new DAOAcces("com.mysql.jdbc.Driver", "tableurnotes", "root", "");
			try {
				Statement verifUtilisateur = dao.getConn().createStatement();
				Statement sta2 = dao.getConn().createStatement();
				String sql ="select * from utilisateur where nom='"+nameCheck+"'and password='"+mdpCheck+"'";
				
				ResultSet rs1 = verifUtilisateur.executeQuery(sql);
				 if(rs1.next()) 
				 {
					 HttpSession h = request.getSession();
						//Codage en dur du rôle d'un utilisateur
						h.setAttribute("role", rs1.getString("fonction"));
						
						int numStagiaire =rs1.getInt("numStagiaire");
						
						h.setAttribute("numStagiaire", numStagiaire);
						
						if(rs1.getString("fonction").equals("eleve")) 
						{
							String sql2 ="select * from stagiaire where idStagiaire="+numStagiaire+";";
							
							rs1.close();
							
							ResultSet rs2 = sta2.executeQuery(sql2);
							
							if (rs2.next()) {
								h.setAttribute("classeStagiaire",rs2.getInt("classeStagiaire"));
							}
							getServletContext().getRequestDispatcher("/TableauStagiaire").forward(request, response);
							
						}
						else
						{
							getServletContext().getRequestDispatcher("/Accueil").forward(request, response);
						}
				 }
			
				 else 
				 {
					 System.out.println("Connexion échouée");
					 getServletContext().getRequestDispatcher("/Connexion").forward(request, response);
				 }
				 
			} 
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
