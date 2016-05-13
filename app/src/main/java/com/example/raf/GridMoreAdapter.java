package com.example.raf;

import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.raf.data.Book;

import java.util.ArrayList;

public class GridMoreAdapter extends RecyclerView.Adapter<GridMoreAdapter.ViewHolder> {


    private ArrayList<Book> books = new ArrayList<>();

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        public TextView textView1;
        public TextView textView2;
        public TextView textView3;
        public ImageView img;


        public ViewHolder(View v) {
            super(v);
            textView1 = (TextView) v
                    .findViewById(R.id.title);
            textView2 = (TextView) v
                    .findViewById(R.id.author);
            textView3 = (TextView) v
                    .findViewById(R.id.price);
            img = (ImageView) v.findViewById(R.id.cover);

        }


    }


    // Provide a suitable constructor (depends on the kind of dataset)


    // Create new views (invoked by the layout manager)
    @Override
    public GridMoreAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element



        holder.textView1.setText(books.get(position).getName());
        holder.textView2.setText(books.get(position).getAuthor().getName());
        holder.textView3.setText(""+books.get(position).getPrice());

        holder.img.setImageBitmap(BitmapFactory.decodeByteArray(books.get(position).getCover()
                                        , 0 , books.get(position).getCover().length));


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return books.size();
    }

    public void addBook(Book b){
        books.add(b);
        notifyDataSetChanged();
    }





//    int [] mCovers = {R.drawable.book1,R.drawable.book2,R.drawable.book3,R.drawable.book1,R.drawable.book2,R.drawable.book3};
//    private String[] titles = {"Book Thief","Ten Thousand Skies above you","Still Alice","Book Thief","Ten Thousand Skies above you","Still Alice"};
//    private String[] authors = {"Markus Zusak","Claudia Gray","Lisa Genova","Claudia Gray","Lisa Genova","Markus Zusak"};
//    private int[] prices = {100,200,300,400,500,600};
}




