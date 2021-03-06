package com.natife.assotiation.choose_how_play;

import android.os.CountDownTimer;

import com.natife.assotiation.initgame.InitGameContract;
import com.natife.assotiation.initgame.InitGameRepository;
import com.natife.assotiation.initgame.Player;
import com.natife.assotiation.utils.PreferUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChooseHowPlayPresenter implements ChooseHowPlayContract.Presenter {
    private ChooseHowPlayContract.View mView;
    private InitGameContract.Repository mRepository;
    private List<Player> playerList = new ArrayList<>();
    private List<String> listWords = new ArrayList<>();
    private int positionWord1 = -1;
    private int positionWord2 = -1;
    //    private Intent intent;
    private String name;
    private int colorPlayer = 0;
    private String word;
    private int positionPlayer;
    private CountDownTimer mCountDownTimer;
    private final int COUNT_DOWN_INTERVAL = 1000;
    private int playerPosDefault;
    private int lapDefault;
    private String word1;
    private String word2;

    //передаем экземпляр View
    public ChooseHowPlayPresenter(ChooseHowPlayContract.View mView) {
        this.mView = mView;
        this.mRepository = InitGameRepository.getInstance();
        playerPosDefault = 0;
        lapDefault = new PreferUtil().restoreNumberCircles(mView.getContextActivity());
    }

    @Override
    public void findDataForFillFields(List<Player> playerList, List<String> listWords, int timeGame) {
        this.playerList = playerList;
        positionPlayer = getPositionPlayer();

        if (lapDefault != 0) {
            this.listWords = listWords;
            positionWord1 = getRandom(listWords.size());
            positionWord2 = getRandom(listWords.size());
            word1 = listWords.get(positionWord1);
            word2 = listWords.get(positionWord2);
            name = playerList.get(positionPlayer).getName();
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            word1 = word1.substring(0, 1).toUpperCase() + word1.substring(1);
            word2 = word2.substring(0, 1).toUpperCase() + word2.substring(1);
            colorPlayer = playerList.get(positionPlayer).getColor();
            mView.showData(name, colorPlayer, word1, word2);
            startTimerGame(timeGame);
        } else {
            mView.gameOver();
            mView.showData(name, colorPlayer, word1, word2);
        }

    }

    @Override
    public List<Player> getPlayerList() {
        return mRepository.getCurrentPlayerList();
    }


    @Override
    public void buttonGoPressed() {
        mView.startGameActivity(positionPlayer);
    }

    private int getRandom(int size) {
        Random rand = new Random();
        int position = rand.nextInt(size);

        if (positionWord1 != -1 && positionWord1 == position) {
            getRandom(size);
        }
        return position;
    }// getRandom


    private void startTimerGame(int timeGame) {
        mCountDownTimer = new CountDownTimer((timeGame + 1) * 1000, COUNT_DOWN_INTERVAL) {

            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                mView.gameOver();
            }
        };
        mCountDownTimer.start();
    }


    @Override
    public void stopTimerGame() {
        mCountDownTimer.cancel();
    }


    private int getPositionPlayer() {
        int pos = 0;
        if (playerPosDefault < playerList.size()) {
            pos = playerPosDefault;
            playerPosDefault++;
        } else {
            playerPosDefault = 0;
            lapDefault--;
        }
        return pos;
    }

}
