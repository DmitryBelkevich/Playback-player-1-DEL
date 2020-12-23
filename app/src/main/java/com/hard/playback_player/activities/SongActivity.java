package com.hard.playback_player.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.hard.playback_player.R;
import com.hard.playback_player.activities.fragments.PlayerFragment;
import com.hard.playback_player.activities.fragments.ScoreFragment;
import com.hard.playback_player.activities.fragments.TextFragment;
import com.hard.playback_player.models.Song;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SongActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button button_play;
    private Button button_stop;

    private Song song;
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        // Toolbar >
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        // < Toolbar

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // View Pager

        ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addItem(new TextFragment(), "Text");
        viewPagerAdapter.addItem(new ScoreFragment(), "Score");
        viewPagerAdapter.addItem(new PlayerFragment(), "Player");
        viewPager.setAdapter(viewPagerAdapter);

        //

        button_play = findViewById(R.id.button_play);
        button_stop = findViewById(R.id.button_stop);

        button_play.setOnClickListener((view) -> {
            start();
        });

        button_stop.setOnClickListener((view) -> {
            stop();
        });

        //

        song = (Song) getIntent().getSerializableExtra("song");

        Toast.makeText(this, song.getBand().getTitle() + " - " + song.getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stop();
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments = new ArrayList<>();
        private List<String> pageTitles = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        public void addItem(Fragment fragment, String pageTitle) {
            fragments.add(fragment);
            pageTitles.add(pageTitle);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return pageTitles.get(position);
        }
    }

    public Song getSong() {
        return song;
    }

    /**
     * Player
     */

    private void init() {
        player = new MediaPlayer();

        String playback = song.getPlaybacks().get(song.getTransposition());
        try {
            FileInputStream fileInputStream = new FileInputStream(playback);
            FileDescriptor fileDescriptor = fileInputStream.getFD();
            player.setDataSource(fileDescriptor);
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        player.setVolume(1.0f, 1.0f);

        player.setOnCompletionListener((mediaPlayer) -> {
            stop();
        });
    }

    private void start() {
        if (player == null)
            init();

        if (player.isPlaying()) {
            player.pause();
            button_play.setText("Paused");
            return;
        }

        player.start();
        button_play.setText("Pause");
    }

    private void stop() {
        if (player == null)
            return;

        player.release();
        button_play.setText("Play");
        player = null;

        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
    }
}