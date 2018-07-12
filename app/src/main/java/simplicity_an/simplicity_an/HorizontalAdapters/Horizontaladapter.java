package simplicity_an.simplicity_an.HorizontalAdapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

import simplicity_an.simplicity_an.MySingleton;
import simplicity_an.simplicity_an.R;
import simplicity_an.simplicity_an.Tab_new_news;
import simplicity_an.simplicity_an.YoutubeVideoPlayer;


public class Horizontaladapter extends RecyclerView.Adapter<Horizontaladapter.Userview> {
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String FONT= "font";
    String fontname;
    List<Tab_new_news.ItemModel>modelList=new ArrayList<>();
    Context conxt;
    public Horizontaladapter(List<Tab_new_news.ItemModel> students, RecyclerView recyclerView, Context context) {
        modelList = students;
        conxt=context;

    }


    @NonNull
    @Override
    public Userview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        Userview userview=new Userview(inflater.inflate(R.layout.horizontal_video_item,parent,false));
        return userview;
    }

    @Override
    public void onBindViewHolder(@NonNull Userview holder, int position) {

        sharedpreferences = conxt. getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        fontname=sharedpreferences.getString(FONT,"");
        String simplycity_title_sans = "fonts/SystemSanFranciscoDisplayBold.ttf";
        final Typeface sansfrancisco = Typeface.createFromAsset(conxt.getAssets(), simplycity_title_sans);
        ImageLoader imageLoader= MySingleton.getInstance(conxt).getImageLoader();
        final Tab_new_news.ItemModel model=modelList.get(position);
        String simplycity_title_fontPath = "fonts/playfairDisplayRegular.ttf";
        final Typeface seguiregular = Typeface.createFromAsset(conxt.getAssets(), simplycity_title_fontPath);


        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(conxt, YoutubeVideoPlayer.class);
        intent.putExtra("ID", model.getId());
        intent.putExtra("TITLE",model.getTitle());
        intent.putExtra("URL",model.getYoutubelink());
        conxt.startActivity(intent);
    }
});
        holder.textView.setText(model.getTitle());
        holder.textView.setTypeface(seguiregular);
        holder.imageView.setImageUrl(model.getImage(),imageLoader);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(conxt, YoutubeVideoPlayer.class);
                intent.putExtra("ID", model.getId());
                intent.putExtra("TITLE",model.getTitle());
                intent.putExtra("URL",model.getYoutubelink());
                conxt.startActivity(intent);
            }
        });
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(conxt, YoutubeVideoPlayer.class);
                intent.putExtra("ID", model.getId());
                intent.putExtra("TITLE",model.getTitle());
                intent.putExtra("URL",model.getYoutubelink());
                conxt.startActivity(intent);
            }
        });
        if(fontname.equals("sanfrancisco")){
            holder.textView.setTypeface(sansfrancisco);
        }
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class Userview extends RecyclerView.ViewHolder{
        LinearLayout linearLayout;
NetworkImageView imageView;
TextView textView;
        public Userview(View itemView) {
            super(itemView);
            imageView=(NetworkImageView)itemView.findViewById(R.id.image);
            textView=(TextView)itemView.findViewById(R.id.title);
            linearLayout=(LinearLayout)itemView.findViewById(R.id.click);
        }
    }
}
