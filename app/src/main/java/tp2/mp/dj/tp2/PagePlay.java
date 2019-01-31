package tp2.mp.dj.tp2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class PagePlay extends AppCompatActivity {

    private ImageView btn_play = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_play);

        btn_play = (ImageView)findViewById(R.id.btn_play);
    }

    @Override
    public void onStart(){
        super.onStart();

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(PagePlay.this, Jeu.class);
                startActivity(intent);
            }
        });
    }
}
