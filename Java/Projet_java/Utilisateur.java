package Projet_java;

import java.util.*;

public class Utilisateur extends Sommet{
    //attribut
    private String prenom;
    private int age;
    private List voisin;

    
    //constructeur
    public Utilisateur(String nom, String prenom, int age){
        super(nom);
        if (prenom == null || age < 12){
            throw new AssertionError("nice try, kid, prenom");
        }
        this.prenom = prenom;
        this.voisin = new ArrayList();
        this.age = age;
    }
    //constructeur tmp
    public Utilisateur(String nom){
        super(nom);
        this.prenom = null;
        this.voisin = new ArrayList();
        this.age = 0;
    }
    //requete
    //List d'utilisateur qu'il suit.
    public List getVoisin(){
       return voisin;
    }
    //ajout voisin sortant
    public void addVoisin(Sommet s){
       if(!getVoisin().contains(s)){
           getVoisin().add(s);
       }
    }
    //retrait voisin sortant
    public void removeVoisin(Sommet s){
       getVoisin().remove(s);
    }
    //NB utilisateur qu'il suit.
    public int nbVoisin(){
       return getVoisin().size();
    }
    //prenom du sommet
    public String getPrenom(){
        return prenom;
    }
    //age du sommet 
    public int getAge(){
        return age;
    }
    //definir l'age
    public void setAge(int n){
        if(n<13 || n>150){
            System.out.println("Age incorrect, age non modifier");
            return;
        }
        age = n;
    }
}
