package me.dong.gdg_testsample.viewmodel;

import android.app.Activity;

import com.android.volley.toolbox.ImageLoader;

import me.dong.gdg_testsample.model.Product;
import me.dong.gdg_testsample.ui.ProductItemView;

/**
 * Created by Dong on 2016-02-13.
 */
//ViewModel: model과 View를 가진다.
//프리젠테이션 로직: 화면상의 디자인 구성을 위한 로직
//양방향 바인딩: ???
    //어댑터뷰애선 어떻게 mvvm을 적용애햐 하는가...-> 고민..
public class ProductViewModel {

    private Activity activity;
    private Product product;
    private ProductItemView productItemView;
    ImageLoader imageLoader;

    public ProductViewModel(Activity activity, Product product, ImageLoader imageLoader){
        this.activity = activity;
        this.product = product;
        this.imageLoader = imageLoader;
        initView(activity);
    }

    private void initView(Activity activity){
        productItemView = new ProductItemView(activity);
        productItemView.bind(product, imageLoader);
    }

}
