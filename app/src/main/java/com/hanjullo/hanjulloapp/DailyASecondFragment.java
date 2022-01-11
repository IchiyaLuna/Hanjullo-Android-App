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

import com.hanjullo.hanjulloapp.databinding.FragmentDailyASecondBadBinding;
import com.hanjullo.hanjulloapp.databinding.FragmentDailyASecondBestBinding;
import com.hanjullo.hanjulloapp.databinding.FragmentDailyASecondGoodBinding;
import com.hanjullo.hanjulloapp.databinding.FragmentDailyASecondNiceBinding;
import com.hanjullo.hanjulloapp.databinding.FragmentDailyASecondNotbadBinding;

import java.util.ArrayList;

public class DailyASecondFragment extends Fragment {

    FragmentDailyASecondBestBinding bestBinding;
    FragmentDailyASecondGoodBinding goodBinding;
    FragmentDailyASecondNiceBinding niceBinding;
    FragmentDailyASecondNotbadBinding notBadBinding;
    FragmentDailyASecondBadBinding badBinding;

    ArrayList<Button> answerButtons;

    public int currentBtn = 0;
    private int stepASelect;

    public DailyASecondFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        stepASelect = getArguments().getInt("stepASelect", 0);

        View view = setBinding(inflater, container);
        setListener();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bestBinding = null;
        goodBinding = null;
        niceBinding = null;
        notBadBinding = null;
        badBinding = null;
    }

    private View setBinding(LayoutInflater inflater, ViewGroup container) {

        bestBinding = null;
        goodBinding = null;
        niceBinding = null;
        notBadBinding = null;
        badBinding = null;

        answerButtons = new ArrayList<>();

        switch (stepASelect) {
            case 1:
                bestBinding = FragmentDailyASecondBestBinding.inflate(inflater, container, false);
                answerButtons.add(bestBinding.best01);
                answerButtons.add(bestBinding.best02);
                answerButtons.add(bestBinding.best03);
                answerButtons.add(bestBinding.best04);
                answerButtons.add(bestBinding.best05);
                answerButtons.add(bestBinding.best06);
                answerButtons.add(bestBinding.best07);
                answerButtons.add(bestBinding.best08);
                answerButtons.add(bestBinding.best09);
                answerButtons.add(bestBinding.best10);
                answerButtons.add(bestBinding.best11);
                answerButtons.add(bestBinding.best12);
                return bestBinding.getRoot();
            case 2:
                goodBinding = FragmentDailyASecondGoodBinding.inflate(inflater, container, false);
                answerButtons.add(goodBinding.good01);
                answerButtons.add(goodBinding.good02);
                answerButtons.add(goodBinding.good03);
                answerButtons.add(goodBinding.good04);
                answerButtons.add(goodBinding.good05);
                answerButtons.add(goodBinding.good06);
                answerButtons.add(goodBinding.good07);
                answerButtons.add(goodBinding.good08);
                answerButtons.add(goodBinding.good09);
                answerButtons.add(goodBinding.good10);
                answerButtons.add(goodBinding.good11);
                answerButtons.add(goodBinding.good12);
                return goodBinding.getRoot();
            case 3:
                niceBinding = FragmentDailyASecondNiceBinding.inflate(inflater, container, false);
                answerButtons.add(niceBinding.nice01);
                answerButtons.add(niceBinding.nice02);
                answerButtons.add(niceBinding.nice03);
                answerButtons.add(niceBinding.nice04);
                answerButtons.add(niceBinding.nice05);
                answerButtons.add(niceBinding.nice06);
                answerButtons.add(niceBinding.nice07);
                answerButtons.add(niceBinding.nice08);
                answerButtons.add(niceBinding.nice09);
                answerButtons.add(niceBinding.nice10);
                answerButtons.add(niceBinding.nice11);
                answerButtons.add(niceBinding.nice12);
                return niceBinding.getRoot();
            case 4:
                notBadBinding = FragmentDailyASecondNotbadBinding.inflate(inflater, container, false);
                answerButtons.add(notBadBinding.notBad01);
                answerButtons.add(notBadBinding.notBad02);
                answerButtons.add(notBadBinding.notBad03);
                answerButtons.add(notBadBinding.notBad04);
                answerButtons.add(notBadBinding.notBad05);
                answerButtons.add(notBadBinding.notBad06);
                answerButtons.add(notBadBinding.notBad07);
                answerButtons.add(notBadBinding.notBad08);
                answerButtons.add(notBadBinding.notBad09);
                answerButtons.add(notBadBinding.notBad10);
                answerButtons.add(notBadBinding.notBad11);
                answerButtons.add(notBadBinding.notBad12);
                answerButtons.add(notBadBinding.notBad13);
                return notBadBinding.getRoot();
            case 5:
                badBinding = FragmentDailyASecondBadBinding.inflate(inflater, container, false);
                answerButtons.add(badBinding.bad01);
                answerButtons.add(badBinding.bad02);
                answerButtons.add(badBinding.bad03);
                answerButtons.add(badBinding.bad04);
                answerButtons.add(badBinding.bad05);
                answerButtons.add(badBinding.bad06);
                answerButtons.add(badBinding.bad07);
                answerButtons.add(badBinding.bad08);
                answerButtons.add(badBinding.bad09);
                answerButtons.add(badBinding.bad10);
                answerButtons.add(badBinding.bad11);
                answerButtons.add(badBinding.bad12);
                return badBinding.getRoot();
        }

        return null;
    }

    private void setListener() {

        View.OnClickListener listener = null;

        switch (stepASelect) {
            case 1:
                listener = v -> {

                    int id = v.getId();

                    if (id == R.id.best01) {
                        currentBtn = 1;
                    } else if (id == R.id.best02) {
                        currentBtn = 2;
                    } else if (id == R.id.best03) {
                        currentBtn = 3;
                    } else if (id == R.id.best04) {
                        currentBtn = 4;
                    } else if (id == R.id.best05) {
                        currentBtn = 5;
                    } else if (id == R.id.best06) {
                        currentBtn = 6;
                    } else if (id == R.id.best07) {
                        currentBtn = 7;
                    } else if (id == R.id.best08) {
                        currentBtn = 8;
                    } else if (id == R.id.best09) {
                        currentBtn = 9;
                    } else if (id == R.id.best10) {
                        currentBtn = 10;
                    } else if (id == R.id.best11) {
                        currentBtn = 11;
                    } else if (id == R.id.best12) {
                        currentBtn = 12;
                    }

                    String answerText = DailyASecondAnswers.getAnswerText(stepASelect, currentBtn);

                    bestBinding.whatSelectTextView.setText(answerText);

                    highlightBtn();
                };
                break;
            case 2:
                listener = v -> {
                    int id = v.getId();

                    if (id == R.id.good01) {
                        currentBtn = 1;
                    } else if (id == R.id.good02) {
                        currentBtn = 2;
                    } else if (id == R.id.good03) {
                        currentBtn = 3;
                    } else if (id == R.id.good04) {
                        currentBtn = 4;
                    } else if (id == R.id.good05) {
                        currentBtn = 5;
                    } else if (id == R.id.good06) {
                        currentBtn = 6;
                    } else if (id == R.id.good07) {
                        currentBtn = 7;
                    } else if (id == R.id.good08) {
                        currentBtn = 8;
                    } else if (id == R.id.good09) {
                        currentBtn = 9;
                    } else if (id == R.id.good10) {
                        currentBtn = 10;
                    } else if (id == R.id.good11) {
                        currentBtn = 11;
                    } else if (id == R.id.good12) {
                        currentBtn = 12;
                    }

                    String answerText = DailyASecondAnswers.getAnswerText(stepASelect, currentBtn);

                    goodBinding.whatSelectTextView.setText(answerText);

                    highlightBtn();
                };
                break;
            case 3:
                listener = v -> {
                    int id = v.getId();

                    if (id == R.id.nice01) {
                        currentBtn = 1;
                    } else if (id == R.id.nice02) {
                        currentBtn = 2;
                    } else if (id == R.id.nice03) {
                        currentBtn = 3;
                    } else if (id == R.id.nice04) {
                        currentBtn = 4;
                    } else if (id == R.id.nice05) {
                        currentBtn = 5;
                    } else if (id == R.id.nice06) {
                        currentBtn = 6;
                    } else if (id == R.id.nice07) {
                        currentBtn = 7;
                    } else if (id == R.id.nice08) {
                        currentBtn = 8;
                    } else if (id == R.id.nice09) {
                        currentBtn = 9;
                    } else if (id == R.id.nice10) {
                        currentBtn = 10;
                    } else if (id == R.id.nice11) {
                        currentBtn = 11;
                    } else if (id == R.id.nice12) {
                        currentBtn = 12;
                    }

                    String answerText = DailyASecondAnswers.getAnswerText(stepASelect, currentBtn);

                    niceBinding.whatSelectTextView.setText(answerText);

                    highlightBtn();
                };
                break;
            case 4:
                listener = v -> {
                    int id = v.getId();

                    if (id == R.id.notBad01) {
                        currentBtn = 1;
                    } else if (id == R.id.notBad02) {
                        currentBtn = 2;
                    } else if (id == R.id.notBad03) {
                        currentBtn = 3;
                    } else if (id == R.id.notBad04) {
                        currentBtn = 4;
                    } else if (id == R.id.notBad05) {
                        currentBtn = 5;
                    } else if (id == R.id.notBad06) {
                        currentBtn = 6;
                    } else if (id == R.id.notBad07) {
                        currentBtn = 7;
                    } else if (id == R.id.notBad08) {
                        currentBtn = 8;
                    } else if (id == R.id.notBad09) {
                        currentBtn = 9;
                    } else if (id == R.id.notBad10) {
                        currentBtn = 10;
                    } else if (id == R.id.notBad11) {
                        currentBtn = 11;
                    } else if (id == R.id.notBad12) {
                        currentBtn = 12;
                    } else if (id == R.id.notBad13) {
                        currentBtn = 13;
                    }

                    String answerText = DailyASecondAnswers.getAnswerText(stepASelect, currentBtn);

                    notBadBinding.whatSelectTextView.setText(answerText);

                    highlightBtn();
                };
                break;
            case 5:

                listener = v -> {
                    int id = v.getId();

                    if (id == R.id.bad01) {
                        currentBtn = 1;
                    } else if (id == R.id.bad02) {
                        currentBtn = 2;
                    } else if (id == R.id.bad03) {
                        currentBtn = 3;
                    } else if (id == R.id.bad04) {
                        currentBtn = 4;
                    } else if (id == R.id.bad05) {
                        currentBtn = 5;
                    } else if (id == R.id.bad06) {
                        currentBtn = 6;
                    } else if (id == R.id.bad07) {
                        currentBtn = 7;
                    } else if (id == R.id.bad08) {
                        currentBtn = 8;
                    } else if (id == R.id.bad09) {
                        currentBtn = 9;
                    } else if (id == R.id.bad10) {
                        currentBtn = 10;
                    } else if (id == R.id.bad11) {
                        currentBtn = 11;
                    } else if (id == R.id.bad12) {
                        currentBtn = 12;
                    }

                    String answerText = DailyASecondAnswers.getAnswerText(stepASelect, currentBtn);

                    badBinding.whatSelectTextView.setText(answerText);

                    highlightBtn();
                };
                break;
        }

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