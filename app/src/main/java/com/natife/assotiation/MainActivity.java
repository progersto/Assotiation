package com.natife.assotiation;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.natife.assotiation.adapters.PlayersAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerPlayers;
    private ImageView back;
    private ImageView settings;
    private RelativeLayout btnAddPlayer;
    private RelativeLayout btnNext;
    private TextView textBtnAddPlayer;
    private TextView textBtnNext;
    private List<String> listName;
    private PlayersAdapter adapterPlayers;

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initPlayerList();
        recyclerPlayers.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapterPlayers = new PlayersAdapter(MainActivity.this, listName);
        recyclerPlayers.setAdapter(adapterPlayers);
    }//onCreate

    private void initPlayerList() {
        listName = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            listName.add(getResources().getString(R.string.name_player) + " " + (i + 1));
        }
    }//initPlayerList


    private void initView() {
        recyclerPlayers = findViewById(R.id.recyclerViewListPlayer);
        back = findViewById(R.id.back);
        settings = findViewById(R.id.settings);
        btnAddPlayer = findViewById(R.id.buttonAddPlayer);
        btnNext = findViewById(R.id.buttonNext);
        textBtnNext = findViewById(R.id.textBtnNext);
        btnAddPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listName.size() <= 5)
                    listName.add(getResources().getString(R.string.name_player) + " " + (listName.size() + 1));
                adapterPlayers = new PlayersAdapter(MainActivity.this, listName);
                recyclerPlayers.setAdapter(adapterPlayers);
            }
        });
    }//initView


}
