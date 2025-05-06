package com.example.task31;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;  // ✅ Added for tinting

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView textViewQuestion;
    private Button[] optionButtons = new Button[4];
    private Button buttonSubmit, buttonNext;
    private ProgressBar progressBar;

    private List<Question> questionList;
    private int currentQuestionIndex = 0;
    private int selectedAnswerIndex = -1;
    private int score = 0;

    private String userName;

    private Drawable defaultButtonBackground;  // ✅ Store default background

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        textViewQuestion = findViewById(R.id.textViewQuestion);
        optionButtons[0] = findViewById(R.id.buttonOption1);
        optionButtons[1] = findViewById(R.id.buttonOption2);
        optionButtons[2] = findViewById(R.id.buttonOption3);
        optionButtons[3] = findViewById(R.id.buttonOption4);

        buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonNext = findViewById(R.id.buttonNext);
        progressBar = findViewById(R.id.progressBar);

        // ✅ Capture the default button background
        defaultButtonBackground = optionButtons[0].getBackground();

        userName = getIntent().getStringExtra("userName");

        setupQuestions();
        showQuestion();

        for (int i = 0; i < optionButtons.length; i++) {
            final int index = i;
            optionButtons[i].setOnClickListener(v -> {
                selectedAnswerIndex = index;
                highlightSelectedOption(index);
            });
        }

        buttonSubmit.setOnClickListener(v -> submitAnswer());
        buttonNext.setOnClickListener(v -> nextQuestion());
    }

    private void setupQuestions() {
        questionList = new ArrayList<>();
        questionList.add(new Question("Which planet is closest to the sun?",
            new String[]{"Venus", "Earth", "Mercury", "Mars"}, 2));
        questionList.add(new Question("What is the capital of Canada?",
            new String[]{"Toronto", "Vancouver", "Ottawa", "Montreal"}, 2));
        questionList.add(new Question("What is the longest river in the world?",
            new String[]{"Amazon", "Yangtze", "Mississippi", "Nile"}, 3));
        questionList.add(new Question("What is the main language spoken in Brazil?",
            new String[]{"Spanish", "Portuguese", "French", "English"}, 1));
        questionList.add(new Question("What country has won the most FIFA World Cups?",
            new String[]{"Brazil", "Germany", "Italy", "Argentina"}, 0));

        progressBar.setMax(questionList.size());
    }

    private void showQuestion() {
        resetOptions();
        selectedAnswerIndex = -1;

        Question q = questionList.get(currentQuestionIndex);
        textViewQuestion.setText(q.getQuestionText());
        String[] options = q.getOptions();

        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i].setText(options[i]);
        }

        progressBar.setProgress(currentQuestionIndex);
        buttonSubmit.setVisibility(View.VISIBLE);
        buttonNext.setVisibility(View.GONE);
    }

    private void highlightSelectedOption(int index) {
        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i].setBackgroundTintList(i == index
                ? ColorStateList.valueOf(Color.LTGRAY)
                : null);
        }
    }

    private void submitAnswer() {
        if (selectedAnswerIndex == -1) {
            Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
            return;
        }

        Question q = questionList.get(currentQuestionIndex);

        if (selectedAnswerIndex == q.getCorrectAnswerIndex()) {
            optionButtons[selectedAnswerIndex].setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            score++;
        } else {
            optionButtons[selectedAnswerIndex].setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            optionButtons[q.getCorrectAnswerIndex()].setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
        }

        for (Button btn : optionButtons) {
            btn.setEnabled(false);
        }

        buttonSubmit.setVisibility(View.GONE);
        buttonNext.setVisibility(View.VISIBLE);
    }

    private void nextQuestion() {
        currentQuestionIndex++;

        if (currentQuestionIndex < questionList.size()) {
            showQuestion();
        } else {
            Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
            intent.putExtra("userName", userName);
            intent.putExtra("score", score);
            intent.putExtra("total", questionList.size());
            startActivity(intent);
            finish();
        }
    }

    private void resetOptions() {
        for (Button btn : optionButtons) {
            btn.setBackgroundTintList(null);  // ✅ Clear any applied tint
            btn.setEnabled(true);
        }
    }
}
