package mymovieeapp.mohamedraafat.example.com.mymovieeapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
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

public class MainActivity extends AppCompatActivity  {
    public String titles;
    public double rates;
    public String release_dates;
    public String overviews;
    public String backdroppath;
    public int ids;
    public String posterimage;
    public String adult;
    public String originaltitle;
    public boolean check_phoneor_tab=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (null!=findViewById(R.id.frgdl)){
            check_phoneor_tab=true;
        }getSupportFragmentManager().
                beginTransaction().
                replace(R.id.frgpl, new fragment_pop(check_phoneor_tab), "frag 1")
                .commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if(id==R.id.popular) {
getSupportFragmentManager().
        beginTransaction().
        replace(R.id.frgpl, new fragment_pop(check_phoneor_tab), "frag 1")
        .commit();
        }

        if(id==R.id.top){

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frgpl,new fragment_top(check_phoneor_tab),"frag 2")
                    .commit();
        }
        if(id==R.id.fav){

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frgpl,new frag_fav(check_phoneor_tab),"frag 3")
                    .commit();
        }
        return  super.onOptionsItemSelected(item);
    }


    public class fragment_pop extends android.support.v4.app.Fragment {

        ArrayList<movie_item> myArrlist=new ArrayList<movie_item>();
        Context mycont= getActivity();;
        RequestQueue rq;
        db database;
        Cursor cr;
        boolean check;
        String urll="http://api.themoviedb.org/3/movie/popular?api_key=57f93999f4575103d984801210045dcd";
        fragment_pop(boolean check){
            this.check=check;
        }
        @Override
        public void onCreate(Bundle SavedInstanceState) {
            super.onCreate(SavedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle SavedInstanceState){
            View v= inflater.inflate(R.layout.popfrag,container,false);
            GridView mygrid=(GridView)v.findViewById(R.id.gridView);
            database =new db(getActivity());
            request_u();
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                check= bundle.getBoolean("check");
            }
            cr=database.selcet_all(database);
            if (cr.moveToFirst()){
                do {
                    if (cr.getString(0).isEmpty()){
                        Toast.makeText(getContext(), "No Data to view , please connect to wifi", Toast.LENGTH_LONG).show();
                    }else {
                        myArrlist.add(new movie_item(cr.getString(0), cr.getString(1), cr.getString(2), cr.getDouble(3), cr.getString(4),cr.getString(5),cr.getInt(6),cr.getString(7),cr.getString(8)));
                    }
                }while (cr.moveToNext());
            }
            mygrid.setAdapter(new firstadapter(myArrlist, getContext()));
            mygrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (check==false) {
                        Intent intent = new Intent(getActivity(), activitydetails.class);
                        intent.putExtra("tit", myArrlist.get(i).title);
                        intent.putExtra("back", myArrlist.get(i).backdrop_path);
                        intent.putExtra("relase date", myArrlist.get(i).release_date);
                        intent.putExtra("vote", myArrlist.get(i).user_rating);
                        intent.putExtra("overview", myArrlist.get(i).overview);
                        intent.putExtra("id", myArrlist.get(i).id);
                        intent.putExtra("adult", myArrlist.get(i).adult);
                        intent.putExtra("poster", myArrlist.get(i).poster_image);
                        intent.putExtra("org title", myArrlist.get(i).original_title);
                        startActivity(intent);
                    }else {
                        titles=myArrlist.get(i).title;
                        backdroppath=myArrlist.get(i).backdrop_path;
                        release_dates=myArrlist.get(i).release_date;
                        rates=myArrlist.get(i).user_rating;
                        ids=myArrlist.get(i).id;
                        overviews=myArrlist.get(i).overview;
                        adult=myArrlist.get(i).adult;
                        posterimage=myArrlist.get(i).poster_image;
                        originaltitle=myArrlist.get(i).original_title;
                        getSupportFragmentManager().
                                beginTransaction().
                                replace(R.id.frgdl, new deltails_frag() , "frag 1")
                                .commit();

                    }
                }
            });

            return  v;
        }
        void request_u(){
            rq= Volley.newRequestQueue(getContext());
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                    urll, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                boolean flag = false;
                                JSONArray jsonArrxx = response.getJSONArray("results");
                                for (int i = 0; i < jsonArrxx.length(); i++) {
                                    JSONObject im = jsonArrxx.getJSONObject(i);
                                    String original_titlex = im.getString("original_title");
                                    String posterx = im.getString("poster_path");
                                    String overviewx = im.getString("overview");
                                    double vote_averagex = im.getDouble("vote_average");
                                    String release_datex = im.getString("release_date");
                                    String adultx=im.getString("adult");
                                    int idx=im.getInt("id");
                                    String titlex=im.getString("title");
                                    String backdrop_pathx=im.getString("backdrop_path");

                                    cr=database.selcet_all(database);
                                    if (cr.moveToFirst()){
                                        do {
                                            if (!cr.getString(0).isEmpty()){
                                                if (cr.getString(0).equals(original_titlex)&& cr.getString(1).equals(posterx)&&cr.getString(2).equals(overviewx)&& cr.getDouble(3)==vote_averagex && cr.getString(4).equals(release_datex)&&cr.getString(5).equals(adultx)&&cr.getInt(6)==idx && cr.getString(7).equals(titlex) &&cr.getString(8).equals(backdrop_pathx) ){
                                                    flag=true;
                                                }
                                            }
                                        }while (cr.moveToNext());
                                    }
                                    if (flag==false) {
                                        database.insert(original_titlex, posterx, overviewx, vote_averagex, release_datex, adultx, idx, backdrop_pathx, titlex);
                                    }


                                    //
                                }
                                //Toast.makeText(getApplicationContext(),"title" , Toast.LENGTH_LONG).show();
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

    public  class fragment_top extends Fragment {
        ArrayList<movie_item> myArrlist=new ArrayList<movie_item>();
        Context mycont= getActivity();;
        RequestQueue rq;
        db database;
        Cursor cr;
        boolean check;
        fragment_top(boolean check){
            this.check=check;
        }
        String urll="http://api.themoviedb.org/3/movie/top_rated?api_key=839399034d750762c56fd8559bf77eff";
        @Override
        public void onCreate(Bundle SavedInstanceState) {
            super.onCreate(SavedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle SavedInstanceState){
            View v= inflater.inflate(R.layout.topfrag,container,false);
            GridView mygrid=(GridView)v.findViewById(R.id.gridView2);
            database =new db(getContext());
            request_u();
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                check= bundle.getBoolean("check");
            }
            cr=database.selcet_all_Rated(database);
            if (cr.moveToFirst()){
                do {
                    if (cr.getString(0).isEmpty()){
                        Toast.makeText(getContext(), "No Data to view , please connect to wifi", Toast.LENGTH_LONG).show();
                    }else {
                        myArrlist.add(new movie_item(cr.getString(0), cr.getString(1), cr.getString(2), cr.getDouble(3), cr.getString(4),cr.getString(5),cr.getInt(6),cr.getString(7),cr.getString(8)));
                    }
                }while (cr.moveToNext());
            }
            mygrid.setAdapter(new firstadapter(myArrlist, getContext()));
            mygrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    if (check == false) {
                        Intent intent = new Intent(getActivity(), activitydetails.class);
                        intent.putExtra("tit", myArrlist.get(i).title);
                        intent.putExtra("back", myArrlist.get(i).backdrop_path);
                        intent.putExtra("relase date", myArrlist.get(i).release_date);
                        intent.putExtra("vote", myArrlist.get(i).user_rating);
                        intent.putExtra("overview", myArrlist.get(i).overview);
                        intent.putExtra("id", myArrlist.get(i).id);
                        intent.putExtra("adult", myArrlist.get(i).adult);
                        intent.putExtra("poster", myArrlist.get(i).poster_image);
                        intent.putExtra("org title", myArrlist.get(i).original_title);
                        startActivity(intent);
                    } else {
                        titles=myArrlist.get(i).title;
                        backdroppath=myArrlist.get(i).backdrop_path;
                        release_dates=myArrlist.get(i).release_date;
                        rates=myArrlist.get(i).user_rating;
                        ids=myArrlist.get(i).id;
                        overviews=myArrlist.get(i).overview;
                        adult=myArrlist.get(i).adult;
                        posterimage=myArrlist.get(i).poster_image;
                        originaltitle=myArrlist.get(i).original_title;
                        getSupportFragmentManager().
                                beginTransaction().
                                replace(R.id.frgdl, new deltails_frag() , "frag 1")
                                .commit();

                    }


                }
            });

            return  v;
        }
        void request_u(){
            rq= Volley.newRequestQueue(getContext());
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                    urll, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                boolean flag = false;
                                JSONArray jsonArrxx = response.getJSONArray("results");
                                for (int i = 0; i < jsonArrxx.length(); i++) {
                                    JSONObject im = jsonArrxx.getJSONObject(i);
                                    String original_titlex = im.getString("original_title");
                                    String posterx = im.getString("poster_path");
                                    String overviewx = im.getString("overview");
                                    double vote_averagex = im.getDouble("vote_average");
                                    String release_datex = im.getString("release_date");
                                    String adultx=im.getString("adult");
                                    int idx=im.getInt("id");
                                    String titlex=im.getString("title");
                                    String backdrop_pathx=im.getString("backdrop_path");
                                    cr=database.selcet_all_Rated(database);
                                    if (cr.moveToFirst()){
                                        do {
                                            if (!cr.getString(0).isEmpty()){
                                                if (cr.getString(0).equals(original_titlex)&& cr.getString(1).equals(posterx)&&cr.getString(2).equals(overviewx)&& cr.getDouble(3)==vote_averagex && cr.getString(4).equals(release_datex)&&cr.getString(5).equals(adultx)&&cr.getInt(6)==idx && cr.getString(7).equals(titlex) &&cr.getString(8).equals(backdrop_pathx) ){
                                                    flag=true;
                                                }
                                            }
                                        }while (cr.moveToNext());
                                    }
                                    if (flag==false) {
                                        database.insert_Rated(original_titlex, posterx, overviewx, vote_averagex, release_datex,adultx,idx,backdrop_pathx,titlex);
                                    }


                                    //
                                }
                                //Toast.makeText(getApplicationContext(),"title" , Toast.LENGTH_LONG).show();
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
        public void newInstance(boolean check) {
            this.check=check;
        }}

    public  class frag_fav extends Fragment {


        db database;
        Cursor cr;
        ArrayList<movie_item> myArrlist=new ArrayList<movie_item>();
        boolean check;
        frag_fav(boolean check){
            this.check=check;
        }
        @Override
        public void onCreate(Bundle SavedInstanceState) {
            super.onCreate(SavedInstanceState);
        }


        @Override
        public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle SavedInstanceState){
            View v= inflater.inflate(R.layout.favfrag,container,false);
            final GridView mygrid=(GridView)v.findViewById(R.id.gridView);
            database =new db(getContext());
            cr=database.selcetfavourit(database);
            if (cr.moveToFirst()){
                do {

                    myArrlist.add(new movie_item(cr.getString(0), cr.getString(1), cr.getString(2), cr.getDouble(3), cr.getString(4),cr.getString(5),cr.getInt(6),cr.getString(7),cr.getString(8)));

                }while (cr.moveToNext());
            }
            mygrid.setAdapter(new firstadapter(myArrlist, getContext()));
            mygrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    if (check == false) {
                        Intent intent = new Intent(getActivity(), activitydetails.class);
                        intent.putExtra("tit", myArrlist.get(i).title);
                        intent.putExtra("back", myArrlist.get(i).backdrop_path);
                        intent.putExtra("relase date", myArrlist.get(i).release_date);
                        intent.putExtra("vote", myArrlist.get(i).user_rating);
                        intent.putExtra("overview", myArrlist.get(i).overview);
                        intent.putExtra("id", myArrlist.get(i).id);
                        intent.putExtra("adult", myArrlist.get(i).adult);
                        intent.putExtra("poster", myArrlist.get(i).poster_image);
                        intent.putExtra("org title", myArrlist.get(i).original_title);
                        startActivity(intent);
                    } else {
                        titles=myArrlist.get(i).title;
                        backdroppath=myArrlist.get(i).backdrop_path;
                        release_dates=myArrlist.get(i).release_date;
                        rates=myArrlist.get(i).user_rating;
                        ids=myArrlist.get(i).id;
                        overviews=myArrlist.get(i).overview;
                        adult=myArrlist.get(i).adult;
                        posterimage=myArrlist.get(i).poster_image;
                        originaltitle=myArrlist.get(i).original_title;
                        getSupportFragmentManager().
                                beginTransaction().
                                replace(R.id.frgdl, new deltails_frag() , "frag 1")
                                .commit();

                    }

                }
            });

            return  v;
        }
        public void newInstance(boolean check) {
            this.check=check;
        }
    }

    /**
     * Created by compu city on 12/12/2016.
     */

    public  class deltails_frag extends Fragment {

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

