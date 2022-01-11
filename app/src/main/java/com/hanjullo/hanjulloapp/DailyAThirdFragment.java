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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DailyAThirdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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
            String text = "";

            if (id == R.id.why01) {
                currentBtn = 1;
                text = "직업";
            } else if (id == R.id.why02) {
                currentBtn = 2;
                text = "학업";
            } else if (id == R.id.why03) {
                currentBtn = 3;
                text = "친구";
            } else if (id == R.id.why04) {
                currentBtn = 4;
                text = "가족";
            } else if (id == R.id.why05) {
                currentBtn = 5;
                text = "취미";
            } else if (id == R.id.why06) {
                currentBtn = 6;
                text = "운동";
            } else if (id == R.id.why07) {
                currentBtn = 7;
                text = "음식";
            } else if (id == R.id.why08) {
                currentBtn = 8;
                text = "수면";
            } else if (id == R.id.why09) {
                currentBtn = 9;
                text = "쇼핑";
            } else if (id == R.id.why10) {
                currentBtn = 10;
                text = "돈";
            } else if (id == R.id.why11) {
                currentBtn = 11;
                text = "휴식";
            } else if (id == R.id.why12) {
                currentBtn = 12;
                text = "여행";
            } else if (id == R.id.why13) {
                currentBtn = 13;
                text = "인간관계";
            } else if (id == R.id.why14) {
                currentBtn = 14;
                text = "SNS";
            }

            binding.whySelectTextView.setText(text);
            highlightBtn();
        };

        for (int i = 0; i < whyButtons.size(); i++) {
            whyButtons.get(i).setOnClickListener(listener);
        }
    }

    private void highlightBtn() {

        Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(VibrationEffect.createOneShot(50,100));

        for (int i = 0; i < whyButtons.size(); i++) {
            if (i == currentBtn - 1) whyButtons.get(i).setBackgroundResource(R.drawable.btn_dark_style);
            else whyButtons.get(i).setBackgroundResource(R.drawable.btn_light_style);
        }
    }
}