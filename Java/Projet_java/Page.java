package Projet_java;

import java.util.*;
/**
 * Décrivez votre classe Page ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class Page extends Sommet{
    //attribut
    
    private List admin;
    
    //constructeur
    public Page(String nom){
        super(nom);
        this.admin = new ArrayList();
    }
    //requete
    public int nbFollower(){
        return getFollower().size();
    }
    //methode
    public void addAdmin(Utilisateur user){
        if(user == null){
            throw new AssertionError("Nice try, user");
        }
        admin.add(user);
    }
    //Connaitre tous les comptes Utilisateur qui sont des administrateurs dePage;
    public List getAdmin(){
        return admin;
    }
}
