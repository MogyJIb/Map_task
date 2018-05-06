package by.gstu.ip.mogyjib.map_task.activities.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import by.gstu.ip.mogyjib.map_task.R;
import by.gstu.ip.mogyjib.map_task.models.PlaceBasicCollection;
import by.gstu.ip.mogyjib.map_task.models.pojo.PlaceBasic;


public class PlaceListFragment extends Fragment implements PlaceRecyclerViewAdapter.OnListItemClickListener {

    private PlaceBasicCollection mPlaceCollection;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place_list, container, false);

        mAdapter = new PlaceRecyclerViewAdapter(this, mPlaceCollection);
        createRecyclerView(view);

        return view;
    }

    public void updateList(PlaceBasicCollection placeCollection) {
        mPlaceCollection = placeCollection;

        mAdapter = new PlaceRecyclerViewAdapter(this, mPlaceCollection);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void createRecyclerView(View view){
        mRecyclerView = (RecyclerView) view.findViewById(R.id.place_list_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mRecyclerView.setAdapter(mAdapter);

        Drawable dividerDrawable = ContextCompat.getDrawable(getContext(), R.drawable.list_divider);
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(dividerDrawable);

        mRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onListItemClick(PlaceBasic place) {

    }
}