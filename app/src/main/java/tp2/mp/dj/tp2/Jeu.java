package tp2.mp.dj.tp2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Jeu extends AppCompatActivity {

    private Toolbar toolbar = null;
    private GridView table = null;

    private SharedPreferences prefs = null;

    private int carteRetourne = 0;
    private int pairesTrouvees = 0;

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
        List<Integer> carte = new ArrayList<>();

        int nb_carte_totale = prefs.getInt("NB_CARTES",8) * 2;

        String avant = prefs.getString("THEME","chaton");
        int arriere = prefs.getInt("FOND",0);

        List<Integer> numero = new ArrayList<>();
        for(int i=0;i<10;i++){
            numero.add(i);
        }

        Collections.shuffle(numero);

        for (int i=0;i < nb_carte_totale/2; i++){
            jeu.add(numero.get(0));
            jeu.add(numero.get(0));
            numero.remove(0);
        }

        Collections.shuffle(jeu);

        for(int j=0; j<jeu.size(); j++) {
            carte.add(getResources().getIdentifier(avant + jeu.get(j),"drawable",getPackageName()));
        }

        Collections.shuffle(carte);

        table.setAdapter(new CarteAdapter(this, carte));
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
