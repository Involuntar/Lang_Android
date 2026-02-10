package com.example.lang_android;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

public class HomeFragment extends Fragment {
    CardView quarksCard, ramenCard, pieCard;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        quarksCard = view.findViewById(R.id.quark_pancakes_card);
        ramenCard = view.findViewById(R.id.ramen_card);
        pieCard = view.findViewById(R.id.shepherds_pie_card);

        Fragment quarkFragment = new QuarkFragment();
        Fragment ramenFragment = new RamenFragment();
        Fragment pieFragment = new PieFragment();

        quarksCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentFragment(quarkFragment);
            }
        });

        ramenCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentFragment(ramenFragment);
            }
        });

        pieCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentFragment(pieFragment);
            }
        });

        return view;
    }

    private void setCurrentFragment(Fragment fragment) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.flFragment, fragment)
                .addToBackStack(null)
                .commit();
    }
}