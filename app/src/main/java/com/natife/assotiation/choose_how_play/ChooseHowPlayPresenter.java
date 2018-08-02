package com.natife.assotiation.choose_how_play;

public class ChooseHowPlayPresenter implements ChooseHowPlayContract.Presenter {
    private ChooseHowPlayContract.View mView;
    private ChooseHowPlayContract.Repository mRepository;

    //передаем экземпляр View
    public ChooseHowPlayPresenter(ChooseHowPlayContract.View mView) {
        this.mView = mView;
        this.mRepository = new ChooseHowPlayRepository();
    }


    @Override
    public void initPlayerList() {

    }

    @Override
    public void btnAddPlayerClicked() {

    }
}
