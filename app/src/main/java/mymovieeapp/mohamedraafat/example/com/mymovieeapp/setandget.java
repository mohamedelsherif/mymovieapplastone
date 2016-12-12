package mymovieeapp.mohamedraafat.example.com.mymovieeapp;

import android.util.Log;

/**
 * Created by compu city on 11/12/2016.
 */

public class setandget {
    private String titles;
    private double rates;
    private String release_dates;
    private String overviews;
    private String backdroppath;
    private int ids;
    private String posterimage;
    private String adult;
    private String originaltitle;

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public double getRates() {
        return rates;
    }

    public void setRates(double rates) {
        this.rates = rates;
    }

    public String getRelease_dates() {
        return release_dates;
    }

    public void setRelease_dates(String release_dates) {
        this.release_dates = release_dates;
    }

    public String getOverviews() {
        return overviews;
    }

    public void setOverviews(String overviews) {
        this.overviews = overviews;
        Log.d("works",overviews);
    }

    public String getBackdroppath() {
        return backdroppath;
    }

    public void setBackdroppath(String backdroppath) {
        this.backdroppath = backdroppath;
    }

    public int getIds() {
        return ids;
    }

    public void setIds(int ids) {
        this.ids = ids;
    }

    public String getPosterimage() {
        return posterimage;
    }

    public void setPosterimage(String posterimage) {
        this.posterimage = posterimage;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getOriginaltitle() {
        return originaltitle;
    }

    public void setOriginaltitle(String originaltitle) {
        this.originaltitle = originaltitle;
    }
}
