package iplayer.example.com.iplayer;

import android.app.ActionBar;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

//Should be seen as master class...
public class MainActivity extends Activity {

    public static ArrayList<String> items;

    ListView listView;

    public static final int STATE_SELECT_ALBUM = 0;
    public static final int STATE_SELECT_SONG = 1;
    public static final int currentState = STATE_SELECT_ALBUM;
    //private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_albums);

        // This enables the "Up" button on the top Action Bar
        // Note that it returns to the parent Activity, specified
        // on `AndroidManifest`
        ActionBar actionBar = getActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        // List to be populated with items
        listView = (ListView)findViewById(R.id.activity_menu_albums_list);

        items = IpMain.songs.getAlbums();

        // Adapter that will convert from Strings to List Items
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, items);

        // Filling teh list with all the items
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        //Getting song index
        int songIndex = position;
        // Starting new intent
        Intent in = new Intent(getApplicationContext(),Play.class);

        // Sending songIndex to Play Activity
        in.putExtra("SongPosition", songIndex);
        setResult(100, in);
        startActivityForResult(in, 100);
                // Closing PlayListView
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
