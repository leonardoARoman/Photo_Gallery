package com.example.leonardoroman.photos19;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import model.Album;
import model.Photo;

public class PhotoDisplay extends AppCompatActivity {
    private ListView tagsListView;
    private ImageView imageView;
    private ArrayAdapter<String> arrayAdapter;

    private int albumIndex, photoIndex, counter;
    private Photo photo;
    private ArrayList<Photo> photos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_display);
        setFields();
        setAdapter();
    }

    public void home(View view){
        finish();
    }

    public void nextPhoto(View view){
        int size = photos.size();
        counter++;
        imageView.setImageBitmap(photos.get(Math.abs(counter)%size).getImage());
    }

    public void previusPhoto(View view){
        int size = photos.size();
        counter--;
        imageView.setImageBitmap(photos.get(Math.abs(counter)%size).getImage());
    }

    public void addTag(View view){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(PhotoDisplay.this);
        View myview = getLayoutInflater().inflate(R.layout.edit_list_item,null);

        // To get the text from the addAlbum dialog in order to create a new album to the list
        final EditText newAlbumEditText = (EditText) myview.findViewById(R.id.editItem);
        alertBuilder.setView(myview)
                .setTitle("Add new tag")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(!newAlbumEditText.getText().toString().isEmpty()){
                            photo.addTag(newAlbumEditText.getText().toString());
                            setAdapter();
                        }
                    }
                });

        alertBuilder.setView(myview);
        AlertDialog addAlbumDialog = alertBuilder.create();
        addAlbumDialog.show();
    }

    public void deletePhoto(View view){
        Photos.getAlbums().get(albumIndex).deletePhoto(photoIndex);
        finish();
    }

    private void setFields(){
        tagsListView = (ListView) findViewById(R.id.photInfoListView);
        imageView = (ImageView) findViewById(R.id.photoImageView);
        Intent intent = getIntent();
        albumIndex = intent.getIntExtra("album",0);
        photoIndex = intent.getIntExtra("photo",0);
        counter = photoIndex;
        photos = Photos.getAlbums().get(albumIndex).getAlbum();
        photo = Photos.getAlbums().get(albumIndex).getPhoto(photoIndex);
        imageView.setImageBitmap(photo.getImage());
        // Temporary objects
        photo.addTag("AC");
        photo.addTag("NJ");
        photo.addTag("RU");

        // To edit album name.
        tagsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int index = i;

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(PhotoDisplay.this);
                View myview = getLayoutInflater().inflate(R.layout.confirm_dialog,null);

                // To get the text from the addAlbum dialog in order to create a new album to the list
                alertBuilder.setView(myview)
                        .setTitle("Add album")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                photo.deleteTag(photo.getTags().get(index));
                                setAdapter();

                            }
                        });

                alertBuilder.setView(myview);
                AlertDialog addAlbumDialog = alertBuilder.create();
                addAlbumDialog.show();
                return false;
            }
        });
    }

    private void setAdapter(){
        arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                photo.getTags()
        );

        tagsListView.setAdapter(arrayAdapter);
    }
}
