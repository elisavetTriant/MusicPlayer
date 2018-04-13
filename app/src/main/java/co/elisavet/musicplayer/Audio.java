package co.elisavet.musicplayer;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Eli on 12-Apr-18.
 * https://stackoverflow.com/questions/2091465/how-do-i-pass-data-between-activities-in-android-application/35406288#35406288
 * http://www.parcelabler.com/
 */

public class Audio implements Parcelable {

    private String title;
    private String album;
    private String artist;
    private String duration;
    private int albumArtResourceId = NO_IMAGE_PROVIDED;

    /** Constant value that represents no image was provided for this audio */
    private static final int NO_IMAGE_PROVIDED = -1;


    public Audio(String title, String album, String artist, String duration, int albumArtResourceId) {

        this.title = title;
        this.album = album;
        this.artist = artist;
        this.duration = duration;
        this.albumArtResourceId = albumArtResourceId;
    }

    public Audio(String title, String album, String artist, String duration) {

        this.title = title;
        this.album = album;
        this.artist = artist;
        this.duration = duration;
    }


    public String getTitle() {
        return title;
    }

    public String getAlbum() {
        return album;
    }

    public String getArtist() {
        return artist;
    }

    public String getDuration() {
        return duration ;
    }

    public int getAlbumArtResourceId() {
        return albumArtResourceId;
    }

    /**
     * Returns whether or not there is an album art for this song.
     */
    public boolean hasImage() {
        return albumArtResourceId != NO_IMAGE_PROVIDED;
    }

    protected Audio(Parcel in) {
        title = in.readString();
        album = in.readString();
        artist = in.readString();
        duration = in.readString();
        albumArtResourceId = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(album);
        dest.writeString(artist);
        dest.writeString(duration);
        dest.writeInt(albumArtResourceId);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Audio> CREATOR = new Parcelable.Creator<Audio>() {
        @Override
        public Audio createFromParcel(Parcel in) {
            return new Audio(in);
        }

        @Override
        public Audio[] newArray(int size) {
            return new Audio[size];
        }
    };
}