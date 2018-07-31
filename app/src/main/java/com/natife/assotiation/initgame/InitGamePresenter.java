package com.natife.assotiation.initgame;

import java.util.List;

public class InitGamePresenter implements InitGameContract.Presenter  {
    //Компоненты MVP приложения
    private InitGameContract.View mView;
    private InitGameContract.Repository mRepository;
    private List<String> listName;
    private List<Integer> listColor;
    private boolean flagStartGame = false;

    //передаем экземпляр View
    public InitGamePresenter(InitGameContract.View mView) {
        this.mView = mView;
        this.mRepository = new InitGameRepository();
    }


    @Override
    public void initPlayerList() {
        listName = mRepository.createListNamePlayers();
        listColor = mRepository.createListColor();
        mView.showListPlayers(listName, listColor);
    }

    @Override
    public void btnAddPlayerClicked() {
        if (listName.size() <= 5){
            listName = mRepository.addNamePlayerInList();
            mView.showListPlayers(listName, listColor);
        }
    }

    @Override
    public void btnNextClicked() {
        if (flagStartGame) {
            flagStartGame = false;
            //start to play...

        }else {
            mView.changeScreen(true);
            flagStartGame = true;
        }
    }

    @Override
    public void btnBackClicked() {
        flagStartGame = false;
        mView.changeScreen(false);
    }

    @Override
    public void btnSettingsClicked() {

    }

    @Override
    public void onDestroy() {

    }
}
