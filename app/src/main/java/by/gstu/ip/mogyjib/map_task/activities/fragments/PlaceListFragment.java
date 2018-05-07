package by.gstu.ip.mogyjib.map_task.activities.fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import by.gstu.ip.mogyjib.map_task.R;
import by.gstu.ip.mogyjib.map_task.activities.PlaceDetailActivity;
import by.gstu.ip.mogyjib.map_task.activities.adapters.PlaceRecyclerViewAdapter;
import by.gstu.ip.mogyjib.map_task.models.PlaceBasicCollection;
import by.gstu.ip.mogyjib.map_task.models.pojo.PlaceBasic;
import static by.gstu.ip.mogyjib.map_task.activities.PlaceDetailActivity.PLACE_ID;

/**
 * Place list fragment contains recycler view and nearby places
 * collection to show as list, places updates by updateList() method.
 * All places shown with basic information as list items, touching
 * by which you can see additional inform about place.
 *
 * @author Evgeniy Shevtsov
 * @version 1.0
 */
public class PlaceListFragment extends Fragment {
    //Nearby places collection
    private PlaceBasicCollection mPlaceCollection;

    //Recycler view and adapter to it to show list items
    private RecyclerView mRecyclerView;
    private PlaceRecyclerViewAdapter mAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PlaceListFragment() {
        mPlaceCollection = new PlaceBasicCollection();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Save instant state of this fragment
        setRetainInstance(true);

        //Inflate layout
        View view = inflater.inflate(R.layout.fragment_place_list, container, false);

        //Create variables
        createAdapter();
        createRecyclerView(view);

        return view;
    }


    /**
     * Create recycler view to show list of places items
     * with specific item layout and divider
     *
     * @param view parent view (recycler view container)
     */
    private void createRecyclerView(View view) {
        mRecyclerView = view.findViewById(R.id.place_list_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mRecyclerView.setAdapter(mAdapter);

        Drawable dividerDrawable = ContextCompat.getDrawable(getContext(), R.drawable.list_divider);
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(dividerDrawable);

        mRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    /**
     * Create new place list adapter and set as on item click listener
     * start of new detail place activity
     */
    private void createAdapter() {
        mAdapter = new PlaceRecyclerViewAdapter((OnItemClickListener<PlaceBasic>) item -> {
            Intent intent = new Intent(getContext(), PlaceDetailActivity.class);
            intent.putExtra(PLACE_ID, item.place_id);
            startActivity(intent);
        }, mPlaceCollection);
    }

    /**
     * By calling this method you can update
     * your list information
     *
     * @param placeCollection nearby places collection
     */
    public void updateList(PlaceBasicCollection placeCollection) {
        mPlaceCollection = placeCollection;

        //Recreate adapter with new items collection
        createAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }
}
