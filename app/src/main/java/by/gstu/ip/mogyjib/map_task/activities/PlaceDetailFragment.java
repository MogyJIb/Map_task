package by.gstu.ip.mogyjib.map_task.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import by.gstu.ip.mogyjib.map_task.R;
import by.gstu.ip.mogyjib.map_task.models.Place;

public class PlaceDetailFragment extends Fragment {

    public static final String ARG_PLACE = "place";
    private Place mPlace;

    public static PlaceDetailFragment newInstance(Place place) {
        PlaceDetailFragment fragment = new PlaceDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PLACE, place);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPlace = (Place) getArguments().getSerializable(ARG_PLACE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_place_detail, container, false);
    }
}
