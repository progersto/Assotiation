package com.natife.assotiation.game;

import android.os.CountDownTimer;
import android.util.Log;

import com.natife.assotiation.initgame.InitGameContract;
import com.natife.assotiation.initgame.InitGameRepository;
import com.natife.assotiation.initgame.Player;
import com.natife.assotiation.utils.PreferUtil;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class GamePresenter implements GameContract.Presenter {
    private GameContract.View mView;
    private InitGameContract.Repository mRepository;

    private int timeGame;
    private int numberLap;
    private CountDownTimer mCountDownTimer;
    private int countDownInterval = 1000;

    //передаем экземпляр View
    public GamePresenter(GameContract.View mView) {
        this.mView = mView;
        this.mRepository = InitGameRepository.getInstance();

        PreferUtil preferUtil = new PreferUtil();
        //get info from preferences

//        timeGame = preferUtil.restoreTimeGame(mView.contextActivity());
//        numberLap = preferUtil.restoreNumberCircles(mView.contextActivity());
    }

    @Override
    public List<Player> getPlayerList() {
        return mRepository.getCurrentPlayerList();
    }


    @Override
    public void playerWin(List<Player> playerList, int winPlayer, int positionGuessingPlayer) {
        int score = playerList.get(winPlayer).getCountScore() + 1;
        int countWords = playerList.get(winPlayer).getCountWords() + 1;
        int scoreGuessingPlayer = playerList.get(positionGuessingPlayer).getCountScore() + 1;
        playerList.get(positionGuessingPlayer).setCountScore(scoreGuessingPlayer);
        playerList.get(winPlayer).setCountScore(score);
        playerList.get(winPlayer).setCountWords(countWords);

//        numberLap -= 1;
        mView.finishCurrentGame();
    }

    @Override
    public void notWin() {
//        numberLap -= 1;
        mView.finishCurrentGame();
    }

    @Override
    public void initTimer(boolean timerBig, int timeMove) {
        mCountDownTimer = new CountDownTimer((timeMove + 1) * 1000, countDownInterval) {

            @Override
            public void onTick(long millisUntilFinished) {
                Log.v("Log_tag", "Tick of Progress" + millisUntilFinished);

                if (timerBig) {
                    int progress = (timeMove - ((int) millisUntilFinished / 1000));
                    mView.setCircularProgressbar(progress);
                }

                long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished));
                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished));
                mView.setTextTimer(String.format(Locale.getDefault(), "%01d:%02d", minutes, seconds));
            }

            @Override
            public void onFinish() {

            }
        };
        mCountDownTimer.start();
    }

    @Override
    public void stopCountDownTimer() {
        mCountDownTimer.cancel();
    }


}
