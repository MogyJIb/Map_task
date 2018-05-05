package by.gstu.ip.mogyjib.map_task.remote.services;

import by.gstu.ip.mogyjib.map_task.models.results.PlaceDetailResult;
import retrofit2.Call;

public class DetailPlaceGAPIService implements RequestableGAPI<PlaceDetailResult> {
    public static final String BASE_URL = "https://maps.googleapis.com/";
    private final String API_NAME = "detail";

    private IDetailSearchGAPI mSearchGAPI;

    public DetailPlaceGAPIService() {
        this(BASE_URL);
    }

    public DetailPlaceGAPIService(String baseUrl){
       mSearchGAPI =  new ServiceGenerator(baseUrl)
                .createService(IDetailSearchGAPI.class);
    }

    @Override
    public Call<PlaceDetailResult> sendRequest(String url) {
        return mSearchGAPI.getDetailPlace(url);
    }

    @Override
    public String getApiName() {
        return API_NAME;
    }
}