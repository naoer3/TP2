package tp2.mp.dj.tp2;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Classe qui ajoute un joueur au classement dans les SharedPreferences
 */
public class TraitementClassement {

    SharedPreferences prefs = null;

    public TraitementClassement(SharedPreferences p){
        prefs = p;
    }

    public void run(Joueur j, int mode){
        // On definit le mode de jeu
        String nom_prefs = "";
        switch(mode){
            case 0:
                nom_prefs = "CLASSEMENT_ZEN";
                break;
            case 1:
                nom_prefs = "CLASSEMENT_NORMAL";
                break;
            case 2:
                nom_prefs = "CLASSEMENT_CLM";
                break;
        }
        // On recupere le classement a partir des SharedPreferences
        String classement_modif = prefs.getString(nom_prefs, null);

        List<Joueur> list_classement = new ArrayList<>();

        // On transforme la chaine de caractere en JSON
        // Et chaque joueur JSON en un élément Joueur qu'on met dans une liste
        if(classement_modif != null) {
            try {
                JSONArray json_joueurs = new JSONArray(classement_modif);
                for (int i =0; i<json_joueurs.length(); i++){
                    Joueur njoueur = new Joueur();
                    njoueur.fromJson(json_joueurs.getJSONObject(i));
                    list_classement.add(njoueur);
                }
            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }
        // On ajoute le joueur qui vient de finir sa partie
        list_classement.add(j);

        //On trie les joueurs en fonction de leur classement
        Collections.sort(list_classement,new Comparator<Joueur>() {
            @Override
            public int compare(Joueur j1, Joueur j2) {
                return Integer.valueOf(j1.score).compareTo(Integer.valueOf(j2.score));
            }
        });

        //On garde les 5 meilleurs
        if(list_classement.size()>5)
            list_classement = list_classement.subList(0,5);

        //On retransforme les Joueur en JSON
        JSONArray json_joueurs_nouveaux = new JSONArray();
        for (Joueur joueur :list_classement) {
            json_joueurs_nouveaux.put(joueur.toJson());
        }

        //On actualise les SharedPreferences
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(nom_prefs,json_joueurs_nouveaux.toString());
        editor.apply();
    }
}
