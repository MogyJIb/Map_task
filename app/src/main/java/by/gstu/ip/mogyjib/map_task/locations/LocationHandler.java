package by.gstu.ip.mogyjib.map_task.locations;

import android.app.Activity;
import android.os.Looper;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class LocationHandler {
    private static final String TAG
            = LocationHandler.class.getSimpleName();

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private LocationRequest mLocationRequest;

    public LocationHandler(Activity activity,LocationCallback locationCallback) {
        mLocationCallback = locationCallback;
        mFusedLocationClient = LocationServices
                .getFusedLocationProviderClient(activity);

        mLocationRequest = LocationUtil.getLocationRequest(1000,
                LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    public void setLocationRequest(LocationRequest locationRequest){
        this.mLocationRequest = locationRequest;
    }

    public void startLocationUpdates(){
        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                mLocationCallback,
                Looper.myLooper());
    }

    public void stopLocationUpdates(){
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }
}
