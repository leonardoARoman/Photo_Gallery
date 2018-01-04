package model;

import android.graphics.Bitmap;
import android.media.Image;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by leonardoroman on 12/5/17.
 */

public class Photo implements Serializable {
    private String tag, date;
    private ArrayList<String> tags;
    private Bitmap bitmapImage;
    private static int count = 0;
    public Photo(Bitmap bitmapImage, String tag, String date){
        this.bitmapImage = bitmapImage;
        this.tag = tag;
        this.date = date;
        tags = new ArrayList<String>();
        tags.add(tag);
        count++;
    }

    public void addTag(String tag){
        tags.add(tag);
    }
    public ArrayList<String> getTags(){
        return tags;
    }
    public Bitmap getImage(){
        return bitmapImage;
    }
    public String encodedToString(){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmapImage.compress(Bitmap.CompressFormat.JPEG,100,baos);
        String image = Base64.encodeToString(baos.toByteArray(),Base64.DEFAULT);
        return image;
    }
    public String getDate(){
        return date;
    }
    public static int getCount(){return count;}
    public String toString(){
        return bitmapImage+"->"+tag;
    }
    public ArrayList<String> deleteTag(String tag){
        for (int i = 0; i < tags.size(); i++){
            if(tags.get(i).equals(tag)){
                tags.remove(i);
            }
        }
        return tags;
    }
}
