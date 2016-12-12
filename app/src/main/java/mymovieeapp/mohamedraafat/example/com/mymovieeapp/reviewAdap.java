package mymovieeapp.mohamedraafat.example.com.mymovieeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by compu city on 03/12/2016.
 */
public class reviewAdap extends BaseAdapter {
    Context con;
    ArrayList<reviewClass> reviw;

    public reviewAdap(ArrayList<reviewClass> movies, Context context) {
        this.reviw = movies;
        this.con= context;
    }

    @Override
    public int getCount() {
        return reviw.size();
    }

    @Override
    public Object getItem(int position) {
        return reviw.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(con).inflate(R.layout.review_item,null);
        TextView author = (TextView) convertView.findViewById(R.id.author);
        TextView content = (TextView) convertView.findViewById(R.id.content);
        TextView url = (TextView) convertView.findViewById(R.id.url);
        author.setText(reviw.get(position).author);
        content.setText(reviw.get(position).content);
        url.setText(reviw.get(position).url_rev);
        return convertView;
    }

}
