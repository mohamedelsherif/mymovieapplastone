package mymovieeapp.mohamedraafat.example.com.mymovieeapp;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class activitydetails extends FragmentActivity {
    String titles;
    double rates;
    String release_dates;
    String overviews;
    String backdroppath;
    int ids;
    String posterimage;
    String adult;
    String originaltitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_det);
        Bundle c=getIntent().getExtras();
        titles=c.getString("tit");
        rates=c.getDouble("vote");
        release_dates=c.getString("relase date");
        overviews=c.getString("overview");
        backdroppath=c.getString("back");
        ids=c.getInt("id");
        adult=c.getString("adult");
        posterimage=c.getString("poster");
        originaltitle=c.getString("org title");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.det,new Details_fragment(),"det frag")
                .commit();



    }


    /**
     * Created by compu city on 11/12/2016.
     */
    public  class Details_fragment extends Fragment {

        ArrayList<videosAdapter> items=new ArrayList<videosAdapter>();
        ArrayList<reviewClass>items2=new ArrayList<reviewClass>();
        trailer_in_list adapter;
        ListView view;
        db database;
        reviewAdap adapter2;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v= inflater.inflate(R.layout.fra_details,container,false);
            setandget sg=new setandget();
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                titles=sg.getTitles();
                rates=sg.getRates();
                release_dates=sg.getRelease_dates();
                overviews=sg.getOverviews();
                backdroppath=sg.getBackdroppath();
                ids=sg.getIds();
                adult=sg.getAdult();
                posterimage=sg.getPosterimage();
                originaltitle=sg.getOriginaltitle();
            }
            final TextView title=(TextView)v.findViewById(R.id.titleoriginal);
            TextView rate=(TextView)v.findViewById(R.id.rate);
            TextView release_date=(TextView)v.findViewById(R.id.releasedate);
            TextView overview=(TextView)v.findViewById(R.id.overview);
            Button favs=(Button)v.findViewById(R.id.buttonss);
            Button reviews=(Button)v.findViewById(R.id.button2);
            database=new db(getContext());
            ImageView poster=(ImageView)v.findViewById(R.id.imageView);
            view=(ListView)v.findViewById(R.id.listView);
            String urll="https://api.themoviedb.org/3/movie/"+ids+"/videos?api_key=57f93999f4575103d984801210045dcd";
            title.setText(titles);
            rate.setText(String.valueOf(rates));
            release_date.setText(release_dates);
            overview.setText(overviews);
            Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w185//" + backdroppath).into(poster);
            //items is Arraylist of video data
            adapter=new trailer_in_list(items,getContext());
            //items2 is Arraylist of reviews data
            adapter2=new reviewAdap(items2,getContext());
            request_u(urll);
            Toast.makeText(getActivity(), "Please wait till Loading", Toast.LENGTH_LONG).show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    view.setAdapter(adapter);
                }
            }, 2000);

            view.setOnTouchListener(new ListView.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    // i used stackoverflow to get this ...
                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            // Disallow ScrollView to intercept touch events.
                            v.getParent().requestDisallowInterceptTouchEvent(true);
                            break;

                        case MotionEvent.ACTION_UP:
                            // Allow ScrollView to intercept touch events.
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }

                    // Handle ListView touch events.
                    v.onTouchEvent(event);
                    return true;
                }
            });
            view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent s = new
                            Intent(Intent.ACTION_VIEW, Uri.parse("http://www." + items.get(i).site + ".com/watch?v=" + items.get(i).key));
                    startActivity(s);
                }
            });
            favs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db database=new db(getActivity());
                    Cursor cr2;
                    boolean flag = false;
                    cr2=database.selcetfavourit(database);
                    if (cr2.moveToFirst()){
                        do {
                            if (!cr2.getString(0).isEmpty()){
                                if (cr2.getString(0).equals(originaltitle)&& cr2.getString(1).equals(posterimage)&&cr2.getString(2).equals(overviews)&& cr2.getDouble(3)==rates && cr2.getString(4).equals(release_dates)&&cr2.getString(5).equals(adult)&&cr2.getInt(6)==ids && cr2.getString(7).equals(titles) &&cr2.getString(8).equals(backdroppath) ){
                                    flag=true;
                                }
                            }
                        }while (cr2.moveToNext());
                    }
                    if (flag == false) {
                        database.insert_fav(originaltitle, posterimage, overviews, rates, release_dates, adult, ids, backdroppath, titles, getContext());
                        Toast.makeText(getContext(),"Added To Favourites",Toast.LENGTH_LONG).show();
                    }

                }
            });
            reviews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String urll="https://api.themoviedb.org/3/movie/"+ ids+"/reviews?api_key=57f93999f4575103d984801210045dcd";
                    RequestQueue rq;
                    rq= Volley.newRequestQueue(getContext());
                    JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                            urll, null,
                            new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    try {

                                        JSONArray jsonArrxx = response.getJSONArray("results");
                                        for (int i = 0; i < jsonArrxx.length(); i++) {
                                            JSONObject im = jsonArrxx.getJSONObject(i);
                                            String author_rev = im.getString("author");
                                            String content_rev = im.getString("content");
                                            String url_rev=im.getString("url");
                                            items2.add(new reviewClass(author_rev,content_rev,url_rev));
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError e) {
                            e.printStackTrace();

                        }
                    });
                    rq.add(jsonObjReq);
                    Toast.makeText(getActivity(),"Please wait till Loading",Toast.LENGTH_LONG).show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            showdialog();
                        }
                    }, 3000);



                }
            });
            return  v;
        }

        void showdialog(){
            final Dialog dialog=new Dialog(getContext());
            dialog.setContentView(R.layout.listview);
            dialog.setTitle("Reviews");
            ListView listt=(ListView)dialog.findViewById(R.id.listView);
            dialog.show();
            listt.setAdapter(adapter2);
            adapter2.notifyDataSetChanged();

        }
        void request_u(String urll){
            RequestQueue rq;
            rq= Volley.newRequestQueue(getContext());
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                    urll, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                JSONArray jsonArrxx = response.getJSONArray("results");
                                for (int i = 0; i < jsonArrxx.length(); i++) {
                                    JSONObject im = jsonArrxx.getJSONObject(i);
                                    String id = im.getString("id");
                                    String iso_639_1 = im.getString("iso_639_1");
                                    String iso_3166_1 = im.getString("iso_3166_1");
                                    int size = im.getInt("size");
                                    String key = im.getString("key");
                                    String name=im.getString("name");
                                    String site=im.getString("site");
                                    String type=im.getString("type");
                                    items.add(new videosAdapter(id,iso_639_1,iso_3166_1,size,key,name,site,type));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError e) {
                    e.printStackTrace();

                }
            });
            rq.add(jsonObjReq);
        }
    }
}

