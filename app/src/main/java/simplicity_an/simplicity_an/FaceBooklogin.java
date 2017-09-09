package simplicity_an.simplicity_an;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by kuppusamy on 4/7/2016.
 */
public class FaceBooklogin extends android.app.Activity {
    TextView textitem;
    CallbackManager callbackManager;
    SharedPreferences sharedpreferences;
    private String EMAILID_CHECK="email";
    private String KEY_GCM = "gcm_id";
    public static final String GcmId = "gcmid";
    public static final String mypreference = "mypref";
    public static final String Activity = "activity";
    public static final String CONTENTID = "contentid";
    String contentid;
    String UPLOAD_CHECK_USER="http://simpli-city.in/request2.php?rtype=checkuser&key=simples";
ProgressDialog pdialog;
    public static final String MYUSERID= "myprofileid";
    public static final String USERNAME= "myprofilename";
    public static final String USERIMAGE= "myprofileimage";
    public static final String USEREMAIL= "myprofileemail";
    public static final String USERMAILID= "myprofileemail";
    String text,emaildata,profileimage,activity,gcmids,gendervalue;

    String UPLOAD_URL="http://simpli-city.in/request2.php?rtype=login&key=simples";

    private String KEY_NAME = "name";
    private String KEY_EMAIL= "email";
    private String KEY_GENDER = "gender";
    RequestQueue requestQueue;
    private String KEY_PROFILEIMAGE = "image";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.facebooklogin);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        requestQueue=Volley.newRequestQueue(this);
        gcmids=sharedpreferences.getString(GcmId,"");
        if (sharedpreferences.contains(Activity)) {
            activity=sharedpreferences.getString(Activity,"");
            Log.e("GCMID:",activity);
            // Log.e("IMAGEURL:",profilepicturefromfacebook);

        }
        textitem = (TextView) findViewById(R.id.textView);
        callbackManager = CallbackManager.Factory.create();
        Intent getactivity=getIntent();


        if(AccessToken.getCurrentAccessToken() != null){
            RequestData();
           // share.setVisibility(View.VISIBLE);
           // details.setVisibility(View.VISIBLE);
        }
        LoginManager.getInstance().logInWithReadPermissions( this ,
        Arrays.asList("public_profile", "user_friends","email"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        if (AccessToken.getCurrentAccessToken() != null) {
                            RequestData();
                            //share.setVisibility(View.VISIBLE);
                            // details.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
    }
    public void RequestData(){
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                JSONObject json = response.getJSONObject();
                try {
                    if(json != null){
                         text = json.getString("name");
                        //details_txt.setText(Html.fromHtml(text));
                        profileimage=json.getString("id");
                      // profile.setProfileId(json.getString("id"));
                        emaildata=json.getString("email");
                       // gendervalue=json.getString("gender");
                       // Toast.makeText(getApplicationContext(),text+":"+emaildata,Toast.LENGTH_SHORT).show();
                        pdialog = new ProgressDialog(FaceBooklogin.this);
                        pdialog.show();
                        pdialog.setContentView(R.layout.custom_progressdialog);
                        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        StringRequest facesigin=new StringRequest(Request.Method.POST, UPLOAD_CHECK_USER, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.toString().trim().equalsIgnoreCase("no")){
                                    pdialog.dismiss();

                                    StringRequest upload=new StringRequest(Request.Method.POST, UPLOAD_URL, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            if(response.toString().trim().equalsIgnoreCase("no")) {
                                                Log.e("UserID","no cancel");
                                            }else {
                                                if(response==""||response==null){
                                                    Log.e("UserID","empty");
                                                }else {
                                                    Log.e("UserID", response);
                                                    String[] array = response.split(",");

                                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                                    editor.putString(MYUSERID,array[0]);
                                                    editor.putString(USERNAME,array[1]);
                                                    editor.putString(USERIMAGE,array[2]);
                                                    String emails=array[3].toString();
                                                    editor.putString(USERMAILID,array[3].toString().trim());
                                                    editor.commit();
                                                    Intent main=new Intent(getApplicationContext(),MainPageEnglish.class);
                                                    startActivity(main);
                                                }


                                               /* if(activity.equalsIgnoreCase("articledesc")){
                                                    Intent back=new Intent(FaceBooklogin.this,Articledescription.class);
                                                    startActivity(back);
                                                }else if(activity.equalsIgnoreCase("newsdesc")) {
                                                    Intent back=new Intent(FaceBooklogin.this,NewsDescription.class);
                                                    startActivity(back);
                                                }else if(activity.equalsIgnoreCase("eventdesc")){
                                                    Intent backevent=new Intent(FaceBooklogin.this,EventsDescription.class);
                                                    startActivity(backevent);
                                                }else if(activity.equalsIgnoreCase("sportsdesc")){
                                                    Intent backsports=new Intent(FaceBooklogin.this,SportsnewsDescription.class);
                                                    startActivity(backsports);
                                                }else if(activity.equalsIgnoreCase("govtdesc")){
                                                    Intent backgovt=new Intent(FaceBooklogin.this,GovernmentnotificationsDescriptions.class);
                                                    startActivity(backgovt);
                                                }else if(activity.equalsIgnoreCase("sciencedesc")){
                                                    Intent backscience=new Intent(FaceBooklogin.this,ScienceandTechnologyDescription.class);
                                                    startActivity(backscience);

                                                }else if(activity.equalsIgnoreCase("doitdesc")){
                                                    Intent backdoit=new Intent(FaceBooklogin.this,DoitDescription.class);
                                                    startActivity(backdoit);
                                                }else if(activity.equalsIgnoreCase("edunewsdesc")){
                                                    Intent backeducationnews=new Intent(FaceBooklogin.this,EducationDescription.class);
                                                    startActivity(backeducationnews);
                                                }else if(activity.equalsIgnoreCase("eduintsdesc")){
                                                    Intent backeducationints=new Intent(FaceBooklogin.this,EducationInstitutionsDescription.class);
                                                    startActivity(backeducationints);
                                                }else if(activity.equalsIgnoreCase("edulearndesc")){
                                                    Intent backedulearn=new Intent(FaceBooklogin.this,EducationLearningDescription.class);
                                                    startActivity(backedulearn);
                                                }else if(activity.equalsIgnoreCase("farmingdesc")){
                                                    Intent backfarming=new Intent(FaceBooklogin.this,Farmingdescription.class);
                                                    startActivity(backfarming);
                                                }else if(activity.equalsIgnoreCase("healthdesc")){
                                                    Intent backhealth=new Intent(FaceBooklogin.this, simplicity_an.simplicity_an.Healthylivingdescription.class);
                                                    startActivity(backhealth);
                                                }else if(activity.equalsIgnoreCase("fooddesc")){
                                                    Intent backfood=new Intent(FaceBooklogin.this,FoodAndCookDescriptionPage.class);
                                                    startActivity(backfood);
                                                }else if(activity.equalsIgnoreCase("tipsdesc")){
                                                    Intent backfoodtip=new Intent(FaceBooklogin.this,TipsDescription.class);
                                                    startActivity(backfoodtip);
                                                }else if(activity.equalsIgnoreCase("traveldesc")){
                                                    Intent backtravel=new Intent(FaceBooklogin.this,TravelsDescription.class);
                                                    startActivity(backtravel);
                                                }else if(activity.equalsIgnoreCase("photoesc")){
                                                    Intent backphoto=new Intent(FaceBooklogin.this,PhotoStories.class);
                                                    startActivity(backphoto);
                                                }else if(activity.equalsIgnoreCase("radiofav")){
                                                    Intent backradiofav=new Intent(FaceBooklogin.this,Radioplayerfavplaylist.class);
                                                    startActivity(backradiofav);
                                                }else if(activity.equalsIgnoreCase("radio")){
                                                    Intent backradio=new Intent(FaceBooklogin.this,Radioplayeractivity.class);
                                                    startActivity(backradio);
                                                }else if(activity.equalsIgnoreCase("musicfav")){
                                                    Intent backmusicfav=new Intent(FaceBooklogin.this,Musicfavplayerpage.class);
                                                    startActivity(backmusicfav);
                                                }else if(activity.equalsIgnoreCase("musicnew")){
                                                    Intent backmusicnew=new Intent(FaceBooklogin.this,MusicnewReleaseplayer.class);
                                                    startActivity(backmusicnew);
                                                }else if(activity.equalsIgnoreCase("music")){
                                                    Intent backmusic= new Intent(FaceBooklogin.this,Musicplayerpage.class);
                                                    startActivity(backmusic);
                                                }else if(activity.equalsIgnoreCase("theatre")){
                                                    Intent backtheatre= new Intent(FaceBooklogin.this,TheatreItemDetail.class);
                                                    startActivity(backtheatre);
                                                }*//*else if(activity.equalsIgnoreCase("citycenter")){
                                                    Intent citycenter= new Intent(FaceBooklogin.this,CityCenter.class);
                                                    startActivity(citycenter);
                                                }else if(activity.equalsIgnoreCase("citycenterprofile")){
                                                    Intent citycenterprofile= new Intent(FaceBooklogin.this,CityProfilePage.class);
                                                    startActivity(citycenterprofile);
                                                }*//*else if(activity.equalsIgnoreCase("notification")){
                                                    Intent notifymyprofilee= new Intent(FaceBooklogin.this, MainPageEnglish.class);
                                                    startActivity(notifymyprofilee);
                                                }else if(activity.equalsIgnoreCase("mainversion")){
                                                    Intent mainversion= new Intent(FaceBooklogin.this, MainPageEnglish.class);
                                                    startActivity(mainversion);
                                                }else if(activity.equalsIgnoreCase("entertainmentversion")){
                                                    Intent entertainversion=new Intent(FaceBooklogin.this,EntertainmentVersiontwo.class);
                                                    startActivity(entertainversion);
                                                }else if(activity.equalsIgnoreCase("beyondversion")){
                                                    Intent entertainversion=new Intent(FaceBooklogin.this,BeyondActivity.class);
                                                    startActivity(entertainversion);
                                                }else if(activity.equalsIgnoreCase("mainversiontamil")){
                                                    Intent mainversion= new Intent(FaceBooklogin.this,MainPageTamil.class);
                                                    startActivity(mainversion);
                                                }else if(activity.equalsIgnoreCase("entertainmentversiontamil")){
                                                    Intent entertainversion=new Intent(FaceBooklogin.this,TamilEntertainment.class);
                                                    startActivity(entertainversion);
                                                }else if(activity.equalsIgnoreCase("beyondversiontamil")){
                                                    Intent entertainversion=new Intent(FaceBooklogin.this,TamilBeyond.class);
                                                    startActivity(entertainversion);
                                                }

                                                else if(activity.equalsIgnoreCase("articledesctamil")){
                                                    Intent back=new Intent(FaceBooklogin.this,TamilArticledescription.class);
                                                    startActivity(back);
                                                }else if(activity.equalsIgnoreCase("newsdesctamil")) {
                                                    Intent back=new Intent(FaceBooklogin.this,TamilNewsDescription.class);
                                                    startActivity(back);
                                                }else if(activity.equalsIgnoreCase("eventdesctamil")){
                                                    Intent backevent=new Intent(FaceBooklogin.this,TamilEventsDescription.class);
                                                    startActivity(backevent);
                                                }else if(activity.equalsIgnoreCase("sportsdesctamil")){
                                                    Intent backsports=new Intent(FaceBooklogin.this,TamilSportsnewsDescription.class);
                                                    startActivity(backsports);
                                                }else if(activity.equalsIgnoreCase("govtdesctamil")){
                                                    Intent backgovt=new Intent(FaceBooklogin.this,Govtdescriptiontamil.class);
                                                    startActivity(backgovt);
                                                }else if(activity.equalsIgnoreCase("sciencedesctamil")){
                                                    Intent backscience=new Intent(FaceBooklogin.this,ScienceandTechnologyDescriptiontamil.class);
                                                    startActivity(backscience);

                                                }else if(activity.equalsIgnoreCase("doitdesctamil")){
                                                    Intent backdoit=new Intent(FaceBooklogin.this,DoitDescriptiontamil.class);
                                                    startActivity(backdoit);
                                                }else if(activity.equalsIgnoreCase("edunewsdesctamil")){
                                                    Intent backeducationnews=new Intent(FaceBooklogin.this,EducationDescriptiontamil.class);
                                                    startActivity(backeducationnews);
                                                }else if(activity.equalsIgnoreCase("eduintsdesctamil")){
                                                    Intent backeducationints=new Intent(FaceBooklogin.this,EducationInstitutionsDescriptiontamil.class);
                                                    startActivity(backeducationints);
                                                }else if(activity.equalsIgnoreCase("edulearndesctamil")){
                                                    Intent backedulearn=new Intent(FaceBooklogin.this,EducationLearningDescriptiontamil.class);
                                                    startActivity(backedulearn);
                                                }else if(activity.equalsIgnoreCase("farmingdesctamil")){
                                                    Intent backfarming=new Intent(FaceBooklogin.this,Farmingdescriptiontamil.class);
                                                    startActivity(backfarming);
                                                }else if(activity.equalsIgnoreCase("healthdesctamil")){
                                                    Intent backhealth=new Intent(FaceBooklogin.this,Healthdescriptiontamil.class);
                                                    startActivity(backhealth);
                                                }else if(activity.equalsIgnoreCase("fooddesctamil")){
                                                    Intent backfood=new Intent(FaceBooklogin.this,FoodAndCookDescriptionPagetamil.class);
                                                    startActivity(backfood);
                                                }else if(activity.equalsIgnoreCase("tipsdesctamil")){
                                                    Intent backfoodtip=new Intent(FaceBooklogin.this,TipsDescriptionTamil.class);
                                                    startActivity(backfoodtip);
                                                }else if(activity.equalsIgnoreCase("traveldesctamil")){
                                                    Intent backtravel=new Intent(FaceBooklogin.this,TravelsDescriptiontamil.class);
                                                    startActivity(backtravel);
                                                }else if(activity.equalsIgnoreCase("radiofavtamil")){
                                                    Intent backradiofav=new Intent(FaceBooklogin.this,Radioplayerfavplaylisttamil.class);
                                                    startActivity(backradiofav);
                                                }else if(activity.equalsIgnoreCase("radiotamil")){
                                                    Intent backradio=new Intent(FaceBooklogin.this,Radioplayeractivitytamil.class);
                                                    startActivity(backradio);
                                                }else{
                                                    Intent main=new Intent(getApplicationContext(),MainActivityVersiontwo.class);
                                                    startActivity(main);
                                                }*/
                                                Intent main=new Intent(getApplicationContext(),MainPageEnglish.class);
                                                startActivity(main);

                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                        }
                                    }){
                                        protected Map<String ,String> getParams()throws AuthFailureError{
                                            Map<String,String> params=new Hashtable<String, String>();

                                            params.put(KEY_EMAIL,emaildata);
                                            params.put(KEY_NAME,text);
                                            params.put(KEY_PROFILEIMAGE,"https://graph.facebook.com/" + profileimage + "/picture");
                                           // params.put(KEY_GENDER,gendervalue);
                                            params.put(KEY_GCM,gcmids);
                                            return  params;
                                        }
                                    };
                                    upload.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                    requestQueue.add(upload);



                                    /*Intent signincomplete=new Intent(FaceBooklogin.this,SigninComplete.class);
                                    signincomplete.putExtra("NAME",text);
                                    signincomplete.putExtra("EMAIL", emaildata);
                                    signincomplete.putExtra("ACTIVITY", activity);
                                    signincomplete.putExtra("IMAGEURL",profileimage);
                                    signincomplete.putExtra("FACEACTIVITY","Facebooklogin");
                                    startActivity(signincomplete);*/
                                }else {
                                    pdialog.dismiss();
                                  //  Log.e("Activity",activity);
                                    if(response==""||response==null){
                                        Log.e("UserID","empty");
                                    }else {
                                        Log.e("UserID", response);
                                        String[] array = response.split(",");

                                        SharedPreferences.Editor editor = sharedpreferences.edit();
                                        editor.putString(MYUSERID,array[0]);
                                        editor.putString(USERNAME,array[1]);
                                        editor.putString(USERIMAGE,array[2]);
                                        String emails=array[3].toString();
                                        editor.putString(USERMAILID,array[3].toString().trim());
                                        editor.commit();
                                        Intent main=new Intent(getApplicationContext(),MainPageEnglish.class);
                                        startActivity(main);
                                    }

                                   /* if(activity.equalsIgnoreCase("articledesc")){
                                        Intent back=new Intent(FaceBooklogin.this,Articledescription.class);
                                        startActivity(back);
                                    }else if(activity.equalsIgnoreCase("newsdesc")) {
                                        Intent back=new Intent(FaceBooklogin.this,NewsDescription.class);
                                        startActivity(back);
                                    }else if(activity.equalsIgnoreCase("eventdesc")){
                                        Intent backevent=new Intent(FaceBooklogin.this,EventsDescription.class);
                                        startActivity(backevent);
                                    }else if(activity.equalsIgnoreCase("sportsdesc")){
                                        Intent backsports=new Intent(FaceBooklogin.this,SportsnewsDescription.class);
                                        startActivity(backsports);
                                    }else if(activity.equalsIgnoreCase("govtdesc")){
                                        Intent backgovt=new Intent(FaceBooklogin.this,GovernmentnotificationsDescriptions.class);
                                        startActivity(backgovt);
                                    }else if(activity.equalsIgnoreCase("sciencedesc")){
                                        Intent backscience=new Intent(FaceBooklogin.this,ScienceandTechnologyDescription.class);
                                        startActivity(backscience);

                                    }else if(activity.equalsIgnoreCase("doitdesc")){
                                        Intent backdoit=new Intent(FaceBooklogin.this,DoitDescription.class);
                                        startActivity(backdoit);
                                    }else if(activity.equalsIgnoreCase("edunewsdesc")){
                                        Intent backeducationnews=new Intent(FaceBooklogin.this,EducationDescription.class);
                                        startActivity(backeducationnews);
                                    }else if(activity.equalsIgnoreCase("eduintsdesc")){
                                        Intent backeducationints=new Intent(FaceBooklogin.this,EducationInstitutionsDescription.class);
                                        startActivity(backeducationints);
                                    }else if(activity.equalsIgnoreCase("edulearndesc")){
                                        Intent backedulearn=new Intent(FaceBooklogin.this,EducationLearningDescription.class);
                                        startActivity(backedulearn);
                                    }else if(activity.equalsIgnoreCase("farmingdesc")){
                                        Intent backfarming=new Intent(FaceBooklogin.this,Farmingdescription.class);
                                        startActivity(backfarming);
                                    }else if(activity.equalsIgnoreCase("healthdesc")){
                                        Intent backhealth=new Intent(FaceBooklogin.this, simplicity_an.simplicity_an.Healthylivingdescription.class);
                                        startActivity(backhealth);
                                    }else if(activity.equalsIgnoreCase("fooddesc")){
                                        Intent backfood=new Intent(FaceBooklogin.this,FoodAndCookDescriptionPage.class);
                                        startActivity(backfood);
                                    }else if(activity.equalsIgnoreCase("tipsdesc")){
                                        Intent backfoodtip=new Intent(FaceBooklogin.this,TipsDescription.class);
                                        startActivity(backfoodtip);
                                    }else if(activity.equalsIgnoreCase("traveldesc")){
                                        Intent backtravel=new Intent(FaceBooklogin.this,TravelsDescription.class);
                                        startActivity(backtravel);
                                    }else if(activity.equalsIgnoreCase("photoesc")){
                                        Intent backphoto=new Intent(FaceBooklogin.this,PhotoStories.class);
                                        startActivity(backphoto);
                                    }else if(activity.equalsIgnoreCase("radiofav")){
                                        Intent backradiofav=new Intent(FaceBooklogin.this,Radioplayerfavplaylist.class);
                                        startActivity(backradiofav);
                                    }else if(activity.equalsIgnoreCase("radio")){
                                        Intent backradio=new Intent(FaceBooklogin.this,Radioplayeractivity.class);
                                        startActivity(backradio);
                                    }else if(activity.equalsIgnoreCase("musicfav")){
                                        Intent backmusicfav=new Intent(FaceBooklogin.this,Musicfavplayerpage.class);
                                        startActivity(backmusicfav);
                                    }else if(activity.equalsIgnoreCase("musicnew")){
                                        Intent backmusicnew=new Intent(FaceBooklogin.this,MusicnewReleaseplayer.class);
                                        startActivity(backmusicnew);
                                    }else if(activity.equalsIgnoreCase("music")){
                                        Intent backmusic= new Intent(FaceBooklogin.this,Musicplayerpage.class);
                                        startActivity(backmusic);
                                    }else if(activity.equalsIgnoreCase("theatre")){
                                        Intent backtheatre= new Intent(FaceBooklogin.this,TheatreItemDetail.class);
                                        startActivity(backtheatre);
                                    }*//*else if(activity.equalsIgnoreCase("citycenter")){
                                        Intent citycenter= new Intent(FaceBooklogin.this,CityCenter.class);
                                        startActivity(citycenter);
                                    }else if(activity.equalsIgnoreCase("citycenterprofile")){
                                        Intent citycenterprofile= new Intent(FaceBooklogin.this,CityProfilePage.class);
                                        startActivity(citycenterprofile);
                                    }*//*else if(activity.equalsIgnoreCase("notifications")){
                                        Intent notifymyprofilee= new Intent(getApplicationContext(), MainPageEnglish.class);
                                        startActivity(notifymyprofilee);
                                    }else if(activity.equalsIgnoreCase("mainversion")){
                                        Intent mainversion= new Intent(FaceBooklogin.this, MainPageEnglish.class);
                                        startActivity(mainversion);
                                    }else if(activity.equalsIgnoreCase("entertainmentversion")){
                                        Intent entertainversion=new Intent(FaceBooklogin.this,EntertainmentVersiontwo.class);
                                        startActivity(entertainversion);
                                    }else if(activity.equalsIgnoreCase("beyondversion")){
                                        Intent entertainversion=new Intent(FaceBooklogin.this,BeyondActivity.class);
                                        startActivity(entertainversion);
                                    }else if(activity.equalsIgnoreCase("mainversiontamil")){
                                        Intent mainversion= new Intent(FaceBooklogin.this,MainPageTamil.class);
                                        startActivity(mainversion);
                                    }else if(activity.equalsIgnoreCase("entertainmentversiontamil")){
                                        Intent entertainversion=new Intent(FaceBooklogin.this,TamilEntertainment.class);
                                        startActivity(entertainversion);
                                    }else if(activity.equalsIgnoreCase("beyondversiontamil")){
                                        Intent entertainversion=new Intent(FaceBooklogin.this,TamilBeyond.class);
                                        startActivity(entertainversion);
                                    }

                                   else if(activity.equalsIgnoreCase("articledesctamil")){
                                        Intent back=new Intent(FaceBooklogin.this,TamilArticledescription.class);
                                        startActivity(back);
                                    }else if(activity.equalsIgnoreCase("newsdesctamil")) {
                                        Intent back=new Intent(FaceBooklogin.this,TamilNewsDescription.class);
                                        startActivity(back);
                                    }else if(activity.equalsIgnoreCase("eventdesctamil")){
                                        Intent backevent=new Intent(FaceBooklogin.this,TamilEventsDescription.class);
                                        startActivity(backevent);
                                    }else if(activity.equalsIgnoreCase("sportsdesctamil")){
                                        Intent backsports=new Intent(FaceBooklogin.this,TamilSportsnewsDescription.class);
                                        startActivity(backsports);
                                    }else if(activity.equalsIgnoreCase("govtdesctamil")){
                                        Intent backgovt=new Intent(FaceBooklogin.this,Govtdescriptiontamil.class);
                                        startActivity(backgovt);
                                    }else if(activity.equalsIgnoreCase("sciencedesctamil")){
                                        Intent backscience=new Intent(FaceBooklogin.this,ScienceandTechnologyDescriptiontamil.class);
                                        startActivity(backscience);

                                    }else if(activity.equalsIgnoreCase("doitdesctamil")){
                                        Intent backdoit=new Intent(FaceBooklogin.this,DoitDescriptiontamil.class);
                                        startActivity(backdoit);
                                    }else if(activity.equalsIgnoreCase("edunewsdesctamil")){
                                        Intent backeducationnews=new Intent(FaceBooklogin.this,EducationDescriptiontamil.class);
                                        startActivity(backeducationnews);
                                    }else if(activity.equalsIgnoreCase("eduintsdesctamil")){
                                        Intent backeducationints=new Intent(FaceBooklogin.this,EducationInstitutionsDescriptiontamil.class);
                                        startActivity(backeducationints);
                                    }else if(activity.equalsIgnoreCase("edulearndesctamil")){
                                        Intent backedulearn=new Intent(FaceBooklogin.this,EducationLearningDescriptiontamil.class);
                                        startActivity(backedulearn);
                                    }else if(activity.equalsIgnoreCase("farmingdesctamil")){
                                        Intent backfarming=new Intent(FaceBooklogin.this,Farmingdescriptiontamil.class);
                                        startActivity(backfarming);
                                    }else if(activity.equalsIgnoreCase("healthdesctamil")){
                                        Intent backhealth=new Intent(FaceBooklogin.this,Healthdescriptiontamil.class);
                                        startActivity(backhealth);
                                    }else if(activity.equalsIgnoreCase("fooddesctamil")){
                                        Intent backfood=new Intent(FaceBooklogin.this,FoodAndCookDescriptionPagetamil.class);
                                        startActivity(backfood);
                                    }else if(activity.equalsIgnoreCase("tipsdesctamil")){
                                        Intent backfoodtip=new Intent(FaceBooklogin.this,TipsDescriptionTamil.class);
                                        startActivity(backfoodtip);
                                    }else if(activity.equalsIgnoreCase("traveldesctamil")){
                                        Intent backtravel=new Intent(FaceBooklogin.this,TravelsDescriptiontamil.class);
                                        startActivity(backtravel);
                                    }else if(activity.equalsIgnoreCase("radiofavtamil")){
                                        Intent backradiofav=new Intent(FaceBooklogin.this,Radioplayerfavplaylisttamil.class);
                                        startActivity(backradiofav);
                                    }else if(activity.equalsIgnoreCase("radiotamil")){
                                        Intent backradio=new Intent(FaceBooklogin.this,Radioplayeractivitytamil.class);
                                        startActivity(backradio);
                                    }else{
                                        Intent main=new Intent(getApplicationContext(),MainActivityVersiontwo.class);
                                        startActivity(main);
                                    }*/
                                    Intent main=new Intent(getApplicationContext(),MainPageEnglish.class);
                                    startActivity(main);
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }){
                            protected Map<String,String> getParams()throws AuthFailureError{
                                Map<String,String > params=new HashMap<String, String>();
                                params.put(EMAILID_CHECK,emaildata);
                                params.put(KEY_GCM,gcmids);
                                return params;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(FaceBooklogin.this);


                        //Adding request to the queue
                        requestQueue.add(facesigin);
                        //textitem.setText(Html.fromHtml(text));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        request.setParameters(parameters);
        request.executeAsync();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}

