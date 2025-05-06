package com.example.task31;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private TextView textViewCongrats, textViewScore;
    private Button buttonTakeNewQuiz, buttonFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        textViewCongrats = findViewById(R.id.textViewCongrats);
        textViewScore = findViewById(R.id.textViewScore);
        buttonTakeNewQuiz = findViewById(R.id.buttonTakeNewQuiz);
        buttonFinish = findViewById(R.id.buttonFinish);

        String userName = getIntent().getStringExtra("userName");
        int score = getIntent().getIntExtra("score", 0);
        int total = getIntent().getIntExtra("total", 0);

        if (score >= 4) {
            textViewCongrats.setText("Congratulations, " + userName + "!");
        } else {
            textViewCongrats.setText("Better luck next time, " + userName + "!");
        }

        textViewScore.setText("Your score: " + score + "/" + total);

        buttonTakeNewQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, MainActivity.class);
            intent.putExtra("userName", userName);
            startActivity(intent);
            finish();
        });

        buttonFinish.setOnClickListener(v -> finishAffinity());
    }
}
