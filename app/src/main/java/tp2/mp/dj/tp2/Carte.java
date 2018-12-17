package tp2.mp.dj.tp2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


public class Carte extends Fragment implements View.OnClickListener{

    private String imageAvant = null;
    private String fond = null;
    private int numPaire = -1;
    private boolean paireTrouve = false;

    private ImageButton image = null;
    private View view = null;

    public Carte() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_carte, container, false);
        image = (ImageButton)view.findViewById(R.id.carte);
        image.setOnClickListener(this);
        return view;
    }

    public void init(String avant, int arriere, int id){
        imageAvant = avant + id;
        fond = "fond" + arriere;
        numPaire = id;

        couvrirCarte();
    }

    public int getNumPaire() {
        return numPaire;
    }

    public boolean isPaireTrouve() { return paireTrouve; }

    public void setPaireTrouve(boolean paireTrouve) { this.paireTrouve = paireTrouve; }

    @Override
    public void onClick(View v) {
        if(!paireTrouve){
            decouvrirCarte();
        }
    }

    public void couvrirCarte(){
        image.setImageResource(getResources().getIdentifier("drawable/" + fond, null,getContext().getPackageName()));
    }

    public void decouvrirCarte(){
        image.setImageResource(getResources().getIdentifier("drawable/" + imageAvant, null,getContext().getPackageName()));
    }
}
