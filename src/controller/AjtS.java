package controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Statement;

import connection.DAOAcces;
import model.Classe;
import model.Examen;
import model.Stagiaire;

/**
 * Servlet implementation class AjtS
 */
@WebServlet("/AjtS")
public class AjtS extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjtS() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		DAOAcces dao = new DAOAcces("com.mysql.jdbc.Driver", "tableurnotes", "root", "");
		HttpSession cl = request.getSession();
		Classe cl1 = (Classe) cl.getAttribute("Cl");
		String nom = request.getParameter("newN");
		String prenom = request.getParameter("newP");
		
		/*ArrayList<Stagiaire> stagiaire = cl1.getStagiaires();*/
		int numSta = 0;
		int cpt = 0;
		String sql = "SELECT MAX(idStagiaire) FROM `stagiaire`;";
		String sqlInsert = "INSERT INTO `stagiaire`(`idStagiaire`, `nomStagiaire`, `prenomStagiaire`, `classeStagiaire`) "
				+ "VALUES (" + cpt + ",'" + nom + "','" + prenom + "'," + cl1.getId() + ")";
		
		try {
			//ResultSet rs1 = dao.getStatement().executeQuery(sql);
			Statement addStag = (Statement) dao.getConn().createStatement();
			addStag.executeUpdate(sqlInsert);
			ResultSet rs = dao.getStatement().executeQuery(sql);
			while(rs.next()) { 
				cpt = rs.getInt(1);
			}
			cl1.ajouterStagiaire(new Stagiaire(cpt,nom,prenom,cl1,dao));
			
			numSta = cpt;
			
			String sqlUser = "INSERT INTO `utilisateur`(`nom`, `password`, `fonction`, `numStagiaire`)"
					+ "VALUES ('" + nom + "','" + prenom +"','eleve', "+ numSta +")";
			
			addStag.executeUpdate(sqlUser);
			
			for(Examen exa: cl1.getExamens()) {
						cl1.getStagiaires().get(cl1.getStagiaires().size()-1).getNotes().add(null);
						String sqlAjoutNote = "INSERT INTO `note`(`idStagiaire`, `idExamen`, `valeurNote`) "
								+ "VALUES (" + cpt + "," + exa.getId() + ",null)";
						addStag.executeUpdate(sqlAjoutNote);
			}
		}catch(SQLException e){
			System.out.println("Problème SQL Recherche id!!");
			e.printStackTrace();
		}
		request.getRequestDispatcher("Accueil").forward(request, response);
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
