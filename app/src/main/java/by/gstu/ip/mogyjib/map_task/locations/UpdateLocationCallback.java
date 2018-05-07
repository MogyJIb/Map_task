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

/**
 * Location update listener. Class which get user location from
 * some period, check if location or search parameters are
 * changed and load new nearby places for new location or url.
 * Result of search it give to object OnDataSearchCompleteListener
 * to handle the result of search.
 *
 * @author Evgeniy Shevtsov
 * @version 1.0
 * @see OnDataSearchCompleteListener
 */
public class UpdateLocationCallback
        extends LocationCallback {
    private static final float MAX_CHANGE_DISTANCE = 100f;

    /**
     * Last user location
     */
    private Location mLastLocation;

    /**
     * Listener which called when data load form new
     * search result completed
     */
    private OnDataSearchCompleteListener<PlaceBasicResult> mDataLoadCompleteListener;

    /**
     * Search parameters
     */
    private String searchPlaceType;
    private int searchRadius;
    private String apiKey;

    /**
     * Flag - parameters are updated
     */
    private Boolean isParametersUpdated;

    //Constructor
    public UpdateLocationCallback(String searchPlaceType,
                                  int searchRadius,
                                  String apiKey) {

        this.searchPlaceType = searchPlaceType;
        this.apiKey = apiKey;
        this.searchRadius = searchRadius;
        isParametersUpdated = true;
    }

    //Called from some time periodic when get user location
    @Override
    public void onLocationResult(LocationResult locationResult) {
        if (locationResult == null)
            return;
        Location location = locationResult.getLastLocation();

        //Check is need update location and nearby places
        synchronized (isParametersUpdated) {
            if (!isUpdateLocation(location) && !isParametersUpdated)
                return;
            isParametersUpdated = false;
        }

        //update location
        mLastLocation = location;

        /*
            Search nearby places by Google Places nearbyplaces API
            and send result to data load complete listener.
         */
        new GooglePlaceAPISearcher<>(new NearbyPlaceGAPIService())
                .buildUrl(generateSearchParameters())
                .setDataLoadCompleteListener(mDataLoadCompleteListener)
                .execute();
    }

    /**
     * Check is users location was changed
     *
     * @param location new location
     * @return boolean value: changed or not
     */
    private boolean isUpdateLocation(Location location){
        if(mLastLocation == null)
            return true;

        /*
            check distance from previous location to current is bigger
            then updated
         */
        return mLastLocation.distanceTo(location) > MAX_CHANGE_DISTANCE;
    }

    /**
     * Generate search request url parameters to send request
     * to Google Places API
     *
     * @return parameters as map <parameterName,parameterValue>
     */
    private Map<String,String> generateSearchParameters(){
      Map<String,String> parameters =  new HashMap<>();
        parameters.put("location",
                mLastLocation.getLatitude()+","+mLastLocation.getLongitude());
        parameters.put("radius",searchRadius+"");
        parameters.put("key",apiKey);
        parameters.put("type",searchPlaceType);
        return parameters;
    }

    /**
     * Get last users location
     *
     * @return last location
     */
    public Location getLastLocation() {
        return mLastLocation;
    }

    /**
     * Set saved/previous/start users location
     *
     * @param location users location
     * @return reference to this object
     */
    public UpdateLocationCallback setLastLocation(Location location) {
        synchronized (isParametersUpdated) {
            mLastLocation = location;
            isParametersUpdated = true;
            return this;
        }
    }

    /**
     * Set on data load complete listener, it will called
     * when search get request result
     *
     * @param dataLoadCompleteListener data load listener
     * @return reference to this object
     */
    public UpdateLocationCallback setDataLoadCompleteListener(OnDataSearchCompleteListener<PlaceBasicResult> dataLoadCompleteListener) {
        synchronized (isParametersUpdated) {
            mDataLoadCompleteListener = dataLoadCompleteListener;
            return this;
        }
    }

    /*
        Setters for parameters, when update parameter, should change
        update flag to update search result with new data
     */

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
