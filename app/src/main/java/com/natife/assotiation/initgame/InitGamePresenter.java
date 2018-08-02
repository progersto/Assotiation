package com.natife.assotiation.initgame;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.natife.assotiation.R;
import com.natife.assotiation.choose_how_play.ChooseHowPlayActivity;

import java.util.ArrayList;
import java.util.List;

public class InitGamePresenter implements InitGameContract.Presenter {
    //Компоненты MVP приложения
    private InitGameContract.View mView;
    private InitGameContract.Repository mRepository;
    private List<String> listName;
    private List<Integer> listColor;
    private boolean flagStartGame = false;
    private List<String> listWords;

    //передаем экземпляр View
    public InitGamePresenter(InitGameContract.View mView) {
        this.mView = mView;
        this.mRepository = new InitGameRepository();
    }


    @Override
    public void initPlayerList() {
        listName = mRepository.createListNamePlayers();
        listColor = mRepository.createListColor();
        mView.showListPlayers(listName, listColor);
    }

    @Override
    public void btnAddPlayerClicked() {
        if (listName.size() <= 5) {
            listName = mRepository.addNamePlayerInList();
            mView.showListPlayers(listName, listColor);
        }
    }


    @Override
    public void btnNextClicked(int difficultLevel) {
        if (flagStartGame) {
            flagStartGame = false;
            listWords = mRepository.createListWords(difficultLevel, mView.contextActivity());
            Log.d("ddd", "listWords = " + listWords);

            //start to play...
            Intent intent = new Intent(mView.contextActivity(), ChooseHowPlayActivity.class);
            intent.putStringArrayListExtra("listWords", (ArrayList<String>) listWords);
            intent.putIntegerArrayListExtra("listColor", (ArrayList<Integer>) listColor);
            intent.putStringArrayListExtra("listName", (ArrayList<String>) listName);
            mView.contextActivity().startActivity(intent);

        } else {
            if (listName.contains("")) {
                new android.support.v7.app.AlertDialog.Builder(mView.contextActivity())
                        .setMessage(R.string.set_name)
                        .setPositiveButton((R.string.ok), (dialog, which) -> dialog.dismiss())
                        .show();
            } else {
                mView.changeScreen(true);
                flagStartGame = true;
            }
        }
    }

    @Override
    public void btnBackClicked() {
        flagStartGame = false;
        mView.changeScreen(false);
    }

    @Override
    public void btnSettingsClicked() {
        Dialog dialog = new Dialog(mView.contextActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (flagStartGame) {
            mView.showSettingsDialog(flagStartGame);
        } else {
            dialog.setContentView(R.layout.dialog_inform);
            dialog.show();
        }
    }

    @Override
    public void onDestroy() {

    }
}
