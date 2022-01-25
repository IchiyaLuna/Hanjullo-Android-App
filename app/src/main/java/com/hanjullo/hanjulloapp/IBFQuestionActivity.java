package com.hanjullo.hanjulloapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hanjullo.hanjulloapp.databinding.ActivityIbfQuestionBinding;

public class IBFQuestionActivity extends AppCompatActivity {

    ActivityIbfQuestionBinding binding;
    TextView TitleTextView;
    TextView QuestionTextView;
    TextView CharNumTextView;
    EditText AnswerEditText;
    Button NextBtn;

    int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIbfQuestionBinding.inflate(getLayoutInflater());
        final View view = binding.getRoot();
        setContentView(view);

        Intent pullIntent = getIntent();
        mode = pullIntent.getIntExtra("IBF",0);

        setBinding();

        switch (mode) {
            case 1:
                TitleTextView.setText("INSIDE ME");
                QuestionTextView.setText("'나' 하면 떠오르는 단어는?");
                break;
            case 2:
                TitleTextView.setText("BEHIND ME");
                QuestionTextView.setText("1년 전 내게 해주고 싶은 말?");
                break;
            case 3:
                TitleTextView.setText("IN FRONT OF ME");
                QuestionTextView.setText("내가 꿈꾸는 집의 모습은?");
                break;

        }

        NextBtn.setOnClickListener(v -> {
            if (AnswerEditText.getText().toString().replace(" ","").equals("")) {
                AnswerEditText.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.bg_ibf_answer_input_err));
            } else {
                Intent intent = new Intent(IBFQuestionActivity.this, ProfileActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });

        AnswerEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = AnswerEditText.getText().toString();
                String lengthText = input.length() + "/140";
                CharNumTextView.setText(lengthText);

                if (!input.replace(" ","").equals("")) {
                    AnswerEditText.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.bg_ibf_answer_input));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setBinding() {
        TitleTextView = binding.ibfTitleTextView;
        CharNumTextView = binding.charNumTextView;
        QuestionTextView = binding.ibfQuestionTextView;
        AnswerEditText = binding.ibfAnswerEditText;
        NextBtn = binding.ibfNextBtn;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View focusView = getCurrentFocus();
        if (focusView != null) {
            Rect rect = new Rect();
            focusView.getGlobalVisibleRect(rect);
            int x = (int) ev.getX(), y = (int) ev.getY();
            if (!rect.contains(x, y)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null)
                    imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
                focusView.clearFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}