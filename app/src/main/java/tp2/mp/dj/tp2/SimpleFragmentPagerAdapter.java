package tp2.mp.dj.tp2;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    public SimpleFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new ClassementZen();
            case 1: return new ClassementNormal();
            case 2: return new ClassementContreLaMontre();
            default: return  null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0: return context.getResources().getString(R.string.zen);
            case 1: return context.getResources().getString(R.string.normal);
            case 2: return context.getResources().getString(R.string.contre_la_montre);
            default: return null;
        }
    }
}
