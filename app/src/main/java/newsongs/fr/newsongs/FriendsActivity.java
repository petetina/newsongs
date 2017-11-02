package newsongs.fr.newsongs;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import newsongs.fr.newsongs.Fragments.PagerFragment;

public class FriendsActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String[] tabs = new String[]{"Rechercher un ami","Mes amis"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_friends);


        //Adding toolbar to the activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.pager);

        //Creating our pager adapter
        PagerFragment adapter = new PagerFragment(getSupportFragmentManager(), tabs.length);

        //Adding adapter to pager
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        for(int i=0; i<tabs.length;i++)
            tabLayout.getTabAt(i).setText(tabs[i]);


        //Adding onTabSelectedListener to swipe views
        tabLayout.addOnTabSelectedListener(this);

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }



}
