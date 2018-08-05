package com.natife.assotiation.choose_how_play;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class DialogResult extends DialogFragment {
    TextView timeMoveTV;
    TextView timeGameTV;
    TextView numberCirclesTV;
    int timeMove;
    int timeGame;
    int numberCircles;
    private List<String> listName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("ddd", "fff");
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        View v = inflater.inflate(R.layout.dialog_result, null);
        RelativeLayout buttonAgain = v.findViewById(R.id.buttonAgain);
        buttonAgain.setOnClickListener(view -> {
            getActivity().finishAffinity();
            dismiss();
            Intent intent = new Intent(getContext(), InitGameActivity.class);
            intent.putStringArrayListExtra("listName", (ArrayList<String>) listName);
            startActivity(intent);
        });

        listName = getArguments().getStringArrayList("listName");

        LinearLayout layoutResult = v.findViewById(R.id.layoutResult);//контейнер для вставки item
        for (int i = 0; i < listName.size(); i++) {
            View newItem = inflater.inflate(R.layout.item_result, null);//добавляемый item
            ImageView image = newItem.findViewById(R.id.image_result);
            TextView nameResult = newItem.findViewById(R.id.name_result);//inserted name
            TextView totalPointsResult = newItem.findViewById(R.id.total_points);
            TextView guessedWordsResult = newItem.findViewById(R.id.guessed_words);
            String name = listName.get(i).substring(0, 1).toUpperCase() + listName.get(i).substring(1);
            nameResult.setText(name);
            layoutResult.addView(newItem);
        }
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
