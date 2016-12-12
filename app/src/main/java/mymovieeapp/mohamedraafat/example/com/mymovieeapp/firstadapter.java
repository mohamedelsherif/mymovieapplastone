package mymovieeapp.mohamedraafat.example.com.mymovieeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by compu city on 03/12/2016.
 */
public class firstadapter extends BaseAdapter {
    Context con;
    ArrayList<movie_item> movi;

    public firstadapter(ArrayList<movie_item> movies, Context context) {
        this.movi = movies;
        this.con= context;
    }

    @Override
    public int getCount() {
        return movi.size();
    }

    @Override
    public Object getItem(int position) {
        return movi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(con).inflate(R.layout.gridviewimg,null);
        ImageView imageView1 = (ImageView) convertView.findViewById(R.id.imageView1);
        Picasso.with(con).load("http://image.tmdb.org/t/p/w185//"+movi.get(position).poster_image).resize(300,300).into(imageView1);
        return convertView;
    }
}




