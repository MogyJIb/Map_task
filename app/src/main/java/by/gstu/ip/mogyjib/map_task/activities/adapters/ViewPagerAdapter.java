package by.gstu.ip.mogyjib.map_task.activities.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Adapter for tab pages, just contain all tabs as fragments
 * and their names and order
 *
 * @author Evgeniy Shevtsov
 * @version 1.0
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    //List of tabs - fragments
    private final List<Fragment> mFragmentList = new ArrayList<>();

    //List of tabs names
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}