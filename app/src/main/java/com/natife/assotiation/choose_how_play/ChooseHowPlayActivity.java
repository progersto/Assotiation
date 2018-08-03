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
    private FrameLayout frame_word1;
    private FrameLayout frame_word2;
    private TextView word1;
    private TextView word2;
    private FrameLayout layout_show;
    private FrameLayout layout_tell;
    private FrameLayout layout_draw;
    private ImageView iconShow;
    private ImageView iconTell;
    private ImageView iconDraw;
    private TextView text_draw;
    private TextView text_show;
    private TextView text_tell;
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
        frame_word1 = findViewById(R.id.frame_word1);
        frame_word2 = findViewById(R.id.frame_word2);
        word1 = findViewById(R.id.word1);
        word2 = findViewById(R.id.word2);
        layout_show = findViewById(R.id.layout_show);
        layout_tell = findViewById(R.id.layout_tell);
        layout_draw = findViewById(R.id.layout_draw);
        iconShow = findViewById(R.id.iconShow);
        iconTell = findViewById(R.id.iconTell);
        iconDraw = findViewById(R.id.icon_draw);
        text_draw = findViewById(R.id.text_draw);
        text_show = findViewById(R.id.text_show);
        text_tell = findViewById(R.id.text_tell);
        buttonGo = findViewById(R.id.buttonGo);
        word1.setOnClickListener(view -> {
            mPresenter.word1Pressed(word1.getText().toString());
            word1.setTextColor(getResources().getColor(colorPlayer));
            word2.setTextColor(getResources().getColor(R.color.colorTextSelextion));
            frame_word1.setForeground(getResources().getDrawable(R.drawable.selected_action_and_word));
            frame_word2.setForeground(getResources().getDrawable(R.drawable.action_and_word));
            GradientDrawable gd = (GradientDrawable)frame_word1.getForeground();
            gd.setStroke(1, getResources().getColor(colorPlayer));
        });
        word2.setOnClickListener(view -> {
            mPresenter.word1Pressed(word1.getText().toString());
            word2.setTextColor(getResources().getColor(colorPlayer));
            word1.setTextColor(getResources().getColor(R.color.colorTextSelextion));
            frame_word2.setForeground(getResources().getDrawable(R.drawable.selected_action_and_word));
            frame_word1.setForeground(getResources().getDrawable(R.drawable.action_and_word));
            GradientDrawable gd = (GradientDrawable)frame_word2.getForeground();
            gd.setStroke(1, getResources().getColor(colorPlayer));
        });
        layout_show.setOnClickListener(view -> {
            mPresenter.layoutShow_Pressed();
            text_show.setTextColor(getResources().getColor(colorPlayer));
            text_tell.setTextColor(getResources().getColor(R.color.colorTextSelextion));
            text_draw.setTextColor(getResources().getColor(R.color.colorTextSelextion));
            iconDraw.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelextion));
            iconTell.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelextion));
            iconShow.setColorFilter(ContextCompat.getColor(this, colorPlayer));
            layout_show.setForeground(getResources().getDrawable(R.drawable.selected_action_and_word));
            layout_tell.setForeground(getResources().getDrawable(R.drawable.action_and_word));
            layout_draw.setForeground(getResources().getDrawable(R.drawable.action_and_word));
            GradientDrawable gd = (GradientDrawable)layout_show.getForeground();
            gd.setStroke(1, getResources().getColor(colorPlayer));
        });
        layout_tell.setOnClickListener(view -> {
            mPresenter.layoutTell_Pressed();
            text_show.setTextColor(getResources().getColor(R.color.colorTextSelextion));
            text_tell.setTextColor(getResources().getColor(colorPlayer));
            text_draw.setTextColor(getResources().getColor(R.color.colorTextSelextion));
            iconDraw.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelextion));
            iconTell.setColorFilter(ContextCompat.getColor(this, colorPlayer));
            iconShow.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelextion));
            layout_show.setForeground(getResources().getDrawable(R.drawable.action_and_word));
            layout_tell.setForeground(getResources().getDrawable(R.drawable.selected_action_and_word));
            layout_draw.setForeground(getResources().getDrawable(R.drawable.action_and_word));
            GradientDrawable gd = (GradientDrawable)layout_tell.getForeground();
            gd.setStroke(1, getResources().getColor(colorPlayer));
        });
        layout_draw.setOnClickListener(view -> {
            mPresenter.layoutDraw_Pressed();
            //color text
            text_show.setTextColor(getResources().getColor(R.color.colorTextSelextion));
            text_tell.setTextColor(getResources().getColor(R.color.colorTextSelextion));
            text_draw.setTextColor(getResources().getColor(colorPlayer));
            //color icon
            iconDraw.setColorFilter(ContextCompat.getColor(this, colorPlayer));
            iconTell.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelextion));
            iconShow.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelextion));
            //background
            layout_show.setForeground(getResources().getDrawable(R.drawable.action_and_word));
            layout_tell.setForeground(getResources().getDrawable(R.drawable.action_and_word));
            layout_draw.setForeground(getResources().getDrawable(R.drawable.selected_action_and_word));
            //change color frame
            GradientDrawable gd = (GradientDrawable)layout_draw.getForeground();
            gd.setStroke(1, getResources().getColor(colorPlayer));
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
        whoseTurn.setTextColor(getResources().getColor(color));
        word1.setText(wordOne);
        word2.setText(wordTwo);
        colorPlayer = color;
    }


}
