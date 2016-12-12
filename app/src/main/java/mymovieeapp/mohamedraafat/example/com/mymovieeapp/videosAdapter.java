package mymovieeapp.mohamedraafat.example.com.mymovieeapp;


public class videosAdapter {
    String id;
    String iso_639_1;
    String iso_3166_1 ;
    int size ;
    String key;
    String name;
    String site;
    String type;
    videosAdapter(String id,String iso_639_1, String iso_3166_1 , int size , String key, String name, String site, String type){
        this.id=id;
        this.iso_639_1=iso_639_1;
        this.iso_3166_1=iso_3166_1;
        this.size=size;
        this.site=site;
        this.type=type;
        this.key=key;
        this.name=name;


    }
}
