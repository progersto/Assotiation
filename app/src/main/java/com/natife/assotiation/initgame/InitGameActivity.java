package com.natife.assotiation.initgame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.natife.assotiation.R;

import java.util.List;
import java.util.Objects;

public class InitGameActivity extends AppCompatActivity implements InitGameContract.View {

    private RecyclerView recyclerPlayers;
    private ImageView back;
    private ImageView settings;
    private RelativeLayout btnAddPlayer;
    private RelativeLayout btnNext;
    private TextView textBtnNext;
    private PlayersAdapter adapterPlayers;
    private View viewRadioButton;
    private TextView textSelection;
    private InitGameContract.Presenter mPresenter;

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, InitGameActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Создаём Presenter и в аргументе передаём ему this - эта Activity расширяет интерфейс InitGameContract.View
        mPresenter = new InitGamePresenter(this);

        initView();

        recyclerPlayers.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapterPlayers = new PlayersAdapter(InitGameActivity.this);
        recyclerPlayers.setAdapter(adapterPlayers);

        ItemTouchHelper.Callback callback = new ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                int swipedPosition = viewHolder.getAdapterPosition();

                if (adapterPlayers.getItemCount() > 3) {
                    adapterPlayers.deleteFromListAdapter(swipedPosition);
                }
            }

            @Override
            public int getSwipeDirs(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder holder) {
                return recyclerView.getAdapter().getItemCount() > 3 ? super.getSwipeDirs(recyclerView, holder) : 0;
            }
        };
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerPlayers);

        mPresenter.initPlayerList();
    }//onCreate


    private void initView() {
        recyclerPlayers = findViewById(R.id.recyclerViewListPlayer);
        textSelection = findViewById(R.id.textSelection);
        back = findViewById(R.id.back);
        settings = findViewById(R.id.settings);
        btnAddPlayer = findViewById(R.id.buttonAddPlayer);
        btnNext = findViewById(R.id.buttonNext);
        textBtnNext = findViewById(R.id.textBtnNext);
        viewRadioButton = findViewById(R.id.viewRadioButton);

        btnAddPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.btnAddPlayerClicked();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.btnNextClicked();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.btnBackClicked();
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.btnSettingsClicked();
            }
        });
    }//initView


    @Override
    public void showListPlayers(List<String> listName, List<Integer> listColor) {
        adapterPlayers.setData(listName, listColor);
    }

    @Override
    public Context contextActivity() {
        return this;
    }

    @Override
    public void changeScreen(boolean flagSetName) {
        recyclerPlayers.setVisibility(flagSetName ? View.GONE : View.VISIBLE);
        viewRadioButton.setVisibility(flagSetName ? View.VISIBLE : View.GONE);
        back.setVisibility(flagSetName ? View.VISIBLE : View.GONE);
        settings.setImageResource(flagSetName ? R.drawable.ic_settings_black_24dp : R.drawable.ic_error_outline_black_24dp);
        btnAddPlayer.setVisibility(flagSetName ? View.GONE : View.VISIBLE);
        textBtnNext.setText(flagSetName ? R.string.text_play : R.string.text_next);
        textSelection.setText(flagSetName ? R.string.text_selection_difficulty_level : R.string.text_selection_name);
    }
}
