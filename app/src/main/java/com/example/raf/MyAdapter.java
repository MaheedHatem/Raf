package com.example.raf;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.example.raf.data.Book;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


    final int FEATURED = 0;
    static Context mContext= null;
    final int NEW_RELEASES = 1;
    final int MOST_POPULAR = 2;
    final int [] types = {FEATURED,NEW_RELEASES,MOST_POPULAR};
    static public GridHomeAdapter gAdapter0;
    static public GridHomeAdapter gAdapter1;
    static public GridHomeAdapter gAdapter2;







    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        public TextView txt;
        public GridView gridView;

        public ViewHolder(View v) {
            super(v);
            txt = (TextView) v.findViewById(R.id.left_text);
            gridView = (GridView) v.findViewById(R.id.grid_home);

        }


    }

    public MyAdapter(Context c){
        this.mContext = c;
        gAdapter0 = new GridHomeAdapter(c);
        gAdapter1 = new GridHomeAdapter(c);
        gAdapter2 = new GridHomeAdapter(c);
    }

    // Provide a suitable constructor (depends on the kind of dataset)


    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        /*holder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Snackbar.make(v, ((TextView)v.findViewById( R.id.title )).getText(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent mainIntent = new Intent(mContext.getApplicationContext(),BookActivity.class);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Pair<View, String> p2 = Pair.create((View)v.findViewById(R.id.cover), "cover");
                    ActivityOptions options = ActivityOptions.
                            makeSceneTransitionAnimation((Activity)mContext, p2);
                    mContext.startActivity(mainIntent, options.toBundle());
                }
                else
                mContext.startActivity(mainIntent);

            }
        });*/


        switch (position){
            case FEATURED:
                holder.gridView.setAdapter(gAdapter0);
                holder.txt.setText("Featured");
                break;
            case NEW_RELEASES:
                holder.txt.setText("New Releases");
                holder.gridView.setAdapter(gAdapter1);
                break;
            case MOST_POPULAR:
                holder.txt.setText("Most Popular");
                holder.gridView.setAdapter(gAdapter2);
                break;
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return types.length;
    }

   /*public void addBook(String title,String author, int cover,int type){

        switch(type){

            case FEATURED:
                gAdapter0.addItem(title,author,cover,type);
                break;
            case NEW_RELEASES:
                gAdapter1.addItem(title,author,cover,type);
                break;
            case MOST_POPULAR:
                gAdapter2.addItem(title,author,cover,type);
                break;
        }
    }*/

    //edited by khaled
    public void addBook( Book book ,int type){

        switch(type){

            case FEATURED:
                gAdapter0.addItem(book , type);
                break;
            case NEW_RELEASES:
                gAdapter1.addItem( book , type);
                break;
            case MOST_POPULAR:
                gAdapter2.addItem(book ,type);
                break;
        }
    }

}




