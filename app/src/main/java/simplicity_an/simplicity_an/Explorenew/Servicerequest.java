package simplicity_an.simplicity_an.Explorenew;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import simplicity_an.simplicity_an.Config;
import simplicity_an.simplicity_an.MainEnglish.ShopFragment;
import simplicity_an.simplicity_an.Utils.Configurl;

public class Servicerequest {
private static  List<IndexProductModel>list=new ArrayList<>();
 RequestQueue vollRequestQueue;

    public List<IndexProductModel>index(final String lang, final String rtype, final String pagenum, final String profileid, final String searchtext, Context context){

        vollRequestQueue=Volley.newRequestQueue(context);

        StringRequest request=new StringRequest(Request.Method.POST, Configurl.exploreurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject object=new JSONObject(response.toString());
                    // String data=object.optString(1);
                    JSONObject jsonObject=object.optJSONObject("result");
                    String datas=jsonObject.optString("category");
                    JSONArray category_array=new JSONArray(datas.toString());
                    // JSONArray array=object.getJSONArray("result");

                    //  JSONArray jsonArray=new JSONArray(data.toString());
                    Log.e("Response","shopcat"+datas.toString());
                   /* JSONObject object_category=new JSONObject(array.toString());

                    Log.e("Response","shopcat"+category_array.toString());*/
                    for (int i = 0; i < category_array.length(); i++) {
                        JSONObject obj = (JSONObject) category_array.get(i);
                        Log.e("Response","shopcatdata"+obj.getString("category_title"));
                        IndexProductModel model=new IndexProductModel();
                        String imageeve = obj.isNull("image") ? null : obj
                                .getString("image");
                        model.setImage(imageeve);
                        model.setCategory_title(obj.getString("category_title"));
                        model.setUrl(obj.getString("url"));
                       list.add(model);
                    }
                }catch (JSONException e){

                }
                Log.e("Response","from+"+list.toString());

CallPage();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> param=new HashMap<>();
                param.put("Key", "Simplicity");
                param.put("Token", "8d83cef3923ec6e4468db1b287ad3fa7");
                param.put("language", lang);
                param.put("rtype", rtype);

                param.put("page", pagenum);
                if(profileid!=null){
                    param.put("user_id ",profileid);
                }
                if(searchtext!=null){
                    param.put("search_text",searchtext);
                }
                return param;
            }
        };
        vollRequestQueue.add(request);

        return list;
    }
public List<IndexProductModel>CallPage(){

    RequestInterface requestInterface=new ShopFragment();
    requestInterface.Datacall(list);
        return list;
}

}
