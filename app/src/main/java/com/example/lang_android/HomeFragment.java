package com.example.lang_android;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;

public class HomeFragment extends Fragment {
    CardView quarksCard, ramenCard, pieCard;
    Button breakfastBtn, lunchBtn, dinnerBtn, allBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        quarksCard = view.findViewById(R.id.quark_pancakes_card);
        ramenCard = view.findViewById(R.id.ramen_card);
        pieCard = view.findViewById(R.id.shepherds_pie_card);

        breakfastBtn = view.findViewById(R.id.breakfast_btn);
        lunchBtn = view.findViewById(R.id.lunch_btn);
        dinnerBtn = view.findViewById(R.id.dinner_btn);
        allBtn = view.findViewById(R.id.all_btn);

        quarksCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentFragment(new QuarkFragment());
            }
        });

        ramenCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentFragment(new RamenFragment());
            }
        });

        pieCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentFragment(new PieFragment());
            }
        });

        breakfastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quarksCard.setVisibility(View.VISIBLE);
                ramenCard.setVisibility(View.GONE);
                pieCard.setVisibility(View.GONE);
            }
        });

        lunchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quarksCard.setVisibility(View.GONE);
                ramenCard.setVisibility(View.VISIBLE);
                pieCard.setVisibility(View.GONE);
            }
        });

        dinnerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quarksCard.setVisibility(View.GONE);
                ramenCard.setVisibility(View.GONE);
                pieCard.setVisibility(View.VISIBLE);
            }
        });

        allBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quarksCard.setVisibility(View.VISIBLE);
                ramenCard.setVisibility(View.VISIBLE);
                pieCard.setVisibility(View.VISIBLE);
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