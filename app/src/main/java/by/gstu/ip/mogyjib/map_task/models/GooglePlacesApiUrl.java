package by.gstu.ip.mogyjib.map_task.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Class represent URL for Google API, it contain
 * some method to simple build and work with URL
 *
 * @author Evgeniy Shevtsov
 * @version 1.0
 */
public class GooglePlacesApiUrl {
    public static final String GOOGLE_MAPS_API_URL = "https://maps.googleapis.com/maps/api/place/";

    public static final String JSON_TYPE = "json";
    public static final String XML_TYPE = "xml";


    private String baseUrl;
    private Map<String, String> parameters;
    private String apiName;
    private String outputType;

    private StringBuilder value;

    public GooglePlacesApiUrl(String apiName) {
        this(GOOGLE_MAPS_API_URL,
                apiName,
                JSON_TYPE);
    }

    public GooglePlacesApiUrl(String baseUrl, String apiName, String outputType) {
        this.baseUrl = baseUrl;
        this.apiName = apiName;
        this.outputType = outputType;
        this.value = new StringBuilder("");
        this.parameters = new HashMap<>();
    }

    /**
     * Method build URL from all parameters
     * and set it to variable 'value'
     */
    public void build() {
        value = new StringBuilder("");

        value.append(baseUrl)
                .append(apiName)
                .append('/')
                .append(outputType);

        if (!parameters.isEmpty()) {
            value.append('?');
            int i = 0;
            for (Map.Entry<String, String> parameter : parameters.entrySet()) {
                value.append(parameter.getKey());
                value.append("=");
                value.append(parameter.getValue());

                if (i < parameters.size() - 1)
                    value.append('&');

                i++;
            }
        }
    }

    public String getValue() {
        return value.toString();
    }

    public void setParameter(String parameterName, String parameterValue) {
        parameters.put(parameterName, parameterValue);
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters.putAll(parameters);
    }

    public void updateParameter(String parameterName, String parameterValue) {
        parameters.remove(parameterName);
        parameters.put(parameterName, parameterValue);
    }

    public void removeParameter(String parameterName) {
        parameters.remove(parameterName);
    }

    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }
}
