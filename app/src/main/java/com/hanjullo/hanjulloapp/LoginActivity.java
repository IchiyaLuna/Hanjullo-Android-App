package com.hanjullo.hanjulloapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.hanjullo.hanjulloapp.databinding.ActivityLoginBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private NetworkConnectionCheck network;

    private ActivityLoginBinding binding;

    private TextView ErrorMessageTextView;
    private TextView AutoLoginTextView;

    private CheckBox AutoLoginCheckBox;

    private EditText UserIDEditText;
    private EditText UserPWEditText;

    private Button LoginBtn;
    private Button RegisterBtn;
    private Button FindIDBtn;
    private Button FindPWBtn;

    private boolean isAutologin;

    private String originIDText;
    private String newIDText;

    private String originPWText;
    private String newPWText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        final View view = binding.getRoot();
        setContentView(view);

        setBinding();
        setTextWatcher();
        setListener();

        network = NetworkConnectionCheck.getInstance(getApplicationContext());
        network.register();
    }

    private void setBinding() {
        isAutologin = false;

        ErrorMessageTextView = binding.errorMessageTextView;
        AutoLoginTextView = binding.autoLoginTextView;

        AutoLoginCheckBox = binding.autoLoginCheckBox;

        UserIDEditText = binding.userIDEditText;
        UserPWEditText = binding.userPWEditText;

        LoginBtn = binding.loginBtn;
        RegisterBtn = binding.registerBtn;
        FindIDBtn = binding.findIdBtn;
        FindPWBtn = binding.findPwBtn;
    }

    private void setTextWatcher() {
        UserIDEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                originIDText = UserIDEditText.getText().toString();

                newIDText = originIDText.replaceAll("[^a-zA-Z0-9]","");

                if (!newIDText.replace(" ","").equals("")) {
                    UserIDEditText.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.bg_login_input));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!newIDText.equals(originIDText)) {
                    UserIDEditText.setText(newIDText);
                    UserIDEditText.setSelection(newIDText.length());
                }
            }
        });

        UserPWEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                originPWText = UserPWEditText.getText().toString();

                newPWText = originPWText.replaceAll("[^a-zA-Z0-9!@#$]","");

                if (!newPWText.replace(" ","").equals("")) {
                    UserIDEditText.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.bg_login_input));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!newPWText.equals(originPWText)) {
                    UserPWEditText.setText(newPWText);
                    UserPWEditText.setSelection(newPWText.length());
                }
            }
        });
    }

    private void setListener() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id = v.getId();

                if (id == R.id.loginBtn) {
                    String ID = UserIDEditText.getText().toString();
                    String PW = UserPWEditText.getText().toString();

                    if (ID.equals("") || PW.equals("")) {
                        if (ID.equals(""))
                            UserIDEditText.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.bg_login_input_err));
                        if (PW.equals(""))
                            UserPWEditText.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.bg_login_input_err));

                        ErrorMessageTextView.setText("아이디와 비밀번호를 모두 입력해 주세요");
                    } else {
                        Retrofit retrofit = RetrofitClient.getClient();
                        LoginInterface loginAPI = retrofit.create(LoginInterface.class);
                        Call<LoginPullDTO> call = loginAPI.pushLogin("login", ID, PW);
                        call.enqueue(new Callback<LoginPullDTO>() {
                            @Override
                            public void onResponse(@NonNull Call<LoginPullDTO> call, @NonNull Response<LoginPullDTO> response) {

                                if (!response.isSuccessful()) {
                                    ErrorMessageTextView.setText("로그인 실패");
                                    return;
                                }

                                if (response.body().isSuccess()) {
                                    UserData userData = UserData.getInstance();
                                    userData.setCredential(ID, PW);
                                    userData.setUserName(response.body().getUsername());

                                    Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                                    finish();
                                } else {
                                    ErrorMessageTextView.setText("로그인 실패");
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<LoginPullDTO> call, @NonNull Throwable t) {
                                ErrorMessageTextView.setText("서버 연결 실패");
                            }
                        });
                    }
                } else if (id == R.id.registerBtn) {
                    Intent intent = new Intent(LoginActivity.this, RegisterHelloActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                } else if (id == R.id.findIdBtn) {

                } else if (id == R.id.findPwBtn) {

                } else if (id == R.id.autoLoginTextView) {
                    AutoLoginCheckBox.setChecked(!isAutologin);
                    isAutologin = !isAutologin;
                } else if (id == R.id.autoLoginCheckBox) {
                    isAutologin = !isAutologin;
                }
            }
        };

        LoginBtn.setOnClickListener(listener);
        RegisterBtn.setOnClickListener(listener);
        FindIDBtn.setOnClickListener(listener);
        FindPWBtn.setOnClickListener(listener);
        AutoLoginTextView.setOnClickListener(listener);
        AutoLoginCheckBox.setOnClickListener(listener);
    }
}