package iplayer.example.com.iplayer.Activities;

import android.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.MediaController.MediaPlayerControl;
import android.widget.PopupMenu;

import iplayer.example.com.iplayer.AdapterSong;
import iplayer.example.com.iplayer.MusicController;
import iplayer.example.com.iplayer.R;

public class ActivityNowPlaying extends ActivityMaster implements MediaPlayerControl,
        OnItemClickListener, OnItemLongClickListener{

    private static final String TAG = ActivityNowPlaying.class.getSimpleName();

    /**
     *List that will display the songs
     */

    private ListView songListView;

    private boolean paused = false;
    private boolean playbackpaused = false;

    private MusicController musicController;

    /** Maps out the songs item*/
    private AdapterSong songApdater;

    /**
     * Little menu that will show when the user
     * clicks the ActionBar.
     * It serves to sort the current song list.
     */
    private PopupMenu popup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_now_playing);

        Log.d(TAG, "Activity Now Playing is live");
    }

    public void createActionBar(){

        ActionBar actionBar = getActionBar();

        if(actionBar == null)
            return;

        Window window = getWindow();
        View view = window.getDecorView();
        int resID = getResources().getIdentifier("action_bar_container", "id", "android");

        popup = new PopupMenu(this, view.findViewById(resID));
        MenuInflater menuInflater = popup.getMenuInflater();

        menuInflater.inflate(R.menu.activity_now_playing_action_bar_submenu, popup.getMenu());

        PopupMenu.OnMenuItemClickListener listener = new PopupMenu.OnMenuItemClickListener(){

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                boolean updateList = false;
                switch (item.getItemId()){

                }
                return false;
            }
        };


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_now_playing, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void start() {

    }

    @Override
    public void pause() {

    }

    @Override
    public int getDuration() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        return 0;
    }

    @Override
    public void seekTo(int pos) {

    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return false;
    }

    @Override
    public boolean canSeekBackward() {
        return false;
    }

    @Override
    public boolean canSeekForward() {
        return false;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }
}
