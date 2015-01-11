package iplayer.example.com.iplayer.Helpers;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Kunle on 11/01/2015.
 */

/**
 * this class is used to help toast appear at once, rather than all repeating each other.
 */
public class SingleToast {

    private static Toast singleToast = null;

    /**
     * Immediately shows a text message.
     * Use this the same way you would call `Toast`.
     *
     * @note It calls "show()" immediately.
     */
    public static void show(Context c, String text, int duration) {

        if (singleToast != null)
            singleToast.cancel(); // override current Toast, mate!

        singleToast = Toast.makeText(c, text, duration);
        singleToast.show();
    }


}
