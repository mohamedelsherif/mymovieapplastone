package mymovieeapp.mohamedraafat.example.com.mymovieeapp;


public class movie_item {
    public String original_title;
    public String poster_image;
    public String overview ;
    public double user_rating ;
    public String release_date;
    public String adult;
    public int id;
    public String title;
    public String backdrop_path;


    public movie_item(String original_title,String poster_image, String overview,double user_rating,String release_date,String adult,int id,String title,String backdrop_path){
        this.original_title=original_title;
        this.poster_image=poster_image;
        this.overview=overview;
        this.user_rating=user_rating;
        this.release_date=release_date;
        this.adult=adult;
        this.id=id;
        this.title=title;
        this.backdrop_path=backdrop_path;

    }

}

