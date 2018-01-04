package com.example.leonardoroman.photos19;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import dataBaseManager.DataBaseManagerHelper;
import model.Album;
import model.AlbumList;
import model.Photo;

public class Photos extends AppCompatActivity {
    private EditText search;
    private ListView albumList;
    private static AlbumList listOfAlbums;
    private ArrayAdapter<Album> albumAdapter;
    private ArrayList<String> albums;
    private DataBaseManagerHelper dataBaseManagerHelper;
    private int albumNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        dataBaseManagerHelper = new DataBaseManagerHelper(this);
        setFields();
        setAdapter();

    }

    @Override
    protected void onStart() {
        super.onStart();
        setAdapter();
    }

    public void addAlbum(View view){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Photos.this);
        View myview = getLayoutInflater().inflate(R.layout.addalbum_dialog,null);

        // To get the text from the addAlbum dialog in order to create a new album to the list
        final EditText newAlbumEditText = (EditText) myview.findViewById(R.id.newAlbumDialogText);
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
                        if(!newAlbumEditText.getText().toString().isEmpty()){
                            listOfAlbums.getAlbums().add(new Album(newAlbumEditText.getText().toString()));
                            dataBaseManagerHelper.addAlbum(new Album(newAlbumEditText.getText().toString()));
                            setAdapter();
                        }
                    }
                });

        alertBuilder.setView(myview);
        AlertDialog addAlbumDialog = alertBuilder.create();
        addAlbumDialog.show();
    }

    public void deleteAlbum(View view){
        Intent intent = new Intent(this,DeleteAlbums.class);
        startActivity(intent);
    }

    //********************************** HELPER METHODS **********************************

    private void setAdapter(){
        albums = dataBaseManagerHelper.getAlbums();
        albumAdapter = new ArrayAdapter<Album>(
                this,
                android.R.layout.simple_list_item_1,
                listOfAlbums.getAlbums()
        );
        albumList.setAdapter(albumAdapter);
    }

    private void setFields(){
        listOfAlbums = new AlbumList();
        // getting data from database
        albums = dataBaseManagerHelper.getAlbums();
        for(int i = 0; i <albums.size(); i++){
            listOfAlbums.addAlbum(new Album(albums.get(i)));
        }

        search = (EditText) findViewById(R.id.search);
        albumList = (ListView) findViewById(R.id.albums);

        // To go to album's photos
        albumList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Photos.this,AlbumPhotos.class);
                System.out.println("Album: "+albums.get(i).toString());
                albumNumber = dataBaseManagerHelper.getAlbumNumber(albums.get(i).toString());
                System.out.println("Album number from database: "+albumNumber);
                intent.putExtra("albumNumber",albumNumber);
                intent.putExtra("index",i);
                startActivity(intent);
            }
        });

        // To edit album name.
        albumList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int index = i;
                System.out.println("album index: "+i+"\nalbum name: "+albums.get(i).toString());
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Photos.this);
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
                                    listOfAlbums.getAlbums().get(index).setAlbumName(newAlbumName.getText().toString());
                                    dataBaseManagerHelper.editAlbumName(newAlbumName.getText().toString(),albumNumber);
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
    }
    public static ArrayList<Album> getAlbums(){
        return listOfAlbums.getAlbums();
    }
}
