package com.example.dud_mobile.models.content;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import com.google.gson.annotations.SerializedName;

public class Level implements Parcelable {

    @SerializedName("id")
    private int id;

    @SerializedName("levelName")
    private String levelName;

    public Level(int id, String levelName) {
        this.id = id;
        this.levelName = levelName;
    }

    protected Level(Parcel in) {
        id = in.readInt();
        levelName = in.readString();
    }

    public static final Creator<Level> CREATOR = new Creator<Level>() {
        @Override
        public Level createFromParcel(Parcel in) {
            return new Level(in);
        }

        @Override
        public Level[] newArray(int size) {
            return new Level[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(levelName);
    }
}
