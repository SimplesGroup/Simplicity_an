package simplicity_an.simplicity_an.Explorenew;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import simplicity_an.simplicity_an.R;
import simplicity_an.simplicity_an.Utils.Fonts;

public class MyWishList extends DialogFragment implements WishListInterface {
    SharedPreferences sharedpreferences;



    public static final String mypreference = "mypref";
    public static final String MYUSERID= "myprofileid";
    public static final String backgroundcolor = "color";
    ProgressDialog pdialog;
    public static final String USERNAME= "myprofilename";
    public static final String USERIMAGE= "myprofileimage";

    String fontname,myprofileid,colorcodes;

    private TextView wishlist_titile_text,gotocart_text;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    private RelativeLayout main_complist_layout;
    private int requestCount = 1;
    private WishListAdapter wishListAdapter;
    private List<IndexProductModel> mywishlist=new ArrayList<>();
    private TextView textView_Noresult;

 private    WishListPresenter wishListPresenter;
    private TextView total_price_text,back_text,checkout_text;
    private LinearLayout cartlayout;
    public MyWishList() {

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
        View root = inflater.inflate(R.layout.explore_wishlist_frag, container, false);
        sharedpreferences =  getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        fontname=sharedpreferences.getString(Fonts.FONT,"");
        colorcodes=sharedpreferences.getString(backgroundcolor,"");
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");
            myprofileid = myprofileid.replaceAll("\\D+","");
        }
        wishListPresenter=new WishListPresenterImplementation(this);
        main_complist_layout = (RelativeLayout)root. findViewById(R.id.wishlist_layouts);
        cartlayout=(LinearLayout)root. findViewById(R.id.cart_layout);
        wishlist_titile_text=(TextView)root.findViewById(R.id.mywishlist_title);
        total_price_text=(TextView)root.findViewById(R.id.total_wishlist_price_shop);
        back_text=(TextView)root.findViewById(R.id.wishlist_back);
        checkout_text=(TextView)root.findViewById(R.id.total_wishlist_checkout) ;
        textView_Noresult=(TextView)root.findViewById(R.id.text_noresultfound_wishlist) ;
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
                cartlayout.setBackgroundColor(getActivity().getResources().getColor(R.color.themedark));
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
            cartlayout.setBackgroundColor(getActivity().getResources().getColor(R.color.themedark));
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(backgroundcolor, "#262626");

            editor.commit();


        }
        Log.e("Response",fontname+",,"+colorcodes);
        if (colorcodes.equals("#262626")) {
            Log.e("Response",colorcodes+"theme");
            wishlist_titile_text.setTextColor(Color.WHITE);

            textView_Noresult.setTextColor(Color.WHITE);
            total_price_text.setTextColor(Color.WHITE);
            back_text.setTextColor(Color.WHITE);
            checkout_text.setTextColor(Color.WHITE);


        } else {
            Log.e("Response",colorcodes+"theme");

            wishlist_titile_text.setTextColor(Color.BLACK);

            textView_Noresult.setTextColor(Color.BLACK);
            total_price_text.setTextColor(Color.WHITE);
            back_text.setTextColor(Color.WHITE);
            checkout_text.setTextColor(Color.WHITE);
        }
        wishlist_titile_text.setText("Wish List");
        back_text.setText("Back");
        checkout_text.setText("Checkout");

        String simplycity_title = "fonts/playfairDisplayRegular.ttf";
        Typeface tf_pala = Typeface.createFromAsset(getActivity().getAssets(), simplycity_title);
        if (fontname.equals("playfair")) {
            wishlist_titile_text.setTypeface(tf_pala);
            back_text.setTypeface(tf_pala);
            total_price_text.setTypeface(tf_pala);
            textView_Noresult.setTypeface(tf_pala);
            checkout_text.setTypeface(tf_pala);
            wishlist_titile_text.setTextSize(24);
            textView_Noresult.setTextSize(22);
            total_price_text.setTextSize(19);
            back_text.setTextSize(19);
            checkout_text.setTextSize(19);
        } else {
            Typeface sanf = Typeface.createFromAsset(getActivity().getAssets(), Fonts.sanfranciscobold);
            wishlist_titile_text.setTypeface(sanf);
            back_text.setTypeface(sanf);
            total_price_text.setTypeface(sanf);
            textView_Noresult.setTypeface(sanf);
            checkout_text.setTypeface(sanf);
            wishlist_titile_text.setTextSize(23);
            textView_Noresult.setTextSize(16);
            total_price_text.setTextSize(15);
            back_text.setTextSize(15);
            checkout_text.setTextSize(15);
        }

        pdialog = new ProgressDialog(getActivity());
        pdialog.show();
        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        recyclerView = (RecyclerView)root.findViewById(R.id.recyclerview_wishlist);
        swipeRefreshLayout = (SwipeRefreshLayout)root. findViewById(R.id.swipe_mywishlist);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        wishListAdapter= new WishListAdapter(getActivity(), mywishlist);
        recyclerView.setAdapter(wishListAdapter);

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                mywishlist.clear();
                wishListAdapter.Listitem();
                wishListAdapter.notifyDataSetChanged();

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

        back_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyWishList.this.dismiss();
            }
        });

        getData();
        return root;
    }
    private void getData() {


        mywishlist=   wishListPresenter.MyWishList(getActivity(),"wishlist","1",myprofileid,"");
        pdialog.dismiss();
        requestCount++;
       // myCartAdapter.notifyDataSetChanged();


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
    public void mywishlistresult(List<IndexProductModel> wishlistresult) {
Log.e("Response","wishdialog"+wishlistresult.size());

        Log.e("Response",wishlistresult.toString());
        if(wishlistresult.size()==0){
            recyclerView.setVisibility(View.GONE);
            textView_Noresult.setVisibility(View.VISIBLE);
        }else {
            wishListAdapter.data(wishlistresult);
            textView_Noresult.setVisibility(View.GONE);

        }
    }
}
