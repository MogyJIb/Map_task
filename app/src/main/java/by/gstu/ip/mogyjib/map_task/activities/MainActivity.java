package by.gstu.ip.mogyjib.map_task.activities;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
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
import by.gstu.ip.mogyjib.map_task.models.pojo.Location;
import by.gstu.ip.mogyjib.map_task.models.results.PlaceBasicResult;
import by.gstu.ip.mogyjib.map_task.remote.OnDataSearchCompleteListener;

public class MainActivity
        extends AppCompatActivity
implements OnDataSearchCompleteListener<PlaceBasicResult>{

    public static final String LOCATION = "location";
    public static final String PLACES = "places";
    public static final String MAP_FRAGMENT = "map fragment";
    public static final String PLACE_LIST_FRAGMENT = "place list fragment";

    private LocationHandler mLocationHandler;
    private UpdateLocationCallback mUpdateLocationCallback;

    private PlaceBasicCollection mPlaceBasicCollection;
    private android.location.Location mLastLocation;

    private MapsFragment mMapsFragment;
    private PlaceListFragment mPlaceListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState==null)
            mPlaceBasicCollection = new PlaceBasicCollection();
        else {
            mPlaceBasicCollection = (PlaceBasicCollection) savedInstanceState.getSerializable(PLACES);
            mLastLocation = savedInstanceState.getParcelable(LOCATION);
        }

        mUpdateLocationCallback = new UpdateLocationCallback(
                "atm",
                1000,
                getResources().getString(R.string.browser_google_maps_key))
            .setDataLoadCompleteListener(this)
            .setLastLocation(mLastLocation);

        mLocationHandler=new LocationHandler(this, mUpdateLocationCallback);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!LocationUtil.checkPermissions(this))
                LocationUtil.requestLocationPermission(this);
            else mLocationHandler.startLocationUpdates();
        }


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(createAdapter(viewPager,savedInstanceState));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private ViewPagerAdapter createAdapter(ViewPager viewPager,Bundle savedInstanceState) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        if(savedInstanceState == null){
            mMapsFragment = new MapsFragment();
            mPlaceListFragment = new PlaceListFragment();
        }else {
            mMapsFragment = (MapsFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState,MAP_FRAGMENT);
            mPlaceListFragment = (PlaceListFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState,PLACE_LIST_FRAGMENT);
        }

        adapter.addFragment(mMapsFragment, "Map");
        adapter.addFragment(mPlaceListFragment, "List");

        return adapter;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(PLACES,mPlaceBasicCollection);
        outState.putParcelable(LOCATION,mLastLocation);

        getSupportFragmentManager().putFragment(outState,MAP_FRAGMENT,mMapsFragment);
        getSupportFragmentManager().putFragment(outState,PLACE_LIST_FRAGMENT,mPlaceListFragment);

        mLocationHandler.stopLocationUpdates();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
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
    public void onDataLoadComplete(List<PlaceBasicResult> results) {
        mPlaceBasicCollection.clear();
        for (PlaceBasicResult placeResult : results) {
            mPlaceBasicCollection.places.addAll(placeResult.results);
        }
        mLastLocation = mUpdateLocationCallback.getLastLocation();
        mPlaceBasicCollection.currentLocation = new Location(mLastLocation.getLatitude(),
                mLastLocation.getLongitude());


        mPlaceBasicCollection.sort();

        mMapsFragment.updateMap(mPlaceBasicCollection);
        mPlaceListFragment.updateList(mPlaceBasicCollection);
    }


    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
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
