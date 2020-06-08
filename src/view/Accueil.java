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

/**
 * Servlet implementation class Accueil
 */
@WebServlet("/Accueil")
public class Accueil extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Accueil() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    
    //Connexion ра la DB et affichage du formulaire
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    HttpSession role = request.getSession();
	    // CODE DE HUGO ET JO 
	    String inforole = (String) role.getAttribute("role");
		
		PrintWriter out=response.getWriter();
		
		DAOAcces dao = new DAOAcces("com.mysql.jdbc.Driver", "webtableurnotes", "root", "");
		String sql = "select * from classe";
		
		
		try {
			 
			ResultSet rs = dao.getStatement().executeQuery(sql);
			
			out.println("<html><head> <link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">" + 
					"</head><body>"+
						"<div align=center><form align=center action=\"Tableau\" method=post >"
						+ "<h1>NOTES DE CLASSES</h1><br><br><h2>Choisissez une classe parmi les suivantes:</h2><br>"+
						"<select name=\"Classes\" size=\"1\">");
			
			while(rs.next()) 
			{
				//out.println(rs.getInt("idClasse")+" "+rs.getString("nomClasse"));
				out.print("<option selected value = "+rs.getInt("idClasse")+">"+rs.getString("nomClasse")); 
				out.print("</option><br><br>");   
			}
			
			out.print("</select><p /><br><br><input type=\"submit\" value = \"Valider\" /> <p /></form></div>");
			
			// CONDITION DE HUGO ET JO
			if(inforole.equals("administrateur"))
			{
			  
			out.print("<form action=\"ControleurAccueil\" name= \"boutonajout\" value = \"ajoutclasse\" method=\"get\"> <input type = \"submit\" name=\"test\" value=\"Creer une classe\"> </form>");
			out.print("<form action=\"ControleurAccueil?direction=2 name= \"boutonajout\" value = \"ajoutmatiere\" method=\"get\"> <input type = \"submit\" name = \"test\" value=\"Creer une matiere\"> </form>");	
			}
			
			out.print("</body></html>");
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
