package com.hanjullo.hanjulloapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.hanjullo.hanjulloapp.databinding.ActivityTosBinding;

public class ToSActivity extends AppCompatActivity {

    ActivityTosBinding binding;

    TextView PrivacyTitleTextView;
    TextView EULATitleTextView;

    TextView PrivacyContentTextView;
    TextView EULAContentTextView;

    CheckBox PrivacyCheckBox;
    CheckBox EULACheckBox;

    Button NextBtn;

    boolean isPrivacyAgreed;
    boolean isEULAAgreed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTosBinding.inflate(getLayoutInflater());
        final View view = binding.getRoot();
        setContentView(view);

        isPrivacyAgreed = false;
        isEULAAgreed = false;

        setBinding();
        setListener();

        PrivacyContentTextView.setMovementMethod(new ScrollingMovementMethod());
        EULAContentTextView.setMovementMethod(new ScrollingMovementMethod());
    }

    private void setBinding() {
        PrivacyTitleTextView = binding.tosPrivacyTextView;
        EULATitleTextView = binding.tosEULATextView;

        PrivacyContentTextView = binding.tosPrivacyContentTextView;
        EULAContentTextView = binding.tosEULAContentTextView;

        PrivacyCheckBox = binding.tosPrivacyCheckBox;
        EULACheckBox = binding.tosEULACheckBox;

        NextBtn = binding.tosNextBtn;
    }

    private void setListener() {
        View.OnClickListener listener = v -> {
            int id = v.getId();

            if (id == R.id.tosPrivacyTextView) {
                PrivacyCheckBox.setChecked(!isPrivacyAgreed);
                isPrivacyAgreed = !isPrivacyAgreed;
            } else if (id == R.id.tosPrivacyCheckBox) {
                isPrivacyAgreed = !isPrivacyAgreed;
            } else if (id == R.id.tosEULATextView) {
                EULACheckBox.setChecked(!isEULAAgreed);
                isEULAAgreed = !isEULAAgreed;
            } else if (id == R.id.tosEULACheckBox) {
                isEULAAgreed = !isEULAAgreed;
            } else if (id == R.id.tosNextBtn) {
                if (isPrivacyAgreed && isEULAAgreed) {
                    Intent intent = new Intent(ToSActivity.this, WelcomeActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                } else {
                    ExceptionToast.showExceptionToast(getApplicationContext(), "TOS", "이용 약관에 모두 동의해 주세요");
                }
            }
        };

        PrivacyTitleTextView.setOnClickListener(listener);
        PrivacyCheckBox.setOnClickListener(listener);
        EULATitleTextView.setOnClickListener(listener);
        EULACheckBox.setOnClickListener(listener);
        NextBtn.setOnClickListener(listener);
    }
}