package model;

import java.sql.*;

import java.util.ArrayList;

import com.mysql.jdbc.Statement;

import connection.DAOAcces;

public class Stagiaire {

	private String nom;
	private String prenom;
	private int id;
	private Classe classe;
	private ArrayList<Double> notes;
	private ArrayList<Integer> examens;

	public static int nbStagiaires = 0;
	
	/**
	 * constructeur de Stagiaire
	 * 
	 * @param 	id: identifiant du stagiaire 
	 * 			nom : nom du stagiaire
	 * 			prenom: prenom du stagiaire
	 * 			classe: classe du stagiaire
	 * 			dao: connexion à la base de données
	 * 
	 **/
	
	public Stagiaire(int id, String nom, String prenom, Classe classe, DAOAcces dao){
		this.nom = nom;
		this.prenom = prenom;
		this.id = id; 
		this.classe = classe;
		this.notes = new ArrayList<Double>();
		this.examens = new ArrayList<Integer>();
		
		String sql = "select * from note where idStagiaire ="+this.id+";";
		
		try {
			Statement st = (Statement) dao.getConn().createStatement(); //on doit ouvrir un 2eme statement pour récupérer la note:
																		//il doit travailler en même temps qu'un statement déjà ouvert dans classe
	    	ResultSet rs = st.executeQuery(sql);
	    	
		    while (rs.next()) {
		    	if (rs.getObject("valeurNote") == null) {
		    		this.ajouterNote(null);
		    	}
		    	else{
		    		this.ajouterNote(rs.getDouble("valeurNote"));
		    	}
		        this.ajouterExamen(rs.getInt("idExamen"));
		    }
		}
	    catch(SQLException e) {
			System.out.println("Problème SQL a!!");
			e.printStackTrace();
		}
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return this.prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	public Classe getClasse() {
		return this.classe;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}

	public ArrayList<Double> getNotes() {
		return this.notes;
	}

	public void ajouterNote(Double note){
		this.notes.add(note);
	}
	
	public void ajouterExamen(int examen){
		this.examens.add(examen);
	}
	
	/**
	 * methode qui crée un tableau d'examen
	 * 
	 * 
	 **/
	public ArrayList<Integer> getExamens() {
		return examens;
	}

	public void setExamens(ArrayList<Integer> examens) {
		this.examens = examens;
	}

	public void setNotes(ArrayList<Double> notes) {
		this.notes = notes;
	}

	/**
	 * méthode qui crée une moyenne de notes à partir d'un tableau de notes
	 * 
	 * 
	 **/
	
	/*public float getMoyenne(){
		float moyenne = 0;
		int j = 0;
		for(int i=0; i<this.notes.size(); i++){
			if (this.notes.get(i) != null) {
				moyenne += this.notes.get(i).floatValue();
				j++;
			}
		}
		moyenne = Math.round((moyenne / j)*100);
		return moyenne/100;
	}*/
	
	public float getMoyenne(){
	 
		float sommeCoeff = 0;
		float sommeM = 0;
		int nbExamen = 0;
		float moyenne = 0;

		for(int i=0; i<this.classe.getMatieres().size(); i++){
			for(int j=0; j<this.classe.getExamens().size(); j++){
				if(this.classe.getMatieres().get(i).getId() == this.classe.getExamens().get(j).getIdMatiere()) {
					for(int k=0; k<this.examens.size(); k++){
						if(this.classe.getExamens().get(j).getId() == this.examens.get(k)) {
							sommeM += this.notes.get(k);	
							nbExamen++;
						}
					}
						sommeM = sommeM / nbExamen;
						moyenne += sommeM * this.classe.getMatieres().get(i).getCoeff();
						sommeCoeff += this.classe.getMatieres().get(i).getCoeff();
						sommeM = 0;
						nbExamen = 0;
				}
			}
		}
		
		moyenne = moyenne / sommeCoeff;
		moyenne = Math.round(moyenne * 100);
		return moyenne / 100;
	}
}
