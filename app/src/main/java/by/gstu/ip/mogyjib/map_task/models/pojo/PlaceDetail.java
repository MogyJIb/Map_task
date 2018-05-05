
package by.gstu.ip.mogyjib.map_task.models.pojo;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class PlaceDetail implements Serializable{

    public String formattedAddress;
    public Geometry geometry;
    public String icon;
    public String internationalPhoneNumber;
    public String name;
    public String placeId;
    public List<String> types = null;
    public String website;

    public PlaceDetail() {
    }

    public PlaceDetail(String formattedAddress, Geometry geometry, String icon,
                       String internationalPhoneNumber, String name,
                       String placeId, List<String> types, String website) {
        this.formattedAddress = formattedAddress;
        this.geometry = geometry;
        this.icon = icon;
        this.internationalPhoneNumber = internationalPhoneNumber;
        this.name = name;
        this.placeId = placeId;
        this.types = types;
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
                "formattedAddress='" + formattedAddress + '\'' +
                ", geometry=" + geometry +
                ", icon='" + icon + '\'' +
                ", internationalPhoneNumber='" + internationalPhoneNumber + '\'' +
                ", name='" + name + '\'' +
                ", placeId='" + placeId + '\'' +
                ", types=" + types +
                ", website='" + website + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceDetail that = (PlaceDetail) o;
        return Objects.equals(formattedAddress, that.formattedAddress) &&
                Objects.equals(geometry, that.geometry) &&
                Objects.equals(icon, that.icon) &&
                Objects.equals(internationalPhoneNumber, that.internationalPhoneNumber) &&
                Objects.equals(name, that.name) &&
                Objects.equals(placeId, that.placeId) &&
                Objects.equals(types, that.types) &&
                Objects.equals(website, that.website);
    }

    @Override
    public int hashCode() {

        return Objects.hash(formattedAddress, geometry, icon, internationalPhoneNumber, name, placeId, types, website);
    }
}
