package com.example.dud_mobile.models.content;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import com.google.gson.annotations.SerializedName;

public class Library implements Parcelable {

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("author")
    private String author;

    @SerializedName("content")
    private String content;

    @SerializedName("filePath")
    private String filePath;

    @SerializedName("level")
    private Level level;

    public Library(int id, String title, String description, String author, String content, String filePath, Level level) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.content = content;
        this.filePath = filePath;
        this.level = level;
    }

    protected Library(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        author = in.readString();
        content = in.readString();
        filePath = in.readString();
        level = in.readParcelable(Level.class.getClassLoader());
    }

    public static final Creator<Library> CREATOR = new Creator<Library>() {
        @Override
        public Library createFromParcel(Parcel in) {
            return new Library(in);
        }

        @Override
        public Library[] newArray(int size) {
            return new Library[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(author);
        dest.writeString(content);
        dest.writeString(filePath);
        dest.writeParcelable(level, flags);
    }
}
