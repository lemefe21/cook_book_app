package com.leme.cookbook.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leme.cookbook.R;
import com.leme.cookbook.model.Baking;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BakingItemAdapter extends RecyclerView.Adapter<BakingItemAdapter.BakingItemAdapterViewHolder> {

    private final BakingItemAdapterOnClickHandler mClickHandle;
    private Context mContext;
    private List<Baking> mBakingList;

    public interface BakingItemAdapterOnClickHandler {
        void onClick(Baking baking);
    }

    public BakingItemAdapter(Context context, BakingItemAdapterOnClickHandler mClickHandle) {
        this.mContext = context;
        this.mClickHandle = mClickHandle;
    }

    @NonNull
    @Override
    public BakingItemAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View inflate = LayoutInflater.from(mContext).inflate(R.layout.baking_list_item, viewGroup, false);

        return new BakingItemAdapterViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull BakingItemAdapterViewHolder viewHolder, int position) {

        Baking baking = mBakingList.get(position);

        viewHolder.mBakingNameTextView.setText(baking.getName());

    }

    @Override
    public int getItemCount() {
        if(mBakingList == null) return 0;
        return mBakingList.size();
    }

    public void setBakingData(List<Baking> bakingList) {
        mBakingList = bakingList;
        notifyDataSetChanged();
    }

    public class BakingItemAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.baking_name)
        TextView mBakingNameTextView;

        public BakingItemAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Baking baking = mBakingList.get(getAdapterPosition());
            mClickHandle.onClick(baking);
        }
    }

}
