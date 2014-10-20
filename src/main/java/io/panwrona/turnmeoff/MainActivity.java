package io.panwrona.turnmeoff;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.RemoteViews;

public class MainActivity extends AppWidgetProvider{


    public static final int SHUTDOWN = 1;
    public static final int RESTART = 2;
    public static final int HIBERNATE = 3;
    public static final int ERROR = 4;
    public static final int SENDING = 5;

    private static final String ACTION_SHUTDOWN = "io.panwrona.turnmeoff.ACTION_SHUTDOWN";
    private static final String ACTION_RESTART = "io.panwrona.turnmeoff.ACTION_RESTART";
    private static final String ACTION_HIBERNATE = "io.panwrona.turnmeoff.ACTION_HIBERNATE";


    RemoteViews views;
    ComponentName widget;
    AppWidgetManager awManager;
    Handler mHandler;
    String ip=null;

@Override
public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                     int[] appWidgetIds) {
    super.onUpdate(context, appWidgetManager, appWidgetIds);

    for(int i=0; i<appWidgetIds.length; i++){

        int currentId = appWidgetIds[i];
        views = new RemoteViews(context.getPackageName(), R.layout.activity_main);

        Intent shutdownIntent = new Intent(context, MainActivity.class);
        shutdownIntent.setAction(ACTION_SHUTDOWN);
        PendingIntent pendingShutdown = PendingIntent.getBroadcast(context, 0, shutdownIntent, 0);

        Intent restartIntent = new Intent(context, MainActivity.class);
        restartIntent.setAction(ACTION_RESTART);
        PendingIntent pendingRestart = PendingIntent.getBroadcast(context, 1 , restartIntent, 0);

        Intent hibernateIntent = new Intent(context, MainActivity.class);
        hibernateIntent.setAction(ACTION_HIBERNATE);
        PendingIntent pendingHibernate = PendingIntent.getBroadcast(context, 2, hibernateIntent, 0);

        views.setOnClickPendingIntent(R.id.shutdownButton, pendingShutdown);
        views.setOnClickPendingIntent(R.id.restartButton, pendingRestart);
        views.setOnClickPendingIntent(R.id.hibernateButton, pendingHibernate);
        appWidgetManager.updateAppWidget(currentId, views);

    }
}
    @Override
    public void onReceive(Context context, Intent intent){
        super.onReceive(context,intent);

        views = new RemoteViews(context.getPackageName(), R.layout.activity_main);
        widget = new ComponentName(context, MainActivity.class);
        awManager = AppWidgetManager.getInstance(context);

        if(intent.getAction().equals(ACTION_SHUTDOWN)){
            new ShutdownAsyncTask(getmHandler(context)).execute("");
        }else if(intent.getAction().equals(ACTION_RESTART)){
           new RestartAsyncTask(getmHandler(context)).execute("");
        }else if(intent.getAction().equals(ACTION_HIBERNATE)){
            new HibernateAsyncTask(getmHandler(context)).execute("");
        }
    }

    private Handler getmHandler(final Context context){
       final String mTag = "Handler";
        mHandler = new Handler(){
            public void handleMessage(Message msg) {
                switch(msg.what){
                    case SHUTDOWN:
                        Log.d(mTag, "In Handler's shutdown");
                        views = new RemoteViews(context.getPackageName(), R.layout.activity_main);
                        widget = new ComponentName(context, MainActivity.class);
                        awManager = AppWidgetManager.getInstance(context);
                        views.setTextViewText(R.id.state, "Shutting PC...");
                        awManager.updateAppWidget(widget,views);
                        break;
                    case RESTART:
                        Log.d(mTag, "In Handler's restart");
                        views = new RemoteViews(context.getPackageName(), R.layout.activity_main);
                        widget = new ComponentName(context, MainActivity.class);
                        awManager = AppWidgetManager.getInstance(context);
                        views.setTextViewText(R.id.state, "Restarting PC...");
                        awManager.updateAppWidget(widget,views);
                        break;
                    case HIBERNATE:
                        Log.d(mTag, "In Handler's hibernate");
                        views = new RemoteViews(context.getPackageName(), R.layout.activity_main);
                        widget = new ComponentName(context, MainActivity.class);
                        awManager = AppWidgetManager.getInstance(context);
                        views.setTextViewText(R.id.state, "Hibernating PC...");
                        awManager.updateAppWidget(widget,views);
                        break;
                    case ERROR:
                        Log.d(mTag, "In Handler's error");
                        views = new RemoteViews(context.getPackageName(), R.layout.activity_main);
                        widget = new ComponentName(context, MainActivity.class);
                        awManager = AppWidgetManager.getInstance(context);
                        views.setTextViewText(R.id.state, "Something went wrong...");
                        awManager.updateAppWidget(widget,views);
                        break;
                    case SENDING:
                        Log.d(mTag, "In Handler's sending");
                        views = new RemoteViews(context.getPackageName(), R.layout.activity_main);
                        widget = new ComponentName(context, MainActivity.class);
                        awManager = AppWidgetManager.getInstance(context);
                        views.setTextViewText(R.id.state, "Sending message...");
                        awManager.updateAppWidget(widget,views);
                        break;
                }
            }

        };
        return mHandler;
    }

}