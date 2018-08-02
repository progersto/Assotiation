package com.natife.assotiation.initgame;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.natife.assotiation.R;
import com.natife.assotiation.utils.PreferUtil;

public class DialogSettings extends DialogFragment {

    TextView timeMoveTV;
    TextView timeGameTV;
    TextView numberCirclesTV;
    int timeMove;
    int timeGame;
    int numberCircles;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_settings_game, null);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        timeMoveTV = v.findViewById(R.id.text_time_move);
        timeGameTV = v.findViewById(R.id.text_time_game);
        numberCirclesTV = v.findViewById(R.id.text_number_of_circles);

        PreferUtil preferUtil = new PreferUtil();
        //get info from preferences
        timeMove = preferUtil.restoreTimeMove(v.getContext());
        timeGame = preferUtil.restoreTimeGame(v.getContext());
        numberCircles = preferUtil.restoreNumberCircles(v.getContext());

        //for first init
        if (timeMove == 0 || timeGame == 0 || numberCircles == 0) {
            timeMove = Integer.parseInt(timeMoveTV.getText().toString());
            timeGame = Integer.parseInt(timeGameTV.getText().toString());
            numberCircles = Integer.parseInt(numberCirclesTV.getText().toString());
            preferUtil.saveTimeMove(v.getContext(), timeMove);
            preferUtil.saveTimeGame(v.getContext(), timeGame);
            preferUtil.saveNumberCircles(v.getContext(), numberCircles);
        }else {
            timeMoveTV.setText(String.valueOf(timeMove));
            timeGameTV.setText(String.valueOf(timeGame));
            numberCirclesTV.setText(String.valueOf(numberCircles));
        }

        v.findViewById(R.id.time_move_minus).setOnClickListener(view -> {
            if (timeMove != 15) {
                timeMove -= 15;
                timeMoveTV.setText(String.valueOf(timeMove));
            }
        });
        v.findViewById(R.id.time_move_plus).setOnClickListener(view -> {
            if (timeMove != 300) {
                timeMove += 15;
                timeMoveTV.setText(String.valueOf(timeMove));
            }
        });
        v.findViewById(R.id.time_game_minus).setOnClickListener(view -> {
            if (timeGame != 15) {
                timeGame -= 1;
                timeGameTV.setText(String.valueOf(timeGame));
            }
        });
        v.findViewById(R.id.time_game_plus).setOnClickListener(view -> {
            if (timeGame != 90) {
                timeGame += 1;
                timeGameTV.setText(String.valueOf(timeGame));
            }
        });
        v.findViewById(R.id.number_of_circles_plus).setOnClickListener(view -> {
            if (numberCircles != 50) {
                numberCircles += 1;
                numberCirclesTV.setText(String.valueOf(numberCircles));
            }
        });
        v.findViewById(R.id.number_of_circles_minus).setOnClickListener(view -> {
            if (numberCircles != 1) {
                numberCircles -= 1;
                numberCirclesTV.setText(String.valueOf(numberCircles));
            }
        });

        v.findViewById(R.id.buttonSave).setOnClickListener(view -> {
            preferUtil.saveTimeMove(v.getContext(), timeMove);
            preferUtil.saveTimeGame(v.getContext(), timeGame);
            preferUtil.saveNumberCircles(v.getContext(), numberCircles);
            dismiss();
        });

        return v;
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
