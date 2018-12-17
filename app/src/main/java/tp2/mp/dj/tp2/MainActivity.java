package tp2.mp.dj.tp2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private Carte carte = null;
    private Carte carte2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        carte = (Carte)getSupportFragmentManager().findFragmentById(R.id.fragment);
        carte2 = (Carte)getSupportFragmentManager().findFragmentById(R.id.fragment2);
        carte.init("minion",0,2);
        carte2.init("minion",0,7);
    }
}
