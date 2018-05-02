package by.gstu.ip.mogyjib.map_task.tests;

import org.junit.Assert;
import org.junit.Test;

import by.gstu.ip.mogyjib.map_task.models.GooglePlacesApiUrl;

import static org.junit.Assert.assertEquals;

public class GooglePlacesApiUrlTest {
    @Test
    public void build_no_parameter() {
        GooglePlacesApiUrl url = new GooglePlacesApiUrl();
        url.build();

        String expected =  "https://maps.googleapis.com/maps/api/place/nearbysearch/json";

        Assert.assertEquals(expected,url.getValue());
    }

    @Test
    public void build_with_parameter() {
        GooglePlacesApiUrl url = new GooglePlacesApiUrl();
        url.setParameter("location","52.4547,30.9348");
        url.build();

        String expected =  "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=52.4547,30.9348";

        Assert.assertEquals(expected,url.getValue());
    }

    @Test
    public void build_with_parameters() {
        GooglePlacesApiUrl url = new GooglePlacesApiUrl();
        url.setParameter("location","52.4547,30.9348");
        url.setParameter("key","key_app");
        url.build();

        String expected =  "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=52.4547,30.9348&key=key_app";

        Assert.assertEquals(expected,url.getValue());
    }

    @Test
    public void remove_parameter() {
        GooglePlacesApiUrl url = new GooglePlacesApiUrl();
        url.setParameter("location","52.4547,30.9348");
        url.removeParameter("location");
        url.build();

        String expected =  "https://maps.googleapis.com/maps/api/place/nearbysearch/json";

        Assert.assertEquals(expected,url.getValue());
    }

    @Test
    public void update_parameter() {
        GooglePlacesApiUrl url = new GooglePlacesApiUrl();
        url.setParameter("location","52.4547,30.9348");
        url.updateParameter("location","52,30");
        url.build();

        String expected =  "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=52,30";

        Assert.assertEquals(expected,url.getValue());
    }

    @Test
    public void remove_pagetoken_parameter(){

        GooglePlacesApiUrl url = new GooglePlacesApiUrl();
        url.setParameter("location","52.4547,30.9348");
        url.setParameter("key","key_app");
        url.setParameter("pagetoken","CqQCFwEAAJ");

        url.removeParameter("pagetoken");
        url.build();

        String expected =  "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=52.4547,30.9348&key=key_app";
        Assert.assertEquals(expected,url.getValue());
    }

    @Test
    public void remove_key_parameter(){

        GooglePlacesApiUrl url = new GooglePlacesApiUrl();
        url.setParameter("location","52.4547,30.9348");
        url.setParameter("key","key_app");
        url.setParameter("pagetoken","CqQCFwEAAJ");

        url.removeParameter("key");
        url.build();

        String expected =  "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=52.4547,30.9348&pagetoken=CqQCFwEAAJ";
        Assert.assertEquals(expected,url.getValue());
    }
}
