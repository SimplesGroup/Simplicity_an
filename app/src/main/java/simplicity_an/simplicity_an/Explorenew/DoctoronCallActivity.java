package simplicity_an.simplicity_an.Explorenew;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import simplicity_an.simplicity_an.AdvertisementPage;
import simplicity_an.simplicity_an.R;


public class DoctoronCallActivity extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    String myuserids;
    public static final String GcmId = "gcmid";
    public static final String MYUSERID = "myprofileid";
    public static final String mypreference = "mypref";
    private final String TAG_REQUEST = "MY_TAG";
    String url_notification_count_valueget,myprofileid;
    WebView search;
    ProgressDialog pdialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.searchweb_layout);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid = sharedpreferences.getString(MYUSERID, "");
            Log.e("MUUSERID:", myprofileid);
        }

        Intent in=getIntent();
        String url=in.getStringExtra("URL");

        search = (WebView) findViewById(R.id.textView_desc);


        pdialog = new ProgressDialog(this);
        pdialog.show();
        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        search.getSettings().setJavaScriptEnabled(true);
        search.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        search.loadUrl(url);
        search.setWebViewClient(new MyBrowser());
    }
    private class MyBrowser extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            pdialog.dismiss();

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(url.startsWith("http://simpli")){
                Intent ads=new Intent(getApplicationContext().getApplicationContext(),AdvertisementPage.class);
                ads.putExtra("ID",url);
                startActivity(ads);
            }else {
                Intent ads=new Intent(getApplicationContext().getApplicationContext(),AdvertisementPage.class);                 ads.putExtra("ID",url);                 startActivity(ads);
                // view.loadUrl(url);
            }
            //

            return true;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event){
        if(event.getAction() == KeyEvent.ACTION_DOWN){
            switch(keyCode){
                case KeyEvent.KEYCODE_BACK:
                  onBackPressed();
                    return true;
            }
        }
        return false;
    }

}
