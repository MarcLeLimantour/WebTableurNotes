package model;

import java.util.ArrayList;

public class Matiere {

	private int id;
	private String nom;
	private ArrayList<Examen> examens;
	private float moyenne;
	private float coeff;
	public static int nbMatieres = 0;
	

	/**
	 * constructeur de matiere
	 * 
	 * @param 	id : identifiant de la matière
	 * 			nom : nom de la base de données
	 * 
	 **/
	
	
	public Matiere(int id, String nom, float coeff){
		nbMatieres++;
		this.id = id;
		this.nom = nom;
		this.examens = new ArrayList<Examen>();
		this.coeff = coeff;
	}
	
	public int getId() {
		return this.id;
	}
	
		
	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public float getCoeff() {
		return this.coeff;
	}

	public void setCoeff(int coeff) {
		this.coeff = coeff;
	}
	
	/**
	 * methode pour obtenir un tableau d'examen
	 *
	 * 
	 **/
	public ArrayList<Examen> getExamens() {
		return examens;
	}

	public void setExamens(ArrayList<Examen> examens) {
		this.examens = examens;
	}
	
	/**
	 * methode pour récupérer la moyenne de la matiere
	 * 
	 * la moyenne est obtenue à partir d'examen
	 * 
	 **/
	public float getMoyenne() {
		this.moyenne = 0;
		int j = 0;
		for(int i=0; i<this.examens.size(); i++){
				this.moyenne += this.examens.get(i).getMoyenne();
				j++;
		}
		this.moyenne = this.moyenne / j;
		this.moyenne = Math.round(this.moyenne * 100);
		return this.moyenne / 100;
	}

	public void setMoyenne(float moyenne) {
		this.moyenne = moyenne;
	}

	public void ajouterExamen(Examen examen){
		this.examens.add(examen);
	}
	
	
}
