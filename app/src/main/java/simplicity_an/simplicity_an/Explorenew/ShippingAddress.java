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
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import simplicity_an.simplicity_an.R;
import simplicity_an.simplicity_an.Utils.Fonts;

public class ShippingAddress  extends AppCompatActivity implements Cartaddressinterface {
    SharedPreferences sharedpreferences;
    public static final String backgroundcolor = "color";
    String activity,contentid,colorcodes;
    public static final String mypreference = "mypref";
    String myprofileid,cartcounts;
    public static final String MYUSERID= "myprofileid";
    public static final String GcmId = "gcmid";
    public static final String Language = "lamguage";
    String playerid,fontname;
    RelativeLayout main_cartaddress_layout;

    private TextView textView_Noresult,shippingtitle_text,your_delivery_location_text,add_new_location_text;
    private TextView addmore_products_textview,back_textview,totalcost_textview,continue_textview;
    ProgressDialog pdialog;
    private RecyclerView recyclerView;
private CartShippingPresenter cartShippingPresenter;
    private int requestCount = 1;
    private List<ShippingModel>shippingaddressarraylist=new ArrayList<>();
    private ShippingAddressAdapter shippingAddressAdapter;

    private LinearLayout cartlayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.explore_cart_address_list_activity);
        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");
            myprofileid = myprofileid.replaceAll("\\D+","");
        }
        colorcodes=sharedpreferences.getString(backgroundcolor,"");
        fontname=sharedpreferences.getString(Fonts.FONT,"");
        main_cartaddress_layout=(RelativeLayout)findViewById(R.id.main_layout_explore_cartaddress);
        cartlayout=(LinearLayout) findViewById(R.id.cart_layout);
cartShippingPresenter=new CartAddresspresenterImpl(this);

Intent get=getIntent();
final String totalprice=get.getStringExtra("TOTALCOST");


        if (colorcodes != null) {
            if (colorcodes.equals("#FFFFFFFF")) {
                int[] colors = {Color.parseColor(colorcodes), Color.parseColor("#FFFFFFFF")};

                GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        colors);
                gd.setCornerRadius(0f);
                main_cartaddress_layout.setBackgroundDrawable(gd);
                cartlayout.setBackgroundColor(Color.BLACK);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(backgroundcolor, "#FFFFFFFF");
                editor.commit();


            } else {
                int[] colors = {Color.parseColor("#262626"), Color.parseColor("#FF000000")};

                GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        colors);
                gd.setCornerRadius(0f);

                main_cartaddress_layout.setBackgroundDrawable(gd);
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

            main_cartaddress_layout.setBackgroundDrawable(gd);
            cartlayout.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.themedark));
            /*fabplus.setBackgroundResource(R.color.theme1button);
            fabinnerplus.setBackgroundResource(R.color.theme1button);
            fabsearch.setBackgroundResource(R.color.theme1button);*/
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(backgroundcolor, "#262626");

            editor.commit();


        }

        textView_Noresult=(TextView)findViewById(R.id.text_noresultfound_cartaddress) ;
        shippingtitle_text=(TextView)findViewById(R.id.cart_title) ;
        your_delivery_location_text=(TextView)findViewById(R.id.your_delivery_location_title);
        add_new_location_text=(TextView)findViewById(R.id.your_delivery_location_Addnew);
        totalcost_textview=(TextView)findViewById(R.id.total_address_price_shop);
        continue_textview=(TextView)findViewById(R.id.total_address_checkout);
        back_textview=(TextView)findViewById(R.id.total_address_back) ;
        shippingtitle_text.setText("Shipping Address");
        your_delivery_location_text.setText("Your Delivery Location");

        add_new_location_text.setText("ADD NEW");
        textView_Noresult.setText("No Result Found");
        back_textview.setText("BACK");
        continue_textview.setText("CONTINUE");
        totalcost_textview.setText("Total : "+totalprice);
        textView_Noresult.setVisibility(View.GONE);
        if (colorcodes.equals("#FFFFFFFF")) {
            shippingtitle_text.setTextColor(Color.BLACK);
            textView_Noresult.setTextColor(Color.BLACK);
            your_delivery_location_text.setTextColor(Color.BLACK);
            add_new_location_text.setTextColor(Color.BLACK);

            back_textview.setTextColor(Color.WHITE);
            totalcost_textview.setTextColor(Color.WHITE);
            continue_textview.setTextColor(Color.WHITE);

        } else {
            shippingtitle_text.setTextColor(Color.WHITE);
            textView_Noresult.setTextColor(Color.WHITE);
            your_delivery_location_text.setTextColor(Color.WHITE);
            add_new_location_text.setTextColor(Color.WHITE);

            back_textview.setTextColor(Color.WHITE);
            totalcost_textview.setTextColor(Color.WHITE);
            continue_textview.setTextColor(Color.WHITE);
        }
        String simplycity_title = "fonts/playfairDisplayRegular.ttf";
        final Typeface tf_pala = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title);
        if (fontname.equals("playfair")) {
            shippingtitle_text.setTypeface(tf_pala);
            textView_Noresult.setTypeface(tf_pala);
            your_delivery_location_text.setTypeface(tf_pala);
            add_new_location_text.setTypeface(tf_pala);

            back_textview.setTypeface(tf_pala);
            totalcost_textview.setTypeface(tf_pala);
            continue_textview.setTypeface(tf_pala);

            shippingtitle_text.setTextSize(24);
            textView_Noresult.setTextSize(22);
            your_delivery_location_text.setTextSize(20);
            add_new_location_text.setTextSize(20);
            totalcost_textview.setTextSize(19);
            back_textview.setTextSize(19);
            continue_textview.setTextSize(19);

        } else {
            Typeface sanf = Typeface.createFromAsset(getApplicationContext().getAssets(), Fonts.sanfranciscobold);
            shippingtitle_text.setTypeface(sanf);
            textView_Noresult.setTypeface(sanf);
            your_delivery_location_text.setTypeface(sanf);
            add_new_location_text.setTypeface(sanf);

            back_textview.setTypeface(sanf);
            totalcost_textview.setTypeface(sanf);
            continue_textview.setTypeface(sanf);

            shippingtitle_text.setTextSize(23);
            textView_Noresult.setTextSize(16);
            your_delivery_location_text.setTextSize(15);
            add_new_location_text.setTextSize(15);
            totalcost_textview.setTextSize(15);
            back_textview.setTextSize(15);
            continue_textview.setTextSize(15);

        }

        pdialog = new ProgressDialog(this);
        pdialog.show();
        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        recyclerView = (RecyclerView)findViewById(R.id.expolre_cart_address_recylerview);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        shippingAddressAdapter= new ShippingAddressAdapter(getApplicationContext(), shippingaddressarraylist);
        recyclerView.setAdapter(shippingAddressAdapter);

        add_new_location_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add_address=new Intent(getApplicationContext(),AddNewshippingAddress.class);
                startActivity(add_address);
            }
        });

        back_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        continue_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



      boolean isdata=shippingAddressAdapter.isCheckeds();
      if(isdata==true){
          Log.e("Response","Checked");
          String address_ids=shippingAddressAdapter.AddressId();
          Log.e("Response","Checked"+address_ids);
          Intent in =new Intent(getApplicationContext(),CartPaymentPage.class);
          in.putExtra("TOTALCOST",totalprice);
          in.putExtra("ADDRESS_ID",address_ids);
          startActivity(in);

      }else {
          Toast.makeText(getApplicationContext(),"Select your delivery Address",Toast.LENGTH_SHORT).show();
      }


            }
        });

        getData();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void getData(){

        shippingaddressarraylist=   cartShippingPresenter.getAddress(getApplicationContext(),"shipping_address_list",myprofileid);
        pdialog.dismiss();
        shippingAddressAdapter.notifyDataSetChanged();

        requestCount++;
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
    public void Shippingadress(List<ShippingModel> shippingaddresslist) {
        Log.e("Response","start");
        Log.e("Response",shippingaddresslist.toString());
        shippingaddressarraylist=shippingaddresslist;
        if(shippingaddresslist.size()==0){
            recyclerView.setVisibility(View.GONE);
            textView_Noresult.setVisibility(View.VISIBLE);
        }else {

            shippingAddressAdapter.data(shippingaddresslist);
            textView_Noresult.setVisibility(View.GONE);

        }

    }
}
