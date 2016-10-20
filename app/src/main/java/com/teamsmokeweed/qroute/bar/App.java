package com.teamsmokeweed.qroute.bar;

import android.app.Application;

import com.squareup.otto.Bus;

/**
 * Created by jongzazaal on 20/10/2559.
 */

public class App extends Application
{
    private static Bus bus;

    @Override
    public void onCreate()
    {
        super.onCreate();

        bus = new Bus(); // Instantiate a new Bus
    }

    public static Bus getBus()
    {
        if (bus == null){
            bus = new Bus();
        }
        return bus;
    }
}
