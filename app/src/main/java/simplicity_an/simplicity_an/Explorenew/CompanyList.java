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
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import simplicity_an.simplicity_an.DoitDescription;
import simplicity_an.simplicity_an.R;
import simplicity_an.simplicity_an.SigninpageActivity;
import simplicity_an.simplicity_an.Utils.Fonts;

public class CompanyList extends AppCompatActivity implements RequestInterface.CompanylistRequest {
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
    private static    CompanylistAdapter companyAdapter;

    android.support.v7.widget.SearchView search;
    ProgressDialog pdialog;
    private String search_value;
    private Servicerequest servicerequest;

    String title_name_item,category_id;
private TextView textView_Noresult;

private LinearLayout cartlayout;
private TextView mycart_text,cart_count_text,cartname_text,wishlist_text;
    String language_data,language_value;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.explore_companylist_activity);
        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");
            myprofileid = myprofileid.replaceAll("\\D+","");
        }
        colorcodes=sharedpreferences.getString(backgroundcolor,"");
        fontname=sharedpreferences.getString(Fonts.FONT,"");
        language_data=sharedpreferences.getString(Language,"");
        main_complist_layout=(RelativeLayout)findViewById(R.id.shop_layout);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        title_shop = (TextView) findViewById(R.id.title_shop_textview);
        textView_Noresult=(TextView)findViewById(R.id.text_noresultfound) ;
        Intent get=getIntent();
        title_name_item=get.getStringExtra("ITEM_NAME");
        category_id=get.getStringExtra("CAT_ID");
        shopDataList=new ArrayList<IndexProductModel>();
        datalist=new ArrayList<>();

        servicerequest=new Servicerequest(this);

        main_complist_layout = (RelativeLayout) findViewById(R.id.shop_layout);

        cartlayout=(LinearLayout) findViewById(R.id.cart_layout);
        mycart_text=(TextView)findViewById(R.id.back_textview) ;
        cart_count_text=(TextView)findViewById(R.id.cart_main_count) ;
        cartname_text=(TextView) findViewById(R.id.title_shop_mycartcount_textview);
        wishlist_text=(TextView)findViewById(R.id.wishlist_textview) ;

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
                companyAdapter.Listitem();
                search_value = query;
                // RecyclerView.LayoutManager gridLayoutManager=new LinearLayoutManager(getActivity());
                requestCount=1;
                getData();
                // Toast.makeText(getActivity(),"clicked",Toast.LENGTH_LONG).show();
               /* Intent simplicity = new Intent(getActivity(), SimplicitySearchview.class);
                simplicity.putExtra("QUERY", search_value);
                startActivity(simplicity);*/

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub

                return false;
            }
        });

        mycart_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });
        cartname_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedpreferences.contains(MYUSERID)) {

                    myprofileid = sharedpreferences.getString(MYUSERID, "");
                    myprofileid = myprofileid.replaceAll("\\D+","");
                }
                if(myprofileid!=null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    MyCart frag;
                    frag = new MyCart();
                    frag.show(ft, "txn_tag");
                }else {
                    Intent signin=new Intent(getApplicationContext(),SigninpageActivity.class);
                    signin.putExtra("ACTIVITY","EXP");
                    startActivity(signin);

                }
            }
        });

wishlist_text.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid = sharedpreferences.getString(MYUSERID, "");
            myprofileid = myprofileid.replaceAll("\\D+","");
        }
        if(myprofileid!=null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            MyWishList frag;
            frag = new MyWishList();
            frag.show(ft, "txn_tag");
        }else {
            Intent signin=new Intent(getApplicationContext(),SigninpageActivity.class);
            signin.putExtra("ACTIVITY","EXP");
            startActivity(signin);

        }
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
                cartlayout.setBackgroundColor(Color.BLACK);

            } else {
                int[] colors = {Color.parseColor("#262626"), Color.parseColor("#FF000000")};

                GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        colors);
                gd.setCornerRadius(0f);

                main_complist_layout.setBackgroundDrawable(gd);
                cartlayout.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.themedark));
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
            cartlayout.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.themedark));
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

        if(language_data.equals("English")) {
            language_value="1";
            mycart_text.setText("Back");
            cartname_text.setText("My Cart");
            wishlist_text.setText("Wish List");
        }else {
            language_value="2";
            mycart_text.setText("பின்செல்");
            cartname_text.setText("மை கார்ட்");
            wishlist_text.setText("விருப்பப் பட்டியல்");
        }
        if (colorcodes.equals("#FFFFFFFF")) {
            title_shop.setTextColor(Color.BLACK);
            textView_Noresult.setTextColor(Color.BLACK);
            mycart_text.setTextColor(Color.WHITE);
            cartname_text.setTextColor(Color.WHITE);
            wishlist_text.setTextColor(Color.WHITE);
        } else {
            title_shop.setTextColor(Color.WHITE);
            textView_Noresult.setTextColor(Color.WHITE);
            mycart_text.setTextColor(Color.WHITE);
            cartname_text.setTextColor(Color.WHITE);
            wishlist_text.setTextColor(Color.WHITE);
        }
        String simplycity_title = "fonts/playfairDisplayRegular.ttf";
        Typeface tf_pala = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title);
        if (fontname.equals("playfair")) {
            title_shop.setTypeface(tf_pala);
            textView_Noresult.setTypeface(tf_pala);
            mycart_text.setTypeface(tf_pala);
            cartname_text.setTypeface(tf_pala);
            wishlist_text.setTypeface(tf_pala);


            title_shop.setTextSize(24);
            textView_Noresult.setTextSize(22);
            mycart_text.setTextSize(19);
            cartname_text.setTextSize(19);
            wishlist_text.setTextSize(19);


        } else {
            Typeface sanf = Typeface.createFromAsset(getApplicationContext().getAssets(), Fonts.sanfranciscobold);
            title_shop.setTypeface(sanf);
            textView_Noresult.setTypeface(sanf);
            mycart_text.setTypeface(sanf);
            cartname_text.setTypeface(sanf);
            wishlist_text.setTypeface(sanf);


            title_shop.setTextSize(23);
            textView_Noresult.setTextSize(16);
            mycart_text.setTextSize(15);
            cartname_text.setTextSize(15);
            wishlist_text.setTextSize(15);

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



        companyAdapter = new CompanylistAdapter(getApplicationContext(), shopDataList);
        recyclerView.setAdapter(companyAdapter);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                shopDataList.clear();
                companyAdapter.Listitem();
                companyAdapter.notifyDataSetChanged();

                requestCount = 1;
                getData();

                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);

                    }
                }, 3000);

            }
        });


        getData();
    }



    private void getData() {
        // requestQueue.add(getDataFromTheServer(requestCount));


        //    Servicerequest servicerequest = new Servicerequest(getActivity());
        if (myprofileid != null) {

            servicerequest.getCompanylist(language_value,"company_list",String.valueOf(requestCount),myprofileid,"",category_id,getApplicationContext());

        } else if (search_value != null) {

            servicerequest.getCompanylist(language_value,"company_list",String.valueOf(requestCount),"",search_value,category_id,getApplicationContext());

        } else if (myprofileid != null && search_value != null) {

            servicerequest.getCompanylist(language_value,"company_list",String.valueOf(requestCount),myprofileid,search_value,category_id,getApplicationContext());
        } else {

            servicerequest.getCompanylist(language_value,"company_list",String.valueOf(requestCount),"","",category_id,getApplicationContext());

        }

        pdialog.dismiss();
        requestCount++;
        companyAdapter.notifyDataSetChanged();


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
    public void SendComp(List<IndexProductModel> listdata) {
        datalist=listdata;
        shopDataList.addAll(listdata);

        for(int i=0;i<shopDataList.size();i++){
            IndexProductModel model=shopDataList.get(i);

            Log.e("Response","cartcount"+String.valueOf(model.getCart_count()).toString());
            cart_count_text.setText(String.valueOf(model.getCart_count()).toString());

        }
       if(shopDataList.size()==0){
            recyclerView.setVisibility(View.GONE);
            textView_Noresult.setVisibility(View.VISIBLE);
        }else {
            companyAdapter.data(listdata);
            textView_Noresult.setVisibility(View.GONE);

        }


    }

    @Override
    public void searchdataComp(List<IndexProductModel> listsearch) {
        datalist=listsearch;
        shopDataList.addAll(listsearch);
        if(shopDataList.size()==0){
            recyclerView.setVisibility(View.GONE);
            textView_Noresult.setVisibility(View.VISIBLE);

        }else {
            companyAdapter.data(listsearch);
            textView_Noresult.setVisibility(View.GONE);
        }


    }
}
