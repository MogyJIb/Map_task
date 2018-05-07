package by.gstu.ip.mogyjib.map_task.activities.fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Collection;

import by.gstu.ip.mogyjib.map_task.R;
import by.gstu.ip.mogyjib.map_task.activities.PlaceDetailActivity;
import by.gstu.ip.mogyjib.map_task.locations.LocationUtil;
import by.gstu.ip.mogyjib.map_task.models.PlaceBasicCollection;
import by.gstu.ip.mogyjib.map_task.models.pojo.Location;
import by.gstu.ip.mogyjib.map_task.models.pojo.PlaceBasic;

import static by.gstu.ip.mogyjib.map_task.activities.PlaceDetailActivity.PLACE_ID;


/**
 * Maps fragment contains google map and nearby places
 * collection to show on this map, map updates by updateMap() method.
 * All place shown on map as markers with inform window, touching
 * by which you can see additional inform about place.
 *
 * @author Evgeniy Shevtsov
 * @version 1.0
 */
public class MapsFragment extends Fragment
        implements OnMapReadyCallback,
        GoogleMap.OnInfoWindowClickListener {

    //String tags to save instant state and transfer data
    private static final String TAG = MapsFragment.class.getSimpleName();
    private final String MAP = "map",
            MARKERS = "markers",
            PLACES = "places";

    //Place collection
    private PlaceBasicCollection mPlaceCollection;
    private GoogleMap mMap;

    //Array of marker ids, which are located on map now
    private ArrayList<String> mMarkerIds;

    public MapsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMarkerIds = new ArrayList<>();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        // Inflate the layout for this fragment
        View view = inflater
                .inflate(R.layout.fragment_maps, container, false);

        //Load google map by support fragment
        SupportMapFragment supportMapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.setRetainInstance(true);
        supportMapFragment.getMapAsync(this);

        return view;
    }

    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready for use.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));

        //Set click listener on marker inform window
        mMap.setOnInfoWindowClickListener(this);
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getContext(), R.raw.map_style));

            if (!success) {
                Log.e(TAG, "Map style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find map style. Error: ", e);
        }

        //Check location permission and set it to map
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (LocationUtil.checkPermissions(getActivity())) {
                mMap.setMyLocationEnabled(true);
            }
        }
    }

    /**
     * Show red marker of current user location on map
     *
     * @param lat locations latitude
     * @param lng locations longitude
     */
    private void setCurrLocationMarker(double lat, double lng) {
        LatLng latLng = new LatLng(lat, lng);

        //Generate markers options and add marker
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title("Your position")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    /**
     * Set all places markers to map and add their
     * ids to markers ids collection
     *
     * @param places nearby places collection
     */
    private void setNearbyPlaces(Collection<PlaceBasic> places) {
        for (PlaceBasic place : places) {
            MarkerOptions markerOptions = getMarkerOptions(place);
            mMarkerIds.add(mMap.addMarker(markerOptions).getId());
        }
    }

    /**
     * Generate marker options for one place and return it
     *
     * @param place place object
     * @return  marker options to add on map
     */
    private MarkerOptions getMarkerOptions(PlaceBasic place) {
        Location currLocation = mPlaceCollection.currentLocation;

        return new MarkerOptions()
                .title(place.name)
                .snippet("distance: " + (int) place.getLocation().distanceTo(currLocation) + " m")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                .position(place.getLatlng())
                .alpha(0.8f)
                .flat(true);
    }

    /**
     * By calling this method you can update
     * all map places and markers information
     *
     * @param placeCollection nearby places collection
     */
    public void updateMap(PlaceBasicCollection placeCollection) {
        mPlaceCollection = placeCollection;

        //clear old data
        mMap.clear();
        mMarkerIds.clear();

        //update with new
        setCurrLocationMarker(mPlaceCollection.currentLocation.lat,
                mPlaceCollection.currentLocation.lng);
        setNearbyPlaces(mPlaceCollection.places);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        //Search place object by marker ids index
        int index = mMarkerIds.indexOf(marker.getId());
        if (index < 0 || index >= mPlaceCollection.places.size())
            return;
        PlaceBasic placeBasic = mPlaceCollection.places.get(index);

        //Start new activity with detail information
        Intent intent = new Intent(getContext(), PlaceDetailActivity.class);
        intent.putExtra(PLACE_ID, placeBasic.place_id);
        startActivity(intent);
    }
}
