package simplicity_an.simplicity_an.Explorenew;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import simplicity_an.simplicity_an.CustomVolleyRequest;
import simplicity_an.simplicity_an.R;
import simplicity_an.simplicity_an.SigninpageActivity;
import simplicity_an.simplicity_an.Utils.Fonts;

public class ProductDetail extends AppCompatActivity implements RequestInterface.Productdetail {
    SharedPreferences sharedpreferences;
    public static final String backgroundcolor = "color";
    String activity,contentid,colorcodes;
    public static final String mypreference = "mypref";
    String myprofileid,cartcounts;
    public static final String MYUSERID= "myprofileid";
    public static final String GcmId = "gcmid";
    public static final String Language = "lamguage";
    String playerid,fontname,language_data;
    RelativeLayout main_complist_layout;
    private int requestCount = 1;
RequestQueue requestQueue;
    ProgressDialog pdialog;
    private Servicerequest servicerequest;
    ProductdetailstoneAdapter productdetailstoneAdapter;
    TextView textView_price,textView_title_label,textView_description_label,textView_title,textView_description;

    TextView productdetails_jewel_label,productdetails_title_label,productdetails_desc_label,productdetails_wastage_label,productdetails_netweight_label,productdetails_materialprice_label;
    TextView productdetails_title,productdetails_desc,productdetails_wastage,productdetails_netweight,productdetails_materialprice;
    RecyclerView recyclerView_stone;
    List<IndexProductModel>productdetailslist;

    Button addtocart,whishlist;

    Spinner spinner_pricelist;
String maincatid,productid;
LinearLayout except_jewel_detail_layout,product_details_layout;
RelativeLayout productdetail_label_layout,stonedetails_layout;

NetworkImageView product_image;
ImageLoader mImageLoader;

String maincateegoryid,categoryid,companyid,productsid,qtyid;
String language_value;

    private LinearLayout cartlayout;
    private TextView back_text,cart_count_text,cartname_text,wishlist_text;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.explore_product_detail_activity);
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
        servicerequest=new Servicerequest(this);
        productdetailslist=new ArrayList<>();
        Intent get=getIntent();
        maincatid=get.getStringExtra("MAIN_CAT_ID");
        productid=get.getStringExtra("PRO_ID");


        cartlayout=(LinearLayout) findViewById(R.id.cart_layout);
        back_text=(TextView)findViewById(R.id.back_textview) ;
        cart_count_text=(TextView)findViewById(R.id.cart_main_count) ;
        cartname_text=(TextView) findViewById(R.id.title_shop_mycartcount_textview);
        wishlist_text=(TextView)findViewById(R.id.wishlist_textview) ;


        product_image=(NetworkImageView)findViewById(R.id.img_product_detail) ;
        spinner_pricelist=(Spinner)findViewById(R.id.spin_qty_list_detail);
        addtocart=(Button) findViewById(R.id.btn_addtocart);
        whishlist=(Button) findViewById(R.id.btn_addtovisitlist);
        except_jewel_detail_layout=(LinearLayout)findViewById(R.id.except_jewel_description) ;
        product_details_layout=(LinearLayout)findViewById(R.id.textView_product_details_jewel_layout) ;
        productdetail_label_layout=(RelativeLayout)findViewById(R.id.textView_product_details_jewel_layout_label) ;
        stonedetails_layout=(RelativeLayout)findViewById(R.id.recycler_product_stone_details_layout) ;


        textView_price=(TextView)findViewById(R.id.textView_price);
        textView_title_label=(TextView)findViewById(R.id.text_title_label);
        textView_title=(TextView)findViewById(R.id.text_title);
        textView_description_label=(TextView)findViewById(R.id.text_description_label);
        textView_description=(TextView)findViewById(R.id.text_description);
        productdetails_jewel_label=(TextView)findViewById(R.id.textView_product_details_jewel);

        productdetails_title_label=(TextView)findViewById(R.id.text_pro_title_label);
        productdetails_desc_label=(TextView)findViewById(R.id.text_pro_description_label);
        productdetails_wastage_label=(TextView)findViewById(R.id.text_pro_wastage_label);
        productdetails_netweight_label=(TextView)findViewById(R.id.text_pro_netweight_label);
        productdetails_materialprice_label=(TextView)findViewById(R.id.text_pro_materialprice_label);
        productdetails_title=(TextView)findViewById(R.id.text_pro_title);
        productdetails_desc=(TextView)findViewById(R.id.text_pro_description);
        productdetails_wastage=(TextView)findViewById(R.id.text_pro_wastage);
        productdetails_netweight=(TextView)findViewById(R.id.text_pro_netweight);
        productdetails_materialprice=(TextView)findViewById(R.id.text_pro_materialprice);

        recyclerView_stone=(RecyclerView)findViewById(R.id.recycler_product_stone_details);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);

        recyclerView_stone.setLayoutManager(layoutManager);

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


        if(language_data.equals("English")) {
language_value="1";
            addtocart.setText("ADD TO CART");
            whishlist.setText("WHISHLIST");
            back_text.setText("Back");
            cartname_text.setText("My Cart");
            wishlist_text.setText("Wish List");
        }else {
            language_value="2";
            addtocart.setText("கார்ட்டில் சேர்க்க");
            whishlist.setText("விருப்ப பட்டியிலில் சேர்க்க");
            back_text.setText("பின்செல்");
            cartname_text.setText("மை கார்ட்");

        }
        if (colorcodes.equals("#FFFFFFFF")) {

            textView_price.setTextColor(Color.BLACK);
            textView_title_label.setTextColor(Color.BLACK);
            textView_title.setTextColor(Color.BLACK);
            textView_description_label.setTextColor(Color.BLACK);
            textView_description.setTextColor(Color.BLACK);
            productdetails_jewel_label.setTextColor(Color.BLACK);

            productdetails_title_label.setTextColor(Color.BLACK);
            productdetails_desc_label.setTextColor(Color.BLACK);
            productdetails_wastage_label.setTextColor(Color.BLACK);
            productdetails_netweight_label.setTextColor(Color.BLACK);
            productdetails_materialprice_label.setTextColor(Color.BLACK);
            productdetails_title.setTextColor(Color.BLACK);
            productdetails_desc.setTextColor(Color.BLACK);
            productdetails_wastage.setTextColor(Color.BLACK);
            productdetails_netweight.setTextColor(Color.BLACK);
            productdetails_materialprice.setTextColor(Color.BLACK);
            addtocart.setTextColor(Color.BLACK);
            whishlist.setTextColor(Color.BLACK);

            back_text.setTextColor(Color.WHITE);
            cartname_text.setTextColor(Color.WHITE);
            wishlist_text.setTextColor(Color.WHITE);
        } else {
            textView_price.setTextColor(Color.WHITE);
            textView_title_label.setTextColor(Color.WHITE);
            textView_title.setTextColor(Color.WHITE);
            textView_description_label.setTextColor(Color.WHITE);
            textView_description.setTextColor(Color.WHITE);
            productdetails_jewel_label.setTextColor(Color.WHITE);

            productdetails_title_label.setTextColor(Color.WHITE);
            productdetails_desc_label.setTextColor(Color.WHITE);
            productdetails_wastage_label.setTextColor(Color.WHITE);
            productdetails_netweight_label.setTextColor(Color.WHITE);
            productdetails_materialprice_label.setTextColor(Color.WHITE);
            productdetails_title.setTextColor(Color.WHITE);
            productdetails_desc.setTextColor(Color.WHITE);
            productdetails_wastage.setTextColor(Color.WHITE);
            productdetails_netweight.setTextColor(Color.WHITE);
            productdetails_materialprice.setTextColor(Color.WHITE);
            addtocart.setTextColor(Color.WHITE);
            whishlist.setTextColor(Color.WHITE);

            back_text.setTextColor(Color.WHITE);
            cartname_text.setTextColor(Color.WHITE);
            wishlist_text.setTextColor(Color.WHITE);

        }
        String simplycity_title = "fonts/playfairDisplayRegular.ttf";
        Typeface tf_pala = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title);
        if (fontname.equals("playfair")) {

            textView_price.setTypeface(tf_pala);
            textView_title_label.setTypeface(tf_pala);
            textView_title.setTypeface(tf_pala);
            textView_description_label.setTypeface(tf_pala);
            textView_description.setTypeface(tf_pala);
            productdetails_jewel_label.setTypeface(tf_pala);

            productdetails_title_label.setTypeface(tf_pala);
            productdetails_desc_label.setTypeface(tf_pala);
            productdetails_wastage_label.setTypeface(tf_pala);
            productdetails_netweight_label.setTypeface(tf_pala);
            productdetails_materialprice_label.setTypeface(tf_pala);
            productdetails_title.setTypeface(tf_pala);
            productdetails_desc.setTypeface(tf_pala);
            productdetails_wastage.setTypeface(tf_pala);
            productdetails_netweight.setTypeface(tf_pala);
            productdetails_materialprice.setTypeface(tf_pala);
            addtocart.setTypeface(tf_pala);
            whishlist.setTypeface(tf_pala);

            back_text.setTypeface(tf_pala);
            cartname_text.setTypeface(tf_pala);
            wishlist_text.setTypeface(tf_pala);



            textView_price.setTextSize(21);
            textView_title_label.setTextSize(21);
            textView_title.setTextSize(21);
            textView_description_label.setTextSize(21);
            textView_description.setTextSize(21);
            productdetails_jewel_label.setTextSize(21);

            productdetails_title_label.setTextSize(21);
            productdetails_desc_label.setTextSize(21);
            productdetails_wastage_label.setTextSize(21);
            productdetails_netweight_label.setTextSize(21);
            productdetails_materialprice_label.setTextSize(21);
            productdetails_title.setTextSize(21);
            productdetails_desc.setTextSize(21);
            productdetails_wastage.setTextSize(21);
            productdetails_netweight.setTextSize(21);
            productdetails_materialprice.setTextSize(21);


            back_text.setTextSize(19);
            cartname_text.setTextSize(19);
            wishlist_text.setTextSize(19);



        } else {
            Typeface sanf = Typeface.createFromAsset(getApplicationContext().getAssets(), Fonts.sanfranciscobold);
            textView_price.setTypeface(sanf);
            textView_title_label.setTypeface(sanf);
            textView_title.setTypeface(sanf);
            textView_description_label.setTypeface(sanf);
            textView_description.setTypeface(sanf);
            productdetails_jewel_label.setTypeface(sanf);

            productdetails_title_label.setTypeface(sanf);
            productdetails_desc_label.setTypeface(sanf);
            productdetails_wastage_label.setTypeface(sanf);
            productdetails_netweight_label.setTypeface(sanf);
            productdetails_materialprice_label.setTypeface(sanf);
            productdetails_title.setTypeface(sanf);
            productdetails_desc.setTypeface(sanf);
            productdetails_wastage.setTypeface(sanf);
            productdetails_netweight.setTypeface(sanf);
            productdetails_materialprice.setTypeface(sanf);

            addtocart.setTypeface(sanf);
            whishlist.setTypeface(sanf);

            back_text.setTypeface(sanf);
            cartname_text.setTypeface(sanf);
            wishlist_text.setTypeface(sanf);



            textView_price.setTextSize(16);
            textView_title_label.setTextSize(16);
            textView_title.setTextSize(16);
            textView_description_label.setTextSize(16);
            textView_description.setTextSize(16);
            productdetails_jewel_label.setTextSize(16);

            productdetails_title_label.setTextSize(16);
            productdetails_desc_label.setTextSize(16);
            productdetails_wastage_label.setTextSize(16);
            productdetails_netweight_label.setTextSize(16);
            productdetails_materialprice_label.setTextSize(16);
            productdetails_title.setTextSize(16);
            productdetails_desc.setTextSize(16);
            productdetails_wastage.setTextSize(16);
            productdetails_netweight.setTextSize(16);
            productdetails_materialprice.setTextSize(16);


            back_text.setTextSize(15);
            cartname_text.setTextSize(15);
            wishlist_text.setTextSize(15);




        }

        pdialog = new ProgressDialog(this);
        pdialog.show();
        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
productdetailstoneAdapter=new ProductdetailstoneAdapter(getApplicationContext(),productdetailslist);
recyclerView_stone.setAdapter(productdetailstoneAdapter);


        getData();



        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedpreferences.contains(MYUSERID)) {

                    myprofileid = sharedpreferences.getString(MYUSERID, "");
                    myprofileid = myprofileid.replaceAll("\\D+","");
                }
                if(myprofileid!=null){
                   List<AddtocartMsg> result=   servicerequest.AddtocartandWhishlist(language_value,"addtocart",myprofileid,maincateegoryid,categoryid,companyid,productsid,qtyid,getApplicationContext());

                        addtocart.setTextColor(Color.RED);

                }else {
                    Intent sign=new Intent(getApplicationContext(),SigninpageActivity.class);
                    sign.putExtra("ACTIVITY","EXP");
                    startActivity(sign);
                }


            }
        });
        whishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myprofileid!=null){
                    servicerequest.AddtocartandWhishlist(language_value,"addtowishlist",myprofileid,maincateegoryid,categoryid,companyid,productsid,qtyid,getApplicationContext());

                }else {
                    Intent sign=new Intent(getApplicationContext(),SigninpageActivity.class);
                    sign.putExtra("ACTIVITY","EXP");
                   startActivity(sign);
                }
            }
        });

back_text.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        onBackPressed();
    }
});


    }

    private void getData() {
        // requestQueue.add(getDataFromTheServer(requestCount));


        //    Servicerequest servicerequest = new Servicerequest(getActivity());
        if (myprofileid != null) {


            servicerequest.getProductDetail(language_value,"product_details",myprofileid,maincatid,productid,getApplicationContext());

        }
        else {

            servicerequest.getProductDetail(language_value,"product_details","",maincatid,productid,getApplicationContext());

        }

        pdialog.dismiss();
        requestCount++;
        productdetailstoneAdapter.notifyDataSetChanged();


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
    public void Productdetails(List<IndexProductModel> details) {




        for(int i=0;i<details.size();i++){

            IndexProductModel model=details.get(i);

            maincateegoryid=model.getMain_category_id();
            categoryid=model.getCategory_id();
            companyid=model.getCompany_id();
            productsid=model.getProduct_id();


if(model.getWastage()!=null || model.getNetweight()!=null || model.getMaterial_price()!=null){

    productdetail_label_layout.setVisibility(View.VISIBLE);
    if(language_data.equals("English")) {
        productdetails_jewel_label.setText("PRODUCT DETAILS");
    }else {
        productdetails_jewel_label.setText("பொருட்களின் விபரம்");
    }

}else {
    productdetail_label_layout.setVisibility(View.GONE);

}

if(model.getWastage()!=null){
    if(language_data.equals("English")) {
        productdetails_wastage_label.setText("Wastage");
    }else {
        productdetails_wastage_label.setText("தேவையற்றவை");
    }
    productdetails_wastage.setText(model.getWastage());

}else {
    productdetails_wastage_label.setVisibility(View.GONE);
    productdetails_wastage.setVisibility(View.GONE);
}
            if(model.getNetweight()!=null){
                if(language_data.equals("English")) {
                    productdetails_netweight_label.setText("Net Weight");
                }else {
                    productdetails_netweight_label.setText("நிகர எடை");
                }
                productdetails_netweight.setText(model.getNetweight());

            }else {
                productdetails_netweight_label.setVisibility(View.GONE);
                productdetails_netweight.setVisibility(View.GONE);
            }
            if(model.getMaterial_price()!=null){
                if(language_data.equals("English")) {
                    productdetails_materialprice_label.setText("Material Price");
                }else {
                    productdetails_materialprice_label.setText("பொருள் விலை");
                }
                productdetails_materialprice.setText(model.getMaterial_price());

            }else {
                productdetails_materialprice_label.setVisibility(View.GONE);
                productdetails_materialprice.setVisibility(View.GONE);
            }

            List<IndexProductModel>pricelist=model.getPricelist();
if(pricelist.size()==0){
    spinner_pricelist.setVisibility(View.GONE);
}else {
    spinner_pricelist.setVisibility(View.VISIBLE);
    List<String>item=new ArrayList<>();
    final ArrayList<String>priceitem=new ArrayList<>();
    final ArrayList<String>qtylist=new ArrayList<>();
    for(int j=0;j< pricelist.size();j++){
        IndexProductModel models=pricelist.get(j);
        String item_qty=models.getQuantity()+" "+models.getMeasurement();
        item.add(item_qty);

        String item_price=models.getPrice();
        priceitem.add(item_price);
        String qid=String.valueOf(models.getQuantity_id());
        qtylist.add(qid);


   }
    if (mImageLoader == null)
        mImageLoader = CustomVolleyRequest.getInstance(this).getImageLoader();

    product_image.setImageUrl(model.getImage(), mImageLoader);
    if(language_data.equals("English")) {
        productdetails_title_label.setText("Title");
        productdetails_desc_label.setText("Description");
    }else {
        productdetails_title_label.setText("(தலைப்பு");
        productdetails_desc_label.setText("விரிவாக்கம்");
    }
    productdetails_title.setText(model.getProduct_title());

    productdetails_desc.setText(model.getProduct_description());


    if (colorcodes.equals("#FFFFFFFF")) {
        spinner_pricelist.getBackground().setColorFilter(getApplicationContext().getResources().getColor(R.color.Black), PorterDuff.Mode.SRC_ATOP);

    }else {
        spinner_pricelist.getBackground().setColorFilter(getApplicationContext().getResources().getColor(R.color.whitecolor), PorterDuff.Mode.SRC_ATOP);

    }

    ArrayAdapter<String> adapter =
            new ArrayAdapter<String>(getApplicationContext(), R.layout.explore_my_spinner_style, item)
            {

                public View getView(int position, View convertView, ViewGroup parent) {
                    View v = super.getView(position, convertView, parent);

                    ((TextView) v).setTextSize(16);
                    if (colorcodes.equals("#FFFFFFFF")) {
                        ((TextView) v).setTextColor(
                                getApplicationContext().getResources().getColorStateList(R.color.Black)
                        );

                    }else {
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
                                getApplicationContext(). getResources().getColorStateList(R.color.Black)
                        );
                    }else {
                        v.setBackgroundColor(Color.BLACK);
                        ((TextView) v).setTextColor(
                                getApplicationContext(). getResources().getColorStateList(R.color.white)
                        );
                    }


                    //((TextView) v).setTypeface(fontStyle);
                    ((TextView) v).setGravity(Gravity.CENTER);

                    return v;
                }
            };

    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    spinner_pricelist.setAdapter(adapter);
    spinner_pricelist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String item = parent.getItemAtPosition(position).toString();

            String price=priceitem.get(position);
            if(language_data.equals("English")) {
                textView_price.setText("Price Rs. " + price);
            }else {
                textView_price.setText("விலை " + price);
            }

            qtyid=qtylist.get(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });
if(model.getMain_category_id().equals("2")){
    List<IndexProductModel>stonelists=model.getStonelist();

    if(stonelists.size()==0){
        recyclerView_stone.setVisibility(View.GONE);
        stonedetails_layout.setVisibility(View.GONE);

    }else {
        stonedetails_layout.setVisibility(View.VISIBLE);
        recyclerView_stone.setVisibility(View.VISIBLE);
        productdetailstoneAdapter.data(stonelists);


    }

}else {
    recyclerView_stone.setVisibility(View.GONE);
    stonedetails_layout.setVisibility(View.GONE);
}


}



        }



    }
}
