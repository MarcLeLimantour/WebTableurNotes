package view;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import connection.DAOAcces;
import model.Classe;
import model.Matiere;
import model.Stagiaire;

/**
 * Servlet implementation class Index
 */
@WebServlet("/Tableau")
public class Tableau extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor. 
	 */
	public Tableau() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		PrintWriter out=response.getWriter();

		out.println("Tableau des notes de la classe de"+request.getParameter("idClasse"));

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		//	this.getServletContext().getRequestDispatcher( "/ChoixAction" ).forward( request, response );
		PrintWriter out=response.getWriter();

		DAOAcces dao = new DAOAcces("com.mysql.jdbc.Driver", "tableurnotes", "root", "");

		int idClasse = Integer.parseInt(request.getParameter("Classes")); //la request: ce qui arrive dans la servlet

		HttpSession role = request.getSession();

		String inforole = (String) role.getAttribute("role");

		Classe cl1 = new Classe(idClasse, dao);
		out.println("<h1 align=center>Tableaux des notes de la classe de "+cl1.getNom()+"</h1><br><br>");
		out.println("<table border=1 align=center><tr><th>"+cl1.getNom()+"</th>");
		for(Matiere mat: cl1.getMatieres()) {
			out.println("<td><table border=1><tr>");
			out.println("<th align=center colspan="+cl1.nbExamensMatiere(mat.getId())+">"+mat.getNom()+"</th></tr><tr><td align=center colspan= "+cl1.nbExamensMatiere(mat.getId())+">Coeff : " + mat.getCoeff() + "</td></tr><tr>");
			int j = 1;
			for(int i = 0; i < cl1.nbExamensMatiere(mat.getId());i++) {
				out.println("<td align=center>Exam"+j+"</td>");
				j++;
			}
			out.println("</tr></table></td>");
		}
		out.println("<th>Moyennes</th>");
		out.println("</tr>");
		
		for(Stagiaire sta: cl1.getStagiaires()) {
			
			out.println("<tr><td>"+sta.getNom()+" "+sta.getPrenom()+"</td>");
			
			for(int j = 0; j < cl1.getMatieres().size(); j++) {
				out.print("<td align=center height=100%><table border=1 width=100% height=100%><tr>");
				for(int i = 0; i < cl1.getExamens().size(); i++) {
					if (j < sta.getExamens().size() && j < cl1.getExamens().size()) {
						if(sta.getExamens().get(i) == cl1.getExamens().get(i).getId() && cl1.getMatieres().get(j).getId() == cl1.getExamens().get(i).getMatiere()){
							if (sta.getNotes().get(i) == null){
								out.println("<td align=center height=100% width="+(100/cl1.nbExamensMatiere(cl1.getMatieres().get(j).getId()))+"%>Abs.</td>");
							}
							else {
								out.println("<td align=center width="+(100/cl1.nbExamensMatiere(cl1.getMatieres().get(j).getId()))+"%>"+sta.getNotes().get(i)+"</td>");	
							}
						}					
				
					}
				}
				out.print("</tr></table></td>");
			}
			out.println("<td align=center>"+sta.getMoyenne()+"</td></tr>");
		}
				
		out.println("<tr><td>Moyennes</td>");

		for(int j = 0; j < cl1.getMatieres().size(); j++) {
			out.print("<td align=center height=100%><table border=1 width=100% height=100%><tr>");
			for(int i = 0; i < cl1.getExamens().size(); i++) {
				if (j < cl1.getExamens().size()) {
					if(cl1.getMatieres().get(j).getId() == cl1.getExamens().get(i).getIdMatiere()) {
					
						out.println("<td align=center width="+(100/cl1.nbExamensMatiere(cl1.getMatieres().get(j).getId()))+"%>"+cl1.getExamens().get(i).getMoyenne()+"</td>");
					}
				}
			}
			out.print("</tr><tr><td align=center colspan='"+cl1.nbExamensMatiere(cl1.getMatieres().get(j).getId())+"'>"+cl1.moyenneMatiere(cl1.getMatieres().get(j).getId())+"</td></tr></table></td>");
		}
		
		out.println("<td align=center>"+cl1.moyenneClasse()+"</td></tr></table><br>");
		
		HttpSession h = request.getSession();
		h.setAttribute("Cl", cl1); // nous chargeons l'objet entier classe "cl1" dans la session, avec l'ï¿½tiquette "Cl"
		
		//ajouter dans le dernier td la moyenne générale de la classe
		// CODE HUGO HUGO ET JO
		if(inforole.equals("administrateur") || inforole.equals("professeur"))
		{
					out.println("<br>"
					+ "<div id='buttons' align='center'>"
					+ "<form action=\"AjoutExam\" name= \"boutonajoutEX\" value = \"ajoutexamen\" method=\"get\">"
					+ "<input type = \"submit\" name=\"test\" value=\"Creer un Examen\"> </form>");
		}
		// CODE DE HUGO HUGO ET JO	
		if(inforole.equals("administrateur"))
		{		
			out.println("<form action=\"FormStagiaire\" name= \"boutonajoutST\" value = \"ajoutclasse\" method=\"get\">"
				+ "<input type = \"submit\" name=\"test\" value=\"Creer un Stagiaire\"> </form>"
				+ "</div>");
		}
		
			//Ajout dans la vue tableau du selecteur de stagiaires de la classe selectionnÃ©e plus tÃ´t
			out.println("<html><head> <link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">" + 
					"</head><body>"+
						"<div align=center><form align=center action=\"TableauStagiaire\"  method=get >"
						+ "<h2>Choisissez un stagiaire parmi les suivants:</h2><br>"+
						"<select name= \"idStagiaire\" size=\"1\">");
			
			for(Stagiaire sta: cl1.getStagiaires()) {
				out.print("<option selected value = "+sta.getId()+">"+sta.getNom()+" "+sta.getPrenom()); 
				out.print("</option><br><br>");
				
				//h.setAttribute("idStagiaire",sta.getId());
			}
			
			out.print("</select><p /><br><br><input type=\"submit\"  value = \"Valider\" /> <p /></form></div>");  
			out.print("</body></html>");
			
		dao.closeConnection();

	}

		
}


