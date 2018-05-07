package by.gstu.ip.mogyjib.map_task.remote.services;

import by.gstu.ip.mogyjib.map_task.models.results.PlaceDetailResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Service of Google Places detail API, to get
 * detail information from server about place by
 * its place id by sending the request.
 *
 * @author Evgeniy Shevtsov
 * @version 1.0
 */
public class DetailPlaceGAPIService implements RequestableGAPI<PlaceDetailResult> {
    public static final String BASE_URL = "https://maps.googleapis.com/";
    private final String API_NAME = "details";

    private IDetailSearchGAPI mSearchGAPI;

    public DetailPlaceGAPIService() {
        this(BASE_URL);
    }

    public DetailPlaceGAPIService(String baseUrl) {
        mSearchGAPI = new ServiceGenerator(baseUrl)
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


    /**
     * Interface of Retrofit remote AIP to get detail place
     * information like PlaceDetailResult object
     *
     * @see retrofit2.Retrofit
     */
    public interface IDetailSearchGAPI {
        @GET
        Call<PlaceDetailResult> getDetailPlace(@Url String url);
    }
}
