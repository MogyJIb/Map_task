package by.gstu.ip.mogyjib.map_task.activities.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import by.gstu.ip.mogyjib.map_task.R;
import by.gstu.ip.mogyjib.map_task.models.OnItemClickListener;
import by.gstu.ip.mogyjib.map_task.models.PlaceBasicCollection;
import by.gstu.ip.mogyjib.map_task.models.pojo.Location;
import by.gstu.ip.mogyjib.map_task.models.pojo.PlaceBasic;

public class PlaceRecyclerViewAdapter extends RecyclerView.Adapter<PlaceRecyclerViewAdapter.ViewHolder> {
    public PlaceBasicCollection mPlaceBasicCollection;
    private OnItemClickListener mListener;

    public PlaceRecyclerViewAdapter(OnItemClickListener listener,
                                    PlaceBasicCollection placeBasicCollection) {
        mListener = listener;
        mPlaceBasicCollection = placeBasicCollection;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_place_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mPlace = mPlaceBasicCollection.places.get(position);
        holder.mPosition = position+1;
        holder.setViewData();

        holder.mView.setOnClickListener(v -> {
            if (null != mListener) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                mListener.onItemClick(holder.mPlace);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPlaceBasicCollection.places.size();
    }

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

            mItemNumberView = (TextView) view.findViewById(R.id.place_item_number);
            mNameView = (TextView) view.findViewById(R.id.place_item_name);
            mDistanceView = (TextView) view.findViewById(R.id.place_item_distance);
        }

        public void setViewData(){
            if(mPlace==null)
                return;

            mItemNumberView.setText(mPosition+"");
            mNameView.setText(mPlace.name);

            Location currLocation = mPlaceBasicCollection.currentLocation;
            mDistanceView.setText((int)mPlace.getLocation()
                    .distanceTo(currLocation)+" m");
        }
    }


}
