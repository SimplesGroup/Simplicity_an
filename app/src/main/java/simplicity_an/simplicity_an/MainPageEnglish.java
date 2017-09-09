package simplicity_an.simplicity_an;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.onesignal.OneSignal;

import simplicity_an.simplicity_an.Explore.ExploreMain;
import simplicity_an.simplicity_an.MainEnglish.CityFragment;
import simplicity_an.simplicity_an.MainEnglish.EntertainmentFragment;
import simplicity_an.simplicity_an.MainEnglish.HappeningFrag;
import simplicity_an.simplicity_an.MainEnglish.MainFrag;
import simplicity_an.simplicity_an.MainEnglish.SettingsFragment;

/**
 * Created by kuppusamy on 5/18/2017.
 */

public class MainPageEnglish extends AppCompatActivity implements Tab_All.OnFragmentInteractionListener,TabRadio.OnFragmentInteractionListener,TabMusic.OnFragmentInteractionListener, TabEntertainmentAll.OnFragmentInteractionListener,TabEntertainmentMusic.OnFragmentInteractionListener,TabentertainmentRadio.OnFragmentInteractionListener{
    ImageButton city,happening,search,audio_video,settings,explore;

    LinearLayout footerbar;
    private ImageButton play_music,fastforward,backforward,close_player;
    TextView music_title_name;
    SharedPreferences sharedpreferences;
    public static final String backgroundcolor = "color";
    String activity,contentid,colorcodes;
    public static final String mypreference = "mypref";
    String myprofileid,cartcounts;
    public static final String MYUSERID= "myprofileid";
    public static final String GcmId = "gcmid";
    String playerid;
    String id,radio_title,radio_url;
    RelativeLayout topLevelLayout;
    private static int SPLASH_TIME_OUT = 4000;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.mainpageenglish);
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

        Instructions();
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
        topLevelLayout.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                topLevelLayout.setVisibility(View.INVISIBLE);
                return false;
            }

        });
      topLevelLayout.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                try{
                    topLevelLayout.setVisibility(View.GONE);
                }catch (Exception e){

                }

            }
        }, SPLASH_TIME_OUT);
        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               happening.setBackgroundResource(R.color.mytransparent);
                search.setBackgroundResource(R.color.mytransparent);
                audio_video.setBackgroundResource(R.color.mytransparent);
                settings.setBackgroundResource(R.color.mytransparent);
                Fragment selectedFragment = null;
                selectedFragment = CityFragment.newInstance();
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
              Intent in = new Intent(getApplicationContext(), ExploreMain.class);
                    startActivity(in);
         /*   android.app.FragmentManager fragmentManager = getFragmentManager();
                MainFrag fragment = new MainFrag();
                android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, fragment);
                fragmentTransaction.commit();*/
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
                selectedFragment = EntertainmentFragment.newInstance();
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
                SettingsFragment fragment = new SettingsFragment();
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
if(radio_url!=null){
    onFragmentInteraction(radio_url,radio_title);
}else {

}
        if(id!=null){
            changefrag();
        }else {


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, CityFragment.newInstance());
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


    private void  Instructions(){

      /*  topLevelLayout.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                topLevelLayout.setVisibility(View.INVISIBLE);
                return false;
            }

        });*/

    }
    public void onFragmentInteraction(String playurl, String title) {
        MusicplayerBottom secondFragment = (MusicplayerBottom) getSupportFragmentManager().findFragmentById(R.id.musicbottomplayer);
        secondFragment.PlaySong(playurl, title );
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
            SettingsFragment fragment = new SettingsFragment();
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
            selectedFragment = EntertainmentFragment.newInstance();
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
        }else {

        }
    }


}
