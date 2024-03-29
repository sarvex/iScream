/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.example.sarvex.iscream;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.mikepenz.iconics.context.IconicsContextWrapper;

import butterknife.ButterKnife;
import icepick.Icepick;


/*
 * MainActivity class that loads MainFragment
 */
public class MainActivity extends Activity {
  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    Icepick.saveInstanceState(this, outState);
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
  }

  /**
   * Called when the activity is first created.
   */

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Icepick.restoreInstanceState(this, savedInstanceState);
    ButterKnife.bind(this);

    setContentView(R.layout.activity_main);
  }
}
