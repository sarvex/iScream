package com.example.sarvex.iscream.utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by sarvex on 18/04/2017.
 */

public class Utility {
  public static void hideProgressBar(ProgressDialog progress) {
    if (progress != null && progress.isShowing()) {
      progress.dismiss();
    }
  }

  public static void showProgressBar(Activity activity, ProgressDialog progress, String message) {
    progress = new ProgressDialog(activity);
    progress.setMessage(message);
    progress.setIndeterminate(true);
    progress.setProgress(0);
    progress.show();
  }

  public static void hideKeyboard(Activity activity) {
    InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context
        .INPUT_METHOD_SERVICE);
    inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
  }
}
