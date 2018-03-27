package co.elisavet.musicplayer;

/**
 * Created by Eli on 26-Mar-18.
 * https://www.sitepoint.com/a-step-by-step-guide-to-building-an-android-audio-player-app/
 */

public class Audio {

    private String data;
    private String title;
    private String album;
    private String artist;
    private String duration;

    public Audio(String data, String title, String album, String artist, String duration) {
        this.data = data;
        this.title = title;
        this.album = album;
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
