package by.gstu.ip.mogyjib.map_task.activities.fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import java.util.List;

import by.gstu.ip.mogyjib.map_task.R;
import by.gstu.ip.mogyjib.map_task.activities.PlaceDetailActivity;
import by.gstu.ip.mogyjib.map_task.locations.LocationUtil;
import by.gstu.ip.mogyjib.map_task.models.PlaceBasicCollection;
import by.gstu.ip.mogyjib.map_task.models.pojo.Location;
import by.gstu.ip.mogyjib.map_task.models.pojo.PlaceBasic;

import static by.gstu.ip.mogyjib.map_task.activities.PlaceDetailActivity.PLACE_ID;

public class MapsFragment extends Fragment
        implements OnMapReadyCallback,
        GoogleMap.OnInfoWindowClickListener
{

    private static final String TAG = MapsFragment.class.getSimpleName();
    private final String MAP = "map",
                MARKERS = "markers",
                PLACES = "places";

    private SupportMapFragment mSupportMapFragment;
    private PlaceBasicCollection mPlaceCollection;
    private GoogleMap mMap;
    private ArrayList<String> mMarkerIds;

    public MapsFragment(){
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
        mSupportMapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mSupportMapFragment.setRetainInstance(true);
        mSupportMapFragment.getMapAsync(this);
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

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(LocationUtil.checkPermissions(getActivity())){
                mMap.setMyLocationEnabled(true);
            }
        }
    }

    private void setCurrLocationMarker(double lat,double lng){
        LatLng latLng = new LatLng(lat,lng);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title("Your position")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    private void setNearbyPlaces(Collection<PlaceBasic> places){
        for(PlaceBasic place : places){
            MarkerOptions markerOptions = getMarkerOptions(place);
            mMarkerIds.add(mMap.addMarker(markerOptions).getId());
        }
    }
    private MarkerOptions getMarkerOptions(PlaceBasic place){
        Location currLocation = mPlaceCollection.currentLocation;

        MarkerOptions markerOptions = new MarkerOptions()
                .title(place.name)
                .snippet("distance: "+(int)place.getLocation().distanceTo(currLocation)+" m")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                .position(place.getLatlng())
                .alpha(0.8f)
                .flat(true);

        return markerOptions;
    }


    public void updateMap(PlaceBasicCollection placeCollection){
        mPlaceCollection = placeCollection;

        mMap.clear();
        setCurrLocationMarker(mPlaceCollection.currentLocation.lat,
                mPlaceCollection.currentLocation.lng);
        setNearbyPlaces(mPlaceCollection.places);
    }


    @Override
    public void onInfoWindowClick(Marker marker) {
        int index = mMarkerIds.indexOf(marker.getId());
        if(index<0||index>=mPlaceCollection.places.size())
            return;
        PlaceBasic placeBasic = mPlaceCollection.places.get(index);

        Intent intent = new Intent(getContext(), PlaceDetailActivity.class);
        intent.putExtra(PLACE_ID,placeBasic.place_id);
        startActivity(intent);
    }
}
