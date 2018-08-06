package com.natife.assotiation.initgame;

import android.os.Parcel;
import android.os.Parcelable;

public class Player implements Parcelable{
    private String name;
    private int Color;
    private int countWords;
    private int countScore;

    public Player(String name, int color, int countWords, int countScore) {
        this.name = name;
        Color = color;
        this.countWords = countWords;
        this.countScore = countScore;
    }

    protected Player(Parcel in) {
        name = in.readString();
        Color = in.readInt();
        countWords = in.readInt();
        countScore = in.readInt();
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return Color;
    }

    public void setColor(int color) {
        Color = color;
    }

    public int getCountWords() {
        return countWords;
    }

    public void setCountWords(int countWords) {
        this.countWords = countWords;
    }

    public int getCountScore() {
        return countScore;
    }

    public void setCountScore(int countScore) {
        this.countScore = countScore;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(Color);
        parcel.writeInt(countWords);
        parcel.writeInt(countScore);
    }
}
