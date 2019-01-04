package simplicity_an.simplicity_an.Explorenew;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import simplicity_an.simplicity_an.AdvertisementPage;
import simplicity_an.simplicity_an.CustomVolleyRequest;
import simplicity_an.simplicity_an.OnLoadMoreListener;
import simplicity_an.simplicity_an.R;
import simplicity_an.simplicity_an.Tab_new_news;
import simplicity_an.simplicity_an.Utils.Fonts;

public class IndexAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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
    public IndexAdapter(Context mContext, List<IndexProductModel> productEnglishList){
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
                ViewGroup type_one = (ViewGroup) mInflater.inflate ( R.layout.explore_feed_item_spinnerproduct, parent, false );
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        sharedpreferences = context. getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        fontname=sharedpreferences.getString(Fonts.FONT,"");
        colorcodes=sharedpreferences.getString(backgroundcolor,"");
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
                    Intent intent = new Intent(context, AdvertisementPage.class);
                    intent.putExtra("IDEX", data.getUrl());
                    context.startActivity(intent);
                }
            });
        }else if(holder instanceof Shopmodel_one){
            final Shopmodel_one    holders= (Shopmodel_one) holder;
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


                    holders.title_category.setText(data.getProduct_title());
                    List<IndexProductModel>list=data.getPricelist();
                    List<String>item=new ArrayList<>();
            final ArrayList<String>priceitem=new ArrayList<>();
                    for(int i=0;i< list.size();i++){
                        IndexProductModel model=list.get(i);
                        String item_qty=model.getQuantity()+" "+model.getMeasurement();
                        item.add(item_qty);

                        String item_price=model.getPrice();
                        priceitem.add(item_price);

                    }
holders.product_image.setImageUrl(data.getImage(), mImageLoader);


            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, item);

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            holders.price_spinner.setAdapter(dataAdapter);
            holders.price_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getItemAtPosition(position).toString();
                    String price=priceitem.get(position);
                    holders.price_item.setText(price);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
holders.add_to_cart.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

    }
});

holders.wishlist_btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

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

                    String item_price=model.getPrice();
                    holders.price_item.setText(item_price);

                }


            }else {
                holders.title_category.setText(data.getCompany_title());
            }



        }

        else if (holder instanceof Shopmodel_three){

            Shopmodel_three    holders= (Shopmodel_three) holder;
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

                String item_price=model.getPrice();

                holders.price_item_withoutspin.setText(item_price);

            }
            holders.product_image_withoutspin.setImageUrl(data.getImage(), mImageLoader);




        }
    }

    @Override
    public int getItemViewType(int position) {
        IndexProductModel model=shopdataList.get(position);

        if(model.getMain_category_id().equals("1")){
            return  VIEW_TYPE_ITEM_ONE;
        }else if(model.getMain_category_id().equals("2")){
            return VIEW_TYPE_ITEM_TWO;
        }else if(model.getMain_category_id().equals("3")){
            return VIEW_TYPE_ITEM_THREE;
        }else {
            return VIEW_TYPE_ITEM_INDEX;
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
    public void data(List<IndexProductModel> lists){
        Log.e("Response","adpter"+lists.toString());
       this.shopdataList.addAll(lists);
        notifyDataSetChanged();
    }
    public void Listitem(String msg){
        Log.e("Response",msg+"hi");
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
        private TextView add_to_cart;
        private Spinner price_spinner;
        public Shopmodel_one(View itemView) {
            super(itemView);


            title_category=(TextView)itemView.findViewById(R.id.text_product_title);
            price_item=(TextView)itemView.findViewById(R.id.text_product_price);
            add_to_cart=(TextView)itemView.findViewById(R.id.text_product_addtocart);
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

