package by.gstu.ip.mogyjib.map_task.remote;

import android.location.Location;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import by.gstu.ip.mogyjib.map_task.handlers.LocationHandler;
import by.gstu.ip.mogyjib.map_task.models.MyPlaces;
import by.gstu.ip.mogyjib.map_task.models.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GoogleMapsAPIService {
    public static final String GOOGLE_MAPS_API_URL = "https://maps.googleapis.com/";
    private IGoogleAPIService mService;
    private String url;

    public GoogleMapsAPIService(){
        this(GOOGLE_MAPS_API_URL);
    }

    public GoogleMapsAPIService(String baseUrl) {
        mService = getRetrofitClient(baseUrl)
                .create(IGoogleAPIService.class);
    }

    private Retrofit getRetrofitClient(String baseUrl){
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void buildUrl(Location location,int radius,String key){
        StringBuilder url = new StringBuilder("");
        url.append(GOOGLE_MAPS_API_URL);
        url.append("maps/api/place/nearbysearch/json?");
        url.append("location="+location.getLatitude()+
                ","+location.getLongitude());
        url.append("&radius="+radius);
        url.append("&key="+key);
        this.url = url.toString();
    }

    public void searchPlaces(GoogleMap map){
        mService.getNearByPlaces(url)
                .enqueue(new Callback<MyPlaces>() {
                    @Override
                    public void onResponse(Call<MyPlaces> call, Response<MyPlaces> response) {
                        if(response.isSuccessful()){
                            for(Result result : response.body().results){
                                MarkerOptions markerOptions = new MarkerOptions();

                                LatLng latLng = new LatLng(result.geometry.location.lat,
                                        result.geometry.location.lng);
                                markerOptions.position(latLng);
                                markerOptions.title(result.name);

                                try {
                                   // markerOptions.icon(BitmapDescriptorFactory.fromPath(result.icon));
                                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
                                }catch (Exception exc){
                                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
                                }

                                map.addMarker(markerOptions);
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<MyPlaces> call, Throwable t) {

                    }
                });
    }
}
