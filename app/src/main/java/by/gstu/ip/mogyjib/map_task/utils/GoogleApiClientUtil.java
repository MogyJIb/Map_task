package by.gstu.ip.mogyjib.map_task.utils;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class GoogleApiClientUtil{

    public static synchronized GoogleApiClient buildGoogleApiClient(Context context,
                                                       GoogleApiClient.ConnectionCallbacks callbacks,
                                                       GoogleApiClient.OnConnectionFailedListener failedListener){

        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(callbacks)
                .addOnConnectionFailedListener(failedListener)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
        return googleApiClient;
    }
}
