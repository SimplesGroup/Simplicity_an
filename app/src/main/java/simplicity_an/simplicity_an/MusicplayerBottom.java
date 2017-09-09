package simplicity_an.simplicity_an;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;

/**
 * Created by kuppusamy on 2/16/2017.
 */
public class MusicplayerBottom extends Fragment {
ImageButton playpause,forward,backward,close;
 public static LinearLayout musicbotttomfraglayout;
   public static MediaPlayer mediaPlayer;
    String playurl,music_title;
    TextView music_title_labels;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.musicbottomplayer,container,false);
        /*Bundle args=getArguments();
        if(args!=null){
            playurl=getArguments().getString("PLAYURL");
            music_title=getArguments().getString("TITLE");
        }*/
        musicbotttomfraglayout=(LinearLayout)view.findViewById(R.id.musicfrag) ;
close=(ImageButton)view.findViewById(R.id.closemusics);
        playpause=(ImageButton)view.findViewById(R.id.play);

        music_title_labels=(TextView)view.findViewById(R.id.title_musicname) ;
        music_title_labels.setText(music_title);

        //mediaPlayer.stop();
        mediaPlayer=new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
      /*  if(playurl!=null){
            callsong();
        }else {

        }*/

        if(mediaPlayer.isPlaying()){
            musicbotttomfraglayout.setVisibility(View.VISIBLE);
        }else {
            musicbotttomfraglayout.setVisibility(View.INVISIBLE);
            Log.e("TAG","NO RUN");
        }
        if(MusicplayerBottom.mediaPlayer.isPlaying()){
            musicbotttomfraglayout.setVisibility(View.VISIBLE);
        }

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
               musicbotttomfraglayout.setVisibility(View.INVISIBLE);
//MusicplayerBottom.this.dismiss();
            }
        });
        playpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    playpause.setImageResource(R.mipmap.playblack);
                    mediaPlayer.pause();
                }else {
                    playpause.setImageResource(R.mipmap.pauseblack);
                    mediaPlayer.start();
                }
            }
        });
        return view;
    }

    public void PlaySong(String playurl, String title){
        try {

            playpause.setImageResource(R.mipmap.pauseblack);
            musicbotttomfraglayout.setVisibility(View.VISIBLE);
            music_title_labels.setText(title);
            /*CommonMethod method=new CommonMethod();
            method.SoundPlayer(playurl,title);*/
            //mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
           mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.setDataSource(playurl);
            mediaPlayer.prepare();
            mediaPlayer.start();
music_title_labels.setText(title);
        } catch (IllegalStateException e) {
            //  Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
