package tp2.mp.dj.tp2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Resultats extends AppCompatActivity implements View.OnClickListener {

    private TextView texte = null;
    private Button btn_recommencer = null;
    private Button btn_classement = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultats);

        texte = (TextView) findViewById(R.id.txt_gagne_perdu);
        btn_classement = (Button)findViewById(R.id.button_recommencer);
        btn_recommencer = (Button)findViewById(R.id.button_classement2);
    }

    @Override
    public void onStart(){
        super.onStart();
        btn_recommencer.setOnClickListener(this);
        btn_classement.setOnClickListener(this);

        Intent intent = getIntent();
        if(intent != null) texte.setText(intent.getStringExtra("RESULTATS"));
    }

    @Override
    public void onClick(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.button_recommencer :
                intent = new Intent(Resultats.this, Jeu.class);
                startActivity(intent);
                break;
            case R.id.button_classement2 :
                intent = new Intent(Resultats.this, ClassementActivity.class);
                startActivity(intent);
                break;
        }
    }
}
