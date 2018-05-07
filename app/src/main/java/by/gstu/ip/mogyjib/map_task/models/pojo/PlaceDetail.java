
package by.gstu.ip.mogyjib.map_task.models.pojo;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class PlaceDetail implements Serializable{

    public String formatted_address;
    public Geometry geometry;
    public String icon;
    public String international_phone_number;
    public String name;
    public String place_id;
    public String website;

    public PlaceDetail() {
    }

    public PlaceDetail(String formatted_address, Geometry geometry, String icon,
                       String international_phone_number, String name,
                       String place_id, String website) {
        this.formatted_address = formatted_address;
        this.geometry = geometry;
        this.icon = icon;
        this.international_phone_number = international_phone_number;
        this.name = name;
        this.place_id = place_id;
        this.website = website;
    }

    public LatLng getLatlng(){
        if(geometry==null || geometry.location == null)
            return null;

        return new LatLng(geometry.location.lat,
                geometry.location.lng);
    }

    @Override
    public String toString() {
        return "PlaceDetail{" +
                "formattedAddress='" + formatted_address + '\'' +
                ", geometry=" + geometry +
                ", icon='" + icon + '\'' +
                ", internationalPhoneNumber='" + international_phone_number + '\'' +
                ", name='" + name + '\'' +
                ", placeId='" + place_id + '\'' +
                ", website='" + website + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceDetail that = (PlaceDetail) o;
        return Objects.equals(formatted_address, that.formatted_address) &&
                Objects.equals(geometry, that.geometry) &&
                Objects.equals(icon, that.icon) &&
                Objects.equals(international_phone_number, that.international_phone_number) &&
                Objects.equals(name, that.name) &&
                Objects.equals(place_id, that.place_id) &&
                Objects.equals(website, that.website);
    }

    @Override
    public int hashCode() {

        return Objects.hash(formatted_address, geometry, icon, international_phone_number, name, place_id, website);
    }
}
