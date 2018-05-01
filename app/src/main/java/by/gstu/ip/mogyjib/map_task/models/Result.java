package by.gstu.ip.mogyjib.map_task.models;

public class Result {
    public Photo[] photos;
    public String id;
    public String place_id;
    public String icon;
    public String vicinity;
    public String scope;
    public String name;
    public String rating;
    public String[] types;
    public String reference;
    public Geometry geometry;

    public Result(Photo[] photos,
                  String id,
                  String place_id,
                  String icon,
                  String vicinity,
                  String scope,
                  String name,
                  String rating,
                  String[] types,
                  String reference,
                  Geometry geometry) {

        this.photos = photos;
        this.id = id;
        this.place_id = place_id;
        this.icon = icon;
        this.vicinity = vicinity;
        this.scope = scope;
        this.name = name;
        this.rating = rating;
        this.types = types;
        this.reference = reference;
        this.geometry = geometry;
    }
}
