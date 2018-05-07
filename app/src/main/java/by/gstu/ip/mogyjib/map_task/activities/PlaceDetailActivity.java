package by.gstu.ip.mogyjib.map_task.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
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

public class PlaceDetailActivity extends AppCompatActivity {
    public static final String PLACE_ID = "place id";
    public static final String PLACE_DETAIL_FRAGMENT = "place fragment";

    private PlaceDetailFragment mPlaceDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);

        setSupportActionBar(findViewById(R.id.my_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState!=null){
            mPlaceDetailFragment = (PlaceDetailFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState,PLACE_DETAIL_FRAGMENT);
        }else{
            mPlaceDetailFragment = new PlaceDetailFragment();

            FragmentTransaction transaction =
                    getSupportFragmentManager().beginTransaction();

            transaction.add(R.id.place_detail_fragment_container,
                    mPlaceDetailFragment);
            transaction.commit();

            String placeId =  getIntent().getStringExtra(PLACE_ID);
            loadPlaceData(placeId);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState,PLACE_DETAIL_FRAGMENT,mPlaceDetailFragment);
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

    private void loadPlaceData(String placeId){
        Map<String,String> parameters = new HashMap<>();
        parameters.put("key",getResources().getString(R.string.browser_google_maps_key));
        parameters.put("placeid",placeId);

        new GooglePlaceAPISearcher<>(new DetailPlaceGAPIService())
                .buildUrl(parameters)
                .setDataLoadCompleteListener(result -> {
                    if(result.size()<1)
                        return;

                    PlaceDetail placeDetail = result.get(0).result;
                    if(placeDetail == null)
                        return;
                    mPlaceDetailFragment.updatePlaceInformationFields(placeDetail);
                })
                .execute();
    }
}
