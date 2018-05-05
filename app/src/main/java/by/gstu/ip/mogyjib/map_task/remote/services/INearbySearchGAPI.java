package by.gstu.ip.mogyjib.map_task.remote.services;

import by.gstu.ip.mogyjib.map_task.models.results.PlaceBasicResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface INearbySearchGAPI {
    @GET
    Call<PlaceBasicResult> getNearbyPlace(@Url String url);
}
