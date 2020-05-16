package com.example.workouttimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static java.lang.StrictMath.ceil;

public class Timer extends AppCompatActivity {
    int noOfRounds, workTime, restTime;
    TextView currentRoundView, timeLeft;
    CountDownTimer timer, restTimer;
    Button pauseButton;
    boolean isPaused = false;
    int currentRound = 1;
    long remainingTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        init();
        startPreTimer();
    }
    private void init() {
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        noOfRounds = bundle.getInt("rounds");
        workTime = bundle.getInt("workTime") * 1000;
        restTime = bundle.getInt("restTime") * 1000;
        pauseButton = findViewById(R.id.pauseButton);
        timer = null;
        currentRoundView = findViewById(R.id.currentRound);
        timeLeft = findViewById(R.id.timeLeft);
        Log.d("DDDDDDebug","Rounds"+ noOfRounds);
        Log.d("DDDDDDebug","work"+ workTime);
        Log.d("DDDDDDebug","rest"+ restTime);

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPaused) {
                    pauseButton.setText("Pause");
                    if (restTimer == null) {
                        startTimer(remainingTime);
                    } else {
                        startRestTimer(remainingTime);
                    }
                } else {
                    pauseButton.setText("Resume");
                }
                isPaused = !isPaused;

            }
        });
    }
    private void startTimer(final long time) {
        setCurrentRoundText(currentRound+"");
        timer = new CountDownTimer(time + 1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(isPaused) {
                    remainingTime = millisUntilFinished;
                    cancel();
                }
                int timeLeft = (int)ceil(millisUntilFinished/1000);
                if (timeLeft == 0) {
                    onFinish();
                    cancel();
                }
                setTimerText("" + timeLeft);
            }
            @Override
            public void onFinish() {
                if(currentRound == noOfRounds){
                    setTimerText("Finished");
                } else {
                    currentRound++;
                    startRestTimer(restTime);
                }
                timer = null;
            }
        }.start();
    }


    private void startPreTimer() {
        restTimer =  new CountDownTimer(6000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(isPaused) {
                    remainingTime = millisUntilFinished;
                    cancel();
                }
                int timeLeft = (int)ceil(millisUntilFinished/1000);
                if (timeLeft == 0) {
                    onFinish();
                    cancel();
                }
                setTimerText("" + timeLeft * -1);
            }
            @Override
            public void onFinish() {
                startTimer(workTime);
                restTimer = null;
            }
        }.start();
    }

    private void startRestTimer(final long time) {
        restTimer = new CountDownTimer(time + 1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(isPaused) {
                    remainingTime = millisUntilFinished;
                    cancel();
                }
                int timeLeft = (int)ceil(millisUntilFinished/1000);
                if (timeLeft == 0) {
                    onFinish();
                    cancel();
                }
                setTimerText("" + timeLeft);
            }
            @Override
            public void onFinish() {
                startTimer(workTime);
            }
        }.start();
    }
    private void setTimerText(String text) {
        Log.d("DDDDDDebug","time:  "+ text);

        timeLeft.setText(text);
    }

    private void setCurrentRoundText(String text) {
        currentRoundView.setText(text);
    }
}
