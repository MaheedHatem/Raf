package com.MCIT.raf;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.MCIT.raf.data.Book;
import com.parse.ParseException;

import java.util.ArrayList;

/**
 * Created by Ahmed on 23/04/2016.
 */

/**
 * Created by Ahmed on 23/04/2016.
 */
public class GridHomeAdapter extends BaseAdapter {

    private Context context;
   // ArrayList<String> mtitles0 = new ArrayList<String>();
   // ArrayList<String> mAuthor0 = new ArrayList<String>();

    //ArrayList<Integer> mCover0 = new ArrayList<Integer>();
    //edited by khaled
    //ArrayList<byte[]> mCover0 = new ArrayList<byte[]>();
    //ArrayList<String> mID0 = new ArrayList<String>();
    ArrayList<Book> mBook0 = new ArrayList<Book>();
    //Context mContext = null;


    final int FEATURED = 0;
    final int NEW_RELEASES = 1;
    final int MOST_POPULAR = 2;
    final int [] types = {FEATURED,NEW_RELEASES,MOST_POPULAR};



    //Constructor to initialize values
    public GridHomeAdapter(Context context) {

        this.context = context;
    }

    @Override
    public int getCount() {

        // Number of times getView method call depends upon gridValues.length
        //return mtitles0.size();
        return mBook0.size();
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

            gridView = inflater.inflate(R.layout.grid_item2, null);

            // set value into textview
            TextView txttitle = (TextView) gridView.findViewById(R.id.title);
            TextView txtauthor = (TextView) gridView.findViewById(R.id.author);
            final ImageView imgcover = (ImageView) gridView.findViewById(R.id.cover);

            txttitle.setText(mBook0.get(position).getName());
            txtauthor.setText(mBook0.get(position).getAuthor().getName());
                    //imgcover.setImageResource(mCover0.get(position));
                    //edited by khaled
            AsyncTask<Void,Void,Void> loadimageTask = new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        mBook0.get(position).fetch();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    Bitmap b = BitmapFactory.decodeByteArray(mBook0.get(position).getCover()
                            , 0, mBook0.get(position).getCover().length);
                    if(b!=null)
                    imgcover.setImageBitmap((Bitmap.createScaledBitmap(b, 271, 400, true)));
                }



            };
            if(mBook0.get(position).containsKey("cover")){
                Bitmap b = BitmapFactory.decodeByteArray(mBook0.get(position).getCover()
                        , 0, mBook0.get(position).getCover().length);
                if(b!=null)
                imgcover.setImageBitmap((Bitmap.createScaledBitmap(b, 271, 400, true)));
            }
            else {
                imgcover.setImageResource(R.drawable.white_cover);
                loadimageTask.execute((Void) null);
            }

            gridView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mBook0.get(position).getCover() == null){
                        Snackbar.make(v, "Loading Book", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                        return;
                    }
                    Snackbar.make(v, ((TextView)v.findViewById( R.id.title )).getText(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    Intent mainIntent = new Intent(context.getApplicationContext(),BookActivity.class);

                    mainIntent.putExtra(context.getString(R.string.book_intent_id) , mBook0.get(position).getObjectId());
                    mainIntent.putExtra(context.getString(R.string.book_intent_name) , mBook0.get(position).getName());
                    mainIntent.putExtra(context.getString(R.string.book_intent_author) , mBook0.get(position).getAuthor().getName());
                    mainIntent.putExtra(context.getString(R.string.book_intent_cover) , mBook0.get(position).getCover());
                    mainIntent.putExtra(context.getString(R.string.book_intent_description) , mBook0.get(position).getDescription());
                    mainIntent.putExtra(context.getString(R.string.book_intent_price) , mBook0.get(position).getPrice());

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Pair<View, String> p2 = Pair.create((View)v.findViewById(R.id.cover), "cover");
                        ActivityOptions options = ActivityOptions.
                                makeSceneTransitionAnimation((Activity)context, p2);
                        context.startActivity(mainIntent, options.toBundle());
                    }
                    else
                        context.startActivity(mainIntent);
                }
            });




        } else {

            gridView = (View) convertView;
        }

        return gridView;
    }



    /*public boolean addItem(String title,String author, int cover , int type){


                mtitles0.add(title);
                mAuthor0.add(author);
                mCover0.add(cover);

        notifyDataSetChanged();
        return true;

    }*/

    //edited by khaled
    public void addItem(Book book, int type){
        if (!mBook0.contains(book)) {
            mBook0.add(book);

            notifyDataSetChanged();
        }
    }

    public ArrayList<Book> getList(){
        return mBook0;
    }

}
