package by.gstu.ip.mogyjib.map_task.remote.services;

import retrofit2.Call;

/**
 * Abstract service to send request to web server
 * by URL and get results.
 *
 * @author Evgeniy Shevtsov
 * @version 1.0
 * @see retrofit2.Retrofit
 */
public interface RequestableGAPI<T> {
    Call<T> sendRequest(String url);

    String getApiName();
}
