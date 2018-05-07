package by.gstu.ip.mogyjib.map_task.activities.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import by.gstu.ip.mogyjib.map_task.R;
import by.gstu.ip.mogyjib.map_task.activities.fragments.OnItemClickListener;
import by.gstu.ip.mogyjib.map_task.models.PlaceBasicCollection;
import by.gstu.ip.mogyjib.map_task.models.pojo.Location;
import by.gstu.ip.mogyjib.map_task.models.pojo.PlaceBasic;


/**
 * Represent adapter to recycler view to show place items list
 * with specific item view, which handle by this view holder class
 *
 * @author Evgeniy Shevtsov
 * @version 1.0
 */
public class PlaceRecyclerViewAdapter extends RecyclerView.Adapter<PlaceRecyclerViewAdapter.ViewHolder> {
    /**
     * Places collection to show
     */
    public PlaceBasicCollection mPlaceBasicCollection;
    private OnItemClickListener mListener;

    //Constructor
    public PlaceRecyclerViewAdapter(OnItemClickListener listener,
                                    PlaceBasicCollection placeBasicCollection) {
        mListener = listener;
        mPlaceBasicCollection = placeBasicCollection;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_place_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        //Set view holder data and show it
        holder.mPlace = mPlaceBasicCollection.places.get(position);
        holder.mPosition = position + 1;
        holder.setViewData();

        //Set on place item click listener
        holder.mView.setOnClickListener(v -> {
            if (null != mListener) {
                mListener.onItemClick(holder.mPlace);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPlaceBasicCollection.places.size();
    }

    /**
     * Class which will contain view for one place item
     * and show its data
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mItemNumberView;
        public final TextView mNameView;
        public final TextView mDistanceView;
        public PlaceBasic mPlace;
        public int mPosition;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            //Find text field views
            mItemNumberView = view.findViewById(R.id.place_item_number);
            mNameView = view.findViewById(R.id.place_item_name);
            mDistanceView = view.findViewById(R.id.place_item_distance);
        }

        /**
         * Show place information in view
         */
        public void setViewData() {
            if (mPlace == null)
                return;

            mItemNumberView.setText(mPosition + "");
            mNameView.setText(mPlace.name);

            Location currLocation = mPlaceBasicCollection.currentLocation;
            mDistanceView.setText((int) mPlace.getLocation()
                    .distanceTo(currLocation) + " m");
        }
    }
}
