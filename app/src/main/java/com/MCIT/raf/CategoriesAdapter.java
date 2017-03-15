package com.MCIT.raf;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.MCIT.raf.data.Book;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    static Context mContext= null;
    public static final int CLASSIC = 0;
    public static final int HISTORY = 1;
    public static final int BIO = 2;
    public static final int SERIES = 3;
    public static final int RELIGION = 4;
    final int [] types = {CLASSIC,HISTORY,BIO,SERIES, RELIGION};
    static public GridHomeAdapter gAdapter0;
    static public GridHomeAdapter gAdapter1;
    static public GridHomeAdapter gAdapter2;
    static public GridHomeAdapter gAdapter3;
    static public GridHomeAdapter gAdapter4;








    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        public TextView txt;
        public GridView gridView;
        public TextView tmore;

        public ViewHolder(View v) {
            super(v);
            txt = (TextView) v.findViewById(R.id.left_text);
            gridView = (GridView) v.findViewById(R.id.grid_home);
            tmore = (TextView) v.findViewById(R.id.right_more);

        }


    }

    public CategoriesAdapter(Context c){
        this.mContext = c;
        gAdapter0 = new GridHomeAdapter(c);
        gAdapter1 = new GridHomeAdapter(c);
        gAdapter2 = new GridHomeAdapter(c);
        gAdapter3 = new GridHomeAdapter(c);
        gAdapter4 = new GridHomeAdapter(c);

    }

    // Provide a suitable constructor (depends on the kind of dataset)


    // Create new views (invoked by the layout manager)
    @Override
    public CategoriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
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

//        holder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            public void onItemClick(AdapterView<?> parent, View v,
//                                    int position, long id) {
//
//                Snackbar.make(v, ((TextView)v.findViewById( R.id.title )).getText(), Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//
//                Intent mainIntent = new Intent(mContext.getApplicationContext(),BookActivity.class);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    Pair<View, String> p2 = Pair.create((View)v.findViewById(R.id.cover), "cover");
//                    ActivityOptions options = ActivityOptions.
//                            makeSceneTransitionAnimation((Activity)mContext, p2);
//                    mContext.startActivity(mainIntent, options.toBundle());
//                }
//                else
//                    mContext.startActivity(mainIntent);
//
//            }
//        });


        switch (position){
            case CLASSIC:
                holder.gridView.setAdapter(gAdapter0);
                holder.txt.setText("Classic");
                holder.tmore.setTag("Classic");
                break;
            case HISTORY:
                holder.txt.setText("History");
                holder.gridView.setAdapter(gAdapter1);
                holder.tmore.setTag("History");
                break;
            case BIO:
                holder.txt.setText("Biography");
                holder.gridView.setAdapter(gAdapter2);
                holder.tmore.setTag("Biography");
                break;
            case SERIES:
                holder.txt.setText("Series");
                holder.gridView.setAdapter(gAdapter3);
                holder.tmore.setTag("Series");
                break;
            case RELIGION:
                holder.txt.setText("Religion");
                holder.gridView.setAdapter(gAdapter4);
                holder.tmore.setTag("Religion");
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

            case CLASSIC:
                gAdapter0.addItem(title,author,cover,type);
                break;
            case HISTORY:
                gAdapter1.addItem(title,author,cover,type);
                break;
            case BIO:
                gAdapter2.addItem(title,author,cover,type);
                break;
            case SERIES:
                gAdapter3.addItem(title,author,cover,type);
                break;
            case RELIGION:
                gAdapter4.addItem(title,author,cover,type);
                break;
        }
    }*/

    public void addBook(Book book, int type){
        switch(type){

            case CLASSIC:
                gAdapter0.addItem(book ,type);
                break;
            case HISTORY:
                gAdapter1.addItem(book,type);
                break;
            case BIO:
                gAdapter2.addItem(book,type);
                break;
            case SERIES:
                gAdapter3.addItem(book,type);
                break;
            case RELIGION:
                gAdapter4.addItem(book,type);
                break;
        }
    }


}




