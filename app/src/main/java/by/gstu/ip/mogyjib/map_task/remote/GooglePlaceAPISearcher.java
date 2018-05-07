package by.gstu.ip.mogyjib.map_task.remote;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import by.gstu.ip.mogyjib.map_task.models.GooglePlacesApiUrl;
import by.gstu.ip.mogyjib.map_task.models.results.Paginated;
import by.gstu.ip.mogyjib.map_task.remote.services.RequestableGAPI;
import retrofit2.Response;

/**
 * Google Place API searcher, it get the service, which will
 * send request to server. Also it has the on data load complete listener
 * which will called when data load is complete and
 * google api url to which service will send the request.
 *
 * Search process is execute in background task. You can build
 * search URL with different parameters and set different type of
 * services to send request and get different result.
 *
 * @author Evgeniy Shevtsov
 * @version 1.0
 * @see RequestableGAPI,GooglePlacesApiUrl,OnDataSearchCompleteListener
 */
public class GooglePlaceAPISearcher<T extends Paginated> extends AsyncTask<Object,String,List<T>>{
    public static final String TAG = GooglePlaceAPISearcher.class.getSimpleName();

    /**
     * Its listener method will called when data search will complete
     */
    private OnDataSearchCompleteListener<T> mDataLoadCompleteListener;

    /**
     * Service to send the request on some type of API
     */
    private RequestableGAPI<T> mService;

    /**
     * URL to send request
     */
    public GooglePlacesApiUrl mUrl;

    public GooglePlaceAPISearcher(RequestableGAPI<T> service) {
        mService = service;
        mUrl = new GooglePlacesApiUrl(service.getApiName());
    }

    /**
     * Check the response is correct
     *
     * @param response request response
     * @return boolean value - correct or not
     */
    private boolean checkResponse(Response<T> response){
        return response != null
                 && response.isSuccessful()
                && response.body() != null;
    }

    /**
     * Check is next page token is correct
     *
     * @param pagetoken newxt result pagetoken
     * @return boolean value - correct or not
     */
    private boolean checkPagetoken(String pagetoken){
        return pagetoken!=null && !pagetoken.isEmpty();
    }

    //Load data in background
    @Override
    protected List<T> doInBackground(Object... objects) {
        boolean isContinue;

        //collection of result
        Set<T> results = new HashSet<>();

        //search data while it has next page token string
        String pagetoken;
            do {
                //Log request URL
                Log.i(TAG,mUrl.getValue());

                //Send request to URL and get response object
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

                //get data next page token
                pagetoken = response.body().getNextPageToken();

                //check is it has next page token to search and update
                isContinue = checkPagetoken(pagetoken);
                if(isContinue) {
                    mUrl.updateParameter("pagetoken", pagetoken);
                    mUrl.build();
                }

                //add search result
                results.add(response.body());
            } while (isContinue);

            mUrl.removeParameter("pagetoken");

            //return loaded data
        return new ArrayList<>(results);
    }

    //Call when data load is complete and call listener
    @Override
    protected void onPostExecute(List<T> results) {
        mDataLoadCompleteListener.onDataLoadComplete(results);
    }

    /**
     * Build search URL with parameters
     *
     * @param parameters url parameters as map<parameterName,parameterValue>
     * @return reference to this object
     */
    public GooglePlaceAPISearcher<T> buildUrl(Map<String,String> parameters){
        mUrl.setParameters(parameters);
        mUrl.build();
        return this;
    }

    /**
     * Setter for data load complete listener, called when data search is complete
     *
     * @param dataLoadCompleteListener data load complete listener
     * @return reference to this object
     */
    public GooglePlaceAPISearcher<T> setDataLoadCompleteListener(OnDataSearchCompleteListener<T> dataLoadCompleteListener) {
        mDataLoadCompleteListener = dataLoadCompleteListener;
        return this;
    }
}
