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
private   List<IndexProductModel>list=new ArrayList<>();

private   List<IndexProductModel>categorylist=new ArrayList<>();
private  List<IndexProductModel>companylist=new ArrayList<>();
private  List<IndexProductModel>productlist=new ArrayList<>();
    private  List<IndexProductModel>subcatlist=new ArrayList<>();
    private  List<IndexProductModel>lowcatlist=new ArrayList<>();
 RequestQueue vollRequestQueue;
private RequestInterface requestInterface;
private RequestInterface.CompanylistRequest requestcompanylist;
private RequestInterface.Productlist requestProductlist;
 public Servicerequest(RequestInterface requestInterfaces){
     this.requestInterface=requestInterfaces;
 }
public Servicerequest(RequestInterface.CompanylistRequest requestcompanylists){
    this.requestcompanylist=requestcompanylists;
}
public Servicerequest(RequestInterface.Productlist requestProductlists){
     this.requestProductlist=requestProductlists;
}
    public List<IndexProductModel>index(final String lang, final String rtype, final String pagenum, final String profileid, final String searchtext, Context context){

        vollRequestQueue=Volley.newRequestQueue(context);

        StringRequest request=new StringRequest(Request.Method.POST, Configurl.exploreurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              //  Log.e("Response","shops"+response.toString());
                categorylist.clear();
                productlist.clear();
                companylist.clear();
                list.clear();
                try{

                    JSONObject object=new JSONObject(response.toString());

                    JSONObject jsonObject=object.optJSONObject("result");
                    String total=jsonObject.optString("total");
                    JSONObject totalobjects=new JSONObject(total.toString());

                    int cat_count=totalobjects.optInt("category");
                    int company_count=totalobjects.optInt("company");
                    int product_count=totalobjects.optInt("product");
                    String datas=jsonObject.optString("category");
                    String company_data=jsonObject.optString("company");
                    String product_data=jsonObject.optString("product");


                   // if(cat_count!=0) {

                        JSONArray category_array = new JSONArray(datas.toString());

                        //Log.e("Response", "shopcat" + datas.toString());

                        for (int i = 0; i < category_array.length(); i++) {
                            JSONObject obj = (JSONObject) category_array.get(i);
                           // Log.e("Response", "shopcatdata" + obj.getString("category_title"));
                            IndexProductModel model = new IndexProductModel();
                            String imageeve = obj.isNull("image") ? null : obj
                                    .getString("image");
                            model.setImage(imageeve);
                            model.setCategory_title(obj.getString("category_title"));
                            model.setCategory_id(obj.getString("category_id"));
                            model.setUrl(obj.getString("url"));
                            model.setItem_arrayname("category");

                            if(searchtext.equals("")|| searchtext==null){
                               // Log.e("Response","maincate");
                                model.setMain_category_id("0");
                            }else {
                                model.setMain_category_id(obj.getString("main_category_id"));


                            }



                            categorylist.add(model);
                        }
                  // }

                    if (product_count!=0){

                        JSONArray product_array=new JSONArray(product_data.toString());
                       // Log.e("Response", "shopproduct" + product_data.toString());
                        for (int i = 0; i < product_array.length(); i++) {
                            JSONObject obj = (JSONObject) product_array.get(i);
                         //   Log.e("Response", "shopprodata" + obj.getString("category_title"));
                            IndexProductModel model = new IndexProductModel();
                            String imageeve = obj.isNull("image") ? null : obj
                                    .getString("image");
                            model.setImage(imageeve);
                            model.setMain_category_id(obj.getString("main_category_id"));
                            model.setCategory_id(obj.getString("category_id"));
                            model.setCompany_id(obj.getString("company_id"));
                            model.setSub_category_id(obj.getString("sub_category_id"));
                            model.setLow_category_id(obj.getString("low_category_id"));
                            model.setProduct_id(obj.getString("product_id"));
                            model.setProduct_title(obj.getString("product_title"));
                            model.setProduct_description(obj.getString("product_description"));
                            model.setUrl(obj.getString("url"));
                            model.setVisit_list(obj.getInt("visit_list"));
                            model.setCart_list(obj.getInt("cart_list"));
                            model.setItem_arrayname("product");
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

                            productlist.add(model);
                        }


                  }


                    if (company_count!=0){

                        JSONArray company_array=new JSONArray(company_data.toString());
                      //  Log.e("Response", "shopcompany" + company_data.toString());
                        for (int i = 0; i < company_array.length(); i++) {
                            JSONObject obj = (JSONObject) company_array.get(i);
                           // Log.e("Response", "shopcatdata" + obj.getString("category_title"));
                            IndexProductModel model = new IndexProductModel();
                            String imageeve = obj.isNull("image") ? null : obj
                                    .getString("image");
                            model.setImage(imageeve);
                            model.setMain_category_id(obj.getString("main_category_id"));
                            model.setCategory_id(obj.getString("category_id"));
                            model.setCompany_id(obj.getString("company_id"));
                            model.setCategory_title(obj.getString("category_title"));
                            model.setCompany_title(obj.getString("company_title"));
                            model.setDescription(obj.getString("description"));
                            model.setUrl(obj.getString("url"));
                            model.setItem_arrayname("company");
                            companylist.add(model);
                        }
                    }


                }catch (JSONException e){

                }
                if(categorylist.size()>0){
                    list.addAll(categorylist);
                }
                if(companylist.size()>0){
                    list.addAll(companylist);
                }
                if(productlist.size()>0){
                    list.addAll(productlist);
                }



                Log.e("Response","from+"+list.size());
                requestInterface.RecyclerLayouts(searchtext);
requestInterface.Send(list);




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
                }else {

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


    public void getCompanylist(final String lang, final String rtype, final String pagenum, final String profileid, final String searchtext, final String cattegory, Context context){
        vollRequestQueue=Volley.newRequestQueue(context);

        StringRequest company_request=new StringRequest(Request.Method.POST, Configurl.exploreurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response","shop__company"+response.toString());
                categorylist.clear();
                productlist.clear();
                companylist.clear();
                    list.clear();
                try{

                    JSONObject object=new JSONObject(response.toString());

                    JSONObject jsonObject=object.optJSONObject("result");
                    String total=jsonObject.optString("total");
                    JSONObject totalobjects=new JSONObject(total.toString());

                    int cat_count=totalobjects.optInt("category");
                    int company_count=totalobjects.optInt("company");
                    int product_count=totalobjects.optInt("product");
                    String datas=jsonObject.optString("category");
                    String company_data=jsonObject.optString("company");
                    String product_data=jsonObject.optString("product");


                    // if(cat_count!=0) {

                    JSONArray category_array = new JSONArray(datas.toString());

                    //Log.e("Response", "shopcat" + datas.toString());

                    for (int i = 0; i < category_array.length(); i++) {
                        JSONObject obj = (JSONObject) category_array.get(i);
                        // Log.e("Response", "shopcatdata" + obj.getString("category_title"));
                        IndexProductModel model = new IndexProductModel();
                        String imageeve = obj.isNull("image") ? null : obj
                                .getString("image");
                        model.setImage(imageeve);
                        model.setCategory_title(obj.getString("category_title"));
                        model.setCategory_id(obj.getString("category_id"));
                        model.setUrl(obj.getString("url"));
                        model.setItem_arrayname("category");

                        if(searchtext.equals("")|| searchtext==null){
                            // Log.e("Response","maincate");
                            model.setMain_category_id("0");
                        }else {
                            model.setMain_category_id(obj.getString("main_category_id"));


                        }



                        categorylist.add(model);
                    }
                    // }

                    if (product_count!=0){

                        JSONArray product_array=new JSONArray(product_data.toString());
                        // Log.e("Response", "shopproduct" + product_data.toString());
                        for (int i = 0; i < product_array.length(); i++) {
                            JSONObject obj = (JSONObject) product_array.get(i);
                            //   Log.e("Response", "shopprodata" + obj.getString("category_title"));
                            IndexProductModel model = new IndexProductModel();
                            String imageeve = obj.isNull("image") ? null : obj
                                    .getString("image");
                            model.setImage(imageeve);
                            model.setMain_category_id(obj.getString("main_category_id"));
                            model.setCategory_id(obj.getString("category_id"));
                            model.setCompany_id(obj.getString("company_id"));
                            model.setSub_category_id(obj.getString("sub_category_id"));
                            model.setLow_category_id(obj.getString("low_category_id"));
                            model.setProduct_id(obj.getString("product_id"));
                            model.setProduct_title(obj.getString("product_title"));
                            model.setProduct_description(obj.getString("product_description"));
                            model.setUrl(obj.getString("url"));
                            model.setVisit_list(obj.getInt("visit_list"));
                            model.setCart_list(obj.getInt("cart_list"));
                            model.setItem_arrayname("product");
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

                            productlist.add(model);
                        }


                    }


                    if (company_count!=0){

                        JSONArray company_array=new JSONArray(company_data.toString());
                        //  Log.e("Response", "shopcompany" + company_data.toString());
                        for (int i = 0; i < company_array.length(); i++) {
                            JSONObject obj = (JSONObject) company_array.get(i);
                            // Log.e("Response", "shopcatdata" + obj.getString("category_title"));
                            IndexProductModel model = new IndexProductModel();
                            String imageeve = obj.isNull("image") ? null : obj
                                    .getString("image");
                            model.setImage(imageeve);
                            model.setMain_category_id(obj.getString("main_category_id"));
                            model.setCategory_id(obj.getString("category_id"));
                            model.setCompany_id(obj.getString("company_id"));
                            model.setCategory_title(obj.getString("category_title"));
                            model.setCompany_title(obj.getString("company_title"));
                            model.setDescription(obj.getString("description"));
                            model.setUrl(obj.getString("url"));
                            model.setItem_arrayname("company");
                            companylist.add(model);
                        }
                    }


                }catch (JSONException e){

                }
                if(categorylist.size()>0){
                    list.addAll(categorylist);
                }
                if(companylist.size()>0){
                    list.addAll(companylist);
                }
                if(productlist.size()>0){
                    list.addAll(productlist);
                }



                Log.e("Response","from+"+list.size());
                //requestInterface.RecyclerLayouts(searchtext);
                requestcompanylist.SendComp(list);





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
                param.put("language", lang);
                param.put("rtype", rtype);
                param.put("category_id",cattegory);
                param.put("page", pagenum);
                if(profileid!=null){
                    param.put("user_id ",profileid);
                }else {

                }
                if(searchtext!=null){
                    param.put("search_text",searchtext);
                }
                return param;
            }
        };
        vollRequestQueue.add(company_request);


    }

    public void getProductlist(final String lang, final String rtype, final String pagenum, final String profileid, final String searchtext, final String cattegory,final String companyid,final String subcatid,final String lowcatid, Context context){
        vollRequestQueue=Volley.newRequestQueue(context);

        StringRequest company_request=new StringRequest(Request.Method.POST, Configurl.exploreurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response","shop__productlist"+response.toString());
                categorylist.clear();
                productlist.clear();
                companylist.clear();
                list.clear();
                try{

                    JSONObject object=new JSONObject(response.toString());

                    JSONObject jsonObject=object.optJSONObject("result");
                    String total=jsonObject.optString("total");
                    JSONObject totalobjects=new JSONObject(total.toString());

                    int cat_count=totalobjects.optInt("category");
                    int company_count=totalobjects.optInt("company");
                    int product_count=totalobjects.optInt("product");
                    String datas=jsonObject.optString("category");
                    String company_data=jsonObject.optString("company");
                    String product_data=jsonObject.optString("product");


                    // if(cat_count!=0) {

                    JSONArray category_array = new JSONArray(datas.toString());

                    //Log.e("Response", "shopcat" + datas.toString());

                    for (int i = 0; i < category_array.length(); i++) {
                        JSONObject obj = (JSONObject) category_array.get(i);
                        // Log.e("Response", "shopcatdata" + obj.getString("category_title"));
                        IndexProductModel model = new IndexProductModel();
                        String imageeve = obj.isNull("image") ? null : obj
                                .getString("image");
                        model.setImage(imageeve);
                        model.setCategory_title(obj.getString("category_title"));
                        model.setCategory_id(obj.getString("category_id"));
                        model.setUrl(obj.getString("url"));
                        model.setItem_arrayname("category");

                        if(searchtext.equals("")|| searchtext==null){
                            // Log.e("Response","maincate");
                            model.setMain_category_id("0");
                        }else {
                            model.setMain_category_id(obj.getString("main_category_id"));


                        }



                        categorylist.add(model);
                    }
                    // }

                    if (product_count!=0){

                        JSONArray product_array=new JSONArray(product_data.toString());
                        // Log.e("Response", "shopproduct" + product_data.toString());
                        for (int i = 0; i < product_array.length(); i++) {
                            JSONObject obj = (JSONObject) product_array.get(i);
                            //   Log.e("Response", "shopprodata" + obj.getString("category_title"));
                            IndexProductModel model = new IndexProductModel();
                            String imageeve = obj.isNull("image") ? null : obj
                                    .getString("image");
                            model.setImage(imageeve);
                            model.setMain_category_id(obj.getString("main_category_id"));
                            model.setCategory_id(obj.getString("category_id"));
                            model.setCompany_id(obj.getString("company_id"));
                            model.setSub_category_id(obj.getString("sub_category_id"));
                            model.setLow_category_id(obj.getString("low_category_id"));
                            model.setProduct_id(obj.getString("product_id"));
                            model.setProduct_title(obj.getString("product_title"));
                            model.setProduct_description(obj.getString("product_description"));
                            model.setUrl(obj.getString("url"));
                            model.setVisit_list(obj.getInt("visit_list"));
                            model.setCart_list(obj.getInt("cart_list"));
                            model.setItem_arrayname("product");
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

                            productlist.add(model);
                        }


                    }


                    if (company_count!=0){

                        JSONArray company_array=new JSONArray(company_data.toString());
                        //  Log.e("Response", "shopcompany" + company_data.toString());
                        for (int i = 0; i < company_array.length(); i++) {
                            JSONObject obj = (JSONObject) company_array.get(i);
                            // Log.e("Response", "shopcatdata" + obj.getString("category_title"));
                            IndexProductModel model = new IndexProductModel();
                            String imageeve = obj.isNull("image") ? null : obj
                                    .getString("image");
                            model.setImage(imageeve);
                            model.setMain_category_id(obj.getString("main_category_id"));
                            model.setCategory_id(obj.getString("category_id"));
                            model.setCompany_id(obj.getString("company_id"));
                            model.setCategory_title(obj.getString("category_title"));
                            model.setCompany_title(obj.getString("company_title"));
                            model.setDescription(obj.getString("description"));
                            model.setUrl(obj.getString("url"));
                            model.setItem_arrayname("company");
                            companylist.add(model);
                        }
                    }


                }catch (JSONException e){

                }
                if(categorylist.size()>0){
                    list.addAll(categorylist);
                }
                if(companylist.size()>0){
                    list.addAll(companylist);
                }
                if(productlist.size()>0){
                    list.addAll(productlist);
                }



                Log.e("Response","from+"+list.size());
               // requestInterface.RecyclerLayouts(searchtext);

                requestProductlist.Sendproductlist(list);





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
                param.put("language", lang);
                param.put("rtype", rtype);
                param.put("category_id",cattegory);
                param.put("company_id",companyid);
                param.put("page", pagenum);
                if(profileid!=null){
                    param.put("user_id ",profileid);
                }else {

                }
                if(searchtext!=null){
                    param.put("search_text",searchtext);
                }
                if(subcatid!=null){
                    param.put("sub_category_id",subcatid);
                }
                if (lowcatid!=null){
                    param.put("low_category_id",lowcatid);
                }

                return param;
            }
        };
        vollRequestQueue.add(company_request);


    }
    public void getProductlistSubcategory(final String lang, final String rtype, final String cattegory,final String companyid, Context context){
        vollRequestQueue=Volley.newRequestQueue(context);

        StringRequest company_request=new StringRequest(Request.Method.POST, Configurl.exploreurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response","shop__productlist"+response.toString());
                categorylist.clear();
                productlist.clear();
                companylist.clear();
                list.clear();
               try{

                    JSONObject object=new JSONObject(response.toString());

                    JSONObject jsonObject=object.optJSONObject("result");
                   Log.e("Response","shop__productlist"+jsonObject.toString());
                   /* String total=jsonObject.optString("total");
                    JSONObject totalobjects=new JSONObject(total.toString());*/


                    String datas=jsonObject.optString("sub_category_list");

                   Log.e("Response","shop__productlistar"+datas.toString());



                    JSONArray category_array = new JSONArray(datas.toString());



                    for (int i = 0; i < category_array.length(); i++) {
                        JSONObject obj = (JSONObject) category_array.get(i);
                        Log.e("Response", "shopcatdata" + obj.getString("sub_category_title"));
                        IndexProductModel model = new IndexProductModel();

                      model.setSub_category_id(obj.getString("sub_category_id"));
                      model.setSub_category_title(obj.getString("sub_category_title"));





                        subcatlist.add(model);
                    }

                   requestProductlist.Subcategory(subcatlist);





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
                param.put("language", lang);
                param.put("rtype", rtype);
                param.put("category_id",cattegory);
                param.put("company_id",companyid);





                return param;
            }
        };
        vollRequestQueue.add(company_request);


    }


    public void getProductlistLowcategory(final String lang, final String rtype, final String subcattegory, Context context){
        vollRequestQueue=Volley.newRequestQueue(context);

        StringRequest company_request=new StringRequest(Request.Method.POST, Configurl.exploreurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response","shop__productlist_low"+response.toString());

               lowcatlist.clear();
               try{

                    JSONObject object=new JSONObject(response.toString());

                    JSONObject jsonObject=object.optJSONObject("result");

                    String datas=jsonObject.optString("low_category_list");





                    JSONArray category_array = new JSONArray(datas.toString());



                    for (int i = 0; i < category_array.length(); i++) {
                        JSONObject obj = (JSONObject) category_array.get(i);

                        IndexProductModel model = new IndexProductModel();

                        model.setLow_category_id(obj.getString("low_category_id"));
                        model.setLow_category_title(obj.getString("low_category_title"));
                        Log.e("Response", "shopcatdata" + obj.getString("low_category_title"));




                        lowcatlist.add(model);
                    }

requestProductlist.Lowcategory(lowcatlist);





                }catch (JSONException e){

                }



                Log.e("Response","from+"+list.size());






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
                param.put("language", lang);
                param.put("rtype", rtype);
                param.put("sub_category_id",subcattegory);






                return param;
            }
        };
        vollRequestQueue.add(company_request);


    }


}
