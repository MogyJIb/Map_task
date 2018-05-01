package by.gstu.ip.mogyjib.map_task.models;

public class Photo {
    public int height;
    public int width;
    public String photo_reference;
    public String[] html_attributions;

    public Photo(int height, int width, String photo_reference, String[] html_attributions) {
        this.height = height;
        this.width = width;
        this.photo_reference = photo_reference;
        this.html_attributions = html_attributions;
    }
}
