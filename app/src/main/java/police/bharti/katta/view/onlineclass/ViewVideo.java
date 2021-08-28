package police.bharti.katta.view.onlineclass;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import police.bharti.katta.R;
import police.bharti.katta.util.YoutubeConfig;

public class ViewVideo extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener,YouTubePlayer.PlaybackEventListener {
    String vcode;
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer myp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.activity_view_video);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        vcode = getIntent().getExtras().getString("url");
        try {
            youTubePlayerView = findViewById(R.id.you);
            youTubePlayerView.initialize(YoutubeConfig.getKey(), ViewVideo.this);
        } catch (Exception e) {

        }
    }
    public void next(View view)
    {
        try{
            if(view.getId()==R.id.button)
            {
                int cm = myp.getCurrentTimeMillis();
                myp.seekToMillis( cm-10000);
            }else {
                int cm = myp.getCurrentTimeMillis();
                myp.seekToMillis(10000 + cm);
            }
        }catch (Exception e)
        {

        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        //   .setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
        youTubePlayer.loadVideo(vcode.trim());
        myp=youTubePlayer;

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    @Override
    public void onPlaying() {

    }

    @Override
    public void onPaused() {

    }

    @Override
    public void onStopped() {

    }

    @Override
    public void onBuffering(boolean b) {

    }

    @Override
    public void onSeekTo(int i) {

    }
}