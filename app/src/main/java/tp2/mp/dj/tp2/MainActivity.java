package tp2.mp.dj.tp2;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private EditText editName = null;
    private SeekBar seeknbcartes = null;
    private Button validate = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editName = (EditText) findViewById(R.id.name);
        seeknbcartes = (SeekBar) findViewById(R.id.seekBar);
        validate = (Button) findViewById(R.id.validate);
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
}
