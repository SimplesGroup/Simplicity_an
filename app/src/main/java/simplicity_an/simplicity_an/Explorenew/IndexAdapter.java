package simplicity_an.simplicity_an.Explorenew;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import simplicity_an.simplicity_an.AdvertisementPage;
import simplicity_an.simplicity_an.CustomVolleyRequest;
import simplicity_an.simplicity_an.OnLoadMoreListener;
import simplicity_an.simplicity_an.R;
import simplicity_an.simplicity_an.Utils.Fonts;

public class IndexAdapter extends RecyclerView.Adapter<IndexAdapter.Shopmodelview> {

    ImageLoader mImageLoader;
    private final int VIEW_TYPE_ITEM = 1;
    private final int VIEW_TYPE_LOADING = 3;

    boolean loading;
    OnLoadMoreListener onLoadMoreListener;
    private final int VIEW_TYPE_PHOTOSTORY = 2;
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
    public Shopmodelview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.feed_item_shopsfrag,parent,false);

        return new Shopmodelview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Shopmodelview holder, int position) {
        sharedpreferences = context. getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        fontname=sharedpreferences.getString(Fonts.FONT,"");
        colorcodes=sharedpreferences.getString(backgroundcolor,"");

        if (mImageLoader == null)
            mImageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        final IndexProductModel data=shopdataList.get(position);

        if(colorcodes.equals("#FFFFFFFF"))
        {
            holder.title_category.setTextColor(Color.BLACK);

        }
        else
        {
            holder.title_category.setTextColor(Color.WHITE);
        }
        String simplycity_title= "fonts/playfairDisplayRegular.ttf";
        Typeface tf_pala = Typeface.createFromAsset(context.getAssets(), simplycity_title);
        if(fontname.equals("playfair")){
            holder.title_category.setTypeface(tf_pala);

        }else {
            Typeface sanf=Typeface.createFromAsset(context.getAssets(),Fonts.sanfranciscobold);
            holder.title_category.setTypeface(sanf);


        }
        holder.title_category.setText(data.getCategory_title());
        Picasso.with(context).load(data.getImage()).networkPolicy(NetworkPolicy.NO_CACHE).into(holder.category_image);
        holder.category_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AdvertisementPage.class);
                intent.putExtra("IDEX", data.getUrl());
                context.startActivity(intent);
            }
        });
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
}

