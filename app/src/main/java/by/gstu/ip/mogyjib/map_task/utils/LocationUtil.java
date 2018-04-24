package by.gstu.ip.mogyjib.map_task.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.model.LatLng;

public class LocationUtil {
    public static final int MY_LOCATION_PERMITION_CODE = 111;

    public static boolean checkLocationPermission( Activity activity,Context context){
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            },MY_LOCATION_PERMITION_CODE);
            return false;
        }else return true;
    }

    public static LatLng getLatLng(Location location){
        double latitude = location.getLatitude();
        double longtitude = location.getLongitude();
        return new LatLng(latitude,longtitude);
    }
    public static LocationRequest getLocationRequest(long interval,int priority) {
        LocationRequest locationRequest = new LocationRequest();

        locationRequest.setInterval(interval);
        locationRequest.setFastestInterval(interval);
        locationRequest.setPriority(priority);

        return locationRequest;
    }
}
