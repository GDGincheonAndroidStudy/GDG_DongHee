package com.ipocs.hashtagculture.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Dong on 2015-12-14.
 */
public class SelectTextView extends TextView {

    Boolean select;

    public SelectTextView(Context context) {
        super(context);
        select = false;
    }

    public SelectTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        select = false;
    }

    public SelectTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        select = false;
    }

    public Boolean getSelect() {
        return select;
    }

    public void setSelect(Boolean select) {
        this.select = select;
    }
}
