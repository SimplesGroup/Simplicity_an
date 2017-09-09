package simplicity_an.simplicity_an;

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
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by kuppusamy on 2/3/2017.
 */
public class TestDesc extends AppCompatActivity {
    NetworkImageView images;
    TextView title,eventdetail,eventdetaildata;
    TextView venuedetails,timing,timingdetails,date,datedetails;
    TextView contactname,contactnamedetails,phone,phonenumberdetails,email,emaildetails,venue_text,location_text,website_text,location_details,website_details;
    NetworkImageView thump;
    WebView description;
    Button booknow,remindme;
    ImageButton comment,share,menu,back,favourite;
    String titl,bitmap,venue,locations;
    String entrytype,entryfees;
    ProgressDialog pdialog;
    long startTime,endTime;
    Calendar cal;
    private List<ItemModel> modelList=new ArrayList<ItemModel>();
    private String URL="http://simpli-city.in/request2.php?rtype=event&key=simples&id=";
    String URLTWO;
    private final String TAG_REQUEST = "MY_TAG";
    String event_title,event_place,event_startdate,event_enddate;
    String URLFAV="http://simpli-city.in/request2.php?rtype=addfav&key=simples";
    String notifiid,myprofileid,shareurl,sharetitle;
    private String KEY_MYUID = "user_id";
    private String KEY_POSTID = "qid";
    private String KEY_QTYPE= "qtype";

    int post_likes_count,favcount;
    RequestQueue queue,requestQueue;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID= "myprofileid";
    public static final String Activity = "activity";
    public static final String CONTENTID = "contentid";
    public static final String MYACTIVITYSEARCH= "myactivitysearch";
    String contentid,activity,searchnonitiid,searchactivity_event;


    List<ItemModels> commentlist = new ArrayList<ItemModels>();
    RecyclerViewAdapter rcAdapter;

    private int requestCount = 1;
    JsonObjectRequest jsonReq;
    String URLTWO_comment;
    String URLCOMMENT = "http://simpli-city.in/request2.php?rtype=viewcomment&key=simples&qtype=event&id=";
    String urlpost = "http://simpli-city.in/request2.php?rtype=comments&key=simples";

    RecyclerView recycler;
    LinearLayoutManager mLayoutManager;
    String postid, myuserid;
    LinearLayout commentbox;
    TextView comment_title,loadmore_title;
    EditText commentbox_editext;
    Button post;
    RecyclerView recycler_comment;
    JSONObject obj, objtwo;
    JSONArray feedArray;
    int ii, i;
    private String KEY_COMMENT = "comment";
    private String KEY_TYPE = "qtype";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.eventsdesc);
        images=(NetworkImageView)findViewById(R.id.photo_image);
        ImageLoader mImageLoader ;

        mImageLoader = MySingleton.getInstance(getApplicationContext()).getImageLoader();
      //images.setImageUrl("http://simpli-city.in/vdfdhfv78lmdsvmg5todlsh4jffgskjb2947qnt/images/event/692355822first%20sports%20day%20celebration.jpg",mImageLoader);

        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        searchactivity_event=sharedpreferences.getString(MYACTIVITYSEARCH,"");
        contentid=sharedpreferences.getString(CONTENTID,"");
        activity=sharedpreferences.getString(Activity,"");
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");
            myprofileid = myprofileid.replaceAll("\\D+","");
        }
        requestQueue= Volley.newRequestQueue(this);

        if(activity==null){
            Intent getnotifi=getIntent();
            notifiid=getnotifi.getStringExtra("ID");
            searchnonitiid = getnotifi.getStringExtra("IDSEARCH");
            notifiid = notifiid.replaceAll("\\D+","");
            Log.e("ID",notifiid);

        } else {
            if(activity.equalsIgnoreCase("eventdesc")){
                notifiid=contentid;
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.remove(Activity);
                editor.remove(CONTENTID);
                editor.apply();

            }else {
                Intent getnotifi=getIntent();
                notifiid=getnotifi.getStringExtra("ID");
                searchnonitiid = getnotifi.getStringExtra("IDSEARCH");
                notifiid = notifiid.replaceAll("\\D+","");
                Log.e("ID",notifiid);
            }
        }

        if(myprofileid!=null){
            URLTWO=URL+notifiid+"&user_id="+myprofileid;
        }else {
            URLTWO=URL+notifiid;
        }
        queue = MySingleton.getInstance(this.getApplicationContext()).
                getRequestQueue();
       /* comment=(ImageButton)findViewById(R.id.btn_4);
        share=(ImageButton)findViewById(R.id.btn_5);
        menu=(ImageButton)findViewById(R.id.btn_3);
        back=(ImageButton)findViewById(R.id.btn_1);
        favourite=(ImageButton)findViewById(R.id.btn_2);*/
        booknow=(Button)findViewById(R.id.booknow);




        commentbox=(LinearLayout)findViewById(R.id.comments_versiontwo) ;
        comment_title=(TextView)findViewById(R.id.comments_title);
        loadmore_title=(TextView)findViewById(R.id.loadmore);
        commentbox_editext=(EditText)findViewById(R.id.comment_description);
        post=(Button)findViewById(R.id.post_button) ;
        recycler_comment=(RecyclerView)findViewById(R.id.commentpagelist_recyclerview) ;

        recycler_comment.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));

        recycler_comment.setLayoutManager(new LinearLayoutManager(getApplicationContext()));



        title=(TextView)findViewById(R.id.titlelabel);
        eventdetail=(TextView)findViewById(R.id.eventdetaillebel);
        eventdetaildata=(TextView)findViewById(R.id.eventdetaildata);
        description=(WebView)findViewById(R.id.webview_eventdescription);
      venue_text=(TextView)findViewById(R.id.text_eventvenue);
        location_text=(TextView)findViewById(R.id.text_eventlocation);
        website_text=(TextView)findViewById(R.id.text_eventwebsite);
        location_details=(TextView)findViewById(R.id.text_eventlocation_details);
        website_details=(TextView)findViewById(R.id.text_eventwebsite_details);
        eventdetail=(TextView)findViewById(R.id.eventdetaillebel);
        eventdetaildata=(TextView)findViewById(R.id.eventdetaildata);
        venuedetails=(TextView)findViewById(R.id.text_eventvenue_details);
        timing=(TextView)findViewById(R.id.text_timing_event);
        timingdetails=(TextView)findViewById(R.id.text_timing_event_data);
        date=(TextView)findViewById(R.id.text_date_event);
        datedetails=(TextView)findViewById(R.id.text_date_event_data);
        contactname=(TextView)findViewById(R.id.text_eventontactname);
        remindme=(Button)findViewById(R.id.remindme);
        remindme.setText("Remind Me");
        contactnamedetails=(TextView)findViewById(R.id.text_eventontactname_details);
        phone=(TextView)findViewById(R.id.text_eventphonenumber);
        phonenumberdetails=(TextView)findViewById(R.id.text_eventphonenumber_details);
       email=(TextView)findViewById(R.id.text_eventemail);
        emaildetails=(TextView)findViewById(R.id.text_eventemail_details);

        description=(WebView)findViewById(R.id.webview_eventdescription);

        comment=(ImageButton)findViewById(R.id.btn_4);
        share=(ImageButton)findViewById(R.id.btn_5);
        menu=(ImageButton)findViewById(R.id.btn_3);
        back=(ImageButton)findViewById(R.id.btn_1);
        favourite=(ImageButton)findViewById(R.id.btn_2);
        String simplycity_title_fontPath = "fonts/robotoSlabRegular.ttf";
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
        comment_title.setTypeface(tf);
        loadmore_title.setTypeface(tf);
        post.setTypeface(tf);
        venue_text.setTypeface(tf);
        location_text.setTypeface(tf);
        website_text.setTypeface(tf);
        venue_text.setText("Venue:");
        location_text.setText("Location:");
        website_text.setText("Website:");
        remindme.setTypeface(tf);
        eventdetail.setTypeface(tf);
        title.setTypeface(tf);
        eventdetaildata.setTypeface(tf);
       venuedetails.setTypeface(tf);
       timing.setTypeface(tf);
        timing.setText("Timing");
       timingdetails.setTypeface(tf);
      date.setTypeface(tf);
        date.setText("Date");
      datedetails.setTypeface(tf);
        contactname.setTypeface(tf);
        contactname.setText("Name:");

        contactnamedetails.setTypeface(tf);
        email.setTypeface(tf);
        email.setText("Email:");
        emaildetails.setTypeface(tf);
        phone.setTypeface(tf);
        phone.setText("Phone:");
        location_details.setTypeface(tf);
        website_details.setTypeface(tf);
        phonenumberdetails.setTypeface(tf);

        pdialog = new ProgressDialog(this);
        pdialog.show();
        pdialog.setContentView(R.layout.custom_progressdialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if(notifiid!=null) {
            JsonObjectRequest jsonreq = new JsonObjectRequest(Request.Method.GET, URLTWO, new Response.Listener<JSONObject>() {


                public void onResponse(JSONObject response) {

                    //VolleyLog.d(TAG, "Response: " + response.toString());
                    if (response != null) {
                        pdialog.dismiss();
                        //dissmissDialog();
                        parseJsonFeed(response);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            jsonreq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            //AppControllers.getInstance().addToRequestQueue(jsonreq);
            requestQueue.add(jsonreq);
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchactivity_event.equalsIgnoreCase("eventsearch")){
                    Intent searchnotification = new Intent(getApplicationContext(), EventSearchview.class);
                    startActivity(searchnotification);
                    finish();
                }
                else   if (searchnonitiid != null) {
                    Intent searchnotification = new Intent(getApplicationContext(), SimplicitySearchview.class);
                    searchnotification.putExtra("IDSEARCH", searchnonitiid);
                    startActivity(searchnotification);
                    finish();
                } else {
                    Intent backeventdesc = new Intent(getApplicationContext(), MainActivityVersiontwo.class);
                    startActivity(backeventdesc);
                    finish();
                }

            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menueventdesc = new Intent(getApplicationContext(), MainActivityVersiontwo.class);
                startActivity(menueventdesc);
                finish();
            }
        });
       if(myprofileid!=null){
            // URLTWO_comment=URLCOMMENT+notifiid;
            comment_title.setText("Post your Comment Here - ");
            post.setText("Post");

            commentbox.setVisibility(View.VISIBLE);

            post.setOnClickListener(new View.OnClickListener() {
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
                                        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                                    } else {
                                        //MyDialogFragment.this.dismiss();
                                        Toast.makeText(getApplicationContext(), "Posted", Toast.LENGTH_LONG).show();
                                        getData();
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
                            String description = commentbox_editext.getText().toString().trim();

                            //Creating parameters
                            Map<String, String> params = new Hashtable<String, String>();

                            //Adding parameters
                            if (postid != null) {
                                if (description != null) {
                                    params.put(KEY_COMMENT, description);
                                    params.put(KEY_TYPE, "event");
                                    params.put(KEY_POSTID, postid);
                                    params.put(KEY_MYUID, myuserid);
                                }

                            }
                            return params;
                        }
                    };

                    //Creating a Request Queue
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                    //Adding request to the queue
                    requestQueue.add(stringRequest);
                    //queue.add(stringRequest);
                }

            });
            rcAdapter = new RecyclerViewAdapter(commentlist,recycler_comment);
            recycler_comment.setAdapter(rcAdapter);
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

            loadmore_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    MyDialogFragment frag;
                    frag = new MyDialogFragment();
                    Bundle args = new Bundle();
                    args.putString("POSTID", notifiid);
                    args.putString("USERID", myprofileid);
                    frag.setArguments(args);
                    frag.show(ft, "txn_tag");
                }
            });

        }else {
            commentbox.setVisibility(View.GONE);
        }
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myprofileid!=null) {
                    android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    MyDialogFragment frag;
                    frag = new MyDialogFragment();
                    Bundle args = new Bundle();
                    args.putString("POSTID", notifiid);
                    args.putString("USERID", myprofileid);
                    frag.setArguments(args);
                    frag.show(ft, "txn_tag");
                }else {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(Activity, "eventdesc");
                    editor.putString(CONTENTID, notifiid);
                    editor.commit();
                    Intent signin=new Intent(TestDesc.this,SigninpageActivity.class);
                    startActivity(signin);
                    finish();
                }
            }
        });


      remindme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setType("vnd.android.cursor.item/event");
                cal = Calendar.getInstance();
                try {
                    Date date = new SimpleDateFormat("MMMM dd, yyyy").parse(event_startdate);
                    startTime=date.getTime();
                    //startTime=date.setHours(4);


                }
                catch(Exception e){ }
                try {
                    Date date = new SimpleDateFormat("MMMM dd, yyyy").parse(event_enddate);
                    endTime=date.getTime();
                }
                catch(Exception e){ }

                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime);
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime);

                intent.putExtra(CalendarContract.Events.TITLE, event_title);
                //intent.putExtra(CalendarContract.Events.DESCRIPTION,  "This is a sample description");
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, event_place);
                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=YEARLY");

                startActivity(intent);
            }
        });


    }
    protected void onStop () {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG_REQUEST);
        }
    }
    private void parseJsonFeed(JSONObject response) {
        ImageLoader  mImageLoader ;

        mImageLoader = MySingleton.getInstance(getApplicationContext()).getImageLoader();
        try {
            JSONArray feedArray = response.getJSONArray("result");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject obj = (JSONObject) feedArray.get(i);
                ItemModel model=new ItemModel();
                //FeedItem model=new FeedItem();
                String image = obj.isNull("thumb") ? null : obj
                        .getString("thumb");
                model.setImage(image);
                String venue = obj.isNull("venue") ? null : obj
                        .getString("venue");
                model.setVenue(venue);
                String locations = obj.isNull("location") ? null : obj
                        .getString("location");
                model.setLocation(locations);
                String contactwebsite = obj.isNull("contact_website") ? null : obj
                        .getString("contact_website");
                model.setContactwebsite(contactwebsite);
                String organizedby = obj.isNull("organized_by") ? null : obj
                        .getString("organized_by");
                model.setContactwebsite(organizedby);
                String entrytype = obj.isNull("etype") ? null : obj
                        .getString("etype");
                model.setEtype(entrytype);
                String entryfees = obj.isNull("emt") ? null : obj
                        .getString("emt");
                model.setEmt(entryfees);
                // bimage=obj.isNull("bimage")?null:obj.getString("bimage");
                // model.setBimage(bimage);
                // model.setName(obj.getString("pub_by"));
                model.setDescription(obj.getString("description"));
                model.setTypeid(obj.getInt("type"));
                model.setPdate(obj.getString("pdate"));
                model.setTitle(obj.getString("title"));
                model.setEventstartdate(obj.getString("event_start_date"));
                model.setEventenddate(obj.getString("event_end_date"));
                model.setContactname(obj.getString("contact_name"));
                model.setVenue(obj.getString("venue"));
                model.setLocation(obj.getString("location"));
                model.setTiming(obj.getString("timing"));
                model.setOrganizedby(obj.getString("organized_by"));
                model.setEtype(obj.getString("etype"));
                model.setEmt(obj.getString("emt"));
                model.setContactphone(obj.getString("contact_phone"));
                model.setContactemail(obj.getString("contact_email"));
                model.setContactwebsite(obj.getString("contact_website"));

               images.setImageUrl(image,mImageLoader);
                //  Picasso.with(getApplicationContext()).load(obj.getString("image")).into(thump);
              title.setText(Html.fromHtml(obj.getString("title")));
              event_title=obj.getString("title");
               event_place=obj.getString("venue");
                event_startdate=obj.getString("event_start_date");
                event_enddate=obj.getString("event_end_date");

               if (organizedby != null) {
                    eventdetaildata.setText("Organized by:" + organizedby);

                } else {
                    eventdetaildata.setVisibility(View.GONE);
                }
                if (entrytype != null) {
                    if (obj.getString("etype").contentEquals("paid") ) {
                        eventdetaildata.setText("Entry Type:" + entrytype + "\n" + "Entry Fee:" +obj.getString("emt"));
                    } else {
                        eventdetaildata.setText("Entry Type:" + entrytype);
                    }
                } else {
                    eventdetaildata.setVisibility(View.GONE);
                }
                String descrition = obj.isNull("description") ? null : obj
                        .getString("description");
                String ss = descrition;
                String s = ss;
                // s = s.replace("\"", "'");
                s = s.replace("\\", "");

                // description.setText(Html.fromHtml(s));

                //description.loadData(String.format(descrip), "text/html", "utf-8");
                //description.loadData(descrip,"text/html","utf-8");
               String fonts="<html>\n" +         "\t<head>\n" +         "\t\t<meta  \thttp-equiv=\"content-type\" content=\"text/html;\" charset=\"UTF-8\">\n" +         "\t\t<style>\n" +         "\t\t@font-face {\n" +         "  font-family: 'segeoui-light';\n" +         " src: url('file:///android_asset/fonts/RobotoSlab-Regular.ttf');\n" +         "  font-style: normal;\n" +         "}\n" +         "\n" +         "@font-face {\n" +         "  font-family: 'segeoui-regular';\n" +         "src: url('file:///android_asset/fonts/RobotoSlab-Regular.ttf');\n" +         "  font-style: normal;\n" +         "}\n" +         "\n" +         "@font-face {\n" +         "  font-family: 'segeoui-sbold';\n" +         " src: url('file:///android_asset/fonts/RobotoSlab-Regular.ttf');\n" +         "  font-style: normal;\n" +         "}\n" +         "\n" +         "@font-face {\n" +         "    font-family: 'RobotoSlab-Bold';\n" +         "   src: url('file:///android_asset/fonts/RobotoSlab-Regular.ttf');\n" +         "    font-style: normal;\n" +         "}\n" +         "@font-face {\n" +         "    font-family: 'RobotoSlab-Light';\n" +         "    src: url('file:///android_asset/fonts/RobotoSlab-Regular.ttf');\n" +         "    font-style: normal;\n" +         "}\n" +         "@font-face {\n" +         "    font-family: 'RobotoSlab-Regular';\n" +         "    src: url('file:///android_asset/fonts/RobotoSlab-Regular.ttf');\n" +         "    font-style: normal;\n" +         "}\n" +         "@font-face {\n" +         "    font-family: 'RobotoSlab-Thin';\n" +         "    src: url('file:///android_asset/fonts/RobotoSlab-Regular.ttf');\n" +         "    font-style: normal;\n" +         "}\n" +         "\t\t</style>\n" +         "\t</head>"; description.loadDataWithBaseURL("", fonts+descrition+"</head>", "text/html", "utf-8", "");
                // description.setBackgroundColor(0x0a000000);
                description.setBackgroundColor(Color.TRANSPARENT);

             venuedetails.setText(venue );
              location_details.setText(obj.getString("location"));
                website_details.setText(obj.getString("contact_website"));
                timing.setText("Timing");
                date.setText("Date");
                timingdetails.setText(obj.getString("timing"));
                datedetails.setText(obj.getString("pdate"));
                contactname.setText("Name:");
                phone.setText("Phone:");
                email.setText("Email:");
                contactnamedetails.setText(obj.getString("contact_name"));
                emaildetails.setText(obj.getString("contact_email"));
                phonenumberdetails.setText(obj.getString("contact_phone"));
                model.setFavcount(obj.getInt("fav_count"));
                model.setShareurl(obj.getString("sharingurl"));
                favcount=obj.getInt("fav_count");
                post_likes_count=obj.getInt("fav_count");
                shareurl=obj.getString("sharingurl");
                sharetitle=obj.getString("title");



                if (favcount == 1) {
                    favourite.setImageResource(R.drawable.favred);
                } else {
                    favourite.setImageResource(R.drawable.favtwo);
                }
                favourite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(myprofileid!=null) {

                            if (post_likes_count == 1) {
                                favourite.setImageResource(R.drawable.favtwo);
                                post_likes_count--;





                                StringRequest stringRequest = new StringRequest(Request.Method.POST, URLFAV,
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
                                        String postid = notifiid;
                                        //Adding parameters
                                        if (postid != null) {


                                            params.put(KEY_POSTID, postid);
                                            params.put(KEY_MYUID, myprofileid);
                                            params.put(KEY_QTYPE, "event");
                                        } else {


                                        }


                                        return params;
                                    }
                                };

                                //Creating a Request Queue
                                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                                //Adding request to the queue
                                requestQueue.add(stringRequest);

                            } else {
                                favourite.setImageResource(R.drawable.favred);
                                post_likes_count++;



                                StringRequest stringRequest = new StringRequest(Request.Method.POST, URLFAV,
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
                                        String postid = notifiid;
                                        //Adding parameters
                                        if (postid != null) {


                                            params.put(KEY_POSTID, postid);
                                            params.put(KEY_MYUID, myprofileid);
                                            params.put(KEY_QTYPE, "event");
                                        } else {


                                        }


                                        return params;
                                    }
                                };

                                //Creating a Request Queue
                                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                                //Adding request to the queue
                                requestQueue.add(stringRequest);
                                //Toast.makeText(getActivity(),count,Toast.LENGTH_LONG).show();
                            }
                        }else {
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(Activity, "eventdesc");
                            editor.putString(CONTENTID, notifiid);
                            editor.commit();
                            Intent signin=new Intent(getApplicationContext(),SigninpageActivity.class);
                            startActivity(signin);
                            finish();
                        }
                    }
                });
                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, sharetitle+ "\n" + shareurl);
                        sendIntent.setType("text/plain");
                        startActivity(Intent.createChooser(sendIntent, "Share using"));

                    }
                });


                modelList.add(model);





            }

            // notify data changes to list adapater
            //rcAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    class ItemModel{
        private int typeid;
        private String name;
        private String image;
        private String bimage;
        private String pdate;
        private String description;
        private String title;
        private String eventstartdate;
        private String eventenddate;
        private String venue;
        private String location;
        private String timing;
        private String contactname;
        private String contactemail;
        private String contactwebsite;
        private String contactphone;
        private String etype;
        private String emt;
        private String organizedby;
        private int favcount;
        private String shareurl;

        public void setFavcount(int favcount) {
            this.favcount = favcount;
        }

        public int getFavcount() {
            return favcount;
        }

        public String getShareurl() {
            return shareurl;
        }



        public void setShareurl(String shareurl) {
            this.shareurl = shareurl;
        }

        public String getOrganizedby(){return organizedby;}
        public void setOrganizedby(String organizedby){this.organizedby=organizedby;}
        public String getEmt(){return emt;}
        public void setEmt(String emt){this.emt=emt;}
        public String getEtype(){return etype;}
        public void setEtype(String etype){this.etype=etype;}
        public String getVenue(){return venue;}
        public void setVenue(String venue){this.venue=venue;}
        public String getLocation(){return location;}
        public void setLocation(String location){this.location=location;}
        public String getTiming(){return timing;}
        public void setTiming(String timing){this.timing=timing;}
        public String getContactname(){return contactname;}
        public void setContactname(String contactname){this.contactname=contactname;}
        public String getContactemail(){return contactemail;}
        public void setContactemail(String contactemail){this.contactemail=contactemail;}
        public String getContactwebsite(){return contactwebsite;}
        public void setContactwebsite(String contactwebsite){this.contactwebsite=contactwebsite;}
        public String getContactphone(){return contactphone;}
        public void setContactphone(String contactphone){this.contactphone=contactphone;}
        /******** start the Food category names****/
        private String category_name;
        /******** start the Food category names****/

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
        public String getBimage() {
            return bimage;
        }
        public void setBimage(String bimage) {
            this.bimage = bimage;
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
        public String getEventstartdate(){return  eventstartdate;}

        public void setEventstartdate(String eventstartdate) {
            this.eventstartdate = eventstartdate;
        }
        public String getEventenddate(){return eventenddate;}
        public void setEventenddate(String eventenddate){
            this.eventenddate=eventenddate;
        }
        /******** start the Food category names****/
        public String getCategory_name(){return  category_name;}

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }
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

        URLTWO_comment = URLCOMMENT +notifiid+"&page="+ requestCount;


        Cache cache = AppControllers.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(URLTWO_comment);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    pdialog.dismiss();
                    // dissmissDialog();
                    parseJsonFeedTwo(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {
            // making fresh volley request and getting json
            jsonReq = new JsonObjectRequest(Request.Method.GET,
                    URLTWO_comment, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d(TAG_REQUEST, "Response: " + response.toString());
                    if (response != null) {
                        pdialog.dismiss();
                        //   dissmissDialog();
                        parseJsonFeedTwo(response);
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
    private void parseJsonFeedTwo(JSONObject response) {
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
                if(feedArray.length()==0){

                    recycler_comment.setVisibility(View.GONE);
                }else {
                    recycler_comment.setVisibility(View.VISIBLE);

                }
                if(feedArray.length()==0){
                    loadmore_title.setVisibility(View.GONE);
                }else {
                    if(feedArray.length()>4){
                        loadmore_title.setText("Load More");
                    }else {
                        loadmore_title.setVisibility(View.GONE);
                    }
                }

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
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.feed_item_comment_all, parent, false);
                return new UserViewHolder(view);
            } else if (viewType == VIEW_TYPE_LOADING) {
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_loading_item, parent, false);
                return new LoadingViewHolder(view);
            }
            return null;

        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            if (holder instanceof UserViewHolder) {

                final UserViewHolder userViewHolder = (UserViewHolder) holder;

                String simplycity_title_fontPath = "fonts/robotoSlabRegular.ttf";
                Typeface seguiregular = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
                if (mImageLoader == null)
                    mImageLoader = MySingleton.getInstance(getApplicationContext()).getImageLoader();


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






    public static class MyDialogFragment extends DialogFragment {
        private String KEY_COMMENT = "comment";
        private String KEY_TYPE = "qtype";
        private String KEY_MYUID = "user_id";
        private String KEY_POSTID = "id";
        String URLCOMMENT = "http://simpli-city.in/request2.php?rtype=viewcomment&key=simples&qtype=event&id=";
        String urlpost = "http://simpli-city.in/request2.php?rtype=comments&key=simples";
        EditText commentbox;
        Button post_review;
        ImageButton close_back;

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
        String postid, myuserid;

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
            View root = inflater.inflate(R.layout.commenteventfragment, container, false);
            titles = (TextView) root.findViewById(R.id.comments_title);
            requestQueue = Volley.newRequestQueue(getActivity());
            postid = getArguments().getString("POSTID");
            myuserid = getArguments().getString("USERID");
            String simplycity_title_fontPath = "fonts/robotoSlabRegular.ttf";
            Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), simplycity_title_fontPath);
            commentbox = (EditText) root.findViewById(R.id.comment_description);
            post_review = (Button) root.findViewById(R.id.post_button);
            close_back = (ImageButton) root.findViewById(R.id.btn_back_comment_review);
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
            titles.setTypeface(tf);
            titles.setText("Comments");
            post_review.setTypeface(tf);
            post_review.setText("POST");
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
                                    params.put(KEY_TYPE, "event");
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

            URLTWO = URLCOMMENT +postid+"&page="+ requestCount;


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

                    String simplycity_title_fontPath = "fonts/robotoSlabRegular.ttf";
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
