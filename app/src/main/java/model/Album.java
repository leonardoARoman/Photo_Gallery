package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by leonardoroman on 12/5/17.
 */

public class Album implements Serializable {
    private ArrayList<Photo> photos;
    private String albumName;
    private static int count = 0;

    public Album(String albumName){
        photos = new ArrayList<Photo>();
        this.albumName = albumName;
        count++;
    }
    public void setAlbumName(String albumName){
        this.albumName = albumName;
    }
    public void setPhotos(ArrayList<Photo> photos){ this.photos = photos; }
    public void addPhotoToAlbum(Photo photo){
        photos.add(photo);
    }
    public String toString(){
        return albumName;
    }
    public Photo getPhoto(int index){
        return photos.get(index);
    }
    public int getAlbumSize(){
        return photos.size();
    }
    public static int getCount() { return count; }
    public ArrayList<Photo> getAlbum(){
        return photos;
    }
    public void deletePhoto(int index){
        photos.remove(index);
    }

}
