package by.gstu.ip.mogyjib.map_task.remote;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import by.gstu.ip.mogyjib.map_task.models.GooglePlacesApiUrl;
import by.gstu.ip.mogyjib.map_task.models.results.Paginated;
import by.gstu.ip.mogyjib.map_task.remote.services.RequestableGAPI;
import retrofit2.Response;

public class GooglePlaceAPISearcher<T extends Paginated> extends AsyncTask<Object,String,Collection<T>>{
    public static final String TAG = GooglePlaceAPISearcher.class.getSimpleName();

    private OnDataLoadCompleteListener<T> mOnRequestResultListener;
    private RequestableGAPI<T> mService;
    public GooglePlacesApiUrl mUrl;

    public GooglePlaceAPISearcher(RequestableGAPI<T> service) {
        mService = service;
        mUrl = new GooglePlacesApiUrl(service.getApiName());
    }

    private boolean checkResponse(Response<T> response){
        return response != null
                 && response.isSuccessful()
                && response.body() != null;
    }

    private boolean checkPagetoken(String pagetoken){
        return pagetoken!=null && !pagetoken.isEmpty();
    }

    @Override
    protected Collection<T> doInBackground(Object... objects) {
        boolean isContinue = true;

        Set<T> results = new HashSet<>();
        String pagetoken = "";
            do {
                //Log request URL
                Log.i(TAG,mUrl.getValue());

                Response<T> response= null;
                try {
                 response = mService
                        .sendRequest(mUrl.getValue())
                        .execute();}
                catch (IOException e) {e.printStackTrace();}
                if(!checkResponse(response))
                    break;

                //Log response body
                Log.i(TAG,response.body().toString());

                pagetoken = response.body().getNextPageToken();
                isContinue = checkPagetoken(pagetoken);

                if(isContinue) {
                    mUrl.updateParameter("pagetoken", pagetoken);
                    mUrl.build();
                }

                results.add(response.body());
            } while (isContinue);

            mUrl.removeParameter("pagetoken");
        return results;
    }

    @Override
    protected void onPostExecute(Collection<T> results) {
        mOnRequestResultListener.onDataLoadComplete(results);
    }


    public GooglePlaceAPISearcher<T> buildUrl(Map<String,String> parameters){
        mUrl.setParameters(parameters);
        mUrl.build();
        return this;
    }

    public GooglePlaceAPISearcher<T> setOnRequestResultListener(OnDataLoadCompleteListener<T> onRequestResultListener) {
        mOnRequestResultListener = onRequestResultListener;
        return this;
    }
}
