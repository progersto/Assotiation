package com.natife.assotiation.game;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.natife.assotiation.R;
import com.natife.assotiation.game.UtilForDraw.PaintView;
import com.natife.assotiation.initgame.Player;

import java.util.List;

public class GameActivity extends AppCompatActivity implements GameContract.View {
    private GameContract.Presenter mPresenter;
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
    private String word;
    private PaintView paintView;
    private RelativeLayout buttonAction;
    private RelativeLayout buttonPointBrush;
    private boolean flagShowBtn;
    private RelativeLayout layoutForDraw;
    private int positionPlayer;
    private List<Player> playerList;
    private boolean timerBig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        howExplain = getIntent().getStringExtra("how_explain");
        positionPlayer = getIntent().getIntExtra("positionPlayer", 0);
        playerList = getIntent().getParcelableArrayListExtra("playerList");
        listWords = getIntent().getStringArrayListExtra("listWords");
        word = getIntent().getStringExtra("word");
        initView();

        //Создаём Presenter и в аргументе передаём ему this - эта Activity расширяет интерфейс GameContract.View
        mPresenter = new GamePresenter(this);
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
        mPresenter.initTimer(false);

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
        mPresenter.initTimer(true);
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
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            if (!flagShowBtn) {
                layoutBtnFromTellAndShow.setVisibility(View.GONE);
            }
        });
        theyGuessed.setOnClickListener(view -> {
            mPresenter.stopCountDownTimer();
            timer.setVisibility(View.GONE);
            layoutBtnFromTellAndShow.setVisibility(View.GONE);
            layoutBtnPlayer.setVisibility(View.VISIBLE);

            for (int i = 0; i < playerList.size(); i++) {
                if (positionPlayer != i){
                    View newItem = LayoutInflater.from(this).inflate(R.layout.item_player_button, null);//добавляемый item
                    RelativeLayout btn = newItem.findViewById(R.id.btnPlayer);
                    TextView textBtnPlayer = newItem.findViewById(R.id.textBtnPlayer);
                    String name = playerList.get(i).getName().substring(0, 1).toUpperCase() + playerList.get(i).getName().substring(1);
                    textBtnPlayer.setText(name);
                    int pos = i;
                    GradientDrawable gd = (GradientDrawable) btn.getBackground();
                    gd.setColor(ContextCompat.getColor(this, playerList.get(i).getColor()));
                    btn.setOnClickListener(view1 -> {
                        mPresenter.playerWin(playerList, pos);
                    });
                    layoutBtnPlayer.addView(newItem);
                }
            }
        });
        theyNotGuessed.setOnClickListener(view -> {
            mPresenter.stopCountDownTimer();
            mPresenter.notWin();
        });

    }

    @Override
    public Context contextActivity() {
        return null;
    }

    @Override
    public void startGame() {

    }
    @Override
    public void finishCurrentGame(){
        this.finish();

    }

    @Override
    public void setCircularProgressbar(int progress) {
        circularProgressbar.setProgress(progress);
    }

    @Override
    public void setTextTimer(String time) {
        if (timerBig){
            textTimer.setText(time);
        }else
            textTimerDraw.setText(time);
    }
}
