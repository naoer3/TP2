package tp2.mp.dj.tp2;

import android.os.AsyncTask;

import java.sql.Timestamp;

public class Chrono extends AsyncTask<Void, Integer, Void> {

    private Timestamp start;

    @Override
    protected void onPreExecute(){

    }

    @Override
    protected Void doInBackground(Void... voids) {
        while(!this.isCancelled()) {
            System.out.println("TEST");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
