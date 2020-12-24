package com.hard.playback_player.activities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.barteksc.pdfviewer.PDFView;
import com.hard.playback_player.R;
import com.hard.playback_player.activities.SongActivity;
import com.hard.playback_player.models.Song;
import com.hard.playback_player.settings.Constants;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScoreFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private PDFView pdfView;
    private Song song;

    public ScoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScoreFragment newInstance(String param1, String param2) {
        ScoreFragment fragment = new ScoreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        SongActivity activity = (SongActivity) getActivity();
        song = activity.getSong();

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_score, container, false);

        pdfView = view.findViewById(R.id.pdfView);

        load();

        return view;
    }

    /**
     * Menu
     */

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        Map<String, String> scoresPaths = song.getScores();
        Set<String> scoresSet = scoresPaths.keySet();

        int id = 1;
        Iterator<String> iterator = scoresSet.iterator();
        while (iterator.hasNext()) {
            String scoreTitle = iterator.next();
            menu.add(1, id, id, scoreTitle);
            id++;
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getTitle() == null)
            return super.onOptionsItemSelected(item);

        song.setScore(item.getTitle().toString());
        load();

        return super.onOptionsItemSelected(item);
    }

    /**
     * load
     */

    private void load() {
        Map<String, String> scoresPaths = song.getScores();

        String score = scoresPaths.get(song.getScore());

        if (score == null)
            return;

        pdfView.fromFile(new File(score))
//                .pages(0, 1, 2) // all pages are displayed by default
                .enableSwipe(true) // allows to block changing pages using swipe
                .swipeHorizontal(song.getScore().equals("Full Score"))
                .enableDoubletap(true)
                .defaultPage(0)
                // allows to draw something on the current page, usually visible in the middle of the screen
//                .onDraw(onDrawListener)
                // allows to draw something on all pages, separately for every page. Called only for visible pages
//                .onDrawAll(onDrawListener)
//                .onLoad(onLoadCompleteListener) // called after document is loaded and starts to be rendered
//                .onPageChange(onPageChangeListener)
//                .onPageScroll(onPageScrollListener)
//                .onError(onErrorListener)
//                .onPageError(onPageErrorListener)
//                .onRender(onRenderListener) // called after document is rendered for the first time
                // called on single tap, return true if handled, false to toggle scroll handle visibility
//                .onTap(onTapListener)
//                .onLongPress(onLongPressListener)
                .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                .password(null)
                .scrollHandle(null)
                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                // spacing between pages in dp. To define spacing color, set view background
                .spacing(0)
//                .autoSpacing(false) // add dynamic spacing to fit each page on its own on the screen
//                .linkHandler(DefaultLinkHandler)
//                .pageFitPolicy(FitPolicy.WIDTH) // mode to fit pages in the view
//                .fitEachPage(false) // fit each page to the view, else smaller pages are scaled relative to largest page.
//                .pageSnap(false) // snap pages to screen boundaries
//                .pageFling(false) // make a fling change only a single page like ViewPager
//                .nightMode(false) // toggle night mode
                .load();
    }
}