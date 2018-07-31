package com.natife.assotiation.initgame;

import android.content.res.Resources;
import android.util.Log;

import com.natife.assotiation.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InitGameRepository implements InitGameContract.Repository {
    private List<String> listName;

    @Override
    public List<String> createListNamePlayers() {
        listName = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            listName.add("");
        }
        return listName;
    }

    @Override
    public List<Integer> createListColor() {
        return Arrays.asList(R.color.colorPlayer1, R.color.colorPlayer2,
                R.color.colorPlayer3, R.color.colorPlayer4, R.color.colorPlayer5, R.color.colorPlayer6);
    }

    @Override
    public List<String> addNamePlayerInList() {
        listName.add("");
        return listName;
    }

    @Override
    public List<String> createListWords(int difficultLevel) {
        Log.d("ddd", "ddd");
        List<String> list = new ArrayList<>();
        list.add("d");
        // создаем список слов из файла в assets...

        return list;
    }


}
