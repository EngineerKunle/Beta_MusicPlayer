package iplayer.example.com.iplayer;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SimpleCursorAdapter;


public class Play extends Activity {

    //this wil be the now playing class...

    private int currentAlbumIndex = 0;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 100) {
            currentAlbumIndex = data.getExtras().getInt("SongPosition", 0);

        }

        if (cursor.moveToPosition(currentAlbumIndex)) {

            String[] columns = {
                    MediaStore.Audio.Media.DATA,
                    MediaStore.Audio.Media._ID,
                    MediaStore.Audio.Media.TITLE,
                    MediaStore.Audio.Media.DISPLAY_NAME,
                    MediaStore.Audio.Media.MIME_TYPE,

            };

            String where = android.provider.MediaStore.Audio.Media.ALBUM + "=?";
            String whereVal[] = { cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Albums.ALBUM)) };

            String orderBy = android.provider.MediaStore.Audio.Media.TITLE;

            cursor = getContentResolver().query(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, columns,
                    where, whereVal, orderBy);

            String[] displayFields = new String[] { MediaStore.Audio.Media.DISPLAY_NAME };
            int[] displayViews = new int[] { android.R.id.text1 };
//            setListAdapter(new SimpleCursorAdapter(this,
//                android.R.layout.simple_list_item_1, cursor,
//                displayFields, displayViews,0));
    }

    }

}
