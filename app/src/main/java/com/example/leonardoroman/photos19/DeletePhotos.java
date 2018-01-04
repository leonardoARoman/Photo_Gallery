package com.example.leonardoroman.photos19;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;

import dataBaseManager.DataBaseManagerHelper;
import model.Album;
import model.Photo;

public class DeletePhotos extends AppCompatActivity {
    private GridView photosCheckBox;
    private ArrayAdapter<Photo> adapter;
    private ArrayList<Photo> selectedPhotos;
    private ArrayList<Photo> photos;
    DataBaseManagerHelper dataBaseManagerHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_photos);
        dataBaseManagerHelper = new DataBaseManagerHelper(this);
        setFields();
        listViewListener();
    }
    public void deleteAlbums(View view){
        ArrayList<String> photosToDelete = new ArrayList<String>();

        for(int i = 0; i < selectedPhotos.size(); i++){
            photosToDelete.add(selectedPhotos.get(i).encodedToString());
            photos.remove(selectedPhotos.get(i));
        }
        dataBaseManagerHelper.deletePhoto(photosToDelete);
        setAdapter();
        finish();
    }
    //********************************** HELPER METHODS **********************************
    private void setFields(){
        Intent intent = getIntent();
        final int album = intent.getIntExtra("album",0);
        if(photos == null) {
            photos = Photos.getAlbums().get(album).getAlbum();
        }
        selectedPhotos = new ArrayList<Photo>();
        photosCheckBox = (GridView) findViewById(R.id.checkboxGridView);
        photosCheckBox.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        setAdapter();
    }

    private void setAdapter(){
        adapter = new ArrayAdapter<Photo>(
                this,
                R.layout.checkbox_listview,
                R.id.checkbox,
                photos
        );
        photosCheckBox.setAdapter(adapter);
    }

    private void listViewListener(){
        photosCheckBox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedPhotos.add((Photo) photosCheckBox.getItemAtPosition(i));
            }
        });
    }
}
/*
package com.example.leonardoroman.photos19;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;

import dataBaseManager.DataBaseManagerHelper;
import model.Album;
import model.CustomAdapter;
import model.Photo;

public class DeletePhotos extends AppCompatActivity {
    private GridView photosCheckBox;
    private ArrayList<Photo> selectedPhotos;
    private ArrayList<Photo> photos;
    private CustomAdapter customAdapter;
    private ArrayList<Bitmap> bitmapImages;
    private DataBaseManagerHelper dataBaseManagerHelper;
    private int albumNumber,album;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_photos);
        dataBaseManagerHelper = new DataBaseManagerHelper(this);
        setFields();
        setAdapter();
        listViewListener();
    }
    public void deleteAlbums(View view){
        ArrayList<String> photosToDelete = new ArrayList<String>();

        for(int i = 0; i < selectedPhotos.size(); i++){
            photosToDelete.add(selectedPhotos.get(i).encodedToString());
            photos.remove(selectedPhotos.get(i));
        }
        dataBaseManagerHelper.deletePhoto(photosToDelete);
        setAdapter();
        finish();
    }
    //********************************** HELPER METHODS **********************************
    private void setFields(){
        bitmapImages = new ArrayList<>();
        Intent intent = getIntent();
        album = intent.getIntExtra("album",0);
        albumNumber = intent.getIntExtra("albumNumber",0);
        photos = Photos.getAlbums().get(album).getAlbum();
        selectedPhotos = new ArrayList<Photo>();
        photosCheckBox = (GridView) findViewById(R.id.checkboxGridView);
        photosCheckBox.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ArrayList<String> photos = dataBaseManagerHelper.getPhotosFromAlbum(albumNumber);
        for (int i = 0; i < photos.size(); i++) {
            // to decode string image back to bitmap image
            byte[] decodedBytes = Base64.decode(photos.get(i), 0);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            bitmapImages.add(bitmap);
        }
        setAdapter();
        photosCheckBox.setAdapter(customAdapter);
    }

    private void setAdapter(){ customAdapter = new CustomAdapter(this,bitmapImages);}

    private void listViewListener(){
        photosCheckBox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedPhotos.add((Photo) photosCheckBox.getItemAtPosition(i));
            }
        });
    }
}

 */