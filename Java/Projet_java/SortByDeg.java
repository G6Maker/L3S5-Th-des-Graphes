package Projet_java;

import java.util.*;
/**
 * Décrivez votre classe sortByDeg ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class SortByDeg implements Comparator<Sommet>
{
    // Used for sorting in ascending order of
    // degre number
    public int compare(Sommet a, Sommet b)
    {
        int nba = a instanceof Page?0:((Utilisateur)a).nbVoisin();
        
        int nbb = b instanceof Page?0:((Utilisateur)b).nbVoisin();
        return  nbb - nba;
    }
}
