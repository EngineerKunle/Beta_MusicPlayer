package iplayer.example.com.iplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import java.util.ArrayList;

import iplayer.example.com.iplayer.Model.Song;

/**
 * Created by EngineerKunle on 17/05/15.
 */
public class AdapterSong  extends BaseAdapter {

    private ArrayList<Song> songs;
    private LayoutInflater songInflater;

    public AdapterSong(Context c, ArrayList<Song> theSongs){

        songs = theSongs;
        songInflater = LayoutInflater.from(c);
    }




    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Object getItem(int position) {
        return songs.get(position);
    }

    @Override
    public long getItemId(int position) {
        //TODO: thinks whats to do here
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //will map out the song to a song layout
        // Will map from a Song to a Song layout
        LinearLayout songLayout = (LinearLayout)songInflater.inflate(R.layout.menu_item_song,
                parent,
                false);


        return null;
    }

}
