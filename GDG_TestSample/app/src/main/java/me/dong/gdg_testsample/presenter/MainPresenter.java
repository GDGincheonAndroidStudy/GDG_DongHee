package me.dong.gdg_testsample.presenter;

/**
 * Created by Dong on 2016-02-17.
 */
public interface MainPresenter {

    void setView(MainPresenter.View view);

    void onConfirm();

    public interface View {
        void setButtonText(String text);
    }
}
