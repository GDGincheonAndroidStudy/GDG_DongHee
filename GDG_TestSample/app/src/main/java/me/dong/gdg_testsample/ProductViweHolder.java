package me.dong.gdg_testsample;

import android.support.v7.widget.RecyclerView;

import me.dong.gdg_testsample.ui.ProductItemView;

/**
 * Created by Dong on 2016-01-30.
 */
public class ProductViweHolder extends RecyclerView.ViewHolder {

    public ProductItemView productItemView;

    public ProductViweHolder(ProductItemView itemView) {
        super(itemView);
        this.productItemView = itemView;
    }
}

