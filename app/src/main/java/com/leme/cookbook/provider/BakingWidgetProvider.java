package com.leme.cookbook.provider;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.leme.cookbook.BakingDetailActivity;
import com.leme.cookbook.R;
import com.leme.cookbook.model.Baking;
import com.leme.cookbook.service.BakingListWidgetService;

public class BakingWidgetProvider extends AppWidgetProvider {

    public static final String FILTER_BAKING = "com.leme.cookbook.provider.FILTER_BAKING";
    public static final String FILTER_BAKING_ITEM = "com.leme.cookbook.provider.FILTER_BAKING_ITEM";

    //as a filter
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent != null) {
            if(intent.getAction().equalsIgnoreCase(FILTER_BAKING)) {
                Baking baking = intent.getParcelableExtra(FILTER_BAKING_ITEM);

                Intent intentToDetail = new Intent(context, BakingDetailActivity.class);
                intentToDetail.putExtra(FILTER_BAKING_ITEM, baking);
                intentToDetail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intentToDetail);
            }
        }

        super.onReceive(context, intent);
    }

    //inicialization and updated
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int i = 0; i < appWidgetIds.length; i++) {

            Intent intentService = new Intent(context, BakingListWidgetService.class);
            intentService.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_list_widget);
            views.setRemoteAdapter(R.id.widget_baking_list, intentService);

            Intent intentFilterBaking = new Intent(context, BakingWidgetProvider.class);
            intentFilterBaking.setAction(FILTER_BAKING);
            PendingIntent pendingIntentFilter = PendingIntent.getBroadcast(context, 0, intentFilterBaking, 0);

            views.setPendingIntentTemplate(R.id.widget_baking_list, pendingIntentFilter);

            appWidgetManager.updateAppWidget(appWidgetIds[i], views);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

}
