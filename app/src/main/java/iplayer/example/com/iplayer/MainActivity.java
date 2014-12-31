package iplayer.example.com.iplayer;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class MainActivity extends ListActivity{

    public static int STATE_SELECT_ALBUM = 0;
    public static int STATE_SELECT_SONG = 1;
    int currentState = STATE_SELECT_ALBUM;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] columns = {
                android.provider.MediaStore.Audio.Albums._ID,
                android.provider.MediaStore.Audio.Albums.ALBUM
        };

        Cursor cursor = getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, columns,
                null, null, null);

        String[] displayFields = new String[] {MediaStore.Audio.Albums.ALBUM};
        int[] displayViews = new int[] {android.R.id.text1};
        setListAdapter(new SimpleCursorAdapter(this,android.R.layout.simple_list_item_1, cursor, displayFields,displayViews,0));

        }


    public void onItemClick(AdapterView<?> listView, View view, int position, long id){

        if(currentState == STATE_SELECT_ALBUM){


            if (cursor.moveToPosition(position)) {
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
                setListAdapter(new SimpleCursorAdapter(this,
                        android.R.layout.simple_list_item_1, cursor,
                        displayFields, displayViews,0));

            } else if (currentState == STATE_SELECT_SONG) {

                if (cursor.moveToPosition(position)) {

                    int fileColumn = cursor
                            .getColumnIndex(MediaStore.Audio.Media.DATA);

                    int mimeTypeColumn = cursor
                            .getColumnIndex(MediaStore.Audio.Media.MIME_TYPE);

                    String audioFilePath = cursor.getString(fileColumn);

                    String mimeType = cursor.getString(mimeTypeColumn);

                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW);

                    File newFile = new File(audioFilePath);

                    intent.setDataAndType(Uri.fromFile(newFile), mimeType);

                    startActivity(intent);
                }
            }
        }
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
