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

    // Renvoie le nom du joueur
    public String getName() {
        return name;
    }

    // Renvoie le score du joueur
    public String getScore() {
        return score;
    }

    // Renvoie le joueur sous forme d'un JSONObject
    public JSONObject toJson() {
        try {
            JSONObject jsonObject = new JSONObject().put("name", name).put("score", score);
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Récupère un joueur via un JSONObject
    public void fromJson(JSONObject jsonObject) {
        try {
            name = jsonObject.getString("name");
            score = jsonObject.getString("score");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Retourne le score du joueur sous forme "mm:ss"
    public String getScoreTemps() {

        Integer temps = Integer.valueOf(score);
        int minutes = temps / 60;
        int secondes = temps - (minutes * 60);
        DecimalFormat df = new DecimalFormat("00");
        return df.format(minutes) + ":" + df.format(secondes);
    }
}
