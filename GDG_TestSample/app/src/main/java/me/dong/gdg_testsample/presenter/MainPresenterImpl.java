package me.dong.gdg_testsample.presenter;

import android.app.Activity;

import me.dong.gdg_testsample.model.MainModel;

/**
 * Created by Dong on 2016-02-17.
 */
public class MainPresenterImpl implements MainPresenter {

    private Activity activity;
    private MainModel mainModel;
    private MainPresenter.View view;

    public MainPresenterImpl(Activity activity){
        this.activity = activity;
        this.mainModel = new MainModel();
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void onConfirm() {
        if(view != null){
            view.setButtonText(mainModel.getStrSearchKeyword());
        }
    }
}
