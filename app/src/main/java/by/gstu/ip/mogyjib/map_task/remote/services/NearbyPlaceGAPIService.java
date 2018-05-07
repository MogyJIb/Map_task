package by.gstu.ip.mogyjib.map_task.remote.services;

import by.gstu.ip.mogyjib.map_task.models.results.PlaceBasicResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;


/**
 * Service of Google Places neearbyplace API, to get
 * nearby places information around some location
 * from server about place by
 * its place id by sending the request.
 *
 * @author Evgeniy Shevtsov
 * @version 1.0
 */
public class NearbyPlaceGAPIService implements RequestableGAPI<PlaceBasicResult> {
    public static final String BASE_URL = "https://maps.googleapis.com/";
    private final String API_NAME = "nearbysearch";

    private INearbySearchGAPI mSearchGAPI;

    public NearbyPlaceGAPIService() {
        this(BASE_URL);
    }

    public NearbyPlaceGAPIService(String baseUrl) {
        mSearchGAPI = new ServiceGenerator(baseUrl)
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


    /**
     * Interface of Retrofit remote AIP to get nearby places
     * information around some location like PlaceBasicResult object
     *
     * @see retrofit2.Retrofit
     */
    public interface INearbySearchGAPI {
        @GET
        Call<PlaceBasicResult> getNearbyPlace(@Url String url);
    }
}
