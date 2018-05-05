
package by.gstu.ip.mogyjib.map_task.models.results;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import by.gstu.ip.mogyjib.map_task.models.pojo.PlaceBasic;

public class PlaceBasicResult implements Serializable, Paginated{

    public String next_page_token;
    public Collection<PlaceBasic> results = null;
    public String status;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PlaceBasicResult() {
    }

    /**
     * 
     * @param results
     * @param status
     * @param next_page_token
     */
    public PlaceBasicResult(String next_page_token, Collection<PlaceBasic> results, String status) {
        super();
        this.next_page_token = next_page_token;
        this.results = results;
        this.status = status;
    }

    @Override
    public String getNextPageToken() {
        return next_page_token;
    }

    @Override
    public String toString() {
        return "PlaceBasicResult{" +
                "next_page_token='" + (next_page_token == null
                ? null : next_page_token.substring(0,10)) + '\'' +
                ", results=" + results.size() +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceBasicResult that = (PlaceBasicResult) o;
        return Objects.equals(next_page_token, that.next_page_token) &&
                Objects.equals(results, that.results) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {

        return Objects.hash(next_page_token, results, status);
    }
}
