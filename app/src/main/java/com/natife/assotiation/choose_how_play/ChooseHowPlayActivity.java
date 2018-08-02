package com.natife.assotiation.choose_how_play;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.natife.assotiation.R;
import com.natife.assotiation.initgame.InitGamePresenter;

import java.util.List;

public class ChooseHowPlayActivity extends AppCompatActivity implements ChooseHowPlayContract.View {

    private ChooseHowPlayContract.Presenter mPresenter;
    private List<String> listName;
    private List<Integer> listColor;
    private List<String> listWords;

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
    }

    private void initViews() {

    }

    @Override
    public Context contextActivity() {
        return ChooseHowPlayActivity.this;
    }

    @Override
    public void showResultDialog() {

    }
}
