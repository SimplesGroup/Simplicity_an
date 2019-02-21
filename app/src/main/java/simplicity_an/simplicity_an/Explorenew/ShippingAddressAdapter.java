package simplicity_an.simplicity_an.Explorenew;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import simplicity_an.simplicity_an.CustomVolleyRequest;
import simplicity_an.simplicity_an.OnLoadMoreListener;
import simplicity_an.simplicity_an.R;
import simplicity_an.simplicity_an.Utils.Fonts;

public class ShippingAddressAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ImageLoader mImageLoader;
    private final int VIEW_TYPE_ITEM = 1;
    private final int VIEW_TYPE_LOADING = 3;
    OnLoadMoreListener onLoadMoreListener;
    private final int VIEW_TYPE_PHOTOSTORY = 2;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    Context context;
    boolean loading;


    List<ShippingModel> shopdataList;
    private SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID= "myprofileid";
    public static final String backgroundcolor = "color";
    String myprofileid,colorcodes,fontname;

    public ShippingAddressAdapter(Context mContext, List<ShippingModel> productEnglishList){
        this.context = mContext;
        this.shopdataList = productEnglishList;
    }
    public int getItemViewType(int position) {
        return shopdataList.get(position) != null ? VIEW_TYPE_ITEM : VIEW_TYPE_LOADING;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from ( parent.getContext () );
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.explore_cart_address_feed, parent, false);
            return new UserViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_loading_item, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof UserViewHolder) {
            final UserViewHolder userViewHolder = (UserViewHolder) holder;

            sharedpreferences = context. getSharedPreferences(mypreference,
                    Context.MODE_PRIVATE);
            fontname=sharedpreferences.getString(Fonts.FONT,"");
            colorcodes=sharedpreferences.getString(backgroundcolor,"");
            if (sharedpreferences.contains(MYUSERID)) {

                myprofileid = sharedpreferences.getString(MYUSERID, "");
                myprofileid = myprofileid.replaceAll("\\D+","");
            }
            if (mImageLoader == null)
                mImageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
            final ShippingModel itemmodel = shopdataList.get(position);

            if (colorcodes.equals("#FFFFFFFF")) {
                userViewHolder.name_textview.setTextColor(Color.BLACK);
                userViewHolder.shippingaddress_textview.setTextColor(Color.BLACK);
                userViewHolder.shippingaddress_continue_textview.setTextColor(Color.BLACK);
                userViewHolder.edit_address.setTextColor(Color.BLACK);
                userViewHolder.delete_address.setTextColor(Color.BLACK);
                userViewHolder.usethis_checkbox.setBackgroundColor(Color.BLACK);
            } else {
                userViewHolder.name_textview.setTextColor(Color.WHITE);
                userViewHolder.shippingaddress_textview.setTextColor(Color.WHITE);
                userViewHolder.shippingaddress_continue_textview.setTextColor(Color.WHITE);
                userViewHolder.edit_address.setTextColor(Color.WHITE);
                userViewHolder.delete_address.setTextColor(Color.WHITE);
                userViewHolder.usethis_checkbox.setBackgroundColor(Color.WHITE);
            }
            String simplycity_title = "fonts/playfairDisplayRegular.ttf";
            Typeface tf_pala = Typeface.createFromAsset(context.getAssets(), simplycity_title);
            if (fontname.equals("playfair")) {

                userViewHolder.name_textview.setTypeface(tf_pala);
                userViewHolder.shippingaddress_textview.setTypeface(tf_pala);
                userViewHolder.shippingaddress_continue_textview.setTypeface(tf_pala);
                userViewHolder.edit_address.setTypeface(tf_pala);
                userViewHolder.delete_address.setTypeface(tf_pala);
            } else {
                Typeface sanf = Typeface.createFromAsset(context.getAssets(), Fonts.sanfranciscobold);

                userViewHolder.name_textview.setTypeface(sanf);
                userViewHolder.shippingaddress_textview.setTypeface(sanf);
                userViewHolder.shippingaddress_continue_textview.setTypeface(sanf);
                userViewHolder.edit_address.setTypeface(sanf);
                userViewHolder.delete_address.setTypeface(sanf);

            }

            userViewHolder.name_textview.setText(itemmodel.getName());
            userViewHolder.shippingaddress_textview.setText(itemmodel.getAddress());
            userViewHolder.shippingaddress_continue_textview.setText(itemmodel.getLandmark()+"/n"+itemmodel.getState());
            userViewHolder.delete_address.setText("Delete");
            userViewHolder.edit_address.setText("Edit");



        }else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    public void RemoveAt(int positions){
        shopdataList.remove(positions);
        notifyItemRemoved(positions);
        notifyItemRangeChanged(positions,shopdataList.size());
        ShippingAddressAdapter.this.notifyDataSetChanged();
    }

    public void data(List<ShippingModel> lists){
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

    public int getItemCount() {
        return shopdataList.size();
    }
    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }
    public void setLoaded() {
        loading = false;
    }
    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }
    static class UserViewHolder extends RecyclerView.ViewHolder {
        CheckBox usethis_checkbox;
        TextView name_textview,shippingaddress_textview,shippingaddress_continue_textview;
        TextView edit_address,delete_address;

        public UserViewHolder(View itemView) {
            super(itemView);

            name_textview=(TextView)itemView.findViewById(R.id.explore_user_delivery_name);
            shippingaddress_textview=(TextView)itemView.findViewById(R.id.explore_user_delivery_address);
            shippingaddress_continue_textview=(TextView)itemView.findViewById(R.id.explore_user_delivery_address_continue);
            edit_address=(TextView)itemView.findViewById(R.id.explore_cartaddress_edit);
            delete_address=(TextView)itemView.findViewById(R.id.explore_cartaddress_delete);
            usethis_checkbox=(CheckBox)itemView.findViewById(R.id.usethisadd_check);



        }
    }
}
