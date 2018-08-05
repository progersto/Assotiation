package com.natife.assotiation.game;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.natife.assotiation.R;
import com.natife.assotiation.choose_how_play.UtilForDraw.PaintView;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class GameActivity extends AppCompatActivity implements GameContract.View {
    private GameContract.Presenter mPresenter;
    private String name;
    private int colorPlayer;
    private List<String> listWords;
    private String howExplain;
    private TextView textTimerDraw;
    private TextView whoseTurn;
    private TextView drawClear;
    private RelativeLayout timer;
    private ProgressBar circularProgressbar;
    private TextView textTimer;
    private LinearLayout layoutBtnFromTellAndShow;
    private RelativeLayout theyGuessed;
    private RelativeLayout theyNotGuessed;
    private RelativeLayout remindWord;
    private LinearLayout layoutBtnPlayer;
    private CountDownTimer mCountDownTimer;
    private String word;
    private PaintView paintView;
    private RelativeLayout buttonAction;
    private RelativeLayout buttonPointBrush;
    private boolean flagShowBtn;
    private RelativeLayout layoutForDraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        howExplain = getIntent().getStringExtra("how_explain");
        name = getIntent().getStringExtra("name");
        colorPlayer = getIntent().getIntExtra("colorPlayer", 0);
        listWords = getIntent().getStringArrayListExtra("listWords");
        word =  getIntent().getStringExtra("word");
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showView(howExplain);
    }

    private void showView(String howExplain) {

        whoseTurn.setTextColor(ContextCompat.getColor(this, colorPlayer));
        switch (howExplain) {
            case "tell":
                whoseTurn.setText(String.format("%s %s", getResources().getString(R.string.describes), name));
                selectedTellOrShow();
                break;
            case "show":
                whoseTurn.setText(String.format("%s %s", getResources().getString(R.string.shows), name));
                selectedTellOrShow();
                break;
            case "draw":
                whoseTurn.setText(String.format("%s %s", getResources().getString(R.string.draws), name));
                selectedDraw();
                break;
        }
    }

    private void selectedDraw() {
        flagShowBtn = false;
        layoutForDraw.setVisibility(View.VISIBLE);
        layoutBtnFromTellAndShow.setVisibility(View.GONE);
        textTimerDraw.setVisibility(View.VISIBLE);
        drawClear.setVisibility(View.VISIBLE);
        initTimer(textTimerDraw);

        paintView = findViewById(R.id.paintView);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        paintView.init(metrics);
        paintView.normal();
//        paintView.emboss();//чеканка
//        paintView.blur();//пятно
    }

    private void selectedTellOrShow() {
        flagShowBtn = true;
        timer.setVisibility(View.VISIBLE);
        layoutBtnFromTellAndShow.setVisibility(View.VISIBLE);
        initTimer(textTimer);
    }

    private void initTimer(TextView textTimer) {
        mCountDownTimer = new CountDownTimer(61 * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                Log.v("Log_tag", "Tick of Progress" + millisUntilFinished);

                int iii = (60 - ((int) millisUntilFinished / 1000));
                circularProgressbar.setProgress(iii);

                long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished));
                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished));

                textTimer.setText(String.format(Locale.getDefault(), "%01d:%02d", minutes, seconds));
            }

            @Override
            public void onFinish() {

            }
        };
        mCountDownTimer.start();
    }

    private void initView() {
        textTimerDraw = findViewById(R.id.text_timer_draw);
        whoseTurn = findViewById(R.id.whose_turn);
        drawClear = findViewById(R.id.draw_clear);
        drawClear.setOnClickListener(view -> paintView.clear());
        timer = findViewById(R.id.timer);
        circularProgressbar = findViewById(R.id.circularProgressbar);
        textTimer = findViewById(R.id.text_timer);
        layoutBtnFromTellAndShow = findViewById(R.id.layout_btn_from_tell_and_show);
        theyGuessed = findViewById(R.id.they_guessed);
        theyNotGuessed = findViewById(R.id.they_not_guessed);
        remindWord = findViewById(R.id.remind_word);
        layoutBtnPlayer = findViewById(R.id.layout_btn_player);
        buttonAction = findViewById(R.id.buttonAction);
        layoutForDraw = findViewById(R.id.layout_for_draw);
        buttonPointBrush = findViewById(R.id.buttonPointBrush);
        buttonAction.setOnClickListener(view -> layoutBtnFromTellAndShow.setVisibility(View.VISIBLE));
        remindWord.setOnClickListener(view -> {
            Toast toast = Toast.makeText(this, word, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            if (!flagShowBtn){
                layoutBtnFromTellAndShow.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public Context contextActivity() {
        return null;
    }

    @Override
    public void showResultDialog() {

    }
}
