package simplicity_an.simplicity_an.Explorenew;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import simplicity_an.simplicity_an.R;
import simplicity_an.simplicity_an.Utils.Fonts;

public class ProductList extends AppCompatActivity implements RequestInterface.Productlist{
    SharedPreferences sharedpreferences;
    public static final String backgroundcolor = "color";
    String activity,contentid,colorcodes;
    public static final String mypreference = "mypref";
    String myprofileid,cartcounts;
    public static final String MYUSERID= "myprofileid";
    public static final String GcmId = "gcmid";
    public static final String Language = "lamguage";
    String playerid,fontname;
    RelativeLayout main_complist_layout;

    private TextView title_shop;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<IndexProductModel> shopDataList ;
    private List<IndexProductModel>datalist;
    private int requestCount = 1;
    private RequestQueue requestQueue;
    private static    ProductlistAdapter productAdapter;

    android.support.v7.widget.SearchView search;
    ProgressDialog pdialog;
    private String search_value;
    private Servicerequest servicerequest;

    String title_name_item,category_id,company_id;
    private TextView textView_Noresult;
    ArrayList<String>title_sub;
    List<String>ids_sub;
    List<String>title_low;
    List<String>ids_low;
    Spinner spinner_subcategory,spinner_lowcategory;
    private ArrayAdapter<String> adapter,lowcatadapter;
    String low_id,sub_id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.explore_productlist_activity);
        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");
            myprofileid = myprofileid.replaceAll("\\D+","");
        }
        colorcodes=sharedpreferences.getString(backgroundcolor,"");
        fontname=sharedpreferences.getString(Fonts.FONT,"");
        main_complist_layout=(RelativeLayout)findViewById(R.id.shop_layout);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        title_shop = (TextView) findViewById(R.id.title_shop_textview);
        textView_Noresult=(TextView)findViewById(R.id.text_noresultfound) ;
        Intent get=getIntent();
        title_name_item=get.getStringExtra("ITEM_NAME");
        category_id=get.getStringExtra("CAT_ID");
        company_id=get.getStringExtra("COMP_ID");
        shopDataList=new ArrayList<IndexProductModel>();
        datalist=new ArrayList<>();
        title_sub=new ArrayList<>();
        ids_sub=new ArrayList<>();
        title_low=new ArrayList<>();
        ids_low=new ArrayList<>();

        servicerequest=new Servicerequest(this);

        spinner_lowcategory=(Spinner)findViewById(R.id.spin_productlist_lowcat);
        spinner_subcategory=(Spinner)findViewById(R.id.spin_productlist_subcat);
        spinner_lowcategory.setVisibility(View.GONE);
        spinner_subcategory.setVisibility(View.GONE);

        main_complist_layout = (RelativeLayout) findViewById(R.id.shop_layout);
        search = (android.support.v7.widget.SearchView) findViewById(R.id.searchview_main);
        search.setActivated(true);
        search.setQueryHint("Search for products/brands in Coimbatore");
        search.onActionViewExpanded();
        search.setIconified(false);
        search.clearFocus();
        setupSearchView();
        shopDataList.clear();

        search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub


            }

            @Override
            public int hashCode() {
                return super.hashCode();
            }
        });


        search.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub

                shopDataList.clear();
                datalist.clear();
                productAdapter.Listitem();
                search_value = query;

                requestCount=1;
                getData();
                title_sub.clear();
                ids_sub.clear();
                title_low.clear();
                ids_low.clear();
                getSubcat();


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub

                return false;
            }
        });

        if (colorcodes != null) {
            if (colorcodes.equals("#FFFFFFFF")) {
                int[] colors = {Color.parseColor(colorcodes), Color.parseColor("#FFFFFFFF")};

                GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        colors);
                gd.setCornerRadius(0f);

                main_complist_layout.setBackgroundDrawable(gd);


            } else {
                int[] colors = {Color.parseColor("#262626"), Color.parseColor("#FF000000")};

                GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        colors);
                gd.setCornerRadius(0f);

                main_complist_layout.setBackgroundDrawable(gd);
                // city.setBackgroundColor(getResources().getColor(R.color.theme1button));
               /* fabplus.setBackgroundResource(R.color.theme1button);
                fabinnerplus.setBackgroundResource(R.color.theme1button);
                fabsearch.setBackgroundResource(R.color.theme1button);*/
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(backgroundcolor, "#262626");

                editor.commit();

            }
        } else {
            int[] colors = {Color.parseColor("#262626"), Color.parseColor("#FF000000")};

            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    colors);
            gd.setCornerRadius(0f);

            main_complist_layout.setBackgroundDrawable(gd);

            /*fabplus.setBackgroundResource(R.color.theme1button);
            fabinnerplus.setBackgroundResource(R.color.theme1button);
            fabsearch.setBackgroundResource(R.color.theme1button);*/
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(backgroundcolor, "#262626");

            editor.commit();


        }
        title_shop.setText(title_name_item);
        textView_Noresult.setText("No Result Found");
        textView_Noresult.setVisibility(View.GONE);
        if (colorcodes.equals("#FFFFFFFF")) {
            title_shop.setTextColor(Color.BLACK);
            textView_Noresult.setTextColor(Color.BLACK);

        } else {
            title_shop.setTextColor(Color.WHITE);
            textView_Noresult.setTextColor(Color.WHITE);
        }
        String simplycity_title = "fonts/playfairDisplayRegular.ttf";
        Typeface tf_pala = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title);
        if (fontname.equals("playfair")) {
            title_shop.setTypeface(tf_pala);
            textView_Noresult.setTypeface(tf_pala);

        } else {
            Typeface sanf = Typeface.createFromAsset(getApplicationContext().getAssets(), Fonts.sanfranciscobold);
            title_shop.setTypeface(sanf);
            textView_Noresult.setTypeface(sanf);


        }

        pdialog = new ProgressDialog(this);
        pdialog.show();
        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview_shop);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_shop);
//RecyclerLayouts();
        RecyclerView.LayoutManager gridLayoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(gridLayoutManager);



        productAdapter = new ProductlistAdapter(getApplicationContext(), shopDataList);
        recyclerView.setAdapter(productAdapter);
       getData();
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                shopDataList.clear();
                productAdapter.Listitem();
                productAdapter.notifyDataSetChanged();

                requestCount = 1;
                getData();
                title_sub.clear();
                ids_sub.clear();
                title_low.clear();
                ids_low.clear();
                getSubcat();
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);

                    }
                }, 3000);

            }
        });



        getSubcat();



       spinner_subcategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position==0){

                }else {
                    String item = parent.getItemAtPosition(position).toString();
                     sub_id=ids_sub.get(position-1).toString();
                    Log.e("ID",sub_id);
                    servicerequest.getProductlistLowcategory("1","low_category_list",sub_id,getApplicationContext());

                    productAdapter.Listitem();
                    shopDataList.clear();
                    datalist.clear();
                    productAdapter.notifyDataSetChanged();
                    requestCount=1;
                   servicerequest.getProductlist("1","product_list",String.valueOf(requestCount),"","",category_id,company_id,sub_id,"",getApplicationContext());

                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
spinner_lowcategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position==0){

        }else {
            String item = parent.getItemAtPosition(position).toString();
            low_id=ids_low.get(position-1).toString();
            Log.e("ID",low_id);
           // servicerequest.getProductlistLowcategory("1","low_category_list",low_id,getApplicationContext());
            requestCount=1;
            productAdapter.Listitem();
            shopDataList.clear();
            datalist.clear();
            productAdapter.notifyDataSetChanged();
           servicerequest.getProductlist("1","product_list",String.valueOf(requestCount),"","",category_id,company_id,sub_id,low_id,getApplicationContext());

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
});


    }
    private void getData() {
        // requestQueue.add(getDataFromTheServer(requestCount));


        //    Servicerequest servicerequest = new Servicerequest(getActivity());
        if (myprofileid != null) {


            servicerequest.getProductlist("1","product_list",String.valueOf(requestCount),myprofileid,"",category_id,company_id,"","",getApplicationContext());

        } else if (search_value != null) {

            servicerequest.getProductlist("1","product_list",String.valueOf(requestCount),"",search_value,category_id,company_id,"","",getApplicationContext());


        } else if (myprofileid != null && search_value != null) {

            servicerequest.getProductlist("1","product_list",String.valueOf(requestCount),myprofileid,search_value,category_id,company_id,"","",getApplicationContext());

        }
        else {

            servicerequest.getProductlist("1","product_list",String.valueOf(requestCount),"","",category_id,company_id,"","",getApplicationContext());
        }

        pdialog.dismiss();
        requestCount++;
        productAdapter.notifyDataSetChanged();


    }
private void getSubcat(){
    servicerequest.getProductlistSubcategory("1","sub_category_list",category_id,company_id,getApplicationContext());

}



    public void dissmissDialog() {
        // TODO Auto-generated method stub
        if (pdialog != null) {
            if (pdialog.isShowing()) {
                pdialog.dismiss();
            }
            pdialog = null;
        }

    }

    private void setupSearchView() {
        // search hint
        search.setQueryHint(getString(R.string.queryhintforshopsearch));

        // background
        View searchPlate = search.findViewById(android.support.v7.appcompat.R.id.search_plate);
        searchPlate.setBackgroundColor(Color.TRANSPARENT);

        // icon
        ImageView searchIcon = (ImageView) search.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        searchIcon.setImageResource(android.R.drawable.ic_menu_search);

        // clear button
        ImageView searchClose = (ImageView) search.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        //searchClose.setImageResource(android.R.drawable.ic_menu_delete);

        // text color
        AutoCompleteTextView searchText = (AutoCompleteTextView) search.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchText.setTextColor(getResources().getColor(R.color.white));
        searchText.setHintTextColor(getResources().getColor(R.color.white));
    }




    @Override
    public void Sendproductlist(List<IndexProductModel> listdata) {
        datalist=listdata;
        shopDataList.addAll(listdata);


        if(shopDataList.size()==0){
            recyclerView.setVisibility(View.GONE);
            textView_Noresult.setVisibility(View.VISIBLE);
        }else {
            recyclerView.setVisibility(View.VISIBLE);
            productAdapter.data(listdata);
            textView_Noresult.setVisibility(View.GONE);

        }
    }

    @Override
    public void searchproductlist(List<IndexProductModel> listsearch) {
        datalist=listsearch;
        shopDataList.addAll(listsearch);
        if(shopDataList.size()==0){
            recyclerView.setVisibility(View.GONE);
            textView_Noresult.setVisibility(View.VISIBLE);

        }else {
            productAdapter.data(listsearch);
            textView_Noresult.setVisibility(View.GONE);
        }
    }

    @Override
    public void Subcategory(List<IndexProductModel> listsubcat) {
if(listsubcat.size()==0){

    spinner_subcategory.setVisibility(View.GONE);

}else {
    spinner_subcategory.setVisibility(View.VISIBLE);
    title_sub.add("CATEGORY");
    for (int i = 0; i < listsubcat.size(); i++) {
        IndexProductModel model = listsubcat.get(i);
        String title_data = model.getSub_category_title();
        String id_data = model.getSub_category_id();
        // Log.e("SUB",title_data.toString());
        title_sub.add(title_data);
        ids_sub.add(id_data);
    }


    adapter = new ArrayAdapter<String>(this, R.layout.explore_my_spinner_style, title_sub) {

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = super.getView(position, convertView, parent);

            ((TextView) v).setTextSize(16);
            if (colorcodes.equals("#FFFFFFFF")) {
                ((TextView) v).setTextColor(
                        getApplicationContext().getResources().getColorStateList(R.color.Black)
                );
            } else {
                ((TextView) v).setTextColor(
                        getApplicationContext().getResources().getColorStateList(R.color.white)
                );
            }

            return v;
        }

        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View v = super.getDropDownView(position, convertView, parent);
            //v.setBackgroundResource(R.drawable.spinner_bg);
            if (colorcodes.equals("#FFFFFFFF")) {
                v.setBackgroundColor(Color.WHITE);
                ((TextView) v).setTextColor(
                        getApplicationContext().getResources().getColorStateList(R.color.Black)
                );
            } else {
                v.setBackgroundColor(Color.BLACK);
                ((TextView) v).setTextColor(
                        getApplicationContext().getResources().getColorStateList(R.color.white)
                );
            }

            ((TextView) v).setGravity(Gravity.CENTER);

            return v;
        }
    };


    spinner_subcategory.setAdapter(adapter);

}



    }

    @Override
    public void Lowcategory(List<IndexProductModel> listlowcat) {

        if(listlowcat.size()==0){

            spinner_lowcategory.setVisibility(View.GONE);

        }else {

            spinner_lowcategory.setVisibility(View.VISIBLE);
title_low.add("CATEGORY");
            for (int i = 0; i < listlowcat.size(); i++) {
                IndexProductModel model = listlowcat.get(i);
                String title_data = model.getLow_category_title();
                String id_data = model.getLow_category_id();
                // Log.e("SUB",title_data.toString());
                title_low.add(title_data);
                ids_low.add(id_data);
            }


            lowcatadapter = new ArrayAdapter<String>(this, R.layout.explore_my_spinner_style, title_low) {

                public View getView(int position, View convertView, ViewGroup parent) {
                    View v = super.getView(position, convertView, parent);

                    ((TextView) v).setTextSize(15);
                    if (colorcodes.equals("#FFFFFFFF")) {
                        ((TextView) v).setTextColor(
                                getApplicationContext().getResources().getColorStateList(R.color.Black)
                        );
                    } else {
                        ((TextView) v).setTextColor(
                                getApplicationContext().getResources().getColorStateList(R.color.white)
                        );
                    }

                    return v;
                }

                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    View v = super.getDropDownView(position, convertView, parent);
                    //v.setBackgroundResource(R.drawable.spinner_bg);
                    if (colorcodes.equals("#FFFFFFFF")) {
                        v.setBackgroundColor(Color.WHITE);
                        ((TextView) v).setTextColor(
                                getApplicationContext().getResources().getColorStateList(R.color.Black)
                        );
                    } else {
                        v.setBackgroundColor(Color.BLACK);
                        ((TextView) v).setTextColor(
                                getApplicationContext().getResources().getColorStateList(R.color.white)
                        );
                    }

                    ((TextView) v).setGravity(Gravity.CENTER);

                    return v;
                }
            };


            spinner_lowcategory.setAdapter(lowcatadapter);
        }
    }
}
