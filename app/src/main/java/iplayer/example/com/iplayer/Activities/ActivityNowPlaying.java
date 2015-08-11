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
import android.widget.TextView;

import iplayer.example.com.iplayer.AdapterSong;
import iplayer.example.com.iplayer.IpMain;
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

    private MenuItem shuffleItem;

    private MenuItem repeatItem;

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

                    //continue from here
                    case R.id.action_bar_submenu_title:
                        IpMain.musicService.sortBy("title");
                        updateList = true;
                        break;

                    case R.id.action_bar_submenu_artist:
                        IpMain.musicService.sortBy("artist");
                        updateList = true;
                        break;

                    case R.id.action_bar_submenu_album:
                        IpMain.musicService.sortBy("album");
                        updateList = true;
                        break;

                    case R.id.action_bar_submenu_track:
                        IpMain.musicService.sortBy("track");
                        updateList = true;
                        break;

                    case R.id.action_bar_submenu_random:
                        IpMain.musicService.sortBy("random");
                        updateList = true;
                        break;
                 }

                if (updateList) {
                    songApdater.notifyDataSetChanged();
                    songListView.setSelection(IpMain.musicService.currentSongPosition);
                }
                return false;
            }
        };

        popup.setOnMenuItemClickListener(listener);

        actionBar.setHomeButtonEnabled(false);

        actionBar.setCustomView(R.layout.activity_now_playing_action_bar);

        TextView textTop = (TextView) actionBar.getCustomView().findViewById(R.id.action_bar_title1);

        textTop.setText(getString(R.string.Now_playing)); //TODO change this to Artist Name

        TextView textBottom = (TextView) actionBar.getCustomView().findViewById(R.id.action_bar_subtitle);

        textBottom.setText("");

        //actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        // And when we click on the custom layout
        // (our button with "Title" and "Subtitle")...
        actionBar
                .getCustomView()
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        popup.show();
                    }
                });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_items, menu);

        shuffleItem = menu.findItem(R.id.action_bar_shuffle);
        repeatItem = menu.findItem(R.id.action_bar_repeat);

        refreshActionBarItems();
        refreshActionBarSubtitle();

        return super.onCreateOptionsMenu(menu);
    }

    /**this method changes icon for shuffle and repeat*/

    private void refreshActionBarItems() {

        shuffleItem
                .setIcon((IpMain.musicService.isShuffle()) ? R.drawable.ic_menu_shuffle_on
                        : R.drawable.ic_menu_shuffle_off);

        repeatItem
                .setIcon((IpMain.musicService.isRepeat()) ? R.drawable.ic_menu_repeat_on
                        : R.drawable.ic_menu_repeat_off);
    }

    private void refreshActionBarSubtitle(){
        ActionBar actionBar = getActionBar();
        if(actionBar==null)
            return;

        if(IpMain.musicService.currentSong == null)
            return;

        TextView textBottom = (TextView) actionBar.getCustomView().findViewById(R.id.action_bar_subtitle);
        textBottom.setText(IpMain.musicService.currentSong.getTitle());

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_bar_shuffle:
                IpMain.musicService.toggleShuffle();
                refreshActionBarSubtitle();
                return true;

            case R.id.action_bar_repeat:
                IpMain.musicService.toggleRepeat();
                refreshActionBarSubtitle();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void start() {

    }

    @Override
    public void pause(){
        super.onPause();

        paused = true;
        playbackpaused = true;

    }

    @Override
    protected void onResume() {
        super.onResume();

        refreshActionBarSubtitle();

        if (paused) {
            // Ensure that the controller
            // is shown when the user returns to the app
            //setMusicController();
            paused = false;
        }

        // Scroll the list view to the current song.
        if (IpMain.settings.get("scroll_on_focus", true))
            songListView.setSelection(IpMain.musicService.currentSongPosition);
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
