package com.example.sarvex.iscream;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ShareActionProvider;

import com.example.sarvex.iscream.intro.IntroActivity;
import com.example.sarvex.iscream.login.LoginActivity;
import com.example.sarvex.iscream.login.ProfileActivity;
import com.example.sarvex.iscream.scream.Scream;
import com.example.sarvex.iscream.scream.ScreamAdapter;
import com.example.sarvex.iscream.utility.Utility;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import icepick.Icepick;

public class MainActivity extends AppCompatActivity {

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.collapse_toolbar) CollapsingToolbarLayout collapsingToolbar;
  @BindView(R.id.scream_recycler_view) RecyclerView recyclerView;

  private AccountHeader accountHeader;
  private Drawer drawer;
  private ShareActionProvider shareActionProvider;

  private FirebaseAnalytics analytics;
  private FirebaseAuth authentication;
  private FirebaseUser user;

  private List<Scream> screams = new ArrayList<>(10);
  private ProgressDialog progress;


  // Used to load the 'native-lib' library on application startup.
  //    static {
  //        System.loadLibrary("native-lib");
  //    }

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
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    authentication = FirebaseAuth.getInstance();
    analytics = FirebaseAnalytics.getInstance(this);
    analytics.setMinimumSessionDuration(5000);

    setupToolbar();

    setupDrawer();

    setScreams();

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
      }
    });

    user = FirebaseAuth.getInstance().getCurrentUser();
    if (user == null) {
      startActivity(new Intent(this, IntroActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent
          .FLAG_ACTIVITY_CLEAR_TASK));
    }
  }

  private void setScreams() {
    recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerView.setHasFixedSize(true);
    recyclerView.setAdapter(new ScreamAdapter(screams));
  }

  private void setupToolbar() {
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setDisplayShowTitleEnabled(true);
    }
  }

  private void setupDrawer() {
    String username = "sarvex";
    String email = "Sarvex Jatasra";

    accountHeader = new AccountHeaderBuilder().withActivity(this).withCompactStyle(false).withHeaderBackground(R
        .drawable.photo).addProfiles(new ProfileDrawerItem().withName(username).withEmail(email))
        .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
      @Override
      public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
        return false;
      }
    }).build();

    drawer = new DrawerBuilder().withActivity(this).withAccountHeader(accountHeader).withToolbar(toolbar).withFullscreen(true).addDrawerItems(new PrimaryDrawerItem().withName("Profile").withIcon(GoogleMaterial.Icon.gmd_person).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
      @Override
      public boolean onItemClick(View view, int i, IDrawerItem iDrawerItem) {
        startActivity(new Intent(getParent(), ProfileActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent
            .FLAG_ACTIVITY_CLEAR_TASK));
        return false;
      }
    }), new PrimaryDrawerItem().withName("Settings").withIcon(GoogleMaterial.Icon.gmd_settings)
        .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
      @Override
      public boolean onItemClick(View view, int i, IDrawerItem iDrawerItem) {
        startActivity(new Intent(getParent(), SettingsActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent
            .FLAG_ACTIVITY_CLEAR_TASK));
        return false;
      }
    }), new PrimaryDrawerItem().withName("Logout").withIcon(FontAwesome.Icon.faw_sign_out)
        .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
      @Override
      public boolean onItemClick(View view, int i, IDrawerItem iDrawerItem) {
        authentication.signOut();

        startActivity(new Intent(getBaseContext(), IntroActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
            Intent.FLAG_ACTIVITY_CLEAR_TASK));
        return false;
      }
    })).build();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    MenuItem searchItem = menu.findItem(R.id.action_search);

    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
    SearchView searchView = null;
    if (searchItem != null) {
      searchView = (SearchView) searchItem.getActionView();
    }
    if (searchView != null) {
      searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    }

    MenuItem shareItem = menu.findItem(R.id.action_share);
    shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
    shareActionProvider.setShareIntent(doShare());

    return super.onCreateOptionsMenu(menu);
  }

  private Intent doShare() {
    return null;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    switch (id) {
      case R.id.action_notification:
        return true;
      case R.id.action_share:
        return true;
      case R.id.action_search:
        return true;
      default:
        break;
    }

    return super.onOptionsItemSelected(item);
  }


}
