package tp2.mp.dj.tp2;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TableRow;
import android.widget.TextView;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private EditText editName = null;
    private SeekBar seeknbcartes = null;
    private Button validate = null;
    private int nb_fonds =4 ;
    private int nb_themes = 4;
    private List themes = Arrays.asList("pokemon", "chaton", "chiot", "minion");
    private TableRow tableRow_fonds = null;
    private TableRow tableRow_themes = null;
    private RadioGroup group = null;
    private TextView valeurnbcartes = null;
    private int mode_jeu = 0;
    private int selected_fond;
    private int selected_theme;
    private int nb_cartes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO radio group en dynamique ou en dur ??
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editName = (EditText) findViewById(R.id.name);
        seeknbcartes = (SeekBar) findViewById(R.id.seekBar);
        valeurnbcartes = (TextView) findViewById(R.id.valeurnbcartes);
        seeknbcartes.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                nb_cartes = progress +3;
                valeurnbcartes.setText(String.valueOf(nb_cartes));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        validate = (Button) findViewById(R.id.validate);
        //LayoutInflater inflater = getLayoutInflater();
        tableRow_fonds = (TableRow) findViewById(R.id.choixfonds);
        tableRow_themes = (TableRow) findViewById(R.id.choixthemes);
        group = findViewById(R.id.radiogroup);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mode_jeu = checkedId;
            }
        });
        BuildChoixFonds();
        BuildChoixThemes();
    }

    @Override
    protected void onStart(){
        super.onStart();
        getPref();
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_user = editName.getText().toString();
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("NAME",name_user);
                editor.putInt("NB_CARTES", nb_cartes);
                editor.putInt("MODEJEU",mode_jeu);
                editor.putInt("SELECTED_FOND", selected_fond);
                editor.putInt("SELECTED_THEME", selected_theme);
                editor.apply();
                // TODO Envoyer le mode de jeu + fond + theme
            }
        });
    }

    private void getPref() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String name_user = prefs.getString("NAME", null);
        nb_cartes = prefs.getInt("NB_CARTES",3);
        mode_jeu = prefs.getInt("MODEJEU",R.id.rbnormal);
        group.check(mode_jeu);
        editName.setText(name_user);
        valeurnbcartes.setText(String.valueOf(nb_cartes));
        seeknbcartes.setProgress(nb_cartes - 3);
        selected_fond = prefs.getInt("SELECTED_FOND", 0);
        tableRow_fonds.getVirtualChildAt(selected_fond).setSelected(true);
        selected_theme = prefs.getInt("SELECTED_THEME", 0);
        tableRow_themes.getVirtualChildAt(selected_theme).setSelected(true);
    }

    private void BuildChoixFonds() {
        for(int i =0;i<nb_fonds;i++){
            ImageView image = new ImageView(this);
            image.setLayoutParams(new TableRow.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT,1));
            int drawableResourceId = this.getResources().getIdentifier("fond"+i, "drawable", this.getPackageName());
            image.setImageResource(drawableResourceId);
            image.setAdjustViewBounds(true);
            image.setPadding(4,4,4,4);
            image.setBackgroundResource(R.drawable.selector);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO à refaire un peu plus propre quand tout marchera
                    for (int i = 0 ; i<tableRow_fonds.getVirtualChildCount(); i++) {
                        tableRow_fonds.getVirtualChildAt(i).setSelected(false);
                        if(v.equals(tableRow_fonds.getVirtualChildAt(i))){
                            selected_fond = i;
                        }
                    }
                    v.setSelected(true);
                    }
            });
            tableRow_fonds.addView(image);
        }
    }

    private void BuildChoixThemes() {
        for(int i =0;i<nb_themes;i++){
            ImageView image = new ImageView(this);
            image.setLayoutParams(new TableRow.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT,1));
            int drawableResourceId = this.getResources().getIdentifier( themes.get(i).toString()+0, "drawable", this.getPackageName());
            image.setImageResource(drawableResourceId);
            image.setAdjustViewBounds(true);
            image.setPadding(4,4,4,4);
            image.setBackgroundResource(R.drawable.selector);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View btn) {
                    for (int i = 0 ; i<tableRow_themes.getVirtualChildCount(); i++) {
                        tableRow_themes.getVirtualChildAt(i).setSelected(false);
                        if(btn.equals(tableRow_themes.getVirtualChildAt(i))){
                            selected_theme = i;
                        }
                    }
                    btn.setSelected(true);
                }
            });
            tableRow_themes.addView(image);
        }
    }
}
