package by.gstu.ip.mogyjib.map_task.models;

public class MyPlaces {
    public String next_page_token;
    public String[] html_attributions;
    public String status;
    public Result[] results;

    public MyPlaces(String next_page_token, Result[] results, String[] html_attributions, String status) {
        this.next_page_token = next_page_token;
        this.results = results;
        this.html_attributions = html_attributions;
        this.status = status;
    }
}
