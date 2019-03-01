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

import simplicity_an.simplicity_an.Utils.Configurl;

public class PaymentPrensenterImpl implements PaymentPresenter {

    private List<PlaceorderModel>mlist=new ArrayList<>();
    private RequestQueue requestQueue;
private PaymentInterface paymentInterface;

    public PaymentPrensenterImpl(PaymentInterface paymentInterface) {
        this.paymentInterface = paymentInterface;
    }

    @Override
    public List<PlaceorderModel> getMycartPayment(Context context, final String rtype,final String userid) {
        requestQueue= Volley.newRequestQueue(context);
        StringRequest request=new StringRequest(Request.Method.POST, Configurl.exploreurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response","payment"+response.toString());
                mlist.clear();
                try {
                    JSONObject object=new JSONObject(response.toString());
                    String datas=object.optString("result");

                    Log.e("Response","payment"+datas.toString());
JSONObject objs=new JSONObject(datas.toString());

                        PlaceorderModel model=new PlaceorderModel();

                        Log.e("Response","payment"+objs.getInt("total_cart_item"));
                        model.setTotal_cart_item(objs.getInt("total_cart_item"));
                        model.setPrice(objs.getInt("price"));
                        model.setDiscount(objs.getInt("discount"));
                        model.setTax(objs.getInt("tax"));
                        model.setUnique_price(objs.getInt("unique_price"));
                        model.setDelivery_charges(objs.getInt("delivery_charges"));
                        model.setNet_price(objs.getInt("net_price"));

                        mlist.add(model);


                }catch (JSONException e){

                }

                paymentInterface.PaymentResult(mlist);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<>();
                params.put("Key", "Simplicity");
                params.put("Token", "8d83cef3923ec6e4468db1b287ad3fa7");
                params.put("rtype",rtype);
                params.put("user_id",userid);
                return params;
            }
        };
requestQueue.add(request);
        return mlist;
    }
}
