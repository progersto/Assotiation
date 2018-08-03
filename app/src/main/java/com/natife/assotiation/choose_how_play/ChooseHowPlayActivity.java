package com.natife.assotiation.choose_how_play;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.natife.assotiation.R;

import java.util.List;

public class ChooseHowPlayActivity extends AppCompatActivity implements ChooseHowPlayContract.View {

    private ChooseHowPlayContract.Presenter mPresenter;
    private List<String> listName;
    private List<Integer> listColor;
    private List<String> listWords;
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
    private ImageView iconShow;
    private ImageView iconTell;
    private ImageView iconDraw;
    private TextView textDraw;
    private TextView textShow;
    private TextView textTell;
    private RelativeLayout buttonGo;
    private int colorPlayer = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_how_play);

        //Создаём Presenter и в аргументе передаём ему this - эта Activity расширяет интерфейс InitGameContract.View
        mPresenter = new ChooseHowPlayPresenter(this);

        listName = getIntent().getStringArrayListExtra("listName");
        listColor = getIntent().getIntegerArrayListExtra("listColor");
        listWords = getIntent().getStringArrayListExtra("listWords");

        initViews();

        mPresenter.findDataForFillFields(listName, listColor, listWords);
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
        iconShow = findViewById(R.id.iconShow);
        iconTell = findViewById(R.id.iconTell);
        iconDraw = findViewById(R.id.icon_draw);
        textDraw = findViewById(R.id.text_draw);
        textShow = findViewById(R.id.text_show);
        textTell = findViewById(R.id.text_tell);
        buttonGo = findViewById(R.id.buttonGo);
        word1.setOnClickListener(view -> {
            mPresenter.word1Pressed(word1.getText().toString());
            word1.setTextColor(ContextCompat.getColor(this, colorPlayer));
            word2.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelextion));
            frameWord1.setForeground(ContextCompat.getDrawable(this, R.drawable.selected_action_and_word));
            frameWord2.setForeground(ContextCompat.getDrawable(this, R.drawable.recycler_backgroind));
            GradientDrawable gd = (GradientDrawable) frameWord1.getForeground();
            gd.setStroke(1, ContextCompat.getColor(this, colorPlayer));
        });
        word2.setOnClickListener(view -> {
            mPresenter.word1Pressed(word1.getText().toString());
            word2.setTextColor(ContextCompat.getColor(this, colorPlayer));
            word1.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelextion));
            frameWord2.setForeground(ContextCompat.getDrawable(this, R.drawable.selected_action_and_word));
            frameWord1.setForeground(ContextCompat.getDrawable(this, R.drawable.recycler_backgroind));
            GradientDrawable gd = (GradientDrawable) frameWord2.getForeground();
            gd.setStroke(1, ContextCompat.getColor(this, colorPlayer));
        });
        layoutShow.setOnClickListener(view -> {
            mPresenter.layoutShow_Pressed();
            textShow.setTextColor(ContextCompat.getColor(this, colorPlayer));
            textTell.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelextion));
            textDraw.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelextion));
            iconDraw.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelextion));
            iconTell.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelextion));
            iconShow.setColorFilter(ContextCompat.getColor(this, colorPlayer));
            layoutShow.setForeground(ContextCompat.getDrawable(this, R.drawable.selected_action_and_word));
            layoutTell.setForeground(ContextCompat.getDrawable(this, R.drawable.recycler_backgroind));
            layoutDraw.setForeground(ContextCompat.getDrawable(this, R.drawable.recycler_backgroind));
            GradientDrawable gd = (GradientDrawable) layoutShow.getForeground();
            gd.setStroke(1, ContextCompat.getColor(this, colorPlayer));
        });
        layoutTell.setOnClickListener(view -> {
            mPresenter.layoutTell_Pressed();
            textShow.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelextion));
            textTell.setTextColor(ContextCompat.getColor(this, colorPlayer));
            textDraw.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelextion));
            iconDraw.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelextion));
            iconTell.setColorFilter(ContextCompat.getColor(this, colorPlayer));
            iconShow.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelextion));
            layoutShow.setForeground(ContextCompat.getDrawable(this, R.drawable.recycler_backgroind));
            layoutTell.setForeground(ContextCompat.getDrawable(this, R.drawable.selected_action_and_word));
            layoutDraw.setForeground(ContextCompat.getDrawable(this, R.drawable.recycler_backgroind));
            GradientDrawable gd = (GradientDrawable) layoutTell.getForeground();
            gd.setStroke(1, ContextCompat.getColor(this, colorPlayer));
        });
        layoutDraw.setOnClickListener(view -> {
            mPresenter.layoutDraw_Pressed();
            //color text
            textShow.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelextion));
            textTell.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelextion));
            textDraw.setTextColor(ContextCompat.getColor(this, colorPlayer));
            //color icon
            iconDraw.setColorFilter(ContextCompat.getColor(this, colorPlayer));
            iconTell.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelextion));
            iconShow.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelextion));
            //background
            layoutShow.setForeground(ContextCompat.getDrawable(this, R.drawable.recycler_backgroind));
            layoutTell.setForeground(ContextCompat.getDrawable(this, R.drawable.recycler_backgroind));
            layoutDraw.setForeground(ContextCompat.getDrawable(this, R.drawable.selected_action_and_word));
            //change color frame
            GradientDrawable gd = (GradientDrawable) layoutDraw.getForeground();
            gd.setStroke(1, ContextCompat.getColor(this, colorPlayer));
        });
        buttonGo.setOnClickListener(view -> {mPresenter.buttonGo(); });
    }



    @Override
    public Context contextActivity() {
        return ChooseHowPlayActivity.this;
    }

    @Override
    public void showResultDialog() {

    }

    @Override
    public void showData(String name, Integer color, String wordOne, String wordTwo) {
        whoseTurn.setText(String.format("%s %s", getResources().getString(R.string.turn), name));
        whoseTurn.setTextColor(ContextCompat.getColor(this, color));
        word1.setText(wordOne);
        word2.setText(wordTwo);
        colorPlayer = color;
    }


}
