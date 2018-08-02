package com.natife.assotiation.choose_how_play;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChooseHowPlayPresenter implements ChooseHowPlayContract.Presenter {
    private ChooseHowPlayContract.View mView;
    private ChooseHowPlayContract.Repository mRepository;
    private List<String> listName = new ArrayList<>();
    private List<Integer> listColor = new ArrayList<>();
    private List<String> listWords = new ArrayList<>();
    private int positionWord1 = -1;
    private int positionWord2 = -1;

    //передаем экземпляр View
    public ChooseHowPlayPresenter(ChooseHowPlayContract.View mView) {
        this.mView = mView;
        this.mRepository = new ChooseHowPlayRepository();
    }

    @Override
    public void findDataForFillFields(List<String> listName, List<Integer> listColor, List<String> listWords) {
        this.listName = listName;
        this.listColor = listColor;
        this.listWords = listWords;
        int positionPlayer = getRandom(listName.size());
        positionWord1 = getRandom(listWords.size());
        positionWord2 = getRandom(listWords.size());
        String word1 = listWords.get(positionWord1);
        String word2 = listWords.get(positionWord2);
        String name = listName.get(positionPlayer);
        word1 = word1.substring(0, 1).toUpperCase() + word1.substring(1);
        word2 = word2.substring(0, 1).toUpperCase() + word2.substring(1);
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        mView.showData(name, listColor.get(positionPlayer), word1, word2);
    }


    @Override
    public void word1Pressed(String word) {

    }

    @Override
    public void word2Pressed(String word) {

    }

    @Override
    public void layoutShow_Pressed() {

    }

    @Override
    public void layoutTell_Pressed() {

    }

    @Override
    public void layoutDraw_Pressed() {

    }

    @Override
    public void buttonGo() {

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
