package com.natife.assotiation.choose_how_play;

import android.content.Intent;
import android.os.Parcelable;

import com.natife.assotiation.game.GameActivity;
import com.natife.assotiation.initgame.InitGameContract;
import com.natife.assotiation.initgame.InitGameRepository;
import com.natife.assotiation.initgame.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChooseHowPlayPresenter implements ChooseHowPlayContract.Presenter{
    private ChooseHowPlayContract.View mView;
    private InitGameContract.Repository mRepository;
    private List<Player> playerList = new ArrayList<>();
    private List<String> listWords = new ArrayList<>();
    private int positionWord1 = -1;
    private int positionWord2 = -1;
    private Intent intent;
    private String name;
    private int colorPlayer = 0;
    private String word;
    private int positionPlayer;

    //передаем экземпляр View
    public ChooseHowPlayPresenter(ChooseHowPlayContract.View mView) {
        this.mView = mView;
        this.mRepository = InitGameRepository.getInstance();
    }

    @Override
    public void findDataForFillFields(List<Player> playerList,  List<String> listWords) {
        this.playerList = playerList;
        this.listWords = listWords;
        positionPlayer = getRandom(playerList.size());
        positionWord1 = getRandom(listWords.size());
        positionWord2 = getRandom(listWords.size());
        String word1 = listWords.get(positionWord1);
        String word2 = listWords.get(positionWord2);
        name = playerList.get(positionPlayer).getName();
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        word1 = word1.substring(0, 1).toUpperCase() + word1.substring(1);
        word2 = word2.substring(0, 1).toUpperCase() + word2.substring(1);
        colorPlayer = playerList.get(positionPlayer).getColor();
        mView.showData(name, colorPlayer, word1, word2);
        intent = new Intent(mView.contextActivity(), GameActivity.class);
    }

    @Override
    public List<Player> getPlayerList() {
        return mRepository.getCurrentPlayerList();
    }


    @Override
    public void word1Pressed(String word) {
        this.word = word;
    }

    @Override
    public void word2Pressed(String word) {
        this.word = word;
    }

    @Override
    public void layoutShow_Pressed() {
        intent.putExtra("how_explain", "show");
    }

    @Override
    public void layoutTell_Pressed() {
        intent.putExtra("how_explain", "tell");
    }

    @Override
    public void layoutDraw_Pressed() {
        intent.putExtra("how_explain", "draw");
    }

    @Override
    public void resultPressed() {
        mView.showResultDialog();
    }

    @Override
    public void buttonGo() {
        intent.putStringArrayListExtra("listWords", (ArrayList<String>) listWords);
        intent.putParcelableArrayListExtra("playerList", (ArrayList<? extends Parcelable>) playerList);
        intent.putExtra("positionPlayer", positionPlayer);
        intent.putExtra("word", word);
        mView.contextActivity().startActivity(intent);
    }

    private int getRandom(int size) {
        Random rand = new Random();
        int position = rand.nextInt(size);

        if (positionWord1 != -1 && positionWord1 == position) {
            getRandom(size);
        }

        return position;
    }// getRandom


}
