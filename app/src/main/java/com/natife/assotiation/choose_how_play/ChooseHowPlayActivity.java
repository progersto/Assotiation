package com.natife.assotiation.choose_how_play;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.natife.assotiation.R;
import com.natife.assotiation.game.GameActivity;
import com.natife.assotiation.initgame.Player;
import com.natife.assotiation.resultgame.ResultGame;
import com.natife.assotiation.utils.Constants;
import com.natife.assotiation.utils.PreferUtil;

import java.util.ArrayList;
import java.util.List;

public class ChooseHowPlayActivity extends AppCompatActivity implements ChooseHowPlayContract.View {

    private ChooseHowPlayContract.Presenter mPresenter;
    private List<String> listWords;
    private List<Player> playerList;
    private TextView whoseTurn;
    private ImageView results;
    private TextView textSelection;
    private FrameLayout frameWord1;
    private FrameLayout frameWord2;
    private TextView word1;
    private TextView word2;
    private FrameLayout layoutShow;
    private FrameLayout layoutTell;
    private FrameLayout layoutDraw;
    private FrameLayout frameShowWords;
    private ImageView iconShow;
    private ImageView iconTell;
    private ImageView iconDraw;
    private TextView textDraw;
    private TextView textShow;
    private TextView textTell;
    private RelativeLayout buttonGo;
    private int colorPlayer = 0;
    private boolean flagWord = false;
    private boolean flagAction = false;
    private String howExplain;
    private String word;
    private final int GAME = 1000;
    private int timeMove;
    private int timeGame;
    private int numberCircles;
    private boolean timeGameFlag = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_how_play);

        PreferUtil preferUtil = new PreferUtil(); //get info from preferences
        timeMove = preferUtil.restoreTimeMove(this);
        timeGame = preferUtil.restoreTimeGame(this);
        numberCircles = preferUtil.restoreNumberCircles(this);

        if (timeMove == 0 || timeGame == 0 || numberCircles == 0) {
            preferUtil.saveTimeMove(this, Constants.TIME_MOVE_DEFOULT);
            preferUtil.saveTimeGame(this, Constants.TIME_GAME_DEFOULT);
            preferUtil.saveNumberCircles(this, Constants.NAMBER_LAP_DEFOULT);
            preferUtil.saveColorDraw(this, ContextCompat.getColor(this, R.color.colorDefault));
            timeMove = preferUtil.restoreTimeMove(this);
            timeGame = preferUtil.restoreTimeGame(this);
            numberCircles = preferUtil.restoreNumberCircles(this);
        }

        //Создаём Presenter и в аргументе передаём ему this - эта Activity расширяет интерфейс InitGameContract.View
        mPresenter = new ChooseHowPlayPresenter(this);

        listWords = getIntent().getStringArrayListExtra("listWords");
        playerList = mPresenter.getPlayerList();

        initViews();

        mPresenter.findDataForFillFields(playerList, listWords, timeGame);
    }


    private void initViews() {
        whoseTurn = findViewById(R.id.whose_turn);
        results = findViewById(R.id.results);
        textSelection = findViewById(R.id.textSelection);
        frameWord1 = findViewById(R.id.frame_word1);
        frameWord2 = findViewById(R.id.frame_word2);
        word1 = findViewById(R.id.word1);
        word2 = findViewById(R.id.word2);
        layoutShow = findViewById(R.id.layout_show);
        layoutTell = findViewById(R.id.layout_tell);
        layoutDraw = findViewById(R.id.layout_draw);
        frameShowWords = findViewById(R.id.frame_show_words);
        iconShow = findViewById(R.id.iconShow);
        iconTell = findViewById(R.id.iconTell);
        iconDraw = findViewById(R.id.icon_draw);
        textDraw = findViewById(R.id.text_draw);
        textShow = findViewById(R.id.text_show);
        textTell = findViewById(R.id.text_tell);
        buttonGo = findViewById(R.id.buttonGo);

        results.setOnClickListener(view -> {
            showResultDialog();
        });
        frameShowWords.setOnClickListener(view -> {
            frameShowWords.setVisibility(View.GONE);
            frameWord1.setVisibility(View.VISIBLE);
            frameWord2.setVisibility(View.VISIBLE);
        });
        word1.setOnClickListener(view -> {
            flagWord = true;
            word = word1.getText().toString();
            word1.setTextColor(ContextCompat.getColor(this, colorPlayer));
            word2.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection));
            frameWord1.setForeground(ContextCompat.getDrawable(this, R.drawable.selected_action_and_word));
            frameWord2.setForeground(ContextCompat.getDrawable(this, R.drawable.recycler_backgroind));
            GradientDrawable gd = (GradientDrawable) frameWord1.getForeground();
            gd.setStroke(3, ContextCompat.getColor(this, colorPlayer));
        });
        word2.setOnClickListener(view -> {
            flagWord = true;
            word = word2.getText().toString();
            word2.setTextColor(ContextCompat.getColor(this, colorPlayer));
            word1.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection));
            frameWord2.setForeground(ContextCompat.getDrawable(this, R.drawable.selected_action_and_word));
            frameWord1.setForeground(ContextCompat.getDrawable(this, R.drawable.recycler_backgroind));
            GradientDrawable gd = (GradientDrawable) frameWord2.getForeground();
            gd.setStroke(3, ContextCompat.getColor(this, colorPlayer));
        });
        layoutShow.setOnClickListener(view -> {
            flagAction = true;
            howExplain = "show";
            textShow.setTextColor(ContextCompat.getColor(this, colorPlayer));
            textTell.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection));
            textDraw.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection));
            iconDraw.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelection));
            iconTell.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelection));
            iconShow.setColorFilter(ContextCompat.getColor(this, colorPlayer));
            layoutShow.setForeground(ContextCompat.getDrawable(this, R.drawable.selected_action_and_word));
            layoutTell.setForeground(ContextCompat.getDrawable(this, R.drawable.recycler_backgroind));
            layoutDraw.setForeground(ContextCompat.getDrawable(this, R.drawable.recycler_backgroind));
            GradientDrawable gd = (GradientDrawable) layoutShow.getForeground();
            gd.setStroke(3, ContextCompat.getColor(this, colorPlayer));
        });
        layoutTell.setOnClickListener(view -> {
            flagAction = true;
            howExplain = "tell";
            textShow.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection));
            textTell.setTextColor(ContextCompat.getColor(this, colorPlayer));
            textDraw.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection));
            iconDraw.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelection));
            iconTell.setColorFilter(ContextCompat.getColor(this, colorPlayer));
            iconShow.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelection));
            layoutShow.setForeground(ContextCompat.getDrawable(this, R.drawable.recycler_backgroind));
            layoutTell.setForeground(ContextCompat.getDrawable(this, R.drawable.selected_action_and_word));
            layoutDraw.setForeground(ContextCompat.getDrawable(this, R.drawable.recycler_backgroind));
            GradientDrawable gd = (GradientDrawable) layoutTell.getForeground();
            gd.setStroke(3, ContextCompat.getColor(this, colorPlayer));
        });
        layoutDraw.setOnClickListener(view -> {
            flagAction = true;
            howExplain = "draw";
            //color text
            textShow.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection));
            textTell.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection));
            textDraw.setTextColor(ContextCompat.getColor(this, colorPlayer));
            //color icon
            iconDraw.setColorFilter(ContextCompat.getColor(this, colorPlayer));
            iconTell.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelection));
            iconShow.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelection));
            //background
            layoutShow.setForeground(ContextCompat.getDrawable(this, R.drawable.recycler_backgroind));
            layoutTell.setForeground(ContextCompat.getDrawable(this, R.drawable.recycler_backgroind));
            layoutDraw.setForeground(ContextCompat.getDrawable(this, R.drawable.selected_action_and_word));
            //change color frame
            GradientDrawable gd = (GradientDrawable) layoutDraw.getForeground();
            gd.setStroke(3, ContextCompat.getColor(this, colorPlayer));
        });
        buttonGo.setOnClickListener(view -> {
            if (flagWord && flagAction) {
                flagWord = false;
                flagAction = false;
                mPresenter.buttonGoPressed();
            } else if (!flagWord && flagAction || !flagWord && !flagAction) {
                Toast.makeText(this, "Выберите слово", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(this, "Выберите действие", Toast.LENGTH_SHORT).show();
        });
    }


    @Override
    public void startGameActivity(int posPlayer) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("positionPlayer", posPlayer);
        intent.putParcelableArrayListExtra("playerList", (ArrayList<? extends Parcelable>) playerList);
        intent.putExtra("word", word);
        intent.putExtra("how_explain", howExplain);
        startActivityForResult(intent, GAME);
    }


    public void showResultDialog() {
        Intent intent = new Intent(this, ResultGame.class);
        intent.putParcelableArrayListExtra("playerList", (ArrayList<? extends Parcelable>) playerList);
        intent.putExtra("timeGameFlag", timeGameFlag);
        startActivity(intent);
    }

    @Override
    public void showData(String name, Integer color, String wordOne, String wordTwo) {
        whoseTurn.setText(String.format("%s %s", getResources().getString(R.string.turn), name));
        whoseTurn.setTextColor(ContextCompat.getColor(this, color));
        word1.setText(wordOne);
        word2.setText(wordTwo);
        colorPlayer = color;
    }


    @Override
    public Context getContextActivity() {
        return this;
    }

    @Override
    public void gameOver() {
        timeGameFlag = false;
    }


    @Override
    protected void onRestart() {
        super.onRestart();

        frameShowWords.setVisibility(View.VISIBLE);
        frameWord1.setVisibility(View.GONE);
        frameWord2.setVisibility(View.GONE);
        word2.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection));
        word1.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection));
        frameWord2.setForeground(ContextCompat.getDrawable(this, R.drawable.recycler_backgroind));
        frameWord1.setForeground(ContextCompat.getDrawable(this, R.drawable.recycler_backgroind));
        layoutShow.setForeground(ContextCompat.getDrawable(this, R.drawable.recycler_backgroind));
        layoutTell.setForeground(ContextCompat.getDrawable(this, R.drawable.recycler_backgroind));
        layoutDraw.setForeground(ContextCompat.getDrawable(this, R.drawable.recycler_backgroind));
        iconDraw.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelection));
        iconTell.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelection));
        iconShow.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelection));
        textShow.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection));
        textTell.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection));
        textDraw.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection));
        mPresenter.findDataForFillFields(playerList, listWords, timeGame);
        if (!timeGameFlag){
            showResultDialog();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.stopTimerGame();
    }

    @Override
    public void onBackPressed() {
    }
}
