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

import connection.DAOAcces;
import model.Classe;
import model.Examen;

/**
 * Servlet implementation class ControleurFinal
 */
@WebServlet("/ControleurFinal")
public class ControleurFinal extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControleurFinal() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession h = request.getSession();
		Examen exa = (Examen) h.getAttribute("Examen");
		Classe cla = (Classe) h.getAttribute("Cl");
		
		
		DAOAcces dao = new DAOAcces("com.mysql.jdbc.Driver", "tableurnotes", "root", "");
		
		
		String sqlExam = "Select * from examen;";
		ResultSet rsExam;
		try {
			rsExam = dao.getStatement().executeQuery(sqlExam);
			
			// rs Exam
			
			rsExam.moveToInsertRow();
			
			rsExam.updateInt("idExamen", exa.getId());
			rsExam.updateString("dateExamen", exa.getDate());
			rsExam.updateInt("classeExamen", cla.getId());
			rsExam.updateInt("matiereExamen", exa.getIdMatiere());
			
			rsExam.insertRow();
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Double note;

		for(int i = 0;i<cla.getStagiaires().size();i++) {			

			// Requete SQL
			String sqlNote = "Select * from note;";

			try {
				ResultSet rsNote = dao.getStatement().executeQuery(sqlNote);

			// rs Note
				
			rsNote.moveToInsertRow();
			rsNote.updateInt("idStagiaire", cla.getStagiaires().get(i).getId());
			rsNote.updateInt("idExamen", exa.getId());

			
		if ((request.getParameter(""+i+"") == "")) {
				
			note = null;
			exa.ajouterNote(note);
			rsNote.insertRow();
			
		} 
		else 
		{
			
			note = Double.parseDouble(request.getParameter(""+i+""));
			System.out.println(note);
			exa.ajouterNote(note);
			rsNote.updateDouble("valeurNote", note);
			rsNote.insertRow();
		}

			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
					}
			
			dao.closeConnection();
					
			getServletContext().getRequestDispatcher("/Accueil").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
