package com.example.sarvex.iscream.login;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sarvex.iscream.R;
import com.mikepenz.iconics.context.IconicsContextWrapper;

import butterknife.ButterKnife;
import icepick.Icepick;

public class ForgotPasswordActivity extends AppCompatActivity {

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
    setContentView(R.layout.activity_forgot_password);
  }
}
