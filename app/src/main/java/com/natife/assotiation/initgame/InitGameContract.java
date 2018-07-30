package com.natife.assotiation.initgame;

import android.content.res.Resources;

import java.util.List;

public interface InitGameContract {

    interface View {
        void showListPlayers(List<String> listName, List<Integer> listColor);

        Resources getResourceForListName();

        void changeScreen(boolean flagChange);
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
        List<String> createListNamePlayers(Resources resources);

        List<Integer> createListColor();

        List<String> addNamePlayerInList(Resources resources);
    }

}
