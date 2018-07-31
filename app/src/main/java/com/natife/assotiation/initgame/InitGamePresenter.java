package com.natife.assotiation.initgame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.Settings;
import android.view.View;
import android.view.Window;

import com.natife.assotiation.R;

import java.util.List;

public class InitGamePresenter implements InitGameContract.Presenter  {
    //Компоненты MVP приложения
    private InitGameContract.View mView;
    private InitGameContract.Repository mRepository;
    private List<String> listName;
    private List<Integer> listColor;
    private boolean flagStartGame = false;

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
        if (listName.size() <= 5){
            listName = mRepository.addNamePlayerInList();
            mView.showListPlayers(listName, listColor);
        }
    }

    @Override
    public void btnNextClicked() {
        if (flagStartGame) {
            flagStartGame = false;
            //start to play...

        }else {
            if (listName.contains("")){
              new android.support.v7.app.AlertDialog.Builder(mView.contextActivity())
                      .setMessage(R.string.set_name)
                      .setPositiveButton((R.string.ok), (dialog, which) -> dialog.dismiss())
                      .show();
            }else {
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
        dialog.setContentView(R.layout.inform_dialog);
        dialog.show();
    }

    @Override
    public void onDestroy() {

    }
}
