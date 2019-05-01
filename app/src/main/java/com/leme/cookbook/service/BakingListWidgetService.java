package com.leme.cookbook.service;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.leme.cookbook.R;
import com.leme.cookbook.model.Baking;
import com.leme.cookbook.provider.BakingWidgetProvider;
import com.leme.cookbook.util.ReadJsonUtil;

import java.util.List;

public class BakingListWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new BakingListRemoteViewFactory(this.getApplicationContext(), intent);
    }

    class BakingListRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

        private Context mContext;
        private List<Baking> bakings;

        public BakingListRemoteViewFactory(Context applicationContext, Intent intent) {
            mContext = applicationContext;
        }

        @Override
        public void onCreate() {
            bakings = ReadJsonUtil.loadJSONFromObject(mContext);
        }

        @Override
        public void onDataSetChanged() {

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

            Intent intentFilter = new Intent();
            intentFilter.putExtra(BakingWidgetProvider.FILTER_BAKING_ITEM, baking);
            views.setOnClickFillInIntent(R.id.widget_item_linear_container, intentFilter);

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
