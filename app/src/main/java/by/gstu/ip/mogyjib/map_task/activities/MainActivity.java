package by.gstu.ip.mogyjib.map_task.activities;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import by.gstu.ip.mogyjib.map_task.R;
import by.gstu.ip.mogyjib.map_task.activities.adapters.ViewPagerAdapter;
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
    public static final String PLACE_TYPE = "place type";

    public static final String MAP_FRAGMENT = "map fragment";
    public static final String PLACE_LIST_FRAGMENT = "place list fragment";

    private LocationHandler mLocationHandler;
    private UpdateLocationCallback mUpdateLocationCallback;

    private PlaceBasicCollection mPlaceBasicCollection;
    private android.location.Location mLastLocation;
    private String mPlaceType;

    private MapsFragment mMapsFragment;
    private PlaceListFragment mPlaceListFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createModels(savedInstanceState);
        createFragments(savedInstanceState);
        createTabs();
        createButton();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!LocationUtil.checkPermissions(this))
                LocationUtil.requestLocationPermission(this);
            else mLocationHandler.startLocationUpdates();
        }
    }


    private void createModels(Bundle savedInstanceState){
        if(savedInstanceState==null) {
            mPlaceBasicCollection = new PlaceBasicCollection();
            mPlaceType = "atm";
        }
        else {
            mPlaceBasicCollection = (PlaceBasicCollection) savedInstanceState.getSerializable(PLACES);
            mLastLocation = savedInstanceState.getParcelable(LOCATION);
            mPlaceType= savedInstanceState.getString(PLACE_TYPE);
        }

        mUpdateLocationCallback = new UpdateLocationCallback(
                mPlaceType,
                1000,
                getResources().getString(R.string.browser_google_maps_key))
                .setDataLoadCompleteListener(this)
                .setLastLocation(mLastLocation);

        mLocationHandler=new LocationHandler(this, mUpdateLocationCallback);
    }

    private void createFragments(Bundle savedInstanceState){
        if(savedInstanceState == null){
            mMapsFragment = new MapsFragment();
            mPlaceListFragment = new PlaceListFragment();
        }else {
            mMapsFragment = (MapsFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState,MAP_FRAGMENT);
            mPlaceListFragment = (PlaceListFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState,PLACE_LIST_FRAGMENT);
        }
    }

    private void createTabs(){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(mMapsFragment, "Map");
        adapter.addFragment(mPlaceListFragment, "List");

        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void createButton(){
        FloatingActionButton choosePlaceTypeButton = findViewById(R.id.fb_choose_place_type);
        choosePlaceTypeButton.setOnClickListener(view -> {
            String[] placeTypes= getResources().getStringArray(R.array.place_types_array);
            new AlertDialog.Builder(this)
                    .setTitle("Select place type")
                    .setSingleChoiceItems(placeTypes, 0, (dialogInterface, selectedIndex) -> {
                        mPlaceType = placeTypes[selectedIndex];
                    })
                    .setPositiveButton("Ok", (dialogInterface, i) -> {
                        mUpdateLocationCallback.setSearchPlaceType(mPlaceType);
                    })
                    .setNegativeButton("Cancel",null)
                    .show();
        });
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(PLACES,mPlaceBasicCollection);
        outState.putParcelable(LOCATION,mLastLocation);
        outState.putString(PLACE_TYPE, mPlaceType);

        getSupportFragmentManager().putFragment(outState,MAP_FRAGMENT,mMapsFragment);
        getSupportFragmentManager().putFragment(outState,PLACE_LIST_FRAGMENT,mPlaceListFragment);

        mLocationHandler.stopLocationUpdates();
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
}
