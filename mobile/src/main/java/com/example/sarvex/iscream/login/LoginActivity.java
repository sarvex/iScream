package com.example.sarvex.iscream.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sarvex.iscream.R;
import com.example.sarvex.iscream.utility.Utility;
import com.mikepenz.iconics.context.IconicsContextWrapper;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import icepick.Icepick;

public class LoginActivity extends AppCompatActivity {

  @BindView(R.id.username) EditText username;
  @BindView(R.id.password) EditText password;

  @BindView(R.id.facebook_login) FloatingActionButton facebook;
  @BindView(R.id.twitter_login) FloatingActionButton twitter;
  @BindView(R.id.google_login) FloatingActionButton google;
  @BindView(R.id.expand) FloatingActionButton expand;
  @BindView(R.id.sign_up) FloatingActionButton signUp;
  @BindView(R.id.login) FloatingActionButton login;

  private boolean isFabOpen;
  private Animation fabOpen;
  private Animation fabClose;
  private Animation rotateForward;
  private Animation rotateBackward;


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
    setContentView(R.layout.activity_login);
    ButterKnife.bind(this);

    fabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.animator.fab_open);
    fabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.animator.fab_close);
    rotateForward = AnimationUtils.loadAnimation(getApplicationContext(), R.animator.rotate_forward);
    rotateBackward = AnimationUtils.loadAnimation(getApplicationContext(), R.animator.rotate_backward);
  }


  @OnEditorAction(R.id.password)
  boolean password(int actionId) {
    if (actionId == EditorInfo.IME_ACTION_DONE) {
      login.performClick();
      return true;
    }
    return false;
  }

  @OnClick(R.id.login)
  public void login(View view) {

  }

  @OnClick(R.id.forgot)
  public void forgot(View view) {
    startActivity(new Intent(this, ForgotActivity.class));
  }

  @OnClick(R.id.sign_up)
  public void signUp(View view) {
    startActivity(new Intent(this, SignUpActivity.class));
  }

  @OnClick(R.id.expand)
  public void onExpand(View view) {
    Utility.hideKeyboard(this);

    if (isFabOpen) {
      expand.startAnimation(rotateBackward);
      facebook.startAnimation(fabClose);
      twitter.startAnimation(fabClose);
      google.startAnimation(fabClose);
      signUp.startAnimation(fabClose);
    } else {
      expand.startAnimation(rotateForward);
      facebook.startAnimation(fabOpen);
      twitter.startAnimation(fabOpen);
      google.startAnimation(fabOpen);
      signUp.startAnimation(fabOpen);
    }

    isFabOpen = !isFabOpen;
    facebook.setClickable(isFabOpen);
    twitter.setClickable(isFabOpen);
    google.setClickable(isFabOpen);
    signUp.setClickable(isFabOpen);
  }

  @OnClick(R.id.facebook_login)
  public void onFacebookLogin(View view) {

  }

  @OnClick(R.id.twitter_login)
  public void onTwitterLogin(View view) {

  }
}
