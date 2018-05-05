package by.gstu.ip.mogyjib.map_task.remote.services;

import retrofit2.Call;

public interface RequestableGAPI<T> {
    Call<T> sendRequest(String url);
    String getApiName();
}
