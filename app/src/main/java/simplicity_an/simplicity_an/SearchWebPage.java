package simplicity_an.simplicity_an;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class SearchWebPage extends Fragment {
    SharedPreferences sharedpreferences;
    String myuserids;
    public static final String GcmId = "gcmid";
    public static final String MYUSERID = "myprofileid";
    public static final String mypreference = "mypref";
    private final String TAG_REQUEST = "MY_TAG";
    String url_notification_count_valueget,myprofileid;
    WebView search;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.searchweb_layout, container, false);
        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid = sharedpreferences.getString(MYUSERID, "");
            Log.e("MUUSERID:", myprofileid);
        }
        search = (WebView) view.findViewById(R.id.search_web);
        search.getSettings().setLoadsImagesAutomatically(true);
        search.getSettings().setPluginState(WebSettings.PluginState.ON);
        search.getSettings().setAllowFileAccess(true);

        search.getSettings().setJavaScriptEnabled(true);
        search.loadUrl("http://simpli-city.in/app/home.php");
        return view;
    }
}