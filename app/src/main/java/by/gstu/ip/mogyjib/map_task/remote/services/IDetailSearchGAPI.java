package by.gstu.ip.mogyjib.map_task.remote.services;

import by.gstu.ip.mogyjib.map_task.models.results.PlaceBasicResult;
import by.gstu.ip.mogyjib.map_task.models.results.PlaceDetailResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface IDetailSearchGAPI {
    @GET
    Call<PlaceDetailResult> getDetailPlace(@Url String url);
}
