package com.hanjullo.hanjulloapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hanjullo.hanjulloapp.databinding.ActivityFindIdResultBinding;

public class FindIDResultActivity extends AppCompatActivity {

    ActivityFindIdResultBinding binding;

    TextView ResultIDTextView;
    Button LoginBtn;
    Button FindPWBtn;

    String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFindIdResultBinding.inflate(getLayoutInflater());
        final View view = binding.getRoot();
        setContentView(view);

        Intent pullIntent = getIntent();
        ID = pullIntent.getStringExtra("id");

        setBinding();
        setListener();

        ResultIDTextView.setText(ID);
    }

    private void setBinding() {
        ResultIDTextView = binding.resultIDTextView;
        LoginBtn = binding.resultIDLoginBtn;
        FindPWBtn = binding.resultIDFindPWBtn;
    }

    private void setListener() {
        View.OnClickListener listener = v -> {

            int id = v.getId();

            if (id == R.id.resultIDLoginBtn) {
                Intent intent = new Intent(FindIDResultActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            } else if (id == R.id.resultIDFindPWBtn) {
                Intent intent = new Intent(FindIDResultActivity.this, FindPWActivity.class);
                intent.putExtra("ID", ID);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        };

        LoginBtn.setOnClickListener(listener);
        FindPWBtn.setOnClickListener(listener);
    }
}