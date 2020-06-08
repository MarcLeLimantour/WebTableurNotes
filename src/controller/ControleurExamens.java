package controller;

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

/**
 * Servlet implementation class AjoutNote
 */
@WebServlet("/ControleurExamens")
public class ControleurExamens extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControleurExamens() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession h = request.getSession();
		Classe cla = (Classe) h.getAttribute("Cl");	

		
		PrintWriter out = response.getWriter();
	
		int idMat = Integer.parseInt(request.getParameter("Classes"));
		String date = request.getParameter("dt");
		
		if(!date.isEmpty()) {
		DAOAcces dao = new DAOAcces("com.mysql.jdbc.Driver", "tableurnotes", "root", "");
		
		String sql = "select MAX(idExamen) as max from examen";
		try {
			ResultSet rs = dao.getStatement().executeQuery(sql);
			int lastId = 0;
			while(rs.next()) {
				lastId = rs.getInt("max");
			}
			lastId=lastId+1;
			
			Examen exa = new Examen(lastId,cla,idMat,date,dao);
			
//			rs.moveToInsertRow();
//			
//			rs.updateInt("idExamen", lastId);
//			rs.updateString("dateExamen", date);
//			rs.updateInt("classeExamen", cla.getId());
//			rs.updateInt("matiereExamen", idMat);
//			
//			rs.insertRow();
			
			
			
			dao.closeConnection();

			h.setAttribute("Examen", exa);
			out.println("Matiere de l'examen :"+exa.getMatiere()+" ----- Date de l'examen : "+exa.getDate());
			
			getServletContext().getRequestDispatcher("/AjoutNote").forward(request,response);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block		
			e.printStackTrace();
	}
	} 
		
	else 
		
	{
		/*HttpSession alerte = request.getSession();
		String message;
		message ="Test";
		
		alerte.setAttribute("message", message);
		*/
		
		getServletContext().getRequestDispatcher("/AjoutExam").forward(request, response);
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
