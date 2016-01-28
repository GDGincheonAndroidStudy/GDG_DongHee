package com.ipocs.hashtagculture.event;

import com.squareup.otto.Bus;

/**
 * Created by Dong on 2015-12-10.
 */
public class BusProvider {
    private static Bus sBus;

    public static Bus getInstance() {
        synchronized (BusProvider.class){
            if(sBus == null){
                sBus = new Bus();
            }
            return sBus;
        }
    }

    private BusProvider() {
    }
}
