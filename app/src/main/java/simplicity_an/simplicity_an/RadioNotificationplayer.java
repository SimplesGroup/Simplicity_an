package simplicity_an.simplicity_an;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import simplicity_an.simplicity_an.Explore.ExploreMain;

/**
 * Created by KuppuSamy on 8/30/2017.
 */

public class RadioNotificationplayer extends AppCompatActivity implements MediaPlayer.OnCompletionListener {
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String MYUSERID= "myprofileid";
    RequestQueue requestQueue;
    public static final String backgroundcolor = "color";
    String colorcodes,myprofileid;
    RelativeLayout mainlayout;
    ImageButton back,main,settings;

    TextView category,subtitle,authername,title,timecurrentseekbar,timebalanceseekbar;
    SeekBar songduration,sound;
    NetworkImageView imageview;
    ImageButton prev,next,play,soundbutton,menu,favbutton;
    private AudioManager myAudioManager;
    ImageLoader mImageLoader;
    Context context;
    private double startTime = 0;
    private double finalTime = 0;
    private Handler myHandler = new Handler();;
    MediaPlayer mediaPlayer;
    public static int oneTimeOnly = 0;
    Boolean isPlayClicked = false;
    private final String TAG_REQUEST = "MY_TAG";
    ProgressDialog pdialog;
    String radio_play_url,radio_title,radio_image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.radionotificationplayer);
        String simplycity_title_reugular= "fonts/robotoSlabBold.ttf";
        Typeface tf1 = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_reugular);
        requestQueue= Volley.newRequestQueue(this);


        Intent get=getIntent();
        radio_play_url=get.getStringExtra("URL");
        radio_title=get.getStringExtra("TITLE");
        radio_image=get.getStringExtra("IMAGE");
Log.e("RADIO",radio_play_url);
        sharedpreferences =  getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        colorcodes=sharedpreferences.getString(backgroundcolor,"");
        if (sharedpreferences.contains(MYUSERID)) {

            myprofileid=sharedpreferences.getString(MYUSERID,"");
            myprofileid = myprofileid.replaceAll("\\D+","");
        }
        mainlayout=(RelativeLayout)findViewById(R.id.version_main_layout);
        if(colorcodes.length()==0){

        }else {
            if(colorcodes.equalsIgnoreCase("004")){
                Log.e("Msg","hihihi");
            }else {

                if(colorcodes!=null){
                    int[] colors = {Color.parseColor(colorcodes), Color.parseColor("#FF000000"), Color.parseColor("#FF000000")};

                    GradientDrawable gd = new GradientDrawable(
                            GradientDrawable.Orientation.TOP_BOTTOM,
                            colors);
                    gd.setCornerRadius(0f);

                    mainlayout.setBackgroundDrawable(gd);
                }else {
                    int[] colors = {Color.parseColor("#383838"), Color.parseColor("#FF000000"), Color.parseColor("#FF000000")};

                    GradientDrawable gd = new GradientDrawable(
                            GradientDrawable.Orientation.TOP_BOTTOM,
                            colors);
                    gd.setCornerRadius(0f);

                    mainlayout.setBackgroundDrawable(gd);

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(backgroundcolor, "#383838");

                    editor.commit();
                }
            }
        }

        back=(ImageButton)findViewById(R.id.btn_ex_back) ;
        main=(ImageButton)findViewById(R.id.btn_ex_search);
        settings=(ImageButton)findViewById(R.id.btn_ex_more);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent in=new Intent(getApplicationContext(),ExploreMain.class);
                startActivity(in);*/
                onBackPressed();
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mImageLoader =  MySingleton.getInstance(getApplicationContext()).getImageLoader();
        imageview=(NetworkImageView)findViewById(R.id.thumbnailone);

        PhoneStateListener phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                if (state == TelephonyManager.CALL_STATE_RINGING) {
                    //Incoming call: Pause music
                    mediaPlayer.pause();
                } else if(state == TelephonyManager.CALL_STATE_IDLE) {
                    //Not in call: Play music
                    mediaPlayer.start();
                } else if(state == TelephonyManager.CALL_STATE_OFFHOOK) {
                    //A call is dialing, active or on hold
                    mediaPlayer.pause();
                }
                super.onCallStateChanged(state, incomingNumber);
            }
        };
        TelephonyManager mgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if(mgr != null) {

            mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
            mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }

        String simplycity_title_fontPath = "fonts/robotoSlabRegular.ttf";
        Typeface seguiregular = Typeface.createFromAsset(getApplicationContext().getAssets(), simplycity_title_fontPath);
        timebalanceseekbar=(TextView)findViewById(R.id.songDuration);
        timecurrentseekbar=(TextView)findViewById(R.id.songDurationfullleft);
        category=(TextView)findViewById(R.id.textView_catgory);
        authername=(TextView)findViewById(R.id.textView_authername);

        songduration=(SeekBar)findViewById(R.id.seekBar);

        prev=(ImageButton)findViewById(R.id.previous);
        next=(ImageButton)findViewById(R.id.next);
        play=(ImageButton)findViewById(R.id.play);

        category.setTypeface(seguiregular);
        authername.setTypeface(seguiregular);
        category.setText(radio_title);
        timecurrentseekbar.setTypeface(seguiregular);
        timebalanceseekbar.setTypeface(seguiregular);



        songduration.setMax((int) finalTime);


        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callsong();
            }
        });


        songduration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                try {
                    if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                        if (fromUser)
                            mediaPlayer.seekTo(progress);
                    } else if (mediaPlayer == null) {
                        Toast.makeText(getApplicationContext(), "Media is not running",
                                Toast.LENGTH_SHORT).show();
                        seekBar.setProgress(0);
                    }
                } catch (Exception e) {
                    Log.e("seek bar", "" + e);
                    seekBar.setEnabled(false);

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //SeekBar sb = (SeekBar);
                //mediaPlayer.seekTo(songduration.getProgress());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

if(radio_image!=null){
    imageview.setImageUrl(radio_image,mImageLoader);
    imageview.setErrorImageResId(R.drawable.ic_launcher);
    imageview.setDefaultImageResId(R.drawable.ic_launcher);
}else {
    imageview.setErrorImageResId(R.drawable.ic_launcher);
    imageview.setDefaultImageResId(R.drawable.ic_launcher);
}
        imageview.setErrorImageResId(R.drawable.ic_launcher);
        imageview.setDefaultImageResId(R.drawable.ic_launcher);
if(radio_play_url!=null){
    callsong();
}else {

}

    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    public void onCompletion(MediaPlayer mp) {
        mediaPlayer.stop();
        mediaPlayer.reset();
        songduration.setProgress(0);
        play.setImageResource(R.drawable.play);

    }


    private void callsong( ){

        int idval=0;
        int  next=0;


        try {
            if(radio_play_url!=null){
                mediaPlayer.setDataSource(radio_play_url);
            }


          //  for (int i = 0; i < modelList.size(); i++) {
//idval=Integer.parseInt(((ItemModel)modelList.get(i)).getId());
               /* idval=((Radioplayeractivity.ItemModel)modelList.get(i)).getTypeid();
                Log.e("valnext",String.valueOf(idval));
*/
               // if(myuserinteger==idval) {
                   // mediaPlayer.setDataSource(((Radioplayeractivity.ItemModel)modelList.get(position)).getLinks());
                    // prev=((ItemModel)modelList.get(position-1)).getId();


                    // setup song from http://www.hrupin.com/wp-content/uploads/mp3/testsong_20_sec.mp3 URL to mediaplayer data source
                    mediaPlayer.prepare();
              /*  }
            }*/

            // you must call this method after setup the datasource in setDataSource method. After calling prepare() the instance of MediaPlayer starts load data from URL to internal buffer.
        } catch (IllegalStateException e) {
            //  Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            play.setImageResource(R.drawable.pause);
        } else {
            if(mediaPlayer.isPlaying()){
                mediaPlayer.pause();
                play.setImageResource(R.drawable.play);
            } else {
                mediaPlayer.reset();
                play.setImageResource(R.drawable.play);
            }

        }
        finalTime = mediaPlayer.getDuration();
        startTime = mediaPlayer.getCurrentPosition();

        if (oneTimeOnly == 0) {
            songduration.setMax((int) finalTime);
            oneTimeOnly = 1;
        }
        timebalanceseekbar.setText(String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime)))
        );

        timecurrentseekbar.setText(String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime)))
        );

        songduration.setProgress((int)startTime);
        myHandler.postDelayed(UpdateSongTime,100);
    }
    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            timecurrentseekbar.setText(String.format("%d:%d",

                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );
            songduration.setProgress((int)startTime);
            myHandler.postDelayed(this, 100);
        }
    };

}
