package com.hanjullo.hanjulloapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
    String OriginPhoneText;
    String NewPhoneText;

    boolean isPhoneChecked;

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
        setTextWatcher();

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

    private void setTextWatcher() {

        PhoneEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                OriginPhoneText = PhoneEditText.getText().toString();

                NewPhoneText = OriginPhoneText.replaceAll("[^0-9]","");

                if (!NewPhoneText.replace(" ","").equals("")) {
                    PhoneEditText.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.bg_register_input));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!NewPhoneText.equals(OriginPhoneText)) {
                    PhoneEditText.setText(NewPhoneText);
                    PhoneEditText.setSelection(NewPhoneText.length());
                }
            }
        });

    }
}