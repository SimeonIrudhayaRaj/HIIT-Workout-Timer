package com.example.workouttimer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    EditText workTime,noOfRounds,restTime;
    TextView timerText;
    Context context;
    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        workTime = findViewById(R.id.roundTimerTextBox);
        noOfRounds = findViewById(R.id.roundsTextBox);
        restTime = findViewById(R.id.restTimerTextBox);
        context = this;
    }
    public void start(View view) {
        int numberOfRounds = getTextAsIntFrom(noOfRounds);
        int rest = getTextAsIntFrom(restTime);
        int work = getTextAsIntFrom(workTime);
        Intent intent = new Intent(this, Timer.class);
        Bundle bundle = new Bundle();
        bundle.putInt("rounds",numberOfRounds);
        bundle.putInt("workTime",work);
        bundle.putInt("restTime",rest);
        intent.putExtra("rounds",numberOfRounds);
        intent.putExtra("workTime",work);
        intent.putExtra("restTime",rest);
        this.startActivity(intent, bundle);

    }


    private int getTextAsIntFrom(EditText view) {
        String limitString = view.getText().toString();
        if (limitString.isEmpty()){
            return  0;
        }
        int limit = Integer.parseInt(limitString);
        return  limit;
    }

}
