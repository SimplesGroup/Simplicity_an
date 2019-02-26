package simplicity_an.simplicity_an.Explorenew;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
import android.widget.Toast;

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
import simplicity_an.simplicity_an.GoogleSignintwo;
import simplicity_an.simplicity_an.OnLoadMoreListener;
import simplicity_an.simplicity_an.R;
import simplicity_an.simplicity_an.SigninpageActivity;
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
    String qtyid;
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
                    Intent intent = new Intent(context, CompanyList.class);
                    intent.putExtra("ITEM_NAME", data.getCategory_title());
                    intent.putExtra("CAT_ID",data.getCategory_id());
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
                holders.title_category.setTextSize(21);
                holders.price_item.setTextSize(21);

            } else {
                Typeface sanf = Typeface.createFromAsset(context.getAssets(), Fonts.sanfranciscobold);
                holders.title_category.setTypeface(sanf);
                holders.price_item.setTypeface(sanf);
                holders.title_category.setTextSize(16);
                holders.price_item.setTextSize(16);

            }


                    holders.title_category.setText(data.getProduct_title());
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
holders.product_image.setImageUrl(data.getImage(), mImageLoader);
            holders.product_image.setImageUrl(data.getImage(), mImageLoader);
            if (colorcodes.equals("#FFFFFFFF")) {
                holders.price_spinner.getBackground().setColorFilter(context.getResources().getColor(R.color.Black), PorterDuff.Mode.SRC_ATOP);

            }else {
                holders.price_spinner.getBackground().setColorFilter(context.getResources().getColor(R.color.whitecolor), PorterDuff.Mode.SRC_ATOP);

            }
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<String>(context, R.layout.explore_my_spinner_style, item)
                    {

                        public View getView(int position, View convertView, ViewGroup parent) {
                            View v = super.getView(position, convertView, parent);

                            ((TextView) v).setTextSize(16);
                            if (colorcodes.equals("#FFFFFFFF")) {
                                ((TextView) v).setTextColor(
                                        context.getResources().getColorStateList(R.color.Black));

                            }else {
                                ((TextView) v).setTextColor(
                                        context.getResources().getColorStateList(R.color.white));

                            }

                            return v;
                        }

                       public View getDropDownView(int position, View convertView, ViewGroup parent) {
                            View v = super.getDropDownView(position, convertView, parent);
                            //v.setBackgroundResource(R.drawable.spinner_bg);
                           if (colorcodes.equals("#FFFFFFFF")) {
                               v.setBackgroundColor(Color.WHITE);
                               ((TextView) v).setTextColor(
                                       context. getResources().getColorStateList(R.color.Black)
                               );
                           }else {
                               v.setBackgroundColor(Color.BLACK);
                               ((TextView) v).setTextColor(
                                       context. getResources().getColorStateList(R.color.white)
                               );
                           }


                            //((TextView) v).setTypeface(fontStyle);
                            ((TextView) v).setGravity(Gravity.CENTER);

                            return v;
                        }
                    };



            // Drop down layout style - list view with radio button
          adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            holders.price_spinner.setAdapter(adapter);
            holders.price_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getItemAtPosition(position).toString();
                    String price=priceitem.get(position);
                    holders.price_item.setText(price );
                    qtyid=qtylist.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

holders.add_to_cart.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid = sharedpreferences.getString(MYUSERID, "");
            myprofileid = myprofileid.replaceAll("\\D+","");
        }
        if(myprofileid!=null){
            Servicerequest servicerequest=new Servicerequest();
            servicerequest.AddtocartandWhishlist("1","addtocart",myprofileid,data.getMain_category_id(),data.getCategory_id(),data.getCompany_id(),data.getProduct_id(),qtyid,context);
            List<AddtocartMsg> result = servicerequest.AddtocartandWhishlist("1","addtocart",myprofileid,data.getMain_category_id(),data.getCategory_id(),data.getCompany_id(),data.getProduct_id(),qtyid,context);
            holders.add_to_cart.setTextColor(Color.RED);
        }else {
            Intent sign=new Intent(context,SigninpageActivity.class);
            sign.putExtra("ACTIVITY","EXP");
            context.startActivity(sign);




        }

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
            holders.price_item.setVisibility(View.VISIBLE);
            if(data.getProduct_id()!=null){

                holders.title_category.setText(data.getProduct_title());

                        List<IndexProductModel>list=data.getPricelist();
                for(int i=0;i< list.size();i++){
                    IndexProductModel model=list.get(i);



                    String item_price=model.getPrice()+" "+model.getOffer_type_text()+" "+model.getOffer_price();
                    holders.price_item.setText(item_price);

                }


            }else {
                holders.title_category.setText(data.getCompany_title());
            }



        }

        else if (holder instanceof Shopmodel_three){

        final     Shopmodel_three    holders= (Shopmodel_three) holder;
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

                //String item_price=model.getPrice();
                String item_price=model.getPrice()+" "+model.getOffer_type_text()+" "+model.getOffer_price();
                holders.price_item_withoutspin.setText(item_price);

            }
            holders.product_image_withoutspin.setImageUrl(data.getImage(), mImageLoader);


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
                return VIEW_TYPE_ITEM_INDEX;
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
    public void data(List<IndexProductModel> lists){
        shopdataList.clear();
        notifyDataSetChanged();
        Log.e("Response","adpter"+lists.toString());
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

