package iplayer.example.com.iplayer.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        // Every time the user focuses this Activity,
        // we need to check it.
        super.onResume();
    }
}
