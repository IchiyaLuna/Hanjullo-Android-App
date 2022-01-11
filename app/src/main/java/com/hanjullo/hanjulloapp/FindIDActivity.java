package com.hanjullo.hanjulloapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hanjullo.hanjulloapp.databinding.ActivityFindIdBinding;
import com.hanjullo.hanjulloapp.databinding.ActivityLoginBinding;

import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FindIDActivity extends AppCompatActivity {

    ActivityFindIdBinding binding;
    EditText InputCodeEditText;
    EditText PhoneEditText;
    EditText NameEditText;
    Button CheckCodeBtn;
    Button GetCodeBtn;
    Button NextBtn;

    String OriginNameText;
    String NewNameText;
    String OriginPhoneText;
    String NewPhoneText;

    boolean isAuthRequested;
    boolean isAuthCompleted;
    String authCode;
    String username;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFindIdBinding.inflate(getLayoutInflater());
        final View view = binding.getRoot();
        setContentView(view);

        isAuthRequested = false;
        isAuthCompleted = false;

        setBinding();
        setListener();
        setTextWatcher();
        setInitAnim();
    }

    private void setBinding() {
        InputCodeEditText = binding.inputCodeEditText;
        PhoneEditText = binding.findPhoneEditText;
        NameEditText = binding.findNameEditText;
        CheckCodeBtn = binding.checkCodeBtn;
        GetCodeBtn = binding.getCodeBtn;
        NextBtn = binding.findIDNextBtn;
    }

    private void setListener() {
        View.OnClickListener listener = v -> {

            int id = v.getId();

            if (id == R.id.getCodeBtn) {

                boolean isValid = true;

                username = NameEditText.getText().toString();
                phone = PhoneEditText.getText().toString();

                if (username.replace(" ","").equals("")) {
                    NameEditText.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.bg_register_input_err));
                    isValid = false;
                }

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
            } else if (id == R.id.checkCodeBtn) {
                String inputCode = InputCodeEditText.getText().toString();

                if (inputCode.equals(authCode)) {
                    ExceptionToast.showExceptionToast(getApplicationContext(),"AUTH", "인증 성공!");
                    NameEditText.setEnabled(false);
                    PhoneEditText.setEnabled(false);
                    InputCodeEditText.setEnabled(false);
                    CheckCodeBtn.setEnabled(false);
                    isAuthCompleted = true;
                } else {
                    ExceptionToast.showExceptionToast(getApplicationContext(),"AUTH", "코드가 틀렸습니다 : " + authCode);
                }
            } else if (id == R.id.findIDNextBtn) {
                if (isAuthCompleted) {
                    Retrofit retrofit = RetrofitClient.getClient();
                    FindIDInterface findIDAPI = retrofit.create(FindIDInterface.class);
                    Call<FindIDPullDTO> call = findIDAPI.pushFindID("findid", username, phone);
                    call.enqueue(new Callback<FindIDPullDTO>() {
                        @Override
                        public void onResponse(@NonNull Call<FindIDPullDTO> call, @NonNull Response<FindIDPullDTO> response) {
                            if (!response.isSuccessful()) {
                                ExceptionToast.showExceptionToast(getApplicationContext(), "RESPONSE", "아이디 찾기 실패!");
                                return;
                            }

                            if (response.body().isSuccess()) {
                                Log.d("[auth]", "onResponse: pre");
                                Intent intent = new Intent(FindIDActivity.this, FindIDResultActivity.class);
                                intent.putExtra("id", response.body().getID());
                                startActivity(intent);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                Log.d("[auth]", "onResponse: aft");
                            } else {
                                ExceptionToast.showExceptionToast(getApplicationContext(), "RESPONSE", "아이디를 찾을 수 없습니다.");
                            }

                            finish();
                        }

                        @Override
                        public void onFailure(@NonNull Call<FindIDPullDTO> call, @NonNull Throwable t) {
                            ExceptionToast.showExceptionToast(getApplicationContext(), "SERVERCONERR", "서버 연결 실패!");
                        }
                    });
                } else {
                    ExceptionToast.showExceptionToast(getApplicationContext(),"AUTH", "인증이 필요합니다.");
                }
            }
        };

        CheckCodeBtn.setOnClickListener(listener);
        GetCodeBtn.setOnClickListener(listener);
        NextBtn.setOnClickListener(listener);
    }

    private void setTextWatcher() {

        NameEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (isAuthRequested) {
                    InputCodeEditText.setText("");
                    reverseAnim();
                    isAuthRequested = false;
                }

                OriginNameText = NameEditText.getText().toString();

                NewNameText = OriginNameText.replaceAll("[^a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣]","");

                if (!NewNameText.replace(" ","").equals("")) {
                    NameEditText.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.bg_register_input));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!NewNameText.equals(OriginNameText)) {
                    NameEditText.setText(NewNameText);
                    NameEditText.setSelection(NewNameText.length());
                }
            }
        });

        PhoneEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (isAuthRequested) {
                    InputCodeEditText.setText("");
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
    }

    private void setInitAnim() {
        InputCodeEditText.setVisibility(View.GONE);
        CheckCodeBtn.setVisibility(View.GONE);
        NextBtn.setVisibility(View.GONE);
    }

    private void startAnim() {

        int shortDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        InputCodeEditText.setAlpha(0f);
        CheckCodeBtn.setAlpha(0f);
        NextBtn.setAlpha(0f);

        InputCodeEditText.setVisibility(View.VISIBLE);
        CheckCodeBtn.setVisibility(View.VISIBLE);
        NextBtn.setVisibility(View.VISIBLE);

        InputCodeEditText.animate()
                .alpha(1f)
                .setDuration(shortDuration)
                .setListener(null);

        CheckCodeBtn.animate()
                .alpha(1f)
                .setDuration(shortDuration)
                .setListener(null);

        NextBtn.animate()
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

        NextBtn.animate()
                .alpha(0f)
                .setDuration(shortDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        NextBtn.setVisibility(View.GONE);
                    }
                });
    }
}