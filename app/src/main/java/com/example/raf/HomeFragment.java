package com.example.raf;

/**
 * Created by Ahmed on 22/04/2016.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.raf.data.Book;


public class HomeFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView mRecyclerView = (RecyclerView) v.findViewById(R.id.recycle_home);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(v.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        final MyAdapter mAdapter = new MyAdapter(v.getContext());
        mRecyclerView.setAdapter(mAdapter);


        /*Bitmap bitmap = ((BitmapDrawable)getResources().getDrawable(R.drawable.book1)).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitmapdata = stream.toByteArray();



        mAdapter.addBook("Book Thief","Markus Zusak", bitmapdata,0);
        /*mAdapter.addBook("Ten Thousand Skies Above You","Claudia Gray",R.drawable.book2,0);
        mAdapter.addBook("Strill Alice","Lisa Genova",R.drawable.book3,0);
        mAdapter.addBook("Ten Thousand Skies Above You","Claudia Gray",R.drawable.book2,1);
        mAdapter.addBook("Strill Alice","Lisa Genova",R.drawable.book3,1);
        mAdapter.addBook("Book Thief","Markus Zusak",R.drawable.book1,1);
        mAdapter.addBook("Strill Alice","Lisa Genova",R.drawable.book3,2);;
        mAdapter.addBook("Book Thief","Markus Zusak",R.drawable.book1,2);
        mAdapter.addBook("Ten Thousand Skies Above You","Claudia Gray",R.drawable.book2,2);*/

        //edited by khaled

        /*ParseQuery<ParseObject> query1 = ParseQuery.getQuery(getString(R.string.parse_book));
        query1.include(getString(R.string.parse_book_author));
        query1.setLimit(3);
        query1.whereEqualTo(getString(R.string.parse_book_featured), true);
        query1.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e== null){
                    for (int i=0; i<objects.size();i++) {
                        Book b = (Book)objects.get(i);
                        //b.initialize();
                        mAdapter.addBook( b, 0);
                    }
                }
            }
        });*/

        Book.getTopHome(mAdapter , getContext() , 0);
        Book.getTopHome(mAdapter , getContext() , 1);
        Book.getTopHome(mAdapter , getContext() , 2);

        /*ParseQuery<ParseObject> query2 = ParseQuery.getQuery(getString(R.string.parse_book));
        query2.include(getString(R.string.parse_book_author));
        query2.setLimit(3);
        query2.whereEqualTo(getString(R.string.parse_book_new), true);
        query2.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e== null){
                    for (int i=0; i<objects.size();i++) {
                        Book b = (Book)objects.get(i);
                        mAdapter.addBook( b, 1);
                    }
                }
            }
        });*/

        /*ParseQuery<ParseObject> query3 = ParseQuery.getQuery(getString(R.string.parse_book));
        query3.include(getString(R.string.parse_book_author));
        query3.setLimit(3);
        query3.whereEqualTo(getString(R.string.parse_book_popular), true);

        query3.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e== null){
                    for (ParseObject o: objects){
                        Book b = (Book) o;
                        mAdapter.addBook(b, 2);
                    }

                }
            }
        });*/


        return v;
    }

}