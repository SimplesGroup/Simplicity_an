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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import simplicity_an.simplicity_an.Explorenew.PaymentGateway.Paymentactivity;
import simplicity_an.simplicity_an.R;
import simplicity_an.simplicity_an.Utils.Fonts;

public class CartPaymentPage extends AppCompatActivity implements PaymentInterface{
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

    TextView title_textview,back_tocart_textview,delivery_textview,total_product_items_textview,deliverycharges_textview,discount_textview;
    TextView total_textview,total_product_items_count_textview,discount_value_textview,total_pricevalue_textview,unique_textview;
    TextView unique_value_title,mycart_textview,mycart_count_textview,pay_textview,deliverytype_textview,price_textview,price_value_textview;
private RelativeLayout view_line_one,view_line_two;
    private LinearLayout cartlayout;

    private PaymentPresenter paymentPresenter;

    private String netpayment;
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
paymentPresenter=new PaymentPrensenterImpl(this);
        main_layout=(RelativeLayout)findViewById(R.id.main_layout_explore);
        cartlayout=(LinearLayout) findViewById(R.id.cart_layout);
view_line_one=(RelativeLayout) findViewById(R.id.line_separter_one_pay);
        view_line_two=(RelativeLayout) findViewById(R.id.line_separter_two_pay);
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
                cartlayout.setBackgroundColor(Color.BLACK);

            } else {
                int[] colors = {Color.parseColor("#262626"), Color.parseColor("#FF000000")};

                GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        colors);
                gd.setCornerRadius(0f);

                main_layout.setBackgroundDrawable(gd);
                cartlayout.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.themedark));
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
            cartlayout.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.themedark));
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(backgroundcolor, "#262626");

            editor.commit();


        }

        paymentPresenter.getMycartPayment(getApplicationContext(),"place_order_view",myprofileid);

        title_textview=(TextView)findViewById(R.id.city_title);
        title_textview.setText("Shipping & Delivery");
        deliverytype_textview=(TextView)findViewById(R.id.your_delivery_location_title);
        deliverytype_textview.setText("Standard Delivery");

        back_tocart_textview=(TextView)findViewById(R.id.back_textview);
        total_product_items_textview=(TextView)findViewById(R.id.explore_pay_products);
        deliverycharges_textview=(TextView)findViewById(R.id.explore_pay_deliverycharge_value);
        total_product_items_count_textview=(TextView)findViewById(R.id.explore_pay_products_value);
        delivery_textview=(TextView)findViewById(R.id.explore_pay_deliverycharge);
        total_textview=(TextView) findViewById(R.id.explore_user_delivery_nettotal);
        total_pricevalue_textview=(TextView) findViewById(R.id.explore_user_delivery_nettotal_value);
        price_textview=(TextView) findViewById(R.id.explore_pay_price);
        price_value_textview=(TextView) findViewById(R.id.explore_pay_price_value);
        discount_textview=(TextView)findViewById(R.id.explore_pay_discount) ;
        discount_value_textview=(TextView)findViewById(R.id.explore_pay_discount_value) ;
        unique_textview=(TextView)findViewById(R.id.explore_pay_unique) ;
        unique_value_title=(TextView)findViewById(R.id.explore_pay_unique_value) ;

        mycart_textview=(TextView)findViewById(R.id.title_shop_mycartcount_textview);
        mycart_count_textview=(TextView)findViewById(R.id.cart_main_count);
        pay_textview=(TextView)findViewById(R.id.pay_textview);


        if (colorcodes.equals("#262626")) {
            Log.e("Response",colorcodes+"theme");
            title_textview.setTextColor(Color.WHITE);
            deliverytype_textview.setTextColor(Color.WHITE);
            back_tocart_textview.setTextColor(Color.WHITE);
            total_product_items_textview.setTextColor(Color.WHITE);
            delivery_textview.setTextColor(Color.WHITE);
            deliverycharges_textview.setTextColor(Color.WHITE);
            total_product_items_count_textview.setTextColor(Color.WHITE);
            price_textview.setTextColor(Color.WHITE);
            price_value_textview.setTextColor(Color.WHITE);
            total_textview.setTextColor(Color.WHITE);
            total_pricevalue_textview.setTextColor(Color.WHITE);
            discount_textview.setTextColor(Color.WHITE);
            discount_value_textview.setTextColor(Color.WHITE);
            unique_textview.setTextColor(Color.WHITE);
            unique_value_title.setTextColor(Color.WHITE);
            mycart_textview.setTextColor(Color.WHITE);
            pay_textview.setTextColor(Color.WHITE);

            view_line_one.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.white));
            view_line_two.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.white));


        } else {
            Log.e("Response",colorcodes+"theme");

            title_textview.setTextColor(Color.BLACK);
            deliverytype_textview.setTextColor(Color.BLACK);

            total_product_items_textview.setTextColor(Color.BLACK);
            delivery_textview.setTextColor(Color.BLACK);
            deliverycharges_textview.setTextColor(Color.BLACK);
            total_product_items_count_textview.setTextColor(Color.BLACK);
            price_textview.setTextColor(Color.BLACK);
            price_value_textview.setTextColor(Color.BLACK);
            total_textview.setTextColor(Color.BLACK);
            total_pricevalue_textview.setTextColor(Color.BLACK);
            discount_textview.setTextColor(Color.BLACK);
            discount_value_textview.setTextColor(Color.BLACK);
            unique_textview.setTextColor(Color.BLACK);
            unique_value_title.setTextColor(Color.BLACK);
            mycart_textview.setTextColor(Color.WHITE);
            pay_textview.setTextColor(Color.WHITE);
            back_tocart_textview.setTextColor(Color.WHITE);
            view_line_one.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.Black));
            view_line_two.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.Black));


        }

        back_tocart_textview.setText("Back");
        delivery_textview.setText("Delivery charges");
        total_textview.setText("Net Price");
        unique_textview.setText("Unique Price");
        discount_textview.setText("Discount");
        price_textview.setText("Price");
        total_product_items_textview.setText("Products");


        String simplycity_title = "fonts/playfairDisplayRegular.ttf";
        Typeface tf_pala = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title);
        if (fontname.equals("playfair")) {
            title_textview.setTypeface(tf_pala);
            deliverytype_textview.setTypeface(tf_pala);
            back_tocart_textview.setTypeface(tf_pala);
            total_product_items_textview.setTypeface(tf_pala);
            delivery_textview.setTypeface(tf_pala);
            deliverycharges_textview.setTypeface(tf_pala);
            total_product_items_count_textview.setTypeface(tf_pala);
            price_textview.setTypeface(tf_pala);
            price_value_textview.setTypeface(tf_pala);
            total_textview.setTypeface(tf_pala);
            total_pricevalue_textview.setTypeface(tf_pala);
            discount_textview.setTypeface(tf_pala);
            discount_value_textview.setTypeface(tf_pala);
            unique_textview.setTypeface(tf_pala);
            unique_value_title.setTypeface(tf_pala);
            mycart_textview.setTypeface(tf_pala);
            pay_textview.setTypeface(tf_pala);




            title_textview.setTextSize(24);
            deliverytype_textview.setTextSize(19);
            back_tocart_textview.setTextSize(19);
            total_product_items_textview.setTextSize(19);
            delivery_textview.setTextSize(19);
            deliverycharges_textview.setTextSize(19);
            total_product_items_count_textview.setTextSize(19);
            price_textview.setTextSize(19);
            price_value_textview.setTextSize(19);
            total_textview.setTextSize(19);
            total_pricevalue_textview.setTextSize(19);
            discount_textview.setTextSize(19);
            discount_value_textview.setTextSize(19);
            unique_textview.setTextSize(19);
            unique_value_title.setTextSize(19);
            mycart_textview.setTextSize(19);
            pay_textview.setTextSize(19);


        } else {
            Typeface sanf = Typeface.createFromAsset(getApplicationContext().getAssets(), Fonts.sanfranciscobold);
            title_textview.setTypeface(sanf);
            deliverytype_textview.setTypeface(sanf);
            back_tocart_textview.setTypeface(sanf);
            total_product_items_textview.setTypeface(sanf);
            delivery_textview.setTypeface(sanf);
            deliverycharges_textview.setTypeface(sanf);
            total_product_items_count_textview.setTypeface(sanf);
            price_textview.setTypeface(sanf);
            price_value_textview.setTypeface(sanf);
            total_textview.setTypeface(sanf);
            total_pricevalue_textview.setTypeface(sanf);
            discount_textview.setTypeface(sanf);
            discount_value_textview.setTypeface(sanf);
            unique_textview.setTypeface(sanf);
            unique_value_title.setTypeface(sanf);
            mycart_textview.setTypeface(sanf);
            pay_textview.setTypeface(sanf);

            title_textview.setTextSize(23);
            deliverytype_textview.setTextSize(15);
            back_tocart_textview.setTextSize(15);
            total_product_items_textview.setTextSize(15);
            delivery_textview.setTextSize(15);
            deliverycharges_textview.setTextSize(15);
            total_product_items_count_textview.setTextSize(15);
            price_textview.setTextSize(15);
            price_value_textview.setTextSize(15);
            total_textview.setTextSize(15);
            total_pricevalue_textview.setTextSize(15);
            discount_textview.setTextSize(15);
            discount_value_textview.setTextSize(15);
            unique_textview.setTextSize(15);
            unique_value_title.setTextSize(15);
            mycart_textview.setTextSize(15);
            pay_textview.setTextSize(15);
        }



        back_tocart_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
pay_textview.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Intent in=new Intent(getApplicationContext(), Paymentactivity.class);
        in.putExtra("AMOUNT",netpayment);
        startActivity(in);

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


    @Override
    public void PaymentResult(List<PlaceorderModel> placeorderlist) {

Log.e("Response",placeorderlist.toString());

            for(int i=0;i<placeorderlist.size();i++){

                PlaceorderModel model=placeorderlist.get(i);
            mycart_count_textview.setText(String.valueOf(model.getTotal_cart_item()).toString());
            total_product_items_count_textview.setText(String.valueOf(model.getTotal_cart_item()).toString());
            price_value_textview.setText(String.valueOf(model.getPrice()).toString());
            discount_value_textview.setText(String.valueOf(model.getDiscount()).toString());
            unique_value_title.setText(String.valueOf(model.getUnique_price()).toString());
            deliverycharges_textview.setText(String.valueOf(model.getDelivery_charges()).toString());
            total_pricevalue_textview.setText(String.valueOf(model.getNet_price()).toString());
            netpayment=String.valueOf(model.getNet_price()).toString();

            }





    }
}
