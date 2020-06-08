package view;

import java.sql.*;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import connection.DAOAcces;
import model.Classe;
import model.Stagiaire;

/**
 * Servlet implementation class AjoutExam
 */
@WebServlet("/AjoutExam")
public class AjoutExam extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjoutExam() {
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
		DAOAcces dao = new DAOAcces("com.mysql.jdbc.Driver", "tableurnotes", "root", "");
		
		// Requete SQL
		String sql = "select * from matiere";
			 // ResultSet recuperant un statement initialiser par la connexion dao 
			 //	executeQuery effectue une requete Sql sur ce statement
		try {
		ResultSet rs = dao.getStatement().executeQuery(sql);

		// Recuperation de l'idclasse envoier par Accueil en POST
		out.println("<html><head>"
				+ "<link rel=\"stylesheet\" href=\"https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css\">" + 
				"  <script src=\"https://code.jquery.com/jquery-1.12.4.js\"></script>" + 
				"  <script src=\"https://code.jquery.com/ui/1.12.1/jquery-ui.js\"></script>" + 
				"  <script>" + 
				"     $(function () {" + 
				"     $(\"#datepicker\").datepicker();"+ 
				"	  $.datepicker.setDefaults({ dateFormat: 'yy-mm-dd' });" + 
				"  });" + 
				"  </script></head><body>");
		
		out.println("<body><h1 align=center>Tableau des notes de la classe de "+cla.getNom()+"</h1><br><br>");
		out.println("<table border=1 align=center><tr><th>Recap de :"+cla.getNom()+"</th>");
		out.println("<div id=\"form\" align=\"center\"><form align=\"center\" action=\"ControleurExamens\" method=\"post\">");
		out.println("Examen"
					+ "<label for=\"Classes\"><br>Matière</label><select name=\"Classes\" size=\"1\">");
	
	
		while(rs.next()) {
			//out.println(rs.getInt("idClasse")+" "+rs.getString("nomClasse"));
			
			// Envoie en POST de la valeur classe choisie
			out.print("<option selected value = "+rs.getInt("idMatiere")+">"+rs.getString("nomMatiere")); 
			out.print("</option>");   
		}
		out.print("</select></br>");
		

		out.print("<span style=\"color:red;\">Veuillez remplir la date</span>");
		out.print("<br> Date <input readonly type=\"date\" name=\"dt\" id=\"datepicker\">");
		
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
				
		for(Stagiaire sta: cla.getStagiaires()) {
			
			out.println("<tr><td>"+sta.getNom()+" "+sta.getPrenom()+"</td>");
				
					}

		
		out.println("<div align=\"center\" <br><br><input align=\"center\" type=\"submit\" value=\"Valider\"></div>");
		
		out.println("</form>");
		out.println("</body></html>");
		
	}
		
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request,response);
		
	}

}
