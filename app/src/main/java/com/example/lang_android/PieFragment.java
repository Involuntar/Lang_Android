package com.example.lang_android;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;


public class PieFragment extends Fragment {
    Button backButton;
    TextView fryTime, bakingTime;
    ImageButton fryPlayButton, fryPauseButton, fryResetButton,
            bakingPlayButton, bakingPauseButton, bakingResetButton;
    EditText editBakingTime;
    boolean fryTimerRunning, bakingTimerRunning = false;
    private final long fryStartTimeInMillis = 1200000;
    private final long bakingStartTimeInMillis = 1500000;
    private long fryTimeLeftInMillis = fryStartTimeInMillis;
    private long bakingTimeLeftInMillis;
    private CountDownTimer fryTimer;
    private CountDownTimer bakingTimer;
    MediaPlayer bakingMediaPlayer;
    MediaPlayer fryMediaPlayer;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pie, container, false);
        backButton = view.findViewById(R.id.back_btn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });
        
        bakingMediaPlayer = MediaPlayer.create(getContext(), R.raw.microwave_timer);
        fryMediaPlayer = MediaPlayer.create(getContext(), R.raw.vibraslap);
        
        editBakingTime = view.findViewById(R.id.baking_timer_input);

        fryTime = view.findViewById(R.id.fry_timer);
        bakingTime = view.findViewById(R.id.baking_timer);

        fryPlayButton = view.findViewById(R.id.start_fry_timer);
        fryPauseButton = view.findViewById(R.id.pause_fry_timer);
        fryResetButton = view.findViewById(R.id.reset_fry_timer);

        bakingPlayButton = view.findViewById(R.id.start_baking_timer);
        bakingPauseButton = view.findViewById(R.id.pause_baking_timer);
        bakingResetButton = view.findViewById(R.id.reset_baking_timer);

        fryPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!fryTimerRunning) {
                    startFryTimer();
                }
            }
        });

        fryPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fryTimer != null) {
                    fryTimer.cancel();
                }
                fryTimerRunning = false;
            }
        });

        fryResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fryTimer != null) {
                    fryTimer.cancel();
                }
                fryTimerRunning = false;
                fryTime.setText("20:00");
                fryTimeLeftInMillis = fryStartTimeInMillis;
            }
        });

        bakingPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bakingTimerRunning) {
                    String input = editBakingTime.getText().toString();
                    if (input.isEmpty()) {
                        bakingTimeLeftInMillis = bakingStartTimeInMillis;
                    } else {
                        try {
                            if (input.contains(":")) {
                                String[] parts = input.split(":");
                                if (parts.length == 0) {
                                    bakingTimeLeftInMillis = bakingStartTimeInMillis;
                                } else {
                                    long minutes = parts[0].isEmpty() ? 0 : Long.parseLong(parts[0]);
                                    long seconds = (parts.length > 1 && !parts[1].isEmpty()) ? Long.parseLong(parts[1]) : 0;
                                    bakingTimeLeftInMillis = (minutes * 60 + seconds) * 1000;
                                }
                            } else {
                                // Если введено просто число, считаем как минуты
                                bakingTimeLeftInMillis = Long.parseLong(input) * 60000;
                            }
                        } catch (NumberFormatException e) {
                            bakingTimeLeftInMillis = bakingStartTimeInMillis;
                        }
                    }
                    startBakingTimer();
                }
            }
        });

        bakingPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bakingTimer != null) {
                    bakingTimer.cancel();
                }
                bakingTimerRunning = false;
            }
        });

        bakingResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bakingTimer != null) {
                    bakingTimer.cancel();
                }
                bakingTimerRunning = false;
                bakingTime.setText("25:00");
                bakingTimeLeftInMillis = bakingStartTimeInMillis;
            }
        });

        return view;
    }

    private void startFryTimer() {
        // Создаем НОВЫЙ объект таймера, передавая ему актуальный ОСТАТОК времени
        fryTimer = new CountDownTimer(fryTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                fryTimeLeftInMillis = millisUntilFinished; // Обновляем остаток
                if (fryTimeLeftInMillis <= 60000) {
                    fryTime.setTextColor(Color.RED);
                }
                NumberFormat f = new DecimalFormat("00");
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;
                fryTime.setText(f.format(min) + ":" + f.format(sec));
            }

            @Override
            public void onFinish() {
                fryMediaPlayer.start();
                fryTimerRunning = false;
                fryTime.setText("00:00");
                fryTimeLeftInMillis = fryStartTimeInMillis;
            }
        }.start();

        fryTimerRunning = true;
    }

    private void startBakingTimer() {
        // Создаем НОВЫЙ объект таймера, передавая ему актуальный ОСТАТОК времени
        bakingTimer = new CountDownTimer(bakingTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                bakingTimeLeftInMillis = millisUntilFinished; // Обновляем остаток
                if (bakingTimeLeftInMillis <= 60000) {
                    bakingTime.setTextColor(Color.RED);
                }
                NumberFormat f = new DecimalFormat("00");
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;
                bakingTime.setText(f.format(min) + ":" + f.format(sec));
            }

            @Override
            public void onFinish() {
                bakingMediaPlayer.start();
                bakingTimerRunning = false;
                bakingTime.setText("00:00");
                bakingTimeLeftInMillis = bakingStartTimeInMillis;
            }
        }.start();

        bakingTimerRunning = true;
    }
}
