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

public class CartAddresspresenterImpl implements CartShippingPresenter {
    private RequestQueue requestQueue;
List<ShippingModel>addresslist=new ArrayList<>();

    private Cartaddressinterface cartaddressinterface;
public CartAddresspresenterImpl(Cartaddressinterface cartShippingPresenters){
    this.cartaddressinterface=cartShippingPresenters;
}

    public CartAddresspresenterImpl() {
    }

    @Override
    public List<ShippingModel> getAddress(Context context, final String rtype,final String userids) {
requestQueue= Volley.newRequestQueue(context);
addresslist.clear();
        StringRequest request=new StringRequest(Request.Method.POST, Configurl.exploreurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response","shipping address"+response.toString());
                try {
                    JSONObject object=new JSONObject(response.toString());
                    String array=object.optString("result");
                    JSONObject resultobject=new JSONObject(array.toString());
                    String address_ary=resultobject.optString("shipping_address_list");

                    JSONArray shipping_arr=new JSONArray(address_ary.toString());

                    for(int i=0;i<shipping_arr.length();i++){
                        JSONObject obj=(JSONObject) shipping_arr.get(i);
                    ShippingModel model=new ShippingModel();
                    model.setId(obj.getString("id"));
                    model.setAddress(obj.getString("address"));
                    model.setName(obj.getString("name"));
                    model.setEmail(obj.getString("email"));
                    model.setPhone(obj.getString("phone"));
                    model.setLocation(obj.getString("location"));
                    model.setPincode(obj.getString("pincode"));
                    model.setLandmark(obj.getString("landmark"));
                    model.setState(obj.getString("state"));
                    model.setLast_used_address(obj.getString("last_used_address"));
                    addresslist.add(model);
                    }
cartaddressinterface.Shippingadress(addresslist);

                }catch (JSONException e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>param=new HashMap<>();
                param.put("Key", "Simplicity");
                param.put("Token", "8d83cef3923ec6e4468db1b287ad3fa7");
                param.put("rtype",rtype);
                param.put("user_id",userids);
                return param;
            }
        };
requestQueue.add(request);
        return addresslist;
    }

    @Override
    public List<ShippingModel> Addressupdateornew(Context context, final String rtype, final String userids, final String name, final String phone, final String email, final String address, final String location, final String pincode, final String landmark, final String state, final String shippingaddress_id) {
        requestQueue= Volley.newRequestQueue(context);
        addresslist.clear();
        StringRequest request=new StringRequest(Request.Method.POST, Configurl.exploreurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response","shipping address"+response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>param=new HashMap<>();
                param.put("Key", "Simplicity");
                param.put("Token", "8d83cef3923ec6e4468db1b287ad3fa7");
                param.put("rtype",rtype);
                param.put("user_id",userids);
                param.put("name",name);
                param.put("phone",phone);
                param.put("email",email);
                param.put("address",address);
                param.put("location",location);
                param.put("pincode",pincode);
                param.put("landmark",landmark);
                param.put("state",state);
                if(shippingaddress_id!=null){
                    param.put("shipping_address_id",shippingaddress_id);
                }
                return param;
            }
        };
        requestQueue.add(request);
        return addresslist;
    }

    @Override
    public List<ShippingModel> getAddressdeleteandactive(Context context, final String rtype, final String userids, final String shippingaddress_id) {

        requestQueue= Volley.newRequestQueue(context);
        addresslist.clear();
        StringRequest request=new StringRequest(Request.Method.POST, Configurl.exploreurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response","shipping address"+response.toString());
                try {
                    JSONObject object=new JSONObject(response.toString());
                  /*  String array=object.optString("result");
                    JSONObject resultobject=new JSONObject(array.toString());
                    String address_ary=resultobject.optString("shipping_address_list");

                    JSONArray shipping_arr=new JSONArray(address_ary.toString());

                    for(int i=0;i<shipping_arr.length();i++){
                        JSONObject obj=(JSONObject) shipping_arr.get(i);
                        ShippingModel model=new ShippingModel();
                        model.setId(obj.getString("id"));
                        model.setAddress(obj.getString("address"));
                        model.setName(obj.getString("name"));
                        model.setEmail(obj.getString("email"));
                        model.setPhone(obj.getString("phone"));
                        model.setLocation(obj.getString("location"));
                        model.setPincode(obj.getString("pincode"));
                        model.setLandmark(obj.getString("landmark"));
                        model.setState(obj.getString("state"));
                        model.setLast_used_address(obj.getString("last_used_address"));
                        addresslist.add(model);
                    }
                    cartaddressinterface.Shippingadress(addresslist);*/

                }catch (JSONException e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>param=new HashMap<>();
                param.put("Key", "Simplicity");
                param.put("Token", "8d83cef3923ec6e4468db1b287ad3fa7");
                param.put("rtype",rtype);
                param.put("user_id",userids);
                param.put("shipping_address_id",shippingaddress_id);
                return param;
            }
        };
        requestQueue.add(request);


        return addresslist;
    }
}
