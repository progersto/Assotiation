package com.natife.assotiation.choose_how_play;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.natife.assotiation.R;
import com.natife.assotiation.initgame.InitGameActivity;
import com.natife.assotiation.initgame.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DialogResult extends DialogFragment {
    TextView timeMoveTV;
    TextView timeGameTV;
    TextView numberCirclesTV;
    int timeMove;
    int timeGame;
    int numberCircles;
    private List<Player> localPayerList;
    private List<Player> playerList;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        View v = inflater.inflate(R.layout.dialog_result, null);
        RelativeLayout buttonAgain = v.findViewById(R.id.buttonAgain);
        buttonAgain.setOnClickListener(view -> {
            getActivity().finishAffinity();
            dismiss();
            Intent intent = new Intent(getContext(), InitGameActivity.class);
            intent.putParcelableArrayListExtra("playerList", (ArrayList<? extends Parcelable>) playerList);
            startActivity(intent);
        });

        playerList = getArguments().getParcelableArrayList("playerList");
        localPayerList = new ArrayList<>(playerList);

        Collections.sort(localPayerList, new Comparator<Player>() {
            @Override
            public int compare(Player player, Player t1) {
                if (player.getCountScore() == t1.getCountScore()) return 0;
                else if (player.getCountScore() < t1.getCountScore()) return 1;
                else return -1;
            }
        });

        LinearLayout layoutResult = v.findViewById(R.id.layoutResult);//контейнер для вставки item

        boolean isWin = checkWin();
        for (int i = 0; i < playerList.size(); i++) {
            View newItem = inflater.inflate(R.layout.item_result, null);//добавляемый item
            ImageView image = newItem.findViewById(R.id.image_result);
            TextView nameResult = newItem.findViewById(R.id.name_result);//inserted name
            TextView totalPointsResult = newItem.findViewById(R.id.total_points);
            TextView guessedWordsResult = newItem.findViewById(R.id.guessed_words);

            String name = playerList.get(i).getName().substring(0, 1).toUpperCase() + playerList.get(i).getName().substring(1);
            nameResult.setText(name);
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
        return v;
    }

    private boolean checkWin() {
        boolean flag = true;
            if (localPayerList.get(0).getCountScore() == localPayerList.get(1).getCountScore() ) {
                flag = false;
            }
        return flag;
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }
}
