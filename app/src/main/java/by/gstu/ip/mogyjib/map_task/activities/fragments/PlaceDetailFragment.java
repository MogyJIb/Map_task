package by.gstu.ip.mogyjib.map_task.activities.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import by.gstu.ip.mogyjib.map_task.R;
import by.gstu.ip.mogyjib.map_task.models.pojo.PlaceDetail;

/**
 * Fragment will represent all detail place information.
 *
 * @author Evgeniy Shevtsov
 * @version 1.0
 */
public class PlaceDetailFragment extends Fragment {

    private PlaceDetail mPlace;

    private TextView mPhoneTV,
            mNameTV,
            mAddressTV,
            mmWebsiteTV,
            mLocationTV;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_place_detail, container, false);

        initializeComponents(view);
        updatePlaceInformationFields(mPlace);
        return view;
    }

    /**
     * Find all container views and initialize it
     *
     * @param view container view
     */
    private void initializeComponents(View view) {
        mPhoneTV = view.findViewById(R.id.place_phone);
        mNameTV = view.findViewById(R.id.place_name);
        mAddressTV = view.findViewById(R.id.place_address);
        mmWebsiteTV = view.findViewById(R.id.place_website);
        mLocationTV = view.findViewById(R.id.place_location);
    }

    /**
     * Update all fields with new place information
     *
     * @param placeDetail
     */
    public void updatePlaceInformationFields(PlaceDetail placeDetail) {
        this.mPlace = placeDetail;
        if (mPlace == null)
            return;

        setTextViewData(mPhoneTV, mPlace.international_phone_number);
        setTextViewData(mNameTV, mPlace.name);
        setTextViewData(mAddressTV, mPlace.formatted_address);
        setTextViewData(mmWebsiteTV, mPlace.website);
        setTextViewData(mLocationTV, mPlace.geometry.location.toString());
    }

    /**
     * Method set string text to view if it's not null
     *
     * @param textView text view
     * @param data     string text
     */
    private void setTextViewData(TextView textView, String data) {
        if (data != null && !data.isEmpty())
            textView.setText(data);
        else
            textView.setText(getString(R.string.unknown));
    }
}
