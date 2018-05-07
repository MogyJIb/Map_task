package by.gstu.ip.mogyjib.map_task.locations;

import android.app.Activity;
import android.os.Looper;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Location handler object, it create client to
 * handle location updates, set up listener and
 * start and stop updates.
 *
 * @author Evgeniy Shevtsov
 * @version 1.0
 */
public class LocationHandler {
    private static final String TAG
            = LocationHandler.class.getSimpleName();
    public static final int UPDATE_INTERVAL = 5000;

    //Location client
    private FusedLocationProviderClient mFusedLocationClient;

    //Location updates callback
    private LocationCallback mLocationCallback;

    /**
     * Location request - how often check update location
     * and with which priority
     */
    private LocationRequest mLocationRequest;

    //Constructor
    public LocationHandler(Activity activity, LocationCallback locationCallback) {
        mLocationCallback = locationCallback;
        mFusedLocationClient = LocationServices
                .getFusedLocationProviderClient(activity);

        mLocationRequest = LocationUtil.getLocationRequest(UPDATE_INTERVAL,
                LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    public void setLocationRequest(LocationRequest locationRequest) {
        this.mLocationRequest = locationRequest;
    }

    /**
     * Method start handle location updates with corresponded
     * request and results callback
     */
    public void startLocationUpdates() {
        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                mLocationCallback,
                Looper.myLooper());
    }

    /**
     * Method will stop location updates handle
     */
    public void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }
}
