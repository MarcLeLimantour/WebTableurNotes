package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connection.DAOAcces;


public class Classe {
	
	private String nom;
	private ArrayList<Stagiaire> stagiaires;
	private ArrayList<Matiere> matieres;
	private ArrayList<Examen> examens;
	private ArrayList<Integer> nbExamensMatiere;
	@SuppressWarnings("unused")
	private ArrayList<Float> moyennesMatieres;
	private float moyenne;
	private int id;
	public static int nbClasses = 0;
	
	/**
	 * constructeur de Classe
	 * 
	 * crée objet classe à partir de la table classe de la base données
	 * 
	 * @param id : identifiant de la classe
	 * 		  dao : connexion à la base de donnés
	 *   
	 * le constructeur crï¿½e 5 tableaux ArrayList
	 * 
	 * Les tableaux stagiaire, matiï¿½res et examens sont remplis depuis la base de donnï¿½es
	 * 
	 * le tableau nbExamensMatiere sert ï¿½ stocker les numeros d'examens et sera utilisï¿½ pour compter le nombre d'examens pour le calcul de moyenne
	 * le taleau moyennesMatieres sert ï¿½ stocker les moyennes de la classe par matiï¿½re
	 */
	public Classe(int id, DAOAcces dao){
		nbClasses++;
		this.stagiaires = new ArrayList<Stagiaire>();
		this.matieres = new ArrayList<Matiere>();
		this.examens = new ArrayList<Examen>();
		this.nbExamensMatiere = new ArrayList<Integer>();
		this.moyennesMatieres = new ArrayList<Float>();
		//this.moyenne = this.moyenneClasse();
		this.id=id;
		
		String sql = "select * from classe where idClasse ="+this.id+";";
		String sql1 = "select * from stagiaire where classeStagiaire ="+this.id+";";
		String sql2 = "select distinct idMatiere, nomMatiere, coefficient from matiere, examen where matiere.idMatiere = examen.matiereExamen and examen.classeExamen ="+this.id+";";
		String sql3 = "select * from examen where classeExamen ="+this.id+";";
		
		try {
			ResultSet rs = dao.getStatement().executeQuery(sql);
			
			while (rs.next()) {
				this.nom = (String) rs.getString("nomClasse");
			}
			
	    	ResultSet rs1 = dao.getStatement().executeQuery(sql1);

		    while (rs1.next()) {
		        this.ajouterStagiaire(new Stagiaire(rs1.getInt("idStagiaire"), // on remplit l'arraylist stagiaire
		        									rs1.getString("nomStagiaire"),
		        									rs1.getString("prenomStagiaire"),
		        									this, // c'est l'objet sur lequel on est en train de travailler
		        										  // ici on rentre cet objet classe dans stagiaire
		        									dao));
		      }
		    
		    ResultSet rs2 = dao.getStatement().executeQuery(sql2);
		    
		    while (rs2.next()) {
	    	      
		        this.ajouterMatiere(new Matiere(rs2.getInt("idMatiere"), // on remplit l'arraylist matiere
		        									rs2.getString("nomMatiere"),
		        									rs2.getFloat("coefficient")));
		       
		      }
		    
		    ResultSet rs3 = dao.getStatement().executeQuery(sql3);
		    
		    while (rs3.next()) {
	    	      
		        this.ajouterExamen(new Examen(rs3.getInt("idExamen"),this,rs3.getInt("matiereExamen"),dao));//on remplit l'arraylist examen
		       
		      }
		    
	    }
	    catch(SQLException e) {
			System.out.println("Problï¿½me SQL b!!");
			e.printStackTrace();
		}
		
		for(int i = 0; i < this.matieres.size(); i++) {
			this.nbExamensMatiere.add(nbExamensMatiere(matieres.get(i).getId()));
		}
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public ArrayList<Stagiaire> getStagiaires() {
		return stagiaires;
	}

	public void setStagiaires(ArrayList<Stagiaire> stagiaires) {
		this.stagiaires = stagiaires;
	}
	
	public ArrayList<Matiere> getMatieres() {
		return this.matieres;
	}

	public void setMatieres(ArrayList<Matiere> matieres) {
		this.matieres = matieres;
	}
	
	public ArrayList<Examen> getExamens() {
		return this.examens;
	}

	public void setExamens(ArrayList<Examen> examens) {
		this.examens = examens;
	}

	public void ajouterStagiaire(Stagiaire stagiaire){
		this.stagiaires.add(stagiaire);
	}
	
	public void ajouterMatiere(Matiere matiere){
		this.matieres.add(matiere);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<Integer> getNbExamensMatiere() {
		return nbExamensMatiere;
	}

	public void setNbExamensMatiere(ArrayList<Integer> nbExamensMatiere) {
		this.nbExamensMatiere = nbExamensMatiere;
	}

	public void ajouterExamen(Examen examen){
		this.examens.add(examen);
	}
	
	public void ajouterNbExamensMatiere(Integer nbExam){
		this.nbExamensMatiere.add(nbExam);
	}
	
	/**
	 * mï¿½thode permettant de rï¿½cupï¿½rer le nombre d'examens par matiï¿½re
	 * @param idMatiere : identifiant matiï¿½re
	 *   
	 *
	 */
	public int nbExamensMatiere(int idMatiere){
		int nbExamens = 0;
		for(int i = 0; i < this.examens.size(); i++) {
			if(this.examens.get(i).getIdMatiere() == idMatiere) {
				nbExamens++;
			}
		}
		return nbExamens;
	}
	
	public float moyenneMatiere(int idMatiere){
		float moyenne = 0;
		int j = 0;
		for(int i = 0; i < this.examens.size(); i++) {
			if(this.examens.get(i).getIdMatiere() == idMatiere) {
				moyenne += this.examens.get(i).getMoyenne();
				j++;
			}
		}
		moyenne = moyenne / j;
		moyenne = Math.round(moyenne * 100);
		return moyenne / 100;
	}
	
	public float moyenneClasse(){
		float sommeCoeff = 0;
		float sommeM = 0;
		int nbExamen = 0;
		this.moyenne = 0;
		
		for(int i=0; i<this.matieres.size(); i++){
			for(int j=0; j<this.examens.size(); j++){
				if(this.examens.get(j).getIdMatiere() == this.matieres.get(i).getId()) {
					sommeM += this.examens.get(j).getMoyenne();	
					nbExamen++;
				}
			}
			sommeM = sommeM / nbExamen;
			this.moyenne += sommeM * this.matieres.get(i).getCoeff();
			sommeCoeff += this.matieres.get(i).getCoeff();
			sommeM = 0;
			nbExamen = 0;
		
		}
		this.moyenne = this.moyenne / sommeCoeff;
		this.moyenne = Math.round(this.moyenne * 100);
		return this.moyenne / 100;
	}
	
}
