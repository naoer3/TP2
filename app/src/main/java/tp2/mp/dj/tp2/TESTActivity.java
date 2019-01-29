package tp2.mp.dj.tp2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class TESTActivity extends AppCompatActivity {

    private EditText name = null;
    private EditText temps_npcps = null;
    private RadioGroup rdgrp = null;
    private int mode_jeu;
    private Button valider = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        name = findViewById(R.id.editText_nom);
        temps_npcps = findViewById(R.id.editText_tps_nbcps);
        rdgrp = findViewById(R.id.radiogroup_mode);
        rdgrp.check(0);
        rdgrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mode_jeu = group.indexOfChild(group.findViewById(checkedId));
            }
        });
        valider = findViewById(R.id.button_classement);
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // On ajoute le joueur si non vide
                if(!name.getText().toString().matches("") && !temps_npcps.getText().toString().matches("") ) {
                    String nom_joueur = name.getText().toString();
                    String score_joueur = temps_npcps.getText().toString();
                    // On va chercher le classement associé
                    String nom_prefs = null;
                    switch(mode_jeu){
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
                    Joueur joueur = new Joueur(nom_joueur, score_joueur);
                    JSONObject jsonjoueur = joueur.toJson();
                    System.out.println(jsonjoueur);
                    System.out.println(jsonjoueur.toString());
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    String classement_modif = prefs.getString(nom_prefs, null);
                    System.out.println("classement_modif : "+classement_modif);
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

                        }
                    }
                    list_classement.add(joueur);
                    // TODO min sdk = 24! Avant 21 !
                    list_classement.sort(new Comparator<Joueur>() {
                        @Override
                        public int compare(Joueur j1, Joueur j2) {
                            return Integer.valueOf(j1.score).compareTo(Integer.valueOf(j2.score));
                        }
                    });
                    System.out.println("list_classement AVANT  " +list_classement.toString());
                    if(list_classement.size()>5)
                        list_classement = list_classement.subList(0,5);
                    System.out.println("list_classement APRES  " +list_classement.toString());
                    JSONArray json_joueurs_nouveaux = new JSONArray();
                    for (Joueur j :list_classement) {
                        json_joueurs_nouveaux.put(j.toJson());
                    }
                    System.out.println("json_joueurs_nouveaux " +json_joueurs_nouveaux);
                    // On édite le classement (si besoin)
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString(nom_prefs,json_joueurs_nouveaux.toString());
                    editor.apply();
                }

                // On va voir la page de classement
                Intent activity;
                activity = new Intent(TESTActivity.this,ClassementActivity.class);
                startActivity(activity);
            }
        });
    }
}
