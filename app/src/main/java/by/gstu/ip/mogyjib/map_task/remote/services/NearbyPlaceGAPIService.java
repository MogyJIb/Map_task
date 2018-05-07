package by.gstu.ip.mogyjib.map_task.remote.services;

import by.gstu.ip.mogyjib.map_task.models.results.PlaceBasicResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public class NearbyPlaceGAPIService implements RequestableGAPI<PlaceBasicResult> {
    public static final String BASE_URL = "https://maps.googleapis.com/";
    private final String API_NAME = "nearbysearch";

    private INearbySearchGAPI mSearchGAPI;

    public NearbyPlaceGAPIService() {
        this(BASE_URL);
    }

    public NearbyPlaceGAPIService(String baseUrl){
        mSearchGAPI =  new ServiceGenerator(baseUrl)
                .createService(INearbySearchGAPI.class);
    }

    @Override
    public Call<PlaceBasicResult> sendRequest(String url) {
        return mSearchGAPI.getNearbyPlace(url);
    }

    @Override
    public String getApiName() {
        return API_NAME;
    }

    public interface INearbySearchGAPI {
        @GET
        Call<PlaceBasicResult> getNearbyPlace(@Url String url);
    }
}
