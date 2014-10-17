package com.example.musicplayer;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.R.array;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements OnClickListener{
	
	ImageButton play, stop, forward;
	public static final String KEY_ACTION = "KEY_ACTION";
	public static final String Sound_URL = "Sound_URL";
	public static final int ACTION_PLAY = 1;
	public static final int ACTION_PAUSE = 2;
	public static final int ACTION_STOP = 3;
	public static final int ACTION_Forward = 4;
	public static final int ACTION_Previous = 5;
	public String[] soundsList = new String[]{"a1.mp3","a2.mp3","a3.mp3","a4.mp3","a5.mp3"};
	
	
	public int playMode = 0; // stop
	public String CurrentSongURL = "";
	
	EditText songUrl;
	String MSSURL = "http://192.168.1.3"; //server url or ip
	String MSPort = "8082";
	String MSUri = "mediacenter";
	String albumUri = "Maroon5";
	String mediaFormat = "mp3";
	
	Uri songUri;
	String baseUrl;
	MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME
		        | ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_CUSTOM);
        
//        songUrl = (EditText) findViewById(R.id.editText_songurl);
        play = (ImageButton) findViewById(R.id.button_play);
        stop = (ImageButton) findViewById(R.id.button_stop); 
        forward = (ImageButton) findViewById(R.id.button_next);
        
        play.setOnClickListener(this);
        stop.setOnClickListener(this);
        forward.setOnClickListener(this);
//		pause.setOnClickListener(this);
//		stop.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

	
	public void log(String msg){
		Toast.makeText(this, msg, Toast.LENGTH_SHORT);
	}
	
	public void playAndPause(){
		Intent intent;
		if(playMode !=1 ){
			try {
				baseUrl = "http://omidgharib.ir/musicBox/";
				String songURL = MSSURL.concat(":").concat(MSPort).concat("/").concat(MSUri).concat("/").concat(albumUri).concat("/").concat("a3.mp3");
				
				intent = new Intent(this, PlayerService.class);
				intent.putExtra(KEY_ACTION, ACTION_PLAY);
				intent.putExtra(Sound_URL, songUri);
				startService(intent);
				playMode = 1; //play
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
//			mediaPlayer.pause();
			intent = new Intent(this, PlayerService.class);
			intent.putExtra(KEY_ACTION, ACTION_PLAY);
			startService(intent);
			playMode = 2; //pause
		}
	}


	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.button_play:
			playAndPause();
			break;
		case R.id.button_next:
			forward();
			break;
		case R.id.button_stop:
			stop();
			break;
		
		}
	}
	
	private void stop() {
		Intent intent;
		intent = new Intent(this, PlayerService.class);
		intent.putExtra(KEY_ACTION, ACTION_PLAY);
		intent.putExtra(Sound_URL, songUri);
		startService(intent);
	}


	private void forward() {
		String songUri = MSSURL.concat(":").concat(MSPort).concat("/").concat(MSUri).concat("/").concat(albumUri).concat("/").concat("a4.mp3");
//		if(mediaPlayer.isPlaying()){
//			mediaPlayer.stop();
//		}
		try {
//			mediaPlayer.setDataSource(songUri);
//			mediaPlayer.prepare();
//			mediaPlayer.start();
			Intent intent;
			intent = new Intent(this, PlayerService.class);
			intent.putExtra(KEY_ACTION, ACTION_Forward);
			intent.putExtra(Sound_URL, songUri);
			startService(intent);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
