package simplicity_an.simplicity_an.Explorenew;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import simplicity_an.simplicity_an.CustomVolleyRequest;
import simplicity_an.simplicity_an.OnLoadMoreListener;
import simplicity_an.simplicity_an.R;
import simplicity_an.simplicity_an.SigninpageActivity;
import simplicity_an.simplicity_an.Tamil.Tab_new_newstamil;
import simplicity_an.simplicity_an.Utils.Fonts;

public class MyCartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        ImageLoader mImageLoader;
private final int VIEW_TYPE_ITEM_INDEX = 1;
private final int VIEW_TYPE_ITEM_ONE = 2;
private final int VIEW_TYPE_ITEM_TWO = 3;
private final int VIEW_TYPE_ITEM_THREE = 4;
private final int VIEW_TYPE_LOADING = 5;

        boolean loading;
        OnLoadMoreListener onLoadMoreListener;

private int visibleThreshold = 5;
private int lastVisibleItem, totalItemCount;
        Context context;
private  int currentvisiblecount;
        List<IndexProductModel> shopdataList;
private SharedPreferences sharedpreferences;
public static final String mypreference = "mypref";
public static final String MYUSERID= "myprofileid";
public static final String backgroundcolor = "color";
        String myprofileid,colorcodes,fontname;

    String qtyid;
public MyCartAdapter(Context mContext, List<IndexProductModel> productEnglishList){
        this.context = mContext;
        this.shopdataList = productEnglishList;
        }

    /*public ShopAdapter(List<IndexProductModel> students, RecyclerView recyclerView){
        shopdataList = students;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();


            recyclerView
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            totalItemCount = linearLayoutManager.getItemCount();
                            lastVisibleItem = linearLayoutManager
                                    .findLastVisibleItemPosition();
                            currentvisiblecount=linearLayoutManager.findLastVisibleItemPosition();
                            if(lastVisibleItem>=10){
                               // fabnews.setVisibility(View.VISIBLE);
                            }else {
                               // fabnews.setVisibility(View.GONE);
                            }
                            if (!loading
                                    && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                                // End has been reached
                                // Do something
                                if (onLoadMoreListener != null) {
                                    onLoadMoreListener.onLoadMore();
                                }
                                loading = true;
                            }
                        }
                    });
        }
    }
*/
@NonNull
@Override
public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from ( parent.getContext () );
        switch ( viewType ) {
        case VIEW_TYPE_ITEM_INDEX:
        ViewGroup index = (ViewGroup) mInflater.inflate ( R.layout.feed_item_shopsfrag, parent, false );
        Shopmodelview indexmodel = new Shopmodelview( index );
        return indexmodel;
        case VIEW_TYPE_ITEM_ONE:
        ViewGroup type_one = (ViewGroup) mInflater.inflate ( R.layout.explore_mycart_feed, parent, false );
        Shopmodel_one type_one_model = new Shopmodel_one( type_one );
        return type_one_model;
        case VIEW_TYPE_ITEM_TWO:
        ViewGroup type_two = (ViewGroup) mInflater.inflate ( R.layout.explore_feed_item_product_jewellery_item, parent, false );
        Shopmodel_two type_two_model = new Shopmodel_two( type_two );
        return type_two_model;
        case VIEW_TYPE_ITEM_THREE:
        ViewGroup type_three = (ViewGroup) mInflater.inflate ( R.layout.explore_feed_item_withoutspinner, parent, false );
        Shopmodel_three type_three_model = new Shopmodel_three( type_three );
        return type_three_model;

        }


        return null;
        }

@Override
public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        sharedpreferences = context. getSharedPreferences(mypreference,
        Context.MODE_PRIVATE);
        fontname=sharedpreferences.getString(Fonts.FONT,"");
        colorcodes=sharedpreferences.getString(backgroundcolor,"");
    if (sharedpreferences.contains(MYUSERID)) {

        myprofileid = sharedpreferences.getString(MYUSERID, "");
        myprofileid = myprofileid.replaceAll("\\D+","");
    }
        if (holder instanceof Shopmodelview) {
        Shopmodelview    holders= (Shopmodelview) holder;
        if (mImageLoader == null)
        mImageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
final IndexProductModel data = shopdataList.get(position);

        if (colorcodes.equals("#FFFFFFFF")) {
        holders.title_category.setTextColor(Color.BLACK);

        } else {
        holders.title_category.setTextColor(Color.WHITE);
        }
        String simplycity_title = "fonts/playfairDisplayRegular.ttf";
        Typeface tf_pala = Typeface.createFromAsset(context.getAssets(), simplycity_title);
        if (fontname.equals("playfair")) {
        holders.title_category.setTypeface(tf_pala);

        } else {
        Typeface sanf = Typeface.createFromAsset(context.getAssets(), Fonts.sanfranciscobold);
        holders.title_category.setTypeface(sanf);


        }
        holders.title_category.setText(data.getCategory_title());
        Picasso.with(context).load(data.getImage()).networkPolicy(NetworkPolicy.NO_CACHE).into(holders.category_image);
        holders.category_image.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
    Intent intent = new Intent(context, ProductDetail.class);
    intent.putExtra("MAIN_CAT_ID", data.getMain_category_id());
    intent.putExtra("PRO_ID",data.getProduct_id());

    context.startActivity(intent);
        }
        });


        }else if(holder instanceof Shopmodel_one){
final Shopmodel_one    holders_one= (Shopmodel_one) holder;
        if (mImageLoader == null)
        mImageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
final IndexProductModel data = shopdataList.get(position);
        if (colorcodes.equals("#FFFFFFFF")) {
            holders_one.title_category.setTextColor(Color.BLACK);
            holders_one.price_item.setTextColor(Color.BLACK);
            holders_one.remove_cart.setTextColor(Color.BLACK);
            holders_one.add_to_wishlist.setTextColor(Color.BLACK);
            holders_one.add_to_cart_plus.setTextColor(Color.BLACK);
            holders_one.add_to_cart_count.setTextColor(Color.BLACK);
            holders_one.add_to_cart_minus.setTextColor(Color.BLACK);


        } else {
            holders_one.title_category.setTextColor(Color.WHITE);
            holders_one.price_item.setTextColor(Color.WHITE);
            holders_one.remove_cart.setTextColor(Color.WHITE);
            holders_one.add_to_wishlist.setTextColor(Color.WHITE);
            holders_one.add_to_cart_plus.setTextColor(Color.WHITE);
            holders_one.add_to_cart_count.setTextColor(Color.WHITE);
            holders_one.add_to_cart_minus.setTextColor(Color.WHITE);
        }
        String simplycity_title = "fonts/playfairDisplayRegular.ttf";
        Typeface tf_pala = Typeface.createFromAsset(context.getAssets(), simplycity_title);
        if (fontname.equals("playfair")) {
            holders_one.title_category.setTypeface(tf_pala);
            holders_one.price_item.setTypeface(tf_pala);
            holders_one.remove_cart.setTypeface(tf_pala);
            holders_one.add_to_wishlist.setTypeface(tf_pala);
            holders_one.add_to_cart_plus.setTypeface(tf_pala);
            holders_one.add_to_cart_count.setTypeface(tf_pala);
            holders_one.add_to_cart_minus.setTypeface(tf_pala);

        } else {
        Typeface sanf = Typeface.createFromAsset(context.getAssets(), Fonts.sanfranciscobold);
            holders_one.title_category.setTypeface(sanf);
            holders_one.price_item.setTypeface(sanf);
            holders_one.remove_cart.setTypeface(sanf);
            holders_one.add_to_wishlist.setTypeface(sanf);
            holders_one.add_to_cart_plus.setTypeface(sanf);
            holders_one.add_to_cart_count.setTypeface(sanf);
            holders_one.add_to_cart_minus.setTypeface(sanf);

        }


            holders_one.title_category.setText(data.getProduct_title());
        List<IndexProductModel>list=data.getPricelist();
        List<String>item=new ArrayList<>();
            final ArrayList<String>qtylist=new ArrayList<>();
final ArrayList<String>priceitem=new ArrayList<>();
        for(int i=0;i< list.size();i++){
        IndexProductModel model=list.get(i);
        String item_qty=model.getQuantity()+" "+model.getMeasurement();
        item.add(item_qty);

            String item_price=model.getPrice()+" "+model.getOffer_type_text()+" "+model.getOffer_price();
        priceitem.add(item_price);
            String qid=String.valueOf(model.getQuantity_id());
            qtylist.add(qid);

        }
            holders_one.product_image.setImageUrl(data.getImage(), mImageLoader);



            ArrayAdapter<String> adapter =
                    new ArrayAdapter<String>(context, R.layout.explore_my_spinner_style, item)
                    {

                        public View getView(int position, View convertView, ViewGroup parent) {
                            View v = super.getView(position, convertView, parent);

                            ((TextView) v).setTextSize(14);
                            if (colorcodes.equals("#262626")) {
                                ((TextView) v).setTextColor(
                                        context.getResources().getColorStateList(R.color.whitecolor));
                            }else {
                                ((TextView) v).setTextColor(
                                        context.getResources().getColorStateList(R.color.Black));



                            }

                            return v;
                        }

                        public View getDropDownView(int position, View convertView, ViewGroup parent) {
                            View v = super.getDropDownView(position, convertView, parent);
                            //v.setBackgroundResource(R.drawable.spinner_bg);
                            if (colorcodes.equals("#262626")) {
                                v.setBackgroundColor(Color.BLACK);
                                ((TextView) v).setTextColor(
                                        context. getResources().getColorStateList(R.color.whitecolor)
                                );

                            }else {
                                v.setBackgroundColor(Color.WHITE);
                                ((TextView) v).setTextColor(
                                        context. getResources().getColorStateList(R.color.Black)
                                );
                            }


                            //((TextView) v).setTypeface(fontStyle);
                            ((TextView) v).setGravity(Gravity.CENTER);

                            return v;
                        }
                    };
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holders_one.price_spinner.setAdapter(adapter);

            holders_one.product_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ProductDetail.class);
                    intent.putExtra("MAIN_CAT_ID", data.getMain_category_id());
                    intent.putExtra("PRO_ID",data.getProduct_id());

                    context.startActivity(intent);
                }
            });

        // Drop down layout style - list view with radio button
        // dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
            int cnt=Integer.parseInt(data.getCount().toString());

            holders_one.price_spinner.setSelection(cnt-1);

            holders_one.price_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
@Override
public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        String price=priceitem.get(position);
    holders_one.price_item.setText(price);

    qtyid=qtylist.get(position);




    String counts=    holders_one.add_to_cart_count.getText().toString();
    int count=Integer.parseInt(counts) + 1;
    int datacount=Integer.parseInt(data.getCount());
if(position > datacount) {
    CartViewPresenter cartViewPresenter = new CartServiceRequest();
    cartViewPresenter.getMycartupdate(context, "updatecartitem", "1", myprofileid, data.getCart_id(), qtyid, String.valueOf(count));

    holders_one.add_to_cart_count.setText(String.valueOf(count).toString());

    holders_one.price_item.setText(price);
    holders_one.price_spinner.setSelection(count-1);
}else {

}
        }

@Override
public void onNothingSelected(AdapterView<?> parent) {

        }
        });


            holders_one.add_to_cart_count.setText(data.getCount());
            holders_one.remove_cart.setText("Remove");
            holders_one.add_to_wishlist.setText("Add to Wishlist");
            if(data.getVisit_list()==1){
               holders_one.add_to_wishlist.setTextColor(Color.RED);
            }else {

            }

            holders_one.remove_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CartViewPresenter cartViewPresenter=new CartServiceRequest();
                    cartViewPresenter.getMycartupdate(context,"removecartitem","1",myprofileid,data.getCart_id(),"","");

                    RemoveAt(position);
                }
            });
            holders_one.add_to_cart_plus.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

          String counts=    holders_one.add_to_cart_count.getText().toString();
          int count=Integer.parseInt(counts) + 1;

            CartViewPresenter cartViewPresenter=new CartServiceRequest();
            cartViewPresenter.getMycartupdate(context,"updatecartitem","1",myprofileid,data.getCart_id(),qtyid,String.valueOf(count));

            holders_one.add_to_cart_count.setText(String.valueOf(count).toString());
            String price=priceitem.get(count);
            holders_one.price_item.setText(price);
            holders_one.price_spinner.setSelection(count-1);
          }
      });

            holders_one.add_to_cart_minus.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String counts=    holders_one.add_to_cart_count.getText().toString();
        int count=Integer.parseInt(counts) - 1;

        CartViewPresenter cartViewPresenter=new CartServiceRequest();
        cartViewPresenter.getMycartupdate(context,"updatecartitem","1",myprofileid,data.getCart_id(),qtyid,String.valueOf(count));

        holders_one.add_to_cart_count.setText(String.valueOf(count).toString());
        String price=priceitem.get(count);
        holders_one.price_item.setText(price);
        holders_one.price_spinner.setSelection(count-1);
    }
});





        }
        else if(holder instanceof Shopmodel_two){
        Shopmodel_two    holders= (Shopmodel_two) holder;
        if (mImageLoader == null)
        mImageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
final IndexProductModel data = shopdataList.get(position);

        if (colorcodes.equals("#FFFFFFFF")) {
        holders.title_category.setTextColor(Color.BLACK);
        holders.price_item.setTextColor(Color.BLACK);

        } else {
        holders.title_category.setTextColor(Color.WHITE);
        holders.price_item.setTextColor(Color.WHITE);
        }
        String simplycity_title = "fonts/playfairDisplayRegular.ttf";
        Typeface tf_pala = Typeface.createFromAsset(context.getAssets(), simplycity_title);
        if (fontname.equals("playfair")) {
        holders.title_category.setTypeface(tf_pala);
        holders.price_item.setTypeface(tf_pala);

        } else {
        Typeface sanf = Typeface.createFromAsset(context.getAssets(), Fonts.sanfranciscobold);
        holders.title_category.setTypeface(sanf);
        holders.price_item.setTypeface(sanf);

        }

        holders.product_image.setImageUrl(data.getImage(),mImageLoader);
        if(data.getProduct_id()!=null){

        holders.title_category.setText(data.getProduct_title());

        List<IndexProductModel>list=data.getPricelist();
        for(int i=0;i< list.size();i++){
        IndexProductModel model=list.get(i);

       // String item_price=model.getPrice();
        String item_price=model.getPrice()+" "+model.getOffer_type_text()+" "+model.getOffer_price();
        holders.price_item.setText(item_price);

        }


        }else {
        holders.title_category.setText(data.getCompany_title());
        }

holders.product_image.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, ProductDetail.class);
        intent.putExtra("MAIN_CAT_ID", data.getMain_category_id());
        intent.putExtra("PRO_ID",data.getProduct_id());

        context.startActivity(intent);
    }
});

        }

        else if (holder instanceof Shopmodel_three){

      final   Shopmodel_three    holders= (Shopmodel_three) holder;
        if (mImageLoader == null)
        mImageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
final IndexProductModel data = shopdataList.get(position);

        if (colorcodes.equals("#FFFFFFFF")) {
        holders.title_category_withoutspin.setTextColor(Color.BLACK);
        holders.price_item_withoutspin.setTextColor(Color.BLACK);

        } else {
        holders.title_category_withoutspin.setTextColor(Color.WHITE);
        holders.price_item_withoutspin.setTextColor(Color.WHITE);
        }
        String simplycity_title = "fonts/playfairDisplayRegular.ttf";
        Typeface tf_pala = Typeface.createFromAsset(context.getAssets(), simplycity_title);
        if (fontname.equals("playfair")) {
        holders.title_category_withoutspin.setTypeface(tf_pala);
        holders.price_item_withoutspin.setTypeface(tf_pala);

        } else {
        Typeface sanf = Typeface.createFromAsset(context.getAssets(), Fonts.sanfranciscobold);
        holders.title_category_withoutspin.setTypeface(sanf);
        holders.price_item_withoutspin.setTypeface(sanf);

        }

        holders.title_category_withoutspin.setText(data.getProduct_title());
        List<IndexProductModel>list=data.getPricelist();

        for(int i=0;i< list.size();i++){
        IndexProductModel model=list.get(i);
        String item_qty=model.getQuantity()+" "+model.getMeasurement();

      //  String item_price=model.getPrice();
            String item_price=model.getPrice()+" "+model.getOffer_type_text()+" "+model.getOffer_price();
        holders.price_item_withoutspin.setText(item_price);

        }
        holders.product_image_withoutspin.setImageUrl(data.getImage(), mImageLoader);

holders.product_image_withoutspin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, ProductDetail.class);
        intent.putExtra("MAIN_CAT_ID", data.getMain_category_id());
        intent.putExtra("PRO_ID",data.getProduct_id());

        context.startActivity(intent);
    }
});
            holders.add_to_cart_withoutspin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sharedpreferences.contains(MYUSERID)) {

                        myprofileid = sharedpreferences.getString(MYUSERID, "");
                        myprofileid = myprofileid.replaceAll("\\D+","");
                    }
                    if(myprofileid!=null){
                        Servicerequest servicerequest=new Servicerequest();
                        List<AddtocartMsg> result = servicerequest.AddtocartandWhishlist("1","addtocart",myprofileid,data.getMain_category_id(),data.getCategory_id(),data.getCompany_id(),data.getProduct_id(),qtyid,context);
                        holders.add_to_cart_withoutspin.setTextColor(Color.RED);
                        /*List<String> result = servicerequest.AddtocartandWhishlist("1","addtocart",myprofileid,data.getMain_category_id(),data.getCategory_id(),data.getCompany_id(),data.getProduct_id(),qtyid,context);

                        for(int i=0;i<result.size();i++){

                            String status=result.get(0);
                            String msg=result.get(1);
                            if(status.equals("success")){
                                holders.add_to_cart_withoutspin.setTextColor(Color.RED);
                            }
                        }*/

                    }else {
                        // Toast.makeText(context,"User not logged in",Toast.LENGTH_LONG).show();

                        Intent sign=new Intent(context,SigninpageActivity.class);
                        sign.putExtra("ACTIVITY","EXP");
                        context.startActivity(sign);


                    }
                }
            });


        }
        }

@Override
public int getItemViewType(int position) {
        IndexProductModel model = shopdataList.get(position);
      if (model.getItem_arrayname().equals("category")) {
        return VIEW_TYPE_ITEM_INDEX;
        } else if (model.getItem_arrayname().equals("company")) {
        return VIEW_TYPE_ITEM_TWO;
        } else   {
        if (model.getMain_category_id().equals("1")) {
        return VIEW_TYPE_ITEM_ONE;
        } else if (model.getMain_category_id().equals("2")) {
        return VIEW_TYPE_ITEM_TWO;
        } else if (model.getMain_category_id().equals("3")) {
        return VIEW_TYPE_ITEM_THREE;
        } else {
        return VIEW_TYPE_ITEM_ONE;
        }

        }
        }

public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
        }
public void setLoaded() {
        loading = false;
        }
@Override
public int getItemCount() {
        return shopdataList.size();
        }


    public void RemoveAt(int positions){
shopdataList.remove(positions);
notifyItemRemoved(positions);
notifyItemRangeChanged(positions,shopdataList.size());
MyCartAdapter.this.notifyDataSetChanged();
    }

public void data(List<IndexProductModel> lists){
  this.shopdataList.clear();
    notifyDataSetChanged();
        Log.e("Response","adpter"+"hi"+lists.toString());
        this.shopdataList.addAll(lists);
        notifyDataSetChanged();
        }
public void Listitem(){
        this.shopdataList.clear();
        notifyDataSetChanged();

        }
public class Shopmodelview extends RecyclerView.ViewHolder{
    private TextView title_category;
    private ImageView category_image;
    public Shopmodelview(View itemView) {
        super(itemView);
        title_category=(TextView)itemView.findViewById(R.id.shop_title_category);

        category_image=(ImageView)itemView.findViewById(R.id.shop_image_category);
    }
}

public class Shopmodel_two extends RecyclerView.ViewHolder{
    private TextView title_category,price_item;
    private NetworkImageView product_image;
    public Shopmodel_two(View itemView) {
        super(itemView);
        title_category=(TextView)itemView.findViewById(R.id.text_product_jewel_title);
        price_item=(TextView)itemView.findViewById(R.id.text_product_jewel_price);

        product_image=(NetworkImageView)itemView.findViewById(R.id.product_jewellery_image);
    }
}

public class Shopmodel_one extends RecyclerView.ViewHolder{
    private TextView title_category,price_item;
    private NetworkImageView product_image;
    private ImageButton wishlist_btn;
    private TextView add_to_cart_plus, add_to_cart_count, add_to_cart_minus;
    private TextView remove_cart,add_to_wishlist;
    private Spinner price_spinner;
    public Shopmodel_one(View itemView) {
        super(itemView);


        title_category=(TextView)itemView.findViewById(R.id.text_product_title);
        price_item=(TextView)itemView.findViewById(R.id.text_product_price);
        add_to_cart_plus=(TextView)itemView.findViewById(R.id.text_product_addtocart_plus);
        add_to_cart_count=(TextView)itemView.findViewById(R.id.text_product_addtocart_count);
        add_to_cart_minus=(TextView)itemView.findViewById(R.id.text_product_addtocart_minus);
        remove_cart=(TextView)itemView.findViewById(R.id.text_remove);
        add_to_wishlist=(TextView)itemView.findViewById(R.id.text_Addtowhishlist) ;
        wishlist_btn=(ImageButton)itemView.findViewById(R.id.btn_product_favourites);
        product_image=(NetworkImageView)itemView.findViewById(R.id.product_imgae);
        price_spinner=(Spinner)itemView.findViewById(R.id.spin_product_price_list);
    }
}

public class Shopmodel_three extends RecyclerView.ViewHolder{
    private TextView title_category_withoutspin,price_item_withoutspin;
    private NetworkImageView product_image_withoutspin;
    private ImageButton wishlist_btn_withoutspin;
    private TextView add_to_cart_withoutspin;

    public Shopmodel_three(View itemView) {
        super(itemView);
        title_category_withoutspin=(TextView)itemView.findViewById(R.id.text_product_title_withoutspin);
        price_item_withoutspin=(TextView)itemView.findViewById(R.id.text_product_price_withoutspin);
        add_to_cart_withoutspin=(TextView)itemView.findViewById(R.id.text_product_addtocart_withoutspin);
        wishlist_btn_withoutspin=(ImageButton)itemView.findViewById(R.id.btn_product_favourites_withoutspin);
        product_image_withoutspin=(NetworkImageView)itemView.findViewById(R.id.product_imgae_withoutspin);

    }
}

}
