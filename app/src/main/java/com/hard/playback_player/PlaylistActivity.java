package com.hard.playback_player;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hard.playback_player.models.Song;
import com.hard.playback_player.services.SongService;
import com.hard.playback_player.storage.Database;
import com.hard.playback_player.storage.DatabaseInitializer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class PlaylistActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView playlist_listView;

    private SongService songService;

    private ArrayAdapter<String> arrayAdapter;
    private List<String> titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        playlist_listView = findViewById(R.id.playlist_listView);

        songService = new SongService();
        titles = new ArrayList<>();

        swipeRefreshLayout.setOnRefreshListener(this);
        playlist_listView.setOnItemClickListener(this);

        onRefresh();
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);

        Database.clear();
        DatabaseInitializer.init();

        Collection<Song> songs = songService.getAll();

        titles.clear();

        Iterator<Song> iterator = songs.iterator();
        while (iterator.hasNext()) {
            Song song = iterator.next();

            String songTitle = song.getTitle();
            String bandTitle = song.getBand().getTitle();

            titles.add(bandTitle + " - " + songTitle);
        }

        setAdapter();

        swipeRefreshLayout.setRefreshing(false);
    }

    private void setAdapter() {
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titles);
        playlist_listView.setAdapter(arrayAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(PlaylistActivity.this, SongActivity.class);

        Song song = songService.getById(id + 1);

        intent.putExtra("song", song);

        startActivity(intent);
    }
}