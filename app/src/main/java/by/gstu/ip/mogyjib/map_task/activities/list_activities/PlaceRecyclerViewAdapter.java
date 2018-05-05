package by.gstu.ip.mogyjib.map_task.activities.list_activities;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import by.gstu.ip.mogyjib.map_task.R;
import by.gstu.ip.mogyjib.map_task.activities.list_activities.PlaceListFragment.OnListFragmentInteractionListener;
import by.gstu.ip.mogyjib.map_task.activities.list_activities.DummyContent.DummyItem;

import java.util.List;

public class PlaceRecyclerViewAdapter extends RecyclerView.Adapter<PlaceRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public PlaceRecyclerViewAdapter(List<DummyItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_place_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mItemNumberView;
        public final TextView mNameView;
        public final TextView mDistanceView;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            mItemNumberView = (TextView) view.findViewById(R.id.place_item_number);
            mNameView = (TextView) view.findViewById(R.id.place_item_name);
            mDistanceView = (TextView) view.findViewById(R.id.place_item_distance);
        }

        @Override
        public String toString() {
            return super.toString() + " '" +  "'";
        }
    }
}
