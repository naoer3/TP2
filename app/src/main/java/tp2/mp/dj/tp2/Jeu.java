package tp2.mp.dj.tp2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Jeu extends AppCompatActivity {

    private Toolbar toolbar = null;
    private TableLayout table = null;
    private Carte carte = null;

    private SharedPreferences prefs = null;

    private int carteRetourne = 0;
    private int pairesTrouvees = 0;

    private int taille_ecran_X = 0;
    private int taille_carte_X = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu);

        toolbar = (Toolbar)findViewById(R.id.toolbar_jeu);
        if(toolbar != null) setSupportActionBar(toolbar);

        table = (TableLayout) findViewById(R.id.jeu_cartes);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        List<Integer> jeu = new ArrayList<>();
        Random rdm = new Random();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        taille_ecran_X = size.x;
        taille_carte_X = 150;

        int nb_carte_ligne = (int)Math.floor(taille_ecran_X / taille_carte_X);
        int nb_carte_totale = prefs.getInt("NB_CARTES",8) * 2;
        int nb_ligne = nb_carte_totale / nb_carte_ligne;
        int nb_carte_der_ligne = nb_carte_totale - (nb_carte_ligne * nb_ligne);

        String avant = prefs.getString("THEME","chaton");
        int arriere = prefs.getInt("FOND",0);

        for (int i=0;i < nb_carte_totale/2; i++){
            //Ne pas reprendre les numeros deja pris
            int id = rdm.nextInt(10);
            jeu.add(id);
            jeu.add(id);
        }

        Collections.shuffle(jeu);

        int index = 0;
        for(int i=0;i<nb_ligne;i++){
            TableRow tb = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            tb.setLayoutParams(lp);
            table.addView(tb, i);
            for(int j=0; j<nb_carte_ligne; j++) {
                Bundle bd = new Bundle();
                Carte carte = new Carte();
                bd.putString("avant", avant);
                bd.putInt("fond", arriere);
                //Jeu est une liste de int
                bd.putInt("id", jeu.get(index));
                carte.setArguments(bd);
                getSupportFragmentManager().beginTransaction().add(tb.getId(),carte).commit();
                index++;
            }
        }
        //La dernière ligne
        /*TableRow tb = new TableRow(this);
        TableLayout.LayoutParams layoutParam = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
        table.addView(tb);
        for(int i=0;i<nb_carte_der_ligne;i++){
            Bundle bd = new Bundle();
            bd.putString("avant", avant);
            bd.putInt("fond", arriere);
            bd.putInt("id", jeu.get(index));
            Carte carte = new Carte();
            carte.setArguments(bd);
            int a = tb.getId();
            getSupportFragmentManager().beginTransaction().add(tb.getId(),carte).commit();
            index++;
        }*/

    }

    @Override
    protected void onStart(){
        super.onStart();


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

//Il faut savoir quand l'utilisateur clique sur une carte, on incrémente carteRetourne
//Au bout de 2 cartes découvertes mais ne faisant pas partie d'une paire, on traite
//Si les cartes on le même ID, on passe paireTrouve (du fragment) à True sinon elle se retourne et carteRetourne passe à 0
//On compte le nombre de paires trouvées (pairesTrouvees), quand on atteint le nombre de paires du jeu la partie est finie
//On affiche d'abord un Toast et par la suite on passera sur une autre activité ou on ouvre une pop up

//Il faudra peut-etre bloquer les évènements de retournement de cartes lors du traitement de l'appli

//Voir s'il existe un layout qui place les objets les uns à la suite des autres
