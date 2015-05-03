package iplayer.example.com.iplayer.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import iplayer.example.com.iplayer.IpMain;
import iplayer.example.com.iplayer.R;

/**
 * Created by EngineerKunle on 19/04/15.
 */

@SuppressLint("Registered") // No need to register this class on AndroidManifest
public class ActivityMaster extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        // Every time the user focuses this Activity,
        // we need to check it.
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_context, menu);

        if(IpMain.mainMenuHasNowPlayingItem){
            menu.findItem(R.id.context_menu_now_playing).setVisible(true);

        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){



        }
        return super.onOptionsItemSelected(item);
    }
}
