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
import com.leme.cookbook.model.Ingredient;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class IngredientItemAdapter extends RecyclerView.Adapter<IngredientItemAdapter.IngredientItemAdapterViewHolder> {

    private Context mContext;
    private List<Ingredient> mIngredientList;

    public IngredientItemAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public IngredientItemAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View inflate = LayoutInflater.from(mContext).inflate(R.layout.detail_item_ingredient, viewGroup, false);

        return new IngredientItemAdapterViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientItemAdapterViewHolder viewHolder, int position) {
        Ingredient ingredient = mIngredientList.get(position);

        viewHolder.mDetailItemIngredient.setText(ingredient.getIngredient());
        viewHolder.mDetailItemMeasure.setText(ingredient.getMeasure());
        viewHolder.mDetailItemQuantity.setText(String.valueOf(ingredient.getQuantity()));
    }

    @Override
    public int getItemCount() {
        if(mIngredientList == null) return 0;
        return mIngredientList.size();
    }

    public void setIngredientData(List<Ingredient> ingredientList) {
        mIngredientList = ingredientList;
        notifyDataSetChanged();
    }

    public class IngredientItemAdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.detail_item_ingredient)
        TextView mDetailItemIngredient;

        @BindView(R.id.detail_item_measure)
        TextView mDetailItemMeasure;

        @BindView(R.id.detail_item_quantity)
        TextView mDetailItemQuantity;

        public IngredientItemAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

    }

}
