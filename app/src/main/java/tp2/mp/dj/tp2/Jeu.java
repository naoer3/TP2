package tp2.mp.dj.tp2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.StateListDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Jeu extends AppCompatActivity {

    private Toolbar toolbar = null;
    private GridView table = null;

    private SharedPreferences prefs = null;

    private int carteRetourne = 0;
    private int pairesTrouvees = 0;
    private int pairesTotales = 0;

    private View premiere_carte_paire = null;
    private View deuxieme_carte_paire = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu);

        toolbar = (Toolbar)findViewById(R.id.toolbar_jeu);
        if(toolbar != null) setSupportActionBar(toolbar);

        table = (GridView) findViewById(R.id.jeu_cartes);

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    @Override
    public void onStart(){
        super.onStart();

        List<Integer> jeu = new ArrayList<>();
        List<ImageView> cartes = new ArrayList<>();

        pairesTotales = prefs.getInt("NB_CARTES",8);

        String avant = prefs.getString("THEME","chaton");
        int arriere = prefs.getInt("FOND",0);

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
                    if((int)image.getTag()!=-1){
                        image.setSelected(true);
                        carteRetourne++;
                        if(carteRetourne == 1) premiere_carte_paire = image;
                        else if(carteRetourne == 2)deuxieme_carte_paire = image;
                        else if(carteRetourne == 3){
                            Object id1 = premiere_carte_paire.getTag();
                            Object id2 = deuxieme_carte_paire.getTag();

                            if(id1 == id2) {
                                pairesTrouvees++;
                                //On ne peut plus retourner les cartes
                                premiere_carte_paire.setTag(-1);
                                deuxieme_carte_paire.setTag(-1);
                                if(pairesTrouvees == pairesTotales){
                                    //TODO : aller sur la page de résultats
                                }
                            }
                            else{
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
                //a faire
                return true;
            case R.id.action_rank :
                //a faire
                return true;
            case R.id.action_settings :
                intent = new Intent(Jeu.this, MainActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
