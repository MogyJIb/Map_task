
package by.gstu.ip.mogyjib.map_task.models.pojo;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class PlaceBasic implements Serializable{

    public Geometry geometry;
    public String name;
    public String placeId;
    /**
     * No args constructor for use in serialization
     * 
     */
    public PlaceBasic() {
    }


    public PlaceBasic(Geometry geometry, String name, String placeId) {
        this.geometry = geometry;
        this.name = name;
        this.placeId = placeId;
    }

    public LatLng getLatlng(){
        if(geometry==null || geometry.location == null)
            return null;

        return new LatLng(geometry.location.lat,
                geometry.location.lng);
    }

    @Override
    public String toString() {
        return "PlaceBasic{" +
                "geometry=" + geometry +
                ", name='" + name + '\'' +
                ", placeId='" + placeId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceBasic that = (PlaceBasic) o;
        return Objects.equals(geometry, that.geometry) &&
                Objects.equals(name, that.name) &&
                Objects.equals(placeId, that.placeId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(geometry, name, placeId);
    }
}
