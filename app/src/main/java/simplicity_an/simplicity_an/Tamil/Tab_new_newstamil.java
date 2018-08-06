package simplicity_an.simplicity_an.Tamil;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import simplicity_an.simplicity_an.AdvertisementPage;
import simplicity_an.simplicity_an.Articledescription;
import simplicity_an.simplicity_an.Columnistdetail;
import simplicity_an.simplicity_an.Columnsdetailpage;
import simplicity_an.simplicity_an.DividerItemDecoration;
import simplicity_an.simplicity_an.DoitDescription;
import simplicity_an.simplicity_an.EducationDescription;
import simplicity_an.simplicity_an.EventsDescription;
import simplicity_an.simplicity_an.Farmingdescription;
import simplicity_an.simplicity_an.FoodAndCookDescriptionPage;
import simplicity_an.simplicity_an.GovernmentnotificationsDescriptions;
import simplicity_an.simplicity_an.Healthylivingdescription;
import simplicity_an.simplicity_an.HorizontalAdapters.Horizontaladapter;
import simplicity_an.simplicity_an.HorizontalAdapters.VerticalAdapters;
import simplicity_an.simplicity_an.JobsDetailPage;
import simplicity_an.simplicity_an.LifestyleDetail;
import simplicity_an.simplicity_an.LikeListFragment;
import simplicity_an.simplicity_an.MainPageEnglish;
import simplicity_an.simplicity_an.MusicPlayer.RadioNotificationplayer;
import simplicity_an.simplicity_an.MySingleton;
import simplicity_an.simplicity_an.NewsDescription;
import simplicity_an.simplicity_an.OnLoadMoreListener;
import simplicity_an.simplicity_an.PhotoStoriesDetail;
import simplicity_an.simplicity_an.R;
import simplicity_an.simplicity_an.RecyclerView_OnClickListener;
import simplicity_an.simplicity_an.ReportNewsOrComplaints;
import simplicity_an.simplicity_an.ScienceandTechnologyDescription;
import simplicity_an.simplicity_an.SigninpageActivity;
import simplicity_an.simplicity_an.SimplicitySearchview;
import simplicity_an.simplicity_an.SportsnewsDescription;
import simplicity_an.simplicity_an.Tab_new_news;
import simplicity_an.simplicity_an.Tabnews;
import simplicity_an.simplicity_an.Tamil.Activity.DoitDescriptiontamil;
import simplicity_an.simplicity_an.Tamil.Activity.EducationDescriptiontamil;
import simplicity_an.simplicity_an.Tamil.Activity.Farmingdescriptiontamil;
import simplicity_an.simplicity_an.Tamil.Activity.FoodAndCookDescriptionPagetamil;
import simplicity_an.simplicity_an.Tamil.Activity.Govtdescriptiontamil;
import simplicity_an.simplicity_an.Tamil.Activity.Healthdescriptiontamil;
import simplicity_an.simplicity_an.Tamil.Activity.JobsDetailPagetamil;
import simplicity_an.simplicity_an.Tamil.Activity.ScienceandTechnologyDescriptiontamil;
import simplicity_an.simplicity_an.Tamil.Activity.TamilNewsDescription;
import simplicity_an.simplicity_an.Tamil.Activity.TipsDescriptionTamil;
import simplicity_an.simplicity_an.Tamil.Activity.TravelsDescriptiontamil;
import simplicity_an.simplicity_an.Tamil.HorizontalAdapters.HorizontalPhotostoryadaptertamil;
import simplicity_an.simplicity_an.Tamil.HorizontalAdapters.Horizontaladaptertamil;
import simplicity_an.simplicity_an.Tamil.HorizontalAdapters.VerticalAdapterstamil;
import simplicity_an.simplicity_an.TipsDescription;
import simplicity_an.simplicity_an.TravelsDescription;
import simplicity_an.simplicity_an.Utils.ChangeFont;
import simplicity_an.simplicity_an.Utils.Configurl;
import simplicity_an.simplicity_an.Utils.Fonts;
import simplicity_an.simplicity_an.YoutubeVideoPlayer;

public class Tab_new_newstamil extends Fragment implements ChangeFont {
    RecyclerView recyclerview_tab_all_news;
    String URL="http://simpli-city.in/request2.php?rtype=alldatatest&key=simples&qtype=news";
    String URLLIKES="http://simpli-city.in/request2.php?rtype=add-liketest&key=simples"; 				String URLSAVE="http://simpli-city.in/request2.php?rtype=addfav&key=simples";
    String URLALL;
    RequestQueue requestQueue;
    private int requestCount = 1;

    ProgressDialog pdialog;
    protected Handler handler;
    private final String TAG_REQUEST = "MY_TAG";
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID = "myprofileid";
    public static final String USERID="user_id";
    public static final String QID="qid";
    public static final String QTYPE="qtype";
    FloatingActionButton fabnews,fabplus;
    String myprofileid;
    LinearLayoutManager lLayout;
    private  static Recyclerviewtaballadapter recyclerview_tab_all_adapter;
    public static List<ItemModel> modelLists=new ArrayList<ItemModel>();
    private boolean isFragmentLoaded=false;
    public static final String backgroundcolor = "color";
    String colorcodes;
    public static final String Activity = "activity";
    public static final String CONTENTID = "contentid";
    int post_likes_count=0,save_item_count;
    int like_finalvalues;
    private Boolean isFabOpen = false;
    private Tabnews.OnFragmentInteractionListener mListener;
    FloatingActionButton fabsearch,fabinnerplus;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;
    SwipeRefreshLayout swipeRefresh;
    public String fontname;
    Typeface tf;
    public static final String FONT= "font";

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !isFragmentLoaded ) {

            isFragmentLoaded = true;
            Log.e("TAB:","ALL");
        }
    }
    public Tab_new_newstamil() {
        // Required empty public constructor
        // setUserVisibleHint(false);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.tad_all,container,false);
        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid = sharedpreferences.getString(MYUSERID, "");
            myprofileid = myprofileid.replaceAll("\\D+","");
        }
        colorcodes=sharedpreferences.getString(backgroundcolor,"");
        requestQueue = Volley.newRequestQueue(getActivity());
        fontname=sharedpreferences.getString(Fonts.FONT,"");
        getData();

        lLayout = new LinearLayoutManager(getActivity());
        recyclerview_tab_all_news = (RecyclerView) view.findViewById(R.id.tab_all_recyclerview);

        recyclerview_tab_all_news.setLayoutManager(lLayout);
        pdialog = new ProgressDialog(getActivity());

        pdialog.show();
        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        fabnews=(FloatingActionButton)view.findViewById(R.id.fabButton) ;
        if(colorcodes.equals("#FFFFFFFF")){
            fabnews.setImageResource(R.mipmap.uptamil);
        }
        else{
            fabnews.setImageResource(R.mipmap.uparrow);
        }
        fabnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recyclerview_tab_all_news.smoothScrollToPosition(0);
            }
        });
        fabplus=(FloatingActionButton)getActivity().findViewById(R.id.fabButtonplus) ;
        fabinnerplus=(FloatingActionButton)getActivity().findViewById(R.id.fabinnerplus) ;
        fabsearch=(FloatingActionButton)getActivity().findViewById(R.id.fabsearch) ;
        fab_open = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_backward);
        fabplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent report=new Intent(getActivity(),ReportNewsOrComplaints.class);
                startActivity(report);*/
                animateFAB();
            }
        });
        fabinnerplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent report=new Intent(getActivity(),ReportNewsOrComplaints.class);
                startActivity(report);
            }
        });
        fabsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent report=new Intent(getActivity(),SimplicitySearchview.class);
                startActivity(report);
            }
        });
        if(colorcodes.length()==0){

        }else {
            if(colorcodes.equalsIgnoreCase("004")){
                Log.e("Msg","hihihi");
            }else {
                if(colorcodes.equalsIgnoreCase("#383838")){
                    fabnews.setBackgroundTintList(getResources().getColorStateList(R.color.theme1button));
                    fabplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme1button));
                    fabinnerplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme1button));
                    fabsearch.setBackgroundTintList(getResources().getColorStateList(R.color.theme1button));
                }else if(colorcodes.equalsIgnoreCase("#59247c")){
                    fabnews.setBackgroundTintList(getResources().getColorStateList(R.color.theme2));
                    fabplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme2));
                    fabinnerplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme2));
                    fabsearch.setBackgroundTintList(getResources().getColorStateList(R.color.theme2));
                }else if(colorcodes.equalsIgnoreCase("#1d487a")){
                    fabnews.setBackgroundTintList(getResources().getColorStateList(R.color.theme3));
                    fabplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme3));
                    fabinnerplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme3));
                    fabsearch.setBackgroundTintList(getResources().getColorStateList(R.color.theme3));
                }else if(colorcodes.equalsIgnoreCase("#7A4100")){
                    fabnews.setBackgroundTintList(getResources().getColorStateList(R.color.theme4));
                    fabplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme4));
                    fabinnerplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme4));
                    fabsearch.setBackgroundTintList(getResources().getColorStateList(R.color.theme4));
                }else if(colorcodes.equalsIgnoreCase("#6E0138")){
                    fabnews.setBackgroundTintList(getResources().getColorStateList(R.color.theme5));
                    fabplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme5));
                    fabinnerplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme5));
                    fabsearch.setBackgroundTintList(getResources().getColorStateList(R.color.theme5));
                }else if(colorcodes.equalsIgnoreCase("#00BFD4")){
                    fabnews.setBackgroundTintList(getResources().getColorStateList(R.color.theme6));
                    fabplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme6));
                    fabinnerplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme6));
                    fabsearch.setBackgroundTintList(getResources().getColorStateList(R.color.theme6));
                }else if(colorcodes.equalsIgnoreCase("#185546")){
                    fabnews.setBackgroundTintList(getResources().getColorStateList(R.color.theme7));
                    fabplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme7));
                    fabinnerplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme7));
                    fabsearch.setBackgroundTintList(getResources().getColorStateList(R.color.theme7));
                }else if(colorcodes.equalsIgnoreCase("#D0A06F")){
                    fabnews.setBackgroundTintList(getResources().getColorStateList(R.color.theme8));
                    fabplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme8));
                    fabinnerplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme8));
                    fabsearch.setBackgroundTintList(getResources().getColorStateList(R.color.theme8));
                }else if(colorcodes.equalsIgnoreCase("#82C6E6")){
                    fabnews.setBackgroundTintList(getResources().getColorStateList(R.color.theme9));
                    fabplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme9));
                    fabinnerplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme9));
                    fabsearch.setBackgroundTintList(getResources().getColorStateList(R.color.theme9));
                }else if(colorcodes.equalsIgnoreCase("#339900")){
                    fabnews.setBackgroundTintList(getResources().getColorStateList(R.color.theme10));
                    fabplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme10));
                    fabinnerplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme10));
                    fabsearch.setBackgroundTintList(getResources().getColorStateList(R.color.theme10));
                }else if(colorcodes.equalsIgnoreCase("#CC9C00")){
                    fabnews.setBackgroundTintList(getResources().getColorStateList(R.color.theme11));
                    fabplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme11));
                    fabinnerplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme11));
                    fabsearch.setBackgroundTintList(getResources().getColorStateList(R.color.theme11));
                }else if(colorcodes.equalsIgnoreCase("#00B09B")){
                    fabnews.setBackgroundTintList(getResources().getColorStateList(R.color.theme12));
                    fabplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme12));
                    fabinnerplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme12));
                    fabsearch.setBackgroundTintList(getResources().getColorStateList(R.color.theme12));
                }
                else if(colorcodes.equalsIgnoreCase("#FFFFFFFF")){
                    fabnews.setBackgroundTintList(getResources().getColorStateList(R.color.theme13));
                    fabplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme13));
                    fabinnerplus.setBackgroundTintList(getResources().getColorStateList(R.color.theme13));
                    fabsearch.setBackgroundTintList(getResources().getColorStateList(R.color.theme13));
                }
            }
        }
        swipeRefresh = (SwipeRefreshLayout)view.findViewById(R.id.swipe);
        swipeRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(true);
                modelLists.clear();
                recyclerview_tab_all_adapter.notifyDataSetChanged();
                requestCount=1;
                getData();
                ( new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefresh.setRefreshing(false);

                    }                 }, 3000);

            }
        });
        //getData();
        recyclerview_tab_all_adapter = new Recyclerviewtaballadapter(modelLists,recyclerview_tab_all_news);
        recyclerview_tab_all_news.setAdapter(recyclerview_tab_all_adapter);
        recyclerview_tab_all_adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Log.e("haint", "Load More");


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("haint", "Load More 2");
                        getData();


                        recyclerview_tab_all_adapter.setLoaded();
                    }
                }, 2000);
            }
        });
        return view;
    }

    @Override
    public void change() {
        recyclerview_tab_all_adapter.Font(modelLists);
    }


    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String playurl, String title, String image);     }
    public void animateFAB(){

        if(isFabOpen){

            fabplus.startAnimation(rotate_backward);
            fabinnerplus.startAnimation(fab_close);
            fabsearch.startAnimation(fab_close);
            fabinnerplus.setClickable(true);
            fabsearch.setClickable(true);
            isFabOpen = false;

            Log.d("Raj", "close");

        } else {

            fabplus.startAnimation(rotate_forward);
            fabinnerplus.startAnimation(fab_open);
            fabsearch.startAnimation(fab_open);
            fabinnerplus.setClickable(true);
            fabsearch.setClickable(true);

            isFabOpen = true;
            Log.d("Raj","open");

        }
    }
    private void getData(){
        requestQueue.add(getDataFromTheServer(requestCount));
        requestCount++;
    }
    StringRequest getDataFromTheServer(final int requestCount){
        if(myprofileid!=null){
            URLALL=URL+"&page="+requestCount+"&user_id="+myprofileid;
            //URLALL=URL+requestCount+"&user_id="+myprofileid;
        }else {
            URLALL=URL+"&page="+requestCount;
            Log.e("JSON",URLALL);
        }

        StringRequest request=new StringRequest(Request.Method.POST, Configurl.api_new_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response","newnews"+response.toString());
                try{
                    JSONObject object=new JSONObject(response.toString());
                    JSONArray array=object.getJSONArray("result");
                    String data=array.optString(1);
                    JSONArray jsonArray=new JSONArray(data.toString());
                    Log.e("Response","newnews"+data.toString());
                    if (response != null) {
                        dissmissDialog();
                        parseJsonFeed(jsonArray);
                    }
                }catch (JSONException e){

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>param=new HashMap<>();
                param.put("Key", "Simplicity");
                param.put("Token", "8d83cef3923ec6e4468db1b287ad3fa7");
                param.put("language", "2");
                param.put("rtype", "city");

                param.put("page", String.valueOf(requestCount));
                /*if(myprofileid!=null){
                    param.put("user_id",myprofileid);
                }else {

                }*/
                return param;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 3, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);

        return request;






        /*JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, URLALL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("JSON ",response.toString());
                if (response != null) {
                    dissmissDialog();

                    parseJsonFeed(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonObjectRequest);
        return jsonObjectRequest;*/
    }


    private void parseJsonFeed(JSONArray response){
        try {
            // JSONArray feedArray = response.getJSONArray("");

            for (int i = 0; i < response.length(); i++) {
                JSONObject obj = (JSONObject) response.get(i);
                ItemModel model = new ItemModel();
                String qtype=obj.isNull("qtypemain")?null:obj.getString("qtypemain");
                //FeedItem model=new FeedItem();


    String image = obj.isNull("image") ? null : obj
            .getString("image");
    model.setImage(image);
    String id = obj.isNull("id") ? null : obj
            .getString("id");
    model.setId(id);

    String date = obj.isNull("date") ? null : obj
            .getString("date");
    model.setPdate(date);
    String title = obj.isNull("title") ? null : obj
            .getString("title");
    model.setTitle(title);
    String qtypes = obj.isNull("qtype") ? null : obj
            .getString("qtype");
    model.setQtype(qtypes);
    // model.setQtype(obj.getString("qtype"));
                if(qtype!=null) {
                    int likes = obj.isNull("likes_count") ? null : obj
                            .getInt("likes_count");
                    model.setLikescount(likes);

                    int comment = obj.isNull("commentscount") ? null : obj
                            .getInt("commentscount");
                    model.setCommentscount(comment);

                    model.setAlbumcount(obj.getInt("album_count"));
                    int count = obj.isNull("like_type") ? null : obj
                            .getInt("like_type");
                    model.setCounttype(count);
                    String reportername = obj.isNull("reporter_name") ? null : obj
                            .getString("reporter_name");
                    model.setEditername(reportername);
                }
                String share = obj.isNull("sharingurl") ? null : obj
                        .getString("sharingurl");
                model.setSharingurl(share);

                String qtypemain = obj.isNull("qtypemain") ? null : obj
                        .getString("qtypemain");
                model.setQtypemain(qtypemain);

                String ads = obj.isNull("url") ? null : obj
                        .getString("url");
                model.setAds(ads);

                String shortdesc = obj.isNull("short_description") ? null : obj
                        .getString("short_description");
                model.setShortdescription(shortdesc);



                String playurl = obj.isNull("radio_file") ? null : obj
                        .getString("radio_file");
                model.setPlayurl(playurl);

                String youtubelink = obj.isNull("youtube_link") ? null : obj
                        .getString("youtube_link");
                model.setYoutubelink(youtubelink);

    List<ItemModel> albums = new ArrayList<>();
    ArrayList<String> album = new ArrayList<String>();
    try {
        JSONArray feedArraygallery = obj.getJSONArray("album");


        for (int k = 0; k < feedArraygallery.length(); k++) {

            JSONObject object = (JSONObject) feedArraygallery.get(k);
            ItemModel models = new ItemModel();
            models.setAlbumimage(object.getString("image"));
            albums.add(models);

            String images = object.getString("image");
            album.add(images);
        }
    } catch (JSONException e) {

    }
    model.setAlbumlist(albums);
    model.setAlbum(album);






                List<ItemModel> videos = new ArrayList<>();
                List<ItemModel> events = new ArrayList<>();
                List<ItemModel> beyond = new ArrayList<>();
                List<ItemModel> special = new ArrayList<>();
                List<ItemModel> photo = new ArrayList<>();

                try {

                    JSONArray jsonArray = obj.getJSONArray("subqtype");
                    String data = jsonArray.optString(1);
                    String data1 = jsonArray.optString(0);
                    JSONObject jsonObject = new JSONObject(data1.toString());
                       String subtitle = jsonObject.isNull("subqtype_title") ? null : jsonObject
                                .getString("subqtype_title");
                        model.setSubqueuetitle(subtitle);

                    String s = jsonObject.getString("subqtype_title");
                    JSONArray arr = new JSONArray(data.toString());
                    Log.e("Response", "object data" + subtitle.toString());
                    if (subtitle.equals("கோவையில் நாளைய நிகழ்வுகள்")) {
                        Log.e("Response", "object data events today" );
                        for (int j = 0; j < arr.length(); j++) {
                            ItemModel models=new ItemModel();
                            JSONObject object = (JSONObject) arr.get(j);
                            String imageeve = object.isNull("image") ? null : object
                                    .getString("image");
                            models.setImage(imageeve);

                            models.setId(object.getString("id"));
                            String dateeve = object.isNull("date") ? null : object
                                    .getString("date");
                            models.setPdate(dateeve);
                            // model.setPdate(obj.getString("pdate"));
                            models.setTitle(object.getString("title"));
                            models.setSubqueuetitle(subtitle);
                            models.setYoutubelink(object.getString("youtube_link"));
                            models.setPlayurl(object.getString("radio_file"));
                            Log.e("LIST",object.getString("title"));
                           /* models.setQtype(object.getString("qtype"));
                            models.setLikescount(object.getInt("likes_count"));
                            models.setCommentscount(object.getInt("commentscount"));
                            //  model.setFavcount(obj.getInt("fav"));
                            models.setSharingurl(object.getString("sharingurl"));
                           // model.setQtypemain(obj.getString("qtypemain"));
                            models.setSubqueuetitle(subtitle);
                            models.setAds(object.getString("url"));
                            String reporternameeve = object.isNull("reporter_name") ? null : object
                                    .getString("reporter_name");
                            models.setEditername(reporternameeve);
                            String shortdesceve = object.isNull("short_description") ? null : object
                                    .getString("short_description");
                            models.setShortdescription(shortdesceve);
                            // model.setEditername(obj.getString("reporter_name"));
                            // model.setShortdescription(obj.getString("short_description"));
                            // model.setDislikecount(obj.getInt("dislikes_count"));
                            models.setCounttype(object.getInt("like_type"));

                            models.setPlayurl(object.getString("radio_file"));
                            models.setYoutubelink(object.getString("youtube_link"));*/
                            events.add(models);


                            Log.e("LIST","List added");
                        }
                    } else if (subtitle.equals("நாடு மற்றும் உலக செய்திகள்")) {
                        Log.e("Response", "object data beyond today" );
                        for (int j = 0; j < arr.length(); j++) {

                            JSONObject object = (JSONObject) arr.get(j);
                            ItemModel models = new ItemModel();
                            String imageeve = object.isNull("image") ? null : object
                                    .getString("image");
                            models.setImage(imageeve);

                            models.setId(object.getString("id"));
                            String dateeve = object.isNull("date") ? null : object
                                    .getString("date");
                            models.setPdate(dateeve);
                            // model.setPdate(obj.getString("pdate"));
                            models.setTitle(object.getString("title"));
                            models.setSubqueuetitle(subtitle);
                            models.setYoutubelink(object.getString("youtube_link"));
                            models.setPlayurl(object.getString("radio_file"));


                            /*String imagebey = object.isNull("image") ? null : object
                                    .getString("image");
                            models.setImage(imagebey);

                            models.setId(object.getString("id"));
                            String datebey = object.isNull("date") ? null : object
                                    .getString("date");
                            models.setPdate(datebey);
                            // model.setPdate(obj.getString("pdate"));
                            models.setTitle(object.getString("title"));
                            models.setQtype(object.getString("qtype"));
                            models.setLikescount(object.getInt("likes_count"));
                            models.setCommentscount(object.getInt("commentscount"));
                            //  model.setFavcount(obj.getInt("fav"));
                            models.setSharingurl(object.getString("sharingurl"));
                            //model.setQtypemain(obj.getString("qtypemain"));
                            models.setSubqueuetitle(subtitle);
                            models.setAds(object.getString("url"));
                            String reporternamebey = object.isNull("reporter_name") ? null : object
                                    .getString("reporter_name");
                            models.setEditername(reporternamebey);
                            String shortdescbey = object.isNull("short_description") ? null : object
                                    .getString("short_description");
                            models.setShortdescription(shortdescbey);
                            // model.setEditername(obj.getString("reporter_name"));
                            // model.setShortdescription(obj.getString("short_description"));
                            // model.setDislikecount(obj.getInt("dislikes_count"));
                            models.setCounttype(object.getInt("like_type"));

                            models.setPlayurl(object.getString("radio_file"));
                            models.setYoutubelink(object.getString("youtube_link"));*/
                            //modelList.add(models);
                            beyond.add(models);
                        }
                    } else if (subtitle.equals("சிறப்பு காணொளி")) {
                        for (int j = 0; j < arr.length(); j++) {
                            ItemModel models=new ItemModel();
                            JSONObject object = (JSONObject) arr.get(j);
                            String imageeve = object.isNull("image") ? null : object
                                    .getString("image");
                            models.setImage(imageeve);

                            models.setId(object.getString("id"));
                            String dateeve = object.isNull("date") ? null : object
                                    .getString("date");
                            models.setPdate(dateeve);
                            // model.setPdate(obj.getString("pdate"));
                            models.setTitle(object.getString("title"));
                            models.setSubqueuetitle(subtitle);
                            models.setYoutubelink(object.getString("youtube_link"));
                            models.setPlayurl(object.getString("radio_file"));
                            /*String imagevid = object.isNull("image") ? null : object
                                    .getString("image");
                            models.setImage(imagevid);

                            models.setId(object.getString("id"));
                            String datevid = object.isNull("date") ? null : object
                                    .getString("date");
                            models.setPdate(datevid);
                            // model.setPdate(obj.getString("pdate"));
                            models.setTitle(object.getString("title"));
                            models.setQtype(object.getString("qtype"));
                            models.setLikescount(object.getInt("likes_count"));
                            models.setCommentscount(object.getInt("commentscount"));
                            //  model.setFavcount(obj.getInt("fav"));
                            models.setSharingurl(object.getString("sharingurl"));
                           //model.setQtypemain(obj.getString("qtypemain"));
                            models.setSubqueuetitle(subtitle);
                            models.setAds(object.getString("url"));
                            String reporternamevid = object.isNull("reporter_name") ? null : object
                                    .getString("reporter_name");
                            models.setEditername(reporternamevid);
                            String shortdescvid = object.isNull("short_description") ? null : object
                                    .getString("short_description");
                            models.setShortdescription(shortdescvid);
                            // model.setEditername(obj.getString("reporter_name"));
                            // model.setShortdescription(obj.getString("short_description"));
                            // model.setDislikecount(obj.getInt("dislikes_count"));
                            models.setCounttype(object.getInt("like_type"));

                            models.setPlayurl(object.getString("radio_file"));
                            models.setYoutubelink(object.getString("youtube_link"));*/
                            videos.add(models);


                        }



                    } else if (subtitle.equals("சிறப்பு கட்டுரைகள்")) {
                        Log.e("Response", "object data special column" );
                        for (int j = 0; j < arr.length(); j++) {
                            ItemModel models=new ItemModel();
                            JSONObject object = (JSONObject) arr.get(j);

                            String imageeve = object.isNull("image") ? null : object
                                    .getString("image");
                            models.setImage(imageeve);

                            models.setId(object.getString("id"));
                            String dateeve = object.isNull("date") ? null : object
                                    .getString("date");
                            models.setPdate(dateeve);
                            // model.setPdate(obj.getString("pdate"));
                            models.setTitle(object.getString("title"));
                            models.setSubqueuetitle(subtitle);
                            models.setYoutubelink(object.getString("youtube_link"));
                            models.setPlayurl(object.getString("radio_file"));

                           /* String imagespl = object.isNull("image") ? null : object
                                    .getString("image");
                            models.setImage(imagespl);

                            models.setId(object.getString("id"));
                            String datespl = object.isNull("date") ? null : object
                                    .getString("date");
                            models.setPdate(datespl);
                            // model.setPdate(obj.getString("pdate"));
                            models.setTitle(object.getString("title"));
                            models.setQtype(object.getString("qtype"));
                            models.setLikescount(object.getInt("likes_count"));
                            models.setCommentscount(object.getInt("commentscount"));
                            //  model.setFavcount(obj.getInt("fav"));
                            models.setSharingurl(object.getString("sharingurl"));
                           // model.setQtypemain(obj.getString("qtypemain"));
                            models.setSubqueuetitle(subtitle);
                            models.setAds(object.getString("url"));
                            String reporternamespl = object.isNull("reporter_name") ? null : object
                                    .getString("reporter_name");
                            models.setEditername(reporternamespl);
                            String shortdescspl = object.isNull("short_description") ? null : object
                                    .getString("short_description");
                            models.setShortdescription(shortdescspl);
                            // model.setEditername(obj.getString("reporter_name"));
                            // model.setShortdescription(obj.getString("short_description"));
                            // model.setDislikecount(obj.getInt("dislikes_count"));
                            models.setCounttype(object.getInt("like_type"));

                            models.setPlayurl(object.getString("radio_file"));
                            models.setYoutubelink(object.getString("youtube_link"));*/
                            //modelList.add(models);
                            special.add(models);
                        }
                    } else if (subtitle.equals(" புகைப்பட செய்திகள் ")) {
                        for (int j = 0; j < arr.length(); j++) {
                            ItemModel models=new ItemModel();
                            JSONObject object = (JSONObject) arr.get(j);

                            String imageeve = object.isNull("image") ? null : object
                                    .getString("image");
                            models.setImage(imageeve);

                            models.setId(object.getString("id"));
                            String dateeve = object.isNull("date") ? null : object
                                    .getString("date");
                            models.setPdate(dateeve);
                            // model.setPdate(obj.getString("pdate"));
                            models.setTitle(object.getString("title"));
                            models.setSubqueuetitle(subtitle);
                            models.setYoutubelink(object.getString("youtube_link"));
                            models.setPlayurl(object.getString("radio_file"));

                            /*String imagepht = object.isNull("image") ? null : object
                                    .getString("image");
                            models.setImage(imagepht);

                            models.setId(object.getString("id"));
                            String datepht = object.isNull("date") ? null : object
                                    .getString("date");
                            models.setPdate(datepht);
                            // model.setPdate(obj.getString("pdate"));
                            models.setTitle(object.getString("title"));
                            models.setQtype(object.getString("qtype"));
                            models.setLikescount(object.getInt("likes_count"));
                            models.setCommentscount(object.getInt("commentscount"));
                            //  model.setFavcount(obj.getInt("fav"));
                            models.setSharingurl(object.getString("sharingurl"));
                           // model.setQtypemain(obj.getString("qtypemain"));
                            models.setSubqueuetitle(subtitle);
                            models.setAds(object.getString("url"));
                            String reporternamepht = object.isNull("reporter_name") ? null : object
                                    .getString("reporter_name");
                            models.setEditername(reporternamepht);
                            String shortdescpht = object.isNull("short_description") ? null : object
                                    .getString("short_description");
                            models.setShortdescription(shortdescpht);
                            // model.setEditername(obj.getString("reporter_name"));
                            // model.setShortdescription(obj.getString("short_description"));
                            // model.setDislikecount(obj.getInt("dislikes_count"));
                            models.setCounttype(object.getInt("like_type"));

                            models.setPlayurl(object.getString("radio_file"));
                            models.setYoutubelink(object.getString("youtube_link"));*/
                            int typevaluenew = object.isNull("album_count") ? null : object
                                    .getInt("album_count");
                            models.setAlbumcount(typevaluenew);
                            //List<ItemModel> albums = new ArrayList<>();
                            ArrayList<String> albumnew = new ArrayList<String>();
                            try {
                                JSONArray feedArraygallery = object.getJSONArray("album");


                                for (int k = 0; k < feedArraygallery.length(); k++) {

                                    JSONObject objects = (JSONObject) feedArraygallery.get(k);
                                    ItemModel modelss = new ItemModel();
                                    modelss.setAlbumimage(objects.getString("image"));
                                    //  albums.add(models);

                                    String images=objects.getString("image");
                                    albumnew.add(images);
                                }
                            }catch (JSONException e){

                            }
                          //  model.setAlbumlist(albums);
                            models.setAlbum(albumnew);
                            photo.add(models);
                        }

                    }


                } catch (JSONException e) {

                }


               /* String image = obj.isNull("image") ? null : obj
                        .getString("image");
                model.setImage(image);

                model.setId(obj.getString("id"));
                String date = obj.isNull("date") ? null : obj
                        .getString("date");
                model.setPdate(date);
                // model.setPdate(obj.getString("pdate"));
                model.setTitle(obj.getString("title"));
                model.setQtype(obj.getString("qtype"));
                model.setLikescount(obj.getInt("likes_count"));
                model.setCommentscount(obj.getInt("commentscount"));
                //  model.setFavcount(obj.getInt("fav"));
                model.setSharingurl(obj.getString("sharingurl"));
                model.setQtypemain(obj.getString("qtypemain"));
                model.setAds(obj.getString("url"));
                String reportername = obj.isNull("reporter_name") ? null : obj
                        .getString("reporter_name");
                model.setEditername(reportername);
                String shortdesc = obj.isNull("short_description") ? null : obj
                        .getString("short_description");
                model.setShortdescription(shortdesc);
                // model.setEditername(obj.getString("reporter_name"));
                // model.setShortdescription(obj.getString("short_description"));
                // model.setDislikecount(obj.getInt("dislikes_count"));
                model.setCounttype(obj.getInt("like_type"));

                model.setPlayurl(obj.getString("radio_file"));
                model.setYoutubelink(obj.getString("youtube_link"));
                int typevalue = obj.isNull("album_count") ? null : obj
                        .getInt("album_count");
                model.setAlbumcount(typevalue);
                List<ItemModel> albums = new ArrayList<>();
                ArrayList<String> album = new ArrayList<String>();
                try {
                    JSONArray feedArraygallery = obj.getJSONArray("album");


                    for (int k = 0; k < feedArraygallery.length(); k++) {

                        JSONObject object = (JSONObject) feedArraygallery.get(k);
                       ItemModel models = new ItemModel();
                        models.setAlbumimage(object.getString("image"));
                        albums.add(models);

                        String images=object.getString("image");
                        album.add(images);
                    }
                }catch (JSONException e){

                }

                model.setAlbumlist(albums);
                model.setAlbum(album);*/
                //modelList.add(model);
                model.setBeyondlist(beyond);
                model.setSpeciallist(special);
                model.setEventlist(events);
                model.setVideoList(videos);
                model.setPhotoStoryList(photo);
                modelLists.add(model);
            }


            recyclerview_tab_all_adapter.notifyDataSetChanged();




        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /*public void onStop() {
         super.onStop();
         if (requestQueue != null) {
             requestQueue.cancelAll(TAG_REQUEST);
         }
     }*/
    public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (Tabnews.OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            //  throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    public void dissmissDialog() {
        // TODO Auto-generated method stub
        if (pdialog != null) {
            if (pdialog.isShowing()) {
                pdialog.dismiss();
            }
            pdialog = null;
        }

    }

  public   class ItemModel{
       int typeid;
        String name;
         String image;

        String pdate;
         String description;
         String title;
        int albumcount;
        String playurl,albumimage;
        ArrayList<String> album;
         List<ItemModel> albumlist;

       List<ItemModel>PhotoStoryList,VideoList,eventlist,beyondlist,speciallist;
         String id;

        String qtype,qtypemain;
        String ads;
        int favcount;
        String sharingurl;
        int likescount,dislikecount,commentscount,counttype;
        String shortdescription,editername;
        String youtubelink,subqueuetitle;

      public List<ItemModel> getBeyondlist() {
          return beyondlist;
      }

      public void setBeyondlist(List<ItemModel> beyondlist) {
          this.beyondlist = beyondlist;
      }

      public List<ItemModel> getSpeciallist() {
          return speciallist;
      }

      public void setSpeciallist(List<ItemModel> speciallist) {
          this.speciallist = speciallist;
      }

      public List<ItemModel> getEventlist() {
          return eventlist;
      }

      public void setEventlist(List<ItemModel> eventlist) {
          this.eventlist = eventlist;
      }

      public void setSubqueuetitle(String subqueuetitle) {
          this.subqueuetitle = subqueuetitle;
      }

      public String getSubqueuetitle() {
          return subqueuetitle;
      }

      public List<ItemModel> getPhotoStoryList() {
            return PhotoStoryList;
        }

        public void setPhotoStoryList(List<ItemModel> photoStoryList) {
            PhotoStoryList = photoStoryList;
        }

        public List<ItemModel> getVideoList() {
            return VideoList;
        }

        public void setVideoList(List<ItemModel> videoList) {
            VideoList = videoList;
        }

        public void setYoutubelink(String youtubelink) {
            this.youtubelink = youtubelink;
        }

        public String getYoutubelink() {
            return youtubelink;
        }

        public String getEditername() {
            return editername;
        }

        public void setEditername(String editername) {
            this.editername = editername;
        }

        public String getShortdescription() {
            return shortdescription;
        }

        public void setShortdescription(String shortdescription) {
            this.shortdescription = shortdescription;
        }

        public String getAds() {
            return ads;
        }

        public void setAds(String ads) {
            this.ads = ads;
        }
        public List<ItemModel> getAlbumlist() {
            return albumlist;
        }

        public void setAlbumlist(List<ItemModel> albumlist) {
            this.albumlist = albumlist;
        }

        public String getAlbumimage() {
            return albumimage;
        }

        public void setAlbumimage(String albumimage) {
            this.albumimage = albumimage;
        }

        public String getPlayurl() {
            return playurl;
        }

        public void setPlayurl(String playurl) {
            this.playurl = playurl;
        }

        public ArrayList<String> getAlbum() {
            return album;
        }

        public void setAlbum(ArrayList<String> album) {
            this.album = album;
        }

        public int getAlbumcount() {
            return albumcount;
        }

        public void setAlbumcount(int albumcount) {
            this.albumcount = albumcount;
        }

        public String getQtypemain() {
            return qtypemain;
        }

        public void setQtypemain(String qtypemain) {
            this.qtypemain = qtypemain;
        }

        public int getFavcount() {
            return favcount;
        }

        public void setFavcount(int favcount) {
            this.favcount = favcount;
        }

        public String getSharingurl() {
            return sharingurl;
        }

        public void setSharingurl(String sharingurl) {
            this.sharingurl = sharingurl;
        }

        public int getCommentscount() {
            return commentscount;
        }

        public int getCounttype() {
            return counttype;
        }

        public int getDislikecount() {
            return dislikecount;
        }

        public int getLikescount() {
            return likescount;
        }

        public void setCommentscount(int commentscount) {
            this.commentscount = commentscount;
        }

        public void setCounttype(int counttype) {
            this.counttype = counttype;
        }

        public void setDislikecount(int dislikecount) {
            this.dislikecount = dislikecount;
        }

        public void setLikescount(int likescount) {
            this.likescount = likescount;
        }

        public String getQtype() {
            return qtype;
        }

        public void setQtype(String qtype) {
            this.qtype = qtype;
        }

        public int getTypeid() {
            return typeid;
        }

        public void setTypeid(int typeid) {
            this.typeid = typeid;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }
        public void setImage(String image) {
            this.image = image;
        }
        public String getDescription(){return description;}
        public  void setDescription(String description){
            this.description=description;
        }
        public String getPdate(){return  pdate;}

        public void setPdate(String pdate) {
            this.pdate = pdate;
        }
        public String getTitle(){return  title;}

        public void setTitle(String title) {
            this.title = title;
        }

        /******** start the Food category names****/
        public String getId(){return  id;}

        public void setId(String id) {
            this.id = id;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        dissmissDialog();
    }
    static class Userviewholdertaball extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView likescount,dislikescount,commentscount,title_item,item_type_name,date;
      public   TextView shortdescription,editername;
        public Button share_button,comment_button,likes_button,save_button;
        ImageButton share_imagebutton,like_imagebutton,comment_imagebutton,arrow_imagebutton;
        public NetworkImageView item_image;
        public RelativeLayout countlayout,listLayout;
        ImageButton play;
        View line;
        RecyclerView_OnClickListener.OnClickListener onClickListener;



        LinearLayout smalldesign_layout,bigdesign_layout;

        public Userviewholdertaball(View itemView) {
            super(itemView);
            this.title_item=(TextView)itemView.findViewById(R.id.item_title_taball);
            this.  item_type_name=(TextView)itemView.findViewById(R.id.qtypetitle_taball);
            this. date=(TextView)itemView.findViewById(R.id.date_taball);
            this. likescount=(TextView)itemView.findViewById(R.id.alltab_likescount);
            this.arrow_imagebutton=(ImageButton)itemView.findViewById(R.id.arrow);
            this.editername=(TextView)itemView.findViewById(R.id.qtypetitle_sourcename);
            this.shortdescription=(TextView)itemView.findViewById(R.id.textview_shortdescription) ;
            this.play=(ImageButton)itemView.findViewById(R.id.taball_play_pause_main);
            this.  commentscount=(TextView)itemView.findViewById(R.id.alltab_commentscount);
            this.  likes_button=(Button)itemView.findViewById(R.id.taball_likes);
            this. save_button=(Button)itemView.findViewById(R.id.taball_savepage);
            this. share_button=(Button)itemView.findViewById(R.id.taball_sharepost);
            this. comment_button=(Button)itemView.findViewById(R.id.taball_comment);
            this.comment_imagebutton=(ImageButton)itemView.findViewById(R.id.button_comment);
            this.like_imagebutton=(ImageButton)itemView.findViewById(R.id.button_likes) ;
            this.share_imagebutton=(ImageButton)itemView.findViewById(R.id.button_share) ;
            this.line = itemView.findViewById(R.id.line_separter);
            this. item_image=(NetworkImageView)itemView.findViewById(R.id.image_alltab);
            this.listLayout=(RelativeLayout) itemView.findViewById(R.id.listlayout_taball);
            this.countlayout=(RelativeLayout)itemView.findViewById(R.id.counts_layout);


            this.bigdesign_layout=(LinearLayout)itemView.findViewById(R.id.big_design);



            this.likes_button.setOnClickListener(this);
            this.comment_button.setOnClickListener(this);
            this.save_button.setOnClickListener(this);
            this.share_button.setOnClickListener(this);
            this.listLayout.setOnClickListener(this);
            this.play.setOnClickListener(this);
            this.likescount.setOnClickListener(this);

            this.like_imagebutton.setOnClickListener(this);
            this.comment_imagebutton.setOnClickListener(this);
            this.share_imagebutton.setOnClickListener(this);
        }

        public void onClick(View v) {

            // setting custom listener
            if (onClickListener != null) {
                onClickListener.OnItemClick(v, getAdapterPosition());

            }

        }

        // Setter for listener
        public void setClickListener(
                RecyclerView_OnClickListener.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }
    }

    static class UserViewHolderphotostories extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView likescount,dislikescount,commentscount,title_item,item_type_name,date,moreimagescount_textview;

        public Button share_button,comment_button,likes_button,save_button;
        TextView shortdescription,editername;
        ImageButton share_imagebutton,like_imagebutton,comment_imagebutton,arrow_imagebutton;
        NetworkImageView   feedImageView;

        NetworkImageView   feedImageView_typetwo_one,feedImageView_typetwo_two;
        NetworkImageView   feed_typethree_ones,feed_typethree_twos,feed_typethree_threes;
        NetworkImageView   feedImageView_typefour_one,feedImageView_typefour_two,feedImageView_typefour_three,feedImageView_typefour_four;
        View line;
        public RelativeLayout countlayout,listLayout;
        // LinearLayout ;
        RecyclerView_OnClickListener.OnClickListener onClickListener;

        public UserViewHolderphotostories(View itemView) {
            super(itemView);


            this.title_item=(TextView)itemView.findViewById(R.id.item_title_taball);
            this.  item_type_name=(TextView)itemView.findViewById(R.id.qtypetitle_taball);
            this. date=(TextView)itemView.findViewById(R.id.date_taball);
            this. likescount=(TextView)itemView.findViewById(R.id.alltab_likescount);
            moreimagescount_textview=(TextView)itemView.findViewById(R.id.more_images);
            this.  commentscount=(TextView)itemView.findViewById(R.id.alltab_commentscount);
            this.  likes_button=(Button)itemView.findViewById(R.id.taball_likes);
            this. save_button=(Button)itemView.findViewById(R.id.taball_savepage);
            this. share_button=(Button)itemView.findViewById(R.id.taball_sharepost);
            this. comment_button=(Button)itemView.findViewById(R.id.taball_comment);
            this.arrow_imagebutton=(ImageButton)itemView.findViewById(R.id.arrow);
            this.editername=(TextView)itemView.findViewById(R.id.qtypetitle_sourcename);
            this.shortdescription=(TextView)itemView.findViewById(R.id.textview_shortdescription) ;
            this.comment_imagebutton=(ImageButton)itemView.findViewById(R.id.button_comment);
            this.like_imagebutton=(ImageButton)itemView.findViewById(R.id.button_likes) ;
            this.share_imagebutton=(ImageButton)itemView.findViewById(R.id.button_share) ;
            this.line = itemView.findViewById(R.id.line_separter);
            this.listLayout=(RelativeLayout) itemView.findViewById(R.id.listlayout_taball);
            this.countlayout=(RelativeLayout)itemView.findViewById(R.id.counts_layout);
            this.likes_button.setOnClickListener(this);
            this.comment_button.setOnClickListener(this);
            this.save_button.setOnClickListener(this);
            this.share_button.setOnClickListener(this);
            this.listLayout.setOnClickListener(this);
            this.likescount.setOnClickListener(this);


            this.like_imagebutton.setOnClickListener(this);
            this.comment_imagebutton.setOnClickListener(this);
            this.share_imagebutton.setOnClickListener(this);

            this.  feedImageView = (NetworkImageView) itemView
                    .findViewById(R.id.feedImage_photostory);

            this. feedImageView_typetwo_one=(NetworkImageView)itemView.findViewById(R.id.feedImage_photostory_type_two_one) ;
            this. feedImageView_typetwo_two=(NetworkImageView)itemView.findViewById(R.id.feedImage_photostory_type_two_two) ;

            this. feed_typethree_ones=(NetworkImageView)itemView.findViewById(R.id.feedImage_photostory_three_one) ;
            this. feed_typethree_twos=(NetworkImageView)itemView.findViewById(R.id.feedImage_photostory_three_two) ;
            this.  feed_typethree_threes=(NetworkImageView)itemView.findViewById(R.id.feedImage_photostory_three_three) ;


            this. feedImageView_typefour_one=(NetworkImageView)itemView.findViewById(R.id.feedImage_photostory_type_four_one) ;
            this. feedImageView_typefour_two=(NetworkImageView)itemView.findViewById(R.id.feedImage_photostory_type_four_two) ;
            this. feedImageView_typefour_three=(NetworkImageView)itemView.findViewById(R.id.feedImage_photostory_type_four_three) ;
            this. feedImageView_typefour_four=(NetworkImageView)itemView.findViewById(R.id.feedImage_photostory_type_four_four) ;
        }
        public void onClick(View v) {

            // setting custom listener
            if (onClickListener != null) {
                onClickListener.OnItemClick(v, getAdapterPosition());

            }

        }

        // Setter for listener
        public void setClickListener(
                RecyclerView_OnClickListener.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }
    }
    public class Horizontalevent extends RecyclerView.ViewHolder {
        RecyclerView horizontal_recylerview;
        TextView text_title,seeall_text;

        public Horizontalevent(View itemView) {
            super(itemView);


            horizontal_recylerview=(RecyclerView)itemView.findViewById(R.id.horizontal_recylerview);
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
            horizontal_recylerview.setLayoutManager(linearLayoutManager);
            text_title=(TextView)itemView.findViewById(R.id.title_horizontal);
            seeall_text=(TextView)itemView.findViewById(R.id.seeall_horizontal);



        }


    }
    public class Horizontalbeyond extends RecyclerView.ViewHolder {
        RecyclerView horizontal_recylerview;
        TextView text_title,seeall_text;

        public Horizontalbeyond(View itemView) {
            super(itemView);


            horizontal_recylerview=(RecyclerView)itemView.findViewById(R.id.horizontal_recylerview);
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
            horizontal_recylerview.setLayoutManager(linearLayoutManager);
            text_title=(TextView)itemView.findViewById(R.id.title_horizontal);
            seeall_text=(TextView)itemView.findViewById(R.id.seeall_horizontal);



        }


    }
    public class Horizontalspecial extends RecyclerView.ViewHolder {
        RecyclerView horizontal_recylerview;
        TextView text_title,seeall_text;

        public Horizontalspecial(View itemView) {
            super(itemView);


            horizontal_recylerview=(RecyclerView)itemView.findViewById(R.id.horizontal_recylerview);
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
            horizontal_recylerview.setLayoutManager(linearLayoutManager);
            text_title=(TextView)itemView.findViewById(R.id.title_horizontal);
            seeall_text=(TextView)itemView.findViewById(R.id.seeall_horizontal);



        }


    }
 public class Horizontalviewholder extends RecyclerView.ViewHolder{
RecyclerView horizontal_recylerview;
TextView text_title,seeall_text;
    public Horizontalviewholder(View itemView) {
        super(itemView);
        horizontal_recylerview=(RecyclerView)itemView.findViewById(R.id.horizontal_recylerview);

        text_title=(TextView)itemView.findViewById(R.id.title_horizontal);
        seeall_text=(TextView)itemView.findViewById(R.id.seeall_horizontal);

    }
}
public   class Horizontalphotostory extends RecyclerView.ViewHolder{
    RecyclerView horizontal_recylerview;
    TextView text_title,seeall_text;
    public Horizontalphotostory(View itemView) {
        super(itemView);
        horizontal_recylerview=(RecyclerView)itemView.findViewById(R.id.horizontal_recylerview);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        horizontal_recylerview.setLayoutManager(linearLayoutManager);
        text_title=(TextView)itemView.findViewById(R.id.title_horizontal);
        seeall_text=(TextView)itemView.findViewById(R.id.seeall_horizontal);
    }
}
    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }
 public    class Recyclerviewtaballadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        LayoutInflater inflater;

        ImageLoader mImageLoader;
        private final int VIEW_TYPE_ITEM = 1;
        private final int VIEW_TYPE_EVENT=6;
     private final int VIEW_TYPE_SPECIAL=8;
     private final int VIEW_TYPE_BEYOND=9;
        private final int VIEW_TYPE_LOADING = 3;
        private final int VIEW_TYPE_VIDEO=4;
        MediaPlayer mediaPlayer;
        boolean loading;
        OnLoadMoreListener onLoadMoreListener;
        private final int VIEW_TYPE_PHOTOSTORY = 2;
     private final int VIEW_TYPE_PHOTOSTORY_NEW = 7;
        private int visibleThreshold = 5;
        private int lastVisibleItem, totalItemCount;
        Context context;
        private  int currentvisiblecount;
        String urlaudio;
     private List<ItemModel>modelList=new ArrayList<>();
        @Override
        public int getItemCount() {
            return modelLists.size();
        }
        public Recyclerviewtaballadapter(List<ItemModel> students, RecyclerView recyclerView) {
            modelList = students;

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
                                    fabnews.setVisibility(View.VISIBLE);
                                }else {
                                    fabnews.setVisibility(View.GONE);
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

        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater mInflater = LayoutInflater.from ( parent.getContext () );
            switch ( viewType ) {
                case VIEW_TYPE_ITEM:
                    ViewGroup vImage = (ViewGroup) mInflater.inflate ( R.layout.horizontalnew_item_normal, parent, false );
                    Userviewholdertaball vhImage = new Userviewholdertaball( vImage );
                    return vhImage;
                case VIEW_TYPE_EVENT:
                    ViewGroup vsmall=(ViewGroup)mInflater.inflate(R.layout.horizontal_vertical_recyclerview,parent,false);
                    Horizontalevent horizontalsmalldesign=new Horizontalevent(vsmall);
                    return horizontalsmalldesign;
                case VIEW_TYPE_SPECIAL:
                    ViewGroup viewGroupspl=(ViewGroup)mInflater.inflate(R.layout.horizontal_vertical_recyclerview,parent,false);
                    Horizontalspecial horizontalspecial=new Horizontalspecial(viewGroupspl);
                    return horizontalspecial;
                case VIEW_TYPE_BEYOND:
                    ViewGroup viewGroupbeyond=(ViewGroup)mInflater.inflate(R.layout.horizontal_vertical_recyclerview,parent,false);
                    Horizontalbeyond horizontalbeyond=new Horizontalbeyond(viewGroupbeyond);
                    return horizontalbeyond;
                case VIEW_TYPE_PHOTOSTORY:
                    ViewGroup vImages = (ViewGroup) mInflater.inflate ( R.layout.feed_item_versionfour_photostory, parent, false );
                   UserViewHolderphotostories vhImages = new UserViewHolderphotostories( vImages );
                    return vhImages;
                case VIEW_TYPE_PHOTOSTORY_NEW:
                    ViewGroup vImagesnew = (ViewGroup) mInflater.inflate ( R.layout.horizontal_recyclerview, parent, false );
                    Horizontalphotostory vhImagesnew = new Horizontalphotostory( vImagesnew );
                    return vhImagesnew;
              case VIEW_TYPE_VIDEO:
                    ViewGroup vImageradio = ( ViewGroup ) mInflater.inflate ( R.layout.horizontal_recyclerview, parent, false );
                    Horizontalviewholder vhImageradio = new Horizontalviewholder ( vImageradio );
                    return vhImageradio;
                case VIEW_TYPE_LOADING:
                    ViewGroup vImageloading = (ViewGroup) mInflater.inflate ( R.layout.layout_loading_item, parent, false );
                  LoadingViewHolder vhImageloading = new LoadingViewHolder( vImageloading );
                    return vhImageloading;
            }

            return null;
        }
        @SuppressLint("ResourceAsColor")
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            fontname = sharedpreferences.getString(Fonts.FONT,"");
            if (holder instanceof Userviewholdertaball) {

                final Userviewholdertaball userViewHolder = (Userviewholdertaball) holder;

                String simplycity_title_fontPath = "fonts/playfairDisplayRegular.ttf";





                final Typeface seguiregular = Typeface.createFromAsset(getActivity().getAssets(), simplycity_title_fontPath);

                String simplycity_title_reqular = "fonts/Lora-Regular.ttf";;
                final Typeface seguiregular_bold = Typeface.createFromAsset(getActivity().getAssets(), simplycity_title_reqular);
                if (mImageLoader == null)
                    mImageLoader = MySingleton.getInstance(getActivity()).getImageLoader();

                String simplycity_title = "fonts/Lora-Regular.ttf";
                final Typeface pala = Typeface.createFromAsset(getActivity().getAssets(), simplycity_title);



                final ItemModel itemmodel = modelList.get(position);
                userViewHolder.comment_button.setText("Comment");
                userViewHolder.comment_button.setTypeface(seguiregular_bold);
                userViewHolder.comment_button.setTransformationMethod(null);
                userViewHolder.share_button.setText("Share");
                userViewHolder.share_button.setTypeface(seguiregular);
                userViewHolder.share_button.setTransformationMethod(null);

                save_item_count=itemmodel.getFavcount();

                if(colorcodes.equals("#FFFFFFFF"))
                {
                    userViewHolder.shortdescription.setTextColor(Color.GRAY);
                    userViewHolder.title_item.setTextColor(Color.BLACK);
                    userViewHolder.item_type_name.setTextColor(Color.GRAY);
                    userViewHolder.likescount.setTextColor(Color.BLACK);
                    userViewHolder.commentscount.setTextColor(Color.BLACK);
                    userViewHolder.line.setBackgroundColor(Color.LTGRAY);

                }
                else if(colorcodes.equals("#00B09B")){
                    userViewHolder.shortdescription.setTextColor(Color.WHITE);
                    userViewHolder.title_item.setTextColor(Color.WHITE);
                    userViewHolder.item_type_name.setTextColor(Color.WHITE);
                    userViewHolder.likescount.setTextColor(Color.WHITE);
                    userViewHolder.commentscount.setTextColor(Color.WHITE);
                    userViewHolder.line.setBackgroundColor(R.color.whitefood);
                }

                if(itemmodel.getCounttype()==1){
                    userViewHolder.like_imagebutton.setImageResource(R.mipmap.heartfullred);
                    userViewHolder.like_imagebutton.setTag("heartfullred");
                    /* userViewHolder.likes_button.setText("Liked");                     userViewHolder.likes_button.setTextColor(getResources().getColor(R.color.red));                     userViewHolder.likes_button.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.likered,0,0,0);                     userViewHolder.likes_button.setTypeface(seguiregular);                     userViewHolder.likes_button.setTransformationMethod(null);*/                 }else {                     userViewHolder.like_imagebutton.setImageResource(R.mipmap.heart);                     userViewHolder.like_imagebutton.setTag("heart");                     /*userViewHolder.likes_button.setText("Like");                     userViewHolder.likes_button.setTextColor(getResources().getColor(R.color.white));                     userViewHolder.likes_button.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.like,0,0,0);                     userViewHolder.likes_button.setTypeface(seguiregular);                     userViewHolder.likes_button.setTransformationMethod(null);*/                 }

                if(itemmodel.getFavcount()==1){
                    userViewHolder.save_button.setText("Saved");
                    userViewHolder.likes_button.setTextColor(getResources().getColor(R.color.red));
                    userViewHolder.save_button.setTypeface(seguiregular);
                    userViewHolder.save_button.setTransformationMethod(null);
                }else {
                    userViewHolder.save_button.setText("Save");
                    userViewHolder.save_button.setTypeface(seguiregular);
                    userViewHolder.save_button.setTransformationMethod(null);
                    userViewHolder.likes_button.setTextColor(getResources().getColor(R.color.white));
                }
                /*if(itemmodel.getQtypemain().equals("radio")||itemmodel.getQtypemain().equals("theatre")||itemmodel.getQtype().equals("Independent Movies")){
                    userViewHolder.play.setVisibility(View.VISIBLE);
                }else {
                    userViewHolder.play.setVisibility(View.GONE);
                }*/
                userViewHolder.shortdescription.setTypeface(pala);

                if(itemmodel.getPdate().equals("null")||itemmodel.getPdate().equals("")){
                    userViewHolder.shortdescription.setText(Html.fromHtml( itemmodel.getShortdescription()));
                }else {
                    if(itemmodel.getShortdescription().equals("")){
                        userViewHolder.shortdescription.setText(Html.fromHtml(itemmodel.getPdate()));
                    }else {
                        userViewHolder.shortdescription.setText(Html.fromHtml(itemmodel.getPdate()+"&nbsp;"+"|"+"&nbsp;"+itemmodel.getShortdescription()));
                    }
                }



                userViewHolder.editername.setTypeface(seguiregular);
                userViewHolder.editername.setText(itemmodel.getEditername());
                userViewHolder.shortdescription.setTypeface(pala);
                mediaPlayer=new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                userViewHolder.title_item.setText(Html.fromHtml(itemmodel.getTitle()));
                String simplycity_title_reugular= "fonts/TAU_Elango_Madhavi.TTF";
                tf= Typeface.createFromAsset(getActivity().getAssets(), simplycity_title);


                if(fontname.equals("playfair")){
                    tf = Typeface.createFromAsset(getActivity().getAssets(), simplycity_title_reugular);
                    userViewHolder.title_item.setTypeface(tf);
                    userViewHolder.title_item.setTextSize(30);
                }else {
                    tf=Typeface.createFromAsset(getActivity().getAssets(),Fonts.muktamalar);
                    userViewHolder.title_item.setTypeface(tf);
                    userViewHolder.title_item.setTextSize(20);
                }

                if(itemmodel.getEditername().equals("")){                     userViewHolder.item_type_name.setText(Html.fromHtml(itemmodel.getQtype()));                 }else {                     userViewHolder.item_type_name.setText(Html.fromHtml(itemmodel.getQtype() + "&nbsp;"+"&nbsp;"+"&nbsp;" + "|" + "&nbsp;"+"&nbsp;"+"&nbsp;" + itemmodel.getEditername()));                 }                userViewHolder.item_type_name.setTypeface(seguiregular_bold);
                //userViewHolder.date.setText(itemmodel.getPdate());
                userViewHolder.likescount.setTypeface(seguiregular_bold);
                userViewHolder.commentscount.setTypeface(seguiregular_bold);
                userViewHolder.date.setTypeface(seguiregular);
                if(itemmodel.getLikescount()==0){                         userViewHolder.likescount.setText(Html.fromHtml("0"+"&nbsp;" +"" +"Like"));                     }else {                         userViewHolder.likescount.setText(Html.fromHtml(itemmodel.getLikescount()+"&nbsp;"+"Like"));                      }                     if(itemmodel.getCommentscount()==0){                          userViewHolder.commentscount.setText(Html.fromHtml("0"+"&nbsp;" +"" +"Comment"));                     }else {                         userViewHolder.commentscount.setText(Html.fromHtml(itemmodel.getCommentscount()+"&nbsp;"  +"Comments"));                     }                                        if(itemmodel.getCommentscount()==0){                      userViewHolder.commentscount.setText(Html.fromHtml("0"+"&nbsp;"  +"Comment"));                 }else {                     userViewHolder.commentscount.setText(Html.fromHtml(itemmodel.getCommentscount()+"&nbsp;"  +"Comments"));                 }
                if(itemmodel.getCommentscount()==0){

                    userViewHolder.commentscount.setText(Html.fromHtml("0"+"&nbsp;"  +"Comment"));
                }else {
                    userViewHolder.commentscount.setText(Html.fromHtml(itemmodel.getCommentscount()+"&nbsp;"  +"Comments"));
                }
                if(itemmodel.getImage()!=null){
                    userViewHolder.item_image.setImageUrl(itemmodel.getImage(),mImageLoader);
                }else {
                    userViewHolder.item_image.setVisibility(View.GONE);
                }

                userViewHolder.setClickListener(new RecyclerView_OnClickListener.OnClickListener() {

                    @Override
                    public void OnItemClick(View view, int position) {
                        switch (view.getId()) {
                            case R.id.listlayout_taball:

                                // Show a toast on clicking layout


                                String type = ((ItemModel) modelLists.get(position)).getQtypemain();
                                String qtype = ((ItemModel) modelLists.get(position)).getQtype();
                                String ids = ((ItemModel) modelLists.get(position)).getId();



                                if(type.equals("news")||type.equals("National")||type.equals("International")) {
                                    Intent intent = new Intent(getActivity(), TamilNewsDescription.class);
                                    intent.putExtra("ID", ids);
                                    startActivity(intent);
                                }else if(type.equals("article")){
                                    Intent intent = new Intent(getActivity(), TamilArticledescription.class);
                                    intent.putExtra("ID", ids);
                                    startActivity(intent);

                                }else if (type.equals("doit")){
                                    Intent intent = new Intent(getActivity(), DoitDescriptiontamil.class);
                                    intent.putExtra("ID", ids);
                                    startActivity(intent);
                                }else if(type.equals("farming")){
                                    Intent intent = new Intent(getActivity(), Farmingdescriptiontamil.class);
                                    intent.putExtra("ID", ids);
                                    startActivity(intent);
                                }else if(type.equals("food")||type.equals("foodtip")){
                                    if(qtype.equals("Food & Cooking")){
                                        Intent intent = new Intent(getActivity(), FoodAndCookDescriptionPagetamil.class);
                                        intent.putExtra("ID", ids);
                                        startActivity(intent);
                                    }else {
                                        Intent intent = new Intent(getActivity(), TipsDescriptionTamil.class);
                                        intent.putExtra("ID", ids);
                                        startActivity(intent);
                                    }


                                }else if(type.equals("govt")){
                                    Intent intent = new Intent(getActivity(), Govtdescriptiontamil.class);
                                    intent.putExtra("ID", ids);
                                    startActivity(intent);
                                }else if(type.equals("health")){
                                    Intent intent = new Intent(getActivity(), Healthdescriptiontamil.class);
                                    intent.putExtra("ID", ids);
                                    startActivity(intent);
                                }else if(type.equals("science")){
                                    Intent intent = new Intent(getActivity(), ScienceandTechnologyDescriptiontamil.class);
                                    intent.putExtra("ID", ids);

                                    startActivity(intent);
                                }else if(type.equals("sports")){
                                    Intent intent = new Intent(getActivity(), SportsnewsDescription.class);
                                    intent.putExtra("ID", ids);
                                    startActivity(intent);
                                }else if(type.equals("travels")){
                                    Intent intent = new Intent(getActivity(), TravelsDescriptiontamil.class);
                                    intent.putExtra("ID", ids);
                                    startActivity(intent);
                                }else if(type.equals("event")){
                                    Intent intent = new Intent(getActivity(), EventsDescription.class);
                                    intent.putExtra("ID", ids);
                                    startActivity(intent);
                                }else if(type.equalsIgnoreCase("Radio")){
                                    Intent intent = new Intent(getActivity(), RadioNotificationplayer.class);
                                    intent.putExtra("URL", itemmodel.getPlayurl());
                                    intent.putExtra("TITLE", itemmodel.getTitle());
                                    intent.putExtra("IMAGE", itemmodel.getImage());
                                    startActivity(intent);
                                }else if(type.equalsIgnoreCase("Music")){
                                    Intent intent = new Intent(getActivity(), RadioNotificationplayer.class);
                                    intent.putExtra("URL", itemmodel.getPlayurl());
                                    intent.putExtra("TITLE", itemmodel.getTitle());
                                    intent.putExtra("IMAGE", itemmodel.getImage());
                                    startActivity(intent);
                                }else if(type.equalsIgnoreCase("Job")){
                                    Intent intent = new Intent(getActivity(), JobsDetailPagetamil.class);
                                    intent.putExtra("ID", ids);
                                    intent.putExtra("TITLE", itemmodel.getTitle());
                                    startActivity(intent);
                                }
                                else if(type.equalsIgnoreCase("theatre")){
                                    Intent intent = new Intent(getActivity(), YoutubeVideoPlayer.class);
                                    intent.putExtra("ID", ids);
                                    intent.putExtra("TITLE",itemmodel.getTitle());
                                    intent.putExtra("URL",itemmodel.getYoutubelink());
                                    startActivity(intent);
                                } else if(type.equals("lifestyle")){
                                    Intent intent = new Intent(getActivity(), LifestyleDetail.class);
                                    intent.putExtra("ID", ids);
                                    startActivity(intent);
                                }else if(type.equalsIgnoreCase("columns")){
                                    Intent intent = new Intent(getActivity(), Columnsdetailpage.class);
                                    intent.putExtra("ID", ids);
                                    startActivity(intent);
                                }else if (type.equals("columnist")){
                                    Intent intent = new Intent(getActivity(), Columnistdetail.class);
                                    intent.putExtra("ID", ids);
                                    startActivity(intent);
                                }
                                else if(type.equalsIgnoreCase("education")){
                                    Intent intent = new Intent(getActivity(), EducationDescriptiontamil.class);
                                    intent.putExtra("ID", ids);
                                    startActivity(intent);
                                }else
                                if(itemmodel.getQtype().equals("Sponsered")||itemmodel.getQtype().equals("Sponsored")){
                                    if(itemmodel.getAds().startsWith("http://simpli")){
                                        Intent intent = new Intent(getActivity(), AdvertisementPage.class);
                                        intent.putExtra("ID", itemmodel.getAds());
                                        startActivity(intent);
                                    }else {
                                        Intent intent = new Intent(getActivity(), AdvertisementPage.class);
                                        intent.putExtra("ID", itemmodel.getAds());
                                        startActivity(intent);
                                    }


                                }
                                break;
                            case R.id.taball_play_pause_main:
                                Log.e("CLick","MainRaio");

                                if(itemmodel.getQtype().equals("Independent Movies")||itemmodel.getQtypemain().equals("theatre")){
                                    Intent intent = new Intent(getActivity(), YoutubeVideoPlayer.class);
                                    intent.putExtra("ID", itemmodel.getId());
                                    intent.putExtra("TITLE",itemmodel.getTitle());
                                    intent.putExtra("URL",itemmodel.getYoutubelink());
                                    startActivity(intent);
                                }else {
                                    urlaudio = itemmodel.getId();
                                    onButtonPressed(itemmodel.getPlayurl(), itemmodel.getTitle(),itemmodel.getImage());
                                    userViewHolder.play.setVisibility(View.GONE);
                                }
                                break;

                            case R.id.alltab_likescount:
                                FragmentTransaction ftlike = getChildFragmentManager().beginTransaction();
                                LikeListFragment frags;
                                frags = new LikeListFragment();
                                Bundle argss = new Bundle();
                                argss.putString("ID", itemmodel.getId());
                                argss.putString("QTYPE",itemmodel.getQtypemain());
                                frags.setArguments(argss);
                                frags.show(ftlike, "txn_tag");
                                break;

                            case R.id.button_likes:
                                if(myprofileid!=null) {
                                    String backgroundImageName = String.valueOf(userViewHolder.like_imagebutton.getTag());
                                    Log.e("RUN","with"+backgroundImageName);
                                    if(backgroundImageName.equals("heart")){
                                        userViewHolder.like_imagebutton.setImageResource(R.mipmap.heartfullred);
                                        userViewHolder.like_imagebutton.setTag("heartfullred");
                                    }else if(backgroundImageName.equals("heartfullred")) {
                                        userViewHolder.like_imagebutton.setImageResource(R.mipmap.heart);
                                    }else {

                                    }
                                    StringRequest likes=new StringRequest(Request.Method.POST, URLLIKES, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            String res;
                                            Log.e("RES",response.toString());
                                            try {
                                                Log.e("RES", "START");
                                                JSONObject data = new JSONObject(response.toString());
                                                String dir = data.getString("result");
                                                Log.d("RES", dir);
                                                JSONObject object=new JSONObject(dir);
                                                String dir2=object.getString("message");
                                                Log.d("RES", dir2);

                                                for (int i = 0; i < object.length(); i++) {

                                                    String dirs = object.getString("message");

                                                    Log.d("RES", dirs);
                                                    res=object.getString("message");
                                                    like_finalvalues=object.getInt("total_likes");
                                                    Log.e("RES",res.toString());


                                                    if(res.equals("Liked")){
                                                        System.out.println(itemmodel.getId());
                                                        like_finalvalues=object.getInt("total_likes");
                                                        Log.e("RES",String.valueOf(like_finalvalues));



                                                        userViewHolder.like_imagebutton.setImageResource(R.mipmap.heartfullred); 				userViewHolder.like_imagebutton.setTag("heartfullred");
                                                    }else if(res.equals("Like")){
                                                        like_finalvalues=object.getInt("total_likes");
                                                        Log.e("RES","dis"+String.valueOf(like_finalvalues));



                                                        userViewHolder.like_imagebutton.setImageResource(R.mipmap.heart); 				userViewHolder.like_imagebutton.setTag("heart");
                                                    }

                                                    userViewHolder.    likescount.setText(Html.fromHtml(like_finalvalues + "&nbsp;" + "Likes"));


                                                }

                                            }catch (JSONException e){

                                            }
                                            /*String res=response.toString();
                                            res = res.replace(" ", "");
                                            res = res.trim();
                                            if(res.equalsIgnoreCase("yes")){
                                                System.out.println(itemmodel.getId());
                                                if(itemmodel.getCounttype()==1){
                                                    like_finalvalues=itemmodel.getLikescount();
                                                }else {
                                                    like_finalvalues=itemmodel.getLikescount()+1;
                                                }

                                                userViewHolder.like_imagebutton.setImageResource(R.mipmap.heartfullred);
                                                userViewHolder.like_imagebutton.setTag("heartfullred");
                                                *//*userViewHolder.likes_button.setText("Liked");
                                                userViewHolder.likes_button.setTextColor(getActivity().getResources().getColor(R.color.red));
                                                userViewHolder.likes_button.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.likered,0,0,0);
                                                userViewHolder.likes_button.setTypeface(seguiregular);
                                                userViewHolder.likes_button.setTransformationMethod(null);*//*
                                            }else if(res.equalsIgnoreCase("no")){
                                                if(itemmodel.getCounttype()==1){
                                                    like_finalvalues=itemmodel.getLikescount()-1;
                                                }else {
                                                    like_finalvalues=itemmodel.getLikescount();
                                                }
                                                System.out.println(itemmodel.getId());
                                                userViewHolder.like_imagebutton.setImageResource(R.mipmap.heart);
                                                userViewHolder.like_imagebutton.setTag("heart");
                                                *//*userViewHolder.likes_button.setText("Like");
                                                userViewHolder.likes_button.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.like,0,0,0);
                                                userViewHolder.likes_button.setTypeface(seguiregular);
                                                userViewHolder.likes_button.setTransformationMethod(null);*//*
                                            }
                                            if(like_finalvalues==0||like_finalvalues==-1){
                                                System.out.println(itemmodel.getId());
                                                userViewHolder.    likescount.setVisibility(View.GONE);
                                            }else {
                                                System.out.println(itemmodel.getId());
                                                System.out.println(like_finalvalues);
                                                userViewHolder.    countlayout.setVisibility(View.VISIBLE);
                                                userViewHolder.    likescount.setVisibility(View.VISIBLE);
                                                userViewHolder.    likescount.setText(Html.fromHtml(like_finalvalues + "&nbsp;" + "Likes"));

                                                like_finalvalues=0;
                                            }*/
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                        }
                                    }){
                                        protected Map<String,String> getParams()throws AuthFailureError{
                                            Map<String,String> param=new Hashtable<String, String>();
                                            String ids=itemmodel.getId();
                                            param.put(QID, ids);
                                            param.put(USERID, myprofileid);
                                            param.put(QTYPE, itemmodel.getQtypemain());
                                            return param;
                                        }
                                    };
                                    RequestQueue likesqueue=Volley.newRequestQueue(getActivity());
                                    likes.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                                    likesqueue.add(likes);

                                }else {
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString(Activity, "mainversion");
                                    editor.putString(CONTENTID, "0");
                                    editor.commit();
                                    Intent sign=new Intent(getActivity(),SigninpageActivity.class);
                                    startActivity(sign);

                                }


                                break;
                            case R.id.taball_savepage:
                                if(myprofileid!=null) {

                                    if (save_item_count == 1) {
                                        userViewHolder.save_button.setText("Save");
                                        userViewHolder.save_button.setTextColor(getResources().getColor(R.color.white));
                                        userViewHolder.save_button.setTypeface(seguiregular);
                                        userViewHolder.save_button.setTransformationMethod(null);
                                        save_item_count--;





                                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLSAVE,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String s) {
                                                        //Disimissing the progress dialog

                                                        //Showing toast message of the response
                                                        if (s.equalsIgnoreCase("no")) {
                                                            //Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show() ;
                                                        } else {
                                                            Log.e("response:", s);

                                                        }

                                                    }
                                                },
                                                new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError volleyError) {
                                                        //Dismissing the progress dialog
                                                        //loading.dismiss();

                                                        //Showing toast
                                                        //  Toast.makeText(CityCenterCommentPage.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                                                    }
                                                }) {
                                            @Override
                                            protected Map<String, String> getParams() throws AuthFailureError {

                                                Map<String, String> params = new Hashtable<String, String>();

                                                //Adding parameters



                                                params.put(QID, itemmodel.getId());
                                                params.put(USERID, myprofileid);
                                                params.put(QTYPE, itemmodel.getQtypemain());



                                                return params;
                                            }
                                        };

                                        //Creating a Request Queue
                                        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

                                        //Adding request to the queue
                                        requestQueue.add(stringRequest);

                                    } else {
                                        userViewHolder.save_button.setText("Save");
                                        userViewHolder.save_button.setTextColor(getResources().getColor(R.color.red));
                                        userViewHolder.save_button.setTypeface(seguiregular);
                                        userViewHolder.save_button.setTransformationMethod(null);
                                        save_item_count++;



                                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLSAVE,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String s) {
                                                        //Disimissing the progress dialog

                                                        //Showing toast message of the response
                                                        if (s.equalsIgnoreCase("no")) {
                                                            //Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show() ;
                                                        } else {
                                                            Log.e("response:", s);


                                                        }

                                                    }
                                                },
                                                new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError volleyError) {
                                                        //Dismissing the progress dialog
                                                        //loading.dismiss();

                                                        //Showing toast
                                                        //  Toast.makeText(CityCenterCommentPage.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                                                    }
                                                }) {
                                            @Override
                                            protected Map<String, String> getParams() throws AuthFailureError {
                                                //Converting Bitmap to String

                                                //Getting Image Name

                                                //Creating parameters
                                                Map<String, String> params = new Hashtable<String, String>();

                                                //Adding parameters



                                                params.put(QID, itemmodel.getId());
                                                params.put(USERID, myprofileid);
                                                params.put(QTYPE, itemmodel.getQtypemain());



                                                return params;
                                            }
                                        };

                                        //Creating a Request Queue
                                        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

                                        //Adding request to the queue
                                        requestQueue.add(stringRequest);
                                        //Toast.makeText(getActivity(),count,Toast.LENGTH_LONG).show();
                                    }
                                }else {
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString(Activity, "mainversiontamil");
                                    editor.putString(CONTENTID, "0");
                                    editor.commit();
                                    Intent signin=new Intent(getActivity(),SigninpageActivity.class);
                                    startActivity(signin);

                                }
                                break;
                            case R.id.button_comment:

                                if(myprofileid!=null) {
                                    FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                                    Tabnews.MyDialogFragment frag;
                                    frag = new Tabnews.MyDialogFragment();
                                    Bundle args = new Bundle();
                                    args.putString("POSTID", itemmodel.getId());
                                    args.putString("USERID", myprofileid);
                                    args.putString("QTYPE",itemmodel.getQtypemain());
                                    frag.setArguments(args);
                                    frag.show(ft, "txn_tag");
                                }else {
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString(Activity, "mainversiontamil");
                                    editor.putString(CONTENTID, "0");
                                    editor.commit();
                                    Intent signin=new Intent(getActivity(),SigninpageActivity.class);
                                    startActivity(signin);

                                }
                                break;
                            case R.id.button_share:
                                Intent sendIntent = new Intent();
                                sendIntent.setAction(Intent.ACTION_SEND);
                                if(itemmodel.getSharingurl().equals("")){
                                    sendIntent.putExtra(Intent.EXTRA_TEXT, itemmodel.getTitle()+ "\n" + itemmodel.getAds()+"\n"+"\n"+"\n"+"Receive instant updates by installing Simplicity for iPhone/iPad,Android and Windows 10(desktop & Mobile)(http://goo.gl/Sv3vfc)");
                                }else {
                                    sendIntent.putExtra(Intent.EXTRA_TEXT, itemmodel.getTitle() + "\n" + itemmodel.getSharingurl() + "\n" + "\n" + "\n" + "Receive instant updates by installing Simplicity for iPhone/iPad,Android and Windows 10(desktop & Mobile)(http://goo.gl/Sv3vfc)");
                                }                                sendIntent.setType("text/plain");
                                startActivity(Intent.createChooser(sendIntent, "Share using"));
                                break;
                        }
                    }

                });
            }else if(holder instanceof Horizontalviewholder){

                Horizontalviewholder horizontalviewholder=(Horizontalviewholder)holder;
                String simplycity_title_fontPath = "fonts/playfairDisplayRegular.ttf";
                final Typeface seguiregular = Typeface.createFromAsset(getActivity().getAssets(), simplycity_title_fontPath);
                ItemModel model=modelLists.get(position);
                List<ItemModel>videolist=model.getVideoList();
                String title=null;

                for(int j=0;j<videolist.size();j++){
                    title=videolist.get(j).getSubqueuetitle();
                }

                horizontalviewholder.text_title.setTextColor(Color.WHITE);
                horizontalviewholder.text_title.setText(title);
                horizontalviewholder.seeall_text.setText("See all");
                horizontalviewholder.seeall_text.setTypeface(seguiregular);
                horizontalviewholder.text_title.setTypeface(seguiregular);
                horizontalviewholder.seeall_text.setTextColor(Color.WHITE);
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
                horizontalviewholder.horizontal_recylerview.setLayoutManager(linearLayoutManager);
                Horizontaladaptertamil horizontaladapter=new Horizontaladaptertamil(videolist,horizontalviewholder.horizontal_recylerview,getActivity());
                horizontalviewholder.horizontal_recylerview.setAdapter(horizontaladapter);

                horizontalviewholder.seeall_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(getActivity(),MainPageEnglish.class);
                        i.putExtra("ID","2");
                        i.putExtra("TAB","video");
                        startActivity(i);
                    }
                });

            }else if(holder instanceof Horizontalphotostory){
                Horizontalphotostory horizontalphotostory=(Horizontalphotostory)holder;
                String simplycity_title_fontPath = "fonts/playfairDisplayRegular.ttf";
                final Typeface seguiregular = Typeface.createFromAsset(getActivity().getAssets(), simplycity_title_fontPath);
                ItemModel model=modelLists.get(position);
                List<ItemModel>photolist=model.getPhotoStoryList();
                horizontalphotostory.text_title.setTextColor(Color.WHITE);
                String title=null;

                for(int j=0;j<photolist.size();j++){
                    title=photolist.get(j).getSubqueuetitle();
                }

                horizontalphotostory.text_title.setText(title);
                horizontalphotostory.seeall_text.setText("See all");
                horizontalphotostory.seeall_text.setTypeface(seguiregular);
                horizontalphotostory.text_title.setTypeface(seguiregular);
                horizontalphotostory.seeall_text.setTextColor(Color.WHITE);
                HorizontalPhotostoryadaptertamil horizontalPhotostoryadapter=new HorizontalPhotostoryadaptertamil(photolist,horizontalphotostory.horizontal_recylerview,getActivity());
                horizontalphotostory.horizontal_recylerview.setAdapter(horizontalPhotostoryadapter);

                horizontalphotostory.seeall_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(getActivity(),MainPageEnglish.class);
                        i.putExtra("ID","2");
                        i.putExtra("TAB","photo");
                        startActivity(i);
                    }
                });

            }else if (holder instanceof Horizontalevent){

                Horizontalevent horizontalsmalldesign=(Horizontalevent) holder;
                String simplycity_title_fontPath = "fonts/playfairDisplayRegular.ttf";
                final Typeface seguiregular = Typeface.createFromAsset(getActivity().getAssets(), simplycity_title_fontPath);
                final ItemModel itemmodel = modelLists.get(position);
                List<ItemModel>list=itemmodel.getEventlist();
                String title=null;

                for(int j=0;j<list.size();j++){
                    title=list.get(j).getSubqueuetitle();
                }

                horizontalsmalldesign.text_title.setText(title);
               horizontalsmalldesign.seeall_text.setText("See all");
               horizontalsmalldesign.seeall_text.setTypeface(seguiregular);
               horizontalsmalldesign.text_title.setTypeface(seguiregular);
                VerticalAdapterstamil adapters=new VerticalAdapterstamil(list,horizontalsmalldesign.horizontal_recylerview,getActivity());
                horizontalsmalldesign.horizontal_recylerview.setAdapter(adapters);

                horizontalsmalldesign.seeall_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(getActivity(),MainPageEnglish.class);
                        i.putExtra("ID","3");
                        i.putExtra("TAB","1");
                        startActivity(i);
                    }
                });
            }else if (holder instanceof Horizontalbeyond){

                Horizontalbeyond horizontalbeyond=(Horizontalbeyond) holder;
                String simplycity_title_fontPath = "fonts/playfairDisplayRegular.ttf";
                final Typeface seguiregular = Typeface.createFromAsset(getActivity().getAssets(), simplycity_title_fontPath);
                final ItemModel itemmodel = modelLists.get(position);
                List<ItemModel>list=itemmodel.getBeyondlist();
                String title=null;

                for(int j=0;j<list.size();j++){
                    title=list.get(j).getSubqueuetitle();
                }

                horizontalbeyond.text_title.setText(title);
                horizontalbeyond.seeall_text.setText("See all");
                horizontalbeyond.seeall_text.setTypeface(seguiregular);
                horizontalbeyond.text_title.setTypeface(seguiregular);
                VerticalAdapterstamil adapters=new VerticalAdapterstamil(list,horizontalbeyond.horizontal_recylerview,getActivity());
                horizontalbeyond.horizontal_recylerview.setAdapter(adapters);

                horizontalbeyond.seeall_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(getActivity(),MainPageEnglish.class);
                        i.putExtra("ID","1");
                        i.putExtra("TAB","beyond");
                        startActivity(i);
                    }
                });
            }else if (holder instanceof Horizontalspecial){

                Horizontalspecial horizontalspecial=(Horizontalspecial) holder;
                String simplycity_title_fontPath = "fonts/playfairDisplayRegular.ttf";
                final Typeface seguiregular = Typeface.createFromAsset(getActivity().getAssets(), simplycity_title_fontPath);
                final ItemModel itemmodel = modelLists.get(position);
                List<ItemModel>list=itemmodel.getSpeciallist();
                String title=null;

                    for(int j=0;j<list.size();j++){
                      title=list.get(j).getSubqueuetitle();
                    }


                horizontalspecial.text_title.setText(title);
                horizontalspecial.seeall_text.setText("See all");
                horizontalspecial.seeall_text.setTypeface(seguiregular);
                horizontalspecial.text_title.setTypeface(seguiregular);
                VerticalAdapterstamil adapters=new VerticalAdapterstamil(list,horizontalspecial.horizontal_recylerview,getActivity());
                horizontalspecial.horizontal_recylerview.setAdapter(adapters);

                horizontalspecial.seeall_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(getActivity(),MainPageEnglish.class);
                        i.putExtra("ID","1");
                        i.putExtra("TAB","specialcolumn");
                        startActivity(i);
                    }
                });

            }



            else if(holder instanceof UserViewHolderphotostories){

                final UserViewHolderphotostories userViewHolder = (UserViewHolderphotostories) holder;

                String simplycity_title_fontPath = "fonts/playfairDisplayRegular.ttf";
                final Typeface seguiregular = Typeface.createFromAsset(getActivity().getAssets(), simplycity_title_fontPath);
                if (mImageLoader == null)
                    mImageLoader = MySingleton.getInstance(getActivity()).getImageLoader();


                final ItemModel itemmodel = modelLists.get(position);
                userViewHolder.comment_button.setText("Comment");
                userViewHolder.comment_button.setTypeface(seguiregular);
                userViewHolder.comment_button.setTransformationMethod(null);
                userViewHolder.share_button.setText("Share");
                userViewHolder.share_button.setTypeface(seguiregular);
                userViewHolder.share_button.setTransformationMethod(null);

                save_item_count=itemmodel.getFavcount();
                if(colorcodes.equals("#FFFFFFFF"))
                {
                    userViewHolder.shortdescription.setTextColor(Color.GRAY);
                    userViewHolder.title_item.setTextColor(Color.BLACK);
                    userViewHolder.item_type_name.setTextColor(Color.GRAY);
                    userViewHolder.likescount.setTextColor(Color.BLACK);
                    userViewHolder.commentscount.setTextColor(Color.BLACK);
                    userViewHolder.line.setBackgroundColor(Color.LTGRAY);

                }
                else if(colorcodes.equals("#00B09B")){
                    userViewHolder.shortdescription.setTextColor(Color.WHITE);
                    userViewHolder.title_item.setTextColor(Color.WHITE);
                    userViewHolder.item_type_name.setTextColor(Color.WHITE);
                    userViewHolder.likescount.setTextColor(Color.WHITE);
                    userViewHolder.commentscount.setTextColor(Color.WHITE);
                    userViewHolder.line.setBackgroundColor(R.color.whitefood);
                }
                if(itemmodel.getCounttype()==1){

                    userViewHolder.like_imagebutton.setImageResource(R.mipmap.heartfullred);

                   /* userViewHolder.likes_button.setText("Liked");
                    userViewHolder.likes_button.setTextColor(getResources().getColor(R.color.red));
                    userViewHolder.likes_button.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.likered,0,0,0);
                    userViewHolder.likes_button.setTypeface(seguiregular);
                    userViewHolder.likes_button.setTransformationMethod(null);*/
                }else {
                    userViewHolder.like_imagebutton.setImageResource(R.mipmap.heart);
                   /* userViewHolder.likes_button.setText("Like");
                    userViewHolder.likes_button.setTextColor(getResources().getColor(R.color.white));
                    userViewHolder.likes_button.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.like,0,0,0);
                    userViewHolder.likes_button.setTypeface(seguiregular);
                    userViewHolder.likes_button.setTransformationMethod(null);*/
                }

                if(itemmodel.getFavcount()==1){
                    userViewHolder.save_button.setText("Saved");
                    userViewHolder.likes_button.setTextColor(getResources().getColor(R.color.red));
                    userViewHolder.save_button.setTypeface(seguiregular);
                    userViewHolder.save_button.setTransformationMethod(null);
                }else {
                    userViewHolder.save_button.setText("Save");
                    userViewHolder.save_button.setTypeface(seguiregular);
                    userViewHolder.save_button.setTransformationMethod(null);
                    userViewHolder.likes_button.setTextColor(getResources().getColor(R.color.white));
                }

                if(itemmodel.getPdate().equals("null")||itemmodel.getPdate().equals("")){                     userViewHolder.shortdescription.setText(Html.fromHtml( itemmodel.getShortdescription()));                 }else {                    if(itemmodel.getShortdescription().equals("")){                        userViewHolder.shortdescription.setText(Html.fromHtml(itemmodel.getPdate()));                     }else {                        userViewHolder.shortdescription.setText(Html.fromHtml(itemmodel.getPdate()+"&nbsp;"+"|"+"&nbsp;"+itemmodel.getShortdescription()));                     }                 }

                userViewHolder.editername.setTypeface(seguiregular);
                userViewHolder.editername.setText(itemmodel.getEditername());

                userViewHolder.title_item.setText(Html.fromHtml(itemmodel.getTitle()));
                if(fontname.equals("playfair")){
                    //  tf = Typeface.createFromAsset(getActivity().getAssets(), String.valueOf(seguiregular));
                    userViewHolder.title_item.setTypeface(seguiregular);
                }else {
                    tf=Typeface.createFromAsset(getActivity().getAssets(),Fonts.muktamalar);
                    userViewHolder.title_item.setTypeface(tf);
                    userViewHolder.title_item.setTextSize(20);
                }
                if(itemmodel.getEditername().equals("")){                     userViewHolder.item_type_name.setText(Html.fromHtml(itemmodel.getQtype()));                 }else {                     userViewHolder.item_type_name.setText(Html.fromHtml(itemmodel.getQtype() + "&nbsp;"+"&nbsp;"+"&nbsp;" + "|" + "&nbsp;"+"&nbsp;"+"&nbsp;" + itemmodel.getEditername()));                 }                userViewHolder.item_type_name.setTypeface(seguiregular);
                // userViewHolder.date.setText(itemmodel.getPdate());
                // userViewHolder.likescount.setTypeface(seguiregular);
                userViewHolder.date.setTypeface(seguiregular);
                if(itemmodel.getLikescount()==0){                         userViewHolder.likescount.setText(Html.fromHtml("0"+"&nbsp;" +"" +"Like"));                     }else {                         userViewHolder.likescount.setText(Html.fromHtml(itemmodel.getLikescount()+"&nbsp;"+"Like"));                      }                     if(itemmodel.getCommentscount()==0){                          userViewHolder.commentscount.setText(Html.fromHtml("0"+"&nbsp;" +"" +"Comment"));                     }else {                         userViewHolder.commentscount.setText(Html.fromHtml(itemmodel.getCommentscount()+"&nbsp;"  +"Comments"));                     }                                        if(itemmodel.getCommentscount()==0){                      userViewHolder.commentscount.setText(Html.fromHtml("0"+"&nbsp;"  +"Comment"));                 }else {                     userViewHolder.commentscount.setText(Html.fromHtml(itemmodel.getCommentscount()+"&nbsp;"  +"Comments"));                 }



                String powers = "";
                String powerstwo = "";
                // Chcek for empty status message
                if (itemmodel.getAlbumcount()==0) {

                    userViewHolder.feedImageView.setImageUrl(itemmodel.getImage(), mImageLoader);
                    userViewHolder.feedImageView.setDefaultImageResId(R.mipmap.cityicon);

                    userViewHolder.feedImageView.setVisibility(View.VISIBLE);

                    userViewHolder.feedImageView_typetwo_one.setVisibility(View.GONE);
                    userViewHolder.feedImageView_typetwo_two.setVisibility(View.GONE);
                    userViewHolder.feed_typethree_ones.setVisibility(View.GONE);
                    userViewHolder.feed_typethree_twos.setVisibility(View.GONE);
                    userViewHolder.feed_typethree_threes.setVisibility(View.GONE);
                    userViewHolder.feedImageView_typefour_one.setVisibility(View.GONE);
                    userViewHolder.feedImageView_typefour_two.setVisibility(View.GONE);
                    userViewHolder.feedImageView_typefour_three.setVisibility(View.GONE);
                    userViewHolder.feedImageView_typefour_four.setVisibility(View.GONE);
                    int j;
if(itemmodel.getAlbum()==null){

}else {
    for( j=0;j<itemmodel.getAlbum().size();j++) {
        powerstwo = itemmodel.getAlbum().get(0);
    }
    userViewHolder.feedImageView.setImageUrl(powerstwo, mImageLoader);
}


                }else if(itemmodel.getAlbumcount()==2){
                    int j;


                    for( j=0;j<itemmodel.getAlbum().size();j++) {
                        powerstwo = itemmodel.getAlbum().get(j);

                        if(j==0) {
                            System.out.println("two1 -->"+itemmodel.getAlbum().get(j));
                            userViewHolder.feedImageView_typetwo_one.setImageUrl(powerstwo, mImageLoader);
                            userViewHolder.feedImageView_typetwo_one.setDefaultImageResId(R.mipmap.ic_launcher);
                            userViewHolder.feedImageView_typetwo_one.setVisibility(View.VISIBLE);

                        }else if(j==1) {
                            System.out.println(" two2-->"+itemmodel.getAlbum().get(j));
                            userViewHolder.feedImageView_typetwo_two.setImageUrl(powerstwo, mImageLoader);
                            userViewHolder.feedImageView_typetwo_two.setDefaultImageResId(R.mipmap.ic_launcher);
                            userViewHolder.feedImageView_typetwo_two.setVisibility(View.VISIBLE);

                        }
                        userViewHolder.feedImageView.setVisibility(View.GONE);

                        userViewHolder.feed_typethree_ones.setVisibility(View.GONE);
                        userViewHolder.feed_typethree_twos.setVisibility(View.GONE);
                        userViewHolder.feed_typethree_threes.setVisibility(View.GONE);
                        userViewHolder.feedImageView_typefour_one.setVisibility(View.GONE);
                        userViewHolder.feedImageView_typefour_two.setVisibility(View.GONE);
                        userViewHolder.feedImageView_typefour_three.setVisibility(View.GONE);
                        userViewHolder.feedImageView_typefour_four.setVisibility(View.GONE);
                    }
                }else if(itemmodel.getAlbumcount()==3){
                    int j;
                    for( j=0;j<itemmodel.getAlbum().size();j++) {
                        powerstwo = itemmodel.getAlbum().get(j);

                        if(j==0) {
                            System.out.println("three -->"+itemmodel.getAlbum().get(j));
                            userViewHolder.feed_typethree_ones.setImageUrl(powerstwo, mImageLoader);
                            userViewHolder.feed_typethree_ones.setDefaultImageResId(R.mipmap.ic_launcher);
                            userViewHolder.feed_typethree_ones.setVisibility(View.VISIBLE);

                        }else if(j==1) {
                            System.out.println(" three2-->"+itemmodel.getAlbum().get(j));
                            userViewHolder.feed_typethree_twos.setImageUrl(powerstwo, mImageLoader);
                            userViewHolder.feed_typethree_twos.setDefaultImageResId(R.mipmap.ic_launcher);
                            userViewHolder.feed_typethree_twos.setVisibility(View.VISIBLE);

                        }else if (j==2) {
                            System.out.println("three3 -->"+itemmodel.getAlbum().get(j));
                            userViewHolder.feed_typethree_threes.setImageUrl(powerstwo, mImageLoader);
                            userViewHolder.feed_typethree_threes.setDefaultImageResId(R.mipmap.ic_launcher);
                            userViewHolder.feed_typethree_threes.setVisibility(View.VISIBLE);

                        }
                        userViewHolder.feedImageView_typetwo_one.setVisibility(View.GONE);
                        userViewHolder.feedImageView_typetwo_two.setVisibility(View.GONE);
                        userViewHolder.feedImageView.setVisibility(View.GONE);

                        userViewHolder.feedImageView_typefour_one.setVisibility(View.GONE);
                        userViewHolder.feedImageView_typefour_two.setVisibility(View.GONE);
                        userViewHolder.feedImageView_typefour_three.setVisibility(View.GONE);
                        userViewHolder.feedImageView_typefour_four.setVisibility(View.GONE);
                    }
                }else {

                    int j;
                    for (j = 0; j < itemmodel.getAlbum().size(); j++) {
                        powerstwo = itemmodel.getAlbum().get(j);

                        if (j == 0) {
                            System.out.println("four -->" + itemmodel.getAlbum().get(j));
                            userViewHolder.feedImageView_typefour_one.setImageUrl(powerstwo, mImageLoader);
                            userViewHolder.feedImageView_typefour_one.setDefaultImageResId(R.mipmap.ic_launcher);
                            userViewHolder.feedImageView_typefour_one.setVisibility(View.VISIBLE);

                        } else if (j == 1) {
                            System.out.println("four2 -->" + itemmodel.getAlbum().get(j));
                            userViewHolder.feedImageView_typefour_two.setImageUrl(powerstwo, mImageLoader);
                            userViewHolder.feedImageView_typefour_two.setDefaultImageResId(R.mipmap.ic_launcher);
                            userViewHolder.feedImageView_typefour_two.setVisibility(View.VISIBLE);

                        } else if (j == 2) {
                            System.out.println("four3 -->" + itemmodel.getAlbum().get(j));
                            userViewHolder.feedImageView_typefour_three.setImageUrl(powerstwo, mImageLoader);
                            userViewHolder.feedImageView_typefour_three.setDefaultImageResId(R.mipmap.ic_launcher);
                            userViewHolder.feedImageView_typefour_three.setVisibility(View.VISIBLE);

                        } else if (j == 3) {
                            System.out.println("four4 -->" + itemmodel.getAlbum().get(j));
                            userViewHolder.feedImageView_typefour_four.setImageUrl(powerstwo, mImageLoader);
                            userViewHolder.feedImageView_typefour_four.setDefaultImageResId(R.mipmap.ic_launcher);
                            userViewHolder.feedImageView_typefour_four.setVisibility(View.VISIBLE);

                        }
                        userViewHolder.feedImageView_typetwo_one.setVisibility(View.GONE);
                        userViewHolder.feedImageView_typetwo_two.setVisibility(View.GONE);
                        userViewHolder.feed_typethree_ones.setVisibility(View.GONE);
                        userViewHolder.feed_typethree_twos.setVisibility(View.GONE);
                        userViewHolder.feed_typethree_threes.setVisibility(View.GONE);
                        userViewHolder.feedImageView.setVisibility(View.GONE);


                    }
                }
                int albumcountsdata=itemmodel.getAlbumcount();
                if(albumcountsdata>4){
                    userViewHolder.moreimagescount_textview.setVisibility(View.VISIBLE);
                    int result=albumcountsdata-4;
                    userViewHolder.moreimagescount_textview.setText(Html.fromHtml("+"+result+"&nbsp;"+"Images"));
                }else {
                    userViewHolder.moreimagescount_textview.setVisibility(View.GONE);
                }



                userViewHolder.setClickListener(new RecyclerView_OnClickListener.OnClickListener() {

                    @Override
                    public void OnItemClick(View view, int position) {
                        switch (view.getId()) {
                            case R.id.listlayout_taball:
                                Intent photostory=new Intent(getActivity(),PhotoStoriesDetail.class);
                                photostory.putExtra("Image", itemmodel.getId());
                                photostory.putExtra("TITLE",itemmodel.getTitle());
                                photostory.putExtra("DATE",itemmodel.getPdate());
                                startActivity(photostory);
                               /* if (itemmodel.getAlbumcount() == 0) {
                                    Log.e("IMAGE:", itemmodel.getImage());


                                    FragmentTransaction fts = getChildFragmentManager().beginTransaction();
                                    MyDialogFragmentviewone frags;
                                    frags = new MyDialogFragmentviewone();
                                    Bundle argss = new Bundle();
                                    argss.putString("Image", itemmodel.getImage());
                                    argss.putString("TITLE",itemmodel.getTitle());
                                    argss.putString("DATE",itemmodel.getPdate());
                                    frags.setArguments(argss);
                                    frags.show(fts, "txn_tag");
                                } else {

                                   FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                                    MyDialogFragmentmultiple frag;
                                    frag = new MyDialogFragmentmultiple();
                                    Bundle args = new Bundle();
                                    args.putString("Image", itemmodel.getId());
                                    args.putString("TITLE",itemmodel.getTitle());
                                    args.putString("DATE",itemmodel.getPdate());
                                    frag.setArguments(args);
                                    frag.show(ft, "txn_tag");
                                }
*/


                                break;
                            case R.id.alltab_likescount:
                                FragmentTransaction ftlike = getChildFragmentManager().beginTransaction();
                                LikeListFragment frags;
                                frags = new LikeListFragment();
                                Bundle argss = new Bundle();
                                argss.putString("ID", itemmodel.getId());
                                argss.putString("QTYPE",itemmodel.getQtypemain());
                                frags.setArguments(argss);
                                frags.show(ftlike, "txn_tag");
                                break;


                            case R.id.taball_savepage:
                                if(myprofileid!=null) {

                                    if (save_item_count == 1) {
                                        userViewHolder.save_button.setText("Saved");
                                        userViewHolder.save_button.setTextColor(getResources().getColor(R.color.white));
                                        userViewHolder.save_button.setTypeface(seguiregular);
                                        userViewHolder.save_button.setTransformationMethod(null);
                                        save_item_count--;





                                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLSAVE,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String s) {
                                                        //Disimissing the progress dialog

                                                        //Showing toast message of the response
                                                        if (s.equalsIgnoreCase("no")) {
                                                            //Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show() ;
                                                        } else {
                                                            Log.e("response:", s);

                                                        }

                                                    }
                                                },
                                                new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError volleyError) {
                                                        //Dismissing the progress dialog
                                                        //loading.dismiss();

                                                        //Showing toast
                                                        //  Toast.makeText(CityCenterCommentPage.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                                                    }
                                                }) {
                                            @Override
                                            protected Map<String, String> getParams() throws AuthFailureError {

                                                Map<String, String> params = new Hashtable<String, String>();

                                                //Adding parameters



                                                params.put(QID, itemmodel.getId());
                                                params.put(USERID, myprofileid);
                                                params.put(QTYPE, itemmodel.getQtypemain());



                                                return params;
                                            }
                                        };

                                        //Creating a Request Queue
                                        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

                                        //Adding request to the queue
                                        requestQueue.add(stringRequest);

                                    } else {
                                        userViewHolder.save_button.setText("Save");
                                        userViewHolder.save_button.setTextColor(getResources().getColor(R.color.red));
                                        userViewHolder.save_button.setTypeface(seguiregular);
                                        userViewHolder.save_button.setTransformationMethod(null);
                                        save_item_count++;



                                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLSAVE,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String s) {
                                                        //Disimissing the progress dialog

                                                        //Showing toast message of the response
                                                        if (s.equalsIgnoreCase("no")) {
                                                            //Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show() ;
                                                        } else {
                                                            Log.e("response:", s);


                                                        }

                                                    }
                                                },
                                                new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError volleyError) {
                                                        //Dismissing the progress dialog
                                                        //loading.dismiss();

                                                        //Showing toast
                                                        //  Toast.makeText(CityCenterCommentPage.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                                                    }
                                                }) {
                                            @Override
                                            protected Map<String, String> getParams() throws AuthFailureError {
                                                //Converting Bitmap to String

                                                //Getting Image Name

                                                //Creating parameters
                                                Map<String, String> params = new Hashtable<String, String>();

                                                //Adding parameters



                                                params.put(QID, itemmodel.getId());
                                                params.put(USERID, myprofileid);
                                                params.put(QTYPE, itemmodel.getQtypemain());



                                                return params;
                                            }
                                        };

                                        //Creating a Request Queue
                                        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

                                        //Adding request to the queue
                                        requestQueue.add(stringRequest);
                                        //Toast.makeText(getActivity(),count,Toast.LENGTH_LONG).show();
                                    }
                                }else {
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString(Activity, "mainversion");
                                    editor.putString(CONTENTID, "0");
                                    editor.commit();
                                    Intent signin=new Intent(getActivity(),SigninpageActivity.class);
                                    startActivity(signin);

                                }
                                break;
                            case R.id.button_likes:
                                if(myprofileid!=null) {
                                    String backgroundImageName = String.valueOf(userViewHolder.like_imagebutton.getTag());
                                    Log.e("RUN","with"+backgroundImageName);
                                    if(backgroundImageName.equals("heart")){
                                        userViewHolder.like_imagebutton.setImageResource(R.mipmap.heartfullred);
                                        userViewHolder.like_imagebutton.setTag("heartfullred");
                                    }else if(backgroundImageName.equals("heartfullred")) {
                                        userViewHolder.like_imagebutton.setImageResource(R.mipmap.heart);
                                    }else {

                                    }
                                    StringRequest likes=new StringRequest(Request.Method.POST, URLLIKES, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            String res;
                                            Log.e("RES",response.toString());
                                            try {
                                                Log.e("RES", "START");
                                                JSONObject data = new JSONObject(response.toString());
                                                String dir = data.getString("result");
                                                Log.d("RES", dir);
                                                JSONObject object=new JSONObject(dir);
                                                String dir2=object.getString("message");
                                                Log.d("RES", dir2);
                                                for (int i = 0; i < object.length(); i++) {
                                                    String dirs = object.getString("message");
                                                    Log.d("RES", dirs);
                                                    res=object.getString("message");
                                                    like_finalvalues=object.getInt("total_likes");
                                                    Log.e("RES",res.toString());
                                                    if(res.equals("Liked")){
                                                        System.out.println(itemmodel.getId());
                                                        like_finalvalues=object.getInt("total_likes");
                                                        Log.e("RES",String.valueOf(like_finalvalues));
                                                        userViewHolder.like_imagebutton.setImageResource(R.mipmap.heartfullred);
                                                        userViewHolder.like_imagebutton.setTag("heartfullred");
                                                    }else if(res.equals("Like")){
                                                        like_finalvalues=object.getInt("total_likes");
                                                        Log.e("RES","dis"+String.valueOf(like_finalvalues));
                                                        userViewHolder.like_imagebutton.setImageResource(R.mipmap.heart);
                                                        userViewHolder.like_imagebutton.setTag("heart");
                                                    }
                                                    userViewHolder.    likescount.setText(Html.fromHtml(like_finalvalues + "&nbsp;" + "Likes"));
                                                }
                                            }catch (JSONException e){

                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                        }
                                    }){
                                        protected Map<String,String> getParams()throws AuthFailureError{
                                            Map<String,String> param=new Hashtable<String, String>();
                                            String ids=itemmodel.getId();
                                            param.put(QID, ids);
                                            param.put(USERID, myprofileid);
                                            param.put(QTYPE, itemmodel.getQtypemain());
                                            return param;
                                        }
                                    };
                                    RequestQueue likesqueue=Volley.newRequestQueue(getActivity());
                                    likes.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                                    likesqueue.add(likes);

                                }else {
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString(Activity, "mainversion");
                                    editor.putString(CONTENTID, "0");
                                    editor.commit();
                                    Intent sign=new Intent(getActivity(),SigninpageActivity.class);
                                    startActivity(sign);

                                }


                                break;
                            case R.id.button_comment:

                                if(myprofileid!=null) {
                                    FragmentTransaction ftcom = getChildFragmentManager().beginTransaction();
                                    Tabnews.MyDialogFragment fragcom;
                                    fragcom = new Tabnews.MyDialogFragment();
                                    Bundle argscom = new Bundle();
                                    argscom.putString("POSTID", itemmodel.getId());
                                    argscom.putString("USERID", myprofileid);
                                    argscom.putString("QTYPE",itemmodel.getQtypemain());
                                    fragcom.setArguments(argscom);
                                    fragcom.show(ftcom, "txn_tag");
                                }else {
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString(Activity, "mainversion");
                                    editor.putString(CONTENTID, "0");
                                    editor.commit();
                                    Intent signin=new Intent(getActivity(),SigninpageActivity.class);
                                    startActivity(signin);

                                }
                                break;
                            case R.id.button_share:
                                Intent sendIntent = new Intent();
                                sendIntent.setAction(Intent.ACTION_SEND);
                                if(itemmodel.getSharingurl().equals("")){
                                    sendIntent.putExtra(Intent.EXTRA_TEXT, itemmodel.getTitle()+ "\n" + itemmodel.getAds()+"\n"+"\n"+"\n"+"Receive instant updates by installing Simplicity for iPhone/iPad,Android and Windows 10(desktop & Mobile)(http://goo.gl/Sv3vfc)");
                                }else {
                                    sendIntent.putExtra(Intent.EXTRA_TEXT, itemmodel.getTitle() + "\n" + itemmodel.getSharingurl() + "\n" + "\n" + "\n" + "Receive instant updates by installing Simplicity for iPhone/iPad,Android and Windows 10(desktop & Mobile)(http://goo.gl/Sv3vfc)");
                                }
                                sendIntent.setType("text/plain");
                                startActivity(Intent.createChooser(sendIntent, "Share using"));

                                break;
                        }
                    }

                });



            }


            else {
                if (holder instanceof LoadingViewHolder) {
                    LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                    loadingViewHolder.progressBar.setVisibility(View.GONE);
                }
            }
        }

        public void onButtonPressed(String playurl, String title,String image) {             if (mListener != null) {                 mListener.onFragmentInteraction(playurl,title,image);              }         }

     public void Font(List<ItemModel> list){
         Log.e("SIZE","adap  "+list.toString());
         this.modelList.addAll(list);

         notifyDataSetChanged();

     }
        public int getItemViewType(int position) {


            ItemModel item = modelList.get(position);
           // return modelList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
            if(item.getQtypemain()!=null){
                if(item.getQtypemain().equalsIgnoreCase("photostories")){
                    return VIEW_TYPE_PHOTOSTORY;
                } else {
                    return  VIEW_TYPE_ITEM;
                }
            }else {
                if (item.getSubqueuetitle().equals("சிறப்பு காணொளி\n")) {
                    return VIEW_TYPE_VIDEO;
                } else if (item.getSubqueuetitle().equals("புகைப்பட செய்திகள்")) {
                    return VIEW_TYPE_PHOTOSTORY_NEW;
                } else if (item.getSubqueuetitle().equals("கோவையில் இன்றைய நிகழ்வுகள்")) {

                    return VIEW_TYPE_EVENT;
                } else if (item.getSubqueuetitle().equals("சிறப்பு கட்டுரைகள்")) {
                    return VIEW_TYPE_SPECIAL;
                } else
                if (item.getSubqueuetitle().equals("நாடு மற்றும் உலக செய்திகள்")){
                    return VIEW_TYPE_BEYOND;
                }else {
                    return VIEW_TYPE_LOADING;
                }

                //return VIEW_TYPE_ITEM;
            }
           /* if(item.getQtypemain().equalsIgnoreCase("photostories")){
                return VIEW_TYPE_PHOTOSTORY;
            }*//*else if(item.getQtypemain().equalsIgnoreCase("radio")){
           return VIEW_TYPE_RADIO;
        }*//*  else{
                return modelList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
            }
*/
        }
        public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
            this.onLoadMoreListener = onLoadMoreListener;
        }
        public void setLoaded() {
            loading = false;
        }


 }
    public static class MyDialogFragment extends DialogFragment {
        private String KEY_COMMENT = "comment";
        private String KEY_TYPE = "qtype";
        private String KEY_MYUID = "user_id";
        private String KEY_POSTID = "id";
        String URLCOMMENT = "http://simpli-city.in/request2.php?rtype=viewcomment&key=simples&id=";
        String urlpost = "http://simpli-city.in/request2.php?rtype=comments&key=simples";
        EditText commentbox;
        Button post_review;
        Button close_back;

        JSONObject obj, objtwo;
        JSONArray feedArray;
        int ii, i;
        TextView titles;
        CoordinatorLayout mCoordinator;
        private final String TAG_REQUEST = "MY_TAG";
        List<ItemModels> commentlist = new ArrayList<ItemModels>();
        //Need this to set the title of the app bar
        CollapsingToolbarLayout mCollapsingToolbarLayout;
       RecyclerViewAdapter rcAdapter;
        ProgressDialog pdialog;
        RequestQueue requestQueue;
        private int requestCount = 1;
        JsonObjectRequest jsonReq;
        String URLTWO;
        String bimage;
        RecyclerView recycler;
        LinearLayoutManager mLayoutManager;
        String postid, myuserid,qtypevalue;

        public MyDialogFragment() {

        }

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setStyle(DialogFragment.STYLE_NORMAL, R.style.MY_DIALOG);
        }

        @Override
        public void onStart() {
            super.onStart();
            Dialog d = getDialog();
            if (d != null) {
                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                d.getWindow().setLayout(width, height);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.taballcomments, container, false);
            // titles = (TextView) root.findViewById(R.id.comments_title);
            requestQueue = Volley.newRequestQueue(getActivity());
            postid = getArguments().getString("POSTID");
            myuserid = getArguments().getString("USERID");
            qtypevalue=getArguments().getString("QTYPE");
            String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
            Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), simplycity_title_fontPath);
            commentbox = (EditText) root.findViewById(R.id.comment_description);
            post_review = (Button) root.findViewById(R.id.post_button);
            close_back = (Button) root.findViewById(R.id.cancel_button);
            // mCoordinator = (CoordinatorLayout) root.findViewById(R.id.root_coordinator);             mCollapsingToolbarLayout = (CollapsingToolbarLayout) root.findViewById(R.id.collapsing_toolbar_layout);
            recycler = (RecyclerView) root.findViewById(R.id.commentpagelist_recyclerview);
            recycler.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
            recycler.setHasFixedSize(true);
            recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
            recycler.setNestedScrollingEnabled(false);
            pdialog = new ProgressDialog(getActivity());
            pdialog.show();
            pdialog.setContentView(R.layout.custom_progressdialog);
            pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            // titles.setTypeface(tf);
            // titles.setText("Comments");
            post_review.setTypeface(tf);
            post_review.setText("POST");
            close_back.setTypeface(tf);
            close_back.setText("Cancel");
            close_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyDialogFragment.this.dismiss();
                }
            });
            post_review.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {

                        StringRequest comment_post_request = new StringRequest(Request.Method.POST, Configurl.api_new_url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.e("Res", response.toString().trim());

                                if (response.equalsIgnoreCase("error")) {
                                    Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();
                                } else {

                                        /*commentbox_editext.setText("");
                                        AddnewCommnent();
                                        scrollView.post(new Runnable() {
                                            public void run() {
                                                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                                            }
                                        });*/

                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                String      description_comment = commentbox.getText().toString().trim();


                                Map<String, String> param = new Hashtable<String, String>();
                                String keytepe = "article";
                                Log.e("qty", keytepe);
                                param.put("Key", "Simplicity");
                                param.put("Token", "8d83cef3923ec6e4468db1b287ad3fa7");
                                param.put("rtype", "comment");
                                param.put("language", "1");
                                param.put("id", postid);
                                param.put("user_id", myuserid);
                                param.put("comment", description_comment);
                                param.put("qtype", qtypevalue);
                                return param;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                        requestQueue.add(comment_post_request);
                    } catch (Exception e) {

                    }
                }

            });

            rcAdapter = new RecyclerViewAdapter(commentlist,recycler);
            recycler.setAdapter(rcAdapter);
            getData();
            rcAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    Log.e("haint", "Load More");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("haint", "Load More 2");


                            // getData();


                            rcAdapter.setLoaded();
                        }
                    }, 2000);
                }
            });

            return root;
        }

        public void dissmissDialog() {
            // TODO Auto-generated method stub
            if (pdialog != null) {
                if (pdialog.isShowing()) {
                    pdialog.dismiss();
                }
                pdialog = null;
            }

        }

        public void onDestroy() {
            super.onDestroy();
            dissmissDialog();
        }

        static class UserViewHolder extends RecyclerView.ViewHolder {
            public TextView name, locations,commentsdecription;


            public NetworkImageView imageview;

            public UserViewHolder(View itemView) {
                super(itemView);

                // im = (ImageView) itemView.findViewById(R.id.imageViewlitle);

                imageview = (NetworkImageView) itemView.findViewById(R.id.thumbnailfoodcategory);


                name = (TextView) itemView.findViewById(R.id.name);
                locations = (TextView) itemView.findViewById(R.id.location);

                commentsdecription = (TextView) itemView.findViewById(R.id.all_page_descriptions);
            }
        }

        static class LoadingViewHolder extends RecyclerView.ViewHolder {
            public ProgressBar progressBar;

            public LoadingViewHolder(View itemView) {
                super(itemView);
                progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
            }
        }

        private void getData() {
            StringRequest request=new StringRequest(Request.Method.POST, Configurl.api_new_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.e("Res",response.toString());
                    try {
                        JSONObject object = new JSONObject(response.toString());
                        JSONArray array = object.getJSONArray("result");
                        String data = array.optString(1);
                        JSONArray     jsonArray = new JSONArray(data.toString());
                        parseJsonFeed(jsonArray);
                    }catch (JSONException e){

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String>param=new HashMap<>();
                    param.put("Key", "Simplicity");
                    param.put("Token", "8d83cef3923ec6e4468db1b287ad3fa7");
                    param.put("rtype", "viewcomment");
                    param.put("language","1");
                    param.put("qtype",qtypevalue);
                    param.put("id",postid);

                    return param;
                }
            };

            request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(request);

        }


        private void parseJsonFeed(JSONArray response) {
            try {
                //  feedArray = response.getJSONArray("result");


                for (ii = 0; ii < response.length(); ii++) {
                    obj = (JSONObject) response.get(ii);

                 ItemModels model = new ItemModels();
                    //FeedItem model=new FeedItem();
                    String image = obj.isNull("image") ? null : obj
                            .getString("image");
                    model.setProfilepic(image);
                    model.setComment(obj.getString("comment"));
                    model.setPadate(obj.getString("date"));
                    model.setName(obj.getString("name"));
                    model.setId(obj.getString("user_id"));


                    commentlist.add(model);

                }

                // notify data changes to list adapater
                rcAdapter.notifyDataSetChanged();

                // notify data changes to list adapater


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        class  ItemModels{
            private String id,comment,padate,name,profilepic;

            public String getComment() {
                return comment;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPadate() {
                return padate;
            }

            public void setPadate(String padate) {
                this.padate = padate;
            }

            public String getProfilepic() {
                return profilepic;
            }

            public void setProfilepic(String profilepic) {
                this.profilepic = profilepic;
            }

        }
        class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
            private LayoutInflater inflater;

            ImageLoader mImageLoader;
            private final int VIEW_TYPE_ITEM = 1;
            private final int VIEW_TYPE_LOADING = 2;
            private final int VIEW_TYPE_FEATURE = 0;
            private boolean loading;
            private OnLoadMoreListener onLoadMoreListener;

            private int visibleThreshold = 5;
            private int lastVisibleItem, totalItemCount;
            Context context;
           String fontname;
            SharedPreferences sharedpreferences;

            public RecyclerViewAdapter(List<ItemModels> students, RecyclerView recyclerView) {
                commentlist = students;

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

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
                if (viewType == VIEW_TYPE_ITEM) {
                    View view = LayoutInflater.from(getActivity()).inflate(R.layout.feed_item_comment_all, parent, false);
                    return new UserViewHolder(view);
                } else if (viewType == VIEW_TYPE_LOADING) {
                    View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_loading_item, parent, false);
                    return new LoadingViewHolder(view);
                }
                return null;

            }


            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                sharedpreferences = getActivity().getSharedPreferences(mypreference,
                        Context.MODE_PRIVATE);

                
                fontname = sharedpreferences.getString(Fonts.FONT,"");
                if (holder instanceof UserViewHolder) {

                    final UserViewHolder userViewHolder = (UserViewHolder) holder;

                    String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
                    Typeface seguiregular = Typeface.createFromAsset(getActivity().getAssets(), simplycity_title_fontPath);
                    if (mImageLoader == null)
                        mImageLoader = MySingleton.getInstance(getActivity()).getImageLoader();


                   ItemModels itemmodel = commentlist.get(position);


                    userViewHolder.name.setText(itemmodel.getName());
                    userViewHolder.name.setTypeface(seguiregular);
                    userViewHolder.commentsdecription.setText(itemmodel.getComment());
                    userViewHolder.imageview.setDefaultImageResId(R.drawable.iconlogo);
                    userViewHolder.imageview.setErrorImageResId(R.drawable.iconlogo);

                    userViewHolder.imageview.setImageUrl(itemmodel.getProfilepic(), mImageLoader);
                    // userViewHolder.im.setVisibility(View.VISIBLE);

                } else if (holder instanceof LoadingViewHolder) {
                   LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                    loadingViewHolder.progressBar.setIndeterminate(true);
                }

            }

            public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
                this.onLoadMoreListener = onLoadMoreListener;
            }


            public int getItemViewType(int position) {


                return commentlist.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;

            }

            public void setLoaded() {
                loading = false;
            }

            public int getItemCount() {
                return commentlist.size();
            }
        }
    }

}
