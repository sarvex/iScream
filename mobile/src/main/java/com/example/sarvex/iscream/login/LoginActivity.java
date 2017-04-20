package com.example.sarvex.iscream.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.LoginStatusCallback;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

  @BindView(R.id.sign_up) FloatingActionButton signUp;
  @BindView(R.id.login) FloatingActionButton login;

  private CallbackManager callbackManager;
  private FirebaseAuth authentication;

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
  protected void onStart() {
    super.onStart();

    FirebaseUser user = authentication.getCurrentUser();
    updateUI(user);
  }

  private void updateUI(FirebaseUser user) {

  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Icepick.restoreInstanceState(this, savedInstanceState);
    setContentView(R.layout.activity_login);
    ButterKnife.bind(this);

    authentication = FirebaseAuth.getInstance();

    setupFacebook();
  }

  private void setupFacebook() {
    callbackManager = CallbackManager.Factory.create();
    LoginButton loginButton = (LoginButton) findViewById(R.id.facebook);
    loginButton.setReadPermissions("email", "public_profile");
    loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
      @Override
      public void onSuccess(LoginResult loginResult) {
        handleFacebookAccessToken(loginResult.getAccessToken());
      }

      @Override
      public void onCancel() {

      }

      @Override
      public void onError(FacebookException e) {

      }
    });

    LoginManager.getInstance().retrieveLoginStatus(this, new LoginStatusCallback() {
      @Override
      public void onCompleted(AccessToken accessToken) {
        handleFacebookAccessToken(accessToken);
      }

      @Override
      public void onFailure() {
        // No access token could be retrieved for the user
      }

      @Override
      public void onError(Exception exception) {
        // An error occurred
      }
    });
  }

  private void handleFacebookAccessToken(AccessToken token) {
    AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
    authentication.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
      @Override
      public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
          FirebaseUser user = authentication.getCurrentUser();
          updateUI(user);
        } else {
          Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
          updateUI(null);
        }
      }
    });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    callbackManager.onActivityResult(requestCode, resultCode, data);
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

}
