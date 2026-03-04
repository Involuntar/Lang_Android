package com.example.lang_android;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class QuarkFragment extends Fragment {
    Button backButton;
    TextView fryTimer;
    ImageButton playButton, pauseButton, resetButton;
    boolean timerRunning = false;
    private final long startTimeInMillis = 300000;
    private long timeLeftInMillis = startTimeInMillis;
    private CountDownTimer timer;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quark, container, false);
        backButton = view.findViewById(R.id.back_btn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });

        fryTimer = view.findViewById(R.id.fry_timer);

        playButton = view.findViewById(R.id.start_timer);
        pauseButton = view.findViewById(R.id.pause_timer);
        resetButton = view.findViewById(R.id.reset_timer);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!timerRunning) {
                    startTimer();
                }
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timer != null) {
                    timer.cancel();
                }
                timerRunning = false;
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timer != null) {
                    timer.cancel();
                }
                timerRunning = false;
                fryTimer.setText("05:00");
                timeLeftInMillis = 300000;
            }
        });

        return view;
    }

    private void startTimer() {
        // Создаем НОВЫЙ объект таймера, передавая ему актуальный ОСТАТОК времени
        timer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished; // Обновляем остаток
                NumberFormat f = new DecimalFormat("00");
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;
                fryTimer.setText(f.format(min) + ":" + f.format(sec));
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                fryTimer.setText("00:00");
                timeLeftInMillis = 300000;
            }
        }.start();

        timerRunning = true;
    }
}