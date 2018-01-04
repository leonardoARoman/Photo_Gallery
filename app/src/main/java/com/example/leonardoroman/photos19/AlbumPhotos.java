package com.example.leonardoroman.photos19;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import dataBaseManager.DataBaseManagerHelper;
import model.Album;
import model.CustomAdapter;
import model.Photo;

public class AlbumPhotos extends AppCompatActivity {
    private GridView photosGrid;
    private ImageView imageView;
    private EditText addTags;
    private TextView albumTextview;

    private DataBaseManagerHelper dataBaseManagerHelper;
    private static final int RESULT_LOAD_IMAGE = 1;// Flag for uploading photo param.
    private ArrayAdapter<ImageView> arrayAdapter;
    private CustomAdapter customAdapter;
    private ArrayList<ImageView> thumbnails;
    private ArrayList<Bitmap> bitmapImages;
    private int indexOfAlbum;
    private int albumNumber;
    private Album album;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_photos);
        dataBaseManagerHelper = new DataBaseManagerHelper(this);
        setFields();

    }

    @Override
    protected void onStart() {
        super.onStart();
        setAdapter();
    }

    public void addPhoto(View view) {

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AlbumPhotos.this);
        View myview = getLayoutInflater().inflate(R.layout.addphoto_dialog,null);

        // To get the text from the addAlbum dialog in order to create a new album to the list
        addTags = (EditText) myview.findViewById(R.id.addTags);
        imageView = (ImageView) myview.findViewById(R.id.imageView_dialog);

        // image on click listener.
        imageView.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent obj opens gallery and gets initialized with chosen image.
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent,RESULT_LOAD_IMAGE);
            }
        });

        // Upload photo popup(addPhoto_dialog).
        alertBuilder.setView(myview)
                .setTitle("Upload photo")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(!addTags.getText().toString().isEmpty()){// && imageView != null){
                            Bitmap bitmapIamge = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmapIamge.compress(Bitmap.CompressFormat.JPEG,100,baos);
                            String image = Base64.encodeToString(baos.toByteArray(),Base64.DEFAULT);
                            album.addPhotoToAlbum(new Photo(bitmapIamge,addTags.getText().toString(),"12/08/2017"));
                            dataBaseManagerHelper.addPhoto(albumNumber,Photo.getCount(),image);
                            //setAdapter();
                            setFields();
                        }
                    }
                });

        alertBuilder.setView(myview);
        AlertDialog addAlbumDialog = alertBuilder.create();
        addAlbumDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            imageView.setImageURI(selectedImage);
        }
    }

    public void deletePhotos(View view){
        Intent intent = new Intent(this,DeletePhotos.class);
        intent.putExtra("album",indexOfAlbum);
        intent.putExtra("albumNumber",albumNumber);
        startActivity(intent);
    }


    // **************************** HELPER METHODS // ****************************
    private void setFields(){
        bitmapImages = new ArrayList<>();
        albumTextview = (TextView) findViewById(R.id.albumPhotosActivity);
        photosGrid = (GridView) findViewById(R.id.photosGrid);

        Intent intent = getIntent();
        // Current album index location from previus activity
        indexOfAlbum = intent.getIntExtra("index", 0);
        albumNumber = intent.getIntExtra("albumNumber",0);
        // get album at current index
        album = Photos.getAlbums().get(indexOfAlbum);

        albumTextview.setText(Photos.getAlbums().get(indexOfAlbum)+" Album");
        // get photos of current album from database
        ArrayList<String> photos = dataBaseManagerHelper.getPhotosFromAlbum(albumNumber);
            Log.v("MAIN", "For loop");
            for (int i = 0; i < photos.size(); i++) {
                // to decode string image back to bitmap image
                byte[] decodedBytes = Base64.decode(photos.get(i), 0);
                Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                bitmapImages.add(bitmap);
                if(photos.size()>album.getAlbumSize()){
                    album.addPhotoToAlbum(new Photo(bitmap, "get", "some"));
                }
            }
        setAdapter();

        photosGrid.setAdapter(customAdapter);
        photosGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(AlbumPhotos.this,PhotoDisplay.class);
                intent.putExtra("album",indexOfAlbum);
                intent.putExtra("photo",i);
                startActivity(intent);
            }
        });


    }

    private void setAdapter(){
        customAdapter = new CustomAdapter(this, bitmapImages);
    }
}





/*
        // To edit photo tag.
        photosGrid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int index = i;

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AlbumPhotos.this);
                View myview = getLayoutInflater().inflate(R.layout.edit_list_item,null);

                // To get the text from the addAlbum dialog in order to create a new album to the list
                final EditText newAlbumName = (EditText) myview.findViewById(R.id.editItem);
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
                                if(!newAlbumName.getText().toString().isEmpty()){
                                    // Album album = new Album(newAlbumEditText.getText().toString());
                                    Photos.getAlbums()
                                            .get(indexOfAlbum)
                                            .getPhoto(index)
                                            .se(newAlbumName.getText().toString());
                                    setAdapter();
                                }
                            }
                        });

                alertBuilder.setView(myview);
                AlertDialog addAlbumDialog = alertBuilder.create();
                addAlbumDialog.show();
                return false;
            }
        });
        */