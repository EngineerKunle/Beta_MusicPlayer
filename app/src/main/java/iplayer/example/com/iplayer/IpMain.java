package iplayer.example.com.iplayer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.IBinder;

import java.util.ArrayList;

/**
 * Created by Kunle on 03/01/2015.
 */
public class IpMain {

    //Logic for Whole player..

    /**
     * All the songs on the device.
     */
    public static SongList songs = new SongList();


    /**
     * Our custom service that allows the music to play
     * even when the app is not on focus.
     */
    public static ServicePlayMusic musicService = null;

    /**
     * Contains the songs that are going to be shown to
     * the user on a particular menu.
     *
     * @note IGNORE THIS - don't mess with it.
     *
     * Every `ActivityMenu*` uses this temporary variable to
     * store subsections of `SongList` and set `ActivityListSongs`
     * to display it.
     */
    public static ArrayList<Song> musicList = null;

    /**
     * List of the songs being currently played by the user.
     *
     * (independent of the UI)
     *
     * TODO remove this shit
     */
    public static ArrayList<Song> nowPlayingList = null;

    /**
     * Flag that tells if the Main Menu has an item that
     * sends the user to the Now Playing Activity.
     *
     * It's here because when firstly initializing the
     * application, there's no Now Playing Activity.
     */
    public static boolean mainMenuHasNowPlayingItem = false;

    // GENERAL PROGRAM INFO
    public static String applicationName = "Tweet Music Player";
    public static String packageName = "<unknown>";
    public static String versionName = "<unknown>";
    public static int    versionCode = -1;
    public static long   firstInstalledTime = -1;
    public static long   lastUpdatedTime    = -1;

    /**
     * Creates everything.
     *
     * Must be called only once at the beginning
     * of the program.
     */
    public static void initialize(Context c) {

        IpMain.packageName = c.getPackageName();

        try {
            // Retrieving several information
            PackageInfo info = c.getPackageManager().getPackageInfo(IpMain.packageName, 0);

            IpMain.versionName        = info.versionName;
            IpMain.versionCode        = info.versionCode;
            IpMain.firstInstalledTime = info.firstInstallTime;
            IpMain.lastUpdatedTime    = info.lastUpdateTime;

        } catch (PackageManager.NameNotFoundException e) {
            // Couldn't get package information
            //
            // Won't do anything, since variables are
            // already started with default values.
        }
    }

    /**
     * Destroys everything.
     *
     * Must be called only once when the program
     * being destroyed.
     */
    public static void destroy() {
        songs.destroy();
    }

    /**
     * The actual connection to the MusicService.
     * We start it with an Intent.
     *
     * These callbacks will bind the MusicService to our internal
     * variables.
     * We can only know it happened through our flag, `musicBound`.
     */
    public static ServiceConnection musicConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicBinder binder = (MusicBinder)service;

            // Here's where we finally create the MusicService
            musicService = binder.getService();
            musicService.setList(IpMain.songs.songs);
            musicService.musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicService.musicBound = false;
        }
    };

    /**
     * Our will to start a new music Service.
     * Android requires that we start a service through an Intent.
     */
    private static Intent musicServiceIntent = null;

    /**
     * Initializes the Music Service at Activity/Context c.
     */
    public static void startMusicService(Context c) {

        if (musicServiceIntent != null)
            return;

        if (IpMain.musicService != null)
            return;

        // Create an intent to bind our Music Connection to
        // the MusicService.
        musicServiceIntent = new Intent(c, ServicePlayMusic.class);
        c.bindService(musicServiceIntent, musicConnection, Context.BIND_AUTO_CREATE);
        c.startService(musicServiceIntent);
    }

    /**
     * Makes the music Service stop and clean itself at
     * Activity/Context c.
     */
    public static void stopMusicService(Context c) {

        if (musicServiceIntent == null)
            return;

        c.stopService(musicServiceIntent);
        musicServiceIntent = null;

        IpMain.musicService = null;
    }


}