package me.dong.gdg_testsample.ui;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import me.dong.gdg_testsample.DetailPageActivity;
import me.dong.gdg_testsample.R;
import me.dong.gdg_testsample.model.Product;

/**
 * Created by Dong on 2016-01-30.
 */
public class ProductItemView extends FrameLayout {

    Context mContext;
    NetworkImageView ivProductImg;
    TextView tvProductTitle;
    View mView;

    public ProductItemView(Context context) {
        super(context);
        mContext = context;
        init(context);
    }

    private void init(Context context) {
        mView = inflate(context, R.layout.item_product, this);
        ivProductImg = (NetworkImageView) mView.findViewById(R.id.imageView_product_image);
        tvProductTitle = (TextView) mView.findViewById(R.id.textView_product_title);
    }

    public void bind(final Product product, ImageLoader imageLoader) {
        tvProductTitle.setText(product.getName());
        ivProductImg.setImageUrl(product.getImageUrl(), imageLoader);
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailPageActivity.class);
                String detailProduct = product.getDetailPageUrl();
                intent.putExtra("detailProductUrl", detailProduct);
                mContext.startActivity(intent);
            }
        });
    }
}
