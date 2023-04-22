package Projet_java;

import java.util.*;
/**
 * Décrivez votre classe abstraite Sommet ici.
 *
 * @author  (votre nom)
 * @version (un numéro de version ou une date)
 */
public abstract class Sommet
{
    //attributs
    private String nom;
    private List follower;
   
    //constructeur
    public Sommet(String nom){
       if (nom == null){
            throw new AssertionError("nice try, nom == null");
       }
       this.nom = nom;
       this.follower = new ArrayList();
    }
   
    //requete
    //NB utilisateur qui le suivent.
    public int nbFollower(){
        return getFollower().size();
    }
    //list follower
    public List getFollower(){
        return follower;
    }
    //ajout follower
    public void addFollower(Utilisateur s){
        if(!getFollower().contains(s)){
            getFollower().add(s);
        }
    }
    //retrait follower
    public void removeFollower(Utilisateur s){
        getFollower().remove(s);
    }
    //obtenir le nom du sommet
    public String getNom(){
        return nom;
    }
}
