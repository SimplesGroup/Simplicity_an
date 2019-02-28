package simplicity_an.simplicity_an.Explorenew;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import simplicity_an.simplicity_an.R;
import simplicity_an.simplicity_an.Utils.Fonts;

public class CartPaymentPage extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID= "myprofileid";
    public static final String backgroundcolor = "color";
    ProgressDialog pdialog;
    public static final String USERNAME= "myprofilename";
    public static final String USERIMAGE= "myprofileimage";

    String fontname,myprofileid,colorcodes;

    private RelativeLayout main_layout;
    ProgressDialog pDialog;

    TextView title_textview,back_tocart_textview,deliverytype_textview,totalitems_textview,deliverycharges_textview,place_order_textview;
    TextView total_textview,totalitems_count_textview,delivery_ount_textview,total_pricevalue_textview,payment_title,payment_gateway_textview;
    TextView select_payment_option_title;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.explore_select_payment_activity);

        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        fontname=sharedpreferences.getString(Fonts.FONT,"");
        colorcodes=sharedpreferences.getString(backgroundcolor,"");
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");
            myprofileid = myprofileid.replaceAll("\\D+","");
        }

        main_layout=(RelativeLayout)findViewById(R.id.main_layout_explore);

        final Intent get=getIntent();
        String price=get.getStringExtra("TOTALCOST");

        if (colorcodes != null) {
            if (colorcodes.equals("#FFFFFFFF")) {
                int[] colors = {Color.parseColor(colorcodes), Color.parseColor("#FFFFFFFF")};

                GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        colors);
                gd.setCornerRadius(0f);

                main_layout.setBackgroundDrawable(gd);


            } else {
                int[] colors = {Color.parseColor("#262626"), Color.parseColor("#FF000000")};

                GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        colors);
                gd.setCornerRadius(0f);

                main_layout.setBackgroundDrawable(gd);
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

            main_layout.setBackgroundDrawable(gd);

            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(backgroundcolor, "#262626");

            editor.commit();


        }

        title_textview=(TextView)findViewById(R.id.city_title);

        title_textview.setText("Shipping & Delivery");
        deliverytype_textview=(TextView)findViewById(R.id.explore_user_delivery_type);
        deliverytype_textview.setText("Standard Delivery");

        back_tocart_textview=(TextView)findViewById(R.id.your_delivery_backtocart);
        totalitems_textview=(TextView)findViewById(R.id.explore_user_delivery_totalitems);
        deliverycharges_textview=(TextView)findViewById(R.id.explore_user_delivery_address_Delivarycharges);
        totalitems_count_textview=(TextView)findViewById(R.id.explore_user_delivery_totalitems_value);
        delivery_ount_textview=(TextView)findViewById(R.id.explore_user_delivery_address_Delivarycharges_value);
        total_textview=(TextView) findViewById(R.id.explore_user_delivery_nettotal);
        total_pricevalue_textview=(TextView) findViewById(R.id.explore_user_delivery_nettotal_value);
        payment_title=(TextView) findViewById(R.id.explore_user_delivery_payment_title);
        payment_gateway_textview=(TextView)findViewById(R.id.explore_user_delivery_payment_gateway) ;
        place_order_textview=(TextView)findViewById(R.id.your_delivery_placeorder) ;
        select_payment_option_title=(TextView)findViewById(R.id.explore_user_delivery_payment_option) ;


        if (colorcodes.equals("#262626")) {
            Log.e("Response",colorcodes+"theme");
            title_textview.setTextColor(Color.WHITE);
            deliverytype_textview.setTextColor(Color.WHITE);
            back_tocart_textview.setTextColor(Color.WHITE);
            totalitems_textview.setTextColor(Color.WHITE);
            deliverycharges_textview.setTextColor(Color.WHITE);
            totalitems_count_textview.setTextColor(Color.WHITE);
            delivery_ount_textview.setTextColor(Color.WHITE);
            total_textview.setTextColor(Color.WHITE);
            total_pricevalue_textview.setTextColor(Color.WHITE);
            payment_title.setTextColor(Color.WHITE);
            payment_gateway_textview.setTextColor(Color.WHITE);
            place_order_textview.setTextColor(Color.WHITE);
            select_payment_option_title.setTextColor(Color.WHITE);


        } else {
            Log.e("Response",colorcodes+"theme");

            title_textview.setTextColor(Color.BLACK);
            deliverytype_textview.setTextColor(Color.BLACK);
            back_tocart_textview.setTextColor(Color.BLACK);
            totalitems_textview.setTextColor(Color.BLACK);
            deliverycharges_textview.setTextColor(Color.BLACK);
            totalitems_count_textview.setTextColor(Color.BLACK);
            delivery_ount_textview.setTextColor(Color.BLACK);
            total_textview.setTextColor(Color.BLACK);
            total_pricevalue_textview.setTextColor(Color.BLACK);
            payment_title.setTextColor(Color.BLACK);
            payment_gateway_textview.setTextColor(Color.BLACK);
            place_order_textview.setTextColor(Color.BLACK);
            select_payment_option_title.setTextColor(Color.BLACK);
        }

        back_tocart_textview.setText("BACK TO CART");
        deliverycharges_textview.setText("Delivery charges");
        total_textview.setText("Total");
        payment_title.setText("Payment");
        payment_gateway_textview.setText("Pay with CCAVENUE");
        place_order_textview.setText("Place Order");
        select_payment_option_title.setText("Select Payment Option");
        total_pricevalue_textview.setText(price);


        String simplycity_title = "fonts/playfairDisplayRegular.ttf";
        Typeface tf_pala = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title);
        if (fontname.equals("playfair")) {
            title_textview.setTypeface(tf_pala);
            deliverytype_textview.setTypeface(tf_pala);
            back_tocart_textview.setTypeface(tf_pala);
            totalitems_textview.setTypeface(tf_pala);
            deliverycharges_textview.setTypeface(tf_pala);
            totalitems_count_textview.setTypeface(tf_pala);
            delivery_ount_textview.setTypeface(tf_pala);
            total_textview.setTypeface(tf_pala);
            total_pricevalue_textview.setTypeface(tf_pala);
            payment_title.setTypeface(tf_pala);
            payment_gateway_textview.setTypeface(tf_pala);
            place_order_textview.setTypeface(tf_pala);
            select_payment_option_title.setTypeface(tf_pala);



            title_textview.setTextSize(24);
            deliverytype_textview.setTextSize(19);
            back_tocart_textview.setTextSize(19);
            totalitems_textview.setTextSize(19);
            deliverycharges_textview.setTextSize(19);
            totalitems_count_textview.setTextSize(19);
            delivery_ount_textview.setTextSize(19);
            total_textview.setTextSize(19);
            total_pricevalue_textview.setTextSize(19);
            payment_title.setTextSize(19);
            payment_gateway_textview.setTextSize(19);
            place_order_textview.setTextSize(19);
            select_payment_option_title.setTextSize(19);


        } else {
            Typeface sanf = Typeface.createFromAsset(getApplicationContext().getAssets(), Fonts.sanfranciscobold);
            title_textview.setTypeface(sanf);
            deliverytype_textview.setTypeface(sanf);
            back_tocart_textview.setTypeface(sanf);
            totalitems_textview.setTypeface(sanf);
            deliverycharges_textview.setTypeface(sanf);
            totalitems_count_textview.setTypeface(sanf);
            delivery_ount_textview.setTypeface(sanf);
            total_textview.setTypeface(sanf);
            total_pricevalue_textview.setTypeface(sanf);
            payment_title.setTypeface(sanf);
            payment_gateway_textview.setTypeface(sanf);
            place_order_textview.setTypeface(sanf);
            select_payment_option_title.setTypeface(sanf);

            title_textview.setTextSize(23);
            deliverytype_textview.setTextSize(15);
            back_tocart_textview.setTextSize(15);
            totalitems_textview.setTextSize(15);
            deliverycharges_textview.setTextSize(15);
            totalitems_count_textview.setTextSize(15);
            delivery_ount_textview.setTextSize(15);
            total_textview.setTextSize(15);
            total_pricevalue_textview.setTextSize(15);
            payment_title.setTextSize(15);
            payment_gateway_textview.setTextSize(15);
            place_order_textview.setTextSize(15);
            select_payment_option_title.setTextSize(15);
        }



        back_tocart_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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


}
