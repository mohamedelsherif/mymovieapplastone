package mymovieeapp.mohamedraafat.example.com.mymovieeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class trailer_in_list extends BaseAdapter {
    Context con;
    ArrayList<videosAdapter> movi;

    public trailer_in_list(ArrayList<videosAdapter> movies, Context context) {
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
        convertView = LayoutInflater.from(con).inflate(R.layout.listaaa,null);
        TextView imageView1 = (TextView) convertView.findViewById(R.id.play);
        return convertView;
    }

}
