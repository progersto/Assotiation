package com.natife.assotiation.choose_how_play;

import android.content.Context;

import java.util.List;

public class ChooseHowPlayContract {

    interface View {

        Context contextActivity();

        void showResultDialog();

    }

    interface Presenter {

        void initPlayerList();

        void btnAddPlayerClicked();

    }

    interface Repository {

        List<String> createListNamePlayers();

        List<Integer> createListColor();

    }
}
