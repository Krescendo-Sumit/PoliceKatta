package police.bharti.katta.view.bhartidetailsmenu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import police.bharti.katta.R;
import police.bharti.katta.adapter.BhartiAdapter;
import police.bharti.katta.model.BhartiModel;
import police.bharti.katta.util.Preferences;
import police.bharti.katta.util.RetrofitClient;
import police.bharti.katta.view.listofbharti.ListOfBharti;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BhartiDetailsIndivisual extends AppCompatActivity {
    ProgressDialog progressDialog;
    Context context;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bharti_details_indivisual);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context=BhartiDetailsIndivisual.this;
        webView=(WebView)findViewById(R.id.web);
        progressDialog = new ProgressDialog(context, R.style.Theme_AppCompat);
        // progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.getWindow().setGravity(Gravity.BOTTOM | Gravity.CENTER);
        int item=getIntent().getExtras().getInt("id");
      //  Toast.makeText(this, ""+item, Toast.LENGTH_SHORT).show();

        webView.setWebViewClient(new Browser_Home());
        webView.setWebChromeClient(new ChromeClient());
        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        setTitle("भरती बद्दल माहिती");
         getBhartiDetails(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == android.R.id.home) {
            // app icon in action bar clicked; goto parent activity.
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void getBhartiDetails( int item) {
        try {
            if (!progressDialog.isShowing())
                progressDialog.show();

  /*        JSONObject jsonObject = new JSONObject();
            jsonObject.put("mobile", Preferences.get(context,Preferences.USER_MOBILE));
            jsonObject.put("id",  Preferences.get(context,Preferences.SELECTEDEXAMID));
            jsonObject.put("type", ""+item);
*/
          String mobile=Preferences.get(context,Preferences.USER_MOBILE);
          String id=Preferences.get(context,Preferences.SELECTEDEXAMID);
          String type=""+item;
            Call<List<BhartiModel>> call = RetrofitClient.getInstance().getMyApi().getBhartiMenuDetails(mobile,id,type);
            call.enqueue(new Callback<List<BhartiModel>>() {
                @Override
                public void onResponse(Call<List<BhartiModel>> call, Response<List<BhartiModel>> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    if(response.body()!=null) {

                        List<BhartiModel> videos = response.body();
                        String vids[] = new String[videos.size()];
                        for (BhartiModel v : videos) {


                           webView.loadData(v.getData(),"text/html", "UTF-8");

                            Log.i("Video name", v.getData());
                        }


                        try {

                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }else
                    {
                        Toast.makeText(BhartiDetailsIndivisual.this, "" +
                                "4" +
                                " went wrong", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<List<BhartiModel>> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {

        }
    }

    private class Browser_Home extends WebViewClient {
        Browser_Home(){}

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url){
            // do your handling codes here, which url is the requested url
            // probably you need to open that url rather than redirect:
            view.loadUrl(url);
            return false; // then it is not handled by default action
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Log.i("WEB_VIEW_TEST", "error code:" + errorCode);

            // view.loadData(data, "text/html", "UTF-8");
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }

    private class ChromeClient extends WebChromeClient {
        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullscreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        ChromeClient() {}

        public Bitmap getDefaultVideoPoster()
        {
            if (mCustomView == null) {
                return null;
            }
            return BitmapFactory.decodeResource(getApplicationContext().getResources(), 2130837573);
        }
        public void onProgressChanged(WebView view, int progress) {
            //Make the bar disappear after URL is loaded, and changes string to Loading...
            //  setTitle("Loading...");
      /*      setProgress(progress * 100); //Make the bar disappear after URL is loaded
            pb.setProgress(progress);
            // Return the app name after finish loading
            if (progress == 100) {

                pb.setVisibility(View.GONE);
            }*/
        }


        public void onHideCustomView()
        {
            ((FrameLayout)getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback)
        {
            if (this.mCustomView != null)
            {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout)getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            getWindow().getDecorView().setSystemUiVisibility(3846 | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }
}