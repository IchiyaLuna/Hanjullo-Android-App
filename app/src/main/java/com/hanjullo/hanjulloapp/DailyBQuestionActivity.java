package com.hanjullo.hanjulloapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hanjullo.hanjulloapp.databinding.ActivityDailyAQuestionBinding;
import com.hanjullo.hanjulloapp.databinding.ActivityDailyBQuestionBinding;

public class DailyBQuestionActivity extends AppCompatActivity {

    ActivityDailyBQuestionBinding binding;
    TextView QuestionTextView;
    TextView CharNumTextView;
    EditText AnswerEditText;
    Button NextBtn;

    Bundle answersBundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDailyBQuestionBinding.inflate(getLayoutInflater());
        final View view = binding.getRoot();
        setContentView(view);

        Intent pullIntent = getIntent();
        answersBundle = pullIntent.getBundleExtra("answersBundle");

        setBinding();

        NextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setBinding() {
        QuestionTextView = binding.stepBQuestionTextView;
        CharNumTextView = binding.charNumTextView;
        AnswerEditText = binding.stepBAnswerEditText;
        NextBtn = binding.stepBNextBtn;
    }
}