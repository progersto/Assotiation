package com.natife.assotiation.initgame;

import android.content.Context;

import java.util.List;

public interface InitGameContract {

    interface View {
        void showListPlayers(List<String> listName, List<Integer> listColor);

        void changeScreen(boolean flagChange);

        Context contextActivity();

    }

    interface Presenter {
        void  initPlayerList();

        void btnAddPlayerClicked();

        void btnNextClicked();

        void btnBackClicked();

        void btnSettingsClicked();

        void onDestroy();
    }

    interface Repository {
        List<String> createListNamePlayers();

        List<Integer> createListColor();

        List<String> addNamePlayerInList();


    }

}
