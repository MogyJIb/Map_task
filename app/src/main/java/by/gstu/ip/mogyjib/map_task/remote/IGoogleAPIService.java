package by.gstu.ip.mogyjib.map_task.remote;

import by.gstu.ip.mogyjib.map_task.models.pojo.MyPlaces;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface IGoogleAPIService {
    @GET
    Call<MyPlaces> getNearByPlaces(@Url String url);
}