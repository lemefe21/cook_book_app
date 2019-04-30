package com.leme.cookbook.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.leme.cookbook.R;
import com.leme.cookbook.model.Baking;
import com.leme.cookbook.util.ReadJsonUtil;

import java.util.List;

public class BakingListWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new BakingListRemoteViewFactory(this.getApplicationContext());
    }

    class BakingListRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

        Context mContext;
        List<Baking> bakings;

        public BakingListRemoteViewFactory(Context applicationContext) {
            mContext = applicationContext;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            bakings = ReadJsonUtil.loadJSONFromObject(mContext);
        }

        @Override
        public void onDestroy() {
            bakings = null;
        }

        @Override
        public int getCount() {
            return bakings.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {

            Baking baking = bakings.get(position);
            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.baking_widget);
            views.setTextViewText(R.id.widget_baking_name, baking.getName());

            Bundle extras = new Bundle();
            extras.putLong("baking_selected", baking.getId());
            Intent fillInIntent = new Intent();
            fillInIntent.putExtras(extras);
            views.setOnClickFillInIntent(R.id.widget_baking_list, fillInIntent);

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }

}
