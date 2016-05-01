package com.example.raf;

/**
 * Created by Ahmed on 23/04/2016.
 */

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;


public class WhishlistFragment extends Fragment {

    TextView toolbarTitle = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_whishlist, container, false);

        final android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        ((HomeActivity)getActivity()).setSupportActionBar(toolbar);

// loop through all toolbar children right after setting support
// action bar because the text view has no id assigned

// also make sure that the activity has some title here
// because calling setText() with an empty string actually
// removes the text view from the toolbar


        for (int i = 0; i < toolbar.getChildCount(); ++i) {
            View child = toolbar.getChildAt(i);

            // assuming that the title is the first instance of TextView
            // you can also check if the title string matches
            if (child instanceof TextView) {
                toolbarTitle = (TextView)child;
                break;
            }

        }


        GridView gridView = (GridView) v.findViewById(R.id.gridView);

        // Set custom adapter (GridAdapter) to gridview

        gridView.setAdapter(  new GridAdapter(v.getContext()) );

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {



                Snackbar.make(v, ((TextView)v.findViewById( R.id.title )).getText(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();



                Intent mainIntent = new Intent(getActivity(),BookActivity.class);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    toolbarTitle.setTransitionName("title");
                    Pair<View, String> p1 = Pair.create((View)v.findViewById(R.id.title), "title");
                    Pair<View, String> p2 = Pair.create((View)v.findViewById(R.id.cover), "cover");
                    ActivityOptions options = ActivityOptions.
                            makeSceneTransitionAnimation(getActivity(), p2,p1);
                    startActivity(mainIntent, options.toBundle());
                }
                else {
                    getActivity().startActivity(mainIntent);
                }

            }
        });



        return v;
    }



}