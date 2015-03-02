package iplayer.example.com.iplayer.Services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.ServiceState;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import iplayer.example.com.iplayer.IpMain;
import iplayer.example.com.iplayer.Model.Song;
import iplayer.example.com.iplayer.NotificationMusic;
import iplayer.example.com.iplayer.R;
import iplayer.example.com.iplayer.external.RemoteControlClientCompat;

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
    public static final String BROADCAST_ORDER = "MUSIC_SERVICE";
    public static final String BROADCAST_EXTRA_GET_ORDER = "com.kure.musicplayer.dasdas.MUSIC_SERVICE";

    public static final String BROADCAST_ORDER_PLAY            = "PLAY";
    public static final String BROADCAST_ORDER_PAUSE           = "PAUSE";
    public static final String BROADCAST_ORDER_TOGGLE_PLAYBACK = "dlsadasd";
    public static final String BROADCAST_ORDER_STOP            = "STOP";
    public static final String BROADCAST_ORDER_SKIP            = "SKIP";
    public static final String BROADCAST_ORDER_REWIND          = "REWIND";

    enum Servicestate{

        Stopped, //when media player has stopped or cannot play

        Preparing, //Media player is preparing to play

        Playing, //Media Player is playing

        Paused, //Media player has stopped

    }


    //setting the current state.
    Servicestate servicestate = Servicestate.Preparing;

    /**
     * Controller that communicates with the lock screen,
     * providing that fancy widget.
     */
    RemoteControlClientCompat lockscreenController = null;

    /**
     * We use this to get the media buttons' Broadcasts and
     * to control the lock screen widget.
     *
     * Component name of the MusicIntentReceiver.
     */
    ComponentName mediaButtonEventReceiver;

    /**
     * Use this to get audio focus:
     *
     * 1. Making sure other music apps don't play
     *    at the same time;
     * 2. Guaranteeing the lock screen widget will
     *    be controlled by us;
     */
    AudioManager audioManager;


    /**
     * Whenever we're created, reset the MusicPlayer and
     * start the MusicScrobblerService.
     */
    public void onCreate() {
        super.onCreate();

        currentSongPosition = 0;

        randomNumberGenerator = new Random();

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        initMusicPlayer();

        Context context = getApplicationContext();

        /**Starting the scrobbler service.
        Intent scrobblerIntent = new Intent(context, ServiceScrobbleMusic.class);
        context.startService(scrobblerIntent);
         **/

        // Registering our BroadcastReceiver to listen to orders
        // from inside our own application.
        LocalBroadcastManager
                .getInstance(getApplicationContext())
                .registerReceiver(localBroadcastReceiver, new IntentFilter(ServicePlayMusic.BROADCAST_ORDER));

        // Registering the headset broadcaster for info related
        // to user plugging the headset.
        IntentFilter headsetFilter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(headsetBroadcastReceiver, headsetFilter);

        Log.w(TAG, "onCreate");
    }

    public void initMusicPlayer() {
        if (player == null)
            player = new MediaPlayer();

        // Assures the CPU continues running this service
        // even when the device is sleeping.
        player.setWakeMode(getApplicationContext(),
                PowerManager.PARTIAL_WAKE_LOCK);

        //player.setAudioStreamType(AudioManager.STREAM_MUSIC);

        // These are the events that will "wake us up"
        player.setOnPreparedListener(this); // player initialized
        player.setOnCompletionListener(this); // song completed
        player.setOnErrorListener(this);

        Log.w(TAG, "initMusicPlayer");
    }

    /**
     * Cleans resources from Android's native MediaPlayer.
     *
     * @note According to the MediaPlayer guide, you should release
     *       the MediaPlayer as often as possible.
     *       For example, when losing Audio Focus for an extended
     *       period of time.
     */
    public void stopMusicPlayer() {
        if (player == null)
            return;

        player.stop();
        player.release();
        player = null;

        Log.w(TAG, "stopMusicPlayer");
    }


    /**
     * Sets the "Now Playing List"
     *
     * @param theSongs Songs list that will play from now on.
     *
     * @note Make sure to call {@link #//playSong()} after this.
     */
    public void setList(ArrayList<Song> theSongs) {
        songs = theSongs;
    }

    /**
     * Appends a song to the end of the currently playing queue.
     *
     * @param song New song to put at the end.
     */
    public void add(Song song) {
        songs.add(song);
    }

    public static class ExternalBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.w(TAG, "external broadcast");

            // Broadcasting orders to our MusicService
            // locally (inside the application)
            LocalBroadcastManager local = LocalBroadcastManager.getInstance(context);

            String action = intent.getAction();

            // Headphones disconnected
            if (action.equals(android.media.AudioManager.ACTION_AUDIO_BECOMING_NOISY)) {


                // ADD SETTINGS HERE
                String text = context.getString(R.string.service_music_play_headphone_off);
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();

                // send an intent to our MusicService to telling it to pause the audio
                Intent broadcastIntent = new Intent(ServicePlayMusic.BROADCAST_ORDER);
                broadcastIntent.putExtra(ServicePlayMusic.BROADCAST_EXTRA_GET_ORDER, ServicePlayMusic.BROADCAST_ORDER_PAUSE);

                local.sendBroadcast(broadcastIntent);
                Log.w(TAG, "becoming noisy");
                return;
            }

            if (action.equals(Intent.ACTION_MEDIA_BUTTON)) {

                // Which media key was pressed
                KeyEvent keyEvent = (KeyEvent) intent.getExtras().get(Intent.EXTRA_KEY_EVENT);

                // Not interested on anything other than pressed keys.
                if (keyEvent.getAction() != KeyEvent.ACTION_DOWN)
                    return;

                String intentValue = null;

                switch (keyEvent.getKeyCode()) {

                    case KeyEvent.KEYCODE_HEADSETHOOK:
                    case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
                        intentValue = ServicePlayMusic.BROADCAST_ORDER_TOGGLE_PLAYBACK;
                        Log.w(TAG, "media play pause");
                        break;

                    case KeyEvent.KEYCODE_MEDIA_PLAY:
                        intentValue = ServicePlayMusic.BROADCAST_ORDER_PLAY;
                        Log.w(TAG, "media play");
                        break;

                    case KeyEvent.KEYCODE_MEDIA_PAUSE:
                        intentValue = ServicePlayMusic.BROADCAST_ORDER_PAUSE;
                        Log.w(TAG, "media pause");
                        break;

                    case KeyEvent.KEYCODE_MEDIA_NEXT:
                        intentValue = ServicePlayMusic.BROADCAST_ORDER_SKIP;
                        Log.w(TAG, "media next");
                        break;

                    case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                        // TODO: ensure that doing this in rapid succession actually plays the
                        // previous song
                        intentValue = ServicePlayMusic.BROADCAST_ORDER_REWIND;
                        Log.w(TAG, "media previous");
                        break;
                }

                // Actually sending the Intent
                if (intentValue != null) {
                    Intent broadcastIntent = new Intent(ServicePlayMusic.BROADCAST_ORDER);
                    broadcastIntent.putExtra(ServicePlayMusic.BROADCAST_EXTRA_GET_ORDER, intentValue);

                    local.sendBroadcast(broadcastIntent);
                }
            }
        }
    }

    /**
     * Will keep an eye on global broadcasts related to
     * the Headset.
     */
    BroadcastReceiver headsetBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            // Headphones just connected (or not)
            if (action.equals(Intent.ACTION_HEADSET_PLUG)) {

                Log.w(TAG, "headset plug");
                boolean connectedHeadphones = (intent.getIntExtra("state", 0) == 1);
                boolean connectedMicrophone = (intent.getIntExtra("microphone", 0) == 1) && connectedHeadphones;

                // User just connected headphone and the player was paused,
                // so we shoud restart the music.
                if (connectedMicrophone && (serviceState == ServiceState.Paused)) {



                    // Will only do it if it's Setting is enabled, of course
                    if (IpMain.settings.get("play_headphone_on", true)) {
                        LocalBroadcastManager local = LocalBroadcastManager.getInstance(context);


                        Intent broadcastIntent = new Intent(ServicePlayMusic.BROADCAST_ORDER);
                        broadcastIntent.putExtra(ServicePlayMusic.BROADCAST_EXTRA_GET_ORDER, ServicePlayMusic.BROADCAST_ORDER_PLAY);

                        local.sendBroadcast(broadcastIntent);
                    }
                }

                // I wonder what's this for
                String headsetName = intent.getStringExtra("name");

                if (connectedHeadphones) {
                    String text = context.getString(R.string.service_music_play_headphone_on, headsetName);

                    Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
                }
            }
        }
    };























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
