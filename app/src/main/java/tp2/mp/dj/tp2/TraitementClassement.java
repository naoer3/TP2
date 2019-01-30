package tp2.mp.dj.tp2;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TraitementClassement {

    SharedPreferences prefs = null;

    public TraitementClassement(SharedPreferences p){
        prefs = p;
    }

    public void run(Joueur j, int mode){
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
        String classement_modif = prefs.getString(nom_prefs, null);
        List<Joueur> list_classement = new ArrayList<>();
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
        list_classement.add(j);
        // TODO min sdk = 24! Avant 21 !
        list_classement.sort(new Comparator<Joueur>() {
            @Override
            public int compare(Joueur j1, Joueur j2) {
                return Integer.valueOf(j1.score).compareTo(Integer.valueOf(j2.score));
            }
        });
        if(list_classement.size()>5)
            list_classement = list_classement.subList(0,5);
        JSONArray json_joueurs_nouveaux = new JSONArray();
        for (Joueur joueur :list_classement) {
            json_joueurs_nouveaux.put(joueur.toJson());
        }
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(nom_prefs,json_joueurs_nouveaux.toString());
        editor.apply();
    }
}
