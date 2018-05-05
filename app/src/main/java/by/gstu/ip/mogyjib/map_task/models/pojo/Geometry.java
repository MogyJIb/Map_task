
package by.gstu.ip.mogyjib.map_task.models.pojo;


import java.io.Serializable;
import java.util.Objects;

public class Geometry implements Serializable {

    public Location location;
    public Viewport viewport;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Geometry() {
    }

    /**
     * 
     * @param viewport
     * @param location
     */
    public Geometry(Location location, Viewport viewport) {
        this.location = location;
        this.viewport = viewport;
    }

    @Override
    public String toString() {
        return "Geometry{" +
                "location=" + location +
                ", viewport=" + viewport +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Geometry geometry = (Geometry) o;
        return Objects.equals(location, geometry.location) &&
                Objects.equals(viewport, geometry.viewport);
    }

    @Override
    public int hashCode() {

        return Objects.hash(location, viewport);
    }
}
