package simplicity_an.simplicity_an.Explorenew;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import simplicity_an.simplicity_an.R;
import simplicity_an.simplicity_an.Utils.Fonts;

public class AddNewshippingAddress extends AppCompatActivity implements Cartaddressinterface {
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

    ProgressDialog pdialog;
    private CartShippingPresenter cartShippingPresenter;
    TextView title_textview;
    EditText name_edit,phone_edit,email_edit,address_one_edit,address_two_edit,location_edit,pincode_edit,landmark_edit,state_edit;
    Button back_button,save_button;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.explore_addnewaddress_activity);
        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");
            myprofileid = myprofileid.replaceAll("\\D+","");
        }
        colorcodes=sharedpreferences.getString(backgroundcolor,"");
        fontname=sharedpreferences.getString(Fonts.FONT,"");
        main_cartaddress_layout=(RelativeLayout)findViewById(R.id.explore_add_new_address);
        cartShippingPresenter=new CartAddresspresenterImpl(this);

        if (colorcodes != null) {
            if (colorcodes.equals("#FFFFFFFF")) {
                int[] colors = {Color.parseColor(colorcodes), Color.parseColor("#FFFFFFFF")};

                GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        colors);
                gd.setCornerRadius(0f);

                main_cartaddress_layout.setBackgroundDrawable(gd);


            } else {
                int[] colors = {Color.parseColor("#262626"), Color.parseColor("#FF000000")};

                GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        colors);
                gd.setCornerRadius(0f);

                main_cartaddress_layout.setBackgroundDrawable(gd);
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

            /*fabplus.setBackgroundResource(R.color.theme1button);
            fabinnerplus.setBackgroundResource(R.color.theme1button);
            fabsearch.setBackgroundResource(R.color.theme1button);*/
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(backgroundcolor, "#262626");

            editor.commit();


        }




        title_textview=(TextView)findViewById(R.id.city_title);

        title_textview.setText("Update Address");
        name_edit=(EditText)findViewById(R.id.username);
       // name_edit.setText(my_profilename);
        location_edit=(EditText)findViewById(R.id.location);
        phone_edit=(EditText)findViewById(R.id.phone);
        email_edit=(EditText)findViewById(R.id.email);
        pincode_edit=(EditText)findViewById(R.id.pincode);
        address_one_edit=(EditText)findViewById(R.id.addresslineone);
        address_two_edit=(EditText)findViewById(R.id.addresslinetwo);
        landmark_edit=(EditText)findViewById(R.id.lanmark);
        state_edit=(EditText)findViewById(R.id.state);
        back_button=(Button)findViewById(R.id.back_button_explore);
        save_button=(Button)findViewById(R.id.save_button_explore);


        pdialog = new ProgressDialog(this);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name=name_edit.getText().toString();
                final String phone=phone_edit.getText().toString();
                final String address1=address_one_edit.getText().toString();
                final String address2=address_two_edit.getText().toString();
                final String locations=location_edit.getText().toString();
                final String pincodes=pincode_edit.getText().toString();
                final String landmark=landmark_edit.getText().toString();
                final String email=email_edit.getText().toString();
                final String state=state_edit.getText().toString();

                final String address=address1+address2;

                pdialog.show();
                pdialog.setContentView(R.layout.custom_progressdialog);
                pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                cartShippingPresenter.Addressupdateornew(getApplicationContext(),"shipping_address",myprofileid,name,phone,email,address,locations,pincodes,landmark,state,"");
                dissmissDialog();
            }
        });


        if (colorcodes.equals("#FFFFFFFF")) {
            title_textview.setTextColor(Color.BLACK);
            name_edit.setTextColor(Color.BLACK);
            phone_edit.setTextColor(Color.BLACK);
            email_edit.setTextColor(Color.BLACK);
            address_one_edit.setTextColor(Color.BLACK);
            address_two_edit.setTextColor(Color.BLACK);
            location_edit.setTextColor(Color.BLACK);
            landmark_edit.setTextColor(Color.BLACK);
            state_edit.setTextColor(Color.BLACK);
            back_button.setTextColor(Color.BLACK);
            save_button.setTextColor(Color.BLACK);
        } else {
            title_textview.setTextColor(Color.WHITE);
            name_edit.setTextColor(Color.WHITE);
            phone_edit.setTextColor(Color.WHITE);
            email_edit.setTextColor(Color.WHITE);
            address_one_edit.setTextColor(Color.WHITE);
            address_two_edit.setTextColor(Color.WHITE);
            location_edit.setTextColor(Color.WHITE);
            landmark_edit.setTextColor(Color.WHITE);
            state_edit.setTextColor(Color.WHITE);
            back_button.setTextColor(Color.WHITE);
            save_button.setTextColor(Color.WHITE);
        }
        String simplycity_title = "fonts/playfairDisplayRegular.ttf";
        Typeface tf_pala = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title);
        if (fontname.equals("playfair")) {
            back_button.setTypeface(tf_pala);
            save_button.setTypeface(tf_pala);
            title_textview.setTypeface(tf_pala);
            name_edit.setTypeface(tf_pala);
            phone_edit.setTypeface(tf_pala);
            email_edit.setTypeface(tf_pala);
            address_one_edit.setTypeface(tf_pala);
            address_two_edit.setTypeface(tf_pala);
            location_edit.setTypeface(tf_pala);
            landmark_edit.setTypeface(tf_pala);
            state_edit.setTypeface(tf_pala);

            title_textview.setTextSize(24);

        } else {
            Typeface sanf = Typeface.createFromAsset(getApplicationContext().getAssets(), Fonts.sanfranciscobold);

            back_button.setTypeface(sanf);
            save_button.setTypeface(sanf);
            title_textview.setTypeface(sanf);
            name_edit.setTypeface(sanf);
            phone_edit.setTypeface(sanf);
            email_edit.setTypeface(sanf);
            address_one_edit.setTypeface(sanf);
            address_two_edit.setTypeface(sanf);
            location_edit.setTypeface(sanf);
            landmark_edit.setTypeface(sanf);
            state_edit.setTypeface(sanf);

            title_textview.setTextSize(23);
        }

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
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void Shippingadress(List<ShippingModel> shippingaddresslist) {

    }
}
