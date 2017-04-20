package com.example.sarvex.iscream.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sarvex.iscream.R;
import com.example.sarvex.iscream.utility.Utility;
import com.mikepenz.iconics.context.IconicsContextWrapper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import icepick.Icepick;

public class ForgotActivity extends AppCompatActivity {

  @BindView(R.id.email) EditText email;
  @BindView(R.id.forgot) FloatingActionButton forgot;

  private ProgressDialog progress;

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
    setContentView(R.layout.activity_forgot);
    ButterKnife.bind(this);
  }

  @OnClick(R.id.forgot)
  public void forgotClicked(View view) {
    Utility.showProgressBar(this, progress, "Checking Email...");
  }

  @OnEditorAction(R.id.email)
  boolean email(int actionId) {
    if (actionId == EditorInfo.IME_ACTION_DONE) {
      forgot.performClick();
      return true;
    }
    return false;
  }
}
