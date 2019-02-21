package simplicity_an.simplicity_an.Explorenew;



import android.app.Dialog;
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
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import simplicity_an.simplicity_an.R;
import simplicity_an.simplicity_an.Utils.Fonts;

public class MyCart extends DialogFragment implements CartInterface {
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID= "myprofileid";
    public static final String backgroundcolor = "color";
    ProgressDialog pdialog;
    public static final String USERNAME= "myprofilename";
    public static final String USERIMAGE= "myprofileimage";

    String fontname,myprofileid,colorcodes;

    private CartViewPresenter cartViewPresenter;
private CartShippingPresenter cartShippingPresenter;
    private TextView mycart_titile_text,total_price_text,addmore_product_text;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
private RelativeLayout main_complist_layout;
    private int requestCount = 1;
    private MyCartAdapter myCartAdapter;
    private List<IndexProductModel>mycartlist=new ArrayList<>();
    private TextView textView_Noresult;
    public MyCart() {

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(android.support.v4.app.DialogFragment.STYLE_NORMAL, R.style.MY_DIALOG);
    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();
        if (d != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            d.getWindow().setLayout(width, height);
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.explore_mycart_view_frag, container, false);
        sharedpreferences =  getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        fontname=sharedpreferences.getString(Fonts.FONT,"");
        colorcodes=sharedpreferences.getString(backgroundcolor,"");
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");
            myprofileid = myprofileid.replaceAll("\\D+","");
        }
cartViewPresenter=new CartServiceRequest(this);

        main_complist_layout = (RelativeLayout)root. findViewById(R.id.shop_layouts);
       mycart_titile_text=(TextView)root.findViewById(R.id.mycart_title_shop);
        total_price_text=(TextView)root.findViewById(R.id.total_cartcount_price_shop);
        addmore_product_text=(TextView)root.findViewById(R.id.add_more_products_shop);
        textView_Noresult=(TextView)root.findViewById(R.id.text_noresultfound_shop) ;
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
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(backgroundcolor, "#262626");

            editor.commit();


        }
        Log.e("Response",fontname+",,"+colorcodes);


        if (colorcodes.equals("#262626")) {
            Log.e("Response",colorcodes+"theme");
            mycart_titile_text.setTextColor(Color.WHITE);
            addmore_product_text.setTextColor(Color.WHITE);
            total_price_text.setTextColor(Color.WHITE);
            textView_Noresult.setTextColor(Color.WHITE);



        } else {
            Log.e("Response",colorcodes+"theme");

            mycart_titile_text.setTextColor(Color.BLACK);
            addmore_product_text.setTextColor(Color.BLACK);
            total_price_text.setTextColor(Color.BLACK);
            textView_Noresult.setTextColor(Color.BLACK);
        }

        addmore_product_text.setText("Add more Products");

        String simplycity_title = "fonts/playfairDisplayRegular.ttf";
        Typeface tf_pala = Typeface.createFromAsset(getActivity().getAssets(), simplycity_title);
        if (fontname.equals("playfair")) {
            mycart_titile_text.setTypeface(tf_pala);
            addmore_product_text.setTypeface(tf_pala);
            total_price_text.setTypeface(tf_pala);
            textView_Noresult.setTypeface(tf_pala);
        } else {
            Typeface sanf = Typeface.createFromAsset(getActivity().getAssets(), Fonts.sanfranciscobold);
          mycart_titile_text.setTypeface(sanf);
            addmore_product_text.setTypeface(sanf);
            total_price_text.setTypeface(sanf);
            textView_Noresult.setTypeface(sanf);
        }
        pdialog = new ProgressDialog(getActivity());
        pdialog.show();
        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        recyclerView = (RecyclerView)root.findViewById(R.id.recyclerview_shops);
        swipeRefreshLayout = (SwipeRefreshLayout)root. findViewById(R.id.swipe_shop_mycart);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        myCartAdapter= new MyCartAdapter(getActivity(), mycartlist);
        recyclerView.setAdapter(myCartAdapter);

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                mycartlist.clear();
                myCartAdapter.Listitem();
                myCartAdapter.notifyDataSetChanged();

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

        addmore_product_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ship=new Intent(getActivity(),ShippingAddress.class);
                startActivity(ship);
            }
        });

        getData();
        return root;
    }
    private void getData() {

       /* if (myprofileid != null) {

            servicerequest.getCompanylist("1","company_list",String.valueOf(requestCount),myprofileid,"",category_id,getApplicationContext());

        } else if (search_value != null) {

            servicerequest.getCompanylist("1","company_list",String.valueOf(requestCount),"",search_value,category_id,getApplicationContext());

        } else if (myprofileid != null && search_value != null) {

            servicerequest.getCompanylist("1","company_list",String.valueOf(requestCount),myprofileid,search_value,category_id,getApplicationContext());
        } else {

            servicerequest.getCompanylist("1","company_list",String.valueOf(requestCount),"","",category_id,getApplicationContext());

        }*/
     mycartlist=   cartViewPresenter.getMycart(getActivity(),"1","cartlist",myprofileid,"","","");
        pdialog.dismiss();
        requestCount++;
        myCartAdapter.notifyDataSetChanged();


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

    @Override
    public void myCartresult(List<IndexProductModel> mycartlistss) {


        Log.e("Response","start");
        Log.e("Response",mycartlistss.toString());
        if(mycartlistss.size()==0){
            recyclerView.setVisibility(View.GONE);
           textView_Noresult.setVisibility(View.VISIBLE);
        }else {
            IndexProductModel model=mycartlistss.get(0);

            total_price_text.setText(model.getMycarttotalitem()+" Product Total Price -"+"Rs."+model.getMycart_netprice());
            myCartAdapter.data(mycartlistss);
            textView_Noresult.setVisibility(View.GONE);

        }
    }
}
