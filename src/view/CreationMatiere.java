package view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import connection.DAOAcces;

/*
 * Servlet implementation class CreationMatiere
 */
@WebServlet("/CreationMatiere")
public class CreationMatiere extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /*
     * @see HttpServlet#HttpServlet()
     */
    public CreationMatiere() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out =response.getWriter();

        //Ouvre la connexion
        DAOAcces dao = new DAOAcces("com.mysql.jdbc.Driver", "tableurnotes", "root", "");

		//Formulaire d'ajout de matière
        out.println("<html><head></head><body>");
        out.println("<div align=center><form action=\"ControleurAjtMatiere\" method=\"get\"><h1>Création de matière</h1><br><br><br>");
        out.println("Nom de la matière: <input type=\"text\" name=\"nmMat\">");

        out.print("<br><br><input type=\"submit\" value = \"Valider\"/></form></div>");
        out.print("</body></html>");
        
        //Ferme la connexion
        dao.closeConnection();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
//        PrintWriter out=response.getWriter();
//
//        DAOAcces dao = new DAOAcces("com.mysql.jdbc.Driver", "webtableurnotes", "root", "");
    }

}