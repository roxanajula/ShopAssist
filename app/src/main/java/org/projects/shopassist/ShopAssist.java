package org.projects.shopassist;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by roxanajula on 5/8/16.
 */

public class ShopAssist extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);

    }
}