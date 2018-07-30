package com.natife.assotiation.initgame;

import android.content.res.Resources;
import android.util.Log;

import com.natife.assotiation.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InitGameRepository implements InitGameContract.Repository {
    List<String> listName;

    @Override
    public List<String> createListNamePlayers(Resources resources) {
        listName = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            listName.add(resources.getString(R.string.name_player) + " " + (i + 1));
        }
        return listName;
    }

    @Override
    public List<Integer> createListColor() {
        return Arrays.asList(R.color.colorPlayer1, R.color.colorPlayer2,
                R.color.colorPlayer3, R.color.colorPlayer4, R.color.colorPlayer5, R.color.colorPlayer6);
    }

    @Override
    public List<String> addNamePlayerInList(Resources resources) {
        listName.add(resources.getString(R.string.name_player) + " " + (listName.size() + 1));
        return listName;
    }
}
