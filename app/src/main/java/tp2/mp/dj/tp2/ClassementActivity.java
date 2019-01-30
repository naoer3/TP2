package tp2.mp.dj.tp2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ClassementActivity extends AppCompatActivity {

    private Button btn_back = null;
    private ViewPager viewPager = null;
    private TabLayout tabLayout = null;
    private SimpleFragmentPagerAdapter adapter;
    private Toolbar toolbar = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classement);
        btn_back = findViewById(R.id.buttonback);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activity;
                activity = new Intent(ClassementActivity.this,TESTActivity.class);
                startActivity(activity);
            }
        });
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);

        toolbar = (Toolbar)findViewById(R.id.toolbar_settings);
        if(toolbar != null) setSupportActionBar(toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter = new SimpleFragmentPagerAdapter(this, getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(adapter.getCount()-1);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String classement_modif = prefs.getString("CLASSEMENT_CLM", null);
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
                // TODO
                return true;
            case R.id.action_rank :
                // TODO
                return true;
            case R.id.action_game :
                intent = new Intent(ClassementActivity.this, Jeu.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
