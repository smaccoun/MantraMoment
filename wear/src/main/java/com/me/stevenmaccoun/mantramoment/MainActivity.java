package com.me.stevenmaccoun.mantramoment;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends WearableActivity implements View.OnClickListener {

    private static final SimpleDateFormat AMBIENT_DATE_FORMAT =
            new SimpleDateFormat("HH:mm", Locale.US);

    private BoxInsetLayout mContainerView;
    private TextView mTextView;
    private TextView mClockView;

    private TextView countDownTimerT;
    private Button countdownB;

    private RepeatCountdownTimer repeatCountdownTimer;
    private long startTimeMillis = 15000;
    private static final long countdownIntervalMillis = 1000;
    private final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    private enum TIMER_BUTTON_STATE
    {
        START,
        RESET
    };
    private TIMER_BUTTON_STATE currentButtonState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAmbientEnabled();

        //mContainerView = (BoxInsetLayout) findViewById(R.id.container);
        //mClockView = (TextView) findViewById(R.id.clock);


        countDownTimerT = (TextView) this.findViewById(R.id.countdownTimer);
        countDownTimerT.setText(String.valueOf(sdf.format(startTimeMillis)));
        repeatCountdownTimer = new RepeatCountdownTimer(startTimeMillis, countdownIntervalMillis);

        countdownB = (Button) this.findViewById(R.id.button);
        countdownB.setOnClickListener(this);
        currentButtonState = TIMER_BUTTON_STATE.START;
        countdownB.setText(currentButtonState.toString());

    }

    @Override
    public void onClick(View v) {
        switch (currentButtonState)
        {
            case START:
            {
                repeatCountdownTimer.start();
                currentButtonState = TIMER_BUTTON_STATE.RESET;
                countdownB.setText(currentButtonState.toString());
                break;
            }

            case RESET:
            {
                repeatCountdownTimer.cancel();
                currentButtonState = TIMER_BUTTON_STATE.START;
                countdownB.setText(currentButtonState.toString());
                countDownTimerT.setText(String.valueOf(sdf.format(startTimeMillis)));
                break;
            }
        }
    }

    public class RepeatCountdownTimer extends CountDownTimer {


        public RepeatCountdownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            countDownTimerT.setText(sdf.format(millisUntilFinished));
        }

        @Override
        public void onFinish() {
            Context context = getApplicationContext();
            CharSequence text = "I EXIST";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            this.start();
        }
    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);
        updateDisplay();
    }

    @Override
    public void onUpdateAmbient() {
        super.onUpdateAmbient();
        updateDisplay();
    }

    @Override
    public void onExitAmbient() {
        updateDisplay();
        super.onExitAmbient();
    }

    private void updateDisplay() {
//        if (isAmbient()) {
//            mContainerView.setBackgroundColor(getResources().getColor(android.R.color.black));
//            mTextView.setTextColor(getResources().getColor(android.R.color.white));
//            mClockView.setVisibility(View.VISIBLE);
//
//            mClockView.setText(AMBIENT_DATE_FORMAT.format(new Date()));
//        } else {
//            mContainerView.setBackground(null);
//            mTextView.setTextColor(getResources().getColor(android.R.color.black));
//            mClockView.setVisibility(View.GONE);
//        }
    }
}
