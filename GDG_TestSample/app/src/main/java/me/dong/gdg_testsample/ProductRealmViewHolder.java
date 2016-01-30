package me.dong.gdg_testsample;

import android.view.View;

import co.moonmonkeylabs.realmsearchview.RealmSearchViewHolder;
import io.realm.RealmViewHolder;
import me.dong.gdg_testsample.ui.ProductItemView;

/**
 * Created by Dong on 2016-01-30.
 */
public class ProductRealmViewHolder extends RealmSearchViewHolder {

    public ProductItemView productItemView;

    public ProductRealmViewHolder(ProductItemView itemView) {
        super(itemView);
        this.productItemView = itemView;
    }
}
