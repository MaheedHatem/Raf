package com.MCIT.raf;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.MCIT.raf.data.Book;

/**
 * Created by Ahmed on 25/04/2016.
 */
public class CategoriesFragment extends Fragment {


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
        final CategoriesAdapter mAdapter = new CategoriesAdapter(v.getContext());
        mRecyclerView.setAdapter(mAdapter);

        /*mAdapter.addBook("Book Thief","Markus Zusak",R.drawable.book1,mAdapter.CLASSIC);
        mAdapter.addBook("Ten Thousand Skies Above You","Claudia Gray",R.drawable.book2,mAdapter.CLASSIC);
        mAdapter.addBook("Strill Alice","Lisa Genova",R.drawable.book3,mAdapter.CLASSIC);
        mAdapter.addBook("Ten Thousand Skies Above You","Claudia Gray",R.drawable.book2,mAdapter.HISTORY);
        mAdapter.addBook("Strill Alice","Lisa Genova",R.drawable.book3,mAdapter.HISTORY);
        mAdapter.addBook("Book Thief","Markus Zusak",R.drawable.book1,mAdapter.HISTORY);
        mAdapter.addBook("Strill Alice","Lisa Genova",R.drawable.book3,mAdapter.BIO);
        mAdapter.addBook("Book Thief","Markus Zusak",R.drawable.book1,mAdapter.BIO);
        mAdapter.addBook("Ten Thousand Skies Above You","Claudia Gray",R.drawable.book2,mAdapter.BIO);
        mAdapter.addBook("Ten Thousand Skies Above You","Claudia Gray",R.drawable.book2,mAdapter.SERIES);
        mAdapter.addBook("Strill Alice","Lisa Genova",R.drawable.book3,mAdapter.SERIES);
        mAdapter.addBook("Book Thief","Markus Zusak",R.drawable.book1,mAdapter.SERIES);
        mAdapter.addBook("Strill Alice","Lisa Genova",R.drawable.book3,mAdapter.RELIGION);
        mAdapter.addBook("Book Thief","Markus Zusak",R.drawable.book1,mAdapter.RELIGION);
        mAdapter.addBook("Ten Thousand Skies Above You","Claudia Gray",R.drawable.book2,mAdapter.RELIGION);*/

        Book.getTopCategory(mAdapter , getContext() , mAdapter.CLASSIC);
        Book.getTopCategory(mAdapter , getContext() , mAdapter.HISTORY);
        Book.getTopCategory(mAdapter , getContext() , mAdapter.BIO);
        Book.getTopCategory(mAdapter , getContext() , mAdapter.SERIES);
        Book.getTopCategory(mAdapter , getContext() , mAdapter.RELIGION);

        /*ParseQuery<ParseObject> queryClassicID = ParseQuery.getQuery(getString(R.string.parse_genre));
        queryClassicID.whereEqualTo(getString(R.string.parse_genre_name), "Classic");
        queryClassicID.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject object, ParseException e) {
                if (e == null){
                    Genre genre = (Genre)object;
                    ParseQuery<ParseObject> queryClassics = ParseQuery.getQuery(getString(R.string.parse_book));
                    queryClassics.setLimit(3);
                    queryClassics.include(getString(R.string.parse_book_author));
                    queryClassics.include(getString(R.string.parse_book_genre));
                    queryClassics.whereEqualTo(getString(R.string.parse_book_genre),genre);
                    queryClassics.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e== null) {
                                for (int i = 0; i < objects.size(); i++) {
                                    mAdapter.addBook((Book) objects.get(i), mAdapter.CLASSIC);
                                }
                            }
                        }
                    });
                }
            }
        });

        ParseQuery<ParseObject> queryHistoryID = ParseQuery.getQuery(getString(R.string.parse_genre));
        queryHistoryID.whereEqualTo(getString(R.string.parse_genre_name), "History");
        queryHistoryID.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject object, ParseException e) {
                if (e == null){
                    Genre genre = (Genre)object;
                    ParseQuery<ParseObject> queryHistory = ParseQuery.getQuery(getString(R.string.parse_book));
                    queryHistory.setLimit(3);
                    queryHistory.include(getString(R.string.parse_book_author));
                    queryHistory.include(getString(R.string.parse_book_genre));
                    queryHistory.whereEqualTo(getString(R.string.parse_book_genre),genre);
                    queryHistory.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e== null) {
                                for (int i = 0; i < objects.size(); i++) {
                                    mAdapter.addBook((Book) objects.get(i), mAdapter.HISTORY);
                                }
                            }
                        }
                    });
                }
            }
        });

        ParseQuery<ParseObject> queryBioID = ParseQuery.getQuery(getString(R.string.parse_genre));
        queryBioID.whereEqualTo(getString(R.string.parse_genre_name), "Biography");
        queryBioID.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject object, ParseException e) {
                if (e == null){
                    Genre genre = (Genre)object;
                    ParseQuery<ParseObject> queryBio = ParseQuery.getQuery(getString(R.string.parse_book));
                    queryBio.setLimit(3);
                    queryBio.include(getString(R.string.parse_book_author));
                    queryBio.include(getString(R.string.parse_book_genre));
                    queryBio.whereEqualTo(getString(R.string.parse_book_genre),genre);
                    queryBio.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e== null) {
                                for (int i = 0; i < objects.size(); i++) {
                                    mAdapter.addBook((Book) objects.get(i), mAdapter.BIO);
                                }
                            }
                        }
                    });
                }
            }
        });

        ParseQuery<ParseObject> querySeriesID = ParseQuery.getQuery(getString(R.string.parse_genre));
        querySeriesID.whereEqualTo(getString(R.string.parse_genre_name), "Series");
        querySeriesID.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject object, ParseException e) {
                if (e == null){
                    Genre genre = (Genre)object;
                    ParseQuery<ParseObject> querySeries = ParseQuery.getQuery(getString(R.string.parse_book));
                    querySeries.setLimit(3);
                    querySeries.include(getString(R.string.parse_book_author));
                    querySeries.include(getString(R.string.parse_book_genre));
                    querySeries.whereEqualTo(getString(R.string.parse_book_genre),genre);
                    querySeries.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e== null) {
                                for (int i = 0; i < objects.size(); i++) {
                                    mAdapter.addBook((Book) objects.get(i), mAdapter.SERIES);
                                }
                            }
                        }
                    });
                }
            }
        });

        ParseQuery<ParseObject> queryReligionID = ParseQuery.getQuery(getString(R.string.parse_genre));
        queryReligionID.whereEqualTo(getString(R.string.parse_genre_name), "Religion");
        queryReligionID.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject object, ParseException e) {
                if (e == null){
                    Genre genre = (Genre)object;
                    ParseQuery<ParseObject> queryReligion = ParseQuery.getQuery(getString(R.string.parse_book));
                    queryReligion.setLimit(3);
                    queryReligion.include(getString(R.string.parse_book_author));
                    queryReligion.include(getString(R.string.parse_book_genre));
                    queryReligion.whereEqualTo(getString(R.string.parse_book_genre),genre);
                    queryReligion.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e== null) {
                                for (int i = 0; i < objects.size(); i++) {
                                    mAdapter.addBook((Book) objects.get(i), mAdapter.RELIGION);
                                }
                            }
                        }
                    });
                }
            }
        });*/

        return v;
    }

}