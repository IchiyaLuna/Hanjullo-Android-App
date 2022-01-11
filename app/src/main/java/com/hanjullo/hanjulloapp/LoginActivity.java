package com.hanjullo.hanjulloapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hanjullo.hanjulloapp.databinding.ActivityLoginBinding;

import java.io.IOException;
import java.security.GeneralSecurityException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private long backPressedTime;

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

        Intent intent = getIntent();

        if (intent.getBundleExtra("UserData") != null) {
            Bundle receivedBundle = intent.getBundleExtra("UserData");
            String ID = receivedBundle.getString("ID", "");
            String PW = receivedBundle.getString("PW", "");

            if (!ID.equals("")) UserIDEditText.setText(ID);
            if (!PW.equals("")) UserPWEditText.setText(PW);
        }

        UserPWEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo
                    .IME_ACTION_DONE) {
                String ID = UserIDEditText.getText().toString();
                String PW = UserPWEditText.getText().toString();

                if (ID.equals("") || PW.equals("")) {
                    if (ID.equals(""))
                        UserIDEditText.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.bg_login_input_err));
                    if (PW.equals(""))
                        UserPWEditText.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.bg_login_input_err));

                    ErrorMessageTextView.setText("아이디와 비밀번호를 모두 입력해 주세요");
                } else {
                    doLogin(ID, PW);
                }
            } else {
                return false;
            }
            return true;
        });
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
        View.OnClickListener listener = v -> {

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
                    doLogin(ID, PW);
                }
            } else if (id == R.id.registerBtn) {
                Intent intent = new Intent(LoginActivity.this, RegisterHelloActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            } else if (id == R.id.findIdBtn) {
                Intent intent = new Intent(LoginActivity.this, FindIDActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            } else if (id == R.id.findPwBtn) {
                Intent intent = new Intent(LoginActivity.this, FindPWActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            } else if (id == R.id.autoLoginTextView) {
                AutoLoginCheckBox.setChecked(!isAutologin);
                isAutologin = !isAutologin;
            } else if (id == R.id.autoLoginCheckBox) {
                isAutologin = !isAutologin;
            }
        };

        LoginBtn.setOnClickListener(listener);
        RegisterBtn.setOnClickListener(listener);
        FindIDBtn.setOnClickListener(listener);
        FindPWBtn.setOnClickListener(listener);
        AutoLoginTextView.setOnClickListener(listener);
        AutoLoginCheckBox.setOnClickListener(listener);
    }

    private void doLogin(String ID, String PW) {
        Retrofit retrofit = RetrofitClient.getClient();
        LoginInterface loginAPI = retrofit.create(LoginInterface.class);
        Call<LoginPullDTO> call = loginAPI.pushLogin("login", ID, PW);
        call.enqueue(new Callback<LoginPullDTO>() {
            @Override
            public void onResponse(@NonNull Call<LoginPullDTO> call, @NonNull Response<LoginPullDTO> response) {

                if (!response.isSuccessful() || response.body() == null) {
                    ErrorMessageTextView.setText("로그인 실패");
                    return;
                }

                if (response.body().isSuccess()) {

                    try {
                        setAutoLogin(AutoLoginCheckBox.isChecked(), ID, PW);
                    } catch (GeneralSecurityException | IOException e) {
                        ErrorMessageTextView.setText("자동 로그인 설정 실패");
                    }

                    UserData userData = UserData.getInstance();
                    userData.setCredential(ID, PW);
                    userData.setUserName(response.body().getUsername());
                    userData.setLoginState(true);

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

    private void setAutoLogin(boolean isAutologin, String ID, String PW) throws GeneralSecurityException, IOException {
        MasterKey masterKey = new MasterKey.Builder(getApplicationContext(), MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build();


        SharedPreferences userLoginPreferences = EncryptedSharedPreferences
                .create(getApplicationContext(),
                        "LoginInfo",
                        masterKey,
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);

        SharedPreferences.Editor editor = userLoginPreferences.edit();

        if (isAutologin) {
            editor.putBoolean("is_autologin", true);
            editor.putString("user_id", ID);
            editor.putString("user_pw", PW);
            editor.apply();
        } else {
            editor.putBoolean("is_autologin", false);
        }

    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backPressedTime + 2000) {
            backPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "'뒤로' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        } else if (System.currentTimeMillis() <= backPressedTime + 2000) {
            finishAffinity();
        }
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