package com.example.raf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Ahmed on 23/04/2016.
 */
public class GridAdapter extends BaseAdapter {

    private Context context;


    //Constructor to initialize values
    public GridAdapter(Context context) {

        this.context        = context;
    }

    @Override
    public int getCount() {

        // Number of times getView method call depends upon gridValues.length
        return titles.length;
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

    public View getView(int position, View convertView, ViewGroup parent) {

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

            textView1.setText(titles[position]);

            // set image based on selected text

            TextView textView2 = (TextView) gridView
                    .findViewById(R.id.author);

            textView2.setText(authors[position]);

            TextView textView3 = (TextView) gridView
                    .findViewById(R.id.price);
            textView3.setText(""+prices[position]);


            ImageView img = (ImageView) gridView.findViewById(R.id.cover);
            img.setImageResource(mCovers[position]);




        } else {

            gridView = (View) convertView;
        }

        return gridView;
    }

    int [] mCovers = {R.drawable.book1,R.drawable.book2,R.drawable.book3,R.drawable.book1,R.drawable.book2,R.drawable.book3};
    private String[] titles = {"Book Thief","Ten Thousand Skies above you","Still Alice","Book Thief","Ten Thousand Skies above you","Still Alice"};
    private String[] authors = {"Markus Zusak","Claudia Gray","Lisa Genova","Claudia Gray","Lisa Genova","Markus Zusak"};
    private int[] prices = {100,200,300,400,500,600};
}