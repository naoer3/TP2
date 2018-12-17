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
import android.widget.SeekBar;
import android.widget.TableRow;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    private EditText editName = null;
    private SeekBar seeknbcartes = null;
    private Button validate = null;
    private int nb_fonds = 3;
    private int nb_themes = 3;
    private TableRow tableRow_fonds = null;
    private TableRow tableRow_themes = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editName = (EditText) findViewById(R.id.name);
        seeknbcartes = (SeekBar) findViewById(R.id.seekBar);
        validate = (Button) findViewById(R.id.validate);
        LayoutInflater inflater = getLayoutInflater();
        tableRow_fonds = (TableRow) findViewById(R.id.choixfonds);
        tableRow_themes = (TableRow) findViewById(R.id.choixthemes);
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
                Integer nb_cartes = seeknbcartes.getProgress();
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("NAME",name_user);
                editor.putInt("NB_CARTES", nb_cartes);
                editor.apply();
            }
        });
    }

    private void getPref() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String name_user = prefs.getString("NAME", null);
        Integer nb_cartes = prefs.getInt("NB_CARTES",0);
        editName.setText(name_user);
        seeknbcartes.setProgress(nb_cartes);
    }

    private void BuildChoixFonds() {
        for(int i =0;i<nb_fonds;i++){
            ImageView image = new ImageView(this);
            image.setLayoutParams(new TableRow.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT,1));
            // TODO
            // A faire avec generique (ne marchait pas la fois précédente..)
            image.setImageResource(R.drawable.chaton0);
//            image.setImageResource(getResources().getIdentifier("drawable/fond"+i+".png",null, String.valueOf(getContext())));
            tableRow_fonds.addView(image);
        }
    }

    private void BuildChoixThemes() {
        // TODO
        // Sur le même exemple que la fonction BuildChoixFonds
    }
}
