package iplayer.example.com.iplayer.Model;

/**
 * Created by Kunle on 04/01/2015.
 */
public class Song {

    private long id;
    private String filePath;

    public Song(long id, String filePath) {
        this.id        = id;
        this.filePath  = filePath;
    }

    /**
     * Identifier for the song on the Android system.
     * (so we can locate the file anywhere)
     */
    public long getId() {
        return id;
    }

    /**
     * Full path for the music file within the filesystem.
     */
    public String getFilePath() {
        return filePath;
    }

    // optional metadata

    private String title       = "";
    private String artist      = "";
    private String album       = "";
    private int    year        = -1;
    private String genre       = "";
    private int    track_no    = -1;
    private long   duration_ms = -1;

    //getters & setters  for optional data

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }


    public String getArtist() {
        return artist;
    }
    public void setArtist(String artist) {
        this.artist = artist;
    }


    public String getAlbum() {
        return album;
    }
    public void setAlbum(String album) {
        this.album = album;
    }


    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }


    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }


    public int getTrackNumber() {
        return track_no;
    }
    public void setTrackNumber(int track_no) {
        this.track_no = track_no;
    }

    /**
     * Sets the duration of the song, in miliseconds.
     */
    public void setDuration(long duration_ms) {
        this.duration_ms = duration_ms;
    }
    /**
     * Returns the duration of the song, in miliseconds.
     */
    public long getDuration() {
        return duration_ms;
    }
    public long getDurationSeconds() {
        return getDuration() / 1000;
    }
    public long getDurationMinutes() {
        return getDurationSeconds() / 60;
    }

}
