package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by leonardoroman on 12/5/17.
 */

public class AlbumList implements Serializable {
    private ArrayList<Album> albums;
    public AlbumList(){
        albums = new ArrayList<Album>();
    }
    public void addAlbum(Album album){
        albums.add(album);
    }

    public void deleteAlbum(Album album){
        for (Album i:albums){
            if (album.equals(i)){
                albums.remove(albums.indexOf(i));
            }
        }
    }

    public void setAlbums(ArrayList<Album> album){
        albums = album;
    }

    public ArrayList<Album> getAlbums(){
        return albums;
    }
}
