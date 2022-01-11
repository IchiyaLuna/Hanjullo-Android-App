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

import com.hanjullo.hanjulloapp.databinding.FragmentDailyAThirdBinding;

import java.util.ArrayList;

public class DailyAThirdFragment extends Fragment {

    FragmentDailyAThirdBinding binding;
    ArrayList<Button> whyButtons;
    int currentBtn = 0;

    public DailyAThirdFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDailyAThirdBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        whyButtons = new ArrayList<>();
        whyButtons.add(binding.why01);
        whyButtons.add(binding.why02);
        whyButtons.add(binding.why03);
        whyButtons.add(binding.why04);
        whyButtons.add(binding.why05);
        whyButtons.add(binding.why06);
        whyButtons.add(binding.why07);
        whyButtons.add(binding.why08);
        whyButtons.add(binding.why09);
        whyButtons.add(binding.why10);
        whyButtons.add(binding.why11);
        whyButtons.add(binding.why12);
        whyButtons.add(binding.why13);
        whyButtons.add(binding.why14);

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

            if (id == R.id.why01) {
                currentBtn = 1;
            } else if (id == R.id.why02) {
                currentBtn = 2;
            } else if (id == R.id.why03) {
                currentBtn = 3;
            } else if (id == R.id.why04) {
                currentBtn = 4;
            } else if (id == R.id.why05) {
                currentBtn = 5;
            } else if (id == R.id.why06) {
                currentBtn = 6;
            } else if (id == R.id.why07) {
                currentBtn = 7;
            } else if (id == R.id.why08) {
                currentBtn = 8;
            } else if (id == R.id.why09) {
                currentBtn = 9;
            } else if (id == R.id.why10) {
                currentBtn = 10;
            } else if (id == R.id.why11) {
                currentBtn = 11;
            } else if (id == R.id.why12) {
                currentBtn = 12;
            } else if (id == R.id.why13) {
                currentBtn = 13;
            } else if (id == R.id.why14) {
                currentBtn = 14;
            }

            String answerText = DailyAThirdAnswers.getAnswerText(currentBtn);

            binding.whySelectTextView.setText(answerText);
            highlightBtn();
        };

        for (int i = 0; i < whyButtons.size(); i++) {
            whyButtons.get(i).setOnClickListener(listener);
        }
    }

    private void highlightBtn() {

        if (getActivity() == null) return;

        Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(VibrationEffect.createOneShot(50,100));

        for (int i = 0; i < whyButtons.size(); i++) {
            if (i == currentBtn - 1) whyButtons.get(i).setBackgroundResource(R.drawable.btn_dark_style);
            else whyButtons.get(i).setBackgroundResource(R.drawable.btn_light_style);
        }
    }
}