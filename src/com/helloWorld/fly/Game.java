package com.helloWorld.fly;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

import android.graphics.Point;
import android.view.Display;

public class Game extends BaseGameActivity{

	public static int CAMERA_WIDTH;
	public static int CAMERA_HEIGHT;
	private Camera mCamera;
	private static HUD mHUD;
	private static Game INSTANCE;
	
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		Point size = new Point();
		Display display = getWindowManager().getDefaultDisplay();
		display.getSize(size);
		CAMERA_WIDTH = size.x;
		CAMERA_HEIGHT = size.y;
		mHUD = new HUD();
		mCamera = new Camera(0,0,CAMERA_WIDTH,CAMERA_HEIGHT);
		mCamera.setHUD(mHUD);
		EngineOptions engineOptions = new EngineOptions(true,ScreenOrientation.LANDSCAPE_FIXED,new RatioResolutionPolicy(CAMERA_WIDTH,CAMERA_HEIGHT),mCamera);
		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
		return engineOptions;
	}
	
	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws Exception {
		INSTANCE = this;
		
		SceneManager.getInstance().init();
		GameManager.getInstance().init();
		ResourceManager.getInstance().loadResources();
		ResourceManager.getInstance().loadScenes();
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {
		Scene mScene = null;
		mScene = start();
		pOnCreateSceneCallback.onCreateSceneFinished(mScene);
		
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
	
	public static Scene start(){
		return SceneManager.getInstance().getGameScene();
	}
	
	public static Game getContext(){
		return INSTANCE;
	}
	
	public static HUD getHUD(){
		return mHUD;
	}

}
