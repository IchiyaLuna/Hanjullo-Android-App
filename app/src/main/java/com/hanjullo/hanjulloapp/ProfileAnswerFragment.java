package com.hanjullo.hanjulloapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ProfileAnswerFragment extends Fragment {


    public ProfileAnswerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_answer, container, false);

        Button todayAnswerBtn = (Button) view.findViewById(R.id.todayAnswerBtn);

        if (getActivity() == null) return null;
        
        todayAnswerBtn.setOnClickListener(view1 -> ((ProfileActivity) getActivity()).startDailyQuestion());

        // Inflate the layout for this fragment
        return view;
    }
}