package com.natife.assotiation.resultgame;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.natife.assotiation.R;
import com.natife.assotiation.initgame.InitGameActivity;
import com.natife.assotiation.initgame.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResultGame extends AppCompatActivity {
    TextView timeMoveTV;
    TextView timeGameTV;
    TextView numberCirclesTV;
    int timeMove;
    int timeGame;
    int numberCircles;
    private List<Player> localPayerList;
    private List<Player> playerList;
    private android.support.v7.widget.ShareActionProvider mShareActionProvider;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View viewResult = getLayoutInflater().inflate(R.layout.activity_result_game, null);
        toolbar = viewResult.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        boolean timeGameFlag = getIntent().getBooleanExtra("timeGameFlag", false);
        playerList = getIntent().getParcelableArrayListExtra("playerList");
        localPayerList = new ArrayList<>(playerList);

        RelativeLayout buttonAgain = viewResult.findViewById(R.id.buttonAgain);
        buttonAgain.setOnClickListener(view -> {
            finishAffinity();
            Intent intent = new Intent(this, InitGameActivity.class);
            intent.putParcelableArrayListExtra("playerList", (ArrayList<? extends Parcelable>) playerList);
            startActivity(intent);
        });
        GradientDrawable gd = (GradientDrawable) buttonAgain.getBackground();
        gd.setColor(ContextCompat.getColor(this, R.color.colorButton));

        Collections.sort(localPayerList, (player, t1) -> {
            if (player.getCountScore() == t1.getCountScore()) return 0;
            else if (player.getCountScore() < t1.getCountScore()) return 1;
            else return -1;
        });

        LinearLayout layoutResult = viewResult.findViewById(R.id.layoutResult);//контейнер для вставки item

        boolean isWin = checkWin();
        for (int i = 0; i < playerList.size(); i++) {
            View newItem = getLayoutInflater().inflate(R.layout.item_result, null);//добавляемый item
            ImageView image = newItem.findViewById(R.id.image_result);
            TextView nameResult = newItem.findViewById(R.id.name_result);//inserted name
            TextView totalPointsResult = newItem.findViewById(R.id.total_points);
            TextView guessedWordsResult = newItem.findViewById(R.id.guessed_words);

            String namePlayer = playerList.get(i).getName().substring(0, 1).toUpperCase() + playerList.get(i).getName().substring(1);
            nameResult.setText(namePlayer);
            String guessedWords = String.format("%s %s %s",
                    getResources().getString(R.string.guessed),
                    String.valueOf(localPayerList.get(i).getCountWords()),
                    getResources().getString(R.string.words));
            guessedWordsResult.setText(guessedWords);
            totalPointsResult.setText(String.valueOf(localPayerList.get(i).getCountScore()));
            if (isWin && i == 0) {
                image.setVisibility(View.VISIBLE);
            } else
                image.setVisibility(View.INVISIBLE);
            layoutResult.addView(newItem);
        }

        setContentView(viewResult);
    }

    private boolean checkWin() {
        boolean flag = true;
        if (localPayerList.get(0).getCountScore() == localPayerList.get(1).getCountScore() ) {
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu resource file.
        getMenuInflater().inflate(R.menu.menu_result, menu);

        MenuItem item = menu.findItem(R.id.menu_share);
        mShareActionProvider = (android.support.v7.widget.ShareActionProvider) MenuItemCompat.getActionProvider(item);
        if (mShareActionProvider != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.share_text));
            shareIntent.setType("text/plain");
            mShareActionProvider.setShareIntent(shareIntent);
        }

        // Return true to display menu
        return true;
    }
}
