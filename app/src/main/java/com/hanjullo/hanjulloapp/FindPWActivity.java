package com.hanjullo.hanjulloapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hanjullo.hanjulloapp.databinding.ActivityFindPwBinding;

import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FindPWActivity extends AppCompatActivity {

    ActivityFindPwBinding binding;
    EditText InputCodeEditText;
    EditText FirstEditText;
    EditText SecondEditText;
    TextView TitleTextView;
    TextView DescTextView;
    Button CheckCodeBtn;
    Button GetCodeBtn;

    String OriginFirstText;
    String NewFirstText;
    String OriginSecondText;
    String NewSecondText;

    boolean isAuthRequested;
    boolean isAuthCompleted;
    boolean isChangeFinished;
    String authCode;
    String ID;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFindPwBinding.inflate(getLayoutInflater());
        final View view = binding.getRoot();
        setContentView(view);

        isAuthRequested = false;
        isAuthCompleted = false;
        isChangeFinished = false;

        setBinding();
        setListener();
        setTextWatcher();
        setInitAnim();

        Intent intent = getIntent();

        if (intent.getStringExtra("ID") != null) {
            String ID = intent.getStringExtra("ID");

            if (!ID.equals("")) FirstEditText.setText(ID);
        }
    }

    private void setBinding() {
        InputCodeEditText = binding.inputCodeEditText;
        FirstEditText = binding.findPWFirstEditText;
        SecondEditText = binding.findPWSecondEditText;
        TitleTextView = binding.findPWTitleTextView;
        DescTextView = binding.findPWDescTextView;
        CheckCodeBtn = binding.pwCheckCodeBtn;
        GetCodeBtn = binding.pwGetCodeBtn;
    }

    private void setListener() {
        View.OnClickListener listener = v -> {

            int id = v.getId();

            if (id == R.id.pwGetCodeBtn) {

                if (isChangeFinished) {
                    Bundle bundle = new Bundle();
                    bundle.putString("ID", ID);

                    Intent intent = new Intent(FindPWActivity.this, LoginActivity.class);
                    intent.putExtra("UserData", bundle);

                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }

                if (isAuthCompleted) {
                    String PW = FirstEditText.getText().toString();

                    if (PW.replace(" ", "").equals("")) {
                        FirstEditText.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.bg_register_input_err));
                        SecondEditText.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.bg_register_input_err));
                        ExceptionToast.showExceptionToast(getApplicationContext(),"PASS", "비밀번호를 입력해주세요.");
                        return;
                    }

                    if (PW.equals(SecondEditText.getText().toString())) {
                        Retrofit retrofit = RetrofitClient.getClient();
                        FindPWInterface findPWAPI = retrofit.create(FindPWInterface.class);
                        Call<FindPWPullDTO> call = findPWAPI.pushFindPW("resetpw", ID, PW, phone);
                        call.enqueue(new Callback<FindPWPullDTO>() {
                            @Override
                            public void onResponse(@NonNull Call<FindPWPullDTO> call, @NonNull Response<FindPWPullDTO> response) {
                                if (!response.isSuccessful() || response.body() == null) {
                                    ExceptionToast.showExceptionToast(getApplicationContext(), "RESPONSE", "비밀번호 찾기 실패!");
                                    return;
                                }

                                if (response.body().isSuccess()) {
                                    isChangeFinished = true;
                                    TitleTextView.setText("비밀번호 변경 완료");
                                    DescTextView.setText("새로운 비밀번호로 로그인 하세요");

                                    GetCodeBtn.setText("로그인 하기");
                                    hideAnim();
                                } else {
                                    ExceptionToast.showExceptionToast(getApplicationContext(), "RESPONSE", "해당하는 계정이 없습니다." + response.body().getResult());
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<FindPWPullDTO> call, @NonNull Throwable t) {
                                ExceptionToast.showExceptionToast(getApplicationContext(), "SERVERCONERR", "서버 연결 실패!");
                            }
                        });
                    } else {
                        FirstEditText.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.bg_register_input_err));
                        SecondEditText.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.bg_register_input_err));
                        ExceptionToast.showExceptionToast(getApplicationContext(),"PASS", "비밀번호가 일치하지 않습니다.");
                    }
                } else {
                    boolean isValid = true;

                    ID = FirstEditText.getText().toString();
                    phone = SecondEditText.getText().toString();

                    if (ID.replace(" ","").equals("")) {
                        FirstEditText.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.bg_register_input_err));
                        isValid = false;
                    }

                    if (phone.replace(" ", "").equals("")) {
                        SecondEditText.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.bg_register_input_err));
                        isValid = false;
                    }

                    if (phone.length() < 11 ||  !phone.startsWith("010")) {
                        SecondEditText.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.bg_register_input_err));
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
            } else if (id == R.id.pwCheckCodeBtn) {
                String inputCode = InputCodeEditText.getText().toString();

                if (inputCode.equals(authCode)) {

                    Retrofit retrofit = RetrofitClient.getClient();
                    FindPWInterface findPWAPI = retrofit.create(FindPWInterface.class);
                    Call<FindPWPullDTO> call = findPWAPI.pushFindPW("check", ID, "", phone);
                    call.enqueue(new Callback<FindPWPullDTO>() {
                        @Override
                        public void onResponse(@NonNull Call<FindPWPullDTO> call, @NonNull Response<FindPWPullDTO> response) {
                            if (!response.isSuccessful() || response.body() == null) {
                                ExceptionToast.showExceptionToast(getApplicationContext(), "RESPONSE", "비밀번호 찾기 실패!");
                                return;
                            }

                            if (response.body().isSuccess()) {
                                isAuthCompleted = true;
                                TitleTextView.setText("비밀번호 재설정");
                                DescTextView.setText("새로운 비밀번호를 입력해주세요");
                                FirstEditText.setHint("새로운 비밀번호");
                                SecondEditText.setHint("새로운 비밀번호 확인");
                                FirstEditText.setText("");
                                SecondEditText.setText("");
                                Typeface font = FirstEditText.getTypeface();
                                FirstEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                                SecondEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                                FirstEditText.setTypeface(font);
                                SecondEditText.setTypeface(font);
                                FirstEditText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(80)});
                                SecondEditText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(80)});
                                GetCodeBtn.setText("비밀번호 변경");
                                reverseAnim();
                            } else {
                                ExceptionToast.showExceptionToast(getApplicationContext(), "RESPONSE", "해당하는 계정이 없습니다.");
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<FindPWPullDTO> call, @NonNull Throwable t) {
                            ExceptionToast.showExceptionToast(getApplicationContext(), "SERVERCONERR", "서버 연결 실패!");
                        }
                    });
                } else {
                    ExceptionToast.showExceptionToast(getApplicationContext(),"AUTH", "코드가 틀렸습니다 : " + authCode);
                }
            }
        };

        CheckCodeBtn.setOnClickListener(listener);
        GetCodeBtn.setOnClickListener(listener);
    }

    private void setTextWatcher() {

        FirstEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                OriginFirstText = FirstEditText.getText().toString();

                if (isAuthCompleted) {

                    NewFirstText = OriginFirstText.replaceAll("[^a-zA-Z0-9!@#$]","");

                } else {

                    if (isAuthRequested) {
                        InputCodeEditText.setText("");
                        reverseAnim();
                        isAuthRequested = false;
                    }

                    NewFirstText = OriginFirstText.replaceAll("[^a-zA-Z0-9]","");

                }

                if (!NewFirstText.replace(" ","").equals("")) {
                    FirstEditText.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.bg_register_input));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!NewFirstText.equals(OriginFirstText)) {
                    FirstEditText.setText(NewFirstText);
                    FirstEditText.setSelection(NewFirstText.length());
                }
            }
        });

        SecondEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                OriginSecondText = SecondEditText.getText().toString();

                if (isAuthCompleted) {

                    NewSecondText = OriginSecondText.replaceAll("[^a-zA-Z0-9!@#$]","");

                } else {

                    if (isAuthRequested) {
                        InputCodeEditText.setText("");
                        reverseAnim();
                        isAuthRequested = false;
                    }

                    NewSecondText = OriginSecondText.replaceAll("[^0-9]","");
                }

                if (!NewSecondText.replace(" ","").equals("")) {
                    SecondEditText.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.bg_register_input));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!NewSecondText.equals(OriginSecondText)) {
                    SecondEditText.setText(NewSecondText);
                    SecondEditText.setSelection(NewSecondText.length());
                }
            }
        });
    }

    private void setInitAnim() {
        InputCodeEditText.setVisibility(View.GONE);
        CheckCodeBtn.setVisibility(View.GONE);
    }

    private void startAnim() {

        int shortDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        InputCodeEditText.setAlpha(0f);
        CheckCodeBtn.setAlpha(0f);

        InputCodeEditText.setVisibility(View.VISIBLE);
        CheckCodeBtn.setVisibility(View.VISIBLE);

        InputCodeEditText.animate()
                .alpha(1f)
                .setDuration(shortDuration)
                .setListener(null);

        CheckCodeBtn.animate()
                .alpha(1f)
                .setDuration(shortDuration)
                .setListener(null);

        GetCodeBtn.animate()
                .alpha(0f)
                .setDuration(shortDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        GetCodeBtn.setVisibility(View.GONE);
                    }
                });
    }

    private void reverseAnim() {

        int shortDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        GetCodeBtn.setAlpha(0f);

        GetCodeBtn.setVisibility(View.VISIBLE);

        GetCodeBtn.animate()
                .alpha(1f)
                .setDuration(shortDuration)
                .setListener(null);

        InputCodeEditText.animate()
                .alpha(0f)
                .setDuration(shortDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        InputCodeEditText.setVisibility(View.GONE);
                    }
                });

        CheckCodeBtn.animate()
                .alpha(0f)
                .setDuration(shortDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        CheckCodeBtn.setVisibility(View.GONE);
                    }
                });
    }

    private void hideAnim() {

        int shortDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        FirstEditText.animate()
                .alpha(0f)
                .setDuration(shortDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        FirstEditText.setVisibility(View.GONE);
                    }
                });

        SecondEditText.animate()
                .alpha(0f)
                .setDuration(shortDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        SecondEditText.setVisibility(View.GONE);
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