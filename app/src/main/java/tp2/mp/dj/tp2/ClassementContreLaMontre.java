package tp2.mp.dj.tp2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class ClassementContreLaMontre extends Fragment {

    private View view;
    private TableLayout tableLayout = null;
    private String nom_prefs ="CLASSEMENT_CLM";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_classement_contre_la_montre, container, false);
        tableLayout = view.findViewById(R.id.tbcontrelamontre);
        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String classement_modif = prefs.getString(nom_prefs, null);
        if(classement_modif != null) {
            try {
                JSONArray json_joueurs = new JSONArray(classement_modif);
                for (int i =0; i<json_joueurs.length(); i++){
                    Joueur joueur = new Joueur();
                    joueur.fromJson(json_joueurs.getJSONObject(i));
                    TableRow row = new TableRow(getActivity());
                    row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT));
                    TextView place = new TextView(getActivity());
                    place.setLayoutParams(new TableRow.LayoutParams(0,TableRow.LayoutParams.WRAP_CONTENT,1));
                    TextView nom = new TextView(getActivity());
                    nom.setLayoutParams(new TableRow.LayoutParams(0,TableRow.LayoutParams.WRAP_CONTENT,1));
                    TextView score = new TextView(getActivity());
                    score.setLayoutParams(new TableRow.LayoutParams(0,TableRow.LayoutParams.WRAP_CONTENT,1));
                    place.setText(String.valueOf(i+1));
                    place.setTextSize(18);
                    nom.setText(joueur.getName());
                    nom.setTextSize(18);
                    score.setText(joueur.getScoreTemps());
                    score.setTextSize(18);
                    row.addView(place);
                    row.addView(nom);
                    row.addView(score);
                    tableLayout.addView(row);
                }
            }
            catch (JSONException e){

            }
        }
    }
}
