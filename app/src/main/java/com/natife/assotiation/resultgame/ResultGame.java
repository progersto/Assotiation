package com.natife.assotiation.resultgame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

        layoutResult = viewResult.findViewById(R.id.layoutResult);//контейнер для вставки item

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
        if (timeGameFlag){
            super.onBackPressed();
        }
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
//        Uri bmpUri = getImageUri(this, getScreenshot());
        MenuItem item = menu.findItem(R.id.menu_share);
        mShareActionProvider = (android.support.v7.widget.ShareActionProvider) MenuItemCompat.getActionProvider(item);
        if (mShareActionProvider != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.share_text));
            shareIntent.setType("image/jpeg");
            mShareActionProvider.setShareIntent(shareIntent);

            mShareActionProvider.setOnShareTargetSelectedListener(new android.support.v7.widget.ShareActionProvider.OnShareTargetSelectedListener() {
                @Override
                public boolean onShareTargetSelected(android.support.v7.widget.ShareActionProvider shareActionProvider, Intent intent) {
                    Log.d("kkk","fff");
                    Uri bmpUri = getImageUri(ResultGame.this, getScreenshot());
                    intent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                    return false;
                }
            });
        }

        // Return true to display menu
        return true;
    }

    private Bitmap getScreenshot(){
        View v = layoutResult.getRootView();
        v.setDrawingCacheEnabled(true);
        return v.getDrawingCache();
//        BitmapDrawable bitmapDrawable = new BitmapDrawable(bm);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            openScreenshot(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
    }

    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }
}
