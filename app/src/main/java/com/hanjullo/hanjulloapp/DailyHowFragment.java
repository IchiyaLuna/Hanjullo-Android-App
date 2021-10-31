package com.hanjullo.hanjulloapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class DailyHowFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public int currentBtn = 0;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DailyHowFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DailyHowFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DailyHowFragment newInstance(String param1, String param2) {
        DailyHowFragment fragment = new DailyHowFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragView = inflater.inflate(R.layout.fragment_daily_how, container, false);

        Button btnBest = (Button) fragView.findViewById(R.id.bestBtn);
        Button btnGood = (Button) fragView.findViewById(R.id.goodBtn);
        Button btnNice = (Button) fragView.findViewById(R.id.niceBtn);
        Button btnNotBad = (Button) fragView.findViewById(R.id.notBadBtn);
        Button btnBad = (Button) fragView.findViewById(R.id.badBtn);

        btnBest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentBtn = 1;
                highlightBtn(fragView);
            }
        });
        btnGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentBtn = 2;
                highlightBtn(fragView);
            }
        });
        btnNice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentBtn = 3;
                highlightBtn(fragView);
            }
        });
        btnNotBad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentBtn = 4;
                highlightBtn(fragView);
            }
        });
        btnBad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentBtn = 5;
                highlightBtn(fragView);
            }
        });

        // Inflate the layout for this fragment
        return fragView;
    }

    private void highlightBtn(View view) {

        Button btnBest = (Button) view.findViewById(R.id.bestBtn);
        Button btnGood = (Button) view.findViewById(R.id.goodBtn);
        Button btnNice = (Button) view.findViewById(R.id.niceBtn);
        Button btnNotBad = (Button) view.findViewById(R.id.notBadBtn);
        Button btnBad = (Button) view.findViewById(R.id.badBtn);

        btnBest.setBackgroundResource(R.drawable.btn_light_style);
        btnGood.setBackgroundResource(R.drawable.btn_light_style);
        btnNice.setBackgroundResource(R.drawable.btn_light_style);
        btnNotBad.setBackgroundResource(R.drawable.btn_light_style);
        btnBad.setBackgroundResource(R.drawable.btn_light_style);


        switch (currentBtn) {
            case 1:
                btnBest.setBackgroundResource(R.drawable.btn_dark_style);
                break;
            case 2:
                btnGood.setBackgroundResource(R.drawable.btn_dark_style);
                break;
            case 3:
                btnNice.setBackgroundResource(R.drawable.btn_dark_style);
                break;
            case 4:
                btnNotBad.setBackgroundResource(R.drawable.btn_dark_style);
                break;
            case 5:
                btnBad.setBackgroundResource(R.drawable.btn_dark_style);
                break;

        }
    }
}