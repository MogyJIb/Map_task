package by.gstu.ip.mogyjib.map_task.locations;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.model.LatLng;

public class LocationUtil {
    public static final int LOCATION_PERMISSIONS_REQUEST_CODE = 111;

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

    public static LocationRequest getLocationRequest(long interval, int priority) {
        LocationRequest locationRequest = new LocationRequest();

        locationRequest.setInterval(interval);
        locationRequest.setFastestInterval(interval);
        locationRequest.setPriority(priority);

        return locationRequest;
    }

    public static LatLng getLatLng(Location location){
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        return new LatLng(latitude,longitude);
    }

}
