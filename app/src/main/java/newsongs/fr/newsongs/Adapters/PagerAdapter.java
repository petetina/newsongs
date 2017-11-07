package newsongs.fr.newsongs.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import newsongs.fr.newsongs.Activities.FriendsActivity;
import newsongs.fr.newsongs.Fragments.FriendsTab1Fragment;
import newsongs.fr.newsongs.Fragments.FriendsTab2Fragment;
import newsongs.fr.newsongs.Fragments.FriendsTab3Fragment;
import newsongs.fr.newsongs.Interfaces.UpdateableFragmentInterface;

/**
 * Created by antoine on 02/11/17.
 */

public class PagerAdapter extends FragmentPagerAdapter{
    private String[] tabs = new String[]{"Rechercher un ami","Invitations","Mes amis"};

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
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
            case 2:
                FriendsTab3Fragment tab3 = new FriendsTab3Fragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getItemPosition(Object object) {
        try{

            FriendsTab2Fragment f = (FriendsTab2Fragment ) object;
            if (f != null) {
                f.refresh();
            }
        }catch (Exception e){

        }
        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return tabs.length;
    }

}