package com.example.sarvex.iscream.intro;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.sarvex.iscream.MainActivity;
import com.example.sarvex.iscream.login.LoginActivity;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;

import com.example.sarvex.iscream.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;

public class IntroActivity extends AppIntro2 implements WelcomeIntroFragment.OnFragmentInteractionListener,
    ScreamerIntroFragment.OnFragmentInteractionListener, PacifierIntroFragment.OnFragmentInteractionListener,
    EnterIntroFragment.OnFragmentInteractionListener {

  private static final String TAG = IntroActivity.class.toString();
  @BindView(R.id.intro_layout) ConstraintLayout introLayout;
  private FirebaseAuth authentication;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    //    addSlide(new WelcomeIntroFragment());
    //    addSlide(new ScreamerIntroFragment());
    //    addSlide(new PacifierIntroFragment());
    //    addSlide(new EnterIntroFragment());

    addSlide(AppIntroFragment.newInstance("Welcome", "Let's take capitalism out of experiences", R.drawable.welcome,
        getResources().getColor(R.color.primary_dark)));
    addSlide(AppIntroFragment.newInstance("Screamer", "Let's take capitalism out of experiences", R.drawable
        .screamer, getResources().getColor(R.color.primary)));
    addSlide(AppIntroFragment.newInstance("Pacifier", "Let's take capitalism out of experiences", R.drawable
        .pacifier, getResources().getColor(R.color.primary_light)));
    addSlide(AppIntroFragment.newInstance("Enter", "Let's take capitalism out of experiences", R.drawable.enter,
        getResources().getColor(R.color.accent)));

    showSkipButton(true);
    setProgressButtonEnabled(true);

    authentication = FirebaseAuth.getInstance();
  }

  @Override
  public void onSkipPressed(Fragment currentFragment) {
    super.onSkipPressed(currentFragment);
    // Do something when users tap on Skip button.
    authentication.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
      @Override
      public void onComplete(@NonNull Task<AuthResult> task) {
        Log.d(TAG, "signInAnonymously:onComplete:" + task.isSuccessful());

        if (!task.isSuccessful()) {
          Log.w(TAG, "signInAnonymously", task.getException());
          Snackbar.make(introLayout, "Authentication failed.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }

        startActivity(new Intent(getApplicationContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
            | Intent.FLAG_ACTIVITY_CLEAR_TASK));
      }
    });
  }

  @Override
  public void onDonePressed(Fragment currentFragment) {
    super.onDonePressed(currentFragment);
    // Do something when users tap on Done button.
    startActivity(new Intent(this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent
        .FLAG_ACTIVITY_CLEAR_TASK));
  }

  @Override
  public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
    super.onSlideChanged(oldFragment, newFragment);
    // Do something when the slide changes.
  }

  @Override
  public void onFragmentInteraction(Uri uri) {

  }
}
