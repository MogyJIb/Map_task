package by.gstu.ip.mogyjib.map_task.models;

public class Viewport {
    public Location southwest;
    public Location northeast;

    public Viewport(Location southwest, Location northeast) {
        this.southwest = southwest;
        this.northeast = northeast;
    }
}
