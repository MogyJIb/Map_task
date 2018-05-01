package by.gstu.ip.mogyjib.map_task;

import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import by.gstu.ip.mogyjib.map_task.handlers.LocationHandler;
import by.gstu.ip.mogyjib.map_task.remote.GoogleMapsAPIService;

public class MapsActivity extends AppCompatActivity
        implements OnMapReadyCallback{


    private static final String TAG = MapsActivity.class.getSimpleName();

    private GoogleMap mMap;
    private Marker mMarker;

    LocationHandler mLocationHandler;
    GoogleMapsAPIService mMapsAPIService;


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

        mMapsAPIService = new GoogleMapsAPIService();

        mLocationHandler=new LocationHandler(this,new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if(locationResult!=null)
                    updateLocation(locationResult.getLastLocation());
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            if (!LocationHandler.checkPermissions(this))
                LocationHandler.requestLocationPermission(this);
    }

    private void updateLocation(Location location) {
        mMap.clear();

        LatLng latLng = LocationHandler.getLatLng(location);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title("Your position")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        mMarker = mMap.addMarker(markerOptions);


        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        mMapsAPIService.buildUrl(location,2000,getResources().getString(R.string.browser_google_maps_key));
        mMapsAPIService.searchPlaces(mMap);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LocationHandler.LOCATION_PERMISSIONS_REQUEST_CODE) {
            if (permissions.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    LocationHandler.checkPermissions(this)) {

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
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.map_style));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(LocationHandler.checkPermissions(this)){
                mMap.setMyLocationEnabled(true);
                mLocationHandler.startLocationUpdates();
            }
        }

    }
}
