package by.gstu.ip.mogyjib.map_task.locations;

import android.location.Location;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;

import java.util.HashMap;
import java.util.Map;

import by.gstu.ip.mogyjib.map_task.models.results.PlaceBasicResult;
import by.gstu.ip.mogyjib.map_task.remote.GooglePlaceAPISearcher;
import by.gstu.ip.mogyjib.map_task.remote.OnDataSearchCompleteListener;
import by.gstu.ip.mogyjib.map_task.remote.services.NearbyPlaceGAPIService;

public class UpdateLocationCallback
        extends LocationCallback {
    private static final float UPDATE_DISTANCE = 100f;

    private Location mLastLocation;
    private OnDataSearchCompleteListener<PlaceBasicResult> mDataLoadCompleteListener;

    private String searchPlaceType;
    private int searchRadius;
    private String apiKey;

    private Boolean isParametersUpdated;

    public UpdateLocationCallback(String searchPlaceType,
                                  int searchRadius,
                                  String apiKey) {

        this.searchPlaceType = searchPlaceType;
        this.apiKey = apiKey;
        this.searchRadius = searchRadius;
        isParametersUpdated = true;
    }

    @Override
    public void onLocationResult(LocationResult locationResult) {
        if (locationResult == null)
            return;
        Location location = locationResult.getLastLocation();

        synchronized (isParametersUpdated) {
            if (!isUpdateLocation(location) && !isParametersUpdated)
                return;
            isParametersUpdated = false;
        }
        mLastLocation = location;

        new GooglePlaceAPISearcher<>(new NearbyPlaceGAPIService())
                .buildUrl(generateSearchParameters())
                .setDataLoadCompleteListener(mDataLoadCompleteListener)
                .execute();


    }

    private boolean isUpdateLocation(Location location){
        if(mLastLocation == null)
            return true;

        float distance = mLastLocation.distanceTo(location);
        if (distance>UPDATE_DISTANCE)
            return true;

        return false;
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

    public Location getLastLocation() {
        return mLastLocation;
    }

    public UpdateLocationCallback setLastLocation(Location location) {
        synchronized (isParametersUpdated) {
            mLastLocation = location;
            isParametersUpdated = true;
            return this;
        }
    }

    public UpdateLocationCallback setDataLoadCompleteListener(OnDataSearchCompleteListener<PlaceBasicResult> dataLoadCompleteListener) {
        synchronized (isParametersUpdated) {
            mDataLoadCompleteListener = dataLoadCompleteListener;
            return this;
        }
    }

    public void setSearchPlaceType(String searchPlaceType) {
        synchronized (isParametersUpdated) {
            this.searchPlaceType = searchPlaceType;
            isParametersUpdated = true;
        }
    }

    public void setSearchRadius(int searchRadius) {
        synchronized (isParametersUpdated) {
            this.searchRadius = searchRadius;
            isParametersUpdated = true;
        }
    }

    public void setApiKey(String apiKey) {
        synchronized (isParametersUpdated) {
            this.apiKey = apiKey;
            isParametersUpdated = true;
        }
    }
}
