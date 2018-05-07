package by.gstu.ip.mogyjib.map_task.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import by.gstu.ip.mogyjib.map_task.models.pojo.Location;
import by.gstu.ip.mogyjib.map_task.models.pojo.PlaceBasic;

/**
 * Model which contain nearby places collection
 * and current users location objects
 *
 * @author Evgeniy Shevtsov
 * @version 1.0
 */
public class PlaceBasicCollection implements Serializable {
    public List<PlaceBasic> places;
    public Location currentLocation;

    public PlaceBasicCollection() {
        places = new ArrayList<>();
    }

    public PlaceBasicCollection(Location currentLocation) {
        this();
        this.currentLocation = currentLocation;
    }

    /**
     * Clear collection
     */
    public void clear() {
        places.clear();
    }

    /**
     * Sort places by distance from current location
     */
    public void sort() {
        if (currentLocation == null)
            return;

        Collections.sort(places, (place1, place2) -> {
            float distance1 = place1.getLocation().distanceTo(currentLocation),
                    distance2 = place2.getLocation().distanceTo(currentLocation);
            return Float.compare(distance1, distance2);
        });
    }
}
