package com.example.musicplayer;

import java.io.IOException;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements OnClickListener {
	
	Button play, pause, stop;
	public static final String KEY_ACTION = "KEY_ACTION";
	public static final int ACTION_PLAY = 1;
	public static final int ACTION_PAUSE = 2;
	public static final int ACTION_STOP = 3;
	
	EditText songUrl;
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
        
        songUrl = (EditText) findViewById(R.id.editText_songurl);
        play = (Button) findViewById(R.id.button_play);
        
        play.setOnClickListener(this);
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
    
    public void playOnClick(View v){
//    	if(songUrl.getText().length() > 0){
//    		try {
//    			baseUrl = "http://omidgharib.ir/musicBox/";
//    			String songUri = baseUrl.concat(songUrl.getText().toString());
//        		mediaPlayer = new MediaPlayer();
//        		mediaPlayer.setDataSource(songUri);
//				mediaPlayer.prepare();
//				mediaPlayer.start();
//			} catch (IllegalStateException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//    	}
//    	else
//    		Toast.makeText(this, "Plz enter the url of your song", Toast.LENGTH_SHORT).show();
    	
    	int id = v.getId();
		Intent intent;
		switch (id) {
		case R.id.button_play:
			intent = new Intent(this, PlayerService.class);
			intent.putExtra(KEY_ACTION, ACTION_PLAY);
			startService(intent);
			break;
//		case R.id.pauseButton:
//			intent = new Intent(this, PlayerService.class);
//			intent.putExtra(KEY_ACTION, ACTION_PAUSE);
//			startService(intent);
//			break;
//		case R.id.stopButton:
//			intent = new Intent(this, PlayerService.class);
//			intent.putExtra(KEY_ACTION, ACTION_STOP);
//			startService(intent);
//			break;
		}
    }
}
