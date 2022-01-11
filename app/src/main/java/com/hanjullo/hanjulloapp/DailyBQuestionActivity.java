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

        StringBuilder buffer = new StringBuilder();
        buffer.append("왜 ");
        buffer.append(DailyAThirdAnswers.getAnswerText(answersBundle.getInt("answerC")));
        buffer.append(" 때문에\n오늘이 ");

        switch (answersBundle.getInt("answerA")) {
            case 1:
                buffer.append("최고였던 것 같아?");
                break;
            case 2:
                buffer.append("좋았던 것 같아?");
                break;
            case 3:
                buffer.append("괜찮았던 것 같아?");
                break;
            case 4:
                buffer.append("그냥 그랬던 것 같아?");
                break;
            case 5:
                buffer.append("별로였던 것 같아?");
                break;
        }

        QuestionTextView.setText(buffer.toString());

        NextBtn.setOnClickListener(v -> {
            if (AnswerEditText.getText().toString().replace(" ","").equals("")) {
                AnswerEditText.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.bg_answer_input_err));
            } else {

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
                    AnswerEditText.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.bg_answer_input));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setBinding() {
        QuestionTextView = binding.stepBQuestionTextView;
        CharNumTextView = binding.charNumTextView;
        AnswerEditText = binding.stepBAnswerEditText;
        NextBtn = binding.stepBNextBtn;
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