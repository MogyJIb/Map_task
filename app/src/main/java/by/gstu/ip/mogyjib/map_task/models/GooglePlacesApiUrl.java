package by.gstu.ip.mogyjib.map_task.models;

import java.util.HashMap;
import java.util.Map;

public class GooglePlacesApiUrl {
    public static final String GOOGLE_MAPS_API_URL = "https://maps.googleapis.com/";
    public static final String NEARBY_SEARCH_API_URL = "maps/api/place/nearbysearch/";
    public static final String JSON_TYPE = "json";


    private String baseUrl;
    private Map<String,String> parameters;
    private String apiUrl;
    private String output;

    private StringBuilder value;

    public GooglePlacesApiUrl(){
        this(GOOGLE_MAPS_API_URL, NEARBY_SEARCH_API_URL, JSON_TYPE);
    }

    public GooglePlacesApiUrl(String baseUrl, String apiUrl, String output) {
        this.baseUrl = baseUrl;
        this.apiUrl = apiUrl;
        this.output = output;
        this.value = new StringBuilder("");
        this.parameters = new HashMap<>();
    }

    public void build(){
        value = new StringBuilder("");

        value.append(baseUrl)
                .append(apiUrl)
                .append(output);

        if(!parameters.isEmpty()) {
            value.append('?');
            int i=0;
            for(Map.Entry<String,String> parameter : parameters.entrySet()){
                value.append(parameter.getKey());
                value.append("=");
                value.append(parameter.getValue());

                if(i<parameters.size()-1)
                    value.append('&');

                i++;
            }
        }
    }

    public String getValue() {
        return value.toString();
    }

    public void setParameter(String parameterName, String parameterValue){
        parameters.put(parameterName,parameterValue);
    }

    public void setParameters(Map<String,String> parameters){
        this.parameters.putAll(parameters);
    }

    public void updateParameter(String parameterName, String parameterValue){
        parameters.remove(parameterName);
        parameters.put(parameterName,parameterValue);
    }

    public void removeParameter(String parameterName){
        parameters.remove(parameterName);
    }
}
