package tp2.mp.dj.tp2;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
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

/**
 * Classe qui définit le fragment dans le viewpager du classement
 */
public class ClassementFragment extends Fragment {

    private TableLayout tableLayout = null;
    private String nom_prefs;
    private int mode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classement, container, false);
        tableLayout = view.findViewById(R.id.tbclassement);
        TextView text = (TextView) view.findViewById(R.id.textView5);
        Bundle args = getArguments();
        if (args!=null) {
            mode = args.getInt("MODE", 0);
            // En fonction du mode on spécifie le nom du SharedPreferences à aller chercher
            switch (mode) {
                case 0:
                    nom_prefs = "CLASSEMENT_ZEN";
                    text.setText("Nombre de coups");
                    break;
                case 1:
                    nom_prefs = "CLASSEMENT_NORMAL";
                    break;
                case 2:
                    nom_prefs = "CLASSEMENT_CLM";
                    break;
            }
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String classement_modif = prefs.getString(nom_prefs, null);
        if (classement_modif != null) {
            try {
                // On récupère le string classement_modif dans un JSONArray
                JSONArray json_joueurs = new JSONArray(classement_modif);
                // Pour chaque joueur que l'on trouve, on crée un objet Joueur
                for (int i = 0; i < json_joueurs.length(); i++) {
                    Joueur joueur = new Joueur();
                    joueur.fromJson(json_joueurs.getJSONObject(i));
                    // On crée une nouvelle ligne dans notre tableau
                    TableRow row = new TableRow(getActivity());
                    row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    // On crée trois emplacements de texte
                    for (int j = 0; j < 3; j++) {
                        TextView text = new TextView(getActivity());
                        text.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
                        text = SetDesignedText(text);
                        switch (j) {
                            case 0: // Place du joueur
                                text.setText(String.valueOf(i + 1));
                                break;
                            case 1: // Nom du joueur
                                text.setText((joueur.getName()));
                                break;
                            case 2: // Score du joueur
                                if (mode == 0) text.setText(joueur.getScore());
                                else text.setText(joueur.getScoreTemps());


                                break;
                        }
                        row.addView(text);
                    }
                    // On ajoute la ligne au tableau
                    tableLayout.addView(row);
                }
            } catch (JSONException e) {
            }
        }
    }

    public TextView SetDesignedText(TextView text) {
        text.setTextSize(24);
        text.setTypeface(null, Typeface.BOLD_ITALIC);
        text.setPadding(0, 0, 0, 20);
        text.setTextColor(Color.WHITE);
        return text;
    }
}
