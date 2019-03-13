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
import simplicity_an.simplicity_an.Utils.Fonts;

public class WishListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
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
public WishListAdapter(Context mContext, List<IndexProductModel> productEnglishList){
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
        ViewGroup type_one = (ViewGroup) mInflater.inflate ( R.layout.explore_wishlist_feed, parent, false );
        Shopmodel_one type_one_model = new Shopmodel_one( type_one );
        return type_one_model;
        case VIEW_TYPE_ITEM_TWO:
        ViewGroup type_two = (ViewGroup) mInflater.inflate ( R.layout.explore_wishlist_feed, parent, false );
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
            holders.title_category.setTextSize(22);
        } else {
        Typeface sanf = Typeface.createFromAsset(context.getAssets(), Fonts.sanfranciscobold);
        holders.title_category.setTypeface(sanf);
            holders.title_category.setTextSize(17);

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




        } else {
            holders_one.title_category.setTextColor(Color.WHITE);
            holders_one.price_item.setTextColor(Color.WHITE);
            holders_one.remove_cart.setTextColor(Color.WHITE);
            holders_one.add_to_wishlist.setTextColor(Color.WHITE);

        }
        String simplycity_title = "fonts/playfairDisplayRegular.ttf";
        Typeface tf_pala = Typeface.createFromAsset(context.getAssets(), simplycity_title);
        if (fontname.equals("playfair")) {
            holders_one.title_category.setTypeface(tf_pala);
            holders_one.price_item.setTypeface(tf_pala);
            holders_one.remove_cart.setTypeface(tf_pala);
            holders_one.add_to_wishlist.setTypeface(tf_pala);

            holders_one.title_category.setTextSize(20);
            holders_one.price_item.setTextSize(20);
            holders_one.remove_cart.setTextSize(20);
            holders_one.add_to_wishlist.setTextSize(20);
        } else {
        Typeface sanf = Typeface.createFromAsset(context.getAssets(), Fonts.sanfranciscobold);
            holders_one.title_category.setTypeface(sanf);
            holders_one.price_item.setTypeface(sanf);
            holders_one.remove_cart.setTypeface(sanf);
            holders_one.add_to_wishlist.setTypeface(sanf);

            holders_one.title_category.setTextSize(16);
            holders_one.price_item.setTextSize(16);
            holders_one.remove_cart.setTextSize(16);
            holders_one.add_to_wishlist.setTextSize(16);

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

            int offerprice=model.getOffer_price();
            String offertext=model.getOffer_type_text();
            if(offerprice<=0){
                String item_price=model.getPrice();
                priceitem.add(item_price);
            }else {

                String item_price=model.getPrice()+" "+model.getOffer_type_text()+" "+model.getOffer_price();
                priceitem.add(item_price);
            }


            String qid=String.valueOf(model.getQuantity_id());
            qtylist.add(qid);

        }
            holders_one.product_image.setImageUrl(data.getImage(), mImageLoader);


if(item.size()>1){
    if (colorcodes.equals("#262626")) {
        holders_one.price_spinner.getBackground().setColorFilter(context.getResources().getColor(R.color.whitecolor), PorterDuff.Mode.SRC_ATOP);

    }else {
        holders_one.price_spinner.getBackground().setColorFilter(context.getResources().getColor(R.color.Black), PorterDuff.Mode.SRC_ATOP);
    }


    ArrayAdapter<String> adapter =
            new ArrayAdapter<String>(context, R.layout.explore_my_spinner_style, item)
            {

                public View getView(int position, View convertView, ViewGroup parent) {
                    View v = super.getView(position, convertView, parent);

                    ((TextView) v).setTextSize(17);
                    if (colorcodes.equals("#262626")) {
                        //  v.setBackgroundColor(Color.BLACK);
                        ((TextView) v).setTextColor(
                                context.getResources().getColorStateList(R.color.white)
                        );
                    }else {
                        ((TextView) v).setTextColor(
                                context.getResources().getColorStateList(R.color.Black)
                        );
                    }

                    return v;
                }

                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    View v = super.getDropDownView(position, convertView, parent);
                    //v.setBackgroundResource(R.drawable.spinner_bg);
                    if (colorcodes.equals("#262626")) {
                        // v.setBackgroundColor(Color.BLACK);
                        ((TextView) v).setTextColor(
                                context.getResources().getColorStateList(R.color.Black)
                        );


                    }else {
                        //  v.setBackgroundColor(Color.WHITE);
                        ((TextView) v).setTextColor(
                                context. getResources().getColorStateList(R.color.white)
                        );
                    }




                    //((TextView) v).setTypeface(fontStyle);
                    ((TextView) v).setGravity(Gravity.CENTER);

                    return v;
                }
            };

    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    holders_one.price_spinner.setAdapter(adapter);
    holders_one.price_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ((TextView) holders_one.price_spinner.getSelectedView()).setBackgroundColor(context.getResources()
                    .getColor(R.color.input_register));
            String item = parent.getItemAtPosition(position).toString();
            String price=priceitem.get(position);
            holders_one.price_item.setText(price);

            //qtyid=qtylist.get(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });
}else {
    holders_one.price_spinner.setVisibility(View.GONE);
    String price =priceitem.get(0);
    holders_one.price_item.setText(price);
}

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





            holders_one.remove_cart.setText("Remove");
            holders_one.add_to_wishlist.setText("GO TO CART");
            if(data.getVisit_list()==1){
               holders_one.add_to_wishlist.setTextColor(Color.RED);
            }else {

            }

            holders_one.remove_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WishListPresenter wishListPresenter=new WishListPresenterImplementation();
                    wishListPresenter.MyWishremove(context,"removewishlistitem",myprofileid,data.getVisit_list_id());
                    RemoveAt(position);
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
            holders.title_category.setTextSize(21);
            holders.price_item.setTextSize(21);
        } else {
        Typeface sanf = Typeface.createFromAsset(context.getAssets(), Fonts.sanfranciscobold);
        holders.title_category.setTypeface(sanf);
        holders.price_item.setTypeface(sanf);
            holders.title_category.setTextSize(16);
            holders.price_item.setTextSize(16);
        }

        holders.product_image.setImageUrl(data.getImage(),mImageLoader);
        if(data.getProduct_id()!=null){

        holders.title_category.setText(data.getProduct_title());




      List<IndexProductModel>list=data.getPricelist();
        for(int i=0;i< list.size();i++){
        IndexProductModel model=list.get(i);

       // String item_price=model.getPrice();
            int offerprice=model.getOffer_price();
            String offertext=model.getOffer_type_text();
            if(offerprice<=0){

            }else {

                String item_price=model.getPrice()+" "+model.getOffer_type_text()+" "+model.getOffer_price();
                holders.price_item.setText(item_price);
            }
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
            holders.title_category_withoutspin.setTextSize(23);
            holders.price_item_withoutspin.setTextSize(22);
        } else {
        Typeface sanf = Typeface.createFromAsset(context.getAssets(), Fonts.sanfranciscobold);
        holders.title_category_withoutspin.setTypeface(sanf);
        holders.price_item_withoutspin.setTypeface(sanf);
            holders.title_category_withoutspin.setTextSize(17);
            holders.price_item_withoutspin.setTextSize(16);
        }

        holders.title_category_withoutspin.setText(data.getProduct_title());
        List<IndexProductModel>list=data.getPricelist();

        for(int i=0;i< list.size();i++){
        IndexProductModel model=list.get(i);
        String item_qty=model.getQuantity()+" "+model.getMeasurement();

      //  String item_price=model.getPrice();
            int offerprice=model.getOffer_price();
            String offertext=model.getOffer_type_text();
            if(offerprice<=0){

            }else {

                String item_price=model.getPrice()+" "+model.getOffer_type_text()+" "+model.getOffer_price();
                holders.price_item_withoutspin.setText(item_price);
            }

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
        return VIEW_TYPE_ITEM_ONE;
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
WishListAdapter.this.notifyDataSetChanged();
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

    private TextView remove_cart,add_to_wishlist;
    private Spinner price_spinner;
    public Shopmodel_one(View itemView) {
        super(itemView);


        title_category=(TextView)itemView.findViewById(R.id.text_product_title);
        price_item=(TextView)itemView.findViewById(R.id.text_product_price);

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
