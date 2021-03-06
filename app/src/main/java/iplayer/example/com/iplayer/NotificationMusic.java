package iplayer.example.com.iplayer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import iplayer.example.com.iplayer.Activities.ActivityNowPlaying;
import iplayer.example.com.iplayer.Model.Song;

/**
 * Created by EngineerKunle on 24/01/15.
 */

public class NotificationMusic extends NotificationSimple {

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

    /**
     * Sends a system notification with a song's information.
     *
     * If the user clicks the notification, will be redirected
     * to the "Now Playing" Activity.
     *
     * If the user clicks on any of the buttons inside it,
     * custom actions will be executed on the
     * `NotificationButtonHandler` class.
     *
     * @param context Activity that calls this function.
     * @param service Service that calls this function.
     *                Required so the Notification can
     *                run on the background.
     * @param song    Song which we'll display information.
     *
     * @note By calling this function multiple times, it'll
     *       update the old notification.
     */

    public void notifySong(Context context, Service service, Song song) {

        if (this.context == null)
            this.context = context;
        if (this.service == null)
            this.service = service;



        // Intent that launches the "Now Playing" Activity
        Intent notifyIntent = new Intent(context, ActivityNowPlaying.class);
        notifyIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Letting the Intent be executed later by other application.
        PendingIntent pendingIntent = PendingIntent.getActivity
                (context,
                        0,
                        notifyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);



        // Setting our custom appearance for the notification
        notificationView = new RemoteViews(IpMain.packageName, R.layout.notification);

        // Manually settings the buttons and text
        // (ignoring the defaults on the XML)
        notificationView.setImageViewResource(R.id.notification_button_play, R.drawable.pause);
        notificationView.setImageViewResource(R.id.notification_button_skip, R.drawable.skip);
        notificationView.setTextViewText(R.id.notification_text_title, song.getTitle());
        notificationView.setTextViewText(R.id.notification_text_artist, song.getArtist());



        // On the notification we have two buttons - Play and Skip
        // Here we make sure the class `NotificationButtonHandler`
        // gets called when user selects one of those.
        //
        // First, building the play button and attaching it.
        Intent buttonPlayIntent = new Intent(context, NotificationPlayButtonHandler.class);
        buttonPlayIntent.putExtra("action", "togglePause");

        PendingIntent buttonPlayPendingIntent = PendingIntent.getBroadcast(context, 0, buttonPlayIntent, 0);
        notificationView.setOnClickPendingIntent(R.id.notification_button_play, buttonPlayPendingIntent);

        // And now, building and attaching the Skip button.
        Intent buttonSkipIntent = new Intent(context, NotificationSkipButtonHandler.class);
        buttonSkipIntent.putExtra("action", "skip");

        PendingIntent buttonSkipPendingIntent = PendingIntent.getBroadcast(context, 0, buttonSkipIntent, 0);
        notificationView.setOnClickPendingIntent(R.id.notification_button_skip, buttonSkipPendingIntent);



        // Finally... Actually creating the Notification
        notificationBuilder = new Notification.Builder(context);

        notificationBuilder.setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher_white)
                .setTicker("'BetaAudio'" + song.getTitle() + "' from '" + song.getArtist() + "'")
                .setOngoing(true)
                .setContentTitle(song.getTitle())
                .setContentText(song.getArtist())
                .setContent(notificationView);

        Notification notification = notificationBuilder.build();



        notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
//		notificationManager.notify(NOTIFICATION_ID, notification);

        // Sets the notification to run on the foreground.
        // (why not the former commented line?)
        service.startForeground(NOTIFICATION_ID, notification);
    }

    /**
     * Called when user clicks the "play/pause" button on the on-going system Notification.
     */
    public static class NotificationPlayButtonHandler extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            IpMain.musicService.togglePlayback();
        }
    }

        /**
         * Called when user clicks the "skip" button on the on-going system Notification.
         */
        public static class NotificationSkipButtonHandler extends BroadcastReceiver {
            @Override
            public void onReceive(Context context, Intent intent) {
                IpMain.musicService.next(true);
                IpMain.musicService.playSong();
            }
        }

        /**
         * Updates the Notification icon if the music is paused.
         */
        public void notifyPaused(boolean isPaused) {
            if ((notificationView == null) || (notificationBuilder == null))
                return;

            int iconID = ((isPaused) ?
                    R.drawable.play :
                    R.drawable.pause);

            notificationView.setImageViewResource(R.id.notification_button_play, iconID);

            notificationBuilder.setContent(notificationView);

//		notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());

            // Sets the notification to run on the foreground.
            // (why not the former commented line?)
            service.startForeground(NOTIFICATION_ID, notificationBuilder.build());
        }

        /**
         * Cancels this notification.
         */
        public void cancel() {
            service.stopForeground(true);

            notificationManager.cancel(NOTIFICATION_ID);
        }

        /**
         * Cancels all sent notifications.
         */
        public static void cancelAll(Context c) {
            NotificationManager manager = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.cancelAll();
        }

}
