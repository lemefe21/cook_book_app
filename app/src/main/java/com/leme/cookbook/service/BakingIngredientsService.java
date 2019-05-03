package com.leme.cookbook.service;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import com.leme.cookbook.R;
import com.leme.cookbook.model.Ingredient;
import com.leme.cookbook.provider.BakingWidgetProvider;

import java.util.ArrayList;
import java.util.List;

public class BakingIngredientsService extends IntentService {

    public static final String ACTION_UPDATE_INGREDIENTS_WIDGET = "com.leme.cookbook.service.BakingIngredientsService.INGREDIENTS";
    public static final String ACTION_UPDATE_WIDGET = "com.leme.cookbook.service.BakingIngredientsService.UPDATE_WIDGET";
    public static final String ACTION_INTENT_KEY = "ingredient_key";

    private List<Ingredient> mIngredients;

    public BakingIngredientsService() {
        super("BakingIngredientsService");
    }

    public static void startActionUpdateBakingWidgets(Context context, List<Ingredient> ingredients) {
        Intent intent = new Intent(context, BakingIngredientsService.class);
        intent.setAction(ACTION_UPDATE_INGREDIENTS_WIDGET);
        intent.putParcelableArrayListExtra(ACTION_INTENT_KEY, new ArrayList<Parcelable>(ingredients));
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_INGREDIENTS_WIDGET.equals(action)) {
                mIngredients = intent.getParcelableArrayListExtra(ACTION_INTENT_KEY);
                handleActionUpdateIngredient(mIngredients);
            } else if(ACTION_UPDATE_WIDGET.equals(action) ) {
                handleActionUpdateWidget();
            }
        }
    }

    private void handleActionUpdateWidget() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingWidgetProvider.class));

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_baking_name);


        BakingWidgetProvider.updateBakingIngredientWidgets(this, appWidgetManager, mIngredients, appWidgetIds );
    }

    private void handleActionUpdateIngredient(List<Ingredient> ingredients) {
        mIngredients = ingredients;
        startActionUpdateWidget(this);
    }

    public static void startActionUpdateWidget(Context context) {
        Intent intent = new Intent(context, BakingIngredientsService.class);
        intent.setAction(ACTION_UPDATE_WIDGET);
        context.startService(intent);
    }
}
