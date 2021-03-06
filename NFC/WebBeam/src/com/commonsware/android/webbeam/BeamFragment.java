/***
  Copyright (c) 2012 CommonsWare, LLC
  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License. You may obtain a copy
  of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
  by applicable law or agreed to in writing, software distributed under the
  License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
  OF ANY KIND, either express or implied. See the License for the specific
  language governing permissions and limitations under the License.
  
  From _The Busy Coder's Guide to Android Development_
    http://commonsware.com/Android
*/

package com.commonsware.android.webbeam;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class BeamFragment extends WebViewFragment {
  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    getWebView().setWebViewClient(new BeamClient());
    getWebView().getSettings().setJavaScriptEnabled(true);
    loadUrl("http://google.com");
    setHasOptionsMenu(true);
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    if (getContract().hasNFC()) {
      inflater.inflate(R.menu.actions, menu);
    }

    super.onCreateOptionsMenu(menu, inflater);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.beam) {
      getContract().enablePush();
      
      return(true);
    }

    return(super.onOptionsItemSelected(item));
  }
  
  WebBeamActivity getContract() {
    return((WebBeamActivity)getActivity());
  }
  
  String getUrl() {
    return(getWebView().getUrl());
  }
  
  void loadUrl(String url) {
    android.util.Log.d(getClass().getSimpleName(), url);
    getWebView().stopLoading();
    getWebView().loadUrl(url);
  }

  class BeamClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView wv, String url) {
      wv.loadUrl(url);

      return(true);
    }
  }
}
