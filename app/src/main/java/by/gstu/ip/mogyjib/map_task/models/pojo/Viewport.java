
package by.gstu.ip.mogyjib.map_task.models.pojo;


import java.io.Serializable;
import java.util.Objects;

public class Viewport implements Serializable{

    public Location northeast;
    public Location southwest;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Viewport() {
    }

    /**
     * 
     * @param southwest
     * @param northeast
     */
    public Viewport(Location northeast, Location southwest) {
        super();
        this.northeast = northeast;
        this.southwest = southwest;
    }

    @Override
    public String toString() {
        return "Viewport{" +
                "northeast=" + northeast +
                ", southwest=" + southwest +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Viewport viewport = (Viewport) o;
        return Objects.equals(northeast, viewport.northeast) &&
                Objects.equals(southwest, viewport.southwest);
    }

    @Override
    public int hashCode() {

        return Objects.hash(northeast, southwest);
    }
}
