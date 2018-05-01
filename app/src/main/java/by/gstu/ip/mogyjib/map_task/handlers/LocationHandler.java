package by.gstu.ip.mogyjib.map_task.handlers;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class LocationHandler {
    public static final int LOCATION_PERMISSIONS_REQUEST_CODE = 111;
    private static final String TAG
            = LocationHandler.class.getSimpleName();

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private LocationRequest mLocationRequest;

    public LocationHandler(Activity activity,LocationCallback locationCallback) {
        mLocationCallback = locationCallback;
        mFusedLocationClient = LocationServices
                .getFusedLocationProviderClient(activity);
        mLocationRequest = getLocationRequest(100000,
                LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public static boolean checkPermissions(Activity activity) {
        int permissionState = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestLocationPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                LOCATION_PERMISSIONS_REQUEST_CODE);
    }

    public static LocationRequest getLocationRequest(long interval,int priority) {
        LocationRequest locationRequest = new LocationRequest();

        locationRequest.setInterval(interval);
        locationRequest.setFastestInterval(interval/2);
        locationRequest.setPriority(priority);

        return locationRequest;
    }

    public static LatLng getLatLng(Location location){
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        return new LatLng(latitude,longitude);
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
