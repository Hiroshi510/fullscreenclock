package com.hiroshi510.fullscreenclock;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

public class ClockWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        SharedPreferences prefs = context.getSharedPreferences("SaveData", Context.MODE_PRIVATE);
        String str = prefs.getString("string","M/d");
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.clock_widget);
        views.setCharSequence(R.id.date,"setFormat12Hour", str);
        views.setCharSequence(R.id.date,"setFormat24Hour", str);

        Intent clIntent = new Intent(context, MainActivity.class);
        PendingIntent clPending = PendingIntent.getActivity(context, 777, clIntent, 0);
        views.setOnClickPendingIntent(R.id.hour, clPending);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled (Context context){
    }

    @Override
    public void onDeleted (Context context, int[] appWidgetIds) {

        SharedPreferences prefs = context.getSharedPreferences("SaveData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("string");
        editor.apply();
    }

    @Override
    public void onDisabled (Context context){
    }
}