package com.natife.assotiation.game;

import android.content.Context;

import java.util.List;

public interface GameContract {
    interface View {

        Context contextActivity();

        void showResultDialog();
    }

    interface Presenter {

        void resultPressed();
    }

    interface Repository {

        List<String> createListNamePlayers();

        List<Integer> createListColor();
    }
}
