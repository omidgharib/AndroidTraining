package com.example.example;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Audio.Media;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;
import android.hardware.Camera;
import android.hardware.Camera.Size;

public class MainActivity extends Activity implements SurfaceHolder.Callback, Camera.PictureCallback{
	
	private String flashMode = null;
	private String effectMode = null;
	SurfaceView surfaceView;
	SurfaceHolder surfaceHolder;
	Camera camera;
	List<Size> supportedSizes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME
		        | ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_CUSTOM);
		
		surfaceView = (SurfaceView) findViewById(R.id.surfaceView1);
		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		surfaceHolder.addCallback(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void captureImageOnClick(View v) {
		if (camera != null) {
			camera.takePicture(null, null, null, this);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
	    case R.id.Flash_Auto:
	    	flashMode = Camera.Parameters.FLASH_MODE_AUTO;
	      break;
	    case R.id.Flash_Off:
	    	flashMode = Camera.Parameters.FLASH_MODE_OFF;
	    	break;
	    case R.id.Flash_On:
	    	flashMode = Camera.Parameters.FLASH_MODE_ON;
	    	break;
	    case R.id.Flash_RedEye:
	    	flashMode = Camera.Parameters.FLASH_MODE_RED_EYE;
	    	break;
	    case R.id.Effects_MONO:
	    	effectMode = Camera.Parameters.EFFECT_MONO;
	    	break;
	    case R.id.Effects_Solarize:
	    	effectMode = Camera.Parameters.EFFECT_SOLARIZE;
	    	break;
	    default:
	      break;
	    }
		updateParamsToCamera();
	    return true;
	}

	@Override
	public void onPictureTaken(byte[] data, Camera camera) {
		Uri imageFileUri = getContentResolver().insert(
				Media.EXTERNAL_CONTENT_URI, new ContentValues());
		try {
			OutputStream imageFileOS = getContentResolver().openOutputStream(imageFileUri);
			imageFileOS.write(data);
			imageFileOS.flush();
			imageFileOS.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		camera.startPreview();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
			camera = Camera.open(0);

			Camera.Parameters parameters = camera.getParameters();

			// supportedSizes = parameters.getSupportedPictureSizes();
			// for( Size size : supportedSizes) {
			// Log.i("camera", "Width:" + size.width + " Height:" +
			// size.height);
			// }
			// parameters.setPictureSize(supportedSizes.get(supportedSizes.size()-1).width,
			// supportedSizes.get(supportedSizes.size()-1).height);

			// parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);

			// parameters.setSceneMode(Camera.Parameters.SCENE_MODE_ACTION);

//			List<String> colorEffects = parameters.getSupportedColorEffects();
//			if (colorEffects != null && effectMode != null && colorEffects.contains(effectMode)) {
//				parameters.setColorEffect(effectMode);
//			}
			
//			List<String> flashModes = parameters.getSupportedFlashModes();
//			if (flashModes != null && flashMode != null && flashModes.contains(flashMode)) {
//				parameters.setFlashMode(flashMode);
//			}

			camera.setParameters(parameters);
			camera.setPreviewDisplay(holder);
			camera.startPreview();
		} catch (Exception e) {
			if(camera != null)
				camera.release();
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if(camera != null){
			camera.stopPreview();
			camera.release();
		}
	}
	
	public void updateParamsToCamera(){
		Camera.Parameters parameters = camera.getParameters();
		List<String> colorEffects = parameters.getSupportedColorEffects();
//		for (int i = 0; i < colorEffects.size(); i++) {
//			Log.d("colorEffect", colorEffects.toString());
//		}
		if (colorEffects != null && effectMode != null && colorEffects.contains(effectMode)) {
			parameters.setColorEffect(effectMode);
		}
		
		List<String> flashModes = parameters.getSupportedFlashModes();
		if (flashModes != null && flashMode != null && flashModes.contains(flashMode)) {
			parameters.setFlashMode(flashMode);
		}

		camera.setParameters(parameters);
	}
}
