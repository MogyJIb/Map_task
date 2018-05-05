package by.gstu.ip.mogyjib.map_task.locations;

import android.location.Location;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

import by.gstu.ip.mogyjib.map_task.models.pojo.PlaceBasic;
import by.gstu.ip.mogyjib.map_task.models.results.PlaceBasicResult;
import by.gstu.ip.mogyjib.map_task.remote.GooglePlaceAPISearcher;
import by.gstu.ip.mogyjib.map_task.remote.services.NearbyPlaceGAPIService;

public class LocationUpdateListener
        extends LocationCallback {
    public static final float UPDATE_DISTANCE = 100f;

    private Location mLastLocation;
    public GoogleMap mMap;

    public String searchPlaceType;
    public int searchRadius;
    public String apiKey;

    public LocationUpdateListener(GoogleMap map,String searchPlaceType,int searchRadius, String apiKey) {
        mMap = map;
        this.searchPlaceType = searchPlaceType;
        this.apiKey = apiKey;
        this.searchRadius = searchRadius;
    }

    @Override
    public void onLocationResult(LocationResult locationResult) {
        if(locationResult==null)
            return;
        Location location = locationResult.getLastLocation();

        if(isUpdateLocation(location)){
            mMap.clear();
            mLastLocation = location;
            setCurrLocationMarker();
            searchPlaces();
        }
    }

    private boolean isUpdateLocation(Location location){
        if(mLastLocation == null)
            return true;

        float distance = mLastLocation.distanceTo(location);
        if (distance>UPDATE_DISTANCE)
            return true;

        return false;
    }

    private void setCurrLocationMarker(){
        LatLng latLng = LocationUtil.getLatLng(mLastLocation);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title("Your position")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
    }

    private void searchPlaces(){
        new GooglePlaceAPISearcher<>(new NearbyPlaceGAPIService())
                .buildUrl(generateSearchParameters())
                .setOnRequestResultListener(results -> {
                    for(PlaceBasicResult placeResult : results){
                        for(PlaceBasic place : placeResult.results){
                            MarkerOptions markerOptions = new MarkerOptions()
                                    .title(place.name)
                                    .position(place.getLatlng())
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                            mMap.addMarker(markerOptions);
                        }
                    }
                })
                .execute();
    }

    private Map<String,String> generateSearchParameters(){
      Map<String,String> parameters =  new HashMap<>();
        parameters.put("location",
                mLastLocation.getLatitude()+","+mLastLocation.getLongitude());
        parameters.put("radius",searchRadius+"");
        parameters.put("key",apiKey);
        parameters.put("type",searchPlaceType);
        return parameters;
    }
}
