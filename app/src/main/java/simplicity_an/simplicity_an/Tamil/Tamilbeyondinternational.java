package simplicity_an.simplicity_an.Tamil;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import simplicity_an.simplicity_an.AppControllers;
import simplicity_an.simplicity_an.DividerItemDecoration;
import simplicity_an.simplicity_an.MySingleton;
import simplicity_an.simplicity_an.NewsDescription;
import simplicity_an.simplicity_an.OnLoadMoreListener;
import simplicity_an.simplicity_an.R;
import simplicity_an.simplicity_an.RecyclerView_OnClickListener;
import simplicity_an.simplicity_an.SigninpageActivity;

/**
 * Created by kuppusamy on 10/3/2016.
 */
public class Tamilbeyondinternational extends Fragment {
    RecyclerView recyclerview_tab_all_news;
    String URL="http://simpli-city.in/request2.php?rtype=alldata&key=simples&language=2&qtype=international";
    String URLLIKES="http://simpli-city.in/request2.php?rtype=add-liketest&key=simples"; 				String URLSAVE="http://simpli-city.in/request2.php?rtype=addfav&key=simples";
    String URLALL;
    RequestQueue requestQueue;
    private int requestCount = 1;
    JsonObjectRequest jsonReq;
    ProgressDialog pdialog;
    protected Handler handler;
    private final String TAG_REQUEST = "MY_TAG";
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID = "myprofileid";
    public static final String USERID="user_id";
    public static final String QID="qid";
    public static final String QTYPE="qtype";
    FloatingActionButton fabnews;
    String myprofileid;
    LinearLayoutManager lLayout;
    Recyclerviewtaballadapter recyclerview_tab_all_adapter;
    List<ItemModel> modelList=new ArrayList<ItemModel>();
    private boolean isFragmentLoaded=false;

    public static final String Activity = "activity";
    public static final String CONTENTID = "contentid";
    int post_likes_count=0,save_item_count;
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !isFragmentLoaded ) {
            isFragmentLoaded = true;
            Log.e("TAB:","TamilNews");
        }
    }
    public Tamilbeyondinternational() {
        // Required empty public constructor
        // setUserVisibleHint(false);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.tad_all,container,false);
        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid = sharedpreferences.getString(MYUSERID, "");
            myprofileid = myprofileid.replaceAll("\\D+","");
        }
        requestQueue = Volley.newRequestQueue(getActivity());
        lLayout = new LinearLayoutManager(getActivity());
        recyclerview_tab_all_news = (RecyclerView) view.findViewById(R.id.tab_all_recyclerview);
        recyclerview_tab_all_news.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerview_tab_all_news.setLayoutManager(lLayout);
        pdialog = new ProgressDialog(getActivity());

        pdialog.show();
        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        fabnews=(FloatingActionButton)view.findViewById(R.id.fabButton) ;
        fabnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recyclerview_tab_all_news.smoothScrollToPosition(0);
            }
        });


        getData();
        recyclerview_tab_all_adapter = new Recyclerviewtaballadapter(modelList,recyclerview_tab_all_news);
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
    private void getData() {
        //Adding the method to the queue by calling the method getDataFromServer
        requestQueue.add(getDataFromTheServer(requestCount));
        // getDataFromTheServer();
        //Incrementing the request counter
        requestCount++;
    }
    private
    JsonObjectRequest getDataFromTheServer( int requestCount) {
        if(myprofileid!=null){
            URLALL=URL+"&page="+requestCount+"&user_id="+myprofileid;
        }else {
            URLALL=URL+"&page="+requestCount;
        }

        Cache cache = AppControllers.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(URLALL);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    dissmissDialog();
                    parseJsonFeed(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {
            // making fresh volley request and getting json
            jsonReq = new JsonObjectRequest(Request.Method.GET,
                    URLALL,  new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d(TAG_REQUEST, "Response: " + response.toString());
                    if (response != null) {
                        dissmissDialog();
                        parseJsonFeed(response);
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG_REQUEST, "Error: " + error.getMessage());
                }
            });

            jsonReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(jsonReq);
        }
        return jsonReq;
    }
    private void parseJsonFeed(JSONObject response){
        try {
            JSONArray feedArray = response.getJSONArray("result");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject obj = (JSONObject) feedArray.get(i);

                ItemModel model = new ItemModel();
                //FeedItem model=new FeedItem();
                String image = obj.isNull("image") ? null : obj
                        .getString("image");
                model.setImage(image);

                model.setId(obj.getString("id"));
                model.setPdate(obj.getString("pdate"));
                model.setTitle(obj.getString("title"));
                model.setQtype(obj.getString("qtype"));
                model.setLikescount(obj.getInt("likes_count"));
                model.setCommentscount(obj.getInt("commentscount"));
                model.setSharingurl(obj.getString("sharingurl"));
                model.setQtypemain(obj.getString("qtypemain"));
                // model.setDislikecount(obj.getInt("dislikes_count"));
                model.setCounttype(obj.getInt("like_type"));
                modelList.add(model);

            }

            // notify data changes to list adapater
            recyclerview_tab_all_adapter.notifyDataSetChanged();


            // notify data changes to list adapater

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void onStop() {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG_REQUEST);
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

    class ItemModel{
        private int typeid;
        private String name;
        private String image;

        private String pdate;
        private String description;
        private String title;

        /******** start the Food category names****/
        private String id;
        /******** start the Food category names****/
        private String qtype,qtypemain;
        int favcount;
        String sharingurl;
        int likescount,dislikecount,counttype,commentscount;

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

        public Button share_button,comment_button,likes_button,save_button;
        public NetworkImageView item_image;
        public RelativeLayout countlayout,listLayout;
        // LinearLayout ;
        RecyclerView_OnClickListener.OnClickListener onClickListener;
        public Userviewholdertaball(View itemView) {
            super(itemView);
            this.title_item=(TextView)itemView.findViewById(R.id.item_title_taball);
            this.  item_type_name=(TextView)itemView.findViewById(R.id.qtypetitle_taball);
            this. date=(TextView)itemView.findViewById(R.id.date_taball);
            this. likescount=(TextView)itemView.findViewById(R.id.alltab_likescount);

            this.  commentscount=(TextView)itemView.findViewById(R.id.alltab_commentscount);
            this.  likes_button=(Button)itemView.findViewById(R.id.taball_likes);
            this. save_button=(Button)itemView.findViewById(R.id.taball_savepage);
            this. share_button=(Button)itemView.findViewById(R.id.taball_sharepost);
            this. comment_button=(Button)itemView.findViewById(R.id.taball_comment);
            this. item_image=(NetworkImageView)itemView.findViewById(R.id.image_alltab);
            this.listLayout=(RelativeLayout) itemView.findViewById(R.id.listlayout_taball);
            this.countlayout=(RelativeLayout)itemView.findViewById(R.id.counts_layout);
            this.likes_button.setOnClickListener(this);
            this.comment_button.setOnClickListener(this);
            this.save_button.setOnClickListener(this);
            this.share_button.setOnClickListener(this);
            this.listLayout.setOnClickListener(this);
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
    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }
    class Recyclerviewtaballadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        LayoutInflater inflater;

        ImageLoader mImageLoader;
        private final int VIEW_TYPE_ITEM = 1;
        private final int VIEW_TYPE_LOADING = 2;

        boolean loading;
        OnLoadMoreListener onLoadMoreListener;

        private int visibleThreshold = 5;
        private int lastVisibleItem, totalItemCount;
        Context context;

        @Override
        public int getItemCount() {
            return modelList.size();
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
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof Userviewholdertaball) {

                final Userviewholdertaball userViewHolder = (Userviewholdertaball) holder;

                String simplycity_title_fontPath = "fonts/Lora-Regular.ttf";;
                final Typeface seguiregular = Typeface.createFromAsset(getActivity().getAssets(), simplycity_title_fontPath);
                if (mImageLoader == null)
                    mImageLoader = MySingleton.getInstance(getActivity()).getImageLoader();


                final ItemModel itemmodel = modelList.get(position);
                userViewHolder.comment_button.setText("கருத்து");
                userViewHolder.comment_button.setTypeface(seguiregular);
                userViewHolder.comment_button.setTransformationMethod(null);
                userViewHolder.share_button.setText("பகிரவும்");
                userViewHolder.share_button.setTypeface(seguiregular);
                userViewHolder.share_button.setTransformationMethod(null);
                post_likes_count=itemmodel.getCounttype();
                save_item_count=itemmodel.getFavcount();
                if(itemmodel.getCounttype()==1){
                    userViewHolder.likes_button.setText("விருப்பு");
                    userViewHolder.likes_button.setTextColor(getResources().getColor(R.color.red));
                    userViewHolder.likes_button.setTypeface(seguiregular);
                    userViewHolder.likes_button.setTransformationMethod(null);
                }else {
                    userViewHolder.likes_button.setText("விருப்பு");
                    userViewHolder.likes_button.setTextColor(getResources().getColor(R.color.white));
                    userViewHolder.likes_button.setTypeface(seguiregular);
                    userViewHolder.likes_button.setTransformationMethod(null);
                }

                if(itemmodel.getFavcount()==1){
                    userViewHolder.save_button.setText("சேமிக்க");
                    userViewHolder.likes_button.setTextColor(getResources().getColor(R.color.red));
                    userViewHolder.save_button.setTypeface(seguiregular);
                    userViewHolder.save_button.setTransformationMethod(null);
                }else {
                    userViewHolder.save_button.setText("சேமிக்க");
                    userViewHolder.save_button.setTypeface(seguiregular);
                    userViewHolder.save_button.setTransformationMethod(null);
                    userViewHolder.likes_button.setTextColor(getResources().getColor(R.color.white));
                }


                userViewHolder.title_item.setText(itemmodel.getTitle());
                userViewHolder.title_item.setTypeface(seguiregular);
                userViewHolder.item_type_name.setText(itemmodel.getQtype());
                userViewHolder.item_type_name.setTypeface(seguiregular);
                userViewHolder.date.setText(itemmodel.getPdate());
                userViewHolder.likescount.setTypeface(seguiregular);
                userViewHolder.date.setTypeface(seguiregular);
                if(itemmodel.getLikescount()==0){                         userViewHolder.commentscount.setText(Html.fromHtml("0"+"&nbsp;"  +"விருப்பு"));                     }else {                         userViewHolder.likescount.setText(Html.fromHtml(itemmodel.getLikescount()+"&nbsp;"+"விருப்பு"));                      }                     if(itemmodel.getCommentscount()==0){                          userViewHolder.commentscount.setText(Html.fromHtml("0"+"&nbsp;"  +"கருத்து"));                     }else {                         userViewHolder.commentscount.setText(Html.fromHtml(itemmodel.getCommentscount()+"&nbsp;"  +"கருத்து"));                     }                     userViewHolder.countlayout.setVisibility(View.VISIBLE);
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


                                String type = ((ItemModel) modelList.get(position)).getQtype();
                                String ids = ((ItemModel) modelList.get(position)).getId();

                                Intent intent = new Intent(getActivity(), NewsDescription.class);
                                intent.putExtra("ID", ids);
                                startActivity(intent);


                                break;

                            case R.id.taball_likes:
                                if(myprofileid!=null) {
                                    if (post_likes_count == 1) {
                                        userViewHolder.likes_button.setText("விருப்பு");
                                        userViewHolder.likes_button.setTextColor(getResources().getColor(R.color.white));
                                        userViewHolder.likes_button.setTypeface(seguiregular);
                                        userViewHolder.likes_button.setTransformationMethod(null);
                                        post_likes_count--;
                                        // String f = itemmodel.getLikescount().toString();

                                        int i =  itemmodel.getLikescount();
                                        String s = "1";
                                        int j = Integer.parseInt(s);

                                        int result = i - j;
                                        String res = String.valueOf(result).toString();
                                        if (result==-1||result==0) {
                                            userViewHolder.likescount.setVisibility(View.GONE);
                                            userViewHolder.countlayout.setVisibility(View.GONE);

                                        } else {
                                            userViewHolder.likescount.setVisibility(View.VISIBLE);
                                            userViewHolder.countlayout.setVisibility(View.VISIBLE);
                                            userViewHolder.likescount.setText(Html.fromHtml(itemmodel.getLikescount() + "&nbsp;" + "விருப்பு"));
                                        }
                                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLLIKES,
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
                                                String postid = itemmodel.getId();
                                                //Adding parameters
                                                if (postid != null) {


                                                    params.put(QID, postid);
                                                    params.put(USERID, myprofileid);
                                                    params.put(QTYPE, itemmodel.getQtypemain());
                                                } else {


                                                }


                                                return params;
                                            }
                                        };

                                        //Creating a Request Queue
                                        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

                                        //Adding request to the queue
                                        requestQueue.add(stringRequest);

                                    } else {
                                        userViewHolder.likes_button.setText("விருப்பு");
                                        userViewHolder.likes_button.setTextColor(getResources().getColor(R.color.red));
                                        userViewHolder.likes_button.setTypeface(seguiregular);
                                        userViewHolder.likes_button.setTransformationMethod(null);
                                        post_likes_count++;



                                        int i = itemmodel.getLikescount();
                                        String s = "1";
                                        int j = Integer.parseInt(s);

                                        Integer result = i + j;
                                        String res = result.toString();
                                        userViewHolder.likescount.setVisibility(View.VISIBLE);
                                        userViewHolder.countlayout.setVisibility(View.VISIBLE);
                                        userViewHolder.likescount.setText(Html.fromHtml(res + "&nbsp;" + "விருப்பு "));

                                        StringRequest stringRequest = new StringRequest(Request.Method.POST,URLLIKES,
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
                                                String postid = itemmodel.getId();
                                                //Adding parameters
                                                if (postid != null) {


                                                    params.put(QID, postid);
                                                    params.put(USERID, myprofileid);
                                                    params.put(QTYPE, itemmodel.getQtypemain());
                                                } else {


                                                }


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
                                    editor.putString(Activity, "beyondversiontamil");
                                    editor.putString(CONTENTID, "0");
                                    editor.commit();
                                    Intent sign=new Intent(getActivity(),SigninpageActivity.class);
                                    startActivity(sign);

                                }


                                break;
                            case R.id.taball_savepage:
                                if(myprofileid!=null) {

                                    if (save_item_count == 1) {
                                        userViewHolder.save_button.setText("சேமிக்க");
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
                                        userViewHolder.save_button.setText("சேமிக்க");
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
                                    editor.putString(Activity, "beyondversiontamil");
                                    editor.putString(CONTENTID, "0");
                                    editor.commit();
                                    Intent signin=new Intent(getActivity(),SigninpageActivity.class);
                                    startActivity(signin);

                                }
                                break;
                            case R.id.taball_comment:

                                if(myprofileid!=null) {
                                    FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                                    MyDialogFragment frag;
                                    frag = new MyDialogFragment();
                                    Bundle args = new Bundle();
                                    args.putString("POSTID", itemmodel.getId());
                                    args.putString("USERID", myprofileid);
                                    args.putString("QTYPE",itemmodel.getQtypemain());
                                    frag.setArguments(args);
                                    frag.show(ft, "txn_tag");
                                }else {
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString(Activity, "beyondversiontamil");
                                    editor.putString(CONTENTID, "0");
                                    editor.commit();
                                    Intent signin=new Intent(getActivity(),SigninpageActivity.class);
                                    startActivity(signin);

                                }
                                break;
                            case R.id.taball_sharepost:
                                Intent sendIntent = new Intent();
                                sendIntent.setAction(Intent.ACTION_SEND);
                                sendIntent.putExtra(Intent.EXTRA_TEXT, itemmodel.getTitle()+ "\n" + itemmodel.getSharingurl());
                                sendIntent.setType("text/plain");
                                startActivity(Intent.createChooser(sendIntent, "Share using"));

                                break;
                        }
                    }

                });
            }
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if(viewType == VIEW_TYPE_ITEM) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.feed_item_taball, parent, false);
                return new Userviewholdertaball(view);
            } else if (viewType == VIEW_TYPE_LOADING) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_loading_item, parent, false);
                return new LoadingViewHolder(view);
            }
            return null;
        }
        public int getItemViewType(int position) {


            return modelList.get(position) == null ? VIEW_TYPE_LOADING:VIEW_TYPE_ITEM ;

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

                    //Showing the progress dialog
                    //final ProgressDialog loading = ProgressDialog.show(getActivity(),"Uploading...","Please wait...",false,false);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, urlpost,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String s) {
                                    //Disimissing the progress dialog
                                    //  loading.dismiss();
                                    //Showing toast message of the response
                                    if (s.equalsIgnoreCase("error")) {
                                        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                                    } else {
                                        MyDialogFragment.this.dismiss();

                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    //Dismissing the progress dialog
                                    //  loading.dismiss();

                                    //Showing toast
                                    // Toast.makeText(CityCenterCommentPage.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            //Converting Bitmap to String


                            //Getting Image Name
                            String description = commentbox.getText().toString().trim();

                            //Creating parameters
                            Map<String, String> params = new Hashtable<String, String>();

                            //Adding parameters
                            if (postid != null) {
                                if (description != null) {
                                    params.put(KEY_COMMENT, description);
                                    params.put(KEY_TYPE, qtypevalue);
                                    params.put(KEY_POSTID, postid);
                                    params.put(KEY_MYUID, myuserid);
                                }

                            }
                            return params;
                        }
                    };

                    //Creating a Request Queue
                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

                    //Adding request to the queue
                    requestQueue.add(stringRequest);
                    //queue.add(stringRequest);
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


                            getData();


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
            //Adding the method to the queue by calling the method getDataFromServer
            requestQueue.add(getDataFromTheServer(requestCount));
            // getDataFromTheServer();
            //Incrementing the request counter
            requestCount++;
        }

        private JsonObjectRequest getDataFromTheServer(int requestCount) {

            URLTWO = URLCOMMENT +postid+"&page="+ requestCount+"&qtype="+qtypevalue;


            Cache cache = AppControllers.getInstance().getRequestQueue().getCache();
            Cache.Entry entry = cache.get(URLTWO);
            if (entry != null) {
                // fetch the data from cache
                try {
                    String data = new String(entry.data, "UTF-8");
                    try {
                        // dissmissDialog();
                        parseJsonFeed(new JSONObject(data));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            } else {
                // making fresh volley request and getting json
                jsonReq = new JsonObjectRequest(Request.Method.GET,
                        URLTWO, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        VolleyLog.d(TAG_REQUEST, "Response: " + response.toString());
                        if (response != null) {
                            //   dissmissDialog();
                            parseJsonFeed(response);
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG_REQUEST, "Error: " + error.getMessage());
                    }
                });

                // Adding request to volley request queue
                jsonReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                requestQueue.add(jsonReq);
            }
            return jsonReq;
        }

        private void parseJsonFeed(JSONObject response) {
            try {
                feedArray = response.getJSONArray("result");


                for (ii = 0; ii < feedArray.length(); ii++) {
                    obj = (JSONObject) feedArray.get(ii);

                    ItemModels model = new ItemModels();
                    //FeedItem model=new FeedItem();
                    String image = obj.isNull("thumb") ? null : obj
                            .getString("thumb");
                    model.setProfilepic(image);
                    model.setComment(obj.getString("comment"));
                    model.setPadate(obj.getString("pdate"));
                    model.setName(obj.getString("name"));
                    model.setId(obj.getString("id"));


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
