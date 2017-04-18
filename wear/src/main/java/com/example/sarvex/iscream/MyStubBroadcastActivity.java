package com.example.sarvex.iscream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mikepenz.iconics.context.IconicsContextWrapper;

import butterknife.ButterKnife;
import icepick.Icepick;

/**
 * Example shell activity which simply broadcasts to our receiver and exits.
 */
public class MyStubBroadcastActivity extends Activity {
  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    Icepick.saveInstanceState(this, outState);
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Icepick.restoreInstanceState(this, savedInstanceState);
    ButterKnife.bind(this);

    Intent i = new Intent();
    i.setAction("com.example.sarvex.iscream.SHOW_NOTIFICATION");
    i.putExtra(MyPostNotificationReceiver.CONTENT_KEY, getString(R.string.title));
    sendBroadcast(i);
    finish();
  }
}
