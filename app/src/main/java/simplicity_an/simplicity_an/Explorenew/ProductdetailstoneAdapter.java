package simplicity_an.simplicity_an.Explorenew;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import simplicity_an.simplicity_an.R;
import simplicity_an.simplicity_an.Utils.Fonts;

import java.util.List;

public class ProductdetailstoneAdapter extends RecyclerView.Adapter<ProductdetailstoneAdapter.MyViewHolder> {
    private List<IndexProductModel> stoneList;
Context context;

    private SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID= "myprofileid";
    public static final String backgroundcolor = "color";
    public static final String Language = "lamguage";
    String language_data,language_value;
    String myprofileid,colorcodes,fontname;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private  TextView stone_label,stone_name_label,stone_cut_label,stone_color_label,stone_clarity_label;
        private TextView stone_weight_label,stone_price_label;
        private  TextView stone_name,stone_cut,stone_color,stone_clarity,stone_weight,stone_price;



        public MyViewHolder(View view) {
            super(view);

            stone_label=(TextView)view.findViewById(R.id.textView_product_detail_stone_label);
            stone_name_label=(TextView)view.findViewById(R.id.text_stone_title_label);
            stone_cut_label=(TextView)view.findViewById(R.id.text_stone_cut_label);
            stone_color_label=(TextView)view.findViewById(R.id.text_stone_color_label);
            stone_clarity_label=(TextView)view.findViewById(R.id.text_stone_clarity_label);
            stone_weight_label=(TextView)view.findViewById(R.id.text_stone_weight_label);
            stone_price_label=(TextView)view.findViewById(R.id.text_stone_price_label);
            stone_name=(TextView)view.findViewById(R.id.text_stone_title);
            stone_cut=(TextView)view.findViewById(R.id.text_stone_cut);
            stone_color=(TextView)view.findViewById(R.id.text_stone_color);
            stone_clarity=(TextView)view.findViewById(R.id.text_stone_clarity);
            stone_weight=(TextView)view.findViewById(R.id.text_stone_weight);
            stone_price=(TextView)view.findViewById(R.id.text_stone_price);



        }
    }


    public ProductdetailstoneAdapter(Context mContext, List<IndexProductModel> stonelist) {
        this.context = mContext;
        this.stoneList = stonelist;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.explore_product_detail_stone_feed, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
       IndexProductModel stone_model = stoneList.get(position);
        sharedpreferences = context. getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        fontname=sharedpreferences.getString(Fonts.FONT,"");
        colorcodes=sharedpreferences.getString(backgroundcolor,"");
        language_data=sharedpreferences.getString(Language,"");

        if(language_data.equals("English")){
            language_value="1";
        }else{
            language_value="2";
        }

        if (colorcodes.equals("#FFFFFFFF")) {

            holder.stone_label.setTextColor(Color.BLACK);
            holder.stone_name_label.setTextColor(Color.BLACK);
            holder.stone_cut_label.setTextColor(Color.BLACK);
            holder.stone_color_label.setTextColor(Color.BLACK);
            holder.stone_clarity_label.setTextColor(Color.BLACK);
            holder.stone_weight_label.setTextColor(Color.BLACK);
            holder.stone_price_label.setTextColor(Color.BLACK);
            holder.stone_name.setTextColor(Color.BLACK);
            holder.stone_cut.setTextColor(Color.BLACK);
            holder.stone_color.setTextColor(Color.BLACK);
            holder.stone_clarity.setTextColor(Color.BLACK);
            holder.stone_weight.setTextColor(Color.BLACK);
            holder.stone_price.setTextColor(Color.BLACK);

        } else {
            holder.stone_label.setTextColor(Color.WHITE);
            holder.stone_name_label.setTextColor(Color.WHITE);
            holder.stone_cut_label.setTextColor(Color.WHITE);
            holder.stone_color_label.setTextColor(Color.WHITE);
            holder.stone_clarity_label.setTextColor(Color.WHITE);
            holder.stone_weight_label.setTextColor(Color.WHITE);
            holder.stone_price_label.setTextColor(Color.WHITE);
            holder.stone_name.setTextColor(Color.WHITE);
            holder.stone_cut.setTextColor(Color.WHITE);
            holder.stone_color.setTextColor(Color.WHITE);
            holder.stone_clarity.setTextColor(Color.WHITE);
            holder.stone_weight.setTextColor(Color.WHITE);
            holder.stone_price.setTextColor(Color.WHITE);

        }
        String simplycity_title = "fonts/playfairDisplayRegular.ttf";
        Typeface tf_pala = Typeface.createFromAsset(context.getAssets(), simplycity_title);
        if (fontname.equals("playfair")) {

            holder.stone_label.setTypeface(tf_pala);
            holder.stone_name_label.setTypeface(tf_pala);
            holder.stone_cut_label.setTypeface(tf_pala);
            holder.stone_color_label.setTypeface(tf_pala);
            holder.stone_clarity_label.setTypeface(tf_pala);
            holder.stone_weight_label.setTypeface(tf_pala);
            holder.stone_price_label.setTypeface(tf_pala);
            holder.stone_name.setTypeface(tf_pala);
            holder.stone_cut.setTypeface(tf_pala);
            holder.stone_color.setTypeface(tf_pala);
            holder.stone_clarity.setTypeface(tf_pala);
            holder.stone_weight.setTypeface(tf_pala);
            holder.stone_price.setTypeface(tf_pala);
        } else {
            Typeface sanf = Typeface.createFromAsset(context.getAssets(), Fonts.sanfranciscobold);

            holder.stone_label.setTypeface(sanf);
            holder.stone_name_label.setTypeface(sanf);
            holder.stone_cut_label.setTypeface(sanf);
            holder.stone_color_label.setTypeface(sanf);
            holder.stone_clarity_label.setTypeface(sanf);
            holder.stone_weight_label.setTypeface(sanf);
            holder.stone_price_label.setTypeface(sanf);
            holder.stone_name.setTypeface(sanf);
            holder.stone_cut.setTypeface(sanf);
            holder.stone_color.setTypeface(sanf);
            holder.stone_clarity.setTypeface(sanf);
            holder.stone_weight.setTypeface(sanf);
            holder.stone_price.setTypeface(sanf);
        }


if (language_data.equals("English")) {
    holder.stone_label.setText("STONE DETAILS");
    holder.stone_name_label.setText("Stone name");
    holder.stone_cut_label.setText("Stone Cut");
    holder.stone_color_label.setText("Stone Color");
    holder.stone_clarity_label.setText("Stone Clarity");
    holder.stone_weight_label.setText("Carat Weight");
    holder.stone_price_label.setText("Stone Price");
    holder.stone_name.setText(stone_model.getStonename());
    holder.stone_cut.setText(stone_model.getStonecut());
    holder.stone_color.setText(stone_model.getStonecolor());
    holder.stone_clarity.setText(stone_model.getStoneclarity());
    holder.stone_weight.setText(stone_model.getStoneweight());
    holder.stone_price.setText(stone_model.getStoneprice());
}else{
    holder.stone_label.setText("ஸ்டோன் விபரம்");
    holder.stone_name_label.setText("ஸ்டோன் பெயர்");
    holder.stone_cut_label.setText("ஸ்டோன் கட்");
    holder.stone_color_label.setText("ஸ்டோன் கலர்");
    holder.stone_clarity_label.setText("ஸ்டோன் கிளாரிட்டி");
    holder.stone_weight_label.setText("காரட் வெயிட்");
    holder.stone_price_label.setText("ஸ்டோன் விலை");
    holder.stone_name.setText(stone_model.getStonename());
    holder.stone_cut.setText(stone_model.getStonecut());
    holder.stone_color.setText(stone_model.getStonecolor());
    holder.stone_clarity.setText(stone_model.getStoneclarity());
    holder.stone_weight.setText(stone_model.getStoneweight());
    holder.stone_price.setText(stone_model.getStoneprice());
}



    }

    @Override
    public int getItemCount() {
        return stoneList.size();
    }
    public void data(List<IndexProductModel> lists){
        stoneList.clear();
        notifyDataSetChanged();
        Log.e("Response","adpter"+lists.toString());
        this.stoneList.addAll(lists);
        notifyDataSetChanged();
    }
    public void Listitem(){
        this.stoneList.clear();
        notifyDataSetChanged();

    }

}
