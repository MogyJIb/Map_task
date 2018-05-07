package by.gstu.ip.mogyjib.map_task.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.HashMap;
import java.util.Map;

import by.gstu.ip.mogyjib.map_task.R;
import by.gstu.ip.mogyjib.map_task.activities.fragments.PlaceDetailFragment;
import by.gstu.ip.mogyjib.map_task.models.pojo.PlaceDetail;
import by.gstu.ip.mogyjib.map_task.remote.GooglePlaceAPISearcher;
import by.gstu.ip.mogyjib.map_task.remote.services.DetailPlaceGAPIService;

/**
 * It get place id, search it by Google Place detail API
 * and show all information in view by creating new place
 * detail Fragment
 *
 * @author Evgeniy Shevtsov
 * @version 1.0
 */
public class PlaceDetailActivity extends AppCompatActivity {
    //String tags to save instant state and transfer data
    public static final String PLACE_ID = "place id";
    public static final String PLACE_DETAIL_FRAGMENT = "place fragment";

    //Place detail fragment to show all place inform
    private PlaceDetailFragment mPlaceDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);

        //Set action bar with back button
        setSupportActionBar(findViewById(R.id.my_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Get fragment from saved instance state
        if (savedInstanceState != null) {
            mPlaceDetailFragment = (PlaceDetailFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState, PLACE_DETAIL_FRAGMENT);
        }
        //Create new detail place fragment
        else {
            mPlaceDetailFragment = new PlaceDetailFragment();

            FragmentTransaction transaction =
                    getSupportFragmentManager().beginTransaction();

            transaction.add(R.id.place_detail_fragment_container,
                    mPlaceDetailFragment);
            transaction.commit();

            String placeId = getIntent().getStringExtra(PLACE_ID);
            loadPlaceData(placeId);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //save fragmant information
        getSupportFragmentManager().putFragment(outState, PLACE_DETAIL_FRAGMENT, mPlaceDetailFragment);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Method will load detail place information from
     * Google place detail AIP, using corresponding service
     * and update detail fragment with this data
     *
     * @param placeId place id to search
     */
    private void loadPlaceData(String placeId) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("key", getResources().getString(R.string.browser_google_maps_key));
        parameters.put("placeid", placeId);

        //Set up background service to load data
        new GooglePlaceAPISearcher<>(new DetailPlaceGAPIService())
                .buildUrl(parameters)
                .setDataLoadCompleteListener(result -> {
                    if (result.size() < 1)
                        return;

                    PlaceDetail placeDetail = result.get(0).result;
                    if (placeDetail == null)
                        return;
                    mPlaceDetailFragment.updatePlaceInformationFields(placeDetail);
                })
                .execute();
    }
}
