package com.natife.assotiation.initgame;

import android.content.Context;

import java.util.List;

public interface InitGameContract {

    interface View {
        void showListPlayers(List<Player> listPlayer);

        void changeScreen(boolean flagChange);

        Context contextActivity();

        void showSettingsDialog(boolean flagStartGame);

    }

    interface Presenter {
        void initPlayerList(List<Player>listWithName);

        void btnAddPlayerClicked();

        void btnNextClicked(int difficultLevel);

        void btnBackClicked();

        void btnSettingsClicked();

        void onDestroy();
    }

    interface Repository {
        List<Player> createListPlayer(List<Player> listWithName);

        List<Player> addNameInPlayerList();

        List<String> createListWords(int difficultLevel, Context context);
    }

}
