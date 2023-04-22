package Projet_java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Graphe extends Observable{
    //Attributs
    private Map<Sommet,List> arc;
    private List sommets;
    //constructeur
    public Graphe(){
        sommets = new ArrayList();
        arc = new HashMap<Sommet, List>();
    }
    //requetes 
    //obtenir l’ensemble des sommets;
    public List getSommets(){
        return sommets;
    }
    //connaitre le nombre de sommets;
    public int nbSommets(){
        return getSommets().size();
    }
    //obtenir l’ensemble des arcs d'un sommets;
    public List arc(Sommet x){
        return arc.get(x);
    }
    //obtenir l’ensemble des arcs;
    public Map getAllArc(){
        return arc;
    }
    //connaitre s'il y a un arc entre x et y
    public boolean isArc(Sommet x, Sommet y){
       return arc(x).contains(y);
    }
    //connaitre le nombre d’arcs d'un sommets;
    public int nbArc(Sommet x){
        return arc(x) == null?0:arc(x).size();
    }
    //connaitre le nombre d’arcs total;
    public int nbAllArc(){
        int nb = 0;
        for(int i = 0; i< nbSommets(); i++){
            nb += nbArc((Sommet)getSommets().get(i));
        }
        return nb;
    }
    //connaitre l'ensemble des nom des sommets
    public List<String> nomSommets(){
        List noms = new ArrayList<String>();
        for(int i = 0; i< nbSommets(); i++){
            Sommet tmp = (Sommet)getSommets().get(i);
            noms.add(tmp.getNom());
        }
        return noms;
    }
    //obtenir l’ensemble des sommets trié par nom;
    public List<String> sortedNom(){
        List snom = nomSommets();
        Collections.sort(snom);
        return snom;
    }
    //ajouter un utilisateur 
    public void addUser(String nom, String prenom, int age){
        Sommet user = new Utilisateur(nom, prenom, age);
        getSommets().add(user);
        arc.put(user, new ArrayList());
    }
    //ajouter une page
    public void addPage(String nom){
        Sommet page = new Page(nom);
        getSommets().add(page);
    }
    //ajouter un arc;
    public void addFollow(Utilisateur user, Sommet followed){
        if((!getSommets().contains(user))){
            throw new AssertionError("user out of graph");
        }
        //ajout dans la map
        List arcfollow = arc(user); //vaut null parfois
        if (arcfollow == null){
            arcfollow = new ArrayList();
        }
        if(!arcfollow.contains(followed)){
            arcfollow.add(followed);
            arc.put(user, arcfollow);
        }
        //ajout dans Sommet
        user.addVoisin(followed);
        /*
        if (!getSommets().contains(followed)){
            followed.addFollower(user);
        }
        */
    }
    //Supprimer un arc;
    public void removeFollow(Utilisateur user, Sommet followed){
        if(!getSommets().contains(user)){
            return;
        }
        //retrait dans la map
        List arcfollow = arc(user);
        if(arcfollow != null) {
        	arcfollow.remove(followed);
        	arc.put(user, arcfollow);
        }
        //retrait dans les deux Sommets
        user.removeVoisin(followed);
        followed.removeFollower(user);
    }
    //ajouter un sommet (et les arcs qui y sont liés);
    public void addSommet(Sommet s){
        //verifier la non presence
    	if(sortedNom().contains(s.getNom())){
    		System.out.println("nom deja existant");
    		return;
    	}
        if(getSommets().contains(s)){
            throw new AssertionError("s");
        }
        sommets.add(s);
        //ajout voisin sortant de s s'il est Utilisateur
        if(s instanceof Utilisateur){
            Utilisateur u = (Utilisateur) s;
            for(int n = 0; n < u.nbVoisin(); n++){
                //System.out.println( n + ((Sommet)u.getVoisin().get(n)).getNom());
                addFollow(u, (Sommet)u.getVoisin().get(n));
            }
        }
        //ajout voisin entrant dans s
        for(int n = 0; n < s.nbFollower(); n++){
           if(getSommets().contains((Utilisateur)s.getFollower().get(n))){
                addFollow((Utilisateur)s.getFollower().get(n), s);
           }
        }
    }
    //supprimer un sommet (et les arcs qui y sont liés);
    public void removeSommet(Sommet s){
        //verifier la non presence
        if(!getSommets().contains(s)){
            return;
        }
        //remove voisin sortant de s s'il est Utilisateur
        if(s instanceof Utilisateur){
            Utilisateur u = (Utilisateur) s;
            for(int n = 0; n < u.nbVoisin(); n++){
                removeFollow(u, (Sommet)u.getVoisin().get(n));
            }
        }
        //ajout voisin entrant dans s
        for(int n = 0; n < s.nbFollower(); n++){
                removeFollow((Utilisateur)s.getFollower().get(n), s);
        }
        sommets.remove(s);
    }
    //obtenir les informations sur un sommet via son nom;
    public Sommet getInfo(String s){
        for(int n = 0; n < nbSommets(); n++){
            Sommet smt = (Sommet)getSommets().get(n);
            if(smt.getNom().compareTo(s) == 0){
                return smt;
            }
        }
        return null;
    }
    //List des comptes de type Utilisateur;
    public List getUtilisateur(){
        List users = new ArrayList();
        for(int n = 0; n < nbSommets(); n++){
           if((Sommet)getSommets().get(n) instanceof Utilisateur){
               users.add((Utilisateur)getSommets().get(n));
           }
        }
        return users;
    }
    //le nombre de comptes de type Utilisateur;
    public int nbUtilisateur(){
        return getUtilisateur().size();
    }
    //List des comptes de type Page;
    public List getPage(){
        List users = new ArrayList();
        for(int n = 0; n < nbSommets(); n++){
           if((Sommet)getSommets().get(n) instanceof Page){
               users.add((Page)getSommets().get(n));
           }
        }
        return users;
    }
    //le nombre de comptes de type Page;
    public int nbPage(){
        return getPage().size();
    }
    //Age moyen des utilisateurs;
    public float avgAge(){
        List users = getUtilisateur();
        float age = 0;
        for(int n = 0; n < nbUtilisateur(); n++){
            age += (float)((Utilisateur)users.get(n)).getAge();
        }
        return (age/nbUtilisateur());
    }
    //list des admins de toutes les pages
    public List getAllAdmin(){
        List page = getPage();
        List admin = new ArrayList();
        for(int n = 0; n < nbPage(); n++){
            admin.addAll(((Page)page.get(n)).getAdmin());
        }
        return admin;
    }
    //obtenir l’ensemble des sommets trié par degré sortant;
    public List sortedDegree(){
         List sdegree = getSommets();
         Collections.sort(sdegree, new SortByDeg());
         return sdegree;
    }
    //obtenir une list d'adjacence sous forme de texte
    public String listAdja(){
        String result = "";
        List sommets = getSommets();
        for(int n = 0; n < nbSommets(); n++){
            result = result + "<" + type((Sommet)sommets.get(n)) +
                ", " + ((Sommet)sommets.get(n)).getNom() + ">, ";
        }
        for(int n = 0; n < nbSommets(); n++){
            List arc = arc((Sommet)sommets.get(n));
            if(!(arc == null)){
                for(int i = 0; i < arc.size(); i++){
                    result = result + "(" + ((Sommet)sommets.get(n)).getNom() +
                        ", " + ((Sommet)arc.get(i)).getNom()+ "),";
                }
            }
        }
        return result;
    }
    //ecrit dans un fichier la list d'adjacences
    public boolean wListAdja(){
        try {

           String content = listAdja();
        
           File file = new File("graph.txt");
        
           // créer le fichier s'il n'existe pas
           if (!file.exists()) {
            file.createNewFile();
           }
        
           FileWriter fw = new FileWriter(file.getAbsoluteFile());
           BufferedWriter bw = new BufferedWriter(fw);
           bw.write(content);
           bw.close();
        
           System.out.println("Modification terminée!");
           return true;
        } catch (IOException e) {
           e.printStackTrace();
           return false;
        }
    }
    //converti un texte en Graph
    public void fromString(String s){
    	//ici on efface l'ancien graph. possibilité de ne pas le faire mais verifier si les arcs et sommets a ajouoter existe deja.
    	sommets = new ArrayList();
        arc = new HashMap<Sommet, List>();
        Pattern sommetPattern = Pattern.compile( "<(.+?), (.+?)>", Pattern.DOTALL );
        Pattern arcPattern = Pattern.compile( "\\((.+?), (.+?)\\)", Pattern.DOTALL );
        Matcher sommetMatcher = sommetPattern.matcher( s );
        List sommet = new ArrayList();
        while (sommetMatcher.find()) {
            if(sommetMatcher.group(1).compareTo("user") == 0){
                Utilisateur u = new Utilisateur(sommetMatcher.group(2));
                sommet.add(u);
            } 
            if(sommetMatcher.group(1).compareTo("page") == 0){
                Page v = new Page(sommetMatcher.group(2));
                sommet.add(v);
            }
        }
        Matcher arcMatcher = arcPattern.matcher( s );
        List allSommet = new ArrayList();
        allSommet.addAll(sommet);
        allSommet.addAll(getSommets());
        while (arcMatcher.find()) {
            Sommet i = getInfoFromList(arcMatcher.group(1), allSommet);
            Sommet i2 = getInfoFromList(arcMatcher.group(2), allSommet);
            if(i instanceof Utilisateur && !(i2 == null)){
                ((Utilisateur)i).addVoisin(i2);
                i2.addFollower((Utilisateur)i);
            }
        }
        for(int n = 0; n < sommet.size(); n++){
            //System.out.println(((Sommet)sommet.get(n)).getNom());
            addSommet((Sommet)sommet.get(n));
        }
    }
    //converti un fichier en graphe
    public void fromFile(String file){
        try{
          // Open the file that is the first 
          // command line parameter
          FileInputStream fstream = new FileInputStream(file);
          // Get the object of DataInputStream
          DataInputStream in = new DataInputStream(fstream);
          BufferedReader br = new BufferedReader(new InputStreamReader(in));
          String strLine;
          //Read File Line By Line
          while ((strLine = br.readLine()) != null)   {
              // Print the content on the console
              fromString(strLine);
          }
          //Close the input stream
          in.close();
            }catch (Exception e){//Catch exception if any
          System.err.println("Error: " + e.getMessage());
      }
    }
    //obtenir un sommet a partir d'une list
    public Sommet getInfoFromList(String s, List l){
        for(int n = 0; n < l.size(); n++){
            Sommet smt = (Sommet)l.get(n);
            if(smt.getNom().compareTo(s) == 0){
                return smt;
            }
        }
        return null;
    }
    //algo Pagerank
    public Map pageRank(){
        Map pr = new HashMap<Sommet, Float>();
        List v = getSommets();
        for(int n = 0; n < v.size(); n++){
            pr.put((Sommet)v.get(n), new Float(1));
        }
        int i = 0;
        while(i < 100){
            for(int n = 0; n < v.size(); n++){
                float pagerank = (float)(0.15/v.size());
                pagerank += calPR(((Sommet)v.get(n)).getFollower(), pr);
                pr.put((Sommet)v.get(n), new Float(pagerank));
                i++;
            }
        }
        return pr;
    }
    //Calcul de la somme du pagerank
    private float calPR(List follower, Map pr){
        float result = 0;
        for(int u = 0; u < follower.size(); u++){
            float c = (float)((Float) pr.get(follower.get(u))).floatValue();
            float d = (float)((Utilisateur)follower.get(u)).nbVoisin();
            result += (float)c/d;
        }
        return (float)0.85*result;
    }
    //obtenir le pagerank d'un sommet
    public float pageRank(Sommet s) {
    	Map m = pageRank();
        return ((Float)m.get(s)).floatValue();
    }
    
    //obtenir la distance minimal pour tout sommet
    public Map shortest(Sommet s){
        //graph doit pas etre vide !!!!!!!!!!!!!!!!!!!!!!!!!!
        Map dist = new HashMap<Sommet, Integer>();
        List v = getSommets();
        for(int n = 0; n < v.size(); n++){
            Sommet current = (Sommet)v.get(n);
            if(current != s){
                dist.put(current, new Integer(10000000));
            } else {
                dist.put(current, new Integer(0));
            }
        }
        List p = new ArrayList(v);
        while(p.size() > 0){
            Sommet u = plusCourt(dist, p);
            p.remove(u);
            List f = u.getFollower();
            for(int i = 0; i < f.size(); i++){
                Sommet vb = (Sommet)f.get(i);
                int alt = ((Integer)dist.get(u)).intValue() + 1;
                if(alt <= ((Integer)dist.get(vb)).intValue()){
                    dist.put(vb, new Integer(alt));
                }
            }
        }
        return dist;
    }
    //parti de lalgo de recherche du sommet
    private Sommet plusCourt(Map dist, List p){
        Sommet min = (Sommet)p.get(0);
        for(int n = 0; n < p.size(); n++){
            boolean c = ((Integer)dist.get(min)).intValue() < ((Integer)dist.get((Sommet)p.get(n))).intValue();
            min = c?min:((Sommet)p.get(n));
        }
        return min;
    }
    //retourne la plus courte distance entre un sommet u et v
    public int shortest(Sommet u, Sommet v){
        Map s = shortest(v);
        return ((Integer)s.get(u)).intValue();
    }
    //retourne le type de sommet
    public String type(Sommet s){
        return (s instanceof Page)?"page":"user";
    }
}
