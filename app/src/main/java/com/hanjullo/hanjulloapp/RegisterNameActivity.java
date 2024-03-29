package com.hanjullo.hanjulloapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.hanjullo.hanjulloapp.databinding.ActivityRegisterNameBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterNameActivity extends AppCompatActivity {

    ActivityRegisterNameBinding binding;
    EditText NameEditText;
    Button NameNextBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterNameBinding.inflate(getLayoutInflater());
        final View view = binding.getRoot();
        setContentView(view);

        setBinding();
        setListener();
    }

    private void setBinding() {

        NameEditText = binding.nameEditText;
        NameNextBtn = binding.nameNextBtn;
    }

    private void setListener() {
        View.OnClickListener listener = v -> {

            int id = v.getId();

            if (id == R.id.nameNextBtn) {

                if (NameEditText.getText().toString().replace(" ","").equals("")) {
                    NameEditText.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.bg_name_input_err));
                    return;
                }

                if (NameEditText.getText().toString().length() <= 10) {
                    Retrofit retrofit = RetrofitClient.getClient();
                    CheckNameInterface checkNameAPI = retrofit.create(CheckNameInterface.class);
                    Call<CheckNamePullDTO> call = checkNameAPI.pushCheckName("check", NameEditText.getText().toString());
                    call.enqueue(new Callback<CheckNamePullDTO>() {
                        @Override
                        public void onResponse(@NonNull Call<CheckNamePullDTO> call, @NonNull Response<CheckNamePullDTO> response) {
                            if (!response.isSuccessful() | response.body() == null) {
                                ExceptionToast.showExceptionToast(getApplicationContext(), "SERVERCONERR", "활동명 검사 실패!");
                                return;
                            }

                            if (response.body().isSuccess()) {
                                NameEditText.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.bg_name_input));
                                Intent intent = new Intent(RegisterNameActivity.this, RegisterAccountActivity.class);
                                intent.putExtra("username", NameEditText.getText().toString());
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_right_open, R.anim.slide_right_close);
                            } else {
                                NameEditText.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.bg_name_input_err));
                                ExceptionToast.showExceptionToast(getApplicationContext(), "RESPONSE", "이미 사용중인 활동명입니다.");
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<CheckNamePullDTO> call, @NonNull Throwable t) {
                            ExceptionToast.showExceptionToast(getApplicationContext(), "SERVERCONERR", "서버 연결 실패!");
                        }
                    });
                } else {
                    NameEditText.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.bg_name_input_err));
                    ExceptionToast.showExceptionToast(getApplicationContext(), "USERINPUTERR", "활동명은 최대 10글자까지 가능합니다.");
                }
            }
        };

        NameNextBtn.setOnClickListener(listener);
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