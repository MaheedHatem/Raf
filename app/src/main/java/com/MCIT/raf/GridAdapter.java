package com.MCIT.raf;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.MCIT.raf.data.Book;

import java.util.ArrayList;

/**
 * Created by Ahmed on 23/04/2016.
 */
public class GridAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Book> books = new ArrayList<Book>();


    //Constructor to initialize values
    public GridAdapter(Context context) {

        this.context        = context;
    }

    @Override
    public int getCount() {

        // Number of times getView method call depends upon gridValues.length
        return books.size();
    }

    @Override
    public Object getItem(int position) {

        return null;
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }


    // Number of times getView method call depends upon gridValues.length

    public View getView(final int position, View convertView, ViewGroup parent) {

        // LayoutInflator to call external grid_item.xml file


        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            // get layout from grid_item.xml ( Defined Below )

            gridView = inflater.inflate( R.layout.grid_item , null);

            // set value into textview

            TextView textView1 = (TextView) gridView
                    .findViewById(R.id.title);

            textView1.setText(books.get(position).getName());

            // set image based on selected text

            TextView textView2 = (TextView) gridView
                    .findViewById(R.id.author);

            textView2.setText(books.get(position).getAuthor().getName());

//            try {
//                Author author = books.get(position).getAuthor().fetch();
//                textView2.setText(author.getName());
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }

//            textView2.setText(books.get(position).getAuthor().getName());

            TextView textView3 = (TextView) gridView
                    .findViewById(R.id.price);
            textView3.setText(""+books.get(position).getPrice());


            ImageView img = (ImageView) gridView.findViewById(R.id.cover);

            Bitmap b = BitmapFactory.decodeByteArray(books.get(position).getCover() , 0 ,
                    books.get(position).getCover().length);
            if(b!=null)
            img.setImageBitmap((Bitmap.createScaledBitmap(b, 271, 400, true)));

            gridView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {





                    Intent mainIntent = new Intent(context.getApplicationContext(),BookActivity.class);

                    mainIntent.putExtra(context.getString(R.string.book_intent_id) , books.get(position).getObjectId());
                    mainIntent.putExtra(context.getString(R.string.book_intent_name) , books.get(position).getName());
                    mainIntent.putExtra(context.getString(R.string.book_intent_author) , books.get(position).getAuthor().getName());
                    mainIntent.putExtra(context.getString(R.string.book_intent_cover) , books.get(position).getCover());
                    mainIntent.putExtra(context.getString(R.string.book_intent_description) , books.get(position).getDescription());
                    mainIntent.putExtra(context.getString(R.string.book_intent_price), books.get(position).getPrice());

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Pair<View, String> p1 = Pair.create((View)v.findViewById(R.id.title), "title");
                        Pair<View, String> p2 = Pair.create((View)v.findViewById(R.id.cover), "cover");
                        ActivityOptions options = ActivityOptions.
                                makeSceneTransitionAnimation((Activity) context, p2,p1);
                        context.startActivity(mainIntent, options.toBundle());
                    }
                    else {
                        context.startActivity(mainIntent);
                    }
                }
            });


        } else {

            gridView = (View) convertView;
            ImageView img = (ImageView) gridView.findViewById(R.id.cover);
            Bitmap b = BitmapFactory.decodeByteArray(books.get(position).getCover() , 0 ,
                    books.get(position).getCover().length);
            if(b!=null)
            img.setImageBitmap((Bitmap.createScaledBitmap(b, 271, 400, true)));
            TextView textView1 = (TextView) gridView
                    .findViewById(R.id.title);
            textView1.setText(books.get(position).getName());
            TextView textView2 = (TextView) gridView
                    .findViewById(R.id.author);
            textView2.setText(books.get(position).getAuthor().getName());

//            try {
//                Author author = books.get(position).getAuthor().fetch();
//                textView2.setText(author.getName());
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
            TextView textView3 = (TextView) gridView
                    .findViewById(R.id.price);
            textView3.setText(""+books.get(position).getPrice());

        }

        return gridView;
    }

    public void addBooks (ArrayList<Book> booksList){
        if (booksList != null) {
//            ArrayList<Book> clone = (ArrayList<Book>) booksList.clone();
            this.books.clear();
            this.books = (ArrayList<Book>) booksList.clone();
        }
        this.notifyDataSetChanged();
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