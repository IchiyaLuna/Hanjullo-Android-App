package com.hanjullo.hanjulloapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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

import com.hanjullo.hanjulloapp.databinding.ActivityRegisterAccountBinding;

import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterAccountActivity extends AppCompatActivity {

    ActivityRegisterAccountBinding binding;
    EditText PasswordCheckEditText;
    EditText PasswordEditText;
    EditText AuthCodeEditText;
    EditText PhoneEditText;
    EditText IDEditText;
    TextView NameTextView;
    Button RegisterNextBtn;
    Button PhoneCheckBtn;
    Button IDCheckBtn;

    String username;
    String phone;
    String authCode;
    String ID;

    String OriginPhoneText;
    String NewPhoneText;
    String originIDText;
    String newIDText;

    boolean isPhoneChecked;
    boolean isIDChecked;

    boolean isAuthRequested;

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
        setInitAnim();

        NameTextView.setText(username);
    }

    private void setBinding() {
        PasswordCheckEditText = binding.passwordCheckEditText;
        PasswordEditText = binding.passwordEditText;
        AuthCodeEditText = binding.authCodeEditText;
        PhoneEditText = binding.phoneEditText;
        IDEditText = binding.idEditText;
        NameTextView = binding.nameTextView;
        RegisterNextBtn = binding.registerNextBtn;
        PhoneCheckBtn = binding.phoneCheckBtn;
        IDCheckBtn = binding.idCheckBtn;
    }

    private void setListener() {
        View.OnClickListener listener = v -> {

            int id = v.getId();

            if (id == R.id.registerNextBtn) {
                if (isPhoneChecked) {
                    if (isIDChecked) {

                        String PW = PasswordEditText.getText().toString();

                        if (PW.replace(" ", "").equals("")) {
                            PasswordEditText.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.bg_register_input_err));
                            PasswordCheckEditText.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.bg_register_input_err));
                            ExceptionToast.showExceptionToast(getApplicationContext(),"PASS", "비밀번호를 입력해주세요.");
                            return;
                        }

                        if (PW.equals(PasswordCheckEditText.getText().toString())) {

                            Retrofit retrofit = RetrofitClient.getClient();
                            RegisterInterface registerAPI = retrofit.create(RegisterInterface.class);
                            Call<RegisterPullDTO> call = registerAPI.pushRegister("reg", ID, PW, username, phone);
                            call.enqueue(new Callback<RegisterPullDTO>() {
                                @Override
                                public void onResponse(@NonNull Call<RegisterPullDTO> call, @NonNull Response<RegisterPullDTO> response) {
                                    if (!response.isSuccessful() | response.body() == null) {
                                        ExceptionToast.showExceptionToast(getApplicationContext(), "SERVERCONERR", "회원가입 실패!");
                                        return;
                                    }

                                    if (response.body().isSuccess()) {
                                        Bundle bundle = new Bundle();
                                        bundle.putString("ID", ID);
                                        bundle.putString("PW", PW);

                                        Intent intent = new Intent(RegisterAccountActivity.this, LoginActivity.class);
                                        intent.putExtra("UserData", bundle);

                                        startActivity(intent);
                                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                        finish();
                                    } else {
                                        ExceptionToast.showExceptionToast(getApplicationContext(), "RESPONSE", "회원가입 실패!" + response.body().getResult());
                                    }
                                }

                                @Override
                                public void onFailure(@NonNull Call<RegisterPullDTO> call, @NonNull Throwable t) {
                                    ExceptionToast.showExceptionToast(getApplicationContext(), "SERVERCONERR", "서버 연결 실패!");
                                }
                            });

                        } else {
                            PasswordEditText.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.bg_register_input_err));
                            PasswordCheckEditText.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.bg_register_input_err));
                            ExceptionToast.showExceptionToast(getApplicationContext(),"PASS", "비밀번호가 일치하지 않습니다.");
                        }
                    } else {
                        IDEditText.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.bg_register_input_err));
                        ExceptionToast.showExceptionToast(getApplicationContext(),"AUTH", "아이디 확인을 눌러주세요.");
                    }
                } else {
                    PhoneEditText.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.bg_register_input_err));
                    ExceptionToast.showExceptionToast(getApplicationContext(),"AUTH", "휴대폰 인증이 필요합니다.");
                }
            } else if (id == R.id.phoneCheckBtn) {

                if (isAuthRequested) {
                    String inputCode = AuthCodeEditText.getText().toString();

                    if (inputCode.equals(authCode)) {
                        ExceptionToast.showExceptionToast(getApplicationContext(),"AUTH", "인증 성공!");
                        PhoneCheckBtn.setText("완료");
                        PhoneEditText.setEnabled(false);
                        AuthCodeEditText.setEnabled(false);
                        PhoneCheckBtn.setEnabled(false);
                        isPhoneChecked = true;
                    } else {
                        ExceptionToast.showExceptionToast(getApplicationContext(),"AUTH", "코드가 틀렸습니다 : " + authCode);
                    }
                } else {
                    boolean isValid = true;

                    AuthCodeEditText.setText("");
                    phone = PhoneEditText.getText().toString();

                    if (phone.replace(" ", "").equals("")) {
                        PhoneEditText.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.bg_register_input_err));
                        isValid = false;
                    }

                    if (phone.length() < 11 ||  !phone.startsWith("010")) {
                        PhoneEditText.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.bg_register_input_err));
                        isValid = false;
                    }

                    if (isValid) {
                        Random random = new Random();
                        authCode = String.format(Locale.KOREA, "%06d", random.nextInt(999999));
                        ExceptionToast.showExceptionToast(getApplicationContext(),"AUTH", authCode);
                        isAuthRequested = true;
                        startAnim();
                    }
                }
            } else if (id == R.id.idCheckBtn) {
                boolean isValid = true;

                ID = IDEditText.getText().toString();

                if (ID.replace(" ", "").equals("")) {
                    PhoneEditText.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.bg_register_input_err));
                    isValid = false;
                }

                if (isValid) {
                    Retrofit retrofit = RetrofitClient.getClient();
                    CheckIdInterface checkIdAPI = retrofit.create(CheckIdInterface.class);
                    Call<CheckIdPullDTO> call = checkIdAPI.pushCheckId("check", ID);
                    call.enqueue(new Callback<CheckIdPullDTO>() {
                        @Override
                        public void onResponse(@NonNull Call<CheckIdPullDTO> call, @NonNull Response<CheckIdPullDTO> response) {
                            if (!response.isSuccessful() | response.body() == null) {
                                ExceptionToast.showExceptionToast(getApplicationContext(), "SERVERCONERR", "아이디 검사 실패!");
                                return;
                            }

                            if (response.body().isSuccess()) {
                                IDEditText.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.bg_register_input));
                                IDCheckBtn.setText("완료");
                                IDCheckBtn.setEnabled(false);
                                isIDChecked = true;
                                ExceptionToast.showExceptionToast(getApplicationContext(), "RESPONSE", "사용 가능한 아이디입니다.");
                            } else {
                                IDEditText.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.bg_register_input_err));
                                ExceptionToast.showExceptionToast(getApplicationContext(), "RESPONSE", "이미 사용중인 아이디입니다.");
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<CheckIdPullDTO> call, @NonNull Throwable t) {
                            ExceptionToast.showExceptionToast(getApplicationContext(), "SERVERCONERR", "서버 연결 실패!");
                        }
                    });
                }
            }
        };

        RegisterNextBtn.setOnClickListener(listener);
        PhoneCheckBtn.setOnClickListener(listener);
        IDCheckBtn.setOnClickListener(listener);
    }

    private void setTextWatcher() {

        PhoneEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (isAuthRequested) {
                    AuthCodeEditText.setText("");
                    reverseAnim();
                    isAuthRequested = false;
                }

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

        IDEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (isIDChecked) {
                    IDCheckBtn.setText("확인");
                    IDCheckBtn.setEnabled(true);
                    isIDChecked = false;
                }

                originIDText = IDEditText.getText().toString();

                newIDText = originIDText.replaceAll("[^a-zA-Z0-9]","");

                if (!newIDText.replace(" ","").equals("")) {
                    IDEditText.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.bg_register_input));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!newIDText.equals(originIDText)) {
                    IDEditText.setText(newIDText);
                    IDEditText.setSelection(newIDText.length());
                }
            }
        });
    }

    private void setInitAnim() {
        AuthCodeEditText.setVisibility(View.GONE);
    }

    private void startAnim() {

        int shortDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        AuthCodeEditText.setAlpha(0f);

        AuthCodeEditText.setVisibility(View.VISIBLE);

        PhoneCheckBtn.setText("확인");

        AuthCodeEditText.animate()
                .alpha(1f)
                .setDuration(shortDuration)
                .setListener(null);
    }

    private void reverseAnim() {

        int shortDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        PhoneCheckBtn.setText("인증");

        AuthCodeEditText.animate()
                .alpha(0f)
                .setDuration(shortDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        AuthCodeEditText.setVisibility(View.GONE);
                    }
                });
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