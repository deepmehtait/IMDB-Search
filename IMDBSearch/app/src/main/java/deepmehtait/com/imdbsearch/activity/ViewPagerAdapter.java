package deepmehtait.com.imdbsearch.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by Deep on 06-Apr-16.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    ArrayList<String> tabNames = new ArrayList<>();

    //ArrayList<> framgentObject=new ArrayList<Object>();
    public void addFragments(Fragment f, String name) {
        this.fragmentArrayList.add(f);
        //  this.framgentObject.add(f);
        this.tabNames.add(name);
    }

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }


    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames.get(position);
    }

    @Override
    public int getItemPosition(Object object) {

        return POSITION_NONE;
    }
}
