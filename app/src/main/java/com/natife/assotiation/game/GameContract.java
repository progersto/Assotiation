package com.natife.assotiation.game;

import android.content.Context;

import com.natife.assotiation.initgame.Player;

import java.util.List;

public interface GameContract {
    interface View {

        Context contextActivity();

        void finishCurrentGame();

        void setCircularProgressbar(int progress);

        void setTextTimer(String time);
    }

    interface Presenter {

        void playerWin(List<Player> playerList, int winPlayer, int positionGuessingPlayer);

        void notWin();

        void initTimer(boolean timerBig, int timeMove);

        void  stopCountDownTimer();

        List<Player> getPlayerList();
    }

    interface Repository {

        List<String> createListNamePlayers();

        List<Integer> createListColor();
    }
}
