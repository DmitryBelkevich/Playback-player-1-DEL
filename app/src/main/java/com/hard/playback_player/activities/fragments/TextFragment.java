package com.hard.playback_player.activities.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.hard.playback_player.R;
import com.hard.playback_player.activities.SongActivity;
import com.hard.playback_player.models.Song;
import com.hard.playback_player.settings.Constants;
import com.hard.playback_player.utils.Reader;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TextFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TextFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView textView;
    private Song song;

    public TextFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TextFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TextFragment newInstance(String param1, String param2) {
        TextFragment fragment = new TextFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text, container, false);

        textView = view.findViewById(R.id.songText);

        load();

        return view;
    }

    private void load() {
        String textUrl = Constants.GOOGLE_DRIVE_FILE + song.getText();

        if (textUrl == null)
            return;

        new AsyncRequest().execute(textUrl);
    }

    private class AsyncRequest extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];

            File file = new File(Constants.STORAGE + "/texts"
                    + "/" + song.getBand().getTitle() + " - " + song.getTitle() + ".txt"
            );

            if (!file.exists())
                Reader.download(url, file);

            String text = null;
            try {
                text = Reader.readFromFile(file.getPath());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            return text;
        }

        @Override
        protected void onPostExecute(String text) {
            super.onPostExecute(text);
            textView.setText(text);
        }
    }
}