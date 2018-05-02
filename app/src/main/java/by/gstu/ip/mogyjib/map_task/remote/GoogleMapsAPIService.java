package by.gstu.ip.mogyjib.map_task.remote;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import by.gstu.ip.mogyjib.map_task.models.GooglePlacesApiUrl;
import by.gstu.ip.mogyjib.map_task.models.pojo.MyPlaces;
import by.gstu.ip.mogyjib.map_task.models.pojo.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static by.gstu.ip.mogyjib.map_task.models.GooglePlacesApiUrl.GOOGLE_MAPS_API_URL;

public class GoogleMapsAPIService extends AsyncTask<Object,String,List<MarkerOptions>>{
    public static final String TAG = GoogleMapsAPIService.class.getSimpleName();

    private IGoogleAPIService mService;
    private GooglePlacesApiUrl mUrl;
    private GoogleMap mGoogleMap;

    public GoogleMapsAPIService(GoogleMap googleMap){
        this(GOOGLE_MAPS_API_URL,googleMap);
    }

    public GoogleMapsAPIService(String baseUrl,GoogleMap googleMap) {
        mService = new ServiceGenerator(baseUrl)
                .createService(IGoogleAPIService.class);

        mGoogleMap = googleMap;
        mUrl = new GooglePlacesApiUrl();
    }



    public GoogleMapsAPIService buildUrl(Map<String,String> parameters){
        mUrl.setParameters(parameters);
        mUrl.build();

        return this;
    }


    private List<MarkerOptions> processResults(Result[] results){
        List<MarkerOptions> markers = new ArrayList<>();
        for (Result result : results) {
            MarkerOptions markerOptions = new MarkerOptions();

            LatLng latLng = new LatLng(result.geometry.location.lat,
                    result.geometry.location.lng);
            markerOptions.position(latLng);
            markerOptions.title(result.name);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            markers.add(markerOptions);
        }
        return markers;
    }

    @Override
    protected List<MarkerOptions> doInBackground(Object... objects) {

        List<MarkerOptions> markers = new ArrayList<>();
        try {
            String pagetoken = "";
            do {
                Log.i(TAG, "url:  " + mUrl.getValue());
                Response<MyPlaces> response = mService
                        .getNearByPlaces(mUrl.getValue())
                        .execute();
                Log.i(TAG, "response:  " + response.body());
                if (response!=null && response.isSuccessful()) {
                    pagetoken = response.body().next_page_token;
                    mUrl.updateParameter("pagetoken",pagetoken);
                    mUrl.build();

                    Result[] results = response.body().results;
                    markers.addAll(processResults(results));
                }
            }while (pagetoken!=null);

            mUrl.removeParameter("pagetoken");
        } catch (IOException e) {
            e.printStackTrace();
        }


        return markers;
    }

    @Override
    protected void onPostExecute(List<MarkerOptions> markers) {
        for(MarkerOptions marker : markers){
            mGoogleMap.addMarker(marker);
        }
    }
}
