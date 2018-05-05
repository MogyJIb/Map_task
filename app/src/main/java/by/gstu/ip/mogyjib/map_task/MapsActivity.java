package by.gstu.ip.mogyjib.map_task;

import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MapStyleOptions;

import by.gstu.ip.mogyjib.map_task.locations.LocationHandler;
import by.gstu.ip.mogyjib.map_task.locations.LocationUpdateListener;
import by.gstu.ip.mogyjib.map_task.locations.LocationUtil;

public class MapsActivity extends AppCompatActivity
        implements OnMapReadyCallback{

    private static final String TAG = MapsActivity.class.getSimpleName();

    private GoogleMap mMap;
    private LocationHandler mLocationHandler;
    private LocationUpdateListener mLocationUpdateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_maps);

        // Get the SupportMapFragment and register for the callback
        // when the map is ready for use.
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        mLocationUpdateListener = new LocationUpdateListener(mMap,
                "atm",
                5000,
                getResources().getString(R.string.browser_google_maps_key));

        mLocationHandler=new LocationHandler(this,mLocationUpdateListener);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            if (!LocationUtil.checkPermissions(this))
                LocationUtil.requestLocationPermission(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LocationUtil.LOCATION_PERMISSIONS_REQUEST_CODE) {
            if (permissions.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    LocationUtil.checkPermissions(this)) {

                mLocationHandler.startLocationUpdates();
                mMap.setMyLocationEnabled(true);
            }
        }
    }

    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready for use.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
        mLocationUpdateListener.mMap = googleMap;
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.map_style));

            if (!success) {
                Log.e(TAG, "Map style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find map style. Error: ", e);
        }


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(LocationUtil.checkPermissions(this)){
                mMap.setMyLocationEnabled(true);
                mLocationHandler.startLocationUpdates();
            }
        }

    }


}
