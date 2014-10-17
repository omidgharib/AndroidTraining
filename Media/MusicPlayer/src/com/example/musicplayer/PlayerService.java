package com.example.musicplayer;

import java.io.File;
import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.IBinder;

public class PlayerService extends Service {

	MediaPlayer mediaPlayer;
	File audioFile;
	String SoundURL;
	
	public PlayerService() {
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
//		String sdCard = Environment.getExternalStorageDirectory().getAbsolutePath();
//		audioFile = new File(sdCard.concat("/Download/kalimba.mp3"));
		mediaPlayer = new MediaPlayer();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		int action = intent.getIntExtra(MainActivity.KEY_ACTION, -1);
		int SoundURL = intent.getIntExtra(MainActivity.Sound_URL, -1);
		
		switch (action) { 
		case MainActivity.ACTION_PLAY:
			play();
			break;
		case MainActivity.ACTION_PAUSE:
			pause();
			break;
		case MainActivity.ACTION_STOP:
			stop();
			break;
		case MainActivity.ACTION_Forward:
			forward();
			break;
		default:
			break;
		}
		
		return Service.START_NOT_STICKY;
	}
	
	private void play() {
		try {
			mediaPlayer = new MediaPlayer();
//    		mediaPlayer.release();
    		mediaPlayer.setDataSource(SoundURL);
			mediaPlayer.prepare();
			// mediaPlayer.prepareAsync(); 
			mediaPlayer.start();
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private void pause() { 
		if( mediaPlayer.isPlaying()) {
			mediaPlayer.pause();
		}
	}
	
	private void stop() {
		if( mediaPlayer.isPlaying()) {
			mediaPlayer.stop();
		}
	}
	
	private void forward(){
		try {
			mediaPlayer.setDataSource(SoundURL);
			mediaPlayer.prepare();
			mediaPlayer.start();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
