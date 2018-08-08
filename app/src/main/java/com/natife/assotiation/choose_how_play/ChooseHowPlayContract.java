package com.natife.assotiation.choose_how_play;

import android.content.Context;

import com.natife.assotiation.initgame.Player;

import java.util.List;

public interface ChooseHowPlayContract {

    interface View {

        void startGameActivity(int posPlayer);

        void showResultDialog();

        void showData(String name, Integer color, String word1, String word2);

        Context getContextActivity();

        void gameOver();
    }

    interface Presenter {

        void stopTimerGame();

        void buttonGoPressed();

        void findDataForFillFields(List<Player> playerList, List<String> listWords, int timeGame);

        List<Player> getPlayerList();
    }

    interface Repository {

        List<String> createListNamePlayers();

        List<Integer> createListColor();

    }
}
