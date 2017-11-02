package newsongs.fr.newsongs.Fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by antoine on 02/11/17.
 */

public class PagerFragment extends FragmentStatePagerAdapter {
    private int nbTabs;

    public PagerFragment(FragmentManager fm, int nbTabs) {
        super(fm);
        this.nbTabs = nbTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }

    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                FriendsTab1Fragment tab1 = new FriendsTab1Fragment();
                return tab1;
            case 1:
                FriendsTab2Fragment tab2 = new FriendsTab2Fragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return nbTabs;
    }
}