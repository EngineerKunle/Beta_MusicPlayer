package iplayer.example.com.iplayer.Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kunle on 06/01/2015.
 */

/**
this not finished and here is where the logic of the class will be.
 */
public class SongList {
    /**
     * Big list with all the Songs found.
     */
    public ArrayList<Song> songs = new ArrayList<Song>();

    /**
     * Big list with all the Playlists found.
     */
   // public ArrayList<Playlist> playlists = new ArrayList<Playlist>();

    /**
     * Maps song's genre IDs to song's genre names.
     * @note It's only available after calling `scanSongs`.
     */
    private HashMap<String, String> genreIdToGenreNameMap;

    /**
     * Maps song's IDs to song genre IDs.
     * @note It's only available after calling `scanSongs`.
     */
    private HashMap<String, String> songIdToGenreIdMap;

    /**
     * Flag that tells if successfully scanned all songs.
     */
    private boolean scannedSongs;

    /**
     * Flag that tells if we're scanning songs right now.
     */
    private boolean scanningSongs;

    /**
     * Tells if we've successfully scanned all songs on
     * the device.
     *
     * This will return `false` both while we're scanning
     * for songs and if some error happened while scanning.
     */
}
