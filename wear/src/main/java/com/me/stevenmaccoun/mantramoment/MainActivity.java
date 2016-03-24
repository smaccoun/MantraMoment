package com.me.stevenmaccoun.mantramoment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends WearableActivity implements View.OnClickListener {

    private static final SimpleDateFormat AMBIENT_DATE_FORMAT =
            new SimpleDateFormat("HH:mm:ss", Locale.US);

    private RelativeLayout mContainerView;
    private TextView mTextView;
    private TextView mClockView;

    private TextView countDownTimerT;
    private Button countdownB;
    private Button plusThirtyB;
    private TextView currentMantraT;

    private RepeatCountdownTimer repeatCountdownTimer;
    private long startTimeMillis = 30000;
    private static final long countdownIntervalMillis = 1000;
    private DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
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

        mContainerView = (RelativeLayout) findViewById(R.id.container);
        //mClockView = (TextView) findViewById(R.id.clock);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date d = new Date(startTimeMillis);
        String startTime = sdf.format(d);

        countDownTimerT = (TextView) this.findViewById(R.id.countdownTimer);
        countDownTimerT.setText(String.valueOf(startTime));
        repeatCountdownTimer = new RepeatCountdownTimer(startTimeMillis, countdownIntervalMillis);

        countdownB = (Button) this.findViewById(R.id.button);
        countdownB.setOnClickListener(this);
        currentButtonState = TIMER_BUTTON_STATE.START;
        countdownB.setText(currentButtonState.toString());

        plusThirtyB = (Button) this.findViewById(R.id.button2);
        plusThirtyB.setOnClickListener(this);

        currentMantraT = (TextView) this.findViewById(R.id.currentMantra);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case (R.id.button):
            {
                switch (currentButtonState)
                {
                    case START:
                    {
                        repeatCountdownTimer.start();
                        currentButtonState = TIMER_BUTTON_STATE.RESET;
                        countdownB.setText(currentButtonState.toString());
                        setDisplayModeConserve();
                        break;
                    }

                    case RESET:
                    {
                        resetTimer();
                        setDisplayModeInteractive();
                        break;
                    }
                }

                break;
            }

            case (R.id.button2):
            {
                repeatCountdownTimer.cancel();
                startTimeMillis += 30000;
                repeatCountdownTimer = new RepeatCountdownTimer(startTimeMillis, countdownIntervalMillis);
                resetTimer();
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
            CharSequence currentMantra = MantraGenerator.getRandomDefaultMantra();
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, currentMantra, duration);
            toast.show();

            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            long[] vibrationPattern = {0, 500, 50, 300};
            //-1 - don't repeat
            final int indexInPatternToRepeat = -1;
            vibrator.vibrate(vibrationPattern, indexInPatternToRepeat);
            currentMantraT.setText(currentMantra);

            this.start();
        }
    }

    public void resetTimer(){
        repeatCountdownTimer.cancel();
        currentButtonState = TIMER_BUTTON_STATE.START;
        countdownB.setText(currentButtonState.toString());
        countDownTimerT.setText(String.valueOf(sdf.format(startTimeMillis)));
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

    private void setDisplayModeConserve(){
        RelativeLayout bgElement = (RelativeLayout) findViewById(R.id.container);
        bgElement.setBackgroundColor(Color.BLACK);
        countDownTimerT.setTextColor(Color.GREEN);
        countdownB.setBackgroundColor(Color.BLACK);
        countdownB.setTextColor(Color.LTGRAY);
        plusThirtyB.setVisibility(View.INVISIBLE);
        currentMantraT.setTextColor(Color.LTGRAY);
        currentMantraT.setVisibility(View.VISIBLE);
    }

    private void setDisplayModeInteractive() {
        RelativeLayout bgElement = (RelativeLayout) findViewById(R.id.container);
        bgElement.setBackgroundColor(Color.WHITE);
        countDownTimerT.setTextColor(Color.BLACK);
        plusThirtyB.setVisibility(View.VISIBLE);
        currentMantraT.setVisibility(View.GONE);
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
