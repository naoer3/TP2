package tp2.mp.dj.tp2;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Classe qui permet l'affichage de fragmens dans le viewPager
 */
public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    public SimpleFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        // On envoie au fragment le mode de jeu qu'il va représenter
        ClassementFragment fragmentClassement = new ClassementFragment();
        Bundle args = new Bundle();
        args.putInt("MODE", position);
        fragmentClassement.setArguments(args);
        return fragmentClassement;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return context.getResources().getString(R.string.zen);
            case 1:
                return context.getResources().getString(R.string.normal);
            case 2:
                return context.getResources().getString(R.string.contre_la_montre);
            default:
                return null;
        }
    }
}
