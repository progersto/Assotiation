package com.natife.assotiation.game;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.natife.assotiation.R;
import com.natife.assotiation.game.UtilForDraw.PaintView;
import com.natife.assotiation.initgame.Player;
import com.natife.assotiation.utils.PreferUtil;

import java.util.List;
import java.util.Objects;

public class GameActivity extends AppCompatActivity implements GameContract.View {
    private GameContract.Presenter mPresenter;
    private String howExplain;
    private TextView textTimerDraw;
    private TextView whoseTurn;
    private TextView drawClear;
    private RelativeLayout timer;
    private ProgressBar circularProgressbar;
    private TextView textTimer;
    private View layoutBtnFromTellAndShow;
    private RelativeLayout theyGuessed;
    private RelativeLayout theyNotGuessed;
    private RelativeLayout remindWord;
    private LinearLayout layoutBtnPlayer;
    private String word;
    private PaintView paintView;
    private RelativeLayout buttonAction;
    private RelativeLayout buttonPointBrush;
    private boolean flagShowBtn;
    private RelativeLayout layoutForDraw;
    private int positionPlayer;
    private List<Player> playerList;
    private boolean timerBig;
    private GradientDrawable gd;
    private int timeMove;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Создаём Presenter и в аргументе передаём ему this - эта Activity расширяет интерфейс GameContract.View
        mPresenter = new GamePresenter(this);

        howExplain = getIntent().getStringExtra("how_explain");
        positionPlayer = getIntent().getIntExtra("positionPlayer", 0);
        word = getIntent().getStringExtra("word");
        timeMove = new PreferUtil().restoreTimeMove(this);//get info from preferences

        playerList = mPresenter.getPlayerList();

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showView(howExplain);
    }

    private void showView(String howExplain) {
        whoseTurn.setTextColor(ContextCompat.getColor(this, playerList.get(positionPlayer).getColor()));
        switch (howExplain) {
            case "tell":
                whoseTurn.setText(String.format("%s %s", getResources().getString(R.string.describes), playerList.get(positionPlayer).getName()));
                selectedTellOrShow();
                break;
            case "show":
                whoseTurn.setText(String.format("%s %s", getResources().getString(R.string.shows), playerList.get(positionPlayer).getName()));
                selectedTellOrShow();
                break;
            case "draw":
                whoseTurn.setText(String.format("%s %s", getResources().getString(R.string.draws), playerList.get(positionPlayer).getName()));
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
        timerBig = false;
        mPresenter.initTimer(false, timeMove);

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
        timerBig = true;
        mPresenter.initTimer(true, timeMove);
    }


    private void initView() {
        textTimerDraw = findViewById(R.id.text_timer_draw);
        whoseTurn = findViewById(R.id.whose_turn);
        drawClear = findViewById(R.id.draw_clear);
        drawClear.setOnClickListener(view -> paintView.clear());
        timer = findViewById(R.id.timer);
        circularProgressbar = findViewById(R.id.circularProgressbar);
        circularProgressbar.setMax(timeMove);
        textTimer = findViewById(R.id.text_timer);
        layoutBtnFromTellAndShow = findViewById(R.id.layout_btn_from_tell_and_show);
        theyGuessed = findViewById(R.id.they_guessed);
        theyNotGuessed = findViewById(R.id.they_not_guessed);
        remindWord = findViewById(R.id.remind_word);
        layoutBtnPlayer = findViewById(R.id.layout_btn_player);
        buttonAction = findViewById(R.id.buttonAction);
        layoutForDraw = findViewById(R.id.layout_for_draw);
        buttonPointBrush = findViewById(R.id.buttonPointBrush);
        buttonAction.setOnClickListener(view -> {
            Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.btn_block);
            RelativeLayout theyGuessed = dialog.findViewById(R.id.they_guessed);
            RelativeLayout theyNotGuessed = dialog.findViewById(R.id.they_not_guessed);
            RelativeLayout remindWord = dialog.findViewById(R.id.remind_word);
            theyGuessed.setOnClickListener(view12 -> {
                dialog.dismiss();
                btnTheyGuessed();
            });
            theyNotGuessed.setOnClickListener(view12 -> {
                dialog.dismiss();
                btnTheyNotGuessed();
            });
            remindWord.setOnClickListener(view12 -> {
                dialog.dismiss();
                btnRemindWord();
            });
            dialog.show();
        });
        remindWord.setOnClickListener(view -> {
            btnRemindWord();
        });
        theyGuessed.setOnClickListener(view -> {
            btnTheyGuessed();
        });
        theyNotGuessed.setOnClickListener(view -> {
            btnTheyNotGuessed();
        });
    }

    private void btnRemindWord(){
        Toast toast = Toast.makeText(this, word, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void btnTheyNotGuessed(){
        mPresenter.stopCountDownTimer();
        mPresenter.notWin();
    }

    private void btnTheyGuessed(){
        mPresenter.stopCountDownTimer();
        whoseTurn.setText(getResources().getString(R.string.who_guessed));
        whoseTurn.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection));

        if (flagShowBtn) {
            timer.setVisibility(View.GONE);
        } else {
            textTimerDraw.setVisibility(View.GONE);
            drawClear.setVisibility(View.GONE);
            layoutForDraw.setVisibility(View.GONE);
        }
        layoutBtnFromTellAndShow.setVisibility(View.GONE);
        layoutBtnPlayer.setVisibility(View.VISIBLE);

        for (int i = 0; i < playerList.size(); i++) {
            if (positionPlayer != i) {
                View newItem = LayoutInflater.from(this).inflate(R.layout.item_player_button, null);//добавляемый item
                RelativeLayout btn = newItem.findViewById(R.id.btnPlayer);
                TextView textBtnPlayer = newItem.findViewById(R.id.textBtnPlayer);
                String name = playerList.get(i).getName().substring(0, 1).toUpperCase() + playerList.get(i).getName().substring(1);
                textBtnPlayer.setText(name);
                int posWin = i;
                gd = (GradientDrawable) btn.getBackground();
                gd.setColor(ContextCompat.getColor(this, playerList.get(i).getColor()));
                btn.setOnClickListener(view1 -> {
                    mPresenter.playerWin(playerList, posWin, positionPlayer);
                });
                layoutBtnPlayer.addView(newItem);
            }
        }
    }

    @Override
    public Context contextActivity() {
        return this;
    }


    @Override
    public void finishCurrentGame() {
        setResult(RESULT_OK, new Intent());
        this.finish();
    }

    @Override
    public void setCircularProgressbar(int progress) {
        circularProgressbar.setProgress(progress);
    }

    @Override
    public void setTextTimer(String time) {
        if (timerBig) {
            textTimer.setText(time);
        } else
            textTimerDraw.setText(time);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (gd != null) {
            gd.setColor(ContextCompat.getColor(this, R.color.colorButton));
        }
        mPresenter.stopCountDownTimer();
    }
}
