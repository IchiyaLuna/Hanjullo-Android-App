package com.hanjullo.hanjulloapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hanjullo.hanjulloapp.databinding.ActivityRegisterHelloBinding;

public class RegisterHelloActivity extends AppCompatActivity {

    ActivityRegisterHelloBinding binding;

    private Button HelloNextBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterHelloBinding.inflate(getLayoutInflater());
        final View view = binding.getRoot();
        setContentView(view);

        setBinding();
        setListener();
    }

    private void setBinding() {

        HelloNextBtn = binding.helloNextBtn;
    }

    private void setListener() {
        View.OnClickListener listener = v -> {

            int id = v.getId();

            if (id == R.id.helloNextBtn) {

                Intent intent = new Intent(RegisterHelloActivity.this, RegisterNameActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right_open, R.anim.slide_right_close);
            }
        };

        HelloNextBtn.setOnClickListener(listener);
    }

}