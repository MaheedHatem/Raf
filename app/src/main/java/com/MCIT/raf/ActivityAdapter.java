package com.MCIT.raf;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.MCIT.raf.data.Request;

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
    List<String> mType= new ArrayList<String>();

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextTitle;
        public TextView mTextStatus;
        public ImageView mStatusIcon;
        public TextView mTextStart;
        public TextView mTextEnd;
        public ViewHolder(View v) {
            super(v);
            mTextTitle =(TextView) v.findViewById(R.id.title);
            mTextStatus =(TextView) v.findViewById(R.id.status);
            mStatusIcon = (ImageView) v.findViewById(R.id.action_icon);
//            mTextStart =(TextView) v.findViewById(R.id.start);
//            mTextEnd =(TextView) v.findViewById(R.id.end);
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
//        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
//        holder.mTextStart.setText(dateFormat.format(mStart.get(position)));
//        if (mStatus.get(position).equals("borrow_request"))
//            holder.mTextEnd.setText(dateFormat.format(mend.get(position)));
//        else
//            holder.mTextEnd.setVisibility(View.GONE);
        String cType= mType.get(position);
        switch (cType){
            case "borrow_request":
                holder.mStatusIcon.setImageResource(R.drawable.ic_av_timer_black_48dp);
                break;
            case "add_request":
                holder.mStatusIcon.setImageResource(R.drawable.ic_add_circle_outline_black_48dp);
                break;
            case "buy_request":
                holder.mStatusIcon.setImageResource(R.drawable.ic_shopping_cart_black_48dp);
                break;
            default:
                break;
        }

        String cStatus= mStatus.get(position);
        switch (cStatus){
            case "Completed":
                holder.mTextStatus.setTextColor(Color.GREEN);
                break;
            case "Pending":
                holder.mTextStatus.setTextColor(Color.BLUE);
                break;
            case "In Progress":
                holder.mTextStatus.setTextColor(Color.RED);
                break;
            default:
                break;
        }
        holder.mTextStatus.setText(mStatus.get(position));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mTitiles.size();
    }

    public void addActivity(String title,Date start, Date end, String type, String status){
        mTitiles.add(title);
        mStart.add(start);
        mend.add(end);
        mStatus.add(status);
        mType.add(type);
        notifyDataSetChanged();
    }

    public void addRequest(ArrayList<Request> requests){
        for (Request r : requests) {
            if (r.getBook().getName() != null)
                addActivity(r.getBook().getName(), (r.getType().equals("borrow_request")) ? r.getStartDate() :
                        r.getDeliveryDate(), r.getEndDate(), r.getType(), r.getStatus());
            else
                addActivity(r.getType().substring(16), (r.getType().equals("borrow_request")) ? r.getStartDate() :
                        r.getDeliveryDate(), r.getEndDate(), r.getType(), r.getStatus());
        }
    }
}