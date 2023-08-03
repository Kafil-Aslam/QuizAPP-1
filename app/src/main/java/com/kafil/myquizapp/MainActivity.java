package com.kafil.myquizapp;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView questionNumberTextView;
    private TextView questionTextView;

    private RadioGroup optionsRadioGroup;
    private RadioButton option1RadioButton;
    private RadioButton option2RadioButton;
    private RadioButton option3RadioButton;
    private RadioButton option4RadioButton;
    private Button submitButton;
    private Button nextButton;


    private TextView feedbackTextView;
    private TextView scoreTextView;
    private TextView timerTextView;



    private List<Question> questions;
    private int currentQuestionIndex;
    private int score;
    private int totalQuestions;
    private boolean answered;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;
    private static final long COUNTDOWN_TIME = 30000; // 30 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // giving id and initializing
        questionNumberTextView = findViewById(R.id.questionNumberTextView);
        questionTextView = findViewById(R.id.questionTextView);
        optionsRadioGroup = findViewById(R.id.optionsRadioGroup);
        option1RadioButton = findViewById(R.id.option1RadioButton);
        option2RadioButton = findViewById(R.id.option2RadioButton);
        option3RadioButton = findViewById(R.id.option3RadioButton);
        option4RadioButton = findViewById(R.id.option4RadioButton);
        submitButton = findViewById(R.id.submitButton);
        nextButton = findViewById(R.id.nextButton);
        feedbackTextView = findViewById(R.id.feedbackTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timerTextView);


        // here we can add as many questions as required
        questions = new ArrayList<>();
        questions.add(new Question("Who won the FIFA World Cup in 2018?", "France", "Brazil", "Germany", "Argentina", "France"));
        questions.add(new Question("Which player has the most Ballon d'Or awards?", "Lionel Messi", "Cristiano Ronaldo", "Michel Platini", "Johan Cruyff", "Lionel Messi"));
        questions.add(new Question("Which country hosted the first-ever FIFA World Cup in 1930?", "Brazil", "Uruguay", "Italy", "Argentina", "Uruguay"));
        questions.add(new Question("Which club has won the most UEFA Champions League titles?", "Real Madrid", "Barcelona", "Bayern Munich", "Liverpool", "Real Madrid"));
        questions.add(new Question("Who is the all-time leading goalscorer in the UEFA Champions League?", "Cristiano Ronaldo", "Lionel Messi", "Robert Lewandowski", "Raul", "Cristiano Ronaldo"));
        questions.add(new Question("Which country won the UEFA Euro 2020 tournament?", "Portugal", "France", "Italy", "Germany", "Italy"));
        questions.add(new Question("Who is the current coach of the England national football team?", "Gareth Southgate", "Jurgen Klopp", "Pep Guardiola", "Zinedine Zidane", "Gareth Southgate"));
        questions.add(new Question("Which country won the Copa America 2021 tournament?", "Brazil", "Argentina", "Uruguay", "Chile", "Argentina"));
        questions.add(new Question("Which player holds the record for the most goals scored in a single FIFA World Cup?", "Pele", "Ronaldo", "Miroslav Klose", "Gerd Muller", "Just Fontaine"));
        questions.add(new Question("In which city is the Camp Nou stadium located?", "Barcelona", "Madrid", "Manchester", "Milan", "Barcelona"));
        questions.add(new Question("Which club has won the most English Premier League titles?", "Manchester United", "Liverpool", "Arsenal", "Chelsea", "Manchester United"));
        questions.add(new Question("Which country won the FIFA World Cup in 2014?", "Spain", "Argentina", "Germany", "Brazil", "Germany"));
        questions.add(new Question("Which player has won the most FIFA Ballon d'Or awards?", "Lionel Messi", "Cristiano Ronaldo", "Zinedine Zidane", "Pele", "Lionel Messi"));
        questions.add(new Question("Which team won the UEFA Champions League in the 2020-2021 season?", "Manchester City", "Real Madrid", "Chelsea", "Bayern Munich", "Chelsea"));
        questions.add(new Question("Who is the all-time leading scorer in the history of the English Premier League?", "Thierry Henry", "Alan Shearer", "Wayne Rooney", "Andy Cole", "Alan Shearer"));
        questions.add(new Question("Which club has won the most Serie A titles in Italy?", "Inter Milan", "AC Milan", "AS Roma", "Juventus", "Juventus"));
        questions.add(new Question("Who is the top scorer in the history of the Copa del Rey competition?", "Cristiano Ronaldo", "Lionel Messi", "Telmo Zarra", "Hugo Sanchez", "Telmo Zarra"));
        questions.add(new Question("Which country has won the most FIFA World Cup titles?", "Brazil", "Germany", "Italy", "Argentina", "Brazil"));
        questions.add(new Question("Which player has the most assists in the history of the English Premier League?", "Frank Lampard", "Cesc Fabregas", "Ryan Giggs", "Wayne Rooney", "Ryan Giggs"));
        questions.add(new Question("Which club won the UEFA Europa League in the 2020-2021 season?", "Manchester United", "Sevilla", "AS Roma", "Arsenal", "Villarreal"));

        totalQuestions = questions.size();
        Collections.shuffle(questions);
        currentQuestionIndex = 0;
        score = 0;
        updateScore();


        // THIS CODE IS USED TO HAVE A TIMER
        timeLeftInMillis = COUNTDOWN_TIME;
        startTimer();


        displayQuestion();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!answered) {
                    checkAnswer();
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!answered) {
                    Toast.makeText(MainActivity.this, "Please select an answer.", Toast.LENGTH_SHORT).show();
                } else {
                    showNextQuestion();
                }
            }
        });
    }

    private void displayQuestion() {
        if (currentQuestionIndex < totalQuestions) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            questionNumberTextView.setText(getString(R.string.question_number, currentQuestionIndex + 1, totalQuestions));
            questionTextView.setText(currentQuestion.getQuestion());
            option1RadioButton.setText(currentQuestion.getOption1());
            option2RadioButton.setText(currentQuestion.getOption2());
            option3RadioButton.setText(currentQuestion.getOption3());
            option4RadioButton.setText(currentQuestion.getOption4());


            optionsRadioGroup.clearCheck();

            optionsRadioGroup.setEnabled(true);
            option1RadioButton.setEnabled(true);
            option2RadioButton.setEnabled(true);
            option3RadioButton.setEnabled(true);
            option4RadioButton.setEnabled(true);
            feedbackTextView.setText("");
            submitButton.setEnabled(true);
            nextButton.setVisibility(View.GONE);

            answered = false;
        } else {
            showResult();
        }
    }

    private void checkAnswer() {
        Question currentQuestion = questions.get(currentQuestionIndex);
        int selectedOptionId = optionsRadioGroup.getCheckedRadioButtonId();

        if (selectedOptionId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedOptionId);
            String selectedAnswer = selectedRadioButton.getText().toString();
            if (selectedAnswer.equals(currentQuestion.getCorrectAnswer())) {
                score++;
                updateScore();
                feedbackTextView.setText(getString(R.string.correct_answer_feedback));
            } else {
                feedbackTextView.setText(getString(R.string.incorrect_answer_feedback, currentQuestion.getCorrectAnswer()));
            }

            option1RadioButton.setEnabled(false);
            option2RadioButton.setEnabled(false);
            option3RadioButton.setEnabled(false);
            option4RadioButton.setEnabled(false);

            submitButton.setEnabled(false);
            nextButton.setVisibility(View.VISIBLE);

            answered = true;
        }else {

            Toast.makeText(this, "Please select an option.", Toast.LENGTH_SHORT).show();
        }
    }


    private void showNextQuestion() {
    currentQuestionIndex++;
    if (currentQuestionIndex < totalQuestions) {
        displayQuestion();
        resetTimer();
    } else {
        showResult();
    }
}

    private void resetTimer() {
        countDownTimer.cancel();
        timeLeftInMillis = COUNTDOWN_TIME;
        startTimer();
    }



private void showResult() {
    questionNumberTextView.setVisibility(View.GONE);
    questionTextView.setVisibility(View.GONE);
    optionsRadioGroup.setVisibility(View.GONE);
    submitButton.setVisibility(View.GONE);
    feedbackTextView.setVisibility(View.GONE);
    timerTextView.setVisibility(View.GONE);

    setContentView(R.layout.activity_score);

    TextView finalScoreTextView = findViewById(R.id.finalScoreTextView);
    Button playAgainButton = findViewById(R.id.playAgainButton);
    Button exitButton = findViewById(R.id.exitButton);

    finalScoreTextView.setText(getString(R.string.final_score_text, score, totalQuestions));

    playAgainButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            resetQuiz();
            recreate();
        }
    });


    exitButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finishAffinity();
        }
    });
}




    private void updateScore() {
        scoreTextView.setText(getString(R.string.score_text, score));
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateTimer();
                if (!answered) {
                    Toast.makeText(MainActivity.this, "Time's up! Moving to the next question.", Toast.LENGTH_SHORT).show();
                    checkAnswer(); // Check the answer immediately when time's up
                }
                showResult(); // Show the final score state
            }

        }.start();
    }

    private void updateTimer() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timerTextView.setText(timeLeftFormatted);

        if (timeLeftInMillis < 10000) {
            timerTextView.setTextColor(getResources().getColor(android.R.color.holo_red_light));
        } else {
            timerTextView.setTextColor(getResources().getColor(android.R.color.black));
        }
    }

    public class Question {
        private String questionText;
        private String option1;
        private String option2;
        private String option3;
        private String option4;
        private String correctAnswer;

        public Question(String questionText, String option1, String option2, String option3, String option4, String correctAnswer) {
            this.questionText = questionText;
            this.option1 = option1;
            this.option2 = option2;
            this.option3 = option3;
            this.option4 = option4;
            this.correctAnswer = correctAnswer;
        }

        public String getQuestion() {
            return questionText;
        }

        public String getOption1() {
            return option1;
        }

        public String getOption2() {
            return option2;
        }

        public String getOption3() {
            return option3;
        }

        public String getOption4() {
            return option4;
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }
    }
    private void resetQuiz() {
        currentQuestionIndex = 0;
        score = 0;
        updateScore();
        Collections.shuffle(questions);
        displayQuestion();

        resetTimer();
    }

}
