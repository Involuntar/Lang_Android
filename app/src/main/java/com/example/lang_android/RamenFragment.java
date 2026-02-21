package com.example.lang_android;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.Locale;

public class RamenFragment extends Fragment {
    Button backButton, watchBtn;
    ImageButton playButton, pauseButton, stopButton;
    MediaPlayer mPlayer;
    Locale currentLocale;
    VideoView videoPlayer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ramen, container, false);
        backButton = view.findViewById(R.id.back_btn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        String lang = prefs.getString("Locale.Helper.Selected.Language", "en");

        videoPlayer = view.findViewById(R.id.videoPlayer);
        Uri ramenVideoUri = Uri.parse("android.resource://" + requireContext().getPackageName() + "/" + R.raw.ramen);
        videoPlayer.setVideoURI(ramenVideoUri);
        MediaController mediaController = new MediaController(requireContext());
        videoPlayer.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoPlayer);

//        watchBtn = view.findViewById(R.id.watch_btn);
//        watchBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                playVideo();
//            }
//        });

        playButton = view.findViewById(R.id.play_btn);
        pauseButton = view.findViewById(R.id.pause_btn);
        stopButton = view.findViewById(R.id.stop_btn);

        pauseButton.setEnabled(false);
        stopButton.setEnabled(false);

        int audioResId;
        if (lang.equals("ru")) {
            audioResId = R.raw.ramen_ru;
        } else if (lang.equals("ja")) {
            audioResId = R.raw.ramen_ja;
        } else {
            audioResId = R.raw.ramen_en;
        }
        mPlayer = MediaPlayer.create(requireContext(), audioResId);

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopPlay();
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pause();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop();
            }
        });

        return view;
    }

    private void stopPlay() {
        mPlayer.stop();
        pauseButton.setEnabled(false);
        stopButton.setEnabled(false);
        try {
            mPlayer.prepare();
            mPlayer.seekTo(0);
            playButton.setEnabled(true);
        } catch (Throwable t) {
            Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void play() {

        mPlayer.start();
        playButton.setEnabled(false);
        pauseButton.setEnabled(true);
        stopButton.setEnabled(true);
    }

    public void pause() {

        mPlayer.pause();
        playButton.setEnabled(true);
        pauseButton.setEnabled(false);
        stopButton.setEnabled(true);
    }

    public void stop() {
        stopPlay();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer.isPlaying()) {
            stopPlay();
        }
    }

    public void playVideo() {
        videoPlayer.start();
    }

    public void pauseVideo() {
        videoPlayer.pause();
    }

    public void stopVideo() {
        videoPlayer.stopPlayback();
        videoPlayer.resume();
    }
}