package tp2.mp.dj.tp2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.StateListDrawable;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Classe qui fait tout le jeu
 */
public class Jeu extends AppCompatActivity implements View.OnClickListener{

    /**
     * Variables globales de l'application
     */
    private Toolbar toolbar = null;
    private GridView table = null;
    private TextView score = null;

    private View premiere_carte_paire = null;
    private View deuxieme_carte_paire = null;

    private SharedPreferences prefs = null;

    private int carteRetourne = 0;
    private int pairesTrouvees = 0;
    private int pairesTotales = 0;
    private int nbCoups = 0;
    private int mode;

    private Chrono chrono = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu);

        toolbar = (Toolbar) findViewById(R.id.toolbar_jeu);
        if (toolbar != null) setSupportActionBar(toolbar);

        table = (GridView) findViewById(R.id.jeu_cartes);

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        score = (TextView) findViewById(R.id.score);
    }

    @Override
    public void onStart() {
        super.onStart();

        // Ajout de listener sur le changement de texte de score
        score.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            //Quand le temps, en mode contre-la-montre, arrive à sa fin, on perd
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().matches("00:00") && mode == 2 /* Mode contre-la-montre*/) {
                    Intent intent = new Intent(Jeu.this, Resultats.class);
                    intent.putExtra("RESULTATS", false);
                    startActivity(intent);
                }
            }
        });

        // On recupere la valeur des SharedPreferences
        pairesTotales = prefs.getInt("NB_CARTES", 8);
        String avant = prefs.getString("THEME", "chaton");
        int arriere = prefs.getInt("FOND", 0);
        mode = prefs.getInt("MODEJEU", 0);

        // Ajout des cartes de notre jeu dans le gridView
        ajoutCarteGridView(avant, arriere);

        // On crée les chronomètres utilisé en fonction du mode de jeu
        switch (mode) {
            case 0: // Zen
                score.setText(String.valueOf(nbCoups));
                break;
            case 1: // Normal
                chrono = new Chrono(false);
                break;
            case 2: // Contre-la-montre
                chrono = new Chrono(true);
                break;
        }

        // Si on n'est pas dans le mode Zen, on démarre le chrono
        if (chrono != null)
            chrono.execute();
    }

    // Quand on sort de l'activite, on arrete le chrono
    @Override
    protected void onStop() {
        super.onStop();
        if (chrono != null)
            chrono.cancel(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    // Action de la toolBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_rank:
                intent = new Intent(Jeu.this, ClassementActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_settings:
                intent = new Intent(Jeu.this, MainActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // TODO : Doriane a commenter
    private class Chrono extends AsyncTask<Void, Integer, Void> {

        private Date start;
        private DecimalFormat df = new DecimalFormat("00");
        private int temps_clm = 0;

        public Chrono(boolean clm) {
            if (clm)
                temps_clm = getTempsClm();
        }

        @Override
        protected void onPreExecute() {
            start = Calendar.getInstance().getTime();
        }

        @Override
        protected Void doInBackground(Void... params) {
            while (!this.isCancelled()) {
                Date now = Calendar.getInstance().getTime();
                long intervalle = now.getTime() - start.getTime();
                Integer temps;
                if (temps_clm != 0)
                    temps = temps_clm - (int) intervalle / 1000;
                else
                    temps = (int) intervalle / 1000;
                final int minutes = temps / 60;
                final int secondes = temps - (minutes * 60);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        score.setText(df.format(minutes) + ":" + df.format(secondes));
                    }
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    // Quand on gagne, on ajoute cette partie au classement
    private void AjoutClassement(int score_int, int mode) {
        TraitementClassement tc = new TraitementClassement(prefs);
        String nom = prefs.getString("NAME", "joueur");
        String score = Integer.toString(score_int);
        tc.run(new Joueur(nom, score), mode);
    }


    // Donne le temps du départ du contre-la-montre
    private int getTempsClm(){ return 10 * pairesTotales; }

    // Evenement onClick des cartes
    // Définit si on trouve ou non une paire
    @Override
    public void onClick(View image) {
        if ((int) image.getTag() != -1 && !image.isSelected()) {
            image.setSelected(true);
            carteRetourne++;
            if (carteRetourne == 1) premiere_carte_paire = image;
            else if (carteRetourne == 2) {
                deuxieme_carte_paire = image;
                Object id1 = premiere_carte_paire.getTag();
                Object id2 = deuxieme_carte_paire.getTag();
                nbCoups++;
                if (id1 == id2) {
                    pairesTrouvees++;
                    premiere_carte_paire.setTag(-1);
                    deuxieme_carte_paire.setTag(-1);
                    if (pairesTrouvees == pairesTotales) {
                        partieGagnee();
                    }
                }
                if(mode==0) score.setText(String.valueOf(nbCoups));
            } else if (carteRetourne == 3) {
                Object id1 = premiere_carte_paire.getTag();
                Object id2 = deuxieme_carte_paire.getTag();

                if (id1 != id2) {
                    premiere_carte_paire.setSelected(false);
                    deuxieme_carte_paire.setSelected(false);
                }
                carteRetourne = 1;
                premiere_carte_paire = image;
            }
        }
    }

    // Fin d'une partie quand elle est gagnee
    private void partieGagnee(){
        if (chrono != null)
            chrono.cancel(true);
        String[] parts;
        Integer temps;
        switch (mode) {
            case 0: // mode Zen
                AjoutClassement(nbCoups, 0);
                break;
            case 1: // mode Normal
                parts = score.getText().toString().split("[^\\d]+");
                temps = Integer.valueOf(parts[0]) * 60 + Integer.valueOf(parts[1]);
                AjoutClassement(temps, 1);
                break;
            case 2: // mode Contre-la-montre
                parts = score.getText().toString().split("[^\\d]+");
                temps = Integer.valueOf(parts[0]) * 60 + Integer.valueOf(parts[1]);
                AjoutClassement(getTempsClm() - temps, 2);
                break;
        }
        Intent intent = new Intent(Jeu.this, Resultats.class);
        intent.putExtra("RESULTATS", true);
        intent.putExtra("MODE",mode);
        startActivity(intent);
    }

    // On selectionne les id (parmis 10 possibles) des cartes qui vont intégrer notre jeu
    // On crée et ajoute des cartes au gridView
    private void ajoutCarteGridView(String avant, int arriere){
        List<Integer> jeu = new ArrayList<>();
        List<ImageView> cartes = new ArrayList<>();
        List<Integer> numero = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            numero.add(i);
        }

        Collections.shuffle(numero);

        for (int i = 0; i < pairesTotales; i++) {
            jeu.add(numero.get(0));
            jeu.add(numero.get(0));
            numero.remove(0);
        }

        Collections.shuffle(jeu);

        for (int j = 0; j < jeu.size(); j++) {
            final ImageView carte = new ImageView(this);
            StateListDrawable states = new StateListDrawable();
            states.addState(new int[]{android.R.attr.state_selected},
                    getResources().getDrawable(getResources().getIdentifier(avant + jeu.get(j), "drawable", getPackageName())));
            states.addState(new int[]{-android.R.attr.state_selected},
                    getResources().getDrawable(getResources().getIdentifier("fond" + arriere, "drawable", getPackageName())));
            carte.setImageDrawable(states);
            carte.setTag(jeu.get(j));
            cartes.add(carte);
            carte.setOnClickListener(this);
        }
        Collections.shuffle(cartes);

        table.setAdapter(new CarteAdapter(this, cartes));
    }

    // Permet d'annuler toute action suite au bouton physique "back"
    public void onBackPressed() {
    }
}
