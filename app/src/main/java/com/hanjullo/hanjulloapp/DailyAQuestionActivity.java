package com.hanjullo.hanjulloapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hanjullo.hanjulloapp.databinding.ActivityDailyAQuestionBinding;

public class DailyAQuestionActivity extends AppCompatActivity {

    ActivityDailyAQuestionBinding binding;

    private int currentStep;
    private int stepASelect;
    private int stepBSelect;

    TextView QuestionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDailyAQuestionBinding.inflate(getLayoutInflater());
        final View view = binding.getRoot();
        setContentView(view);

        QuestionTextView = binding.todayQuestionText;

        FragmentSwitch(1);

        binding.stepANextBtn.setOnClickListener(v -> {

            switch (currentStep) {
                case 1:
                    if (getStepASel()) {
                        FragmentSwitch(2);
                    }

                    break;
                case 2:
                    if (getStepBSel()) {
                        FragmentSwitch(3);
                    }

                    break;
            }
        });
    }

    private void FragmentSwitch(int fragCode) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (fragCode) {
            case 1:
                DailyAFirstFragment stepAFrag = new DailyAFirstFragment();

                transaction.replace(R.id.fragFrame, stepAFrag);

                transaction.commit();

                QuestionTextView.setText(R.string.question_A_text);

                currentStep = 1;
                break;
            case 2:
                Bundle bundle = new Bundle();
                bundle.putInt("stepASelect", stepASelect);

                DailyASecondFragment stepBFrag = new DailyASecondFragment();
                stepBFrag.setArguments(bundle);

                transaction.replace(R.id.fragFrame, stepBFrag);
                transaction.commit();

                QuestionTextView.setText(R.string.question_B_text);

                currentStep = 2;
                break;
            case 3:
                DailyAThirdFragment stepCFrag = new DailyAThirdFragment();

                transaction.replace(R.id.fragFrame, stepCFrag);

                transaction.commit();

                String questionText = "";

                switch (stepASelect) {
                    case 1:
                        questionText = "무엇이 나의 하루를\n최고로 만들었어?";
                        break;
                    case 2:
                        questionText = "무엇이 나의 하루를\n좋게 만들었어?";
                        break;
                    case 3:
                        questionText = "무엇이 나의 하루를\n괜찮게 만들었어?";
                        break;
                    case 4:
                        questionText = "무엇이 나의 하루를\n그저 그렇게 만들었어?";
                        break;
                    case 5:
                        questionText = "무엇이 나의 하루를\n별로로 만들었어?";
                        break;
                }

                QuestionTextView.setText(questionText);

                currentStep = 3;
            default:
                break;
        }
    }

    private boolean getStepASel() {
        DailyAFirstFragment stepAFrag = (DailyAFirstFragment) getSupportFragmentManager().findFragmentById(R.id.fragFrame);
        stepASelect = stepAFrag.currentBtn;

        return stepASelect != 0;
    }

    private boolean getStepBSel() {
        DailyASecondFragment stepBFrag = (DailyASecondFragment) getSupportFragmentManager().findFragmentById(R.id.fragFrame);
        stepBSelect = stepBFrag.currentBtn;

        return stepBSelect != 0;
    }

    private void getStepCSel() {
        DailyAThirdFragment stepCFrag = (DailyAThirdFragment) getSupportFragmentManager().findFragmentById(R.id.fragFrame);
    }
}