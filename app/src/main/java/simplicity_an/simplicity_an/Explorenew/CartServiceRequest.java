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

public class CartServiceRequest implements CartViewPresenter {
private List<IndexProductModel> cartlist=new ArrayList<>();
RequestQueue requestQueue;

private CartInterface cartInterfaceview;

public CartServiceRequest(CartInterface cartInterfaceview){
    this.cartInterfaceview=cartInterfaceview;
}

    @Override
    public List<IndexProductModel> getMycart(Context context, final String lang, final String rtype, final String userid, final String cartid, final String qtyid, final String count) {
        requestQueue= Volley.newRequestQueue(context);

        StringRequest request=new StringRequest(Request.Method.POST, Configurl.exploreurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response",response);
                cartlist.clear();
                try {

                    JSONObject object=new JSONObject(response.toString());

                    JSONObject product_array=object.getJSONObject("result");
                    String data=product_array.optString("cart_list");
                    int totalcartitem=product_array.optInt("total_cart_item");
                    int total_net_price_cart=product_array.getInt("net_price");
                    JSONArray cart_array = new JSONArray(data.toString());

                    for (int i = 0; i < cart_array.length(); i++) {
                        JSONObject obj = (JSONObject) cart_array.get(i);
                           Log.e("Response", "mycart" + obj.getString("product_title"));
                        IndexProductModel model = new IndexProductModel();
                        String imageeve = obj.isNull("image") ? null : obj
                                .getString("image");
                        model.setImage(imageeve);
                        model.setMain_category_id(obj.getString("main_category_id"));
                        model.setCategory_id(obj.getString("category_id"));
                        model.setCompany_id(obj.getString("company_id"));
                        model.setCart_id(obj.getString("cart_id"));
                        model.setProduct_id(obj.getString("product_id"));
                        model.setProduct_title(obj.getString("product_title"));
                        model.setProduct_description(obj.getString("product_description"));
                        model.setUrl(obj.getString("url"));
                        model.setCount(obj.getString("count"));
                        model.setVisit_list(obj.getInt("visit_list"));
                        model.setQty_id(obj.getString("qty_id"));
                        model.setItem_arrayname("product");
                        model.setMycart_netprice(total_net_price_cart);
                        model.setMycarttotalitem(totalcartitem);
                        JSONArray pricelistarray=obj.getJSONArray("price_list");

                        List<IndexProductModel>price=new ArrayList<>();
                        for(int j=0;j<pricelistarray.length();j++){
                            JSONObject obj_price=(JSONObject)pricelistarray.get(j);
                            IndexProductModel model_price = new IndexProductModel();
                            model_price.setMeasurement(obj_price.getString("measurement"));
                            model_price.setQuantity(obj_price.getString("quantity"));
                            model_price.setPrice(obj_price.getString("price"));
                            model_price.setStock(obj_price.getString("stock"));
                            model_price.setQuantity_id(obj_price.getInt("quantity_id"));
                            model_price.setOffer_type_text(obj_price.getString("offer_type_text"));
                            model_price.setOffer_type(obj_price.getString("offer_type"));
                            model_price.setOffer_price(obj_price.getInt("offer_price"));
                            price.add(model_price);


                        }
                        model.setPricelist(price);

                        cartlist.add(model);

                    }
                    Log.e("Response","mycat"+","+cartlist.size());
                    cartInterfaceview.myCartresult(cartlist);
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

                Map<String,String> param=new HashMap<>();
                param.put("Key", "Simplicity");
                param.put("Token", "8d83cef3923ec6e4468db1b287ad3fa7");
                param.put("rtype",rtype);
                param.put("user_id",userid);
                param.put("language",lang);

                if(cartid!=null){
                    param.put("cart_id",cartid);
                }
                if(qtyid!=null){
                    param.put("qty_id",qtyid);
                }
                if (count!=null){
                    param.put("count",count);
                }

                return param;
            }
        };
requestQueue.add(request);
        return cartlist;
    }
}
