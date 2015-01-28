package iplayer.example.com.iplayer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.widget.RemoteViews;

/**
 * Created by EngineerKunle on 24/01/15.
 */

public class NotificationMusic extends NotificationSimple{

    /**
     * this is the remote control to where we can access resources
     */
    Context context = null;

    Service service = null;

    /**
     * gives access to all that is needed to build the Notification
     */

    Notification.Builder notificationBuilder = null;

    /**
     * Custom appearance of the notification, also updated.
     * RemoteViews (views that are managed by another process than your application)
     */
    RemoteViews notificationView = null;


    NotificationManager notificationManager = null;










}
