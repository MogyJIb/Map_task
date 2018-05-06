
package by.gstu.ip.mogyjib.map_task.models.pojo;


import java.io.Serializable;
import java.util.Objects;

public class Location implements Serializable {

    public double lat;
    public double lng;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Location() {
    }

    /**
     * 
     * @param lng
     * @param lat
     */
    public Location(double lat, double lng) {
        super();
        this.lat = lat;
        this.lng = lng;
    }


    public float distanceTo(double lat,double lng){
        float[] results = new float[1];
        android.location.Location
                .distanceBetween(this.lat,this.lng,lat,lng,results);
        return results[0];
    }

    @Override
    public String toString() {
        return "Location{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Double.compare(location.lat, lat) == 0 &&
                Double.compare(location.lng, lng) == 0;
    }

    @Override
    public int hashCode() {

        return Objects.hash(lat, lng);
    }
}
