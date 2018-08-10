package com.natife.assotiation.resultgame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
    private LinearLayout layoutResult;
    private boolean timeGameFlag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View viewResult = getLayoutInflater().inflate(R.layout.activity_result_game, null);

        layoutResult = viewResult.findViewById(R.id.layoutResult);//контейнер для вставки item
        toolbar = viewResult.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        timeGameFlag = getIntent().getBooleanExtra("timeGameFlag", false);
        playerList = getIntent().getParcelableArrayListExtra("playerList");
        localPayerList = new ArrayList<>(playerList);

        RelativeLayout buttonAgain = viewResult.findViewById(R.id.buttonAgain);
        buttonAgain.setOnClickListener(view -> {
            finishAffinity();
            Intent intent = new Intent(this, InitGameActivity.class);
            intent.putParcelableArrayListExtra("playerList", (ArrayList<? extends Parcelable>) playerList);
            startActivity(intent);
        });
        ImageView btnBack = viewResult.findViewById(R.id.back);
        btnBack.setOnClickListener(view -> finish());
        btnBack.setVisibility(timeGameFlag ? View.VISIBLE : View.INVISIBLE);
        GradientDrawable gd = (GradientDrawable) buttonAgain.getBackground();
        gd.setColor(ContextCompat.getColor(this, R.color.colorButton));

        Collections.sort(localPayerList, (player, t1) -> {
            if (player.getCountScore() == t1.getCountScore()) return 0;
            else if (player.getCountScore() < t1.getCountScore()) return 1;
            else return -1;
        });

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

    @Override
    public void onBackPressed() {
        if (timeGameFlag) {
            super.onBackPressed();
        }
    }

    private boolean checkWin() {
        boolean flag = true;
        if (localPayerList.get(0).getCountScore() == localPayerList.get(1).getCountScore()) {
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_share:

                Bitmap bitmap = getBitmapFromView(layoutResult);
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                startShare(bitmap);
               break;
        }
        return true;
    }

    private void startShare(Bitmap bitmap) {
        try {
            File file = new File(this.getExternalCacheDir(),"logicchip.png");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);

            final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.share_text));
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            intent.setType("image/png");
            startActivity(Intent.createChooser(intent, "Share image via"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_result, menu);
        return true;
    }



    private Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        } else {
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return returnedBitmap;
    }
}
