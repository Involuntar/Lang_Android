package com.example.lang_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {
    ImageButton engBtn, rusBtn, japBtn;
    Context context;
    MediaPlayer mPlayer;
    @Override
    protected void attachBaseContext(Context newBase) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(newBase);
        String lang = prefs.getString("Locale.Helper.Selected.Language", "en");
        super.attachBaseContext(LocalHelper.setLocale(newBase, lang));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getSupportFragmentManager().registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentResumed(@NonNull FragmentManager fm, @NonNull Fragment f) {
                super.onFragmentResumed(fm, f);
                if (f instanceof HomeFragment && savedInstanceState == null) {
                    return;
                }

                playTransitionSound();
            }
        }, true);

        if (savedInstanceState == null) {
            mPlayer = MediaPlayer.create(this, R.raw.windows_xp_startup);
            mPlayer.start();
        }

        Fragment homeFragment = new HomeFragment();
        if (savedInstanceState == null) {
            setCurrentFragment(homeFragment);
        }

        engBtn = findViewById(R.id.eng_btn);
        engBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLanguage("en");
            }
        });

        rusBtn = findViewById(R.id.rus_btn);
        rusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLanguage("ru");
            }
        });

        japBtn = findViewById(R.id.jap_btn);
        japBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLanguage("ja");
            }
        });
    }

    private void playTransitionSound() {
        MediaPlayer transitionPlayer = MediaPlayer.create(this, R.raw.windows_xp_critical_stop);
        transitionPlayer.setOnCompletionListener(MediaPlayer::release);
        transitionPlayer.start();
    }

    private void stopPlay(){
        mPlayer.stop();
        try {
            mPlayer.prepare();
            mPlayer.seekTo(0);
        }
        catch (Throwable t) {
            Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setCurrentFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.flFragment, fragment, null)
                .commit();
    }

    private void changeLanguage(String lang) {
        LocalHelper.setLocale(this, lang);
        recreate();
    }
}