package com.example.leonardoroman.photos19;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import dataBaseManager.DataBaseManagerHelper;
import model.Album;

public class DeleteAlbums extends AppCompatActivity {
    private ListView albumsCheckBox;
    private ArrayAdapter<Album> adapter;
    private ArrayList<Album> selectedAlbums;
    private  ArrayList<Album> albums;
    private DataBaseManagerHelper dataBaseManagerHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_albums);
        setFields();
        setAdapter();
        listViewListener();
    }

    public void deleteAlbums(View view){
        ArrayList<String> albumsToDelete = new ArrayList<String>();
        for(int i = 0; i < selectedAlbums.size(); i++){
            albumsToDelete.add(selectedAlbums.get(i).toString());
            albums.remove(selectedAlbums.get(i));
        }
        dataBaseManagerHelper.deleteAlbum(albumsToDelete);
        setAdapter();
        finish();
    }
    //********************************** HELPER METHODS **********************************
    private void setFields(){
        dataBaseManagerHelper = new DataBaseManagerHelper(this);
        albums = Photos.getAlbums();
        selectedAlbums = new ArrayList<Album>();
        albumsCheckBox = (ListView) findViewById(R.id.checkboxListView);
        albumsCheckBox.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    private void setAdapter(){
        adapter = new ArrayAdapter<Album>(
                this,
                R.layout.checkbox_listview,
                R.id.checkbox,
                albums
        );
        albumsCheckBox.setAdapter(adapter);
    }

    private void listViewListener(){
        albumsCheckBox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedAlbums.add((Album) albumsCheckBox.getItemAtPosition(i));
            }
        });
    }
}
