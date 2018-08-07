package com.natife.assotiation.choose_how_play;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.natife.assotiation.R;
import com.natife.assotiation.initgame.Player;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_how_play);

        //Создаём Presenter и в аргументе передаём ему this - эта Activity расширяет интерфейс InitGameContract.View
        mPresenter = new ChooseHowPlayPresenter(this);

        listWords = getIntent().getStringArrayListExtra("listWords");
        playerList = mPresenter.getPlayerList();

        initViews();

        mPresenter.findDataForFillFields(playerList, listWords);
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
            mPresenter.resultPressed();
        });
        frameShowWords.setOnClickListener(view -> {
            frameShowWords.setVisibility(View.GONE);
            frameWord1.setVisibility(View.VISIBLE);
            frameWord2.setVisibility(View.VISIBLE);
        });
        word1.setOnClickListener(view -> {
            flagWord = true;
            mPresenter.word1Pressed(word1.getText().toString());
            word1.setTextColor(ContextCompat.getColor(this, colorPlayer));
            word2.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection));
            frameWord1.setForeground(ContextCompat.getDrawable(this, R.drawable.selected_action_and_word));
            frameWord2.setForeground(ContextCompat.getDrawable(this, R.drawable.recycler_backgroind));
            GradientDrawable gd = (GradientDrawable) frameWord1.getForeground();
            gd.setStroke(3, ContextCompat.getColor(this, colorPlayer));
        });
        word2.setOnClickListener(view -> {
            flagWord = true;
            mPresenter.word1Pressed(word2.getText().toString());
            word2.setTextColor(ContextCompat.getColor(this, colorPlayer));
            word1.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection));
            frameWord2.setForeground(ContextCompat.getDrawable(this, R.drawable.selected_action_and_word));
            frameWord1.setForeground(ContextCompat.getDrawable(this, R.drawable.recycler_backgroind));
            GradientDrawable gd = (GradientDrawable) frameWord2.getForeground();
            gd.setStroke(3, ContextCompat.getColor(this, colorPlayer));
        });
        layoutShow.setOnClickListener(view -> {
            flagAction = true;
            mPresenter.layoutShow_Pressed();
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
            mPresenter.layoutTell_Pressed();
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
            mPresenter.layoutDraw_Pressed();
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
                mPresenter.buttonGo();
            } else if (!flagWord && flagAction || !flagWord && !flagAction) {
                Toast.makeText(this, "Выберите слово", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(this, "Выберите действие", Toast.LENGTH_SHORT).show();
        });
    }


    @Override
    public Context contextActivity() {
        return ChooseHowPlayActivity.this;
    }


    @Override
    public void showResultDialog() {
        DialogResult dialogResult = new DialogResult();
        Bundle args = new Bundle();
        args.putParcelableArrayList("playerList", (ArrayList<? extends Parcelable>) playerList);
        dialogResult.setArguments(args);
        dialogResult.show(getSupportFragmentManager(), "dialogResult");
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
    protected void onRestart() {
        super.onRestart();

        List<Player> ddd = playerList;
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
        mPresenter.findDataForFillFields(playerList, listWords);
    }


}
