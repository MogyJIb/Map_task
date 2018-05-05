package by.gstu.ip.mogyjib.map_task.remote.services;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private final String baseUrl;

    private Retrofit.Builder builder;
    private Retrofit retrofit;

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder();

    public ServiceGenerator(String baseUrl) {
        this.baseUrl = baseUrl;
        builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create());

        retrofit = builder.build();

    }

    public  <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}