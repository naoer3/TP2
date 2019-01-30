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

public class Jeu extends AppCompatActivity {

    private Toolbar toolbar = null;
    private GridView table = null;

    private SharedPreferences prefs = null;

    private int carteRetourne = 0;
    private int pairesTrouvees = 0;
    private int pairesTotales = 0;
    private int nbCoups = 0;

    private View premiere_carte_paire = null;
    private View deuxieme_carte_paire = null;

    private Chrono chrono = null;
    private TextView score = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu);

        toolbar = (Toolbar)findViewById(R.id.toolbar_jeu);
        if(toolbar != null) setSupportActionBar(toolbar);

        table = (GridView) findViewById(R.id.jeu_cartes);

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        score = (TextView) findViewById(R.id.text_time);
    }

    @Override
    public void onStart(){
        super.onStart();

        score.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                /*System.out.println(s);
                String[] parts = s.toString().split("[^\\d]+");
                Integer temps = Integer.valueOf(parts[0]) * 60 + Integer.valueOf(parts[1]);*/
            }
        });

        List<Integer> jeu = new ArrayList<>();
        List<ImageView> cartes = new ArrayList<>();

        pairesTotales = prefs.getInt("NB_CARTES",8);

        String avant = prefs.getString("THEME","chaton");
        int arriere = prefs.getInt("FOND",0);

        // TODO
        //chrono = new Chrono();

        List<Integer> numero = new ArrayList<>();
        for(int i=0;i<10;i++){
            numero.add(i);
        }

        Collections.shuffle(numero);

        for (int i=0;i < pairesTotales; i++){
            jeu.add(numero.get(0));
            jeu.add(numero.get(0));
            numero.remove(0);
        }

        Collections.shuffle(jeu);

        for(int j=0; j<jeu.size(); j++) {
            final ImageView carte = new ImageView(this);
            StateListDrawable states = new StateListDrawable();
            states.addState(new int[] {android.R.attr.state_selected},
                    getResources().getDrawable(getResources().getIdentifier(avant + jeu.get(j),"drawable",getPackageName())));
            states.addState(new int[] {-android.R.attr.state_selected},
                    getResources().getDrawable(getResources().getIdentifier("fond"+arriere,"drawable",getPackageName())));
            carte.setImageDrawable(states);
            carte.setTag(jeu.get(j));
            cartes.add(carte);
            carte.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View image) {

                if((int)image.getTag()!=-1 && !image.isSelected()){
                    image.setSelected(true);
                    carteRetourne++;
                    if(carteRetourne == 1) premiere_carte_paire = image;
                    else if(carteRetourne == 2){
                        deuxieme_carte_paire = image;
                        Object id1 = premiere_carte_paire.getTag();
                        Object id2 = deuxieme_carte_paire.getTag();

                        if(id1 == id2) {
                            pairesTrouvees++;
                            premiere_carte_paire.setTag(-1);
                            deuxieme_carte_paire.setTag(-1);
                            if(pairesTrouvees == pairesTotales){
                                //chrono.cancel(true);
                                     if(chrono != null)
                                        chrono.cancel(true);
                                                                  String[] parts = score.getText().toString().split("[^\\d]+");
                                    Integer temps = Integer.valueOf(parts[0]) * 60 + Integer.valueOf(parts[1]);
                                int mode = prefs.getInt("MODEJEU",0);
                                switch (mode){
                                    case 0 : //mode Zen
                                        AjoutClassement(nbCoups,0);
                                }
                                Intent intent = new Intent(Jeu.this, Resultats.class);
                                intent.putExtra("RESULTATS",true);
                                startActivity(intent);
                            }
                        }
                        nbCoups++;
                    }
                    else if(carteRetourne == 3){
                        Object id1 = premiere_carte_paire.getTag();
                        Object id2 = deuxieme_carte_paire.getTag();

                        if(id1 != id2) {
                            premiere_carte_paire.setSelected(false);
                            deuxieme_carte_paire.setSelected(false);
                        }
                        carteRetourne = 1;
                        premiere_carte_paire = image;
                    }
                }
                }
            });
        }
        Collections.shuffle(cartes);

        table.setAdapter(new CarteAdapter(this, cartes));
        chrono.execute();
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(chrono != null)
            chrono.cancel(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent;
        switch (item.getItemId()){
            case R.id.action_pause :
                // TODO
                return true;
            case R.id.action_rank :
                // TODO
                return true;
            case R.id.action_settings :
                intent = new Intent(Jeu.this, MainActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class Chrono extends AsyncTask<Void, Integer, Void> {

        private Date start;
        private DecimalFormat df = new DecimalFormat("00");
        private int temps_clm = 0;

        public Chrono(boolean clm){
            if(clm)
                temps_clm = 10 * pairesTotales;
        }

        @Override
        protected void onPreExecute(){
            start = Calendar.getInstance().getTime();
        }

        @Override
        protected Void doInBackground(Void... params) {
            while(!this.isCancelled()) {
                Date now =  Calendar.getInstance().getTime();
                long intervalle = now.getTime()-start.getTime();
                Integer temps;
                if(temps_clm!=0)
                    temps =temps_clm - (int) intervalle / 1000;
                        else
                    temps =(int) intervalle / 1000;
                System.out.println(temps);
                final int minutes = temps / 60;
                final int secondes = temps - (minutes*60);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        score.setText(df.format(minutes)+":"+df.format(secondes));

                    }
                });
                System.out.println("TEST");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

    private void AjoutClassement(int score_int, int mode){
        TraitementClassement tc = new TraitementClassement(prefs);
        String nom = prefs.getString("NAME","joueur");
        String score = Integer.toString(score_int);
        tc.run(new Joueur(nom,score),mode);
    }
}
