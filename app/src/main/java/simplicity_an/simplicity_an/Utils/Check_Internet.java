package simplicity_an.simplicity_an.Utils;

import android.content.Context;
import android.net.ConnectivityManager;



public class Check_Internet {
    private Context context;
    public boolean isNetworkConnected(Context context){

        ConnectivityManager cm=(ConnectivityManager)context. getSystemService(Context.CONNECTIVITY_SERVICE);

return cm.getActiveNetworkInfo()!=null;
    }
}
