
package by.gstu.ip.mogyjib.map_task.models.results;

import java.io.Serializable;
import java.util.Objects;

import by.gstu.ip.mogyjib.map_task.models.pojo.PlaceDetail;

public class PlaceDetailResult implements Serializable,Paginated{

    public PlaceDetail result;
    public String status;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PlaceDetailResult() {
    }


    public PlaceDetailResult(PlaceDetail result, String status) {
        super();
        this.result = result;
        this.status = status;
    }

    @Override
    public String getNextPageToken() {
        return null;
    }

    @Override
    public String toString() {
        return "PlaceDetailResult{" +
                "result=" + result +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceDetailResult that = (PlaceDetailResult) o;
        return Objects.equals(result, that.result) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(result, status);
    }
}
