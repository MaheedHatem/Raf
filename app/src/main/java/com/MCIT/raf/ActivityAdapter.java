package com.MCIT.raf;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.MCIT.raf.data.Request;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ahmed on 25/04/2016.
 */
public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {
    List<String> mTitiles= new ArrayList<String>();
    List<Date> mStart= new ArrayList<Date>();
    List<Date> mend= new ArrayList<Date>();
    List<String> mStatus= new ArrayList<String>();

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextTitle;
        public TextView mTextStatus;
        public TextView mTextStart;
        public TextView mTextEnd;
        public ViewHolder(View v) {
            super(v);
            mTextTitle =(TextView) v.findViewById(R.id.title);
            mTextStatus =(TextView) v.findViewById(R.id.status);
            mTextStart =(TextView) v.findViewById(R.id.start);
            mTextEnd =(TextView) v.findViewById(R.id.end);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)


    // Create new views (invoked by the layout manager)
    @Override
    public ActivityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextTitle.setText(mTitiles.get(position));
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
        holder.mTextStart.setText(dateFormat.format(mStart.get(position)));
        if (mStatus.get(position) != "buy_request")
            holder.mTextEnd.setText(dateFormat.format(mend.get(position)));
        else
            holder.mTextEnd.setVisibility(View.GONE);
        holder.mTextStatus.setText(mStatus.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mTitiles.size();
    }

    public void addActivity(String title,Date start,Date end, String status){
        mTitiles.add(title);
        mStart.add(start);
        mend.add(end);
        mStatus.add(status);
        notifyDataSetChanged();
    }

    public void addRequest(ArrayList<Request> requests){
        for (Request r : requests)
            addActivity(r.getBook().getName() , (r.getType()=="buy_request")?r.getDeliveryDate(): r.getStartDate()
                    , r.getEndDate() , r.getType());
    }
}