package simplicity_an.simplicity_an.MainTamil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.onesignal.OneSignal;

import simplicity_an.simplicity_an.MainEnglish.HappeningFrag;
import simplicity_an.simplicity_an.MusicplayerBottom;
import simplicity_an.simplicity_an.R;
import simplicity_an.simplicity_an.Tamil.TamilEntertainmentall;
import simplicity_an.simplicity_an.Tamil.TamilRadio;
import simplicity_an.simplicity_an.Tamil.TamilTaball;
import simplicity_an.simplicity_an.Tamil.Tamilentertainmentmusic;
import simplicity_an.simplicity_an.Tamil.Tamilentertainmentradio;
import simplicity_an.simplicity_an.Tamil.Tamilnews;

/**
 * Created by kuppusamy on 5/18/2017.
 */

public class MainPageTamil extends AppCompatActivity implements TamilTaball.OnFragmentInteractionListener,TamilRadio.OnFragmentInteractionListener, TamilEntertainmentall.OnFragmentInteractionListener,Tamilentertainmentmusic.OnFragmentInteractionListener,Tamilentertainmentradio.OnFragmentInteractionListener,Tamilnews.OnFragmentInteractionListener{
    ImageButton city,happening,search,audio_video,settings;
    SharedPreferences sharedpreferences;
    public static final String backgroundcolor = "color";
    String colorcodes;
    public static final String mypreference = "mypref";
    String myprofileid;
    public static final String MYUSERID= "myprofileid";
    public static final String GcmId = "gcmid";
    String playerid;
    String id,radio_title,radio_url;
    RelativeLayout topLevelLayout;


    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.mainpagetamilmain);
        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");
            myprofileid = myprofileid.replaceAll("\\D+","");
        }
        colorcodes=sharedpreferences.getString(backgroundcolor,"");
        Intent get=getIntent();
        id=get.getStringExtra("ID");
        radio_title=get.getStringExtra("TITLE");
        radio_url=get.getStringExtra("URL");
//Check();


        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
                Log.d("debug", "User:" + userId);
                Log.e("ColorCodes",userId);
                SharedPreferences.Editor gcmidseditor=sharedpreferences.edit();
                gcmidseditor.putString(GcmId,userId);
                gcmidseditor.commit();
                playerid=userId;
                if (registrationId != null)
                    Log.d("debug", "registrationId:" + registrationId);

            }
        });
        city=(ImageButton)findViewById(R.id.btn_versiontwocity);
        happening=(ImageButton)findViewById(R.id.btn_versiontwobeyond);
        search=(ImageButton)findViewById(R.id.btn_versiontwosearch);
        audio_video=(ImageButton)findViewById(R.id.btn_versiontwoexplore);
        settings=(ImageButton)findViewById(R.id.btn_versiontwonotifications);
        topLevelLayout=(RelativeLayout)findViewById(R.id.top_layout);
      topLevelLayout.setVisibility(View.GONE);




        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                happening.setBackgroundResource(R.color.mytransparent);
                search.setBackgroundResource(R.color.mytransparent);
                audio_video.setBackgroundResource(R.color.mytransparent);
                settings.setBackgroundResource(R.color.mytransparent);
                Fragment selectedFragment = null;
                selectedFragment = CityFragmentTamil.newInstance();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();
                if(colorcodes.length()==0){

                }else {
                    if(colorcodes.equalsIgnoreCase("004")){
                        Log.e("Msg","hihihi");
                    }else {
                        if(colorcodes.equalsIgnoreCase("#383838")){
                            city.setBackgroundResource(R.color.theme1button);
                        }else if(colorcodes.equalsIgnoreCase("#59247c")){
                            city.setBackgroundResource(R.color.theme2);
                        }else if(colorcodes.equalsIgnoreCase("#1d487a")){
                            city.setBackgroundResource(R.color.theme3);
                        }else if(colorcodes.equalsIgnoreCase("#7A4100")){
                            city.setBackgroundResource(R.color.theme4);
                        }else if(colorcodes.equalsIgnoreCase("#6E0138")){
                            city.setBackgroundResource(R.color.theme5);
                        }else if(colorcodes.equalsIgnoreCase("#00BFD4")){
                            city.setBackgroundResource(R.color.theme6);
                        }else if(colorcodes.equalsIgnoreCase("#185546")){
                            city.setBackgroundResource(R.color.theme7);
                        }else if(colorcodes.equalsIgnoreCase("#D0A06F")){
                            city.setBackgroundResource(R.color.theme8);
                        }else if(colorcodes.equalsIgnoreCase("#82C6E6")){
                            city.setBackgroundResource(R.color.theme9);
                        }else if(colorcodes.equalsIgnoreCase("#339900")){
                            city.setBackgroundResource(R.color.theme10);
                        }else if(colorcodes.equalsIgnoreCase("#CC9C00")){
                            city.setBackgroundResource(R.color.theme11);
                        }else if(colorcodes.equalsIgnoreCase("#00B09B")){
                            city.setBackgroundResource(R.color.theme12);
                        }
                    }
                }
            }
        });
        happening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                city.setBackgroundResource(R.color.mytransparent);
                search.setBackgroundResource(R.color.mytransparent);
                audio_video.setBackgroundResource(R.color.mytransparent);
                settings.setBackgroundResource(R.color.mytransparent);
                Fragment selectedFragment = null;
                selectedFragment = HappeningFragTamil.newInstance();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();
                if(colorcodes.length()==0){

                }else {
                    if(colorcodes.equalsIgnoreCase("004")){
                        Log.e("Msg","hihihi");
                    }else {
                        if(colorcodes.equalsIgnoreCase("#383838")){
                            happening.setBackgroundResource(R.color.theme1button);
                        }else if(colorcodes.equalsIgnoreCase("#59247c")){
                            happening.setBackgroundResource(R.color.theme2);
                        }else if(colorcodes.equalsIgnoreCase("#1d487a")){
                            happening.setBackgroundResource(R.color.theme3);
                        }else if(colorcodes.equalsIgnoreCase("#7A4100")){
                            happening.setBackgroundResource(R.color.theme4);
                        }else if(colorcodes.equalsIgnoreCase("#6E0138")){
                            happening.setBackgroundResource(R.color.theme5);
                        }else if(colorcodes.equalsIgnoreCase("#00BFD4")){
                            happening.setBackgroundResource(R.color.theme6);
                        }else if(colorcodes.equalsIgnoreCase("#185546")){
                            happening.setBackgroundResource(R.color.theme7);
                        }else if(colorcodes.equalsIgnoreCase("#D0A06F")){
                            happening.setBackgroundResource(R.color.theme8);
                        }else if(colorcodes.equalsIgnoreCase("#82C6E6")){
                            happening.setBackgroundResource(R.color.theme9);
                        }else if(colorcodes.equalsIgnoreCase("#339900")){
                            happening.setBackgroundResource(R.color.theme10);
                        }else if(colorcodes.equalsIgnoreCase("#CC9C00")){
                            happening.setBackgroundResource(R.color.theme11);
                        }else if(colorcodes.equalsIgnoreCase("#00B09B")){
                            happening.setBackgroundResource(R.color.theme12);
                        }
                    }
                }
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  if(myprofileid!=null) {
                happening.setBackgroundResource(R.color.mytransparent);
                city.setBackgroundResource(R.color.mytransparent);
                audio_video.setBackgroundResource(R.color.mytransparent);
                settings.setBackgroundResource(R.color.mytransparent);
            /*    Intent in = new Intent(getApplicationContext(), ExploreMain.class);
                    startActivity(in);*/
              FragmentManager fragmentManager= getSupportFragmentManager();
                MainFragTamil fragments = new MainFragTamil();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, fragments);
                fragmentTransaction.commit();
                if (colorcodes.length() == 0) {

                } else {
                    if (colorcodes.equalsIgnoreCase("004")) {
                        Log.e("Msg", "hihihi");
                    } else {
                        if (colorcodes.equalsIgnoreCase("#383838")) {
                            search.setBackgroundResource(R.color.theme1button);
                        } else if (colorcodes.equalsIgnoreCase("#59247c")) {
                            search.setBackgroundResource(R.color.theme2);
                        } else if (colorcodes.equalsIgnoreCase("#1d487a")) {
                            search.setBackgroundResource(R.color.theme3);
                        } else if (colorcodes.equalsIgnoreCase("#7A4100")) {
                            search.setBackgroundResource(R.color.theme4);
                        } else if (colorcodes.equalsIgnoreCase("#6E0138")) {
                            search.setBackgroundResource(R.color.theme5);
                        } else if (colorcodes.equalsIgnoreCase("#00BFD4")) {
                            search.setBackgroundResource(R.color.theme6);
                        } else if (colorcodes.equalsIgnoreCase("#185546")) {
                            search.setBackgroundResource(R.color.theme7);
                        } else if (colorcodes.equalsIgnoreCase("#D0A06F")) {
                            search.setBackgroundResource(R.color.theme8);
                        } else if (colorcodes.equalsIgnoreCase("#82C6E6")) {
                            search.setBackgroundResource(R.color.theme9);
                        } else if (colorcodes.equalsIgnoreCase("#339900")) {
                            search.setBackgroundResource(R.color.theme10);
                        } else if (colorcodes.equalsIgnoreCase("#CC9C00")) {
                            search.setBackgroundResource(R.color.theme11);
                        } else if (colorcodes.equalsIgnoreCase("#00B09B")) {
                            search.setBackgroundResource(R.color.theme12);
                        }
                    }
                }
                /*}else {
                    Intent in = new Intent(getApplicationContext(), SigninpageActivity.class);
                    startActivity(in);
                }*/
            }
        });
        audio_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                happening.setBackgroundResource(R.color.mytransparent);
                search.setBackgroundResource(R.color.mytransparent);
                city.setBackgroundResource(R.color.mytransparent);
                settings.setBackgroundResource(R.color.mytransparent);
                Fragment selectedFragment = null;
                selectedFragment = EntertainmentFragmentTamil.newInstance();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();
                if(colorcodes.length()==0){

                }else {
                    if(colorcodes.equalsIgnoreCase("004")){
                        Log.e("Msg","hihihi");
                    }else {
                        if(colorcodes.equalsIgnoreCase("#383838")){
                            audio_video.setBackgroundResource(R.color.theme1button);
                        }else if(colorcodes.equalsIgnoreCase("#59247c")){
                            audio_video.setBackgroundResource(R.color.theme2);
                        }else if(colorcodes.equalsIgnoreCase("#1d487a")){
                            audio_video.setBackgroundResource(R.color.theme3);
                        }else if(colorcodes.equalsIgnoreCase("#7A4100")){
                            audio_video.setBackgroundResource(R.color.theme4);
                        }else if(colorcodes.equalsIgnoreCase("#6E0138")){
                            audio_video.setBackgroundResource(R.color.theme5);
                        }else if(colorcodes.equalsIgnoreCase("#00BFD4")){
                            audio_video.setBackgroundResource(R.color.theme6);
                        }else if(colorcodes.equalsIgnoreCase("#185546")){
                            audio_video.setBackgroundResource(R.color.theme7);
                        }else if(colorcodes.equalsIgnoreCase("#D0A06F")){
                            audio_video.setBackgroundResource(R.color.theme8);
                        }else if(colorcodes.equalsIgnoreCase("#82C6E6")){
                            audio_video.setBackgroundResource(R.color.theme9);
                        }else if(colorcodes.equalsIgnoreCase("#339900")){
                            audio_video.setBackgroundResource(R.color.theme10);
                        }else if(colorcodes.equalsIgnoreCase("#CC9C00")){
                            audio_video.setBackgroundResource(R.color.theme11);
                        }else if(colorcodes.equalsIgnoreCase("#00B09B")){
                            audio_video.setBackgroundResource(R.color.theme12);
                        }
                    }
                }
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                happening.setBackgroundResource(R.color.mytransparent);
                search.setBackgroundResource(R.color.mytransparent);
                audio_video.setBackgroundResource(R.color.mytransparent);
                city.setBackgroundResource(R.color.mytransparent);
                FragmentManager fragmentManager = getSupportFragmentManager();
                SettingsFragmentTamil fragment = new SettingsFragmentTamil();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, fragment);
                fragmentTransaction.commit();
                if(colorcodes.length()==0){

                }else {
                    if(colorcodes.equalsIgnoreCase("004")){
                        Log.e("Msg","hihihi");
                    }else {
                        if(colorcodes.equalsIgnoreCase("#383838")){
                            settings.setBackgroundResource(R.color.theme1button);
                        }else if(colorcodes.equalsIgnoreCase("#59247c")){
                            settings.setBackgroundResource(R.color.theme2);
                        }else if(colorcodes.equalsIgnoreCase("#1d487a")){
                            settings.setBackgroundResource(R.color.theme3);
                        }else if(colorcodes.equalsIgnoreCase("#7A4100")){
                            settings.setBackgroundResource(R.color.theme4);
                        }else if(colorcodes.equalsIgnoreCase("#6E0138")){
                            settings.setBackgroundResource(R.color.theme5);
                        }else if(colorcodes.equalsIgnoreCase("#00BFD4")){
                            settings.setBackgroundResource(R.color.theme6);
                        }else if(colorcodes.equalsIgnoreCase("#185546")){
                            settings.setBackgroundResource(R.color.theme7);
                        }else if(colorcodes.equalsIgnoreCase("#D0A06F")){
                            settings.setBackgroundResource(R.color.theme8);
                        }else if(colorcodes.equalsIgnoreCase("#82C6E6")){
                            settings.setBackgroundResource(R.color.theme9);
                        }else if(colorcodes.equalsIgnoreCase("#339900")){
                            settings.setBackgroundResource(R.color.theme10);
                        }else if(colorcodes.equalsIgnoreCase("#CC9C00")){
                            settings.setBackgroundResource(R.color.theme11);
                        }else if(colorcodes.equalsIgnoreCase("#00B09B")){
                            settings.setBackgroundResource(R.color.theme12);
                        }
                    }
                }
            }
        });
        /*if(radio_url!=null){
            onFragmentInteraction(radio_url,radio_title);
        }else {

        }*/
        if(id!=null){
            changefrag();
        }else {


            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, CityFragmentTamil.newInstance());
            transaction.commit();
            if(colorcodes.length()==0){

            }else {
                if(colorcodes.equalsIgnoreCase("004")){
                    Log.e("Msg","hihihi");
                }else {
                    if(colorcodes.equalsIgnoreCase("#383838")){
                        city.setBackgroundResource(R.color.theme1button);
                    }else if(colorcodes.equalsIgnoreCase("#59247c")){
                        city.setBackgroundResource(R.color.theme2);
                    }else if(colorcodes.equalsIgnoreCase("#1d487a")){
                        city.setBackgroundResource(R.color.theme3);
                    }else if(colorcodes.equalsIgnoreCase("#7A4100")){
                        city.setBackgroundResource(R.color.theme4);
                    }else if(colorcodes.equalsIgnoreCase("#6E0138")){
                        city.setBackgroundResource(R.color.theme5);
                    }else if(colorcodes.equalsIgnoreCase("#00BFD4")){
                        city.setBackgroundResource(R.color.theme6);
                    }else if(colorcodes.equalsIgnoreCase("#185546")){
                        city.setBackgroundResource(R.color.theme7);
                    }else if(colorcodes.equalsIgnoreCase("#D0A06F")){
                        city.setBackgroundResource(R.color.theme8);
                    }else if(colorcodes.equalsIgnoreCase("#82C6E6")){
                        city.setBackgroundResource(R.color.theme9);
                    }else if(colorcodes.equalsIgnoreCase("#339900")){
                        city.setBackgroundResource(R.color.theme10);
                    }else if(colorcodes.equalsIgnoreCase("#CC9C00")){
                        city.setBackgroundResource(R.color.theme11);
                    }else if(colorcodes.equalsIgnoreCase("#00B09B")){
                        city.setBackgroundResource(R.color.theme12);
                    }
                }
            }
        }
    }
    private boolean isFirstTime()
    {
        /*SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore", false);*/
       /* if (!ranBefore) {

            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.commit();*/
        topLevelLayout.setVisibility(View.VISIBLE);
        topLevelLayout.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                topLevelLayout.setVisibility(View.INVISIBLE);
                return false;
            }

        });


        // }
        // return ranBefore;
        return true;

    }



    public void onFragmentInteraction(String playurl, String title,String image) {
        MusicplayerBottom secondFragment = (MusicplayerBottom) getSupportFragmentManager().findFragmentById(R.id.musicbottomplayer);
        secondFragment.PlaySong(playurl, title ,image );
    }

    public void Check(){
        if( MusicplayerBottom.mediaPlayer.isPlaying()){
            MusicplayerBottom.mediaPlayer.stop();
        }else {

        }
    }
    private void changefrag(){
        if(id.equals("1")){
            happening.setBackgroundResource(R.color.mytransparent);
            search.setBackgroundResource(R.color.mytransparent);
            audio_video.setBackgroundResource(R.color.mytransparent);
            city.setBackgroundResource(R.color.mytransparent);
            FragmentManager fragmentManager = getSupportFragmentManager();
            CityFragmentTamil fragment = new CityFragmentTamil();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment);
            fragmentTransaction.commit();
            if(colorcodes.length()==0){

            }else {
                if(colorcodes.equalsIgnoreCase("004")){
                    Log.e("Msg","hihihi");
                }else {
                    if(colorcodes.equalsIgnoreCase("#383838")){
                        settings.setBackgroundResource(R.color.theme1button);
                    }else if(colorcodes.equalsIgnoreCase("#59247c")){
                        settings.setBackgroundResource(R.color.theme2);
                    }else if(colorcodes.equalsIgnoreCase("#1d487a")){
                        settings.setBackgroundResource(R.color.theme3);
                    }else if(colorcodes.equalsIgnoreCase("#7A4100")){
                        settings.setBackgroundResource(R.color.theme4);
                    }else if(colorcodes.equalsIgnoreCase("#6E0138")){
                        settings.setBackgroundResource(R.color.theme5);
                    }else if(colorcodes.equalsIgnoreCase("#00BFD4")){
                        settings.setBackgroundResource(R.color.theme6);
                    }else if(colorcodes.equalsIgnoreCase("#185546")){
                        settings.setBackgroundResource(R.color.theme7);
                    }else if(colorcodes.equalsIgnoreCase("#D0A06F")){
                        settings.setBackgroundResource(R.color.theme8);
                    }else if(colorcodes.equalsIgnoreCase("#82C6E6")){
                        settings.setBackgroundResource(R.color.theme9);
                    }else if(colorcodes.equalsIgnoreCase("#339900")){
                        settings.setBackgroundResource(R.color.theme10);
                    }else if(colorcodes.equalsIgnoreCase("#CC9C00")){
                        settings.setBackgroundResource(R.color.theme11);
                    }else if(colorcodes.equalsIgnoreCase("#00B09B")){
                        settings.setBackgroundResource(R.color.theme12);
                    }
                }
            }
        }else if(id.equals("2")){
            happening.setBackgroundResource(R.color.mytransparent);
            search.setBackgroundResource(R.color.mytransparent);
            city.setBackgroundResource(R.color.mytransparent);
            settings.setBackgroundResource(R.color.mytransparent);
            Fragment selectedFragment = null;
            selectedFragment = EntertainmentFragmentTamil.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selectedFragment);
            transaction.commit();
            if(colorcodes.length()==0){

            }else {
                if (colorcodes.equalsIgnoreCase("004")) {
                    Log.e("Msg", "hihihi");
                } else {
                    if (colorcodes.equalsIgnoreCase("#383838")) {
                        audio_video.setBackgroundResource(R.color.theme1button);
                    } else if (colorcodes.equalsIgnoreCase("#59247c")) {
                        audio_video.setBackgroundResource(R.color.theme2);
                    } else if (colorcodes.equalsIgnoreCase("#1d487a")) {
                        audio_video.setBackgroundResource(R.color.theme3);
                    } else if (colorcodes.equalsIgnoreCase("#7A4100")) {
                        audio_video.setBackgroundResource(R.color.theme4);
                    } else if (colorcodes.equalsIgnoreCase("#6E0138")) {
                        audio_video.setBackgroundResource(R.color.theme5);
                    } else if (colorcodes.equalsIgnoreCase("#00BFD4")) {
                        audio_video.setBackgroundResource(R.color.theme6);
                    } else if (colorcodes.equalsIgnoreCase("#185546")) {
                        audio_video.setBackgroundResource(R.color.theme7);
                    } else if (colorcodes.equalsIgnoreCase("#D0A06F")) {
                        audio_video.setBackgroundResource(R.color.theme8);
                    } else if (colorcodes.equalsIgnoreCase("#82C6E6")) {
                        audio_video.setBackgroundResource(R.color.theme9);
                    } else if (colorcodes.equalsIgnoreCase("#339900")) {
                        audio_video.setBackgroundResource(R.color.theme10);
                    } else if (colorcodes.equalsIgnoreCase("#CC9C00")) {
                        audio_video.setBackgroundResource(R.color.theme11);
                    } else if (colorcodes.equalsIgnoreCase("#00B09B")) {
                        audio_video.setBackgroundResource(R.color.theme12);
                    }
                }
            }
        }else if(id.equals("3")){
            city.setBackgroundResource(R.color.mytransparent);
            search.setBackgroundResource(R.color.mytransparent);
            audio_video.setBackgroundResource(R.color.mytransparent);
            settings.setBackgroundResource(R.color.mytransparent);
            Fragment selectedFragment = null;
            selectedFragment = HappeningFrag.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selectedFragment);
            transaction.commit();
            if(colorcodes.length()==0){

            }else {
                if(colorcodes.equalsIgnoreCase("004")){
                    Log.e("Msg","hihihi");
                }else {
                    if(colorcodes.equalsIgnoreCase("#383838")){
                        happening.setBackgroundResource(R.color.theme1button);
                    }else if(colorcodes.equalsIgnoreCase("#59247c")){
                        happening.setBackgroundResource(R.color.theme2);
                    }else if(colorcodes.equalsIgnoreCase("#1d487a")){
                        happening.setBackgroundResource(R.color.theme3);
                    }else if(colorcodes.equalsIgnoreCase("#7A4100")){
                        happening.setBackgroundResource(R.color.theme4);
                    }else if(colorcodes.equalsIgnoreCase("#6E0138")){
                        happening.setBackgroundResource(R.color.theme5);
                    }else if(colorcodes.equalsIgnoreCase("#00BFD4")){
                        happening.setBackgroundResource(R.color.theme6);
                    }else if(colorcodes.equalsIgnoreCase("#185546")){
                        happening.setBackgroundResource(R.color.theme7);
                    }else if(colorcodes.equalsIgnoreCase("#D0A06F")){
                        happening.setBackgroundResource(R.color.theme8);
                    }else if(colorcodes.equalsIgnoreCase("#82C6E6")){
                        happening.setBackgroundResource(R.color.theme9);
                    }else if(colorcodes.equalsIgnoreCase("#339900")){
                        happening.setBackgroundResource(R.color.theme10);
                    }else if(colorcodes.equalsIgnoreCase("#CC9C00")){
                        happening.setBackgroundResource(R.color.theme11);
                    }else if(colorcodes.equalsIgnoreCase("#00B09B")){
                        happening.setBackgroundResource(R.color.theme12);
                    }
                }
            }
        }else if(id.equals("4")){
            happening.setBackgroundResource(R.color.mytransparent);
            city.setBackgroundResource(R.color.mytransparent);
            audio_video.setBackgroundResource(R.color.mytransparent);
            settings.setBackgroundResource(R.color.mytransparent);
            Fragment selectedFragment = null;
            selectedFragment = MainFragTamil.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selectedFragment);
            transaction.commit();
            if(colorcodes.length()==0){

            }else {
                if(colorcodes.equalsIgnoreCase("004")){
                    Log.e("Msg","hihihi");
                }else {
                    if(colorcodes.equalsIgnoreCase("#383838")){
                        search.setBackgroundResource(R.color.theme1button);
                    }else if(colorcodes.equalsIgnoreCase("#59247c")){
                        search.setBackgroundResource(R.color.theme2);
                    }else if(colorcodes.equalsIgnoreCase("#1d487a")){
                        search.setBackgroundResource(R.color.theme3);
                    }else if(colorcodes.equalsIgnoreCase("#7A4100")){
                        search.setBackgroundResource(R.color.theme4);
                    }else if(colorcodes.equalsIgnoreCase("#6E0138")){
                        search.setBackgroundResource(R.color.theme5);
                    }else if(colorcodes.equalsIgnoreCase("#00BFD4")){
                        search.setBackgroundResource(R.color.theme6);
                    }else if(colorcodes.equalsIgnoreCase("#185546")){
                        search.setBackgroundResource(R.color.theme7);
                    }else if(colorcodes.equalsIgnoreCase("#D0A06F")){
                        search.setBackgroundResource(R.color.theme8);
                    }else if(colorcodes.equalsIgnoreCase("#82C6E6")){
                        search.setBackgroundResource(R.color.theme9);
                    }else if(colorcodes.equalsIgnoreCase("#339900")){
                        search.setBackgroundResource(R.color.theme10);
                    }else if(colorcodes.equalsIgnoreCase("#CC9C00")){
                        search.setBackgroundResource(R.color.theme11);
                    }else if(colorcodes.equalsIgnoreCase("#00B09B")){
                        search.setBackgroundResource(R.color.theme12);
                    }
                }
            }
        }
        else if(id.equals("5")){
            happening.setBackgroundResource(R.color.mytransparent);
            search.setBackgroundResource(R.color.mytransparent);
            audio_video.setBackgroundResource(R.color.mytransparent);
            settings.setBackgroundResource(R.color.mytransparent);
            Fragment selectedFragment = null;
            selectedFragment = SettingsFragmentTamil.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selectedFragment);
            transaction.commit();
            if(colorcodes.length()==0){

            }else {
                if(colorcodes.equalsIgnoreCase("004")){
                    Log.e("Msg","hihihi");
                }else {
                    if(colorcodes.equalsIgnoreCase("#383838")){
                        city.setBackgroundResource(R.color.theme1button);
                    }else if(colorcodes.equalsIgnoreCase("#59247c")){
                        city.setBackgroundResource(R.color.theme2);
                    }else if(colorcodes.equalsIgnoreCase("#1d487a")){
                        city.setBackgroundResource(R.color.theme3);
                    }else if(colorcodes.equalsIgnoreCase("#7A4100")){
                        city.setBackgroundResource(R.color.theme4);
                    }else if(colorcodes.equalsIgnoreCase("#6E0138")){
                        city.setBackgroundResource(R.color.theme5);
                    }else if(colorcodes.equalsIgnoreCase("#00BFD4")){
                        city.setBackgroundResource(R.color.theme6);
                    }else if(colorcodes.equalsIgnoreCase("#185546")){
                        city.setBackgroundResource(R.color.theme7);
                    }else if(colorcodes.equalsIgnoreCase("#D0A06F")){
                        city.setBackgroundResource(R.color.theme8);
                    }else if(colorcodes.equalsIgnoreCase("#82C6E6")){
                        city.setBackgroundResource(R.color.theme9);
                    }else if(colorcodes.equalsIgnoreCase("#339900")){
                        city.setBackgroundResource(R.color.theme10);
                    }else if(colorcodes.equalsIgnoreCase("#CC9C00")){
                        city.setBackgroundResource(R.color.theme11);
                    }else if(colorcodes.equalsIgnoreCase("#00B09B")){
                        city.setBackgroundResource(R.color.theme12);
                    }
                }
            }
        }
        else {

        }
    }



    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
            startActivity(intent);
            finish();
            System.exit(0);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}
