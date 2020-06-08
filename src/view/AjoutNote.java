package view;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import connection.DAOAcces;
import model.Classe;
import model.Examen;
import model.Stagiaire;

/**
 * Servlet implementation class AjoutNote
 */
@WebServlet("/AjoutNote")
public class AjoutNote extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjoutNote() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
PrintWriter out=response.getWriter();
		
		HttpSession h = request.getSession();
		Classe cla = (Classe) h.getAttribute("Cl");
		Examen exa = (Examen) h.getAttribute("Examen");
		int i = 0;
		
		DAOAcces dao = new DAOAcces("com.mysql.jdbc.Driver", "tableurnotes", "root", "");
		
		// Requete SQL
		String sql = "select * from matiere where idMatiere = "+exa.getMatiere()+";";
			 // ResultSet recuperant un statement initialiser par la connexion dao 
			 //	executeQuery effectue une requete Sql sur ce statement
		try {
		ResultSet rs = dao.getStatement().executeQuery(sql);

		// Recuperation de l'idclasse envoier par Accueil en POST
		out.println("<html><head></head><body>");
		out.println("<h1 align=center>Ajout de notes pour la classe de "+cla.getNom()+"</h1><br><br>");
		out.println("<table border=1 align=center><tr><th colspan=\"2\">Recap de :"+cla.getNom()+"</th>");

		while(rs.next()) {

			out.println("Matière : "+rs.getString("nomMatiere")
						+"</br> Date : "+exa.getDate());
			
		}
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
		out.println("<div id=\"form\" align=\"center\"><form align=\"center\" action=\"ControleurFinal\" method=\"post\">");
				
		for(Stagiaire sta: cla.getStagiaires()) {
			
			out.println("<tr><td>N°"+i+" "+sta.getNom()+" "+sta.getPrenom()+"</td><td>Note : <input size=\"2\" name=\""+i+"\"></td>");
			i++;
					}
		
		out.println("<input type=\"submit\" value=\"Valider\"></div>");
		out.println("</form>");
		out.println("</body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
