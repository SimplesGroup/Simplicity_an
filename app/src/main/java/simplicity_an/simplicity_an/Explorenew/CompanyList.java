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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;
import simplicity_an.simplicity_an.R;
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

        if (colorcodes != null) {
            if (colorcodes.equals("#FFFFFFFF")) {
                int[] colors = {Color.parseColor(colorcodes), Color.parseColor("#FFFFFFFF")};

                GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        colors);
                gd.setCornerRadius(0f);

                main_complist_layout.setBackgroundDrawable(gd);


            } else {
                int[] colors = {Color.parseColor("#262626"), Color.parseColor("#00000000")};

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
            int[] colors = {Color.parseColor("#262626"), Color.parseColor("#00000000")};

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

        } else {
            title_shop.setTextColor(Color.WHITE);
        }
        String simplycity_title = "fonts/playfairDisplayRegular.ttf";
        Typeface tf_pala = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title);
        if (fontname.equals("playfair")) {
            title_shop.setTypeface(tf_pala);

        } else {
            Typeface sanf = Typeface.createFromAsset(getApplicationContext().getAssets(), Fonts.sanfranciscobold);
            title_shop.setTypeface(sanf);


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

            servicerequest.getCompanylist("1","company_list",String.valueOf(requestCount),myprofileid,"",category_id,getApplicationContext());

        } else if (search_value != null) {

            servicerequest.getCompanylist("1","company_list",String.valueOf(requestCount),"",search_value,category_id,getApplicationContext());

        } else if (myprofileid != null && search_value != null) {

            servicerequest.getCompanylist("1","company_list",String.valueOf(requestCount),myprofileid,search_value,category_id,getApplicationContext());
        } else {

            servicerequest.getCompanylist("1","company_list",String.valueOf(requestCount),"","",category_id,getApplicationContext());
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
