package iplayer.example.com.iplayer;

/**
 * Created by EngineerKunle on 24/01/15.
 */
public class NotificationSimple {

    /**
     * Post a notification to be shown in the status bar. If a notification with thePlay.png
     * same id has already been posted by your application and has not yet been canceled,
     * it will be replaced by the updated information.
     */

    protected int NOTIFICATION_ID;

    /**
     * Counter to assure each created Notification gets
     * an unique ID at runtime.
     */
    protected static int LAST_NOTIFICATION_ID = 1;


    public NotificationSimple() {
        NOTIFICATION_ID = LAST_NOTIFICATION_ID;
        LAST_NOTIFICATION_ID++;
    }









}
