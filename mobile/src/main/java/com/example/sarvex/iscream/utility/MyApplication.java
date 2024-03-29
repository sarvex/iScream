package com.example.sarvex.iscream.utility;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by sarvex on 15/4/17.
 */

public class MyApplication extends Application {

  private RefWatcher refWatcher;

  public static RefWatcher getRefWatcher(Context context) {
    MyApplication application = (MyApplication) context.getApplicationContext();
    return application.refWatcher;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    if (LeakCanary.isInAnalyzerProcess(this)) {
      return;
    }

    refWatcher = LeakCanary.install(this);
  }
}
