package by.gstu.ip.mogyjib.map_task.activities;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import by.gstu.ip.mogyjib.map_task.R;
import by.gstu.ip.mogyjib.map_task.activities.fragments.MapsFragment;
import by.gstu.ip.mogyjib.map_task.activities.fragments.PlaceListFragment;
import by.gstu.ip.mogyjib.map_task.locations.LocationHandler;
import by.gstu.ip.mogyjib.map_task.locations.UpdateLocationCallback;
import by.gstu.ip.mogyjib.map_task.locations.LocationUtil;
import by.gstu.ip.mogyjib.map_task.models.PlaceBasicCollection;
import by.gstu.ip.mogyjib.map_task.models.results.PlaceBasicResult;
import by.gstu.ip.mogyjib.map_task.remote.OnDataSearchCompleteListener;

public class MainActivity
        extends AppCompatActivity
implements OnDataSearchCompleteListener<PlaceBasicResult>{

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private LocationHandler mLocationHandler;
    private UpdateLocationCallback mUpdateLocationCallback;

    private PlaceBasicCollection mPlaceBasicCollection;

    private MapsFragment mMapsFragment;
    private PlaceListFragment mPlaceListFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPlaceBasicCollection = new PlaceBasicCollection();

        mUpdateLocationCallback = new UpdateLocationCallback(this,
                "atm",
                1000,
                getResources().getString(R.string.browser_google_maps_key));

        mLocationHandler=new LocationHandler(this, mUpdateLocationCallback);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!LocationUtil.checkPermissions(this))
                LocationUtil.requestLocationPermission(this);
            else mLocationHandler.startLocationUpdates();
        }



        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        mMapsFragment = new MapsFragment();
        adapter.addFragment(mMapsFragment, "Map");

        mPlaceListFragment = new PlaceListFragment();
        adapter.addFragment(mPlaceListFragment, "List");

        viewPager.setAdapter(adapter);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LocationUtil.LOCATION_PERMISSIONS_REQUEST_CODE) {
            if (permissions.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    LocationUtil.checkPermissions(this)) {

                mLocationHandler.startLocationUpdates();
            }
        }
    }

    @Override
    public void onDataLoadComplete(Collection<PlaceBasicResult> results) {
        mPlaceBasicCollection.clear();
        for (PlaceBasicResult placeResult : results) {
            mPlaceBasicCollection.places.addAll(placeResult.results);
        }
        mPlaceBasicCollection.currentLocation =
                mUpdateLocationCallback.getLastLocation();

        mPlaceBasicCollection.sort();

        mMapsFragment.updateMap(mPlaceBasicCollection);
        mPlaceListFragment.updateList(mPlaceBasicCollection);
    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
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
}
