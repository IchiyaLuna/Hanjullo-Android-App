package com.hanjullo.hanjulloapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hanjullo.hanjulloapp.databinding.ActivityRegisterAccountBinding;
import com.hanjullo.hanjulloapp.databinding.ActivityRegisterNameBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterAccountActivity extends AppCompatActivity {

    ActivityRegisterAccountBinding binding;
    EditText PasswordCheckEditText;
    EditText PasswordEditText;
    EditText PhoneEditText;
    TextView NameTextView;
    Button RegisterNextBtn;
    Button PhoneCheckBtn;

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent pullIntent = getIntent();
        username = pullIntent.getStringExtra("username");

        binding = ActivityRegisterAccountBinding.inflate(getLayoutInflater());
        final View view = binding.getRoot();
        setContentView(view);

        setBinding();
        setListener();

        NameTextView.setText(username);
    }

    private void setBinding() {
        PasswordCheckEditText = binding.passwordCheckEditText;
        PasswordEditText = binding.passwordEditText;
        PhoneEditText = binding.phoneEditText;
        NameTextView = binding.nameTextView;
        RegisterNextBtn = binding.registerNextBtn;
        PhoneCheckBtn = binding.phoneCheckBtn;
    }

    private void setListener() {
        View.OnClickListener listener = v -> {

            int id = v.getId();

            if (id == R.id.registerNextBtn) {

            } else if (id == R.id.phoneCheckBtn) {

            }
        };

        RegisterNextBtn.setOnClickListener(listener);
        PhoneCheckBtn.setOnClickListener(listener);
    }
}