package com.natife.assotiation.initgame;

import android.content.Context;

import com.natife.assotiation.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.natife.assotiation.utils.ListGenerator.createListSelectedLevel;

public class InitGameRepository implements InitGameContract.Repository {
    private List<Player> playerList;
    private List<Integer>colorList = new ArrayList<>(Arrays.asList(R.color.colorPlayer1, R.color.colorPlayer2,
            R.color.colorPlayer3, R.color.colorPlayer4, R.color.colorPlayer5, R.color.colorPlayer6));
    private static volatile InitGameRepository INSTANCE;

    public static InitGameContract.Repository getInstance() {
        if (INSTANCE == null) {
            synchronized (InitGameRepository.class){
                if (INSTANCE == null) {
                    INSTANCE = new InitGameRepository();
                }
            }
        }
        return INSTANCE;
    }


    @Override
    public List<Player> addNameInPlayerList() {
        playerList.add(new Player("", colorList.get(playerList.size() + 1), 0, 0));
        return playerList;
    }


    @Override
    public List<String> createListWords(int difficultLevel, Context context) {
        // создаем список слов выбранной сложности из файла в assets...
        return createListSelectedLevel(context, difficultLevel);
    }


    @Override
    public List<Player> createListPlayer(List<Player> listWithName) {
        playerList = new ArrayList<>();
        if (listWithName == null) {
            for (int i = 0; i < 3; i++) {
                playerList.add(new Player("", colorList.get(i), 0, 0));
            }
        } else {
            for (int i = 0; i < listWithName.size(); i++) {
                playerList.add(new Player(listWithName.get(i).getName(), colorList.get(i), 0, 0));
            }
        }
        return playerList;
    }


    public List<Player> getCurrentPlayerList(){
        return playerList;
    }


}
