package tp2.mp.dj.tp2;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.DecimalFormat;

public class Joueur {

    String name;
    String score;

    public Joueur(String n, String tnc) {
        name = n;
        score = tnc;
    }

    public Joueur() {

    }

    public String getName() {
        return name;
    }

    public String getScore() {
        return score;
    }

    public JSONObject toJson() {
        try {
            JSONObject jsonObject = new JSONObject().put("name", name).put("score", score);
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void fromJson(JSONObject jsonObject) {
        try {
            name = jsonObject.getString("name");
            score = jsonObject.getString("score");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String toString(){
        return "Name : "+name+" / Score : "+score;
    }

    public String getScoreTemps() {

        Integer temps = Integer.valueOf(score);
        // TODO : on n'autorise pas plus d'une heure de temps passé sur le jeu ?
        int minutes = temps / 60;
        int secondes = temps - (minutes*60);
        // TODO : rajout d'un zéro avant
        DecimalFormat df = new DecimalFormat("00");
        return df.format(minutes)+":"+df.format(secondes);
    }
}
