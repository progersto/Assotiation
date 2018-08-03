package com.natife.assotiation.initgame;

import android.content.Context;

import java.util.List;

public interface InitGameContract {

    interface View {
        void showListPlayers(List<String> listName, List<Integer> listColor);

        void changeScreen(boolean flagChange);

        Context contextActivity();

        void showSettingsDialog(boolean flagStartGame);

    }

    interface Presenter {
        void initPlayerList(List<String>listName);

        void btnAddPlayerClicked();

        void btnNextClicked(int difficultLevel);

        void btnBackClicked();

        void btnSettingsClicked();

        void onDestroy();
    }

    interface Repository {
        List<String> createListNamePlayers(List<String> listWithName);

        List<Integer> createListColor();

        List<String> addNamePlayerInList();

        List<String> createListWords(int difficultLevel, Context context);
    }

}
