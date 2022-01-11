package com.hanjullo.hanjulloapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hanjullo.hanjulloapp.databinding.FragmentDailyAFirstBinding;

import java.util.ArrayList;


public class DailyAFirstFragment extends Fragment {

    FragmentDailyAFirstBinding binding;
    ArrayList<Button> answerButtons;

    public int currentBtn = 0;

    public DailyAFirstFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentDailyAFirstBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        answerButtons = new ArrayList<>();
        answerButtons.add(binding.bestBtn);
        answerButtons.add(binding.goodBtn);
        answerButtons.add(binding.niceBtn);
        answerButtons.add(binding.notBadBtn);
        answerButtons.add(binding.badBtn);

        setListener();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setListener() {
        View.OnClickListener listener = v -> {

            int id = v.getId();

            if (id == R.id.bestBtn) {
                currentBtn = 1;
            } else if (id == R.id.goodBtn) {
                currentBtn = 2;
            } else if (id == R.id.niceBtn) {
                currentBtn = 3;
            } else if (id == R.id.notBadBtn) {
                currentBtn = 4;
            } else if (id == R.id.badBtn) {
                currentBtn = 5;
            }

            highlightBtn();
        };

        for (int i = 0; i < answerButtons.size(); i++) {
            answerButtons.get(i).setOnClickListener(listener);
        }
    }
    private void highlightBtn() {

        Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(VibrationEffect.createOneShot(50,100));

        for (int i = 0; i < answerButtons.size(); i++) {
            if (i == currentBtn - 1) answerButtons.get(i).setBackgroundResource(R.drawable.btn_dark_style);
            else answerButtons.get(i).setBackgroundResource(R.drawable.btn_light_style);
        }
    }
}