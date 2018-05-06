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
    public static final float UPDATE_DISTANCE = 100f;

    private Location mLastLocation;
    private OnDataSearchCompleteListener<PlaceBasicResult> mDataLoadCompleteListener;

    public String searchPlaceType;
    public int searchRadius;
    public String apiKey;

    public UpdateLocationCallback(OnDataSearchCompleteListener<PlaceBasicResult> dataLoadCompleteListener,
                                  String searchPlaceType,
                                  int searchRadius,
                                  String apiKey) {

        mDataLoadCompleteListener = dataLoadCompleteListener;
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
            mLastLocation = location;

            new GooglePlaceAPISearcher<>(new NearbyPlaceGAPIService())
                    .buildUrl(generateSearchParameters())
                    .setDataLoadCompleteListener(mDataLoadCompleteListener)
                    .execute();
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
}
