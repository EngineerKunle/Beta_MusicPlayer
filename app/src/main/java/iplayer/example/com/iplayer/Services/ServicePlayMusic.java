package iplayer.example.com.iplayer.Services;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.Random;

import iplayer.example.com.iplayer.Model.Song;

/**
 * Created by EngineerKunle on 20/01/15.
 */

//need to work on audiofocus class

public class ServicePlayMusic extends Service 	implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener,
        AudioManager.OnAudioFocusChangeListener {

    /**
     * AudioFocus will allow your app to have priority over other audio
     * playing apps.
     */

    private MediaPlayer player; //creating Mediaplayer variable

    public static final String BROADCAST_ACTION = "com.iplayer.MUSIC_SERVICE";

    /** String used to get the current state Extra on the Broadcast Intent */
    public static final String BROADCAST_EXTRA_STATE = "x_japan";

    /** String used to get the song ID Extra on the Broadcast Intent */
    public static final String BROADCAST_EXTRA_SONG_ID = "tenacious_d";

    // All possible messages this Service will broadcast
    // Ignore the actual values

    /** Broadcast for when some music started playing */
    public static final String BROADCAST_EXTRA_PLAYING = "Usher";

    /** Broadcast for when some music just got paused */
    public static final String BROADCAST_EXTRA_PAUSED = "MJ";

    /** Broadcast for when a paused music got unpaused*/
    public static final String BROADCAST_EXTRA_UNPAUSED = "Chris Brown";

    /** Broadcast for when current music got played until the end */
    public static final String BROADCAST_EXTRA_COMPLETED = "jB";

    /** Broadcast for when the user skipped to the next song */
    public static final String BROADCAST_EXTRA_SKIP_NEXT = "skipped";

    /** Broadcast for when the user skipped to the previous song */
    public static final String BROADCAST_EXTRA_SKIP_PREVIOUS = "previous";

    /**
     * List of songs we're  currently playing.
     */
    private ArrayList<Song> songs;

    /**
     * Index of the current song we're playing on the `songs` list.
     */
    public int currentSongPosition;

    /**
     * Copy of the current song being played (or paused).
     *
     * Use it to get info from the current song.
     */
    public Song currentSong = null;

    /**
     * Flag that indicates whether we're at Shuffle mode.
     */
    private boolean shuffleMode = false;

    /**
     * Random number generator for the Shuffle Mode.
     */
    private Random randomNumberGenerator;

    private boolean repeatMode = false;

    /**
     * Spawns an on-going notification with our current
     * playing song.
     */
    private NotificationMusic notification = null;

    // The tag we put on debug messages
    final static String TAG = "MusicService";

    // These are the Intent actions that we are prepared to handle. Notice that the fact these
    // constants exist in our class is a mere convenience: what really defines the actions our
    // service can handle are the <action> tags in the <intent-filters> tag for our service in
    // AndroidManifest.xml.
    public static final String BROADCAST_ORDER = "com.kure.musicplayer.MUSIC_SERVICE";
    public static final String BROADCAST_EXTRA_GET_ORDER = "com.kure.musicplayer.dasdas.MUSIC_SERVICE";

    public static final String BROADCAST_ORDER_PLAY            = "com.kure.musicplayer.action.PLAY";
    public static final String BROADCAST_ORDER_PAUSE           = "com.kure.musicplayer.action.PAUSE";
    public static final String BROADCAST_ORDER_TOGGLE_PLAYBACK = "dlsadasd";
    public static final String BROADCAST_ORDER_STOP            = "com.kure.musicplayer.action.STOP";
    public static final String BROADCAST_ORDER_SKIP            = "com.kure.musicplayer.action.SKIP";
    public static final String BROADCAST_ORDER_REWIND          = "com.kure.musicplayer.action.REWIND";


    @Override
    public void onAudioFocusChange(int focusChange) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
