
package by.gstu.ip.mogyjib.map_task.models.pojo;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.Objects;

public class PlaceBasic implements Serializable{

    public Geometry geometry;
    public String name;
    public String place_id;
    /**
     * No args constructor for use in serialization
     * 
     */
    public PlaceBasic() {
    }


    public PlaceBasic(Geometry geometry, String name, String place_id) {
        this.geometry = geometry;
        this.name = name;
        this.place_id = place_id;
    }

    public LatLng getLatlng(){
        if(geometry==null || geometry.location == null)
            return null;

        return new LatLng(geometry.location.lat,
                geometry.location.lng);
    }

    public Location getLocation(){
        if(geometry==null)
            return null;
        return geometry.location;
    }

    @Override
    public String toString() {
        return "PlaceBasic{" +
                "geometry=" + geometry +
                ", name='" + name + '\'' +
                ", placeId='" + place_id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceBasic that = (PlaceBasic) o;
        return Objects.equals(geometry, that.geometry) &&
                Objects.equals(name, that.name) &&
                Objects.equals(place_id, that.place_id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(geometry, name, place_id);
    }
}
