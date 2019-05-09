package simplicity_an.simplicity_an.MainEnglish;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import simplicity_an.simplicity_an.Explorenew.IndexAdapter;
import simplicity_an.simplicity_an.Explorenew.IndexProductModel;
import simplicity_an.simplicity_an.Explorenew.MyCart;
import simplicity_an.simplicity_an.Explorenew.RequestInterface;
import simplicity_an.simplicity_an.Explorenew.Servicerequest;
import simplicity_an.simplicity_an.R;
import simplicity_an.simplicity_an.SigninpageActivity;
import simplicity_an.simplicity_an.Utils.Fonts;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

public class ShopFragment extends Fragment implements RequestInterface {
    private TextView title_shop,mycart_text,mycart_count_text;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private    List<IndexProductModel> shopDataList ;
    private List<IndexProductModel>datalist;
    private int requestCount = 1;
    private RequestQueue requestQueue;
    private static    IndexAdapter shopAdapter;

    android.support.v7.widget.SearchView search;

    private SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID = "myprofileid";
    public static final String backgroundcolor = "color";
    public static final String Language = "lamguage";
    String myprofileid, colorcodes, fontname;
    RelativeLayout mainlayout;
    ProgressDialog pdialog;
    private String search_value;
    private Servicerequest servicerequest;
    String language_data,language_value;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static ShopFragment newInstance() {
        ShopFragment fragment = new ShopFragment();
        return fragment;
    }

    public ShopFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shop_fragment, container, false);
        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid = sharedpreferences.getString(MYUSERID, "");
            myprofileid = myprofileid.replaceAll("\\D+", "");
        }
        colorcodes = sharedpreferences.getString(backgroundcolor, "");
        fontname = sharedpreferences.getString(Fonts.FONT, "");
        language_data=sharedpreferences.getString(Language,"");
        requestQueue = Volley.newRequestQueue(getActivity());
        title_shop = (TextView) view.findViewById(R.id.title_shop_textview);
        mycart_text = (TextView) view.findViewById(R.id.title_shop_mycartcount_textview);
        mycart_count_text = (TextView) view.findViewById(R.id.cart_main_count);
        shopDataList=new ArrayList<IndexProductModel>();
        datalist=new ArrayList<>();

        servicerequest=new Servicerequest(this);

        mainlayout = (RelativeLayout) view.findViewById(R.id.shop_layout);
        search = (android.support.v7.widget.SearchView) view.findViewById(R.id.searchview_main);
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
                shopAdapter.Listitem();
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

                mainlayout.setBackgroundDrawable(gd);


            } else {
                int[] colors = {Color.parseColor("#262626"), Color.parseColor("#00000000")};

                GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        colors);
                gd.setCornerRadius(0f);

                mainlayout.setBackgroundDrawable(gd);
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

            mainlayout.setBackgroundDrawable(gd);

            /*fabplus.setBackgroundResource(R.color.theme1button);
            fabinnerplus.setBackgroundResource(R.color.theme1button);
            fabsearch.setBackgroundResource(R.color.theme1button);*/
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(backgroundcolor, "#262626");

            editor.commit();


        }
        if(language_data.equals("English")){
            language_value="1";
            title_shop.setText("Shop Essentials");
        }else {
            language_value="2";
            title_shop.setText("Shop Essentials");
        }

        if (colorcodes.equals("#FFFFFFFF")) {
            title_shop.setTextColor(Color.BLACK);
            mycart_text.setTextColor(Color.BLACK);

        } else {
            title_shop.setTextColor(Color.WHITE);
            mycart_text.setTextColor(Color.WHITE);
        }
        String simplycity_title = "fonts/playfairDisplayRegular.ttf";
        Typeface tf_pala = Typeface.createFromAsset(getActivity().getAssets(), simplycity_title);
        if (fontname.equals("playfair")) {
            title_shop.setTypeface(tf_pala);
            mycart_text.setTypeface(tf_pala);
            title_shop.setTextSize(24);
            mycart_text.setTextSize(24);


        } else {
            Typeface sanf = Typeface.createFromAsset(getActivity().getAssets(), Fonts.sanfranciscobold);
            title_shop.setTypeface(sanf);
            mycart_text.setTypeface(sanf);
            title_shop.setTextSize(20);
            mycart_text.setTextSize(17);


        }

        pdialog = new ProgressDialog(getActivity());
        pdialog.show();
        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_shop);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_shop);
//RecyclerLayouts();

mycart_text.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid = sharedpreferences.getString(MYUSERID, "");
            myprofileid = myprofileid.replaceAll("\\D+","");
        }
        if(myprofileid!=null) {
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            MyCart frag;
            frag = new MyCart();
            frag.show(ft, "txn_tag");
        }else {
            Intent signin=new Intent(getActivity(),SigninpageActivity.class);
            signin.putExtra("ACTIVITY","EXP");
            startActivity(signin);

        }
    }
});


        shopAdapter = new IndexAdapter(getActivity(), shopDataList);
        recyclerView.setAdapter(shopAdapter);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                shopDataList.clear();

                shopAdapter.Listitem();
                shopAdapter.notifyDataSetChanged();
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

        return view;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach()  {
        super.onDetach();
    }
    private void getData() {
        // requestQueue.add(getDataFromTheServer(requestCount));


        //    Servicerequest servicerequest = new Servicerequest(getActivity());
        if (myprofileid != null) {
            shopDataList = servicerequest.index(language_value, "index", String.valueOf(requestCount), myprofileid, "", getActivity());

        } else if (search_value != null) {
            shopDataList = servicerequest.index(language_value, "index", String.valueOf(requestCount), "", search_value, getActivity());


        } else if (myprofileid != null && search_value != null) {
            shopDataList = servicerequest.index(language_value, "index", String.valueOf(requestCount), myprofileid, search_value, getActivity());

        } else {

            shopDataList = servicerequest.index(language_value, "index", String.valueOf(requestCount), "", "", getActivity());
        }

        pdialog.dismiss();
        requestCount++;
        shopAdapter.notifyDataSetChanged();


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
    public void Send(List<IndexProductModel> listdata) {


        datalist=listdata;
        shopAdapter.data(shopDataList);
        for(int i=0;i<listdata.size();i++){
            IndexProductModel model=listdata.get(i);

          Log.e("Response","cartcount"+String.valueOf(model.getCart_count()).toString());
            mycart_count_text.setText(String.valueOf(model.getCart_count()).toString());

        }




    }

    @Override
    public void searchdata(List<IndexProductModel> listsearch) {

        datalist=listsearch;
        shopAdapter.data(shopDataList);


    }
    @Override
public void RecyclerLayouts(String search_values){
    if(search_values.equals("")|| search_values==null){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);

        recyclerView.setLayoutManager(gridLayoutManager);
    }else {
        RecyclerView.LayoutManager gridLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(gridLayoutManager);

    }
}

}

