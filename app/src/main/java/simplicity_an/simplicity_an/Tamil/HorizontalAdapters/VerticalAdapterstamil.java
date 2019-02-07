package simplicity_an.simplicity_an.Tamil.HorizontalAdapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

import simplicity_an.simplicity_an.AdvertisementPage;
import simplicity_an.simplicity_an.Columnistdetail;
import simplicity_an.simplicity_an.Columnsdetailpage;
import simplicity_an.simplicity_an.EventsDescription;
import simplicity_an.simplicity_an.LifestyleDetail;
import simplicity_an.simplicity_an.MusicPlayer.RadioNotificationplayer;
import simplicity_an.simplicity_an.MySingleton;
import simplicity_an.simplicity_an.NewsDescription;
import simplicity_an.simplicity_an.R;
import simplicity_an.simplicity_an.SportsnewsDescription;
import simplicity_an.simplicity_an.Tab_new_news;
import simplicity_an.simplicity_an.Tamil.Activity.DoitDescriptiontamil;
import simplicity_an.simplicity_an.Tamil.Activity.EducationDescriptiontamil;
import simplicity_an.simplicity_an.Tamil.Activity.Farmingdescriptiontamil;
import simplicity_an.simplicity_an.Tamil.Activity.FoodAndCookDescriptionPagetamil;
import simplicity_an.simplicity_an.Tamil.Activity.Govtdescriptiontamil;
import simplicity_an.simplicity_an.Tamil.Activity.Healthdescriptiontamil;
import simplicity_an.simplicity_an.Tamil.Activity.JobsDetailPagetamil;
import simplicity_an.simplicity_an.Tamil.Activity.ScienceandTechnologyDescriptiontamil;
import simplicity_an.simplicity_an.Tamil.Activity.TamilEventsDescription;
import simplicity_an.simplicity_an.Tamil.Activity.TamilNewsDescription;
import simplicity_an.simplicity_an.Tamil.Activity.TipsDescriptionTamil;
import simplicity_an.simplicity_an.Tamil.Activity.TravelsDescriptiontamil;
import simplicity_an.simplicity_an.Tamil.Tab_new_newstamil;
import simplicity_an.simplicity_an.Tamil.TamilArticledescription;
import simplicity_an.simplicity_an.YoutubeVideoPlayer;

public class VerticalAdapterstamil extends RecyclerView.Adapter<VerticalAdapterstamil.Userview> {
    List<Tab_new_newstamil.ItemModel> modelList=new ArrayList<>();
    Context conxt;
    public VerticalAdapterstamil(List<Tab_new_newstamil.ItemModel> students, RecyclerView recyclerView, Context context) {
        modelList = students;
        conxt=context;

    }
    @NonNull
    @Override
    public Userview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        Userview userview=new Userview(inflater.inflate(R.layout.horizontal_small_design,parent,false));
        return userview;
    }

    @Override
    public void onBindViewHolder(@NonNull Userview holder,final int position) {
        final Tab_new_newstamil.ItemModel model=modelList.get(position);
        String simplycity_title_fontPath = "fonts/TAU_Elango_Madhavi.TTF";
        final Typeface seguiregular = Typeface.createFromAsset(conxt.getAssets(), simplycity_title_fontPath);

        ImageLoader imageLoader= MySingleton.getInstance(conxt).getImageLoader();
        holder.title.setText(Html.fromHtml(model.getTitle()));
        holder.title.setTypeface(seguiregular);
        holder.imageView.setImageUrl(model.getImage(),imageLoader);

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Response",model.getSubqueuetitle()+model.getId()+model.getQtypemain());
                if(model.getSubqueuetitle().equals("Special Column")|| model.getSubqueuetitle().equals("சிறப்பு கட்டுரைகள்")){
                    Intent intent = new Intent(conxt, Columnsdetailpage.class);
                    intent.putExtra("ID", model.getId());
                   conxt. startActivity(intent);
                }else if(model.getSubqueuetitle().equals("கோவையில் நாளைய நிகழ்வுகள்")){
                    Intent intent = new Intent(conxt, TamilEventsDescription.class);
                    intent.putExtra("ID", model.getId());
                  conxt.  startActivity(intent);
                }else if(model.getSubqueuetitle().equals("Beyond Coimbatore")||model.getSubqueuetitle().equals("நாடு மற்றும் உலக செய்திகள்")){
                    String type = ((Tab_new_newstamil.ItemModel) modelList.get(position)).getQtypemain();
                    String qtype = ((Tab_new_newstamil.ItemModel) modelList.get(position)).getQtype();
                    String ids = ((Tab_new_newstamil.ItemModel) modelList.get(position)).getId();



                    if(type.equals("news")||type.equals("National")||type.equals("International")) {
                        Intent intent = new Intent(conxt, TamilNewsDescription.class);
                        intent.putExtra("ID", ids);
                        conxt. startActivity(intent);
                    }else if(type.equals("article")){
                        Intent intent = new Intent(conxt, TamilArticledescription.class);
                        intent.putExtra("ID", ids);
                        conxt.startActivity(intent);

                    }else if(type.equals("lifestyle")){
                                    Intent intent = new Intent(conxt, LifestyleDetail.class);
                                    intent.putExtra("ID", ids);

                                conxt.    startActivity(intent);
                                }
                    else if (type.equals("doit")){
                        Intent intent = new Intent(conxt, DoitDescriptiontamil.class);
                        intent.putExtra("ID", ids);
                        conxt. startActivity(intent);
                    }else if(type.equals("farming")){
                        Intent intent = new Intent(conxt, Farmingdescriptiontamil.class);
                        intent.putExtra("ID", ids);
                        conxt. startActivity(intent);
                    }else if(type.equals("food")||type.equals("foodtip")){
                        if(qtype.equals("Food & Cooking")){
                            Intent intent = new Intent(conxt, FoodAndCookDescriptionPagetamil.class);
                            intent.putExtra("ID", ids);
                            conxt. startActivity(intent);
                        }else {
                            Intent intent = new Intent(conxt, TipsDescriptionTamil.class);
                            intent.putExtra("ID", ids);
                            conxt.  startActivity(intent);
                        }


                    }else if(type.equals("govt")){
                        Intent intent = new Intent(conxt, Govtdescriptiontamil.class);
                        intent.putExtra("ID", ids);
                        conxt. startActivity(intent);
                    }else if(type.equals("health")){
                        Intent intent = new Intent(conxt, Healthdescriptiontamil.class);
                        intent.putExtra("ID", ids);
                        conxt. startActivity(intent);
                    }else if(type.equals("science")){
                        Intent intent = new Intent(conxt, ScienceandTechnologyDescriptiontamil.class);
                        intent.putExtra("ID", ids);

                        conxt.  startActivity(intent);
                    }else if(type.equals("sports")){
                        Intent intent = new Intent(conxt, SportsnewsDescription.class);
                        intent.putExtra("ID", ids);
                        conxt.startActivity(intent);
                    }else if(type.equals("travels")){
                        Intent intent = new Intent(conxt, TravelsDescriptiontamil.class);
                        intent.putExtra("ID", ids);
                        conxt. startActivity(intent);
                    }else if(type.equals("event")){
                        Intent intent = new Intent(conxt, EventsDescription.class);
                        intent.putExtra("ID", ids);
                        conxt. startActivity(intent);
                    }else if(type.equalsIgnoreCase("Radio")){
                        Intent intent = new Intent(conxt, RadioNotificationplayer.class);
                        intent.putExtra("URL", model.getPlayurl());
                        intent.putExtra("TITLE", model.getTitle());
                        intent.putExtra("IMAGE", model.getImage());
                        conxt. startActivity(intent);
                    }else if(type.equalsIgnoreCase("Music")){
                        Intent intent = new Intent(conxt, RadioNotificationplayer.class);
                        intent.putExtra("URL", model.getPlayurl());
                        intent.putExtra("TITLE", model.getTitle());
                        intent.putExtra("IMAGE", model.getImage());
                        conxt. startActivity(intent);
                    }else if(type.equalsIgnoreCase("Job")){
                        Intent intent = new Intent(conxt, JobsDetailPagetamil.class);
                        intent.putExtra("ID", ids);
                        intent.putExtra("TITLE", model.getTitle());
                        conxt.startActivity(intent);
                    }
                    else if(type.equalsIgnoreCase("theatre")){
                        Intent intent = new Intent(conxt, YoutubeVideoPlayer.class);
                        intent.putExtra("ID", ids);
                        intent.putExtra("TITLE",model.getTitle());
                        intent.putExtra("URL",model.getYoutubelink());
                        conxt. startActivity(intent);
                    } else if(type.equals("lifestyle")){
                        Intent intent = new Intent(conxt, LifestyleDetail.class);
                        intent.putExtra("ID", ids);
                        conxt. startActivity(intent);
                    }else if(type.equalsIgnoreCase("columns")){
                        Intent intent = new Intent(conxt, Columnsdetailpage.class);
                        intent.putExtra("ID", ids);
                        conxt. startActivity(intent);
                    }else if (type.equals("columnist")){
                        Intent intent = new Intent(conxt, Columnistdetail.class);
                        intent.putExtra("ID", ids);
                        conxt. startActivity(intent);
                    }
                    else if(type.equalsIgnoreCase("education")){
                        Intent intent = new Intent(conxt, EducationDescriptiontamil.class);
                        intent.putExtra("ID", ids);
                        conxt. startActivity(intent);
                    }else
                    if(model.getQtype().equals("Sponsered")||model.getQtype().equals("Sponsored")){
                        if(model.getAds().startsWith("http://simpli")){
                            Intent intent = new Intent(conxt, AdvertisementPage.class);
                            intent.putExtra("ID", model.getAds());
                            conxt. startActivity(intent);
                        }else {
                            Intent intent = new Intent(conxt, AdvertisementPage.class);
                            intent.putExtra("ID", model.getAds());
                            conxt. startActivity(intent);
                        }


                    }
                }
            }
        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Response",model.getSubqueuetitle()+model.getId()+model.getQtypemain());

                if(model.getSubqueuetitle().equals("Special Column")|| model.getSubqueuetitle().equals("சிறப்பு கட்டுரைகள்")){
                    Intent intent = new Intent(conxt, Columnsdetailpage.class);
                    intent.putExtra("ID", model.getId());
                    conxt. startActivity(intent);
                }else if(model.getSubqueuetitle().equals("கோவையில் நாளைய நிகழ்வுகள்")){
                    Intent intent = new Intent(conxt, TamilEventsDescription.class);
                    intent.putExtra("ID", model.getId());
                    conxt.  startActivity(intent);
                }else if(model.getSubqueuetitle().equals("Beyond Coimbatore")||model.getSubqueuetitle().equals("நாடு மற்றும் உலக செய்திகள்")){
                    String type = ((Tab_new_newstamil.ItemModel) modelList.get(position)).getQtypemain();
                    String qtype = ((Tab_new_newstamil.ItemModel) modelList.get(position)).getQtype();
                    String ids = ((Tab_new_newstamil.ItemModel) modelList.get(position)).getId();



                    if(type.equals("news")||type.equals("National")||type.equals("International")) {
                        Intent intent = new Intent(conxt, TamilNewsDescription.class);
                        intent.putExtra("ID", ids);
                        conxt. startActivity(intent);
                    }else if(type.equals("article")){
                        Intent intent = new Intent(conxt, TamilArticledescription.class);
                        intent.putExtra("ID", ids);
                        conxt.startActivity(intent);

                    }else if(type.equals("lifestyle")){
                                    Intent intent = new Intent(conxt, LifestyleDetail.class);
                                    intent.putExtra("ID", ids);

                                  conxt.  startActivity(intent);
                                }
                    else if (type.equals("doit")){
                        Intent intent = new Intent(conxt, DoitDescriptiontamil.class);
                        intent.putExtra("ID", ids);
                        conxt. startActivity(intent);
                    }else if(type.equals("farming")){
                        Intent intent = new Intent(conxt, Farmingdescriptiontamil.class);
                        intent.putExtra("ID", ids);
                        conxt. startActivity(intent);
                    }else if(type.equals("food")||type.equals("foodtip")){
                        if(qtype.equals("Food & Cooking")){
                            Intent intent = new Intent(conxt, FoodAndCookDescriptionPagetamil.class);
                            intent.putExtra("ID", ids);
                            conxt. startActivity(intent);
                        }else {
                            Intent intent = new Intent(conxt, TipsDescriptionTamil.class);
                            intent.putExtra("ID", ids);
                            conxt.  startActivity(intent);
                        }


                    }else if(type.equals("govt")){
                        Intent intent = new Intent(conxt, Govtdescriptiontamil.class);
                        intent.putExtra("ID", ids);
                        conxt. startActivity(intent);
                    }else if(type.equals("health")){
                        Intent intent = new Intent(conxt, Healthdescriptiontamil.class);
                        intent.putExtra("ID", ids);
                        conxt. startActivity(intent);
                    }else if(type.equals("science")){
                        Intent intent = new Intent(conxt, ScienceandTechnologyDescriptiontamil.class);
                        intent.putExtra("ID", ids);

                        conxt.  startActivity(intent);
                    }else if(type.equals("sports")){
                        Intent intent = new Intent(conxt, SportsnewsDescription.class);
                        intent.putExtra("ID", ids);
                        conxt.startActivity(intent);
                    }else if(type.equals("travels")){
                        Intent intent = new Intent(conxt, TravelsDescriptiontamil.class);
                        intent.putExtra("ID", ids);
                        conxt. startActivity(intent);
                    }else if(type.equals("event")){
                        Intent intent = new Intent(conxt, EventsDescription.class);
                        intent.putExtra("ID", ids);
                        conxt. startActivity(intent);
                    }else if(type.equalsIgnoreCase("Radio")){
                        Intent intent = new Intent(conxt, RadioNotificationplayer.class);
                        intent.putExtra("URL", model.getPlayurl());
                        intent.putExtra("TITLE", model.getTitle());
                        intent.putExtra("IMAGE", model.getImage());
                        conxt. startActivity(intent);
                    }else if(type.equalsIgnoreCase("Music")){
                        Intent intent = new Intent(conxt, RadioNotificationplayer.class);
                        intent.putExtra("URL", model.getPlayurl());
                        intent.putExtra("TITLE", model.getTitle());
                        intent.putExtra("IMAGE", model.getImage());
                        conxt. startActivity(intent);
                    }else if(type.equalsIgnoreCase("Job")){
                        Intent intent = new Intent(conxt, JobsDetailPagetamil.class);
                        intent.putExtra("ID", ids);
                        intent.putExtra("TITLE", model.getTitle());
                        conxt.startActivity(intent);
                    }
                    else if(type.equalsIgnoreCase("theatre")){
                        Intent intent = new Intent(conxt, YoutubeVideoPlayer.class);
                        intent.putExtra("ID", ids);
                        intent.putExtra("TITLE",model.getTitle());
                        intent.putExtra("URL",model.getYoutubelink());
                        conxt. startActivity(intent);
                    } else if(type.equals("lifestyle")){
                        Intent intent = new Intent(conxt, LifestyleDetail.class);
                        intent.putExtra("ID", ids);
                        conxt. startActivity(intent);
                    }else if(type.equalsIgnoreCase("columns")){
                        Intent intent = new Intent(conxt, Columnsdetailpage.class);
                        intent.putExtra("ID", ids);
                        conxt. startActivity(intent);
                    }else if (type.equals("columnist")){
                        Intent intent = new Intent(conxt, Columnistdetail.class);
                        intent.putExtra("ID", ids);
                        conxt. startActivity(intent);
                    }
                    else if(type.equalsIgnoreCase("education")){
                        Intent intent = new Intent(conxt, EducationDescriptiontamil.class);
                        intent.putExtra("ID", ids);
                        conxt. startActivity(intent);
                    }else
                    if(model.getQtype().equals("Sponsered")||model.getQtype().equals("Sponsored")){
                        if(model.getAds().startsWith("http://simpli")){
                            Intent intent = new Intent(conxt, AdvertisementPage.class);
                            intent.putExtra("ID", model.getAds());
                            conxt. startActivity(intent);
                        }else {
                            Intent intent = new Intent(conxt, AdvertisementPage.class);
                            intent.putExtra("ID", model.getAds());
                            conxt. startActivity(intent);
                        }


                    }
                }
            }
        });
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(model.getSubqueuetitle().equals("Special Column")|| model.getSubqueuetitle().equals("சிறப்பு கட்டுரைகள்")){
                    Intent intent = new Intent(conxt, Columnsdetailpage.class);
                    intent.putExtra("ID", model.getId());
                    conxt. startActivity(intent);
                }else if(model.getSubqueuetitle().equals("கோவையில் நாளைய நிகழ்வுகள்")){
                    Intent intent = new Intent(conxt, TamilEventsDescription.class);
                    intent.putExtra("ID", model.getId());
                    conxt.  startActivity(intent);
                }else if(model.getSubqueuetitle().equals("Beyond Coimbatore")||model.getSubqueuetitle().equals("நாடு மற்றும் உலக செய்திகள்")){
                    Log.e("Response",model.getSubqueuetitle()+model.getId()+model.getQtypemain());
                    String type = ((Tab_new_newstamil.ItemModel) modelList.get(position)).getQtypemain();
                    String qtype = ((Tab_new_newstamil.ItemModel) modelList.get(position)).getQtype();
                    String ids = ((Tab_new_newstamil.ItemModel) modelList.get(position)).getId();



                    if(type.equals("news")||type.equals("National")||type.equals("International")) {
                        Intent intent = new Intent(conxt, TamilNewsDescription.class);
                        intent.putExtra("ID", ids);
                        conxt. startActivity(intent);
                    }else if(type.equals("article")){
                        Intent intent = new Intent(conxt, TamilArticledescription.class);
                        intent.putExtra("ID", ids);
                        conxt.startActivity(intent);

                    }else if(type.equals("lifestyle")){
                                    Intent intent = new Intent(conxt, LifestyleDetail.class);
                                    intent.putExtra("ID", ids);

                                  conxt.  startActivity(intent);
                                }
                    else if (type.equals("doit")){
                        Intent intent = new Intent(conxt, DoitDescriptiontamil.class);
                        intent.putExtra("ID", ids);
                        conxt. startActivity(intent);
                    }else if(type.equals("farming")){
                        Intent intent = new Intent(conxt, Farmingdescriptiontamil.class);
                        intent.putExtra("ID", ids);
                        conxt. startActivity(intent);
                    }else if(type.equals("food")||type.equals("foodtip")){
                        if(qtype.equals("Food & Cooking")){
                            Intent intent = new Intent(conxt, FoodAndCookDescriptionPagetamil.class);
                            intent.putExtra("ID", ids);
                            conxt. startActivity(intent);
                        }else {
                            Intent intent = new Intent(conxt, TipsDescriptionTamil.class);
                            intent.putExtra("ID", ids);
                            conxt.  startActivity(intent);
                        }


                    }else if(type.equals("govt")){
                        Intent intent = new Intent(conxt, Govtdescriptiontamil.class);
                        intent.putExtra("ID", ids);
                        conxt. startActivity(intent);
                    }else if(type.equals("health")){
                        Intent intent = new Intent(conxt, Healthdescriptiontamil.class);
                        intent.putExtra("ID", ids);
                        conxt. startActivity(intent);
                    }else if(type.equals("science")){
                        Intent intent = new Intent(conxt, ScienceandTechnologyDescriptiontamil.class);
                        intent.putExtra("ID", ids);

                        conxt.  startActivity(intent);
                    }else if(type.equals("sports")){
                        Intent intent = new Intent(conxt, SportsnewsDescription.class);
                        intent.putExtra("ID", ids);
                        conxt.startActivity(intent);
                    }else if(type.equals("travels")){
                        Intent intent = new Intent(conxt, TravelsDescriptiontamil.class);
                        intent.putExtra("ID", ids);
                        conxt. startActivity(intent);
                    }else if(type.equals("event")){
                        Intent intent = new Intent(conxt, EventsDescription.class);
                        intent.putExtra("ID", ids);
                        conxt. startActivity(intent);
                    }else if(type.equalsIgnoreCase("Radio")){
                        Intent intent = new Intent(conxt, RadioNotificationplayer.class);
                        intent.putExtra("URL", model.getPlayurl());
                        intent.putExtra("TITLE", model.getTitle());
                        intent.putExtra("IMAGE", model.getImage());
                        conxt. startActivity(intent);
                    }else if(type.equalsIgnoreCase("Music")){
                        Intent intent = new Intent(conxt, RadioNotificationplayer.class);
                        intent.putExtra("URL", model.getPlayurl());
                        intent.putExtra("TITLE", model.getTitle());
                        intent.putExtra("IMAGE", model.getImage());
                        conxt. startActivity(intent);
                    }else if(type.equalsIgnoreCase("Job")){
                        Intent intent = new Intent(conxt, JobsDetailPagetamil.class);
                        intent.putExtra("ID", ids);
                        intent.putExtra("TITLE", model.getTitle());
                        conxt.startActivity(intent);
                    }
                    else if(type.equalsIgnoreCase("theatre")){
                        Intent intent = new Intent(conxt, YoutubeVideoPlayer.class);
                        intent.putExtra("ID", ids);
                        intent.putExtra("TITLE",model.getTitle());
                        intent.putExtra("URL",model.getYoutubelink());
                        conxt. startActivity(intent);
                    } else if(type.equals("lifestyle")){
                        Intent intent = new Intent(conxt, LifestyleDetail.class);
                        intent.putExtra("ID", ids);
                        conxt. startActivity(intent);
                    }else if(type.equalsIgnoreCase("columns")){
                        Intent intent = new Intent(conxt, Columnsdetailpage.class);
                        intent.putExtra("ID", ids);
                        conxt. startActivity(intent);
                    }else if (type.equals("columnist")){
                        Intent intent = new Intent(conxt, Columnistdetail.class);
                        intent.putExtra("ID", ids);
                        conxt. startActivity(intent);
                    }
                    else if(type.equalsIgnoreCase("education")){
                        Intent intent = new Intent(conxt, EducationDescriptiontamil.class);
                        intent.putExtra("ID", ids);
                        conxt. startActivity(intent);
                    }else
                    if(model.getQtype().equals("Sponsered")||model.getQtype().equals("Sponsored")){
                        if(model.getAds().startsWith("http://simpli")){
                            Intent intent = new Intent(conxt, AdvertisementPage.class);
                            intent.putExtra("ID", model.getAds());
                            conxt. startActivity(intent);
                        }else {
                            Intent intent = new Intent(conxt, AdvertisementPage.class);
                            intent.putExtra("ID", model.getAds());
                            conxt. startActivity(intent);
                        }


                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class Userview extends RecyclerView.ViewHolder{
    NetworkImageView imageView;
    TextView title;
    RelativeLayout layout;
        public Userview(View itemView) {
            super(itemView);
            imageView=(NetworkImageView)itemView.findViewById(R.id.image_small_design);
            title=(TextView)itemView.findViewById(R.id.item_title_small_design);
            layout=(RelativeLayout)itemView.findViewById(R.id.listlayout_taball);
        }
    }
}
