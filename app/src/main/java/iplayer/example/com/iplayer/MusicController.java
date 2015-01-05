package iplayer.example.com.iplayer;

import android.content.Context;
import android.widget.MediaController;


/**
 * Created by Kunle on 05/01/2015.
 */
public class MusicController extends MediaController {

    public MusicController(Context c) {
        super(c);
    }


    /**
     * We're overriding the parent's `hide` method, so we
     * can prevent the controls from hiding after 3 seconds.
     */
	/*public void hide() { }*/
}
