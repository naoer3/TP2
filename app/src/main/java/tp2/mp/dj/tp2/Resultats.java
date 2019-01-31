package tp2.mp.dj.tp2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Page qui indique si on a gagne ou perdu la partie
 */
public class Resultats extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btn_recommencer = null;
    private ImageButton btn_classement = null;
    private ImageView image = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultats);
        btn_classement = (ImageButton)findViewById(R.id.button_recommencer);
        btn_recommencer = (ImageButton)findViewById(R.id.button_classement2);
        image = (ImageView)findViewById(R.id.image_resultats);
    }

    @Override
    public void onStart(){
        super.onStart();
        btn_recommencer.setOnClickListener(this);
        btn_classement.setOnClickListener(this);
        boolean resultats = true;
        Intent intent = getIntent();

        // Si la partie est perdue, on change l'image de l'ImageView
        if(intent != null) resultats = intent.getBooleanExtra("RESULTATS",true);
        if(!resultats) image.setImageResource(R.drawable.perdu);
    }

    // Au clic sur un bouton, on retourne sur la page jeu ou on va sur le classement
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