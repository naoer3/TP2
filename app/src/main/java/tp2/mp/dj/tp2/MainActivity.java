package tp2.mp.dj.tp2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

/**
 * Classe qui permet de modifier les préférences (nombre de cartes, choix du dos de cartes, choix du thème, nom du joueur, mode de jeu)
 */
public class MainActivity extends AppCompatActivity {

    private EditText editName = null;
    private SeekBar seeknbcartes = null;
    private TableRow tableRow_fonds = null;
    private TableRow tableRow_themes = null;
    private RadioGroup group = null;
    private TextView valeurnbcartes = null;
    private ImageView play = null;
    private List<String> themes = Arrays.asList("pokemon", "chaton", "chiot", "minion");
    private int mode_jeu = 0;
    private int selected_fond;
    private int selected_theme;
    private int nb_cartes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editName = (EditText) findViewById(R.id.name);
        seeknbcartes = (SeekBar) findViewById(R.id.seekBar);
        valeurnbcartes = (TextView) findViewById(R.id.valeurnbcartes);
        tableRow_fonds = (TableRow) findViewById(R.id.choixfonds);
        tableRow_themes = (TableRow) findViewById(R.id.choixthemes);
        group = findViewById(R.id.radiogroup);
        play = (ImageView) findViewById(R.id.btn_play2);
        // Permet de maintenir à jour la valeur du nombre de cartes à jour en fonction de la valeur de la SeekBar
        seeknbcartes.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                nb_cartes = progress + 3;
                valeurnbcartes.setText(String.valueOf(nb_cartes));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mode_jeu = group.indexOfChild(group.findViewById(checkedId));
            }
        });
        BuildChoixFonds();
        BuildChoixThemes();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_settings);
        if (toolbar != null) setSupportActionBar(toolbar);

    }

    @Override
    protected void onStart() {
        super.onStart();
        getPref();

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavePreferences();
                Intent intent;
                intent = new Intent(MainActivity.this, Jeu.class);
                startActivity(intent);
            }
        });
    }

    // Sauvegarde les préférences dans un SharedPreferences
    private void SavePreferences() {
        String name_user = editName.getText().toString();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("NAME", name_user);
        editor.putInt("NB_CARTES", nb_cartes);
        editor.putInt("MODEJEU", mode_jeu);
        editor.putInt("FOND", selected_fond);
        String theme = themes.get(selected_theme);
        editor.putString("THEME", theme);
        editor.apply();
    }

    // Récupère les préférences dans le SharedPreferences et met à jour les variables
    private void getPref() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String name_user = prefs.getString("NAME", "Joueur");
        nb_cartes = prefs.getInt("NB_CARTES", 3);
        mode_jeu = prefs.getInt("MODEJEU", 0);
        group.check(group.getChildAt(mode_jeu).getId());
        editName.setText(name_user);
        valeurnbcartes.setText(String.valueOf(nb_cartes));
        seeknbcartes.setProgress(nb_cartes - 3);
        selected_fond = prefs.getInt("FOND", 0);
        tableRow_fonds.getVirtualChildAt(selected_fond).setSelected(true);
        String theme = prefs.getString("THEME", "chatons");
        for (int i = 0; i < themes.size(); i++) {
            if (theme.matches(themes.get(i)))
                selected_theme = i;
        }
        tableRow_themes.getVirtualChildAt(selected_theme).setSelected(true);
    }

    // Affiche les différents choix possibles de fonds
    private void BuildChoixFonds() {
        int nb_fonds = 4;
        for (int i = 0; i < nb_fonds; i++) {
            ImageView image = new ImageView(this);
            image.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            int drawableResourceId = this.getResources().getIdentifier("fond" + i, "drawable", this.getPackageName());
            image.setImageResource(drawableResourceId);
            image.setAdjustViewBounds(true);
            image.setPadding(4, 4, 4, 4);
            image.setBackgroundResource(R.drawable.selector);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < tableRow_fonds.getVirtualChildCount(); i++) {
                        tableRow_fonds.getVirtualChildAt(i).setSelected(false);
                        if (v.equals(tableRow_fonds.getVirtualChildAt(i))) {
                            selected_fond = i;
                        }
                    }
                    v.setSelected(true);
                }
            });
            tableRow_fonds.addView(image);
        }
    }

    // Affiche les différents choix possibles de thèmes
    private void BuildChoixThemes() {
        int nb_themes = 4;
        for (int i = 0; i < nb_themes; i++) {
            ImageView image = new ImageView(this);
            image.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            int drawableResourceId = this.getResources().getIdentifier(themes.get(i) + 0, "drawable", this.getPackageName());
            image.setImageResource(drawableResourceId);
            image.setAdjustViewBounds(true);
            image.setPadding(4, 4, 4, 4);
            image.setBackgroundResource(R.drawable.selector);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View btn) {
                    for (int i = 0; i < tableRow_themes.getVirtualChildCount(); i++) {
                        tableRow_themes.getVirtualChildAt(i).setSelected(false);
                        if (btn.equals(tableRow_themes.getVirtualChildAt(i))) {
                            selected_theme = i;
                        }
                    }
                    btn.setSelected(true);
                }
            });
            tableRow_themes.addView(image);
        }
    }

    // Concerne la toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    // Concerne la toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        SavePreferences();
        switch (item.getItemId()) {
            case R.id.action_rank:
                intent = new Intent(MainActivity.this, ClassementActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_game:
                intent = new Intent(MainActivity.this, Jeu.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Permet d'annuler toute action suite au bouton physique "back"
    public void onBackPressed() {
    }
}
