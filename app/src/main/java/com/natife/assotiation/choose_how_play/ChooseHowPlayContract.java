package com.natife.assotiation.choose_how_play;

import android.content.Context;

import java.util.List;

public interface ChooseHowPlayContract {

    interface View {

        Context contextActivity();

        void showResultDialog();

        void showData(String name, Integer color, String word1, String word2);

    }

    interface Presenter {

        void word1Pressed(String word);

        void word2Pressed(String word);

        void layoutShow_Pressed();

        void layoutTell_Pressed();

        void layoutDraw_Pressed();

        void buttonGo();

        void findDataForFillFields(List<String> listName, List<Integer> listColor, List<String> listWords);

    }

    interface Repository {

        List<String> createListNamePlayers();

        List<Integer> createListColor();

    }
}