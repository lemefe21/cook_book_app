package com.leme.cookbook.service;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.leme.cookbook.provider.BakingWidgetProvider;

public class BakingWidgetService extends IntentService {

    public static final String ACTION_BAKING_SERVICE = "com.leme.cookbook.action";

    public BakingWidgetService() {
        super("BakingWidgetService");
    }

    public static void startActionBakingIngredientList(Context context) {
        Intent intent = new Intent(context, BakingWidgetService.class);
        intent.setAction(ACTION_BAKING_SERVICE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent != null) {
            final String action = intent.getAction();
            if(ACTION_BAKING_SERVICE.equals(action)) {
                long baking_selected = intent.getLongExtra("baking_selected", 0);
                handleIntentBakingAction(baking_selected);
            }
        }
    }

    private void handleIntentBakingAction(long id) {
        Log.i("BakingAppLog", "handleIntentBakingAction: " + id);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingWidgetProvider.class));

        //Now update all widgets
        BakingWidgetProvider.updateBakingWidgets(this, appWidgetManager, appWidgetIds);
    }

}
