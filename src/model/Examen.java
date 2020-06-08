package model;

import java.sql.*;
import java.util.ArrayList;

import connection.DAOAcces;

public class Examen {

	private int id;
	private Classe classe;
	private ArrayList<Double> notes;
	private String date;
	private int idMatiere;
	private float moyenne;
	public static int nbExamens = 0;
	
	public Examen(int id, Classe classe, int idMatiere, DAOAcces dao){
		this.id = id;
		this.classe = classe;
		this.notes = new ArrayList<Double>();
		this.idMatiere = idMatiere;
		
		nbExamens++;
		
		String sql = "select * from note where idExamen ="+this.id+";";
		
		try {
			Statement st = (Statement) dao.getConn().createStatement();
	    	ResultSet rs = st.executeQuery(sql);
	    	
		    while (rs.next()) {
		    	if (rs.getObject("valeurNote") == null) {
		    		this.ajouterNote(null);
		    	}
		    	else{
		    		this.ajouterNote(rs.getDouble("valeurNote"));
		    	}
		    	
		    }
		}
	    catch(SQLException e) {
			System.out.println("Problème SQL !!");
			e.printStackTrace();
		}
	}

	
	
	public Examen(int id, Classe classe, int idMatiere,String date, DAOAcces dao){
		this.id = id;
		this.classe = classe;
		this.date = date;
		this.notes = new ArrayList<Double>();
		this.idMatiere = idMatiere;
		
		nbExamens++;
		
		String sql = "select * from note where idExamen ="+this.id+";";
		
		try {
			Statement st = (Statement) dao.getConn().createStatement();
	    	ResultSet rs = st.executeQuery(sql);
	    	
		    while (rs.next()) {
		    	if (rs.getObject("valeurNote") == null) {
		    		this.ajouterNote(null);
		    	}
		    	else{
		    		this.ajouterNote(rs.getDouble("valeurNote"));
		    	}
		    	
		    }
		}
	    catch(SQLException e) {
			System.out.println("Probleme SQL !!");
			e.printStackTrace();
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}
	
	public int getIdMatiere() {
		return idMatiere;
	}

	public void setIdMatiere(int idMatiere) {
		this.idMatiere = idMatiere;
	}

	public Classe getClasse() {
		return classe;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}

	public ArrayList<Double> getNotes() {
		return notes;
	}

	public void setNotes(ArrayList<Double> notes) {
		this.notes = notes;
	}
	
	public int getMatiere() {
		return this.idMatiere;
	}

	public void setMatiere(int matiere) {
		this.idMatiere = matiere;
	}


	
	public void ajouterNote(Double note){
		this.notes.add(note);
	}
	
	public float getMoyenne(){
		this.moyenne = 0;
		int j = 0;
		for(int i=0; i<this.notes.size(); i++){
			if (this.notes.get(i) != null) {
			this.moyenne += this.notes.get(i).floatValue();
			j++;
			}
		}
		this.moyenne = this.moyenne / j;
		this.moyenne = Math.round(this.moyenne * 100);
		return this.moyenne / 100;
	}
}
