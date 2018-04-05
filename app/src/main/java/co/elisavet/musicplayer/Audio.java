package co.elisavet.musicplayer;

import android.graphics.drawable.Drawable;

/**
 * Created by Eli on 26-Mar-18.
 * https://www.sitepoint.com/a-step-by-step-guide-to-building-an-android-audio-player-app/
 */

public class Audio {

    private String data;
    private String title;
    private String album;
    private String album_id;
    private String album_art;
    private String artist;
    private String duration;

    public Audio(String data, String title, String album, String album_id, String album_art, String artist, String duration) {
        this.data = data;
        this.title = title;
        this.album = album;
        this.album_id = album_id;
        this.album_art = album_art;
        this.artist = artist;
        this.duration = duration;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getAlbumId() {
        return album_id;
    }

    public void setAlbumId(String album_id) {
        this.album_id = album_id;
    }

    public String getAlbumArt() {
        return album_art;
    }

    public void setAlbumArt(String album_art) {
        this.album_art = album_art;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDuration() {
        long milliseconds = Long.valueOf(duration);
        long seconds= (milliseconds/1000)%60;
        int minutes= (int)(milliseconds/1000-seconds)/60;
        return String.valueOf(minutes)+":"+ String.format("%02d", seconds);
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

}
