package simplicity_an.simplicity_an.HorizontalAdapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import simplicity_an.simplicity_an.PhotoStoriesDetail;
import simplicity_an.simplicity_an.R;
import simplicity_an.simplicity_an.Tab_new_news;

public class HorizontalPhotostoryadapter extends RecyclerView.Adapter<HorizontalPhotostoryadapter.Userview> {
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String FONT= "font";
    String fontname;
    List<Tab_new_news.ItemModel> modelList=new ArrayList<>();
    Context conxt;
    public HorizontalPhotostoryadapter(List<Tab_new_news.ItemModel> students, RecyclerView recyclerView, Context context) {
        modelList = students;
        conxt=context;

    }
    @NonNull
    @Override
    public Userview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        Userview userview=new Userview(layoutInflater.inflate(R.layout.horizontal_photo_item,parent,false));
        return userview;
    }

    @Override
    public void onBindViewHolder(@NonNull Userview holder, int position) {

        sharedpreferences = conxt. getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        fontname=sharedpreferences.getString(FONT,"");
        String simplycity_title_sans = "fonts/SystemSanFranciscoDisplayBold.ttf";
        final Typeface sansfrancisco = Typeface.createFromAsset(conxt.getAssets(), simplycity_title_sans);

        final Tab_new_news.ItemModel model=modelList.get(position);
        String simplycity_title_fontPath = "fonts/playfairDisplayRegular.ttf";
        final Typeface seguiregular = Typeface.createFromAsset(conxt.getAssets(), simplycity_title_fontPath);

        holder.textView.setText(model.getTitle());
        holder.textView.setTypeface(seguiregular);
        int j;
        String image;
        for (j = 0; j < model.getAlbum().size(); j++) {
            image = model.getAlbum().get(0);
            Picasso.with(conxt)
                    .load(image)
                    .into(holder.imageView);
        }
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photostory=new Intent(conxt,PhotoStoriesDetail.class);
                photostory.putExtra("Image", model.getId());
                photostory.putExtra("TITLE",model.getTitle());
                photostory.putExtra("DATE",model.getPdate());
               conxt. startActivity(photostory);
            }
        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photostory=new Intent(conxt,PhotoStoriesDetail.class);
                photostory.putExtra("Image", model.getId());
                photostory.putExtra("TITLE",model.getTitle());
                photostory.putExtra("DATE",model.getPdate());
                conxt. startActivity(photostory);
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
        ImageView imageView;
        TextView textView;
        public Userview(View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.image);
            textView=(TextView)itemView.findViewById(R.id.title);
        }
    }
}
